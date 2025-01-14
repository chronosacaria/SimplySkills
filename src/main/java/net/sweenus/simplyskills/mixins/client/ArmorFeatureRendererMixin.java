package net.sweenus.simplyskills.mixins.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.sweenus.simplyskills.registry.EffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {



    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    public void simplyskills$renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                         LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i,
                                         BipedEntityModel<LivingEntity> bipedEntityModel, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity){
            if (livingEntity.hasStatusEffect(EffectRegistry.STEALTH)) {
                ci.cancel();
            }
        }
    }

}
