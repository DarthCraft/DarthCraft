package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.HOST)
public class Command_invade extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {
        Player player = PlayerUtils.getPlayer(args[0]);

        player.setPlayerListName(ChatColor.DARK_RED + player.getName());

        player.chat("Yes, Elgin is far better than my shitty little county thing.");

        player.chat("I command all of you to join Elgin right now!");

        player.awardAchievement(Achievement.OVERKILL);

        player.playEffect(null, Effect.GHAST_SHOOT, cmd);

        player.throwSnowball();

        return false;

        }
    }
