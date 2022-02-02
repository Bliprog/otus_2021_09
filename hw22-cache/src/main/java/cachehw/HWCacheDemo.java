package cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        HwCache<String, Integer> cache = new MyCache<>();
        var instance= new HWCacheDemo();
        instance.demo(cache);
        instance.demo2(cache);
    }

    private void demo(HwCache<String, Integer> cache) {

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put(new String("1"), 1);
        listener=null;
        logger.info("getValue:{}", cache.get("1"));
        System.gc();
    }

    private void demo2(HwCache<String, Integer> cache){
        cache.put(new String("2"), 21);
        logger.info("demo 2 getValue:{}", cache.get("2"));
    }
}
