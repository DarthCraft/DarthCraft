package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_o extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {

        if (args.length >= 1)
            {
            plugin.adminChat.sendAdminMessage(sender.getName(), StringUtils.join(args, " "));
            return true;
            }

        if (!(sender instanceof Player))
            {
            return warn("Only in-game players can toggle adminchat.");
            }

        plugin.adminChat.toggleAdminChat((Player) sender);
        return true;
        }
    }
