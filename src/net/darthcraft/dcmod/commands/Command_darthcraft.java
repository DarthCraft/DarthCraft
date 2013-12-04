package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.ConfigConverter;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permission.ANYONE)
public class Command_darthcraft extends DarthCraftCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            msg(ChatColor.GRAY + "-- " + ChatColor.DARK_PURPLE + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.GRAY + " --");
            msg(ChatColor.DARK_PURPLE + "Darth" + ChatColor.LIGHT_PURPLE + "Craft" + ChatColor.GOLD + " is a community-focussed,"
                    + " non-PvP survival server.");
            msg(ChatColor.GOLD + "It is made possible by a long list of names of whom which developers, administrators and "
                    + "other staff who can't all be named.");
            msg(ChatColor.RED + "Special thanks to:");
            msg(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "DarthSalamon" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Developer" + ChatColor.WHITE + ")");
            msg(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "KickAssScott" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Game manager" + ChatColor.WHITE + ")");
            msg(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "pbgben" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Host, Developer" + ChatColor.WHITE + ")");
            msg(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "JabbaTheJake" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            msg(ChatColor.DARK_GRAY + "- " + ChatColor.DARK_GREEN + "oscarandjo" + ChatColor.WHITE + " (" + ChatColor.YELLOW + "Head-Admin" + ChatColor.WHITE + ")");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("plugin")) {
                msg(ChatColor.GOLD + plugin.pluginName + " for 'DarthCraft', a community-focussed non-PvP server.");
                msg(ChatColor.GOLD + String.format("Version " + ChatColor.BLUE + "%s.%s" + ChatColor.BLUE + ", built %s.",
                        plugin.pluginVersion,
                        plugin.pluginBuildNumber,
                        plugin.pluginBuildDate));
                msg(ChatColor.GOLD + "Created by: " + plugin.pluginAuthors);
                msg(ChatColor.GREEN + "Visit " + ChatColor.AQUA + "http://darthcraft.net/" + ChatColor.GREEN + " for more information.");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!PermissionUtils.hasPermission(sender, Permission.HOST)) {
                    return noPerms();
                }
                
                util.adminAction(sender, "Reloading DarthCraft config");

                plugin.mainConfig.load();

                // Debug-mode
                plugin.logger.setDebugMode(plugin.mainConfig.getBoolean("debug"));
                plugin.logger.debug("Debug-mode enabled!");

                // Disable the plugin if the config defines so
                if (!plugin.mainConfig.getBoolean("enabled", true)) {
                    plugin.logger.warning("Disabling: defined in config");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    return true;
                }

                // Load bans
                plugin.bansConfig.load();

                // Parse old BanPlus ban files
                final ConfigConverter converter = ConfigConverter.getInstance(plugin);
                converter.parseBanPlusConfig();

                // Cache items from config files
                plugin.banManager.loadBans();
                plugin.trollMode.loadSettings();
                plugin.chatFilter.loadSettings();
                plugin.forceIp.loadSettings();


                msg("Finished reloading config.");
                return true;
            }

        }

        return showUsage(cmd);
    }
}
