package net.sedixed.in_the_fog.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.entity.custom.*;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, InTheFogMod.MOD_ID);

    public static final RegistryObject<EntityType<LostMinerEntity>> LOST_MINER = ENTITY_TYPES.register(
            "lost_miner",
            () -> EntityType.Builder
                    .of(LostMinerEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_miner")
    );

    public static final RegistryObject<EntityType<LostFighterEntity>> LOST_FIGHTER = ENTITY_TYPES.register(
            "lost_fighter",
            () -> EntityType.Builder
                    .of(LostFighterEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_fighter")
    );

    public static final RegistryObject<EntityType<LostFishermanEntity>> LOST_FISHERMAN = ENTITY_TYPES.register(
            "lost_fisherman",
            () -> EntityType.Builder
                    .of(LostFishermanEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_fisherman")
    );

    public static final RegistryObject<EntityType<LostFarmerEntity>> LOST_FARMER = ENTITY_TYPES.register(
            "lost_farmer",
            () -> EntityType.Builder
                    .of(LostFarmerEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_farmer")
    );

    public static final RegistryObject<EntityType<LostEntity>> LOST = ENTITY_TYPES.register(
            "lost",
            () -> EntityType.Builder
                    .of(LostEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_farmer")
    );

    public static final RegistryObject<EntityType<LostBuilderEntity>> LOST_BUILDER = ENTITY_TYPES.register(
            "lost_builder",
            () -> EntityType.Builder
                    .of(LostBuilderEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_builder")
    );

    public static final RegistryObject<EntityType<LostAdventurerEntity>> LOST_ADVENTURER = ENTITY_TYPES.register(
            "lost_adventurer",
            () -> EntityType.Builder
                    .of(LostAdventurerEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("lost_adventurer")
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
