package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.ANYONE)
public class Command_report extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {

        if (args.length == 0)
        {
            return false;
        }

        Player player = PlayerUtils.getPlayer(args[0]);

        String Reported = player.getName();

        String report_reason = null;
        if (args.length <= 2)
        {
            return false;
        }
        else if (args.length >= 2)
        {
            report_reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        }

        if (player == sender)
        {
            sender.sendMessage(DC_Messages.WORLD_IMPLODE);
            return true;
        }

        for (Player admins : Bukkit.getOnlinePlayers())
        {
            if (Permissions.PermissionUtils.hasPermission((Player) admins, Permission.ADMIN) || Permissions.PermissionUtils.hasPermission((Player) admins, Permission.HEADADMIN) || Permissions.PermissionUtils.hasPermission((Player) admins, Permission.HOST))
            {
                admins.sendMessage(DC_Utils.colorize("&8[&4DarthCraft Plugin&8] &a" + sender.getName() + ChatColor.DARK_RED + DC_Messages.REPORT_REASON + Reported + " - " + player.getAddress().getAddress().getHostAddress() + " &2" + report_reason + "&4."));

            }
        }

        player.sendMessage(DC_Utils.colorize("&8[&4DarthCraft Plugin&8] &4" + DC_Messages.REPORTEE + "&5" + report_reason));

        sender.sendMessage(DC_Utils.colorize("&8[&4DarthCraft Plugin&8] &4" + DC_Messages.REPORT));

        return true;
    }

}
