package de.sirati97.oilmod.api.util;

import gnu.trove.set.hash.THashSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by sirati97 on 14.02.2016.
 */
public class WeakReferenceTicker implements Runnable{
    private Plugin plugin;
    private Set<WeakReference<Tickable>> tickables= new THashSet<>();
    private int delay;
    private int times;
    private long lastTick = 0;

    public WeakReferenceTicker(Plugin plugin, int delay, int times) {
        this.plugin = plugin;
        this.delay = delay;
        this.times = times;
    }

    public void tick(final int times) {
        lastTick = System.currentTimeMillis();
        final Set<WeakReference<Tickable>> tickables_toDelete= new THashSet<>();
        tickables.forEach(new Consumer<WeakReference<Tickable>>() {
            @Override
            public void accept(WeakReference<Tickable> tickableWeakReference) {
                if (tickableWeakReference.get()==null) {
                    tickables_toDelete.add(tickableWeakReference);
                } else {
                    tickableWeakReference.get().tick(times);
                }
            }
        });
        tickables_toDelete.forEach(new Consumer<WeakReference<Tickable>>() {
            @Override
            public void accept(WeakReference<Tickable> tickableWeakReference) {
                tickables.remove(tickableWeakReference);
            }
        });
    }

    @Override
    public void run() {
        tick(times);
        if (tickables.size() >0) {
            Bukkit.getScheduler().runTaskLater(plugin, this, delay);
        }
    }

    public void add(Tickable tickable) {
        if (tickable == null || !tickable.isTickable())return;
        for (WeakReference<Tickable> tickableWeakReference:tickables) {
            if (tickableWeakReference.get()==tickable)return;
        }
        tickables.add(new WeakReference<>(tickable));
        if (tickables.size() == 1) {
            long timeSinceLastTick = (System.currentTimeMillis()-lastTick)/50;
            if (timeSinceLastTick >= delay) {
                run();
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, this, timeSinceLastTick);
            }
        }
    }
}
