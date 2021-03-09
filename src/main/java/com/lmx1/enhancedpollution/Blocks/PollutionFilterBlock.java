package com.lmx1.enhancedpollution.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PollutionFilterBlock extends Block implements ITileEntityProvider {

    public PollutionFilterBlock() {
        super(Material.iron);
        this.setHardness(2.0f);
        this.setResistance(6.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.isBlockContainer = true;
        this.setBlockTextureName("enhancedpollution:pollution_filter");
        this.setBlockName("enhancedpollution_pollution_filter");
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new PollutionFilterTile();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int d) {
        super.breakBlock(world, x, y, z, block, d);
        world.removeTileEntity(x,y,z);
    }

    /*
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }*/
}