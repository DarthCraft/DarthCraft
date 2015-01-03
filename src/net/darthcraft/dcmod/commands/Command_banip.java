package net.darthcraft.dcmod.commands;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.darthcraft.dcmod.DC_Utils;
import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.player.Ban;
import net.darthcraft.dcmod.player.Ban.BanType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.util.TimeUtils;
import net.pravian.bukkitlib.util.IpUtils;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_banip extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {
        if (args.length == 0)
            {
            return showUsage(cmd);
            }

        final boolean all = args[0].equals("-a");
        if (all)
            {
            args = Arrays.copyOfRange(args, 1, args.length); // Shift one
            }

        if (args.length < 2)
            {
            return warn("Please specify proper reason when banning a player.");
            }

        final OfflinePlayer target = PlayerUtils.getOfflinePlayer(args[0]);
        if (target == null || !target.hasPlayedBefore())
            {
            return warn("Player not found, or never joined the server.");
            }

        if (target.isOnline())
            {
            if (Permissions.PermissionUtils.hasPermission((Player) target, Permission.ADMIN))
                {
                if (!Permissions.PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
                    {
                    return warn("You may not ban that player.");
                    }
                }
            }

        final List<String> ips = new ArrayList<String>();
        if (all)
            {
            for (String ip : playerManager.getInfo(target).getIps())
                {
                ips.add(ip);
                }
            }
        else
            {
            ips.add(playerManager.getInfo(target).getLastIp());
            }

        final List<String> bannedIps = new ArrayList<String>();
        final String reason = StringUtils.join(args, " ", 1, args.length);

        Ban ban = banManager.getNameBan(target.getName());

        if (ban != null)
            { // If a player is already banned by name
            for (String ip : ips)
                {

                if (ban.getIps().contains(ip))
                    {
                    continue;
                    }

                bannedIps.add(ip);
                ban.addIp(ip);

                }
            if (bannedIps.isEmpty())
                {
                return warn((all ? "All the players IPs have already been banned" : "That IP is already banned"));
                }

            banManager.update(ban);

            }
        else
            {
            if (args.length < (all ? 3 : 2))
                {
                return warn("Please specify proper reason when banning a player.");
                }

            for (String ip : ips)
                {
                ban = new Ban();
                ban.setType(BanType.IP);
                ban.setBy(sender.getName());
                ban.addIp(ip);
                ban.setReason(reason);
                ban.setExpiryDate(null);

                if (banManager.isBanned(ban))
                    {
                    continue;
                    }

                bannedIps.add(ip);
                banManager.ban(ban);
                }

            if (bannedIps.isEmpty())
                {
                return warn((all ? "All the players IPs have already been banned" : "That IP is already banned"));
                }
            }

        util.adminAction(sender,
                         "Banning IP" + (ips.size() > 1 ? "s " : " ") + PlayerUtils.concatPlayernames(ips) + " (" + target.getName() + ") for " + reason);

        for (Player player : server.getOnlinePlayers())
            {
            if (ips.contains(IpUtils.getIp(player)))
                {
                player.kickPlayer(ChatColor.RED
                                  + "Banned!\n"
                                  + "Reason: " + reason + "\n"
                                  + "Expires: " + TimeUtils.parseDate(null) + "\n"
                                  + "Banned by: " + sender.getName());
                }
            }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M hh:mm");
        String Time = sdf.format(new Date());
        // Changed to Unix Time Frame. 

        long unixTime = System.currentTimeMillis() / 1000L;

        try
            {
            DC_Utils.updateDatabase("INSERT INTO bans (IP, BanBy, Reason, Expires, Time) VALUES ('" + ips + "', '" + sender.getName() + "', '" + reason + "','" + Time + "');");

            }
        catch (SQLException ex)
            {
            sender.sendMessage("Error submitting report to Database. Please consult a developer ASAP");
            }

        return true;
        }
    }
