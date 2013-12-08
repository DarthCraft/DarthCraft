package net.darthcraft.dcmod;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.Bukkit;
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

    public void adminAction(String admin, String action, ChatColor color) {
        server.broadcastMessage(color + admin + " - " + action);
    }

    public void adminAction(CommandSender sender, String action) {
        adminAction(sender.getName(), action, ChatColor.RED);
    }

    public void msg(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.YELLOW + message);

    }

    public ChatColor getChatColor(Player player) {
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

    public void sendSyncMessage(final CommandSender sendTo, final String message) {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                sendTo.sendMessage(message);
            }
        });
    }
}
