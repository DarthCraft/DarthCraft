package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_setrank extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length != 2)
        {
            if (!PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
            {
                noPerms();
                return true;
            }

            if (args.length == 3 && args[2].equalsIgnoreCase("-f"))
            {
                server.dispatchCommand(server.getConsoleSender(), "manuadd " + args[0] + " " + args[1]);
                return true;
            }

            showUsage(cmd);
            return true;
        }

        OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);

        if (player == null)
        {
            warn(DC_Messages.PLAYER_NOT_FOUND);
            return true;
        }

        if (args[1].equalsIgnoreCase("member"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " member");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'member'");
            return true;

        }
        else if (args[1].equalsIgnoreCase("loyalmember"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " loyalmember");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'loyal'");
            return true;
        }

        if (!PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
        { // HeadAdmin and above
            noPerms();
            return true;
        }

        if (args[1].equalsIgnoreCase("premium"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " premium");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'premium'");
            return true;

        }
        else if (args[1].equalsIgnoreCase("admin"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " admin");
            util.adminAction(sender, "Congratulations to " + player.getName() + " for achiving the rank of admin!");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'admin'");
            return true;

        }
        else if (args[1].equalsIgnoreCase("premiumadmin"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " premiumadmin");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'pradmin'");
            return true;
        }

        if (!PermissionUtils.hasPermission(sender, Permission.HOST))
        { // Host only
            noPerms();
            return true;
        }

        if (args[1].equalsIgnoreCase("headadmin"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " headadmin");
            player.setOp(true);
            util.adminAction(sender, "Congratulations to " + player.getName() + " for achiving the rank of Head Admin!");
            sender.sendMessage(ChatColor.YELLOW + "Set " + player.getName() + DC_Messages.RANK_SET + "'headadmin'");
            return true;

        }
        else if (args[1].equalsIgnoreCase("partner"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " partner");
            sender.sendMessage("Set " + player.getName() + DC_Messages.RANK_SET + "'partner'");
            return true;

        }
        else if (args[1].equalsIgnoreCase("host"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " host");
            player.setOp(true);
            util.adminAction(sender, "Congratulations to " + player.getName() + " for achiving the rank of host!");
            sender.sendMessage("Set " + player.getName() + DC_Messages.RANK_SET + "host'");
            return true;
        }
        else if (args[1].equalsIgnoreCase("legacymember"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " legacymember");
            sender.sendMessage("Set " + player.getName() + DC_Messages.RANK_SET + "'legacymember'");
            return true;
        }
        else if (args[1].equalsIgnoreCase("legacypremium"))
        {
            server.dispatchCommand(server.getConsoleSender(), "manuadd " + player.getName() + " legacypremium");
            sender.sendMessage("Set " + player.getName() + DC_Messages.RANK_SET + "'legacypremium'");
            return true;
        }

        warn(DC_Messages.WORLD_IMPLODE);

        return true;
    }
}
