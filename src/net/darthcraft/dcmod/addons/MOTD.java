package net.darthcraft.dcmod.addons;

import java.util.Collection;
import java.util.Random;
import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import net.pravian.bukkitlib.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTD extends DarthCraftAddon
{
    
    public Player randomplayer;
   
    public MOTD(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onServerPing(ServerListPingEvent event)
    {        
        Random newrandom = new Random();
        
        if(server.getOnlinePlayers().isEmpty())
        {
            Player randomplayer = null;
        }
        else
        {
            Player randomplayer = (Player) server.getOnlinePlayers().toArray()[server.getOnlinePlayers().size()];
        }
        final String playerip = event.getAddress().getHostAddress();
        final String playername = plugin.playerManager.getPlayerNameByIp(playerip);
        

        if (playername == null)
        {
            final String randomplayer = "";
        }
        else
        {
            final String randomplayer = playername;
        }
        
        if(server.getOnlinePlayers().equals(0))
        {
            Player randomplayer = null;
        }

        final String pingmotdline1 = ChatUtils.colorize(DC_Messages.PING_LINE1.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", playername)).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size()));
        final String pingmotdline2 = ChatUtils.colorize(DC_Messages.PING_LINE2.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", playername)).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size()));

        event.setMotd(pingmotdline1 + "\n" + pingmotdline2);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        
         Random newrandom = new Random();
         
        if(server.getOnlinePlayers().isEmpty())
        {
            randomplayer = player;
            
        }
        else
        {
            Player randomplayer = (Player) server.getOnlinePlayers().toArray()[server.getOnlinePlayers().size()];
        }

        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE1.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE2.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE3.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE4.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE5.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
    
    }
}
