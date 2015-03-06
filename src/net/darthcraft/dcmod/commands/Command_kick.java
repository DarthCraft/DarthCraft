package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.LEGACY)
public class Command_kick extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length == 0)
        {
            return showUsage(cmd);
        }

        if (args.length < 2)
        {
            return warn(DC_Messages.SPECIFY_REASON);
        }

        Player player = PlayerUtils.getPlayer(args[0]);
        if (player == null)
        {
            return warn(DC_Messages.PLAYER_NOT_FOUND);
        }

        if (Permissions.PermissionUtils.hasPermission(player, Permission.ADMIN))
        {
            if (!Permissions.PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
            {
                return warn(DC_Messages.CANNOT_KICK_PLAYER);
            }
        }

        final String reason = StringUtils.join(args, " ", 1, args.length);

        util.adminAction(sender, "Kicking " + player.getName() + " for " + reason);

        player.kickPlayer(ChatColor.RED
                + DC_Messages.YOU_BEEN_KICKED + " \n"
                + reason);

        return true;
    }
}
