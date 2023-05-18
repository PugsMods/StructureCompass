package com.pugzarecute.structurecompass.items;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PropertyGetterImpl implements IItemPropertyGetter {
    private final ItemModelsProperties.Angle wobble = new ItemModelsProperties.Angle();
    private final ItemModelsProperties.Angle wobbleRandom = new ItemModelsProperties.Angle();

    public float call(ItemStack item, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity) {
        Entity entity;
        if(livingEntity == null) entity = item.getEntityRepresentation();
        else entity = livingEntity;

        if(entity != null){
            if (world == null && entity.level instanceof ClientWorld) {
                world = (ClientWorld)entity.level;
            }

            BlockPos blockpos;

            if(item.getOrCreateTag().getBoolean("compass_found")) blockpos = new BlockPos(item.getOrCreateTag().getInt("compass_x"),item.getOrCreateTag().getInt("compass_y"),item.getOrCreateTag().getInt("compass_z"));
            else blockpos = null;


            if (blockpos != null && !(entity.position().distanceToSqr((double)blockpos.getX() + 0.5D, entity.position().y(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F)) {
                boolean isEntityPlayerAndLocalPlayer = livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).isLocalPlayer();

                double rot_offset = 0.0D;

                if (isEntityPlayerAndLocalPlayer) {
                    rot_offset = livingEntity.yRot;
                } else if (entity instanceof ItemFrameEntity) {
                    rot_offset = this.getFrameRotation((ItemFrameEntity)entity);
                } else if (entity instanceof ItemEntity) {
                    rot_offset = 180.0F - ((ItemEntity)entity).getSpin(0.5F) / ((float)Math.PI * 2F) * 360.0F;
                } else if (livingEntity != null) {
                    rot_offset = livingEntity.yBodyRot;
                }

                rot_offset = MathHelper.positiveModulo(rot_offset / 360.0D, 1.0D);

                double circ_angle = this.angleTO(Vector3d.atCenterOf(blockpos), entity) / (double)((float)Math.PI * 2F);

                double wobbleAngle;
                long time = world.getGameTime();
                if (isEntityPlayerAndLocalPlayer) {
                    if (this.wobble.shouldUpdate(time)) {
                        this.wobble.update(time, 0.5D - (rot_offset - 0.25D));
                    }

                    wobbleAngle = circ_angle + this.wobble.rotation;
                } else {
                    wobbleAngle = 0.5D - (rot_offset - 0.25D - circ_angle);
                }

                return MathHelper.positiveModulo((float)wobbleAngle, 1.0F);
            } else {
                long time = world.getGameTime();
                if (this.wobbleRandom.shouldUpdate(time)) {
                    this.wobbleRandom.update(time, Math.random());
                }

                double wobble = this.wobbleRandom.rotation + (double)((float)item.hashCode() / 2.14748365E9F);
                return MathHelper.positiveModulo((float)wobble, 1.0F);
            }
        }

        return 0F;
    }

    private double getFrameRotation(ItemFrameEntity frame) {
        Direction direction = frame.getDirection();
        int rot = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
        return MathHelper.wrapDegrees(180 + direction.get2DDataValue() * 90 + frame.getRotation() * 45 + rot);
    }

    private double angleTO(Vector3d vec3d, Entity entity) {
        return Math.atan2(vec3d.z() - entity.getZ(), vec3d.x() - entity.getX());
    }
    }

