package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.ANYONE)
public class Command_sharexp extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        // Getting the player and sender. 
        final Player player = PlayerUtils.getPlayer(args[0]);
        final Player sender_p = (Player) sender;

        if (sender_p == player)
        {
            sender_p.sendMessage(DC_Messages.WORLD_IMPLODE);
            return showUsage(cmd);
        }

        if (args.length >= 2)
        {

            // Getting Information about the current XP and such. 
            final int amount = Integer.parseInt(args[1]);
            final int playeroriginal = (int) player.getExp();
            final int senderoriginal = (int) player.getExp();
            final int playeramountpretax = playeroriginal + amount;
            final int newsenderamount = senderoriginal - amount;

            // Checking if the sender has enough EXP to send. 
            if (senderoriginal < amount && amount <= 0)
            {
                sender.sendMessage(ChatColor.DARK_RED + DC_Messages.XP_LOW + senderoriginal);
                return true;
            }
            else
            {
                player.setExp(playeramountpretax);
                sender_p.setExp(newsenderamount);
                player.sendMessage(ChatColor.DARK_GREEN + DC_Messages.NEW_XP + playeramountpretax);
                sender_p.sendMessage(ChatColor.DARK_GREEN + DC_Messages.NEW_XP + newsenderamount);
                return true;

            }

        }
        else
        {
            return showUsage(cmd);
        }
    }

}
