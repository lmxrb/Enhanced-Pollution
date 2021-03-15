package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import com.lmx1.enhancedpollution.Handlers.EventHandler;
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
