import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockHomeWork {
    private final Lock lock = new ReentrantLock();
    String nextThread = "Thread-0";
    private final Map<String, Integer> countMap = new HashMap<>();
    private final Map<String, String> actionMap = new HashMap<>();

    private void action(String nextThr) {
        while (!Thread.currentThread().isInterrupted()) {
            var threadName = Thread.currentThread().getName();
            while (threadName.equals(nextThread)) {
                lock.lock();
                var count = countMap.get(threadName);
                var action = actionMap.get(threadName);
                System.out.println(threadName + ":" + countMap.get(Thread.currentThread().getName()));
                if (count >= 10) {
                    action = "minus";
                } else {
                    if (count <= 1)
                        action = "plus";
                }
                if (action.equals("plus")) {
                    count += 1;
                } else {
                    count -= 1;
                }
                actionMap.put(threadName, action);
                countMap.put(threadName, count);
                nextThread = nextThr;
                lock.unlock();
                sleep();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void work() {
        countMap.put("Thread-0", 1);
        countMap.put("Thread-1", 1);
        actionMap.put("Thread-0", "plus");
        actionMap.put("Thread-1", "plus");
        new Thread(() -> this.action("Thread-1")).start();
        new Thread(() -> this.action("Thread-0")).start();

    }
}
