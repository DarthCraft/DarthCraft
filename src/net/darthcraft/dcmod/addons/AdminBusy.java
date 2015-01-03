package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AdminBusy extends DarthCraftAddon
    {

    private static final int MIN_WORD_LENGTH = 4;

    public AdminBusy(DarthCraft plugin)
        {
        super(plugin);
        }

    public void toggleBusyStatus(Player player)
        {
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

        info.setBusy(!info.isBusy());

        util.msg(player, ChatColor.AQUA + "You have toggled busy status o" + (info.isBusy() ? "n" : "ff"));
        util.adminAction(player, ChatColor.AQUA + "Toggled busy status o" + (info.isBusy() ? "n" : "ff"));

        if (DC_Utils.HOSTS.contains(player.getName()) || DC_Utils.HEADADMINS.contains(player.getName()))
            {
            // player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            player.setPlayerListName((info.isBusy() ? ChatColor.GRAY + player.getName() : ChatColor.LIGHT_PURPLE + player.getName()));
            }

        else if (PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
            {
            //  player.setPlayerListName(ChatColor.RED + player.getName());
            player.setPlayerListName((info.isBusy() ? ChatColor.GRAY + player.getName() : ChatColor.RED + player.getName()));
            }

        else if (PermissionUtils.hasPermission(player, Permissions.Permission.PREMIUM))
            {
            // player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
            player.setPlayerListName((info.isBusy() ? ChatColor.GRAY + player.getName() : ChatColor.DARK_PURPLE + player.getName()));
            }
        }

    public void onPlayerChat(AsyncPlayerChatEvent event)
        {
        final String[] words = event.getMessage().split(" ");
        for (final String word : words)
            {
            if (word.length() < MIN_WORD_LENGTH)
                {
                continue;
                }

            final Player player = server.getPlayer(word);
            if (player == null)
                {
                continue;
                }

            if (!PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
                {
                return;
                }

            if (plugin.playerManager.getInfo(player).isBusy())
                {
                plugin.util.sendSyncMessage(event.getPlayer(), ChatColor.RED + player.getName() + " is busy right now, try again later");
                }
            }
        }

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
        {
        final String[] words = event.getMessage().split(" ");
        for (final String word : words)
            {
            if (word.length() < MIN_WORD_LENGTH)
                {
                continue;
                }

            final Player player = server.getPlayer(word);
            if (player == null)
                {
                continue;
                }

            if (!PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
                {
                return;
                }

            if (plugin.playerManager.getInfo(player).isBusy())
                {
                plugin.util.sendSyncMessage(event.getPlayer(), ChatColor.RED + player.getName() + " is busy right now, try again later");
                }
            }
        }
    }
