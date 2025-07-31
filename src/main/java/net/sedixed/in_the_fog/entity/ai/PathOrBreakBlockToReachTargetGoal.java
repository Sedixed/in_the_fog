package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.sedixed.in_the_fog.util.LookManager;

import java.util.ArrayList;
import java.util.List;

public class PathOrBreakBlockToReachTargetGoal extends Goal {
    private final Mob mob;
    private BlockPos targetBlock;
    private int breakTicks;
    private int lastStage = -1;
    private boolean lockedPositionForMining;

    public PathOrBreakBlockToReachTargetGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() == null) {
            return false;
        }
        Path path = mob.getNavigation().createPath(mob.getTarget(), 0);

        if (path != null && path.canReach()) {
            mob.getLookControl().setLookAt(mob.getTarget());
            return false;
        }

        BlockPos blocking = getBlockingBlock();
        if (blocking == null) {
            return false;
        }

        if (!canSeeBlock(blocking)) {
            mob.getLookControl().setLookAt(mob.getTarget());
            return false;
        }

        this.targetBlock = blocking;
        lockedPositionForMining = true;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.getTarget() == null) {
            return false;
        }

        Path path = mob.getNavigation().createPath(mob.getTarget(), 0);
        if (path != null && path.canReach()) {
            return false;
        }

        return breakTicks < getBreakDuration(mob.level().getBlockState(targetBlock));
    }

    @Override
    public void start() {
        breakTicks = 0;
        lastStage = -1;
    }

    @Override
    public void tick() {
        breakTicks++;

        Level level = mob.level();
        BlockState state = level.getBlockState(targetBlock);

        if (state.isAir() || state.getDestroySpeed(level, targetBlock) < 0) {
            return;
        }

        Vec3 targetBlockCenter = targetBlock.getCenter();
        mob.getLookControl().setLookAt(targetBlockCenter.x, targetBlockCenter.y, targetBlockCenter.z, 360.0f, 360.0f);
        if (lockedPositionForMining) {
            // No movement nor rotation
            this.mob.getNavigation().stop();
            this.mob.setDeltaMovement(Vec3.ZERO);
            LookManager.forceEntityLookAt(mob, targetBlockCenter, null);
        }

        int breakTime = getBreakDuration(state);

        // Break animation
        int stage = (int) ((float) breakTicks / breakTime * 10.0F);
        if (stage != lastStage) {
            lastStage = stage;
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.destroyBlockProgress(mob.getId(), targetBlock, stage);
            }
        }

        if (breakTicks % 2 == 0) {
            this.mob.swing(InteractionHand.MAIN_HAND);
        }

        if (breakTicks % 5 == 0) {
            level.playSound(null, targetBlock, state.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        if (breakTicks >= breakTime) {
            level.destroyBlock(targetBlock, true, mob);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.destroyBlockProgress(mob.getId(), targetBlock, -1);
                lockedPositionForMining = false;
            }
        }
    }

    private BlockPos getBlockingBlock() {
        if (mob.getTarget() == null) {
            return null;
        }

        Vec3 start = mob.getEyePosition(1.0F);
        Vec3 endHead = mob.getTarget().getEyePosition(1.0F);
        Vec3 endFeet = mob.getTarget().position();

        double mobY = mob.getEyeY() - 0.5;
        int sampleCount = 8;
        List<Vec3> intermediatePoints = getIntermediateTargets(start, endFeet, mobY, sampleCount);

        List<Vec3> targets = new ArrayList<>();
        targets.add(endHead);
        targets.add(endFeet);
        targets.addAll(intermediatePoints);

        for (Vec3 end: targets) {
            if (start.distanceToSqr(end) < 0.25) {
                continue;
            }

            BlockHitResult hit = mob.level().clip(new ClipContext(
                    start,
                    end,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    mob
            ));

            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = hit.getBlockPos();
                BlockState state = mob.level().getBlockState(pos);
                if (state.getDestroySpeed(mob.level(), pos) >= 0 && !state.isAir() && isCloseEnough(pos)) {
                    return pos;
                }
            }
        }

        return null;
    }

    private List<Vec3> getIntermediateTargets(Vec3 start, Vec3 end, double fixedY, int samples) {
        List<Vec3> result = new ArrayList<>();
        Vec3 delta = end.subtract(start);
        for (int i = 1; i <= samples; i++) {
            double t = i / (double)(samples + 1);
            Vec3 point = start.add(delta.scale(t));
            result.add(new Vec3(point.x, fixedY, point.z));
        }
        return result;
    }


    private boolean canSeeBlock(BlockPos blockPos) {
        Vec3 eyePos = mob.getEyePosition(1.0F);
        Vec3 blockCenter = Vec3.atCenterOf(blockPos);
        Vec3 toBlock = blockCenter.subtract(eyePos).normalize();
        Vec3 lookVec = mob.getViewVector(1.0F).normalize();

        double angle = Math.acos(lookVec.dot(toBlock)) * (180 / Math.PI);
        return angle < 45;

    }

    private boolean isCloseEnough(BlockPos blockPos) {
        double distSq = mob.position().distanceToSqr(Vec3.atCenterOf(blockPos));
        return distSq <= 16.0; // 4 blocks
    }

    private int getBreakDuration(BlockState state) {
        float hardness = state.getDestroySpeed(mob.level(), targetBlock);
        if (hardness < 0) {
            return Integer.MAX_VALUE;
        }

        float miningSpeed = 1.0F;

        ItemStack tool = mob.getMainHandItem();
        if (!tool.isEmpty()) {
            miningSpeed = tool.getDestroySpeed(state);
        }

        if (miningSpeed <= 0) {
            return Integer.MAX_VALUE;
        }

        int ticks = (int) Math.ceil((hardness * 20) / miningSpeed);
        return Math.max(ticks, 1);
    }
}
