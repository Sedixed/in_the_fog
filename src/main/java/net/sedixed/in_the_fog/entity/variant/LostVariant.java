package net.sedixed.in_the_fog.entity.variant;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.in_the_fog.InTheFogMod;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum LostVariant {
    DEFAULT(0),
    FADE_AGED_DEFAULT(1),
    FADE_DEFAULT(2),
    POSTERIZED_DEFAULT(3),

    WHITE_EYES_DEFAULT(4),
    WHITE_EYES_FADE_AGED_DEFAULT(5),
    WHITE_EYES_FADE_DEFAULT(6),
    WHITE_EYES_POSTERIZED_DEFAULT(7),

    BLACK_EYES_DEFAULT(8),
    BLACK_EYES_FADE_AGED_DEFAULT(9),
    BLACK_EYES_FADE_DEFAULT(10),
    BLACK_EYES_POSTERIZED_DEFAULT(11),

    RED_EYES_DEFAULT(12),
    RED_EYES_FADE_AGED_DEFAULT(13),
    RED_EYES_FADE_DEFAULT(14),
    RED_EYES_POSTERIZED_DEFAULT(15);


    private static final LostVariant[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(LostVariant::getId))
            .toArray(LostVariant[]::new);

    private final int id;

    LostVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LostVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static final Map<LostVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LostVariant.class), (map) -> {
                // Default
                map.put(
                        LostVariant.DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/default/default.png")
                );
                map.put(
                        LostVariant.FADE_AGED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/default/fade_aged_default.png")
                );
                map.put(
                        LostVariant.FADE_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/default/fade_default.png")
                );
                map.put(
                        LostVariant.POSTERIZED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/default/posterized_default.png")
                );

                // White eyes
                map.put(
                        LostVariant.WHITE_EYES_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/white_eyes/default.png")
                );
                map.put(
                        LostVariant.WHITE_EYES_FADE_AGED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/white_eyes/fade_aged_default.png")
                );
                map.put(
                        LostVariant.WHITE_EYES_FADE_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/white_eyes/fade_default.png")
                );
                map.put(
                        LostVariant.WHITE_EYES_POSTERIZED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/white_eyes/posterized_default.png")
                );

                // Black eyes
                map.put(
                        LostVariant.BLACK_EYES_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/black_eyes/default.png")
                );
                map.put(
                        LostVariant.BLACK_EYES_FADE_AGED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/black_eyes/fade_aged_default.png")
                );
                map.put(
                        LostVariant.BLACK_EYES_FADE_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/black_eyes/fade_default.png")
                );
                map.put(
                        LostVariant.BLACK_EYES_POSTERIZED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/black_eyes/posterized_default.png")
                );

                // Red eyes
                map.put(
                        LostVariant.RED_EYES_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/red_eyes/default.png")
                );
                map.put(
                        LostVariant.RED_EYES_FADE_AGED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/red_eyes/fade_aged_default.png")
                );
                map.put(
                        LostVariant.RED_EYES_FADE_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/red_eyes/fade_default.png")
                );
                map.put(
                        LostVariant.RED_EYES_POSTERIZED_DEFAULT,
                        new ResourceLocation(InTheFogMod.MOD_ID, "textures/entity/red_eyes/posterized_default.png")
                );
            });
}
