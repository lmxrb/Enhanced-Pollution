package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
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
}
