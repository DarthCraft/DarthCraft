package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permission.ANYONE)
public class Command_darthcraft extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        int unique = plugin.dataFile.getInt("uniqueplayers");
        int players = plugin.dataFile.getInt("players");

        if (args.length == 0 || args[0].equalsIgnoreCase("1"))
        {
            sender.sendMessage(ChatColor.DARK_AQUA + "--" + ChatColor.GOLD + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.DARK_AQUA + " Server" + ChatColor.DARK_AQUA + "--");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GOLD + "The " + ChatColor.GOLD + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.GOLD + " server is a community-focused, Non-PVP Server");
            sender.sendMessage(ChatColor.GOLD + "The server has been designed with the players in mind, and we have a lot of people who have made the server what it is today, and we thank them all.");
            sender.sendMessage(ChatColor.GOLD + "We would like to thank all the players and admins for making the server such a friendly and amazing place to play, and we thank you for your continued effort with the server.");
            sender.sendMessage(ChatColor.GOLD + "We would also like to give special thanks to the following players, who have made an outstanding contribution to the DarthCraft Server:");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "KimJongBen" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Owner, Host, Developer" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "Wild1145" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Lead Developer" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "KickAssScott" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Game manager" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "DarthSalamon" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Founder" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "JabbaTheJake" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "boulos77" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GOLD + "The server you are playing has had " + ChatColor.DARK_RED + unique + ChatColor.GOLD + " unique players and " + ChatColor.DARK_RED + players + ChatColor.GOLD + " different player joins since the plugin was added.");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_PURPLE + "Please use /darthcraft 2  or /darthcraft plugin  to see some developers information on this plugin.");
        }
        else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("plugin"))
        {
            sender.sendMessage(ChatColor.DARK_PURPLE + "The DarthCraft plugin was built for the DarthCraft server, here are some statistics about the plugin currently running on your server:");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_RED + "Build Version: " + plugin.pluginVersion);
            sender.sendMessage(ChatColor.DARK_RED + "Build Date: " + plugin.pluginBuildDate);
            sender.sendMessage(ChatColor.DARK_RED + "Build Number: " + plugin.pluginBuildNumber);
            sender.sendMessage(ChatColor.DARK_RED + "Plugin Authors: " + plugin.pluginAuthors);
            sender.sendMessage("");
            sender.sendMessage(ChatColor.DARK_PURPLE + "For more information, please see our website: " + ChatColor.AQUA + "http://www.darthcraft.net");
        }

        else if (args[0].equalsIgnoreCase("devtest"))
        {
            sender.sendMessage(ChatColor.DARK_RED + "Nothing to see here...");
        }

        return true;
    }
}
