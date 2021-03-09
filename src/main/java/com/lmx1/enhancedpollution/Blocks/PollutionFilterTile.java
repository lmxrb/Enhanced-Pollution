package com.lmx1.enhancedpollution.Blocks;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class PollutionFilterTile extends TileEntity  {

    private int ticks;
    public final float filteringCapacity = -5f;

    //add a should update because of blacklisted dimensions

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            ticks++;
            if(ticks >= 40) {
                ticks = 0;
                capturePollution();
                markDirty();
            }
        }
    }

    public void capturePollution(){
        Chunk chunk = this.worldObj.getChunkFromBlockCoords(this.xCoord,this.zCoord);
        if (ChunkHandler.getPollution(chunk) <= -filteringCapacity) {
            ChunkHandler.deletePollution(chunk);
        } else {
            ChunkHandler.changePollution(chunk, filteringCapacity);
        }
    }

}
