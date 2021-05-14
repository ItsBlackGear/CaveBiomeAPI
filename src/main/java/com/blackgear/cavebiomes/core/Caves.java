package com.blackgear.cavebiomes.core;

import com.blackgear.cavebiomes.common.events.RemapEvent;
import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//<>

@Mod(value = Caves.MODID)
public class Caves {
    public static final String MODID = "cavebiomes";
    public static final Logger LOGGER = LogManager.getLogger();

    public Caves() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new RemapEvent());

        CaveBiomes.BIOMES.register(modEventBus);


        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CaveConfig.COMMON_CONFIG);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        CaveBiomeAPI.addDefaultCaves();
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }
}