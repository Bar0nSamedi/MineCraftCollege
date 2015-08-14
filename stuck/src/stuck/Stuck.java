/***
 * stolen from Polen
 * Kappa
***/
package stuck;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Stuck extends JavaPlugin {
  public boolean onCommand(CommandSender sender, Command command, 
                           String commandLabel, String[] args) {
    if (commandLabel.equalsIgnoreCase("Stuck")) {
      if(args.length == 1) {
          @SuppressWarnings("deprecation")
        Player player = getServer().getPlayer(args[0]);
        if (player != null) { return stuck(player, Material.STONE); }
      } else if(args.length == 2) {
          @SuppressWarnings("deprecation")
          Player player = getServer().getPlayer(args[0]);
          Material mat = Material.matchMaterial(args[1]);
          if (player != null && mat != null) {
              //sender.sendMessage("Caching player "+args[0]+" with "+mat.name());
              return stuck(player, mat);
          }
      } else {
        sender.sendMessage("Usage: /stuck playerName [materialName]");
      }
    }
    return false;
  }

  public boolean stuck(Player player, Material material) {
    World world = player.getWorld();
    Location loc = player.getLocation();
    int playerX = (int) loc.getX(); 
    int playerY = (int) loc.getY(); 
    int playerZ = (int) loc.getZ();
    loc.setX(playerX + 0.5); loc.setY(playerY); loc.setZ(playerZ + 0.5);
    player.teleport(loc);

    int[][] offsets = {
     //x,  y,  z
      {0,  -1, 0},
      {0,  2,  0},
      {1,  0,  0},
      {1,  1,  0},
      {-1, 0,  0},
      {-1, 1,  0},
      {0,  0,  1},
      {0,  1,  1},
      {0,  0, -1},
      {0,  1, -1},
    };

    for(int i = 0; i < offsets.length; i++) {
      int x = offsets[i][0]; 
      int y = offsets[i][1]; 
      int z = offsets[i][2];
      Block b = world.getBlockAt(x + playerX, y + playerY, z + playerZ);
        b.setType(material);

    }
    return true;
  }
}
