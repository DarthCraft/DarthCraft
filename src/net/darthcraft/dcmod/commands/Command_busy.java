package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(value = SourceType.ANY)
@Permissions(value = Permissions.Permission.ADMIN)
public class Command_busy extends DarthCraftCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player)) {
            return warn("Only in-game players can toggle adminchat.");
        }

        plugin.adminBusy.toggleBusyStatus((Player) sender);
        return true;
    }
}
