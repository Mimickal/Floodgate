package mimickal.mc.floodgate;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    private static final int RANGE_DEFAULT = 32;

    public static int RANGE = RANGE_DEFAULT;

    public static void load() {
        File configFile = new File("config/" + FloodgateMod.MOD_ID + ".cfg");
        Configuration config = new Configuration(configFile);

        config.load();

        RANGE = config.getInt(
                "range", FloodgateMod.MOD_ID,
                RANGE_DEFAULT, 0, Integer.MAX_VALUE,
                "The max number of blocks a floodgate can place a source block from itself.\r\n" +
                "This value should probably stay below 64,\r\n" +
                "but you're welcome to drown the entire universe."
        );

        config.save();
    }
}
