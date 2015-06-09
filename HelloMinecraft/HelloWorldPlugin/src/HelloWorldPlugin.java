/**
 * Stolen by Matt on 5/12/2015.
 */
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class HelloWorldPlugin extends JavaPlugin implements Listener {
    public static Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onLoad() {
        log.info("[HelloWorldPlugin] Loading....");
    }

    @Override
    public void onEnable() {
        log.info("[HelloWorldPlugin] Starting up....");
        getServer().getPluginManager().registerEvents(this, this);
    }


    @Override
    public void onDisable() {
        log.info("[HelloWorldPlugin] Shutting Down....");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use that command!");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("hello")) {
            sender.sendMessage("Hello World!");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("killp")) {
            @SuppressWarnings("deprecation")
            Player target = (Bukkit.getServer().getPlayer(args[0]));

            if (args.length < 1) {
                sender.sendMessage("Please specify a Player to kill.");
                return true;
            } else if (target == null) {
                sender.sendMessage(args[0] + " is currently offline.");
                return true;
            } else {
                float explosionPower = 4F; //This is the explosion power - TNT explosions are 4F by default
                target.getWorld().createExplosion(target.getLocation(), explosionPower);
                target.setHealth(0.0D);
                return true;
            }


        } else if (cmd.getName().equalsIgnoreCase("up")) {

                //check if sender is a Player
                if (sender instanceof Player) {
                    Player me = (Player) sender;
                    //get a list of nearby entities
                    List<Entity> list = me.getNearbyEntities(30, 30, 30);

                    //Iterate over each item in this list
                    for (Entity target : list) {
                        //check if its a player (or not for instant fun)

                        if (target instanceof Player) {


                            Vector v = new Vector(0, 2, 0);
                            // Create a new Vector pointing upwards and set the targets velocity
                            target.setVelocity(v);
                        }


                    }
                }
            }

     else if (cmd.getName().equalsIgnoreCase("creep")) {

                Player player = (Player) sender;
                Creeper creeper = player.getWorld().spawn(player.getLocation(), Creeper.class);
                Bat bat = player.getWorld().spawn(player.getLocation(), Bat.class);
                bat.setPassenger(creeper);
                bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3));



        }



        return false;
    }
}




    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(p.getItemInHand().getType() == Material.SEEDS)
        {
            Block b = p.getTargetBlock((Set<Material>)null,200);
            Location loc = b.getLocation();
            p.getWorld().strikeLightning(loc);

        } else if (p.getItemInHand().getType() == Material.DIRT) {

            //Get the location we are loking at/interacting with
            Block b = p.getTargetBlock((Set<Material>) null, 200);
            BlockIterator blocks=new BlockIterator(Player,30);
            while (blocks.hasNext()) {
                Block b=blocks.next();


                    p.playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, null);
                    //if the block is not AIR then set to LAVA
                if (b.getType() !=  Material.AIR) {
                    b.setType(Material.LAVA);
                    p.playSound(b.getLocation(), Sound.EXPLODE, 1.0, 5.0);
                    break;
                }
            }



    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        p.sendMessage("OI KID, " + p.getName() + "! GET THE FUCK OFF MY LAWN");

    }

}
