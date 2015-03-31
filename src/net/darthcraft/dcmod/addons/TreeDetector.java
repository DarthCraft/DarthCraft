package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class TreeDetector extends DarthCraftAddon
{

    public TreeDetector(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onUncancelledBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.getBlock().getType() == Material.SAPLING)
        {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + event.getPlayer().getName() + " Has destroyed a saplin, they clearly hate the trees! ATTACK!!!!");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "eco take " + event.getPlayer().getName() + " 12.5");
            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You have destroyed a saplin, shame on you. You have been fined 25Dar by order of the lord and mighty Ben.");
        }
        else if (event.getBlock().getType() == Material.WOOD)
        {
            event.getPlayer().sendMessage(ChatColor.GRAY + "Please remember to re-plant the trees. It allows us to keep the world looking amazing :)");
        }
    }
    
     public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        if (event.getBlock().getType() == Material.SAPLING)
        {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + event.getPlayer().getName() + " Has planted a saplin, they clearly love the trees!");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "eco give " + event.getPlayer().getName() + " 5");
            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You have destroyed a saplin, shame on you. You have been given 15Dar by order of the lord and mighty Ben.");
        }
    }

}
