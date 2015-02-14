package net.darthcraft.dcmod.commands;

import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_skull extends DarthCraftCommand
{

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
    {
        String owner;
        final Player player = (Player) sender;

        if (args.length > 0)
        {
            owner = args[0];
        }
        else
        {
            owner = sender.getName();
        }

        ItemStack itemSkull = player.getItemInHand();
        SkullMeta metaSkull = null;
        boolean spawn = false;

        if (itemSkull != null && itemSkull.getType() == Material.SKULL_ITEM && itemSkull.getDurability() == 3)
        {
            metaSkull = (SkullMeta) itemSkull.getItemMeta();
        }
        else
        {
            itemSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            metaSkull = (SkullMeta) itemSkull.getItemMeta();
            spawn = true;
        }
        metaSkull.setDisplayName("§fSkull of " + owner);
        metaSkull.setOwner(owner);
        itemSkull.setItemMeta(metaSkull);

        return false;

    }
}
