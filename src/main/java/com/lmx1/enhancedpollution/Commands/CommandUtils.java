package com.lmx1.enhancedpollution.Commands;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import com.lmx1.enhancedpollution.Handlers.EventHandler;
import com.lmx1.enhancedpollution.Utils.Coordinate;
import com.lmx1.enhancedpollution.Utils.Utils;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {

    public static boolean changePollution(World w, ChunkCoordinates coords, int range, double pollution, boolean type){
        //Make sure there's a thread for that world
        if(Utils.findThread(w) != null){
            if(range > 0 ) {
                List<Coordinate> coordinateList = new ArrayList<>();
                int chunkX = coords.posX >> 4;
                int chunkZ = coords.posZ >> 4;
                for (int x = -range; x <= range; x++) {
                    for (int z = -range; z <= range; z++) {
                        int newX = chunkX + x;
                        int newZ = chunkZ + z;
                        double distance = Math.sqrt(newX * newX + newZ * newZ);
                        if (distance <= range) {
                            coordinateList.add(new Coordinate(newX, newZ));
                        }
                    }
                }
                for (Coordinate coord : coordinateList) {
                    if(type) {
                        ChunkHandler.changePollution(w.getChunkFromChunkCoords(coord.getX(), coord.getZ()), pollution);
                    }
                    else{
                        ChunkHandler.addPollution(w.getChunkFromChunkCoords(coord.getX(), coord.getZ()), pollution);
                    }
                }
                return coordinateList.size() > 0;
            }
            else{
                if(type) {
                    ChunkHandler.changePollution(w.getChunkFromBlockCoords(coords.posX, coords.posZ), pollution);
                }
                else{
                    ChunkHandler.addPollution(w.getChunkFromBlockCoords(coords.posX, coords.posZ), pollution);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean setRGB(int r, int g, int b){
        EventHandler.fog.set(0, (float) r);
        EventHandler.fog.set(1, (float) g);
        EventHandler.fog.set(2, (float) b);
        return true;
    }

}
