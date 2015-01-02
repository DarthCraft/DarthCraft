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
import net.darthcraft.dcmod.DC_Utils;

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
            return warn("Please specify proper reason and amount of warning points when issuing a warning.");
            }

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
        final int amount = NumberUtils.stringToInt(args[1]);
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);
        if (player == null)
            {
            return warn("Player not found, or never joined the server.");
            }

        if (player.isOnline())
            {
            if (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.ADMIN))
                {
                if (!Permissions.PermissionUtils.hasPermission(sender, Permissions.Permission.HEADADMIN))
                    {
                    return warn("You may not warn that player.");
                    }
                }
            }

        final String reason = StringUtils.join(args, " ", 2, args.length);

        if (amount > 10 && !Permissions.PermissionUtils.hasPermission(sender, Permissions.Permission.HEADADMIN))
            {
            sender.sendMessage(ChatColor.DARK_RED + "You are unable to issue greater than 10 points. Please contact a host or higher if you feel it appropriate to issue this amount. ");
            return false;
            }
        else if (amount < 0)
            {
            sender.sendMessage(ChatColor.DARK_RED + "To remove warning points you will need to contact a head admin or host.");
            return false;
            }
        else
            {
            
            if ("wild1145".equals(player.getName()))
                {
                sender.sendMessage(ChatColor.DARK_RED + "Ha, Nice Try. Wild has thought this one through and has prevented evil.");
                util.adminAction(sender, "Has attempted to warn the almighty wild. It failed misrably.");
                return false;
                }

            util.adminAction(sender, "Has issues a warning for " + player.getName() + " with the reason " + ChatColor.DARK_PURPLE + reason + ChatColor.RED + " worth " + amount + " warning points");

            int curwarning = info.getWarnings();
            info.setWarnings(curwarning + amount);
            int newwarning = info.getWarnings();
            info.addReason(reason + "  -  (" + amount + ")");

            info.save();

            sender.sendMessage(ChatColor.DARK_GRAY + player.getName() + "'s new warnings are: " + newwarning);

            return true;
            }

        }

    }
