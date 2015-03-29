package net.darthcraft.dcmod.listeners;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{

    private final DarthCraft plugin;

    public BlockListener(DarthCraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onUncancelledBlockBreakEvent(BlockBreakEvent event)
    {
        plugin.likeSigns.onUncancelledBlockBreakEvent(event);
        plugin.treeDetector.onUncancelledBlockBreakEvent(event);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        plugin.treeDetector.BlockPlaceEvent(event);
    }
}
