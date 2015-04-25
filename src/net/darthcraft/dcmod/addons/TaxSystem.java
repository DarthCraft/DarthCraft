package net.darthcraft.dcmod.addons;

import java.util.Date;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class TaxSystem extends DarthCraftAddon
{

    public TaxSystem(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
        Date weekago = new Date();
        Date today = new Date();
        weekago.setDate(today.getDate() - 7);
        
        if (info.getLastTaxDate().before(weekago))
        {
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "eco take " + player + " " + (info.getBeaconCount() * 2));
            info.setLastTaxDate(today);
        }
        else
        {
            LoggerUtils.info(player + " has not been taxed on this login.");
        }
    }

}
