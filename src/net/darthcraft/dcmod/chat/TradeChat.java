package net.darthcraft.dcmod.chat;

import java.util.logging.Level;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.darthcraft.dcmod.player.PlayerManager.PlayerInfo;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TradeChat extends DarthCraftAddon
{

    public TradeChat(DarthCraft plugin)
    {
        super(plugin);
    }

    public void toggleTradeChat(Player player)
    {
        PlayerInfo info = plugin.playerManager.getInfo(player);

        info.setInAdminChat(!info.isInTradeChat());

        player.sendMessage(ChatColor.AQUA + "Toggled The Trade Chat o" + (info.isInAdminChat() ? "n" : "ff") + ".");
    }

    public void sendTradeMessage(String name, String msg)
    {
        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            PlayerInfo info = plugin.playerManager.getInfo(player);

            if (PermissionUtils.hasPermission(player, Permission.ADMIN))
            {
                if (info.isInTradeChat())
                {
                    player.sendMessage(ChatColor.WHITE + "<" + ChatColor.DARK_PURPLE + "TRADE" + ChatColor.WHITE + "> " + ChatColor.RESET + player.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.GOLD + msg);
                }
                else
                {
                    player.sendMessage(ChatColor.DARK_PURPLE + "TC: " + ChatColor.DARK_AQUA + name + ChatColor.WHITE + ": " + ChatColor.GRAY + msg);
                }
            }
            else
            {
                if (info.isInTradeChat())
                {
                    player.sendMessage(ChatColor.WHITE + "<" + ChatColor.DARK_PURPLE + "TRADE" + ChatColor.WHITE + "> " + ChatColor.RESET + player.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.GOLD + msg);

                }
            }
        }
        plugin.logger.log(Level.INFO, "<TRADE> {0}: {1}", new Object[]
        {
            name, msg
        });

    }

    // events
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        if (plugin.playerManager.getInfo(event.getPlayer()).isInAdminChat())
        {
            sendTradeMessage(event.getPlayer().getName(), event.getMessage());
            event.setCancelled(true);
        }

    }
}
