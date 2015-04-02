package net.darthcraft.dcmod.addons;

import java.util.HashSet;
import net.darthcraft.dcmod.DC_Utils;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbductionHammer extends DarthCraftAddon
{

    public AbductionHammer(DarthCraft plugin)
    {
        super(plugin);
    }

    public void teleport(World world, Player player, int x, int y, int z)
    {
        player.teleport(new Location(world, x, y, z));
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event)
    {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null)
        {
            return;
        }
        Entity e = null;
        if (item.equals(DC_Utils.getDoomHammer()) && DC_Utils.DOOMHAMMERS.contains(player.getName()))
        {
            for (Block block : player.getLineOfSight((HashSet<Byte>) null, 50))
            {
                Location loc2 = block.getLocation();
                for (LivingEntity entity : player.getWorld().getLivingEntities())
                {
                    if (entity.getLocation().distance(loc2) <= 2 && !entity.equals(player))
                    {
                        e = entity;
                    }
                }
            }
            if (e instanceof Player)
            {
                final Player eplayer = (Player) e;
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        eplayer.getWorld().createExplosion(eplayer.getLocation().getX(), eplayer.getLocation().getY(), eplayer.getLocation().getZ(), 0f, false, false);
                        eplayer.getWorld().strikeLightningEffect(eplayer.getLocation());
                        teleport(eplayer.getWorld(), eplayer, -247, 170, -525);
                        teleport(eplayer.getWorld(), eplayer, -247, 170, -525);
                    }
                }.runTaskLater(plugin, 20L * 2L);

            }
            else
            {
                if (e instanceof LivingEntity)
                {
                    final LivingEntity le = (LivingEntity) e;
                    le.setVelocity(le.getVelocity().add(new Vector(0, 3, 0)));
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            le.getWorld().createExplosion(le.getLocation().getX(), le.getLocation().getY(), le.getLocation().getZ(), 5f, false, false);
                            le.getWorld().strikeLightningEffect(le.getLocation());
                            le.setHealth(0d);
                        }
                    }.runTaskLater(plugin, 20L * 2L);

                }
            }
            event.setCancelled(true);
        }
    }

}
