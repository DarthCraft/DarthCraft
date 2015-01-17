package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import net.darthcraft.dcmod.player.PlayerManager.PlayerInfo;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.LEGACY)
public class Command_stfu extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length != 1)
        {
            return showUsage(cmd);
        }

        Player player = PlayerUtils.getPlayer(args[0]);
        if (player == null)
        {
            return warn("Player not found!");
        }

        if (PermissionUtils.hasPermission(player, Permission.ADMIN))
        {
            if (!PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
            {
                return warn("You may not mute that player.");
            }
        }

        PlayerInfo info = plugin.playerManager.getInfo(player);

        if (info.isMuted())
        {
            return warn("That player is already muted.");
        }

        util.adminAction(sender, "Telling " + player.getName() + " to STFU");
        info.setMuted(true);
        return true;
    }
}
