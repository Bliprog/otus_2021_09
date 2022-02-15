import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PingPongHomeWork {
    private static final Logger logger = LoggerFactory.getLogger(PingPongHomeWork.class);
    private int threadId =1;
    private final Map<String, Integer> countMap=new HashMap<>();
    private final Map<String, String> actionMap=new HashMap<>();

    private synchronized void action(int id){
        while(!Thread.currentThread().isInterrupted()) {
            try {
                while (threadId==id) {
                    this.wait();
                }

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
                threadId = id;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void work(){
    countMap.put("Thread-0",1);
    countMap.put("Thread-1",1);
    actionMap.put("Thread-0","plus");
    actionMap.put("Thread-1","plus");
    new Thread(() -> this.action(2)).start();
    new Thread(() -> this.action(1)).start();

    }
}
