package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public class Backup extends DarthCraftAddon
{
    
    public Backup(DarthCraft plugin)
    {
        super(plugin);
    }
    
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        if (plugin.mainConfig.getBoolean("offline"))
        {
            Player player = event.getPlayer();
            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
            
            if (!info.exists())
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Sorry, but DarthCraft is currently in an offline mode. You look to be a new player and as such are unable to be internally authenticated. Please try again later, or go to www.darthcraft.net and post on the forums for further support.");
                
            }
            if (!event.getHostname().equals(info.getLastIp()))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Sorry, but DarthCraft is currently in an offline mode. It appears that your last registered IP does not match your current IP. Please try again later.");
            }
            
            player.setPlayerListName(ChatColor.AQUA + player.getName());
            server.broadcastMessage(ChatColor.RED + "Warning: " + player.getName() + " has been offline authenticated. This means that while we have made every effort to ensure that validity of this player, they could be an imposter.");
            
            for (Player players : plugin.getServer().getOnlinePlayers())
            {
                if (Permissions.PermissionUtils.hasPermission(players, Permissions.Permission.ADMIN))
                {
                    players.sendMessage(ChatColor.WHITE + "<" + ChatColor.DARK_PURPLE + "ADMIN" + ChatColor.WHITE + "> " + ChatColor.RED + "DCMOD" + ChatColor.GRAY + ": " + ChatColor.AQUA + player + " has been authenticated as an offline player, and may be an imposter.");
                }
            }
            plugin.logger.info("<ADMIN> DCMOD" + ": " + player + " has been authenticated as an offline player, and may be an imposter.");
        }
        else
        {
            plugin.logger.info("DCMod backp system has not been enabled for this player.");
        }
    }
}
