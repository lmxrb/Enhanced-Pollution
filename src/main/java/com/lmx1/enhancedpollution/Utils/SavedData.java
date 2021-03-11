package com.lmx1.enhancedpollution.Utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SavedData extends WorldSavedData{

    public World world;

    public SavedData(World w)
    {
        super("pollutionStorage");
        world = w;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {

    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {

    }

    public static SavedData getHandler(World world){
        return new SavedData(world);
    }

    public void addPollution(double pollution, int xCoord, int zCoord){
        //add Pollution
        //convert coords to chunks
        //world.mapStorage.setData();

    }
}
