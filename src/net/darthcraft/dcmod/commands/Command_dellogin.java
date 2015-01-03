package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.ANYONE)
public class Command_dellogin extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {

        if (args.length == 1)
            {
            if (Permissions.PermissionUtils.hasPermission((Player) sender, Permissions.Permission.ADMIN))
                {
                Player player = PlayerUtils.getPlayer(args[0]);

                final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

                info.setLoginMessage(null);

                return true;
                }
            return false;
            }

        else
            {

            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo((Player) sender);

            info.setLoginMessage(null);

            sender.sendMessage(ChatColor.DARK_GREEN + "You have successfuly reset your login message. You will no longer have one when you join the server.");
            return true;
            }
        }

    }
