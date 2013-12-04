package net.darthcraft.dcmod;

import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

    private final DarthCraft plugin;
    private final Server server;

    public Util(DarthCraft plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    /* --- DarthCraft Logging --- */
    /*
     public void debug(Object message) {
     if (!plugin.debugMode) {
     return;
     }

     if (message instanceof Exception || message instanceof Throwable) {
     plugin.logger.info("[DEBUG] " + ExceptionUtils.getFullStackTrace((Exception) message));
     } else {
     plugin.logger.info("[DEBUG] " + (String) message);
     }
     }
    
     public void info(String msg) {
     LoggerUtils.info(plugin, msg);
     }

     public void warning(String msg) {
     LoggerUtils.warning(plugin, msg);
     }

     public void severe(Object msg) {
     LoggerUtils.severe(plugin, msg);
     }
     */

    /* --- DarthCraft Utils --- */
    public void adminAction(String admin, String action, ChatColor color) {
        server.broadcastMessage(color + admin + " - " + action);
    }

    public void adminAction(CommandSender sender, String action) {
        adminAction(sender.getName(), action, ChatColor.RED);
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.YELLOW + message);

    }

    public ChatColor getColor(Player player) {
        if (PermissionUtils.hasPermission(player, Permission.HOST)) {
            return ChatColor.LIGHT_PURPLE;
        }
        
        if (PermissionUtils.hasPermission(player, Permission.ADMIN)) {
            return ChatColor.GOLD;
        }
        
        if (PermissionUtils.hasPermission(player, Permission.MEMBER)) {
            return ChatColor.WHITE;
        }
        
        return ChatColor.GRAY;
    }
}
