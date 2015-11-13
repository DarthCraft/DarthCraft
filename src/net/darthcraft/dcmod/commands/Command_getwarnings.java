package net.darthcraft.dcmod.commands;

import java.util.Iterator;
import java.util.List;
import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.ANY)
@Permissions(Permissions.Permission.ANYONE)
public class Command_getwarnings extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {

        if (args.length == 0)
        {

            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo((OfflinePlayer) sender);

            sender.sendMessage(DC_Messages.WARNING_REASONS + info.getWarnings());
            sender.sendMessage(DC_Messages.WARNINGS_AS_FOLLOWS);

            List<String> someList = info.getReasons();

            for (String item : someList)
            {
                sender.sendMessage(ChatColor.DARK_AQUA + item);
            }

            sender.sendMessage(DC_Messages.WAIVE_WARNINGS_REASONS);

            List<String> waivesomeList = info.getWaiveReasons();

            for (String item : waivesomeList)
            {
                sender.sendMessage(ChatColor.DARK_AQUA + item);
            }

            return true;

        }
        else if (args.length == 1)
        {
            final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
            final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(player);

            if (sender == player || (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.ADMIN) || (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.HOST)) || (Permissions.PermissionUtils.hasPermission((Player) player, Permissions.Permission.HEADADMIN))))
            {
                sender.sendMessage(ChatColor.DARK_AQUA + player.getName() + DC_Messages.OTHER_WARNING_AMOUNT + info.getWarnings());
                sender.sendMessage(DC_Messages.WARNINGS_AS_FOLLOWS);

                List<String> someList = info.getReasons();

                for (String item : someList)
                {
                    sender.sendMessage(ChatColor.DARK_AQUA + item);
                }

                sender.sendMessage(DC_Messages.WAIVE_WARNINGS_REASONS);

                List<String> waivesomeList = info.getWaiveReasons();

                for (String item : waivesomeList)
                {
                    sender.sendMessage(ChatColor.DARK_AQUA + item);
                }

                return true;
            }
            else
            {
                sender.sendMessage(DC_Messages.NO_PREMS);
                return false;
            }
        }
        return false;

    }
}
