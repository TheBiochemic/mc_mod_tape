package biochemic.tape.util;

import java.util.ArrayList;

public enum TapeVariants {
    NO_TAPE((byte)0, ""),
    BLANK((byte)1, "tape_blank"),
    PROTECTION((byte)2, "tape_protection"),
    DEFECTIVE((byte)3, "tape_defective"),
    HAZARD((byte)4, "tape_hazard"),
    RADIATION((byte)5, "tape_radiation"),
    GUIDANCE((byte)6, "tape_guidance"),
    SAFETY((byte)7, "tape_safety"),
    LIGHT_GRAY((byte)8, "tape_light_gray"),
    GRAY((byte)9, "tape_gray"),
    BLACK((byte)10, "tape_black"),
    BROWN((byte)11, "tape_brown"),
    RED((byte)12, "tape_red"),
    ORANGE((byte)13, "tape_orange"),
    YELLOW((byte)14, "tape_yellow"),
    LIME((byte)15, "tape_lime"),
    GREEN((byte)16, "tape_green"),
    CYAN((byte)17, "tape_cyan"),
    LIGHT_BLUE((byte)18, "tape_light_blue"),
    BLUE((byte)19, "tape_blue"),
    PURPLE((byte)20, "tape_purple"),
    MAGENTA((byte)21, "tape_magenta"),
    PINK((byte)22, "tape_pink");

    public static int legalTapeVariantsNum() {
        return 1 + (Configuration.enablePatterns ? 6 : 0) + (Configuration.enableColors ? 15 : 0);
    }

    public static TapeVariants[] getLegalTapeVariants() {

        ArrayList<TapeVariants> variants = new ArrayList<TapeVariants>(22);

        variants.add(BLANK);

        if (Configuration.enablePatterns) {
            variants.add(PROTECTION);
            variants.add(DEFECTIVE);
            variants.add(HAZARD);
            variants.add(RADIATION);
            variants.add(GUIDANCE);
            variants.add(SAFETY);
        }

        if (Configuration.enableColors) {
            variants.add(LIGHT_GRAY);
            variants.add(GRAY);
            variants.add(BLACK);
            variants.add(BROWN);
            variants.add(RED);
            variants.add(ORANGE);
            variants.add(YELLOW);
            variants.add(LIME);
            variants.add(GREEN);
            variants.add(CYAN);
            variants.add(LIGHT_BLUE);
            variants.add(BLUE);
            variants.add(PURPLE);
            variants.add(MAGENTA);
            variants.add(PINK);
        }

        return  variants.toArray(new TapeVariants[variants.size()]);
    }

    public byte num;
    public String registryName;

    TapeVariants(byte num, String registryName) {
        this.num = num;
        this.registryName = registryName;
    }
}
