package net.darthcraft.dcmod.addons;

import java.util.Date;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class VoteToPlay extends DarthCraftAddon
{

    public VoteToPlay(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onPlayerLogin(PlayerLoginEvent event)
    {
        if (plugin.mainConfig.getBoolean("votetoplay"))
        {
        final Player player = event.getPlayer();
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

        if (info.getLastLogin() != new Date())
        {
            if (info.getDaysLeft() == 0)
            {
                event.disallow(Result.KICK_OTHER, ChatColor.DARK_RED + "You are required to vote again before being able to join. Please do so and try again");
            }
            else
            {
                info.setDaysLeft(info.getDaysLeft() - 1);
                if (info.getDaysLeft() < 2)
                {
                    player.sendMessage(ChatColor.DARK_RED + "Warning: You currently have less than two days of access left. Please do some voting to boost your remaining access days.");
                }
                else
                {
                    player.sendMessage(ChatColor.DARK_GREEN + "You currently have " + info.getDaysLeft() + " days of playing the server until you will need to vote.");
                }
            }
        }
        else
        {
            event.allow();
            if (info.getDaysLeft() < 2)
            {
                player.sendMessage(ChatColor.DARK_RED + "Warning: You currently have less than two days of access left. Please do some voting to boost your remaining access days.");
            }
        }
        }
        else
        {
            logger.debug("The DCMod VoteToPlay setup has not been enabled - All players should be able to join.");
        }
    }
}
