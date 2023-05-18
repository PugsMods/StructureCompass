package com.pugzarecute.structurecompass;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pugzarecute.structurecompass.items.CompassItem;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.pugzarecute.structurecompass.items.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import pers.solid.brrp.v1.api.LanguageProvider;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.forge.RRPEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Mod("structurecompass")
@Mod.EventBusSubscriber(modid = Structurecompass.MODID)
public class Structurecompass {

    public static final RuntimeResourcePack PACK  = RuntimeResourcePack.create(new ResourceLocation(Structurecompass.MODID,"assets"));
    public static final RuntimeResourcePack DATA  = RuntimeResourcePack.create(new ResourceLocation(Structurecompass.MODID,"data"));

    public static LanguageProvider LANG = LanguageProvider.create();
    public static final String MODID = "structurecompass";
    public Structurecompass() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        FMLJavaModLoadingContext.get().getModEventBus().register(ModEventBusSubscriber.class);


        Registry.regItems();
        Registry.ITEM.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT, "structurecompass_client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER, "structurecompass_server.toml");


    }
    @SubscribeEvent
    public void onBRRPEvent(RRPEvent.AfterVanilla event) {

        if(event.resourceType == ResourcePackType.CLIENT_RESOURCES){
            PACK.clearResources();
            for (Structure<?> s : ForgeRegistries.STRUCTURE_FEATURES.getValues()){
                if(!Objects.isNull(s.getRegistryName())) {
                    String BASE = "{\n" +
                            "  \"parent\": \"structurecompass:item/generated\",\n" +
                            "  \"textures\": {\n" +
                            "    \"layer0\": \"structurecompass:item/compass_base\",\n" +
                            "    \"layer1\": \"structurecompass:item/dial_16\"\n" +
                            "  },\n" +
                            "  \"overrides\": [\n" +
                            "    { \"predicate\": { \"angle\": 0.000000 }, \"model\": \"structurecompass:item/dial_16\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.015625 }, \"model\": \"structurecompass:item/dial_17\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.046875 }, \"model\": \"structurecompass:item/dial_18\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.078125 }, \"model\": \"structurecompass:item/dial_19\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.109375 }, \"model\": \"structurecompass:item/dial_20\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.140625 }, \"model\": \"structurecompass:item/dial_21\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.171875 }, \"model\": \"structurecompass:item/dial_22\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.203125 }, \"model\": \"structurecompass:item/dial_23\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.234375 }, \"model\": \"structurecompass:item/dial_24\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.265625 }, \"model\": \"structurecompass:item/dial_25\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.296875 }, \"model\": \"structurecompass:item/dial_26\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.328125 }, \"model\": \"structurecompass:item/dial_27\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.359375 }, \"model\": \"structurecompass:item/dial_28\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.390625 }, \"model\": \"structurecompass:item/dial_29\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.421875 }, \"model\": \"structurecompass:item/dial_30\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.453125 }, \"model\": \"structurecompass:item/dial_31\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.484375 }, \"model\": \"structurecompass:item/dial_0\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.515625 }, \"model\": \"structurecompass:item/dial_1\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.546875 }, \"model\": \"structurecompass:item/dial_2\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.578125 }, \"model\": \"structurecompass:item/dial_3\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.609375 }, \"model\": \"structurecompass:item/dial_4\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.640625 }, \"model\": \"structurecompass:item/dial_5\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.671875 }, \"model\": \"structurecompass:item/dial_6\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.703125 }, \"model\": \"structurecompass:item/dial_7\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.734375 }, \"model\": \"structurecompass:item/dial_8\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.765625 }, \"model\": \"structurecompass:item/dial_9\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.796875 }, \"model\": \"structurecompass:item/dial_10\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.828125 }, \"model\": \"structurecompass:item/dial_11\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.859375 }, \"model\": \"structurecompass:item/dial_12\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.890625 }, \"model\": \"structurecompass:item/dial_13\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.921875 }, \"model\": \"structurecompass:item/dial_14\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.953125 }, \"model\": \"structurecompass:item/dial_15\" },\n" +
                            "    { \"predicate\": { \"angle\": 0.984375 }, \"model\": \"structurecompass:item/dial_16\" }\n" +
                            "  ]\n" +
                            "}\n";
                    PACK.addModel(new ResourceLocation(MODID, "item/" + s.getRegistryName().getPath()), BASE.getBytes());
                }
            }
            Registry.ITEM.getEntries().forEach(s -> LANG.add(s.get(),Config.names.get(((CompassItem)s.get()).getStructure()).get()));

            PACK.addLang(new ResourceLocation(MODID,"en_us"),LANG);
            event.addPack(PACK);
        }
    }
}
