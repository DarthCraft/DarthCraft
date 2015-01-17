package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.ANYONE)
public class Command_setlogin extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {

        if (args.length == 0)
        {
            return false;
        }

        final String title = StringUtils.join(args, " ", 0, args.length);
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo((Player) sender);

        info.setLoginMessage(title);

        sender.sendMessage(DC_Utils.colorize(ChatColor.DARK_AQUA + "Your login message has been set to: " + sender.getName() + " is " + ChatColor.RESET + title));
        sender.sendMessage(ChatColor.DARK_RED + "If you wish to change this, please make the changes BEFORE your next login.");
        return true;
    }

}
