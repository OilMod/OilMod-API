package org.oilmod.api.items.type;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.data.LongData;
import org.oilmod.api.items.OilItemStack;

/**
 * Tickable items are ticked by the api. The contact only includes that items visible to the player are repeatedly ticked.
 * Repeatedly means in regular intervals to provide a seamless experience to the player, it does not mean that they are ticked every tick.
 * Generally items not visible to the player are ticked the moment they come visible, allowing them to catch up.
 * Implementations for this have to provide a ticking function that has a runtime promotional to log(ticksToProcess)
 *
 * Generally this might break the expectations players have around chunk-loading and that items in unloaded chunks are frozen.
 * The implementation can decide to mimic chunk-loading behavior. e.g. ticking on chunk save, setting last tick on chunk load
 * If chunk-loading if mimicked the getMaxLazyTicks property is to be ignored!
 *
 * tickable items store a lastTick property
 */
public interface ITickable extends IUnique{
    String lastTickKey = "lastTick";
    static void processTicks(OilItemStack stack, long currentTick, boolean limit) {
        LongData lasttick = getLastTickData(stack);
        ITickable tickable = ((ITickable)stack.getItem());
        long ticksToProcess =  currentTick - lasttick.getData();
        if (limit) ticksToProcess = Math.min(ticksToProcess, tickable.getMaxLazyTicks());
        if (ticksToProcess <= 0)return;
        tickable.onTick(stack, ticksToProcess);
        lasttick.setData(currentTick);
    }

    static void setLastTick(OilItemStack stack, long value) {
        LongData lasttick = getLastTickData(stack);
        lasttick.setData(value);
    }

    static LongData getLastTickData(OilItemStack stack) {
        Validate.isTrue(stack.getItem() instanceof ITickable, "cannot retrieve lastTick tag from non tickable item!");
        LongData lasttick = (LongData) stack.getRegisteredIData().get(lastTickKey);
        if (lasttick == null) throw new IllegalStateException("lastTick IData was not set... huh? Did you overwrite initStack and forgot to call ITickable.super.initStack?");
        return lasttick;
    }

    default void initStack(OilItemStack stack){
        new LongData(lastTickKey, stack); //self registers
    }


    void onTick(OilItemStack stack, long ticksToProcess);
    default int getMaxLazyTicks() {return 20*60*60*24*7;}//1 week
}
