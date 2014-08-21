package net.darthcraft.dcmod.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permissions
    {

    Permission value() default Permission.MEMBER;

    public static enum Permission
        {

        // Permission levels
        ANYONE(""),
        GUEST("darthcraft.guest"),
        MEMBER("darthcraft.member"),
        LEGACY("darthcraft.legacy"),
        PREMIUM("darthcraft.premium"),
        ADMIN("darthcraft.admin"),
        HEADADMIN("darthcraft.headadmin"),
        HOST("darthcraft.host");
        private String permission;

        public String getPermission()
            {
            return permission;
            }

        private Permission(String permission)
            {
            this.permission = permission;
            }
        }

    public static class PermissionUtils
        {

        public static boolean hasPermission(CommandSender sender, Permission permissionLevel)
            {
            if (!(sender instanceof Player) || permissionLevel == Permission.ANYONE)
                {
                return true;
                }

            return sender.hasPermission(permissionLevel.getPermission());
            }

        public static boolean hasPermission(CommandSender sender, Class<? extends DarthCraftCommand> commandClass, DarthCraft plugin)
            {
            Permissions permissions;
            try
                {
                permissions = commandClass.getAnnotation(Permissions.class);
                }
            catch (NullPointerException e)
                {
                plugin.logger.warning("Command " + commandClass.getName() + " doesn't have permissions set!");
                return true;
                }

            return hasPermission(sender, permissions.value());
            }
        }
    }
