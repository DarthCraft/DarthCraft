package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.apache.commons.lang.StringUtils;
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
         sender_p.sendMessage(ChatColor.DARK_RED + "It appears either you are just being silly, or forgot your own name. Please select a person to share with OTHER than yourself. ");
         return showUsage(cmd);
         }
         
        if (args.length >= 2)
        {

            // Getting Information about the current XP and such. 
            final int amount = Integer.parseInt(args[1]);
            final int playeroriginal = player.getLevel();
            final int senderoriginal = sender_p.getLevel();
            final int newplaeramount = playeroriginal + amount;
            final int newsenderamount = senderoriginal - amount;
            
            

            // Checking if the sender has enough EXP to send. 
            if (senderoriginal < amount)
            {
                sender.sendMessage(ChatColor.DARK_RED + "Sorry, but you currently too little XP to share. Please use a number less than " + senderoriginal);
                return true;
            }
            else
            {
                player.setLevel(newplaeramount);
                sender_p.setLevel(newsenderamount);
                player.sendMessage(ChatColor.DARK_GREEN + "Your new XP level is: " + newplaeramount);
                sender_p.sendMessage(ChatColor.DARK_GREEN + "Your new XP level is: " + newsenderamount);
                return true;

            }

        }
        else
        {
            return showUsage(cmd);
        }
    }

}
