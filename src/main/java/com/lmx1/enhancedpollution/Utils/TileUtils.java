package com.lmx1.enhancedpollution.Utils;

import com.lmx1.enhancedpollution.Integration.EnderIOPollution;
import cpw.mods.fml.common.Loader;
import crazypants.enderio.machine.AbstractMachineEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileUtils {

    //Returns amount of pollution that Tile produces
    public static double getPollution(TileEntity tile){
        if(tile instanceof TileEntityFurnace){
            return ((TileEntityFurnace) tile).isBurning() ? 2.5d : 0;
        }
        if(Loader.isModLoaded("EnderIO")){
            if(tile instanceof AbstractMachineEntity) {
                return EnderIOPollution.getPollution(tile);
            }
        }
        return 0;
    }

    /*
    public static void clearTileCache(){

    }*/
}
