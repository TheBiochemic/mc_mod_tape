package biochemic.tape.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTape extends Block {

    public static PropertyBool NORTH_WEST = PropertyBool.create("northwest");
    public static PropertyBool NORTH_EAST = PropertyBool.create("northeast");
    public static PropertyBool SOUTH_WEST = PropertyBool.create("southwest");
    public static PropertyBool SOUTH_EAST = PropertyBool.create("southeast");

    protected static final AxisAlignedBB TAPE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public BlockTape() {
        super(Material.CIRCUITS);
        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(NORTH_WEST, true)
                        .withProperty(NORTH_EAST, true)
                        .withProperty(SOUTH_WEST, true)
                        .withProperty(SOUTH_EAST, true));

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TAPE_AABB;
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

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());

        if(downState.isTopSolid())
            return true;

        if(downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID)
            return true;

        return false;
    }

    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(NORTH_WEST, (meta & 1) != 0)
                .withProperty(NORTH_EAST, (meta & 2) != 0)
                .withProperty(SOUTH_WEST, (meta & 4) != 0)
                .withProperty(SOUTH_EAST, (meta & 8) != 0);
    }

    public int getMetaFromState(IBlockState state) {

        int result = 0;

        if(state.getValue(NORTH_WEST)) result+=1;
        if(state.getValue(NORTH_EAST)) result+=2;
        if(state.getValue(SOUTH_WEST)) result+=4;
        if(state.getValue(SOUTH_EAST)) result+=8;

        return result;
    }

    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canPlaceBlockAt(worldIn, pos))
            worldIn.setBlockToAir(pos);
    }

}
