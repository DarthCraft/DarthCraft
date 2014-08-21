package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import net.darthcraft.dcmod.addons.PlayerManager.PlayerInfo;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_unmute extends DarthCraftCommand
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

        PlayerInfo info = plugin.playerManager.getInfo(player);

        if (!info.isMuted())
            {
            return warn("That player is not muted.");
            }

        util.adminAction(sender, "Unmuting " + player.getName());
        info.setMuted(false);
        msg("Unuted " + player.getName() + ".");
        return true;

        }
    }
