package net.darthcraft.dcmod.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class Command_topic extends DarthCraftCommand {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args) {
        
        sender.sendMessage("Your Random Topic is: " + plugin.topicGenerator.getTopic());
        return true;
    }
    
    
    
}
