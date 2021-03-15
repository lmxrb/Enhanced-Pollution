package com.lmx1.enhancedpollution.Blocks;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class PollutionFilterTile extends TileEntity  {

    private int ticks;
    //TODO: Add custom filters and passive/active filtering (fans)
    public final double filteringCapacity = -125d;

    //should update because of blacklisted dimensions

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote){
            ticks++;
            //Every 2 seconds captures pollution
            if(ticks >= 40) {
                ticks = 0;
                capturePollution();
                markDirty();
            }
        }
    }

    public void capturePollution(){
        Chunk chunk = this.worldObj.getChunkFromBlockCoords(this.xCoord,this.zCoord);
        //If filtering capacity is bigger than chunk's pollution sets the chunks pollution to 0
        //since it can't be negative
        if (ChunkHandler.getPollution(chunk) <= -filteringCapacity) {
            ChunkHandler.deletePollution(chunk);
        } else {
            ChunkHandler.addPollution(chunk, filteringCapacity);
        }
    }

}
