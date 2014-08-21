package net.darthcraft.dcmod.bans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.darthcraft.dcmod.bans.Ban;
import net.darthcraft.dcmod.bans.Ban.BanType;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.IpUtils;
import net.pravian.bukkitlib.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scheduler.BukkitRunnable;

public class BanManager extends DarthCraftAddon
    {

    private final YamlConfig bansconfig;
    private final List<Ban> bans;

    public BanManager(DarthCraft plugin)
        {
        super(plugin);
        this.bans = new ArrayList<Ban>();
        this.bansconfig = plugin.bansConfig;
        }

    public void loadBans()
        {

        // Player bans
        ConfigurationSection section = bansconfig.getConfigurationSection("UUID");
        if (section != null)
            {
            for (String player : section.getKeys(false))
                {

                try
                    {
                    ConfigurationSection cs = bansconfig.getConfigurationSection("UUID." + player);

                    Ban ban = new Ban();
                    ban.setType(BanType.UUID);
                    ban.setName(player.toLowerCase());
                    ban.setBy(cs.getString("by"));
                    ban.setReason(cs.getString("reason"));
                    ban.setExpiryDate(TimeUtils.parseString(cs.getString("expires")));
                    ban.addIps(cs.getStringList("ips"));

                    bans.add(ban);

                    }
                catch (Exception e)
                    {
                    logger.warning("Could not load player ban: " + player + "!");
                    }

                }
            }

        // Ip bans
        section = bansconfig.getConfigurationSection("ips");
        if (section != null)
            {
            for (String ip : section.getKeys(false))
                {

                try
                    {
                    ConfigurationSection cs = bansconfig.getConfigurationSection("ips." + ip);

                    Ban ban = new Ban();
                    ban.setType(BanType.IP);
                    ban.addIp(IpUtils.fromEscapedString(ip));
                    ban.setBy(cs.getString("by"));
                    ban.setReason(cs.getString("reason"));
                    ban.setExpiryDate(TimeUtils.parseString(cs.getString("expires")));

                    bans.add(ban);

                    }
                catch (Exception e)
                    {
                    logger.warning("Could not load IP-ban: " + ip);
                    }

                }
            }

        removeExpired();

        logger.info("Loaded " + bans.size() + " bans");
        }

    public void saveBans()
        {
        if (bans.isEmpty())
            {
            return;
            }

        for (Ban ban : bans)
            {
            ban.saveTo(bansconfig);
            }

        logger.info("Saved " + bans.size() + " bans");
        }

    public List<Ban> getBans()
        {
        return bans;
        }

    public void onPlayerLogin(PlayerLoginEvent event)
        {

        if (event.getResult() != Result.ALLOWED)
            {
            return;
            }

        final String name = event.getPlayer().getName();
        final String ip = IpUtils.getIp(event);

        try
            {
            boolean expiredBans = false;
            Ban matchingBan = null;

            for (Ban ban : bans)
                {

                if (ban.isExpired())
                    {
                    expiredBans = true;
                    continue;
                    }

                if (ban.getIps().contains(ip))
                    {
                    matchingBan = ban;
                    break;
                    }

                if (name.equalsIgnoreCase(ban.getName()))
                    {
                    matchingBan = ban;
                    break;
                    }

                }

            if (expiredBans)
                {
                new BukkitRunnable()
                    {
                    @Override
                    public void run()
                        {
                        removeExpired();
                        }
                    }.runTaskLater(plugin, 40L);
                }

            if (matchingBan == null)
                {
                return;
                }

            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, matchingBan.getKickMessage());

            }
        catch (Exception ex)
            {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.GOLD
                                                               + "Sorry! " + ChatColor.AQUA + "There was a problem checking if you were banned.\n"
                                                               + "Please report this issue at http://darthcraft.net/forums");
            logger.severe("Problem checking ban status for " + name);
            logger.severe(ex);
            }
        }

    public void removeExpired()
        {
        final List<Ban> toUnban = new ArrayList<Ban>();

        for (Ban ban : bans)
            {
            if (ban.isExpired())
                {
                toUnban.add(ban);
                }
            }

        for (Ban ban : toUnban)
            {
            plugin.logger.info("Removing expired ban: " + ban.getTarget());
            unban(ban);
            }
        }

    public void ban(Ban ban)
        {
        if (isBanned(ban))
            {
            logger.warning("Not banning " + ban.getTarget() + ": already banned!");
            return;
            }

        bans.add(ban);
        ban.saveTo(bansconfig);
        }

    public void unban(Ban ban)
        {
        if (!isBanned(ban))
            {
            logger.warning("Not unbanning " + ban.getTarget() + ": not banned!");
            return;
            }

        bans.remove(ban);
        ban.deleteFrom(bansconfig);

        // Player bans can also contain IPs
        if (ban.getType() == BanType.IP)
            {
            for (Ban currentBan : bans)
                {

                if (ban.getType() == BanType.UUID)
                    {
                    return;
                    }

                if (!currentBan.containsIp(ban.getIp()))
                    {
                    currentBan.removeIp(ban.getIp());
                    currentBan.saveTo(bansconfig);
                    }
                }
            }
        }

    public void update(Ban ban)
        {

        for (Ban currentBan : bans)
            {
            if (!currentBan.getTarget().equalsIgnoreCase(ban.getTarget()))
                {
                continue;
                }

            currentBan.setBy(ban.getBy());
            if (ban.hasIps())
                {
                currentBan.clearIps();
                currentBan.addIps(ban.getIps());
                }
            currentBan.setExpiryDate(ban.getExpiryDate());

            if (currentBan.getType() == BanType.UUID)
                {
                currentBan.setName(ban.getName());
                }

            currentBan.saveTo(bansconfig);
            return;
            }

        }

    public boolean isBanned(Ban ban)
        {
        final String target = ban.getTarget();
        final BanType type = ban.getType();

        for (Ban currentBan : bans)
            {
            if (currentBan.getType() == type && currentBan.getTarget().equalsIgnoreCase(target))
                {
                return true;
                }
            }
        return false;

        }

    public boolean isIpBanned(String ip)
        {
        return getIpBan(ip) != null;
        }

    public boolean isNameBanned(String name)
        {
        return getNameBan(name) != null;
        }

    public Ban getIpBan(String ip)
        {
        ip = ip.trim();

        for (Ban ban : bans)
            {
            if (ban.getType() != BanType.IP)
                {
                continue;
                }

            if (ban.getIp().equals(ip))
                {
                return ban;
                }
            }

        for (Ban ban : bans)
            {
            if (ban.getType() != BanType.UUID)
                {
                continue;
                }

            if (ban.getIps().contains(ip))
                {
                return ban;
                }
            }
        return null;
        }

    public Ban getNameBan(String name)
        {
        name = name.toLowerCase().trim();

        for (Ban ban : bans)
            {
            if (ban.getType() != BanType.UUID)
                {
                continue;
                }

            if (ban.getName().equalsIgnoreCase(name))
                {
                return ban;
                }
            }

        for (Ban ban : bans)
            {
            if (ban.getType() != BanType.UUID)
                {
                continue;
                }

            if (ban.getName().toLowerCase().startsWith(name))
                {
                return ban;
                }
            }
        return null;
        }
    }
