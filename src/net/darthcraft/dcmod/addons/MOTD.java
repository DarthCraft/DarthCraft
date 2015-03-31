/*package net.darthcraft.dcmod.addons;

import java.util.Collection;
import java.util.Random;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTD extends DarthCraftAddon
{
    private String randomplayer;

    public MOTD(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onServerPing(ServerListPingEvent event)
    {
        final String playerip = event.getAddress().getHostAddress();
        final String playername = plugin.playerManager.getPlayerNameByIp(playerip);
        
        if(playername == null)
        {
            final String randomplayer = "";
        }
        else
        {
            final String randomplayer = playername;
        }
        

        Random random = new Random();
        Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
        final int onlineplayercount = onlinePlayers.size();
        int randomPlayer = random.nextInt(onlineplayercount);
        Player player = (Player) onlinePlayers.toArray()[randomPlayer];

        event.setMotd(DC_Utils.colorize(plugin.messages.getString("serverlistmotd").replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", ((randomplayer))).replace("%randomplayer%", player.getName())) + "\n" + (plugin.messages.getString("serverlistmotdline2").replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", (randomplayer)).replace("%randomplayer%", player.getName())));
    }
}

*/