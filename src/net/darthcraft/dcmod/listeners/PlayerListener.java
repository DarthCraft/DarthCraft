package net.darthcraft.dcmod.listeners;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.DC_Utils;
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
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;

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
        Player player = event.getPlayer();

        plugin.forceIp.onPlayerLogin(event);
        plugin.banManager.onPlayerLogin(event);

        // Hard Coded Perm-Ban
        if (player.getUniqueId().toString().equals("76093c08-4054-4a54-95c5-961bcfa768c6"))
            {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "FlamedBulletLuke, May I congratulate you on being the first person to be hard-coded into the DarthCraftMod's permban system. I hope you think carefully before threatening this server.");
            }
        }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
        {
        plugin.playerManager.onUncancelledPlayerJoin(event);
        plugin.banWarner.onUncancelledPlayerJoin(event);
        plugin.loginTitles.onUncancelledPlayerJoin(event);
        }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event)
        {
        plugin.likeSigns.onPlayerInteractEvent(event);
        }

    // Player Tab colours.
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerJoinEvent(PlayerJoinEvent event)
        {
        Player player = event.getPlayer();
        if (DC_Utils.HOSTS.contains(player.getName()) || DC_Utils.HEADADMINS.contains(player.getName()))
            {
            player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            }

        else if (PermissionUtils.hasPermission(player, Permission.ADMIN))
            {
            player.setPlayerListName(ChatColor.RED + player.getName());
            }

        else if (PermissionUtils.hasPermission(player, Permission.PREMIUM))
            {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
            }

        }

    }
