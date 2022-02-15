import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockHomeWork {
    private final Lock lock = new ReentrantLock();
    private int threadId =1;
    private final Map<String, Integer> countMap=new HashMap<>();
    private final Map<String, String> actionMap=new HashMap<>();

    private void action(){
        while(!Thread.currentThread().isInterrupted()) {
                var threadName=Thread.currentThread().getName();
                var count = countMap.get(threadName);
                var action = actionMap.get(threadName);
                lock.lock();
                System.out.println(threadName+":"+countMap.get(Thread.currentThread().getName()));
                lock.unlock();
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
        countMap.put("Thread-0",1);
        countMap.put("Thread-1",1);
        actionMap.put("Thread-0","plus");
        actionMap.put("Thread-1","plus");
        new Thread(() -> this.action()).start();
        new Thread(() -> this.action()).start();

    }
}
