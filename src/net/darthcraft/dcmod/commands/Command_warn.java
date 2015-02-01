package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permissions.Permission.ADMIN)
public class Command_warn extends DarthCraftCommand
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

        final Player player = PlayerUtils.getPlayer(args[0]);
        final int amount = Integer.parseInt(args[1]);
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

        if (player == null)
        {
            return warn(DC_Messages.PLAYER_NOT_FOUND);
        }

        if (player.isOnline())
        {
            if (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.ADMIN))
            {
                if (!Permissions.PermissionUtils.hasPermission(sender, Permissions.Permission.HEADADMIN))
                {
                    return warn(DC_Messages.CANNOT_WARN_PLAYER);
                }
            }
        }

        final String reason = StringUtils.join(args, " ", 2, args.length);

        if (amount > 10 && !Permissions.PermissionUtils.hasPermission(sender, Permissions.Permission.HEADADMIN))
        {
            sender.sendMessage(DC_Messages.NO_MORE_THAN_TEN);
            return false;
        }
        else if (amount < 0)
        {
            sender.sendMessage(DC_Messages.CANNOT_REMOVE_POINTS);
            return false;
        }
        else
        {

            if ("wild1145".equals(player.getName()) &! "wild1145".equals(sender.getName()))
            {
                sender.sendMessage(ChatColor.DARK_RED + "Ha, Nice Try. Wild has thought this one through and has prevented evil.");
                util.adminAction(sender, "Has attempted to warn the almighty wild. It failed misrably.");
                return false;
            }

            util.adminAction(sender, DC_Messages.WARNING_BROADCAST_MESSAGE  + player.getName() + ChatColor.DARK_PURPLE + reason + ChatColor.RED + " (" + amount + ")");

            int curwarning = info.getWarnings();
            info.setWarnings(curwarning + amount);
            int newwarning = info.getWarnings();
            info.addReason(reason + "  -  (" + amount + ")");

            info.save();

            sender.sendMessage(ChatColor.DARK_GRAY + player.getName() + "'s new warnings are: " + newwarning);

            plugin.warningSystem.warningCheck(player);

            return true;

        }

    }

}
