package biochemic.tape.util;
import net.minecraftforge.common.property.IUnlistedProperty;


public class UnlistedByte implements IUnlistedProperty<Byte> {
    private final String name;

	public UnlistedByte(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(Byte value) {
		return true;
	}

	@Override
	public Class<Byte> getType() {
		return Byte.class;
	}

	@Override
	public String valueToString(Byte value) {
		return value == null ? "none" : value.toString();
	}
}
