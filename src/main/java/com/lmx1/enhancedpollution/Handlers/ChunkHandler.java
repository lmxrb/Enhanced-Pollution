package com.lmx1.enhancedpollution.Handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;

public class ChunkHandler {

    //Is this the more efficient way of storing the chunks?
    public static HashMap<Chunk, Double> chunkStorage = new HashMap<>();
    public static HashMap<Chunk, Double> pollutedChunkStorage = new HashMap<>();

    //Loads chunk pollution data and saves to chunkStorage
    public static void loadChunk(Chunk chunk, NBTTagCompound data){
        double pollution = data.getDouble("pollution");
        chunkStorage.put(chunk, pollution);
    }

    //Saves pollution saved in chunkStorage to chunk data
    public static NBTTagCompound saveChunk(Chunk chunk, NBTTagCompound data){
        double pollution = 0;
        if(chunkStorage.containsKey(chunk)) {
            pollution = chunkStorage.get(chunk);
        }
        data.setDouble("pollution", pollution);
        return data;
    }

    //Changes chunk pollution
    public static void changePollution(Chunk chunk, double newPollution){
        if(!chunkStorage.containsKey(chunk)){
            chunkStorage.put(chunk, newPollution);
        }
        else{
            double pollution = chunkStorage.get(chunk) + newPollution;
            newPollutedChunk(chunk, pollution);
            chunkStorage.replace(chunk, pollution);
        }
    }

    //Gets chunk pollution and if it isn't already set, creates with 0 pollution
    public static double getPollution(Chunk chunk){
        if(!chunkStorage.containsKey(chunk)){
            chunkStorage.put(chunk, 0D);
        }
        return chunkStorage.get(chunk);
    }

    //Delete pollution (used by machines), makes pollution 0 (doesn't remove it from storage)
    public static void deletePollution(Chunk chunk){
        chunkStorage.replace(chunk, 0D);
    }

    public static void unloadChunk(Chunk chunk){
        chunkStorage.remove(chunk);
        pollutedChunkStorage.remove(chunk);
    }

    //Tries to create new pollutedChunk (for use in pollution spreading)
    public static void newPollutedChunk(Chunk chunk, double pollution){
        //Uses this function just in case it isn't generated yet
        getPollution(chunk);
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
