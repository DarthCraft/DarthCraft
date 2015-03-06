package net.darthcraft.dcmod.addons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager;
import net.pravian.bukkitlib.serializable.SerializableBlockLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LikeSigns extends DarthCraftAddon
{

    private final List<Location> signs;
    private final List<String> likers;

    public LikeSigns(DarthCraft plugin)
    {
        super(plugin);
        this.signs = new ArrayList<>();
        this.likers = new ArrayList<>();
    }

    public void loadSettings()
    {
        signs.clear();
        likers.clear();

        for (String sign : plugin.likersConfig.getStringList("signs"))
        {
            final SerializableBlockLocation location = new SerializableBlockLocation(sign);
            if (location.deserialize() == null)
            {
                logger.log(Level.WARNING, "Could not load LikeSign; Invalid sign: {0}", sign);
                continue;
            }

            signs.add(location.deserialize());
        }

        likers.addAll(plugin.likersConfig.getStringList("likers"));
        updateSigns();
    }

    public void saveSettings()
    {
        final List<String> signLocations = new ArrayList<>();
        for (Location sign : signs)
        {
            final SerializableBlockLocation location = new SerializableBlockLocation(sign);
            if (location.serialize() == null)
            {
                logger.log(Level.WARNING, "Could not save LikeSign; Invalid sign: {0}", sign);
                continue;
            }
            signLocations.add(location.serialize());
        }

        plugin.likersConfig.set("signs", signLocations);
        plugin.likersConfig.set("likers", likers);

        plugin.likersConfig.save();
    }

    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }

        if (!signs.contains(event.getClickedBlock().getLocation()))
        {
            return;
        }

        like(player);
    }

    public void onUncancelledBlockBreakEvent(BlockBreakEvent event)
    {
        if (!signs.contains(event.getBlock().getLocation()))
        {
            return;
        }

        signs.remove(event.getBlock().getLocation());
        saveSettings();

        util.msg(event.getPlayer(), "LikeSign removed");
    }

    public void updateSigns()
    {
        for (Location location : signs)
        {
            updateSign(location);
        }
    }

    public void updateSign(Location location)
    {
        final Block block = location.getBlock();
        if (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN)
        {
            logger.debug("Set mat");
            block.setType(Material.SIGN_POST);
        }

        final Sign sign = (Sign) block.getState();
        sign.setLine(0, ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Like" + ChatColor.DARK_BLUE + "]");
        sign.setLine(1, ChatColor.DARK_PURPLE + "Darth" + ChatColor.LIGHT_PURPLE + "Craft");
        sign.setLine(2, ChatColor.DARK_GRAY + "---");
        sign.setLine(3, "Likers: " + ChatColor.DARK_RED + likers.size());
        sign.update();
    }

    public void like(Player player)
    {
        String uuid = player.getUniqueId().toString();

        if (likers.contains(uuid))
        {
            util.msg(player, ChatColor.GREEN + "You've already liked DarthCraft, thanks! " + ChatColor.LIGHT_PURPLE + "<3");
            return;
        }

        likers.add(uuid);

        saveSettings();
        updateSigns();

        util.msg(player, ChatColor.GREEN + "Thanks for liking DarthCraft!");
        server.broadcastMessage(ChatColor.GREEN + player.getName() + " received 100D for liking DarthCraft!");
        server.broadcastMessage(ChatColor.GREEN + "Want to like DarthCraft too? Visit " + ChatColor.GRAY + "/warp like");
        server.dispatchCommand(server.getConsoleSender(), "eco give " + player.getName() + " 100");
    }

    public List<String> getLikers()
    {
        return likers;
    }

    public List<Location> getSigns()
    {
        return signs;
    }
}
