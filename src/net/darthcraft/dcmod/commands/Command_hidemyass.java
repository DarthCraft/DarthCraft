package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(value = SourceType.PLAYER)
@Permissions(value = Permissions.Permission.ADMIN)
public class Command_hidemyass extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("You must be a player to execute that command!");
            return true;
        }
        Player player = (Player) sender;

        if (player.getGameMode() != GameMode.SPECTATOR)
        {
            player.setGameMode(GameMode.SPECTATOR);
        }
        else
        {
            player.setGameMode(GameMode.SURVIVAL);
        }

        return true;

    }
}
