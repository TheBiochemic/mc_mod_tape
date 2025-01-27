package biochemic.tape.items;

import org.apache.logging.log4j.Level;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.BlockTape;
import biochemic.tape.registry.BlockRegistry;
import biochemic.tape.registry.CreativeTabRegistry;
import biochemic.tape.tileentity.TileEntityTape;
import biochemic.tape.util.TapeVariants;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockTape extends ItemBlock {

    private TapeVariants tapeVariant;
    private String registryName;

    public ItemBlockTape(TapeVariants variant, String registryName) {
        super(BlockRegistry.TAPE);
        this.tapeVariant = variant;
        this.registryName = registryName;
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return TapeMod.MODID + "." + this.registryName;
    }

    @Override
    public String getUnlocalizedName() {
        return TapeMod.MODID + "." + this.registryName;
    }

    @Override
    public CreativeTabs getCreativeTab(){
        return CreativeTabRegistry.DEFAULT;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer p, World w, BlockPos xyz, EnumHand h, EnumFacing facing, float hitX, float hitY, float hitZ) {

        IBlockState state = w.getBlockState(xyz);
        Block block = state.getBlock();

        //if block is the tape or a replaceable block, keep original position. Move to the side otherwise.
        if (!block.isReplaceable(w, xyz) && block != BlockRegistry.TAPE) {
                xyz = xyz.offset(facing);
                state = w.getBlockState(xyz);
                block = state.getBlock();
        }

        ItemStack stack = p.getHeldItem(h);

        //Check if player can place the block
        if (!stack.isEmpty() && p.canPlayerEdit(xyz, facing, stack) && w.mayPlace(this.block, xyz, false, facing, null)) {

            boolean placeBlock = true;
            IBlockState defaultState = BlockRegistry.TAPE.getDefaultState();

            if(block != BlockRegistry.TAPE) {
                // If this Block doesnt exist yet
                if(!super.placeBlockAt(stack, p, w, xyz, facing, hitX, hitY, hitZ, defaultState)) {
                    placeBlock = false;
                };

            }

            //Place down a new piece into the block
            if (placeBlock) {
                TileEntityTape entity = (TileEntityTape) w.getTileEntity(xyz);
                updateStateFromFacing(this.tapeVariant, hitX, hitY, hitZ, facing, entity);

                SoundType soundtype = BlockRegistry.TAPE.getSoundType(defaultState, w, xyz, p);

                    w.playSound(p, xyz,
                        soundtype.getPlaceSound(),
                        SoundCategory.BLOCKS,
                        (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);

                    stack.damageItem(1, p);

                    entity.markDirty();
                w.markAndNotifyBlock(xyz, null, state, state, 2);
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    private static TileEntityTape updateStateFromFacing(TapeVariants variant, float hitX, float hitY, float hitZ, EnumFacing facing, TileEntityTape entity) {

        TapeMod.logger.log(Level.INFO, "hit at (" + hitX + ", " + hitY + "," + hitZ + "),  facing " + facing);

        switch(facing) {
            case UP: {
                if (hitX < 0.5 && hitZ < 0.5) {entity.bottom.setVerticalBackLeft(variant.num);}
                if (hitX < 0.5 && hitZ >= 0.5) {entity.bottom.setVerticalBackRight(variant.num);}
                if (hitX >= 0.5 && hitZ < 0.5) {entity.bottom.setVerticalFrontLeft(variant.num);}
                if (hitX >= 0.5 && hitZ >= 0.5) {entity.bottom.setVerticalFrontRight(variant.num);}
                break;
            }

            case DOWN: {
                if (hitX < 0.5 && hitZ < 0.5) {entity.top.setVerticalBackLeft(variant.num);}
                if (hitX < 0.5 && hitZ >= 0.5) {entity.top.setVerticalBackRight(variant.num);}
                if (hitX >= 0.5 && hitZ < 0.5) {entity.top.setVerticalFrontLeft(variant.num);}
                if (hitX >= 0.5 && hitZ >= 0.5) {entity.top.setVerticalFrontRight(variant.num);}
                break;
            }

            case NORTH: {
                if (hitX < 0.5 && hitY < 0.5) {entity.back.setHorizontalTopLeft(variant.num);}
                if (hitX < 0.5 && hitY >= 0.5) {entity.back.setHorizontalBottomLeft(variant.num);}
                if (hitX >= 0.5 && hitY < 0.5) {entity.back.setHorizontalTopRight(variant.num);}
                if (hitX >= 0.5 && hitY >= 0.5) {entity.back.setHorizontalBottomRight(variant.num);}
                break;
            }

            case SOUTH: {
                if (hitX < 0.5 && hitY < 0.5) {entity.front.setHorizontalTopLeft(variant.num);}
                if (hitX < 0.5 && hitY >= 0.5) {entity.front.setHorizontalBottomLeft(variant.num);}
                if (hitX >= 0.5 && hitY < 0.5) {entity.front.setHorizontalTopRight(variant.num);}
                if (hitX >= 0.5 && hitY >= 0.5) {entity.front.setHorizontalBottomRight(variant.num);}
                break;
            }

            case WEST: {
                if (hitZ < 0.5 && hitY < 0.5) {entity.left.setHorizontalTopLeft(variant.num);}
                if (hitZ < 0.5 && hitY >= 0.5) {entity.left.setHorizontalBottomLeft(variant.num);}
                if (hitZ >= 0.5 && hitY < 0.5) {entity.left.setHorizontalTopRight(variant.num);}
                if (hitZ >= 0.5 && hitY >= 0.5) {entity.left.setHorizontalBottomRight(variant.num);}
                break;
            }

            case EAST: {
                if (hitZ < 0.5 && hitY < 0.5) {entity.right.setHorizontalTopLeft(variant.num);}
                if (hitZ < 0.5 && hitY >= 0.5) {entity.right.setHorizontalBottomLeft(variant.num);}
                if (hitZ >= 0.5 && hitY < 0.5) {entity.right.setHorizontalTopRight(variant.num);}
                if (hitZ >= 0.5 && hitY >= 0.5) {entity.right.setHorizontalBottomRight(variant.num);}
                break;
            }
        }

        return entity;
    }

}