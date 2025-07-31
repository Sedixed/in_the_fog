package net.sedixed.in_the_fog.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class LookManager {
    public static void forceEntityLookAt(Mob mob, BlockPos pos, Double customY) {
        Vec3 vec3 = Vec3.atCenterOf(pos);
        forceEntityLookAt(mob, vec3, customY);
    }

    public static void forceEntityLookAt(Mob mob, Vec3 vec3, Double customY) {
        mob.getLookControl().setLookAt(
                vec3.x,
                customY == null ? vec3.y : customY,
                vec3.z,
                30.0f,
                30.0f
        );
        // No rotation
        double dx = vec3.x - mob.getX();
        double dy = customY == null ? vec3.y - mob.getEyeY() : customY;
        double dz = vec3.z - mob.getZ();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float pitch = (float) (-(Mth.atan2(dy, distanceXZ) * (180F / Math.PI)));

        mob.setYRot(yaw);
        mob.setYHeadRot(yaw);
        mob.yBodyRot = yaw;
        mob.yHeadRot = yaw;
        mob.setXRot(pitch);
    }

}
