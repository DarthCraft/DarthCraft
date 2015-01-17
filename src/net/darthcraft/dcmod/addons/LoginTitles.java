package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginTitles extends DarthCraftAddon
{

    public LoginTitles(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

        if (info.getLoginMessage() == null)
        {

            logger.debug(player + "Has a blank login message and as such no message has been set");

        }
        else
        {
            if (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.ADMIN) || Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.PREMIUM))
            {

                Bukkit.broadcastMessage(DC_Utils.colorize("&2&l" + player.getName() + " is " + info.getLoginMessage()));

                logger.debug(player + "Has been given the login message of: " + info.getLoginMessage());

            }
            else
            {
                logger.debug(player + "Has not been given any login title.");
            }
        }
    }
}
