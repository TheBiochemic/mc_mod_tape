package biochemic.tape.items;

import biochemic.tape.blocks.BlockTape;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockTape extends ItemBlock {

    public ItemBlockTape(BlockTape block) {
        super(block);
        this.setMaxDamage(128);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer p, World w, BlockPos xyz, EnumHand h, EnumFacing f, float hx, float hy, float hz) {

        //If not on floor, cancel
        if(f != EnumFacing.UP) return EnumActionResult.FAIL;

        IBlockState state = w.getBlockState(xyz);
        Block block = state.getBlock();

        if(block instanceof BlockTape && block != this.block)
            return EnumActionResult.FAIL;

        //if block is the tape or a replaceable block, keep original position. Move to the side otherwise.
        if (!block.isReplaceable(w, xyz) && block != this.block) {
                xyz = xyz.offset(f);
                state = w.getBlockState(xyz);
                block = state.getBlock();
        }

        //calculate meta with position
        int meta = 0;
        if(hz < 0.5f) meta = 1;
        else meta = 4;

        if(hx > 0.5f) meta *= 2;

        ItemStack stack = p.getHeldItem(h);

        //Check if player can place the block
        if (!stack.isEmpty() && p.canPlayerEdit(xyz, f, stack) && w.mayPlace(this.block, xyz, false, f, null)) {

            IBlockState newState;

            //if this is a newly created block
            if(block != this.block) {
                newState = this.block.getStateForPlacement(w, xyz, f, hx, hy, hz, meta, p, h);
            } else { //if there is already this tape
                int oldMeta = this.block.getMetaFromState(state);

                if((oldMeta & meta) != 0) return EnumActionResult.FAIL;
                else newState = this.block.getStateForPlacement(w, xyz, f, hx, hy, hz, meta + oldMeta, p, h);
            }

            //place new Block
            if (placeBlockAt(stack, p, w, xyz, f, hx, hy, hz, newState)) {
                newState = w.getBlockState(xyz);
                SoundType soundtype = newState.getBlock().getSoundType(newState, w, xyz, p);

                w.playSound(p, xyz,
                        soundtype.getPlaceSound(),
                        SoundCategory.BLOCKS,
                        (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);

                stack.damageItem(1, p);
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
        //RandomAdditions.logger.log(Level.INFO, "hit at (" + hx + ", " + hy + "," + hz + "), resulting meta: " + meta);
        //return super.onItemUse(p, w, xyz, h, f, hx, hy, hz);
    }

    public boolean placeBlockAt(ItemStack st, EntityPlayer p, World w, BlockPos xyz, EnumFacing f, float hx, float hy, float hz, IBlockState newState) {
        if(f != EnumFacing.UP) return false;
        return super.placeBlockAt(st, p, w, xyz, f, hx, hy, hz, newState);
    }
}