package com.pugzarecute.structurecompass.items;

import com.pugzarecute.structurecompass.Config;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompassItem extends Item {
    final Structure<?> structure_needed;

    public CompassItem(Structure<?> structure) {
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TOOLS));
        this.structure_needed = structure;
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if(!world.isClientSide){


            BlockPos structure = ((ServerWorld)entity.getCommandSenderWorld()).findNearestMapFeature(structure_needed, entity.blockPosition(), Config.maxdist.get(structure_needed).get(),false);


            if(structure!=null && Math.sqrt(entity.distanceToSqr(structure.getX(),entity.getY(),structure.getZ())) <= Config.maxdist.get(structure_needed).get()){
                stack.getOrCreateTag().putInt("compass_x",structure.getX());
                stack.getOrCreateTag().putInt("compass_y", (int) entity.getY());
                stack.getOrCreateTag().putInt("compass_z",structure.getZ());
                stack.getOrCreateTag().putBoolean("compass_found",true);
            }else{
                onFail(stack);
            }
        }
    }

    public Structure<?> getStructure() {
        return structure_needed;
    }

    private void onFail(ItemStack stack){
        stack.getOrCreateTag().putBoolean("compass_found",false);
        stack.getOrCreateTag().putInt("compass_x",0);
        stack.getOrCreateTag().putInt("compass_y",0);
        stack.getOrCreateTag().putInt("compass_z",0);
    }
}
