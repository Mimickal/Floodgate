package mimickal.mc.floodgate;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = FloodgateMod.MODID,
        name = FloodgateMod.NAME,
        version = FloodgateMod.VERSION,
        acceptedMinecraftVersions = "[1.10.2]"
)
public class FloodgateMod
{
    public static final String MODID = "floodgate";
    public static final String NAME = "Floodgate";
    public static final String VERSION = "1.10.2-1.0.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
