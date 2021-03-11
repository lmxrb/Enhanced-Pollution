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
    private static final List<Chunk> toRemove = new ArrayList<>();
    private static double adjacentPollution = 0;
    private static double spread = 0;

    public static void spreadAll(World w, HashMap<Chunk, Double> storage){
        try{
            storage.forEach((c, p) -> processSpread(w, c,p));
        }
        catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    private static void processSpread(World w, Chunk chunk, double pollution){
        adjacentChunks = Utils.getAdjacentPollutedChunks(w, chunk);
        spreadToNearby(chunk, pollution);
    }

    //Spreads pollution to one chunk
    private static void spread(Chunk chunk, double p){
        double newPollution = Math.round(adjacentPollution > 0 ? p / adjacentPollution * spread : spread / adjacentChunks.size());
        ChunkHandler.changePollution(chunk, newPollution);
    }

    //Spreads pollution to adjacent chunks
    private static void spreadToNearby(Chunk centerChunk, double pollution){
        //Refactor
        if(pollution < 50){ return; }
        adjacentChunks.forEach((c, p) -> calcPol(c, pollution, p));
        toRemove.forEach(c -> adjacentChunks.remove(c));
        double adjacentAverage = adjacentPollution > 0 ? adjacentPollution / adjacentChunks.size() : 0;
        spread = 0.1 * adjacentChunks.size() * pollution - adjacentAverage;
        adjacentChunks.forEach(PollutionSpread::spread);
        ChunkHandler.changePollution(centerChunk, Math.round(-spread));
        adjacentPollution = 0;
    }

    //Calculates amount of pollution on adjacent chunks
    private static void calcPol(Chunk chunk, double main, double pollution){
        if(pollution < main){
            adjacentPollution += pollution;
        }
        else{
            toRemove.add(chunk);
        }
    }
}
