package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;

public class PollutionSpreading {

    //TODO: Fix pollution spreading

    private static HashMap<Chunk, Float> chunks = new HashMap<>();
    private static World world;
    private static float nearbyPollution = 0;
    private static float spread = 0;

    public static void spreadPollution(World w){
        world = w;
        ChunkHandler.pollutedChunkStorage.forEach(PollutionSpreading::nearby);
    }

    private static void spreading(Chunk chunk, Float p){
        ChunkHandler.changePollution(chunk, (1 - p/nearbyPollution) * spread);
    }

    //Spreads pollution to adjacent chunks
    private static void nearby(Chunk chunk, float pollution){
        //Refactor
        Chunk chunk1 = world.getChunkFromChunkCoords(chunk.xPosition - 1, chunk.zPosition);
        chunks.put(chunk1, ChunkHandler.getPollution(chunk1));
        chunk1 = world.getChunkFromChunkCoords(chunk.xPosition + 1, chunk.zPosition);
        chunks.put(chunk1, ChunkHandler.getPollution(chunk1));
        chunk1 = world.getChunkFromChunkCoords(chunk.xPosition, chunk.zPosition - 1);
        chunks.put(chunk1, ChunkHandler.getPollution(chunk1));
        chunk1 = world.getChunkFromChunkCoords(chunk.xPosition, chunk.zPosition + 1);
        chunks.put(chunk1, ChunkHandler.getPollution(chunk1));
        chunks.forEach((c, p) -> getPol(c, pollution, p));
        spread = (float) 0.2 * chunks.size()*pollution - nearbyPollution/chunks.size() + 1;
        chunks.forEach(PollutionSpreading::spreading);
        ChunkHandler.changePollution(chunk, pollution - spread);
        nearbyPollution = 0;
    }

    private static void getPol(Chunk chunk, float main, float pollution){
        if(pollution < main){
            nearbyPollution += pollution;
        }
        else{
            chunks.remove(chunk);
        }
    }
}
