package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import net.darthcraft.dcmod.player.PlayerManager;
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
            return warn(DC_Messages.SPECIFY_REASON);
        }

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
        final int amount = Integer.parseInt(args[1]);
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
        if (player == null)
        {
            return warn(DC_Messages.PLAYER_NOT_FOUND);
        }

        if (!player.isOnline())
        {

            return warn(DC_Messages.CANNOT_WARN_PLAYER);

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
                sender.sendMessage(DC_Messages.WORLD_IMPLODE);
                return false;
            }

            int curwarning = info.getWarnings();
            int newwarning = curwarning - amount;

            if (newwarning > 0)
            {
                sender.sendMessage(DC_Messages.WORLD_IMPLODE);
                return false;
            }
            info.setWarnings(newwarning);
            info.addWaiveReason(reason + "  -  (" + amount + ")");

            util.adminAction(sender, "Has waived a warning for " + player.getName() + " with the reason " + ChatColor.DARK_PURPLE + reason + ChatColor.RED + " worth " + amount + " warning points");

            info.save();

            sender.sendMessage(ChatColor.DARK_GRAY + player.getName() + "'s new warnings are: " + newwarning);

            return true;
        }

    }

}
