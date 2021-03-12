package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

public class PollutionSpread {

    private static HashMap<Chunk, Double> adjacentChunks;
    private static HashMap<Chunk, Double> heavyPollutedStorage;
    private static final List<Chunk> toRemove = new ArrayList<>();
    public static final List<Chunk> toIgnore = new ArrayList<>();
    private static double adjacentPollution = 0;
    private static double spread = 0;


    //Spread the pollution of every heavy polluted chunk
    public static void spreadAll(World w, HashMap<Chunk, Double> storage){
        heavyPollutedStorage = storage;
        try{
            heavyPollutedStorage.forEach((c, p) -> processSpread(w, c,p));
        }
        catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    //Starts trying to spread the chunk's pollution
    private static void processSpread(World w, Chunk chunk, double pollution){
        adjacentChunks = Utils.getAdjacentPollutedChunks(w, chunk);
        toIgnore.add(chunk);
        spreadToNearby(chunk, pollution);
    }

    //Spreads pollution to one chunk (of the adjacent ones)
    private static void spread(Chunk chunk, double p){
        double newPollution;
        if(heavyPollutedStorage.containsKey(chunk)){
            toIgnore.add(chunk);
            return;
        }
        if(adjacentPollution > 0){
            //(TODO): Make it so it adds based on how much pollution this chunk has compared to the other adjacent chunks
            //double adjacentAverage = adjacentPollution / adjacentChunks.size();
            newPollution = spread / adjacentChunks.size();
            //newPollution = adjacentAverage - p / adjacentPollution * spread;
            newPollution = newPollution > 0 ? newPollution : 0;
        }
        else{
            newPollution = spread / adjacentChunks.size();
        }
        ChunkHandler.changePollution(chunk, p + Math.round(newPollution));
    }

    //Spreads pollution to adjacent chunks
    private static void spreadToNearby(Chunk centerChunk, double pollution){
        //Refactor
        if(pollution < 12000){ return; }
        adjacentChunks.forEach((c, p) -> calcPol(c, pollution, p));
        toRemove.forEach(c -> adjacentChunks.remove(c));
        double adjacentAverage = adjacentPollution > 0 && adjacentPollution < pollution ? adjacentPollution / adjacentChunks.size() : 0;
        spread = Math.round(0.0125 * adjacentChunks.size() * (pollution-12000) - adjacentAverage);
        adjacentChunks.forEach(PollutionSpread::spread);
        System.out.println("spread: " + spread);
        ChunkHandler.changePollution(centerChunk, -spread);
        adjacentPollution = 0;
    }

    //Calculates amount of pollution on adjacent chunks
    private static void calcPol(Chunk chunk, double main, double pollution){
        if(pollution < main/4 && !toIgnore.contains(chunk)){
            adjacentPollution += pollution;
        }
        else{
            toRemove.add(chunk);
        }
    }
}
