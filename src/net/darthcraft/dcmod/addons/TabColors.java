package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
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
        if (DC_Utils.HOSTS.contains(player.getName()) || DC_Utils.HEADADMINS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
        }

        else if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
        {
            player.setPlayerListName(ChatColor.RED + player.getName());
        }

        else if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.PREMIUM))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
        }

    }
}
