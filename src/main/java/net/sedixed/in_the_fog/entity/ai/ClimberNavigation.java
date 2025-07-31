package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ClimberNavigation extends GroundPathNavigation {
    public ClimberNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected boolean canMoveDirectly(Vec3 startVec, Vec3 endVec) {
        return super.canMoveDirectly(startVec, endVec) || isClimbableBlockAt(endVec);
    }

    private boolean isClimbableBlockAt(Vec3 position) {
        BlockPos pos = BlockPos.containing(position);
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.LADDER) || state.is(Blocks.VINE) || state.is(Blocks.SCAFFOLDING);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new ClimberNodeEvaluator();
        this.nodeEvaluator.setCanWalkOverFences(true);
        this.nodeEvaluator.setCanOpenDoors(true);
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanFloat(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}
