package net.darthcraft.dcmod.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.logging.Level;
import net.darthcraft.dcmod.DC_Messages;
import net.pravian.bukkitlib.command.SourceType;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Retention(RetentionPolicy.RUNTIME)
public @interface Source
{

    SourceType value();

    public static class SourceUtils
    {

        public static boolean fromSource(CommandSender sender, SourceType source)
        {
            if (source == SourceType.ANY)
            {
                return true;
            }

            return (source == SourceType.PLAYER ? sender instanceof Player : !(sender instanceof Player));
        }

        public static boolean fromSource(CommandSender sender, Class<? extends DarthCraftCommand> commandClass, DarthCraft plugin)
        {
            Source source;
            try
            {
                source = commandClass.getAnnotation(Source.class);
            }
            catch (NullPointerException ex)
            {
                plugin.logger.log(Level.WARNING, DC_Messages.NO_COMMAND_SOURCE, commandClass.getName());
                return true;
            }

            return fromSource(sender, source.value());
        }
    }
}
