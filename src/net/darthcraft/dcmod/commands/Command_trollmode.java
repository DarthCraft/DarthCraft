package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.CONSOLE)
@Permissions(Permission.HEADADMIN)
public class Command_trollmode extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {
        plugin.trollMode.toggleTrollMode();
        return false;
        }
    }
