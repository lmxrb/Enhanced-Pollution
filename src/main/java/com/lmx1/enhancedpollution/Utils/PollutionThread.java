package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class PollutionThread extends Thread {

    public World world;
    public SavedData handler;
    public int t = 0;

    public PollutionThread(World w)
    {
        super();
        this.world = w;
        this.setDaemon(true);
        this.setPriority(3);
        //regularFog(world.getWorldTime());
        handler = SavedData.getHandler(world);
        //UPDATE INFO
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            while(!Minecraft.getMinecraft().isGamePaused()) {
                //System.gc();
                try {
                    //Sleep every 2.5 seconds (Will be configurable and increased)
                    sleep(2500);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                processTiles();
                t++;
                if(t == 2 && !ChunkHandler.chunkStorage.isEmpty()) {
                    t = 0;
                    PollutionSpread.spreadAll(world, ChunkHandler.pollutedChunkStorage);
                }
            }
        }
        interrupt();
    }

    //Function that checks for polluting tiles (and changes pollution accordingly)
    private void processTiles(){
        for(Object obj : world.loadedTileEntityList){
            TileEntity tile = (TileEntity) obj;
            double pollution = TileUtils.getPollution(tile);
            if(pollution >= 0) {
                Chunk tileChunk = tile.getWorldObj().getChunkFromBlockCoords(tile.xCoord, tile.zCoord);
                ChunkHandler.changePollution(tileChunk, pollution);
            }
        }
    }

    //TODO: Add pollution based fog
    /*void processFog(float r, float g, float b, float d){
        List<Float> fog = new ArrayList<Float>();
        fog.add(r);
        fog.add(g);
        fog.add(b);
        fog.add(d);
        MyEventHandler.fog = fog;
    }

    void regularFog(long l){
        if (l < 13000) {
            processFog(181, 208, 255, 0.01f);
        } else {
            processFog(75, 89, 110, 0.001f);
        }
    }*/

}
