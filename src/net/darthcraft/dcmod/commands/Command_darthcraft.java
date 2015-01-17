package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.pravian.bukkitlib.command.SourceType;
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
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.GRAY + "-- " + ChatColor.DARK_PURPLE + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.GRAY + " --");
            sender.sendMessage(ChatColor.DARK_PURPLE + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.GOLD + " is a community-focussed,"
                               + " non-PvP survival server.");
            sender.sendMessage(ChatColor.GOLD + "It is made possible by a long list of names of whom which developers, administrators and "
                               + "other staff who can't all be named.");
            sender.sendMessage(ChatColor.RED + "Special thanks to:");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "pbgben" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Owner, Host, Developer" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "Wild1145" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Lead Developer" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "KickAssScott" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Game manager" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "DarthSalamon" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Exiled, Developer" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "JabbaTheJake" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "boulos77" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            return true;
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("plugin"))
            {
                sender.sendMessage(ChatColor.GOLD + plugin.pluginName + " for 'DarthCraft', a community-focussed non-PvP server.");
                sender.sendMessage(ChatColor.GOLD + String.format("Version " + ChatColor.BLUE + "%s.%s" + ChatColor.BLUE + ", built %s.",
                                                                  plugin.pluginVersion,
                                                                  plugin.pluginBuildNumber,
                                                                  plugin.pluginBuildDate));
                sender.sendMessage(ChatColor.GOLD + "Created by: " + plugin.pluginAuthors);
                sender.sendMessage(ChatColor.GREEN + "Visit " + ChatColor.AQUA + "http://darthcraft.net/" + ChatColor.GREEN + " for more information.");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!PermissionUtils.hasPermission(sender, Permission.HOST))
                {
                    return noPerms();
                }

                util.adminAction(sender, "Reloading DarthCraft config");

                plugin.mainConfig.load();

                // Debug-mode
                plugin.logger.setDebugMode(plugin.mainConfig.getBoolean("debug"));
                plugin.logger.debug("Debug-mode enabled!");

                // Disable the plugin if the config defines so
                if (!plugin.mainConfig.getBoolean("enabled", true))
                {
                    plugin.logger.warning("Disabling: defined in config");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    return true;
                }

                // Load configs
                plugin.bansConfig.load();
                plugin.likersConfig.load();

                // Cache items from config files
                plugin.banManager.loadBans();
                plugin.trollMode.loadSettings();
                plugin.chatFilter.loadSettings();
                plugin.forceIp.loadSettings();
                plugin.likeSigns.loadSettings();

                sender.sendMessage("Finished reloading " + plugin.pluginName + " v" + plugin.pluginVersion + "." + plugin.pluginBuildNumber);
                return true;
            }

        }

        return showUsage(cmd);
    }
}
