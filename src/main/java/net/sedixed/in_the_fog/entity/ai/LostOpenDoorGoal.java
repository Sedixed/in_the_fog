package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;

public class LostOpenDoorGoal extends OpenDoorGoal {
    public LostOpenDoorGoal(Mob pMob, boolean pCloseDoor) {
        super(pMob, pCloseDoor);
    }

    @Override
    protected void setOpen(boolean pOpen) {
        super.setOpen(pOpen);
        this.mob.swing(InteractionHand.MAIN_HAND);
    }
}
