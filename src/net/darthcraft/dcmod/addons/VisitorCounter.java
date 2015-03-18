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
        int unique = plugin.dataFile.getInt("uniqueplayers");
        int players = plugin.dataFile.getInt("players");

        players++;
        plugin.dataFile.set("players", players);
        //Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + players + " players that have joined the server.");

        if (!event.getPlayer().hasPlayedBefore())
        {
            unique++;
            plugin.dataFile.set("uniqueplayers", unique);
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + players + " players that have joined the server.");
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[DC Visitor Counter] There have been " + unique + " unique players that have joined the server.");

        }
        plugin.dataFile.save();
    }
}
