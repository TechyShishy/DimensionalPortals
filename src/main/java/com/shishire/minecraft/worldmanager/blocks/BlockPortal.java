package com.shishire.minecraft.worldmanager.blocks;

import com.shishire.minecraft.worldmanager.WorldManager;
import com.shishire.minecraft.worldmanager.tileentity.TileEntityPortal;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortal extends net.minecraft.block.BlockPortal implements ITileEntityProvider
{

	public BlockPortal()
	{
		super();
		this.setLightLevel(0.75F);
		this.setRegistryName("portal");
		this.setCreativeTab(CreativeTabs.MISC);
		this.setUnlocalizedName(WorldManager.MOD_ID + ".portal");
		this.setHardness(-1.0F);
		this.setSoundType(SoundType.GLASS);
	}
	
    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
        {
        	TileEntity tileEntityIn = worldIn.getTileEntity(pos);
			if (tileEntityIn instanceof TileEntityPortal)
			{
				TileEntityPortal tileEntityPortal = (TileEntityPortal) tileEntityIn;
				entityIn.changeDimension(tileEntityPortal.getDimension());
				WorldManager.LOG.info("Moving " + entityIn.getName() + " to dimension " + tileEntityPortal.getDimension());

			}
			else
			{
				WorldManager.LOG.error(entityIn.getName() + " interacted with " + state.getBlock().getUnlocalizedName()
					+ " but " + tileEntityIn.getClass().toString() + " is not " + TileEntityPortal.class.toString());
			}
            //entityIn.setPortal(pos);
        	//WorldManager.LOG.info("No portal for you! Muahaha");
        }
    }
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntityPortal tileEntityPortal = new TileEntityPortal();
		tileEntityPortal.setDimension(-1);

		return tileEntityPortal;
	}
}
