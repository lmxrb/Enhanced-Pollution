package com.lmx1.enhancedpollution.Handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;

public class ChunkHandler {

    public static HashMap<Chunk, Float> chunkStorage = new HashMap<>();
    public static HashMap<Chunk, Float> pollutedChunkStorage = new HashMap<>();

    public static void loadChunk(Chunk chunk, NBTTagCompound data){
        float pollution = data.getFloat("pollution");
        chunkStorage.put(chunk, pollution);
    }

    public static NBTTagCompound saveChunk(Chunk chunk, NBTTagCompound data){
        float pollution = 0;
        if(chunkStorage.containsKey(chunk)) {
            pollution = chunkStorage.get(chunk);
        }
        data.setFloat("pollution", pollution);
        return data;
    }

    public static void changePollution(Chunk chunk, float newPollution){
        float pollution = chunkStorage.get(chunk) + newPollution;
        newPollutedChunk(chunk, pollution);
        chunkStorage.replace(chunk, pollution);
    }

    public static float getPollution(Chunk chunk){
        if(!chunkStorage.containsKey(chunk)){
            chunkStorage.put(chunk, 0f);
        }
        return chunkStorage.get(chunk);
    }

    public static void deletePollution(Chunk chunk){
        chunkStorage.replace(chunk, 0f);
    }

    public static void unloadChunk(Chunk chunk){
        chunkStorage.remove(chunk);
        pollutedChunkStorage.remove(chunk);
    }

    public static void newPollutedChunk(Chunk chunk, float pollution){
        if(pollutedChunkStorage.containsKey(chunk)){
            if(pollution > 50) {
                pollutedChunkStorage.replace(chunk, pollution);
            }
            else{
                pollutedChunkStorage.remove(chunk);
            }
        }
        else if(pollution > 50 && chunk.isChunkLoaded){
            pollutedChunkStorage.put(chunk, pollution);
        }
    }
}
