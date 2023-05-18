package com.pugzarecute.structurecompass;

import com.pugzarecute.structurecompass.items.CompassItem;
import com.pugzarecute.structurecompass.items.PropertyGetterImpl;
import com.pugzarecute.structurecompass.items.Registry;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Structurecompass.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        Registry.ITEM.getEntries().forEach((entry)-> ItemModelsProperties.register(entry.get(), new ResourceLocation("angle"), new PropertyGetterImpl() ));
    }
    @SubscribeEvent
    public static void colorReg(ColorHandlerEvent.Item event){

           Registry.ITEM.getEntries().forEach(m -> event.getItemColors().register((stack,layer) ->{
               if(layer==1) return Integer.parseInt(Config.hues.get(((CompassItem)m.get()).getStructure()).get().substring(1),16);
               return 0xFFFFFF;
           },m.get()));
    }
}
