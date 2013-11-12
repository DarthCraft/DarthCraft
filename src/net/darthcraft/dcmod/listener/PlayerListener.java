package net.darthcraft.dcmod.listener;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final DarthCraft plugin;

    public PlayerListener(DarthCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        plugin.trollMode.onPlayerChat(event);
        plugin.chatFilter.onPlayerChat(event);
        plugin.adminChat.onPlayerChat(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.playerManager.onPlayerQuit(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        plugin.forceIp.onPlayerLogin(event);
        plugin.banManager.onPlayerLogin(event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onUncancelledPlayerLogin(PlayerLoginEvent event) {
        plugin.playerManager.onUncancelledPlayerLogin(event);
    }
}
