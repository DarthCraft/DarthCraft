package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.HOST)
public class Command_abduct extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        Player player = (Player) sender;
        if(!DC_Utils.DOOMHAMMERS.contains(player.getName()))
        {
            
            for(int i = 0; i < 5; i++)
            {
                player.getWorld().strikeLightningEffect(player.getLocation());
            }
            sender.sendMessage(ChatColor.GREEN + "Ready to abduct players!");
            player.getInventory().addItem(DC_Utils.getDoomHammer());
            DC_Utils.DOOMHAMMERS.add(player.getName());
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Players are safe once again...");
            player.getInventory().remove(DC_Utils.getDoomHammer());
            DC_Utils.DOOMHAMMERS.remove(player.getName());
        }
        return true;
    }

}
