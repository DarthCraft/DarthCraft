package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.PLAYER)
@Permissions(Permission.ANYONE)
public class Command_christmas extends DarthCraftCommand
    {
    
    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {
        /*
        sender.sendMessage(ChatColor.GOLD + "The administrtion team here at DarthCraft would like to wish you a very " + ChatColor.DARK_RED + "Merry Christmas!");
        
        if (!sender.hasPermission("darthcraft.nochristmas"))
            {
            
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " diamond 12");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " cake 1");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " 98 15");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " 168 32");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " 169 1");
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "give " + sender.getName() + " 2 12");
            
           
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "manuaddp " + sender.getName() + " darthcraft.nochristmas");
            
            // Debugging Usage Only
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "email send wild1145 permission for " + sender.getName() + " to get the christmas gift has been approved");
            }
        else
            {
            sender.sendMessage(ChatColor.DARK_RED + "Sorry, but you have already had your Christmas Gifts!");
            
            // Debugging usage only 
            Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "email send wild1145 permission for " + sender.getName() + " to get a christmas gift has been refused.");
            }
        return true;
                */
        
        sender.sendMessage(ChatColor.DARK_RED + "Sorry, but its not quite Christmas yet! We hope you had an amazing Christmas but check back here next year to see what madness we have in stall for you!");
        return true;
        }
    
    }
