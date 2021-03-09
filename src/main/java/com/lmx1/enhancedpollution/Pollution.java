package com.lmx1.enhancedpollution;


import com.lmx1.enhancedpollution.Items.PollutionMeasureItem;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Pollution.MODID, version = Pollution.VERSION)
public class Pollution
{
    public static final String MODID = "enhancedpollution";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        GameRegistry.registerItem(new PollutionMeasureItem(), "pollution_measurer");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new MyEventHandler());
    }

}
