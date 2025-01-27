package biochemic.tape.tileentity;

import javax.annotation.Nullable;

import biochemic.tape.util.Configuration;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTape extends TileEntity {

    public class TapeFaceProperty {
        private byte[] corners = {0, 0, 0, 0};

        public byte getHorizontalTopLeft() { return corners[0]; }
        public byte getHorizontalTopRight() { return corners[1]; }
        public byte getHorizontalBottomLeft() { return corners[2]; }
        public byte getHorizontalBottomRight() { return corners[3]; }

        public byte getVerticalFrontLeft() { return corners[0]; }
        public byte getVerticalFrontRight() { return corners[1]; }
        public byte getVerticalBackLeft() { return corners[2]; }
        public byte getVerticalBackRight() { return corners[3]; }

        public void setHorizontalTopLeft(byte value) { corners[0] = value; }
        public void setHorizontalTopRight(byte value) { corners[1] = value; }
        public void setHorizontalBottomLeft(byte value) { corners[2] = value; }
        public void setHorizontalBottomRight(byte value) { corners[3] = value; }

        public void setVerticalFrontLeft(byte value) { corners[0] = value; }
        public void setVerticalFrontRight(byte value) { corners[1] = value; }
        public void setVerticalBackLeft(byte value) { corners[2] = value; }
        public void setVerticalBackRight(byte value) { corners[3] = value; }

        public boolean needsSupport() {
            return 
                corners[0] > (byte)0 || 
                corners[1] > (byte)0 ||
                corners[2] > (byte)0 ||
                corners[3] > (byte)0;
        }

        public void clearFace() {
            corners = new byte[]{ 0, 0, 0, 0 };
        }

        public byte[] asArray() { return corners; }

        public TapeFaceProperty(NBTTagCompound nbt, String name) {
            this.corners = new byte[]{ 
                nbt.getByte(name+"0"),
                nbt.getByte(name+"1"),
                nbt.getByte(name+"2"),
                nbt.getByte(name+"3")
            };
        }

        public TapeFaceProperty() {
            this.corners = new byte[]{ 0, 0, 0, 0 };
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared()
    {
        return Configuration.renderTileEntityDistance;
    }

    public TapeFaceProperty top = new TapeFaceProperty();
    public TapeFaceProperty bottom = new TapeFaceProperty();
    public TapeFaceProperty left = new TapeFaceProperty();
    public TapeFaceProperty right = new TapeFaceProperty();
    public TapeFaceProperty front = new TapeFaceProperty();
    public TapeFaceProperty back = new TapeFaceProperty();

    private void writeFaceToNBT(NBTTagCompound nbt, TapeFaceProperty face, String name) {
        byte[] arrayFace = face.asArray();
        nbt.setByte(name+"0", arrayFace[0]);
        nbt.setByte(name+"1", arrayFace[1]);
        nbt.setByte(name+"2", arrayFace[2]);
        nbt.setByte(name+"3", arrayFace[3]);
    }

    @Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

    @Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

    @Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

    @Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.writeFaceToNBT(compound, this.top, "top");
        this.writeFaceToNBT(compound, this.bottom, "bottom");
        this.writeFaceToNBT(compound, this.left, "left");
        this.writeFaceToNBT(compound, this.right, "right");
        this.writeFaceToNBT(compound, this.front, "front");
        this.writeFaceToNBT(compound, this.back, "back");
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.top = new TapeFaceProperty(compound, "top");
        this.bottom = new TapeFaceProperty(compound, "bottom");
        this.left = new TapeFaceProperty(compound, "left");
        this.right = new TapeFaceProperty(compound, "right");
        this.front = new TapeFaceProperty(compound, "front");
        this.back = new TapeFaceProperty(compound, "back");
    }

}