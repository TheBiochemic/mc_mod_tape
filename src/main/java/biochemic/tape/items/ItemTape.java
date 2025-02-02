package biochemic.tape.items;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.BlockTape;
import biochemic.tape.registry.BlockRegistry;
import biochemic.tape.registry.CreativeTabRegistry;
import biochemic.tape.registry.ItemRegistry;
import biochemic.tape.tileentity.TileEntityTape;
import biochemic.tape.util.TapeVariants;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTape extends Item {

    private TapeVariants tapeVariant;
    private String registryName;

    public ItemTape(TapeVariants variant, String registryName) {
        super();
        this.tapeVariant = variant;
        this.registryName = registryName;
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
    }

    public TapeVariants getVariant() {
        return this.tapeVariant;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        return false;
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
        if (!stack.isEmpty() && p.canPlayerEdit(xyz, facing, stack) && w.mayPlace(BlockRegistry.TAPE, xyz, false, facing, null)) {

            boolean placeBlock = true;
            IBlockState defaultState = BlockRegistry.TAPE.getDefaultState();

            if(block != BlockRegistry.TAPE) {
                // If this Block doesnt exist yet
                if(!this.placeBlockAt(stack, p, w, xyz, facing, hitX, hitY, hitZ, defaultState)) {
                    placeBlock = false;
                };

            }

            //Place down a new piece into the block
            if (placeBlock) {
                TileEntityTape entity = (TileEntityTape) w.getTileEntity(xyz);
                updateStateFromFacing(this.tapeVariant, hitX, hitY, hitZ, facing, entity);

                if (!BlockTape.clearUnsupportedFaces(w, xyz, state)) {
                    SoundType soundtype = BlockRegistry.TAPE.getSoundType(defaultState, w, xyz, p);

                    w.playSound(p, xyz,
                        soundtype.getPlaceSound(),
                        SoundCategory.BLOCKS,
                        (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);

                    stack.damageItem(1, p);
                } else{
                    return EnumActionResult.FAIL;
                }
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    /**
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     * This is a copy from ItemBlock
     *
     * @param stack The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side The side the player (or machine) right-clicked on.
     */
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == BlockRegistry.TAPE)
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            BlockRegistry.TAPE.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }

    public static ItemTape getVariantFromFacing(BlockPos pos, float hitX, float hitY, float hitZ, EnumFacing facing, World world) {

        try {

            TileEntityTape entity = (TileEntityTape) world.getTileEntity(pos);
            byte index = 0;

            switch(facing) {
                case UP: {
                    if (hitX < 0.5 && hitZ < 0.5) {index = entity.bottom.getVerticalBackLeft();}
                    if (hitX < 0.5 && hitZ >= 0.5) {index = entity.bottom.getVerticalBackRight();}
                    if (hitX >= 0.5 && hitZ < 0.5) {index = entity.bottom.getVerticalFrontLeft();}
                    if (hitX >= 0.5 && hitZ >= 0.5) {index = entity.bottom.getVerticalFrontRight();}
                    break;
                }
    
                case DOWN: {
                    if (hitX < 0.5 && hitZ < 0.5) {index = entity.top.getVerticalBackLeft();}
                    if (hitX < 0.5 && hitZ >= 0.5) {index = entity.top.getVerticalBackRight();}
                    if (hitX >= 0.5 && hitZ < 0.5) {index = entity.top.getVerticalFrontLeft();}
                    if (hitX >= 0.5 && hitZ >= 0.5) {index = entity.top.getVerticalFrontRight();}
                    break;
                }
    
                case NORTH: {
                    if (hitX < 0.5 && hitY < 0.5) {index = entity.back.getHorizontalTopLeft();}
                    if (hitX < 0.5 && hitY >= 0.5) {index = entity.back.getHorizontalBottomLeft();}
                    if (hitX >= 0.5 && hitY < 0.5) {index = entity.back.getHorizontalTopRight();}
                    if (hitX >= 0.5 && hitY >= 0.5) {index = entity.back.getHorizontalBottomRight();}
                    break;
                }
    
                case SOUTH: {
                    if (hitX < 0.5 && hitY < 0.5) {index = entity.front.getHorizontalTopLeft();}
                    if (hitX < 0.5 && hitY >= 0.5) {index = entity.front.getHorizontalBottomLeft();}
                    if (hitX >= 0.5 && hitY < 0.5) {index = entity.front.getHorizontalTopRight();}
                    if (hitX >= 0.5 && hitY >= 0.5) {index = entity.front.getHorizontalBottomRight();}
                    break;
                }
    
                case WEST: {
                    if (hitZ < 0.5 && hitY < 0.5) {index = entity.left.getHorizontalTopLeft();}
                    if (hitZ < 0.5 && hitY >= 0.5) {index = entity.left.getHorizontalBottomLeft();}
                    if (hitZ >= 0.5 && hitY < 0.5) {index = entity.left.getHorizontalTopRight();}
                    if (hitZ >= 0.5 && hitY >= 0.5) {index = entity.left.getHorizontalBottomRight();}
                    break;
                }
    
                case EAST: {
                    if (hitZ < 0.5 && hitY < 0.5) {index = entity.right.getHorizontalTopLeft();}
                    if (hitZ < 0.5 && hitY >= 0.5) {index = entity.right.getHorizontalBottomLeft();}
                    if (hitZ >= 0.5 && hitY < 0.5) {index = entity.right.getHorizontalTopRight();}
                    if (hitZ >= 0.5 && hitY >= 0.5) {index = entity.right.getHorizontalBottomRight();}
                    break;
                }
            }

            for (ItemTape tape : ItemRegistry.TAPES.values()) {
                byte variant = tape.getVariant().num;
                if (variant == index) {
                    return tape;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return ItemRegistry.TAPES.get("tape_blank");
    }

    private static TileEntityTape updateStateFromFacing(TapeVariants variant, float hitX, float hitY, float hitZ, EnumFacing facing, TileEntityTape entity) {

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