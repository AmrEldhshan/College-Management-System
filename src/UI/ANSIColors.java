package UI;

public class ANSIColors {
    // Reset
    public static final String RESET = "\u001B[0m";
    // Regular Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bold Colors
    public static final String BLACK_BOLD = "\u001B[30;1m";
    public static final String RED_BOLD = "\u001B[31;1m";
    public static final String GREEN_BOLD = "\u001B[32;1m";
    public static final String YELLOW_BOLD = "\u001B[33;1m";
    public static final String BLUE_BOLD = "\u001B[34;1m";
    public static final String PURPLE_BOLD = "\u001B[35;1m";
    public static final String CYAN_BOLD = "\u001B[36;1m";
    public static final String WHITE_BOLD = "\u001B[37;1m";

    // Background Colors
    public static final String BLACK_BG = "\u001B[40m";
    public static final String RED_BG = "\u001B[41m";
    public static final String GREEN_BG = "\u001B[42m";
    public static final String YELLOW_BG = "\u001B[43m";
    public static final String BLUE_BG = "\u001B[44m";
    public static final String PURPLE_BG = "\u001B[45m";
    public static final String CYAN_BG = "\u001B[46m";
    public static final String WHITE_BG = "\u001B[47m";

    // Bright Colors
    public static final String BLACK_BRIGHT = "\u001B[90m";
    public static final String RED_BRIGHT = "\u001B[91m";
    public static final String GREEN_BRIGHT = "\u001B[92m";
    public static final String YELLOW_BRIGHT = "\u001B[93m";
    public static final String BLUE_BRIGHT = "\u001B[94m";
    public static final String PURPLE_BRIGHT = "\u001B[95m";
    public static final String CYAN_BRIGHT = "\u001B[96m";
    public static final String WHITE_BRIGHT = "\u001B[97m";

    // Bright Background Colors
    public static final String BLACK_BG_BRIGHT = "\u001B[100m";
    public static final String RED_BG_BRIGHT = "\u001B[101m";
    public static final String GREEN_BG_BRIGHT = "\u001B[102m";
    public static final String YELLOW_BG_BRIGHT = "\u001B[103m";
    public static final String BLUE_BG_BRIGHT = "\u001B[104m";
    public static final String PURPLE_BG_BRIGHT = "\u001B[105m";
    public static final String CYAN_BG_BRIGHT = "\u001B[106m";
    public static final String WHITE_BG_BRIGHT = "\u001B[107m";

    // Formatting
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String REVERSED = "\u001B[7m";


    public static String getFCAIArt() {
        return BLUE_BOLD +
                "    _______________    ____\n" +
                "   / ____/ ____/   |  /  _/\n" +
                "  / /_  / /   / /| |  / /  \n" +
                " / __/ / /___/ ___ |_/ /   \n" +
                "/_/    \\____/_/  |_/___/   \n" +
                RESET;
    }
}
