package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import com.lmx1.enhancedpollution.Handlers.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    public static List<Chunk> getAdjacentChunks(World world, Chunk centerChunk){
        List<Chunk> chunkList = new ArrayList<>();
        chunkList.add(world.getChunkFromChunkCoords(centerChunk.xPosition - 1, centerChunk.zPosition));
        chunkList.add(world.getChunkFromChunkCoords(centerChunk.xPosition + 1, centerChunk.zPosition));
        chunkList.add(world.getChunkFromChunkCoords(centerChunk.xPosition, centerChunk.zPosition - 1));
        chunkList.add(world.getChunkFromChunkCoords(centerChunk.xPosition, centerChunk.zPosition + 1));
        return chunkList;
    }

    public static HashMap<Chunk, Double> getAdjacentPollutedChunks(World world, Chunk centerChunk){
        HashMap<Chunk, Double> map = new HashMap<>();
        for (Chunk chunk: getAdjacentChunks(world, centerChunk)) {
            map.put(chunk, ChunkHandler.getPollution(chunk));
        }
        return map;
    }

    public static double getEntityChunkPollution(Entity entity){
        //World world = entity.worldObj;
        //Needs to happen through a packet
        World world = ((PollutionThread) EventHandler.threads.iterator().next()).world;
        Chunk chunk = world.getChunkFromChunkCoords(entity.chunkCoordX, entity.chunkCoordZ);
        try {
            double pollution = ChunkHandler.chunkStorage.get(chunk);
            return pollution;
        }
        catch (NullPointerException e) {
            return 0;
        }
    }

    public static PollutionThread findThread(World w){
        for(Thread thread : EventHandler.threads){
            //Always true, but letting it be for now
            try{
                PollutionThread t = (PollutionThread) thread;
                if(t.world == w){
                    return t;
                }
            }
            catch(ClassCastException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
