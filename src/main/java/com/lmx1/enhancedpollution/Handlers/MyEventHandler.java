package com.lmx1.enhancedpollution.Handlers;

import com.lmx1.enhancedpollution.Utils.PollutionThread;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;
import java.util.List;

public class MyEventHandler {

    public static List<Thread> threads = new ArrayList<Thread>();
    public static List<Float> fog = new ArrayList<Float>();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogColor(EntityViewRenderEvent.FogColors event)
    {
        if(!Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness))
        {
            event.red = fog.get(0)/255f;
            event.green = fog.get(1)/255f;
            event.blue = fog.get(2)/255f;
        }
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        event.density = fog.get(3);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if(event.world.getClass() == WorldServer.class) {
            if(!threads.isEmpty()){
                return;
            }
            //Allow dimension blacklisting!
            //Careful with nether's lava!
            PollutionThread newThread = new PollutionThread(event.world);
            threads.add(newThread);
            newThread.start();
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event){
        if(!event.world.isRemote){
            for (Thread thread : threads) {
                //thread.interrupt();
                thread.stop();
            }
        }
    }


    @SubscribeEvent
    public void onChunkSaveEvent(ChunkDataEvent.Save event)
    {
        event.getData().setFloat("pollution", ChunkHandler.saveChunk(event.getChunk(), event.getData()).getFloat("pollution"));
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event)
    {
        ChunkHandler.loadChunk(event.getChunk(), event.getData());
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkDataEvent.Unload event)
    {
        ChunkHandler.unloadChunk(event.getChunk());
    }

}
