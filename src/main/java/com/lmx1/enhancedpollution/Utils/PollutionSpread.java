package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Could possibly be static

public class PollutionSpread {

    private HashMap<Chunk, Float> chunks;
    private List<Chunk> toRemove = new ArrayList<>();
    private float adjacentPollution = 0;
    private float spread = 0;

    public PollutionSpread(World w, Chunk chunk, float pollution){
        chunks = Utils.getAdjacentPollutedChunks(w, chunk);
        spreadToNearby(chunk, pollution);
    }

    //Spreads pollution to one chunk
    private void spread(Chunk chunk, Float p){
        float newPollution = (1 - p/ adjacentPollution) * spread;
        ChunkHandler.changePollution(chunk, newPollution);
    }

    //Spreads pollution to adjacent chunks
    private void spreadToNearby(Chunk centerChunk, float pollution){
        //Refactor
        if(pollution < 50){ return; }
        chunks.forEach((c, p) -> calcPol(c, pollution, p));
        toRemove.forEach(c -> chunks.remove(c));
        spread = (float) 0.2 * chunks.size() * pollution - adjacentPollution / chunks.size() + 1;
        chunks.forEach(this::spread);
        ChunkHandler.changePollution(centerChunk, pollution - spread);
        adjacentPollution = 0;
    }

    //Calculates amount of pollution on adjacent chunks
    private void calcPol(Chunk chunk, float main, float pollution){
        if(pollution < main){
            adjacentPollution += pollution;
        }
        else{
            toRemove.add(chunk);
        }
    }
}
