package org.oilmod.api.util;

import gnu.trove.set.hash.THashSet;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by sirati97 on 14.02.2016.
 */
public class WeakReferenceTicker implements Runnable{
    private Set<WeakReference<Tickable>> tickables= new THashSet<>();
    private int delay;
    private int times;
    private long lastTick = 0;

    public WeakReferenceTicker(int delay, int times) {
        this.delay = delay;
        this.times = times;
    }

    public synchronized void tick(final int times) {
        lastTick = System.currentTimeMillis();
        final Set<WeakReference<Tickable>> tickables_toDelete= new THashSet<>();
        for (WeakReference<Tickable> tickableWeakReference:tickables) {
            if (tickableWeakReference.get()==null) {
                tickables_toDelete.add(tickableWeakReference);
            } else {
                try {
                    tickableWeakReference.get().tick(times);
                } catch (Throwable t) {
                    Exception e = new ExecutionException("Error ticking " + tickableWeakReference.get().toString(),t);
                    e.printStackTrace();
                }
            }
        }
        for (WeakReference<Tickable> tickableWeakReference:tickables_toDelete) {
            tickables.remove(tickableWeakReference);
        }
    }

    @Override
    public void run() {
        tick(times);
        if (tickables.size() >0) {
            OilUtil.runTaskLater( this, delay);
        }
    }

    public synchronized void add(Tickable tickable) {
        if (tickable == null || !tickable.isTickable())return;
        for (WeakReference<Tickable> tickableWeakReference:tickables) {
            if (tickableWeakReference.get()==tickable)return;
        }
        tickables.add(new WeakReference<>(tickable));

        if (tickables.size() == 1) {
            long timeSinceLastTick = (System.currentTimeMillis()-lastTick)/50;
            if (timeSinceLastTick >= delay) {
                OilUtil.runTask(this);
            } else {
                OilUtil.runTaskLater(this, timeSinceLastTick);
            }
        }
    }
}
