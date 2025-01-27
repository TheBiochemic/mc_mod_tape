package biochemic.tape.util;

public enum TapeVariants {
    NO_TAPE((byte)0, ""),
    BLANK((byte)1, "tape_blank"),
    PROTECTION((byte)2, "tape_protection"),
    DEFECTIVE((byte)3, "tape_defective"),
    HAZARD((byte)4, "tape_hazard"),
    RADIATION((byte)5, "tape_radiation"),
    GUIDANCE((byte)6, "tape_guidance"),
    SAFETY((byte)7, "tape_safety");

    public static int TAPE_VARIANTS_NUM = 7;
    public static TapeVariants[] LEGAL_TAPE_VARIANTS = {
        BLANK, PROTECTION, DEFECTIVE, DEFECTIVE, HAZARD, RADIATION, GUIDANCE, SAFETY
    };

    public byte num;
    public String registryName;

    TapeVariants(byte num, String registryName) {
        this.num = num;
        this.registryName = registryName;
    }
}
