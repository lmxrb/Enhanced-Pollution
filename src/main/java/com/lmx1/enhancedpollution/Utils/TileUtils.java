package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Integration.IE.IEMultiBlockIntegration;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileUtils {

    public static float getPollution(TileEntity tile){
        if(tile instanceof TileEntityFurnace){
            return ((TileEntityFurnace) tile).isBurning() ? 50f : 0.0f;
        }
        if(tile.getClass().getName().startsWith("blusunrize.immersiveengineering.common")){
            return IEMultiBlockIntegration.getPollution(tile);
        }
        return 0;
    }

    public static void clearTileCache(){

    }
}
