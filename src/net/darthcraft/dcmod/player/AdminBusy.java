package net.darthcraft.dcmod.player;

import net.darthcraft.dcmod.player.PlayerManager;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.darthcraft.dcmod.commands.Permissions;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        util.msg(player, ChatColor.AQUA + "Toggled busy status o" + (info.isBusy() ? "n" : "ff"));
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
    }
