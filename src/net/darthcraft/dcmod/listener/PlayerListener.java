package net.darthcraft.dcmod.listener;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final DarthCraft plugin;

    public PlayerListener(DarthCraft plugin) {
        this.plugin = plugin;
    }
    
    

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        plugin.trollMode.onPlayerChat(event);
        plugin.adminChat.onPlayerChat(event);
        plugin.adminBusy.onPlayerChat(event);
        plugin.chatFilter.onPlayerChat(event);
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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event) {
        plugin.playerManager.onUncancelledPlayerJoin(event);
        plugin.banWarner.onUncancelledPlayerJoin(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        plugin.likeSigns.onPlayerInteractEvent(event);
    }
    
    // Player Tab colours.
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (Util.HOSTS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
        }
        if (Util.HEADADMINS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
        }
    }
    
}
