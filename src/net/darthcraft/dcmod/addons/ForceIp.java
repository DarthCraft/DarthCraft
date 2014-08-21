package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.pravian.bukkitlib.util.ChatUtils;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class ForceIp extends DarthCraftAddon
    {

    public boolean enabled;
    public String hostname;
    public int port;
    public String kickMessage;

    public ForceIp(DarthCraft plugin)
        {
        super(plugin);
        }

    public void loadSettings()
        {
        this.enabled = plugin.mainConfig.getBoolean("forceip.enabled");
        this.hostname = plugin.mainConfig.getString("forceip.hostname");
        this.port = plugin.mainConfig.getInt("forceip.port");
        this.kickMessage = ChatUtils.colorize(plugin.mainConfig.getString("forceip.kickmessage"));
        }

    public void onPlayerLogin(PlayerLoginEvent event)
        {
        if (!enabled)
            {
            return;
            }

        plugin.logger.debug("Using: " + event.getHostname());

        if ((!event.getHostname().equalsIgnoreCase(hostname + ":" + port)))
            {
            event.disallow(Result.KICK_OTHER, kickMessage);
            }

        }
    }
