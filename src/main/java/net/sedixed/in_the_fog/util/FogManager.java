package net.sedixed.in_the_fog.util;

import net.minecraft.client.Minecraft;

public class FogManager {
    private static boolean fogActive = false;
    private static float fogProgress = 0.0f;

    private static final float SPEED = 0.004f;
    private static int fogTimer = 0;
    private static final float fogChancePercentagePerTick = 0.1f;

    public static void tick(Minecraft mc) {
        if (mc.level == null || mc.player == null) return;

        if (fogActive && fogProgress < 1.0f) {
            fogProgress = Math.min(1.0f, fogProgress + SPEED);
        } else if (!fogActive && fogProgress > 0.0f) {
            fogProgress = Math.max(0.0f, fogProgress - SPEED);
        }

        if (fogActive) {
            fogTimer--;
            if (fogTimer <= 0) {
                fogActive = false;
            }
        } else {
            if (mc.level.random.nextFloat() < fogChancePercentagePerTick / 100.0f) {
                fogActive = true;
                fogTimer = 20 * 30 + mc.level.random.nextInt(20 * 60);
            }
        }
    }

    public static float getFogProgress() {
        return fogProgress;
    }

    public static boolean shouldRenderFog() {
        return fogProgress > 0.01f;
    }

    public static void setForcedFog(boolean enabled) {
        fogActive = enabled;
    }
}
