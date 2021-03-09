package com.lmx1.enhancedpollution.Items;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class PollutionMeasureItem extends Item {

    public PollutionMeasureItem(){
        super();
        this.setUnlocalizedName("pollution_measurer");
        this.setTextureName("enhancedpollution:pollution_measurer");
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int dim, float hitX, float hitY, float hitZ){
        if(!world.isRemote) {
        	//Retrieve pollution value
            float pollution = 0.0;
            player.addChatMessage(new ChatComponentTranslation("Chunk's pollution: " + String.format("%.3f", pollution)));
        }
        return true;
    }
}