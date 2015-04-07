package net.darthcraft.dcmod.addons;

import java.util.Collection;
import java.util.Random;
import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTD extends DarthCraftAddon
{

    Random random = new Random();
    Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
    final int onlineplayercount = onlinePlayers.size();
    int randomPlayer = random.nextInt(onlineplayercount);
    Player randomplayer = (Player) onlinePlayers.toArray()[randomPlayer];

    public MOTD(DarthCraft plugin)
    {
        super(plugin);
    }

// This is disabled for now. It will be re-worked in a future version. 
    /*
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

     event.setMotd(DC_Utils.colorize(plugin.messages.getString("serverlistmotd").replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", ((randomplayer))).replace("%randomplayer%", randomplayer.getName())) + "\n" + (plugin.messages.getString("serverlistmotdline2").replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", (randomplayer)).replace("%randomplayer%", randomplayer.getName())));
     }
     */
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE1.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE2.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE3.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE4.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE5.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
    }
}
