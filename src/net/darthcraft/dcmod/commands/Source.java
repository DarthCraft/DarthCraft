package net.darthcraft.dcmod.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
                plugin.logger.warning("Command " + commandClass.getName() + " doesn't have a command-source set!");
                return true;
                }

            return fromSource(sender, source.value());
            }
        }
    }
