import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorHomeWork {
    private final Map<String, Integer> countMap=new HashMap<>();
    private final Map<String, String> actionMap=new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private void action(){
        while(!Thread.currentThread().isInterrupted()) {
            var threadName=Thread.currentThread().getName();
            var count = countMap.get(threadName);
            var action = actionMap.get(threadName);
            System.out.println(threadName+":"+countMap.get(Thread.currentThread().getName()));
            if(count>=10) {
                action="minus";
            }
            else {
                if(count<=1)
                    action="plus";
            }
            if(action.equals("plus")){
                count+=1;
            }
            else {
                count-=1;
            }
            actionMap.put(threadName,action);
            countMap.put(threadName,count);
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void work(){
        countMap.put("pool-1-thread-1",1);
        countMap.put("pool-1-thread-2",1);
        actionMap.put("pool-1-thread-1","plus");
        actionMap.put("pool-1-thread-2","plus");
        executor.submit(()->this.action());
        executor.submit(()->this.action());

    }

}
