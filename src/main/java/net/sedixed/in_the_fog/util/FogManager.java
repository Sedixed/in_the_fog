package net.sedixed.in_the_fog.util;

import net.minecraft.client.Minecraft;

public class FogManager {
    private static boolean fogActive = false;
    private static float fogProgress = 0.0f;
    private static int fogTimer = 0;

    private static final float SPEED = 0.004f;
    private static final float FOG_CHANCE_PERCENTAGE_PER_TICK = 0.1f;

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
            if (mc.level.random.nextFloat() < FOG_CHANCE_PERCENTAGE_PER_TICK / 100.0f) {
                fogActive = true;
                fogTimer = 20 * 30 + mc.level.random.nextInt(20 * 60); // TODO fog duration
            }
        }
    }

    public static float getFogProgress() {
        return fogProgress;
    }

    public static boolean shouldRenderFog() {
        return fogProgress > 0.0f;
    }

    public static void setFogActive(boolean enabled) {
        fogActive = enabled;
    }

    public static boolean isFogActive() {
        return fogActive;
    }
}
