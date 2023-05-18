package com.pugzarecute.structurecompass;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Config {
    public static final ForgeConfigSpec CLIENT;

    public static final ForgeConfigSpec SERVER;
    public static Map<Structure<?>,ForgeConfigSpec.ConfigValue<String>> hues = new HashMap<>();
    public static Map<Structure<?>,ForgeConfigSpec.ConfigValue<String>> names = new HashMap<>();


    public static Map<Structure<?>,ForgeConfigSpec.IntValue> maxdist = new HashMap<>();

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder serverBuilder = new ForgeConfigSpec.Builder();
        clientConfigSetup(commonBuilder);
        serverConfigSetup(serverBuilder);
        CLIENT = commonBuilder.build();
        SERVER = serverBuilder.build();
    }

    private static void clientConfigSetup(ForgeConfigSpec.Builder builder) {
        for(Map.Entry<RegistryKey<Structure<?>>, Structure<?>> s : ForgeRegistries.STRUCTURE_FEATURES.getEntries()){
            hues.put(s.getValue(),builder.define("item."+ Objects.requireNonNull(s.getValue().getRegistryName()).getPath()+".hue","#FFFFFF"));
            names.put(s.getValue(), builder.define("item."+s.getValue().getRegistryName().getPath()+".name", WordUtils.capitalizeFully(s.getValue().getFeatureName().replace("_"," "))+" Compass"));
        }
    }
    private static void serverConfigSetup(ForgeConfigSpec.Builder builder) {
        for(Map.Entry<RegistryKey<Structure<?>>, Structure<?>> s : ForgeRegistries.STRUCTURE_FEATURES.getEntries()){
            maxdist.put(s.getValue(),builder.defineInRange("item."+s.getValue().getRegistryName().getPath()+".maxdistance",500,0,Integer.MAX_VALUE));
        }
    }
}
