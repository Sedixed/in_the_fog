package net.sedixed.in_the_fog.entity.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.LivingEntity;

public class LostModel<T extends LivingEntity> extends HumanoidModel<T> {
    public LostModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(
                HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f),
                64,
                64
        );
    }
}
