package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(value = SourceType.PLAYER)
@Permissions(value = Permissions.Permission.ADMIN)
public class Command_fly extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length == 0)
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("You must be a player to execute that command!");
                return true;
            }

            Player player = (Player) sender;

            if (!player.getAllowFlight())
            {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(ChatColor.GOLD + "Fly mode enabled!");
            }
            else
            {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(ChatColor.DARK_RED + "Fly mode disabled!");

            }

        }
        else if (args.length == 1)
        {
            final Player player = PlayerUtils.getPlayer(args[0]);

            if (!player.getAllowFlight())
            {
                player.setAllowFlight(true);
                player.setFlying(true);
                sender.sendMessage(ChatColor.GOLD + player.getName() + "'s Fly mode enabled!");
                player.sendMessage(ChatColor.GOLD + "Fly mode enabled!");
            }
            else
            {
                player.setAllowFlight(false);
                player.setFlying(false);
                sender.sendMessage(ChatColor.DARK_RED + player.getName() + "'s Fly mode disabled!");
                player.sendMessage(ChatColor.DARK_RED + "Fly mode disabled!");

            }
        }

        return true;

    }
}
