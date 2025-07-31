package net.sedixed.in_the_fog.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class ClimberNodeEvaluator extends WalkNodeEvaluator {
    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z, Mob mob) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = level.getBlockState(pos);

        if (state.is(Blocks.LADDER) || state.is(Blocks.VINE) || state.is(Blocks.SCAFFOLDING)) {
            return BlockPathTypes.WALKABLE;
        }

        return super.getBlockPathType(level, x, y, z, mob);
    }

    @Override
    public int getNeighbors(Node[] neighbors, Node current) {
        int count = super.getNeighbors(neighbors, current);

        BlockPos pos = new BlockPos(current.x, current.y, current.z);
        BlockState currentState = this.level.getBlockState(pos);

        if (isClimbable(currentState)) {
            BlockPos upPos = pos.above();
            BlockState upState = this.level.getBlockState(upPos);

            if (isClimbable(upState)) {
                Node upNode = getNode(current.x, current.y + 1, current.z);
                if (upNode != null && !upNode.closed) {
                    upNode.type = BlockPathTypes.WALKABLE;
                    neighbors[count++] = upNode;
                }
            }
        }

        return count;
    }

    private boolean isClimbable(BlockState state) {
        return state.is(Blocks.LADDER) || state.is(Blocks.VINE) || state.is(Blocks.SCAFFOLDING);
    }


}
