package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AdminBusy extends DarthCraftAddon {

    private static final int MIN_WORD_LENGTH = 4;

    public AdminBusy(DarthCraft plugin) {
        super(plugin);
    }

    public void toggleBusyStatus(Player player) {
        PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

        info.setBusy(!info.isBusy());

        if (info.isBusy()) {
            player.sendMessage(ChatColor.AQUA + "Toggled busy status on");
            Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is busy");
        } else {
            player.sendMessage(ChatColor.AQUA + "Toggled busy status off");
            Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + " is no longer busy");
        }
    }

    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String[] words = event.getMessage().split(" ");
        for (String word : words) {
            if (word.length() > MIN_WORD_LENGTH) {
                Player match = Bukkit.getPlayer(word);
                if (match != null && Permissions.PermissionUtils.hasPermission(match, Permissions.Permission.ADMIN)) {
                    if (plugin.playerManager.getInfo(match).isBusy()) {
                        plugin.util.sendSyncMessage(event.getPlayer(), ChatColor.RED + match.getName() + " is busy right now, try again later");
                    }
                }
            }
        }
    }
}
