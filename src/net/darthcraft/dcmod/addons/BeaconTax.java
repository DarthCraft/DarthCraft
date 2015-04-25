package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BeaconTax extends DarthCraftAddon
{

    public BeaconTax(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onUncancelledBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.getBlock().getType() == Material.BEACON)
        {
            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(event.getPlayer());
            info.setBeaconCount(info.getBeaconCount() - 1);
        }
    }

    public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        if (event.getBlock().getType() == Material.BEACON)
        {
            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(event.getPlayer());
            info.setBeaconCount(info.getBeaconCount() + 1);
            
            
        }
    }

}
