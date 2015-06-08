/**
 * Stolen by Matt on 5/12/2015.
 */
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class HelloWorldPlugin extends JavaPlugin {
    public static Logger log = Logger.getLogger("Minecraft");

    public void onLoad()
    {
        log.info("[HelloWorldPlugin] Loaded....");
    }
    public void onEnable()
    {
        log.info("[HelloWorldPlugin] Starting up....");
    }
    public void onDisable()
    {
        log.info("[HelloWorldPlugin] Shutting Down....");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("hello"))
        {
            sender.sendMessage("Hello World!");
            return true;
        }
        return false;
    }
}
