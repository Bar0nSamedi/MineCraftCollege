/**
 * Created by Mattbook2 on 20.07.15.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
    Plugin plugin = this;
    public int alive = 0;
    public Location loc;
    public String eventType = "";
    public Random r = new Random();


    public main() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (main.this.alive == 0) {
                    main.this.loc = new Location((World) Bukkit.getWorlds().get(0), (double) main.this.r.nextInt(100), (double) main.this.r.nextInt(100), (double) main.this.r.nextInt(100));
                    main.this.loc = new Location(main.this.loc.getWorld(), (double) main.this.loc.getBlockX(), (double) main.this.loc.getWorld().getHighestBlockYAt(main.this.loc), (double) main.this.loc.getBlockZ());
                    main.this.startEvent();
                }

            }
        }, 20L, (long) (20.0D * this.getConfig().getDouble("TimeInterval")));
    }

    public void onDisable() {
        this.reloadConfig();
        this.saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can get kits!");
            return true;
        } else {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("event")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                    if (!p.hasPermission("Events.Use")) {
                        p.sendMessage(ChatColor.RED + "You don\'t have permission to do this!");
                        return true;
                    } else {
                        this.loc = this.getLocation(p);
                        this.startEvent();
                        return true;
                    }
                } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                    if (!p.hasPermission("Events.Use")) {
                        p.sendMessage(ChatColor.RED + "You don\'t have permission to do this!");
                        return true;
                    } else {
                        this.reloadConfig();
                        this.saveConfig();
                        p.sendMessage(ChatColor.GREEN + "Config file reloaded.");
                        return true;
                    }
                } else if (args.length == 1 && args[0].equalsIgnoreCase("tp")) {
                    if (!p.hasPermission("Events.TP")) {
                        p.sendMessage(ChatColor.RED + "You don\'t have permission to do this!");
                        return true;
                    } else {
                        p.teleport(this.loc);
                        return true;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Command: /event [start/tp/reload]");
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public Location getLocation(Player p) {
        this.loc = new Location(p.getWorld(), (double) (p.getLocation().getBlockX() + this.r.nextInt(1000) + 100), (double) p.getLocation().getBlockY(), (double) (p.getLocation().getBlockZ() + this.r.nextInt(1000) + 100));
        this.loc = new Location(this.loc.getWorld(), (double) this.loc.getBlockX(), (double) this.loc.getWorld().getHighestBlockYAt(this.loc), (double) this.loc.getBlockZ());
        return this.loc;
    }

    public void startEvent() {
        this.alive = this.r.nextInt(25) + 10;
        int tempAlive = this.alive;
        switch (this.r.nextInt(4)) {

            case 0:
                while (true) {
                    if (tempAlive == 0) {
                        break;
                    }

                    Zombie var5 = (Zombie) this.loc.getWorld().spawnEntity(this.loc, EntityType.ZOMBIE);
                    this.eventType = "Zombies!";
                    var5.setCustomName("Event " + this.eventType);
                    --tempAlive;
                }
            case 1:
                while (true) {
                    if (tempAlive == 0) {
                        break;
                    }

                    Spider var4 = (Spider) this.loc.getWorld().spawnEntity(this.loc, EntityType.SPIDER);
                    this.eventType = "SPINNEN!";
                    var4.setCustomName("Event " + this.eventType);
                    --tempAlive;
                }
            case 2:
                while (true) {
                    if (tempAlive == 0) {
                        break;
                    }

                    Skeleton var3 = (Skeleton) this.loc.getWorld().spawnEntity(this.loc, EntityType.SKELETON);
                    this.eventType = "Skeletors";
                    var3.setCustomName("Event " + this.eventType);
                    --tempAlive;
                }
            case 3:
                while (true) {
                    if (tempAlive != 0) {
                        break;
                    }
                        Wolf w = (Wolf) this.loc.getWorld().spawnEntity(this.loc, EntityType.WOLF);
                        w.setAngry(true);
                        this.eventType = "Wolfchen";
                        w.setCustomName("Event " + this.eventType);
                        --tempAlive;
                    }
                }


        Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Sprich dein Gebet. Eine Gruppe" + this.eventType + " wurde bei" + this.loc.getBlockX() + ", " + this.loc.getBlockY() + ", " + this.loc.getBlockZ() + "gesehen!");
        Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "In nomine Patris, et fillie, et spiritus sancti");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                main.this.removeMobs(main.this.loc);
            }
        }, (long) (20 * this.getConfig().getInt("TimeExpired")));
    }

    public void removeMobs(Location loc) {
        List near = loc.getWorld().getLivingEntities();
        Iterator var4 = near.iterator();

        while (var4.hasNext()) {
            LivingEntity e = (LivingEntity) var4.next();
            if (e.getCustomName() != null && e.getCustomName().equalsIgnoreCase("Event " + this.eventType)) {
                e.damage(10000.0D);
            }
        }

    }
}

