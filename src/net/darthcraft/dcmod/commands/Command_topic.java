package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permissions.Permission.ANYONE)
public class Command_topic extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {

        sender.sendMessage(plugin.topicsConfig.getString("topicmessage") + " " + plugin.topicGenerator.getTopic());
        return true;
        }

    }
