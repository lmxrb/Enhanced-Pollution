package com.lmx1.enhancedpollution.Integration;

import crazypants.enderio.machine.alloy.TileAlloySmelter;
import crazypants.enderio.machine.crusher.TileCrusher;
import crazypants.enderio.machine.generator.combustion.TileCombustionGenerator;
import crazypants.enderio.machine.generator.stirling.TileEntityStirlingGenerator;
import crazypants.enderio.machine.slicensplice.TileSliceAndSplice;
import net.minecraft.tileentity.TileEntity;

public class EnderIOPollution {

    public static double getPollution(TileEntity tile){
        if(tile instanceof TileCrusher){
            return ((TileCrusher) tile).isActive() ? 7.5d : 0;
        }
        if(tile instanceof TileAlloySmelter){
            return ((TileAlloySmelter) tile).isActive() ? 20d : 0;
        }
        if(tile instanceof TileSliceAndSplice){
            return((TileSliceAndSplice) tile).isActive() ? 12.5d : 0;
        }
        if(tile instanceof TileCombustionGenerator){
            return ((TileCombustionGenerator) tile).isActive() ? 60d : 0;
        }
        if(tile instanceof TileEntityStirlingGenerator){
            return ((TileEntityStirlingGenerator) tile).isActive() ? 50d : 0;
        }
        return 0;
    }
}
