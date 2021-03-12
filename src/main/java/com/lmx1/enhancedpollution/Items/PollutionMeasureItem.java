package com.lmx1.enhancedpollution.Items;

import com.lmx1.enhancedpollution.Handlers.ChunkHandler;
import com.lmx1.enhancedpollution.Utils.PollutionSpread;
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
        //Run only on server side
        if(!world.isRemote) {
            //Gets amount of pollution and prints it to chat
            double pollution = ChunkHandler.getPollution(world.getChunkFromBlockCoords(x, z));
            player.addChatMessage(new ChatComponentTranslation("Chunk's pollution: " + pollution));
        }
        return true;
    }
}