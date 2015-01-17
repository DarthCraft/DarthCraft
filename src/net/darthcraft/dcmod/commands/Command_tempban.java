package net.darthcraft.dcmod.commands;

import java.util.Date;
import net.darthcraft.dcmod.player.Ban;
import net.darthcraft.dcmod.player.Ban.BanType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.TimeUtils;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_tempban extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length < 2)
        {
            return showUsage(cmd);
        }

        if (args.length < 3)
        {
            return warn("Please specify proper reason when banning a player.");
        }

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
        if (player == null)
        {
            return warn("Player not found, or never joined the server.");
        }

        if (player.isOnline())
        {
            if (Permissions.PermissionUtils.hasPermission((Player) player, Permission.ADMIN))
            {
                if (!Permissions.PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
                {
                    return warn("You may not ban that player.");
                }
            }
        }

        final Date until = TimeUtils.parseDateOffset(args[1]);
        if (until == null)
        {
            return warn("Incorrect expiry date.");
        }

        final String reason = StringUtils.join(args, " ", 2, args.length);

        util.adminAction(sender, "Banning " + player.getName() + " for " + reason + " until " + TimeUtils.parseDate(until));

        final Ban ban = new Ban();
        ban.setType(BanType.UUID);
        ban.setName(player.getName());
        ban.setUuid(player.getUniqueId());
        ban.setReason(reason);
        ban.setBy(sender.getName());
        ban.setExpiryDate(until);
        banManager.ban(ban);

        if (player.isOnline())
        {
            ((Player) player).kickPlayer(ban.getKickMessage());
        }

        return true;
    }
}
