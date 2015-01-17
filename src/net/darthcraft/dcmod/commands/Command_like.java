package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Source(SourceType.PLAYER)
@Permissions(Permission.MEMBER)
public class Command_like extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        if (args.length == 0)
        {
            plugin.likeSigns.like((Player) sender);
            return true;
        }

        if (args.length != 1)
        {
            return showUsage(cmd);
        }

        if (args[0].equals("amount"))
        {
            msg(ChatColor.GREEN + "There are " + ChatColor.DARK_RED.toString() + plugin.likeSigns.getLikers().size() + ChatColor.GREEN + " players who have DarthCraft!");
            msg(ChatColor.GREEN + (plugin.likeSigns.getLikers().contains(sender.getName()) ? "You liked DarthCraft, thanks!" + ChatColor.LIGHT_PURPLE + "<3"
                                   : "You haven't liked DarthCraft yet! To like try /warp like"));
            return true;
        }

        if (args[0].equals("set"))
        {
            if (!PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
            {
                return noPerms();
            }

            final Block block = ((Player) sender).getTargetBlock(null, 10);
            if (block == null || !(block.getState() instanceof Sign))
            {
                return warn("You have to be looking at a sign");
            }

            if (plugin.likeSigns.getSigns().contains(block.getLocation()))
            {
                return warn("That is already a LikeSign");
            }

            plugin.likeSigns.getSigns().add(block.getLocation());
            plugin.likeSigns.updateSign(block.getLocation());
            plugin.likeSigns.saveSettings();
            return msg("LikeSign set");
        }

        if (args[0].equals("remove"))
        {
            if (!PermissionUtils.hasPermission(sender, Permission.HEADADMIN))
            {
                return noPerms();
            }

            final Block block = ((Player) sender).getTargetBlock(null, 10);
            if (block == null || !(block.getState() instanceof Sign))
            {
                return warn("You have to be looking at a sign");
            }

            if (!plugin.likeSigns.getSigns().contains(block.getLocation()))
            {
                return warn("That is not a LikeSign");
            }

            plugin.likeSigns.getSigns().remove(block.getLocation());
            plugin.likeSigns.saveSettings();

            final Sign sign = (Sign) block.getState();
            sign.setLine(0, ChatColor.LIGHT_PURPLE + "<3");
            sign.setLine(1, "");
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();

            return msg("LikeSign removed");
        }

        return showUsage(cmd);
    }
}
