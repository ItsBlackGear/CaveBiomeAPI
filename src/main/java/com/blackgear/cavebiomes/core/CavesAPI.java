package com.blackgear.cavebiomes.core;

import com.blackgear.cavebiomes.common.events.RemapEvent;
import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.blackgear.cavebiomes.core.api.GlobalBiomeFeatures;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import com.blackgear.cavebiomes.core.registries.CaveCarvers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//<>

/**
 * Hello, this API allows the developers to generate their own cave biomes in the world.
 * Special Thanks go to TelepathicGrunt, CorgiTaco and LudoCrypt who contributed with a lot of code and knowledge.
 *
 * @apiNote the API by itself it's in the {@link CaveBiomeAPI} class.
 */
@Mod(value = CavesAPI.MOD_ID)
public class CavesAPI {
    public static final String MOD_ID = "cavebiomeapi";

    public CavesAPI() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new RemapEvent());
        MinecraftForge.EVENT_BUS.register(new GlobalBiomeFeatures());

        CaveBiomes.BIOMES.register(modEventBus);
        CaveCarvers.CARVERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CaveConfig.common);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        CaveBiomeAPI.addDefaultCaves();
    }
}