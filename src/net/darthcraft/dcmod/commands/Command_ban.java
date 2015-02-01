package net.darthcraft.dcmod.commands;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.player.Ban;
import net.darthcraft.dcmod.player.Ban.BanType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.darthcraft.dcmod.DC_Messages;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_ban extends DarthCraftCommand
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

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
        if (player == null)
        {
            return warn(DC_Messages.PLAYER_NOT_FOUND);
        }

        if (player.isOnline())
        {
            if (Permissions.PermissionUtils.hasPermission((Player) player, Permission.ADMIN))
            {
                if (!Permissions.PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
                {
                    return warn(DC_Messages.CANNOT_BAN_PLAYER);
                }
            }
        }

        if (banManager.getNameBan(player.getName()) != null)
        {
            return warn(DC_Messages.ALREADY_BANNED);
        }

        final String reason = StringUtils.join(args, " ", 1, args.length);

        util.adminAction(sender, "Banning " + player.getName() + " for " + reason);

        Ban ban = new Ban();
        ban.setType(BanType.UUID);
        ban.setName(player.getName());
        ban.setUuid(player.getUniqueId());
        ban.setBy(sender.getName());
        ban.setReason(reason);
        ban.setExpiryDate(null);

        banManager.ban(ban);

        if (player.isOnline())
        {
            ((Player) player).kickPlayer(ban.getKickMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M hh:mm");
        String Time = sdf.format(new Date());
        // Changed to Unix Time Frame. 

        long unixTime = System.currentTimeMillis() / 1000L;

        try
        {
            DC_Utils.updateDatabase("INSERT INTO bans (Name, UUID, BanBy, Reason, Expires, Time) VALUES ('" + player.getName() + "', '" + player.getUniqueId() + "', '" + sender.getName() + "', '" + reason + "','" + Time + "');");

        }
        catch (SQLException ex)
        {
            sender.sendMessage(DC_Messages.ERROR);
        }

        return true;
    }
}
