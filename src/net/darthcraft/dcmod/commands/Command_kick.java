package net.darthcraft.dcmod.commands;

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
public class Command_kick extends DarthCraftCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            return showUsage(cmd);
        }

        if (args.length < 2) {
            return warn("Please provide a valid reason when using this command.");
        }

        Player player = PlayerUtils.getPlayer(args[0]);
        if (player == null) {
            return warn("Player not found!");
        }
         
        if (Permissions.PermissionUtils.hasPermission(player, Permission.ADMIN)) {
            if (!Permissions.PermissionUtils.hasPermission(sender, Permission.HEADADMIN)) {
                return warn("You may not kick that player.");
            }
        }
        
        final String reason = StringUtils.join(args, " ", 1, args.length);
        
        util.adminAction(sender, "Kicking " + player.getName() + " for " + reason);

        player.kickPlayer(ChatColor.RED
                + "You have been kicked\n"
                + "Reason: " + reason);

        return true;
    }
}
