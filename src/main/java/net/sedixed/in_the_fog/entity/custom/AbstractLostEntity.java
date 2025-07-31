package net.sedixed.in_the_fog.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sedixed.in_the_fog.entity.ai.*;
import net.sedixed.in_the_fog.entity.variant.LostVariant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractLostEntity extends PathfinderMob {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(AbstractLostEntity.class, EntityDataSerializers.INT);

    private boolean forcedVariant = false;

    protected AbstractLostEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        initializeEntity();
        setAggressive(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LostOpenDoorGoal(this, true));
        // Melee and ranged attack must be defined in subclasses
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 48.0F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new LostTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new ClimberNavigation(this, world);
    }

    @Override
    public boolean onClimbable() {
        BlockPos pos = this.blockPosition();
        return isClimbable(level().getBlockState(pos)) ||
                isClimbable(level().getBlockState(pos.above())) ||
                isClimbable(level().getBlockState(pos.below())) ||
                isClimbable(level().getBlockState(pos.below(2)));
    }


    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor pLevel,
            DifficultyInstance pDifficulty,
            MobSpawnType pReason,
            @Nullable SpawnGroupData pSpawnData,
            @Nullable CompoundTag pDataTag
    ) {
        if (!isForcedVariant()) {
            LostVariant variant = Util.getRandom(LostVariant.values(), random);
            setVariant(variant);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    @Override
    public void tick() {
        super.tick();
        double y = this.getLookControl().getWantedY();
        //System.out.println("Looking at height : " + y);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        // No loot for you bozo
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    protected abstract void initializeEntity();

    public LostVariant getVariant() {
        return LostVariant.byId(getTypeVariant() & 255);
    }

    private void setVariant(LostVariant variant) {
        entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public boolean isForcedVariant() {
        return forcedVariant;
    }

    public void setForcedVariant(boolean forcedVariant) {
        this.forcedVariant = forcedVariant;
    }

    private boolean isClimbable(BlockState state) {
        return state.is(Blocks.LADDER) || state.is(Blocks.VINE) || state.is(Blocks.SCAFFOLDING);
    }
}
