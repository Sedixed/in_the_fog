package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class LostTargetGoal extends TargetGoal {
    private final Mob mob;
    private LivingEntity target;

    private final TargetingConditions conditions = TargetingConditions.forCombat()
            .ignoreLineOfSight()
            .ignoreInvisibilityTesting()
            .selector(entity -> entity instanceof Player);

    public LostTargetGoal(Mob mob) {
        super(mob, false);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        double range = 32.0D;
        double height = 16.0D;
        AABB searchBox = this.mob.getBoundingBox().inflate(range, height, range);

        this.target = this.mob.level().getNearestEntity(
                this.mob.level().getEntitiesOfClass(Player.class, searchBox),
                this.conditions,
                this.mob,
                this.mob.getX(),
                this.mob.getY(),
                this.mob.getZ()
        );

        return this.target != null;
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.target.isAlive();
    }
}

