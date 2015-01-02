package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.darthcraft.dcmod.player.PlayerManager;
import org.apache.commons.lang.NumberUtils;
import org.bukkit.ChatColor;

@Source(SourceType.ANY)
@Permissions(Permissions.Permission.HEADADMIN)
public class Command_delwarn extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {

        if (args.length == 0)
            {
            return showUsage(cmd);
            }

        if (args.length < 3)
            {
            return warn("Please specify proper reason and amount of warning points when waiving a warning.");
            }

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
        final int amount = NumberUtils.stringToInt(args[1]);
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
        if (player == null)
            {
            return warn("Player not found, or never joined the server.");
            }

        if (!player.isOnline())
            {

            return warn("You may not warn that player.");

            }

        final String reason = StringUtils.join(args, " ", 2, args.length);

        if (amount < 0)
            {
            sender.sendMessage(ChatColor.DARK_RED + "To waive a player warning, please use the /warn command");
            return false;
            }
        else
            {
            
            if (info.getWarnings() == 0)
                {
                sender.sendMessage(ChatColor.DARK_RED + "How about no... This player already has no warning points so your going to break shit.");
                return false;
                }

            util.adminAction(sender, "Has waived a warning for " + player.getName() + " with the reason " + ChatColor.DARK_PURPLE + reason + ChatColor.RED + " worth " + amount + " warnining points");

            int curwarning = info.getWarnings();
            info.setWarnings(curwarning - amount);
            int newwarning = info.getWarnings();
            info.addWaiveReason(reason + "  -  (" + amount + ")");

            info.save();

            sender.sendMessage(ChatColor.DARK_GRAY + player.getName() + "'s new warnings are: " + newwarning);

            return true;
            }

        }

    }
