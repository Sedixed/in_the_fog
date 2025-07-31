package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.sedixed.in_the_fog.util.LookManager;

public class ClimbLadderGoal extends Goal {
    private final Mob mob;
    private final double approachSpeed = 0.05;
    private final double climbSpeed = 0.15;
    private final double descendSpeed = -0.5;
    private final double minApproachDistance = 0.05;

    public ClimbLadderGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() == null || !mob.onClimbable()) {
            return false;
        }

        return true;
    }

    private boolean isLadder(BlockState state) {
        return state.is(Blocks.LADDER) && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public void start() {
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        BlockPos ladderPos = mob.blockPosition();
        BlockState state = mob.level().getBlockState(ladderPos);

        BlockPos blockAbove = ladderPos.above();
        BlockState stateAbove = mob.level().getBlockState(blockAbove);

        BlockPos blockBelow = ladderPos.below();
        BlockState stateBelow = mob.level().getBlockState(blockBelow);

        // on the edge of the top ladder or at the bottom of the ladder
        if (mob.onGround()) {
            return;
        }

        // block below and above not a ladder
        if (!isLadder(stateBelow) && !isLadder(stateAbove)) {
            mob.getJumpControl().jump();
            return;
            /**
            Direction attachedTo = stateUnderBelow.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Vec3 pushTowardWall = Vec3.atLowerCornerOf(attachedTo.getNormal()).scale(-0.1);
            mob.setDeltaMovement(
                    pushTowardWall.x,
                    mob.getDeltaMovement().y,
                    pushTowardWall.z
            );

            BlockPos wallPos = ladderPos.relative(attachedTo.getOpposite());
            Vec3 lookAt = Vec3.atCenterOf(wallPos);
            mob.getLookControl().setLookAt(lookAt.x, mob.getY(), lookAt.z);
            System.out.println("case 2");
            return;
             */
        }

        // Weird case but may happen if ladders are placed randomly
        if (!isLadder(state) && !isLadder(stateBelow)) {
            return;
        }

        // Close entity to ladder and nullify lateral movement
        Direction attachedTo = (
                state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) ?
                        state :
                        stateBelow
        ).getValue(BlockStateProperties.HORIZONTAL_FACING);
        Vec3 ladderCenter = Vec3.atCenterOf(ladderPos);
        Vec3 toLadder = ladderCenter.subtract(mob.position());
        Vec3 toLadderHorizontal = new Vec3(toLadder.x, 0, toLadder.z);
        double dist = toLadderHorizontal.length();

        if (dist > minApproachDistance) {
            Vec3 correction = toLadderHorizontal.normalize().scale(approachSpeed);
            mob.setDeltaMovement(mob.getDeltaMovement().add(correction.x, 0, correction.z));
        }

        double dy = mob.getTarget().getY() - mob.getY();

        // ~ same height
        if (Math.abs(dy) <= 0.5) {
            double newDy = 0;
            if (dy < -0.1d) {
                newDy = descendSpeed;
            } else if (dy > 0.1d) {
                newDy = climbSpeed;
            }

            Vec3 toTarget = mob.getTarget().position().subtract(mob.position());
            Vec3 toTargetHoriz = new Vec3(toTarget.x, 0, toTarget.z);
            Vec3 horiz = toTargetHoriz.normalize().scale(0.2);
            LookManager.forceEntityLookAt(mob, mob.getTarget().position(), null);

            mob.setDeltaMovement(
                    horiz.x,
                    newDy,
                    horiz.z
            );
            mob.fallDistance = 0.0F;

            if (newDy == 0) {
                mob.getNavigation().moveTo(mob.getTarget(), 1.0);
            }
            return;
        }

        // Ladder pos for look
        BlockPos attachedBlockPos = ladderPos.relative(attachedTo.getOpposite());
        // player above
        if (dy > 0.5) {
            mob.setDeltaMovement(mob.getDeltaMovement().x, climbSpeed, mob.getDeltaMovement().z);
            LookManager.forceEntityLookAt(mob, attachedBlockPos, mob.getEyeY() + 0.5);
            mob.fallDistance = 0.0F;
        // player below
        } else {
            mob.setDeltaMovement(mob.getDeltaMovement().x, descendSpeed, mob.getDeltaMovement().z);
            LookManager.forceEntityLookAt(mob, attachedBlockPos, mob.getEyeY() - 1);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }
}
