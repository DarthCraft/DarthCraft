package net.darthcraft.dcmod.listeners;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerListener implements Listener
{

    private final DarthCraft plugin;

    public PlayerListener(DarthCraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(final AsyncPlayerChatEvent event)
    {
        plugin.trollMode.onPlayerChat(event);
        plugin.adminChat.onPlayerChat(event);
        plugin.adminBusy.onPlayerChat(event);
        plugin.chatFilter.onPlayerChat(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.playerManager.onPlayerQuit(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        plugin.backup.onPlayerLogin(event);
        plugin.forceIp.onPlayerLogin(event);
        plugin.banManager.onPlayerLogin(event);
        plugin.permBan.onPlayerLogin(event);
        plugin.voteToPlay.onPlayerLogin(event);
        
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {
        plugin.playerManager.onUncancelledPlayerJoin(event);
        plugin.banWarner.onUncancelledPlayerJoin(event);
        plugin.loginTitles.onUncancelledPlayerJoin(event);
        plugin.motd.onUncancelledPlayerJoin(event);
        plugin.taxSystem.onUncancelledPlayerJoin(event);
        
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        plugin.likeSigns.onPlayerInteractEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        plugin.tabColors.onPlayerJoinEvent(event);
        plugin.visitorCounter.onPlayerJoinEvent(event);
      //  plugin.playerSource.onPlayerJoinEvent(event);
        
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event)
    {
        plugin.abductionHammer.onPlayerUseItem(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerPing(ServerListPingEvent event)
    {
       // plugin.motd.onServerPing(event);
    }
}
