package net.sedixed.in_the_fog.entity.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.sedixed.in_the_fog.entity.ai.ClimbLadderGoal;
import net.sedixed.in_the_fog.entity.ai.PathOrBreakBlockToReachTargetGoal;

public class LostMinerEntity extends AbstractLostEntity {
    public LostMinerEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new PathOrBreakBlockToReachTargetGoal(this));
        this.goalSelector.addGoal(2, new ClimbLadderGoal(this));
    }

    @Override
    protected void initializeEntity() {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        //pickaxe.enchant(Enchantments.BLOCK_EFFICIENCY, 3);
        pickaxe.getOrCreateTag().putBoolean("Unbreakable", true);
        this.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
        this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.TORCH));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
    }
}
