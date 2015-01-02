package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.DC_Utils;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permissions.Permission.ANYONE)
public class Command_topic extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {

        sender.sendMessage(DC_Utils.colorize("&8[&4DarthCraft Plugin&8] " + plugin.topicsConfig.getString("topicmessage") + " " + ChatColor.DARK_AQUA + plugin.topicGenerator.getTopic()));
        return true;
        }

    }
