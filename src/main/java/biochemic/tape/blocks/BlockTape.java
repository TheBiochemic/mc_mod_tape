package biochemic.tape.blocks;

import java.util.ArrayList;
import java.util.List;

import biochemic.tape.tileentity.TileEntityTape;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTape extends Block implements ITileEntityProvider {

    protected static final AxisAlignedBB TAPE_BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
    protected static final AxisAlignedBB TAPE_TOP_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB TAPE_RIGHT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB TAPE_LEFT_AABB = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB TAPE_FRONT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
    protected static final AxisAlignedBB TAPE_BACK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB TAPE_MULTI_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);

    public BlockTape() {
        super(Material.CIRCUITS);
        this.setSoundType(SoundType.CLOTH);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        TileEntity tileEntity = source.getTileEntity(pos);

        if (tileEntity instanceof TileEntityTape) {

            AxisAlignedBB box = NULL_AABB;

            TileEntityTape entityTape = (TileEntityTape) tileEntity;

            if (entityTape.bottom.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_BOTTOM_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (entityTape.top.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_TOP_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (entityTape.front.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_FRONT_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (entityTape.back.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_BACK_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (entityTape.left.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_LEFT_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (entityTape.right.needsSupport()) {
                if (box == NULL_AABB) { box = TAPE_RIGHT_AABB; } else { box = TAPE_MULTI_AABB; }
            }

            if (box == NULL_AABB) {
                return TAPE_MULTI_AABB;
            } else {
                return box;
            }
        }

        return TAPE_MULTI_AABB;
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }

    /*public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());

        if(downState.isSideSolid(worldIn, pos, EnumFacing.UP))
            return true;

        if(downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID)
            return true;

        return false;
    }*/

    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        TileEntityTape tileEntity = (TileEntityTape) worldIn.getTileEntity(pos);

        boolean bottomNeedsSupport = tileEntity.bottom.needsSupport();
        boolean topNeedsSupport = tileEntity.top.needsSupport();
        boolean leftNeedsSupport = tileEntity.left.needsSupport();
        boolean rightNeedsSupport = tileEntity.right.needsSupport();
        boolean frontNeedsSupport = tileEntity.front.needsSupport();
        boolean backNeedsSupport = tileEntity.back.needsSupport();

        //Check the different faces, if they're solid, and if not, clear them
        if(!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
            bottomNeedsSupport = false;
            tileEntity.bottom.clearFace();
        }

        if(!worldIn.isSideSolid(pos.up(), EnumFacing.DOWN)) {
            topNeedsSupport = false;
            tileEntity.top.clearFace();
        }

        if(!worldIn.isSideSolid(pos.south(), EnumFacing.NORTH)) {
            backNeedsSupport = false;
            tileEntity.back.clearFace();
        }

        if(!worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH)) {
            frontNeedsSupport = false;
            tileEntity.front.clearFace();
        }

        if(!worldIn.isSideSolid(pos.east(), EnumFacing.WEST)) {
            leftNeedsSupport = false;
            tileEntity.left.clearFace();
        }

        if(!worldIn.isSideSolid(pos.west(), EnumFacing.EAST)) {
            rightNeedsSupport = false;
            tileEntity.right.clearFace();
        }

        if(!bottomNeedsSupport && !topNeedsSupport && !leftNeedsSupport && !rightNeedsSupport && !frontNeedsSupport && !backNeedsSupport) {
            worldIn.setBlockToAir(pos);
        } else {
            tileEntity.markDirty();
            worldIn.markAndNotifyBlock(pos, null, state, state, 2);
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTape();
    }

}
