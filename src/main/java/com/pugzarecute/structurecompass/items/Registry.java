package com.pugzarecute.structurecompass.items;

import com.pugzarecute.structurecompass.Structurecompass;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registry {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Structurecompass.MODID);

    public static HashMap<Structure<?>,RegistryObject<Item>> m = new HashMap<>();

    public static void regItems(){

    for(Map.Entry<RegistryKey<Structure<?>>, Structure<?>> structure: ForgeRegistries.STRUCTURE_FEATURES.getEntries()){
        Structure<?> s = structure.getValue().getStructure();
        m.put(s,ITEM.register(Objects.requireNonNull(s.getRegistryName()).getPath(),()->new CompassItem(s)));
    }
    }

}
