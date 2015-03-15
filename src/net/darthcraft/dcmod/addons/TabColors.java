package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabColors extends DarthCraftAddon
{

    public TabColors(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
        if (DC_Utils.HOSTS.contains(player.getName()) || DC_Utils.HEADADMINS.contains(player.getName()))
        {
            if (info.isBusy())
            {
                player.setPlayerListName(ChatColor.GRAY + player.getName());
                player.sendMessage(ChatColor.DARK_RED + "Warning: You are still in BUSY Mode.");
            }
            else
            {
                player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            }
        }

        else if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
        {
            if (info.isBusy())
            {
                player.setPlayerListName(ChatColor.GRAY + player.getName());
                player.sendMessage(ChatColor.DARK_RED + "Warning: You are still in BUSY Mode.");
            }
            else
            {
                player.setPlayerListName(ChatColor.DARK_RED + player.getName());
            }
        }

        else if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.PREMIUM))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
        }

    }
}
