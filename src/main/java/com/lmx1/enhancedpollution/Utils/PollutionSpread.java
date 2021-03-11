package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PollutionSpread {

    private static HashMap<Chunk, Float> adjacentChunks;
    private static List<Chunk> toRemove = new ArrayList<>();
    private static float adjacentPollution = 0;
    private static float spread = 0;

    public static void spreadAll(World w, HashMap<Chunk, Float> chunkStorage){
        chunkStorage.forEach((c,p) -> processSpread(w, c,p));
    }

    private static void processSpread(World w, Chunk chunk, float pollution){
        adjacentChunks = Utils.getAdjacentPollutedChunks(w, chunk);
        spreadToNearby(chunk, pollution);
    }

    //Spreads pollution to one chunk
    private static void spread(Chunk chunk, Float p){
        float newPollution = (1 - p/ adjacentPollution) * spread;
        ChunkHandler.changePollution(chunk, newPollution);
    }

    //Spreads pollution to adjacent chunks
    private static void spreadToNearby(Chunk centerChunk, float pollution){
        //Refactor
        if(pollution < 50){ return; }
        adjacentChunks.forEach((c, p) -> calcPol(c, pollution, p));
        toRemove.forEach(c -> adjacentChunks.remove(c));
        spread = (float) 0.2 * adjacentChunks.size() * pollution - adjacentPollution / adjacentChunks.size() + 1;
        adjacentChunks.forEach(PollutionSpread::spread);
        ChunkHandler.changePollution(centerChunk, pollution - spread);
        adjacentPollution = 0;
    }

    //Calculates amount of pollution on adjacent chunks
    private static void calcPol(Chunk chunk, float main, float pollution){
        if(pollution < main){
            adjacentPollution += pollution;
        }
        else{
            toRemove.add(chunk);
        }
    }
}
