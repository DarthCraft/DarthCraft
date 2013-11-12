package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.PlayerManager.PlayerInfo;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AdminChat extends DarthCraftAddon {

    public AdminChat(DarthCraft plugin) {
        super(plugin);
    }

    public void toggleAdminChat(Player player) {
        PlayerInfo info = plugin.playerManager.getInfo(player);

        info.setInAdminChat(!info.isInAdminChat());

        player.sendMessage(ChatColor.AQUA + "Toggled Admin Chat o" + (info.isInAdminChat() ? "n" : "ff") + ".");
    }

    public void sendAdminMessage(String name, String msg) {

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (PermissionUtils.hasPermission(player, Permission.ADMIN)) {
                player.sendMessage("<" + ChatColor.DARK_PURPLE + "ADMIN" + ChatColor.WHITE + "> "
                        + ChatColor.DARK_RED + name + ChatColor.WHITE + ": " + ChatColor.AQUA + msg);
            }
        }
        plugin.logger.info("MADMIN> " + name + ": " + msg);

    }

    // events
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.playerManager.getInfo(event.getPlayer()).isInAdminChat()) {
            sendAdminMessage(event.getPlayer().getName(), event.getMessage());
            event.setCancelled(true);
        }

    }
}