package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;

public class VisitorCounter extends DarthCraftAddon
{

    public VisitorCounter(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        int unique = plugin.mainConfig.getInt("uniqueplayers");
        int players = plugin.mainConfig.getInt("players");

        players++;
        plugin.mainConfig.set("players", players);
        //Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + players + " players that have joined the server.");

        if (!event.getPlayer().hasPlayedBefore())
        {
            unique++;
            plugin.mainConfig.set("uniqueplayers", unique);
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + players + " players that have joined the server.");
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + unique + " unique players that have joined the server.");

        }
        plugin.mainConfig.save();
    }
}
