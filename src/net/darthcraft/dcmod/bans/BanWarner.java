package net.darthcraft.dcmod.bans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BanWarner extends DarthCraftAddon
    {

    public BanWarner(DarthCraft plugin)
        {
        super(plugin);
        }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
        {
        getFishbansRunnable(event.getPlayer()).runTaskAsynchronously(plugin);
        }

    public URL getUrl(Player player)
        {
        try
            {
            return new URL("http://api.fishbans.com/stats/" + player.getName());
            }
        catch (MalformedURLException ex)
            {
            logger.warning("Could not generate fishbans URL for " + player.getName());
            return null;
            }
        }

    public BukkitRunnable getFishbansRunnable(final Player player)
        {
        return new BukkitRunnable()
            { // 
            @Override
            public void run()
                {
                final URL url = getUrl(player);
                final JSONObject json;

                try
                    {
                    final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    json = (JSONObject) JSONValue.parse(in.readLine());
                    in.close();
                    }
                catch (Exception ex)
                    {
                    logger.severe("Error fetching fishbans information from " + url.getHost());
                    logger.severe(ex);
                    return;
                    }

                logger.debug(url);
                logger.debug(json);

                getWarnRunnable(json).runTask(plugin);
                }
            };
        }

    public BukkitRunnable getWarnRunnable(final JSONObject object)
        {
        return new BukkitRunnable()
            { // Sync
            @Override
            public void run()
                {
                try
                    {
                    if (object.get("success").equals(false))
                        {
                        logger.warning("Fishbans returned success: false");
                        logger.warning(object.get("error"));
                        return;
                        }

                    final JSONObject stats = (JSONObject) object.get("stats");

                    if (stats.get("totalbans").equals(0L))
                        { // User was never banned
                        return;
                        }

                    plugin.adminChat.sendAdminMessage("BanWarner", ChatColor.RED + "Warning: "
                                                                   + stats.get("username") + " has been banned " + stats.get("totalbans") + " times!");

                    final JSONObject services = (JSONObject) stats.get("service");

                    for (Object service : services.keySet())
                        {
                        if (services.get(service).equals(0L))
                            {
                            continue;
                            }

                        plugin.adminChat.sendAdminMessage("BanWarner", ChatColor.RED + "Warning: "
                                                                       + services.get(service) + " times on " + service);
                        }

                    }
                catch (Exception ex)
                    {
                    logger.severe("Error parsing fishbans JSON: " + object);
                    logger.severe(ex);
                    }
                }
            };
        }
    }
