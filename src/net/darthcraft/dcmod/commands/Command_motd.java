package net.darthcraft.dcmod.commands;

import java.util.Collection;
import java.util.Random;
import net.darthcraft.dcmod.DC_Messages;
import net.darthcraft.dcmod.DC_Utils;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(value = SourceType.PLAYER)
@Permissions(value = Permissions.Permission.ANYONE)
public class Command_motd extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("You must be a player to execute that command!");
            return true;
        }

        Random random = new Random();
        Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
        final int onlineplayercount = onlinePlayers.size();
        int randomPlayer = random.nextInt(onlineplayercount);
        Player randomplayer = (Player) onlinePlayers.toArray()[randomPlayer];

        Player player = (Player) sender;

        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE1.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE2.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE3.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE4.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));
        player.sendMessage(DC_Utils.colorize(DC_Messages.IGNMOTD_LINE5.replace("%serverversion%", Bukkit.getBukkitVersion()).replace("%playername%", player.getName())).replace("%randomplayer%", randomplayer.getName()).replace("%onlinecount%", Integer.toString(Bukkit.getOnlinePlayers().size())));

        return true;

    }
}
