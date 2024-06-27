package wf.utils.config.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ThreadMultipleLoopTask extends ThreadLoopTask {


    private final HashMap<String, Runnable> runnableMap = new HashMap<>();
    public final ArrayList<Runnable> runnables = new ArrayList<>();


    public ThreadMultipleLoopTask(long delay, long period) {
        super(null, period, delay);
        setRunnable(() -> {
            for(Runnable runnable : runnables)
                runnable.run();
        });
    }


    public void addRunnable(String name, Runnable runnable) {
        Runnable oldRunnable = runnableMap.put(name, runnable);
        if(oldRunnable != null && oldRunnable != runnable) runnables.remove(oldRunnable);
        runnables.add(runnable);
    }


    public boolean removeRunnable(String name) {
        Runnable deletedRunnable = runnableMap.remove(name);
        if(deletedRunnable != null){
            runnables.remove(deletedRunnable);
            return true;
        }

        return false;
    }
}
