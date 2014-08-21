package net.darthcraft.dcmod.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import java.util.Date;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager.PlayerInfo;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CustomListener implements Listener
    {

    private final DarthCraft plugin;
    private final Server server;

    public CustomListener(DarthCraft plugin)
        {
        this.plugin = plugin;
        this.server = plugin.getServer();
        }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent event)
        {
        final Vote vote = event.getVote();

        final OfflinePlayer player = PlayerUtils.getOfflinePlayer(vote.getUsername());
        if (player == null)
            {
            return;
            }

        if (!player.hasPlayedBefore())
            {
            return;
            }

        final PlayerInfo info = plugin.playerManager.getInfo(player);
        if (!info.exists())
            {
            return;
            }

        info.load();
        info.setVotes(info.getVotes() + 1);
        info.setLastVote(new Date());
        info.save();

        server.broadcastMessage(ChatColor.GREEN + player.getName() + " voted for DarthCraft on " + vote.getServiceName());
        server.broadcastMessage(ChatColor.GREEN + player.getName() + " received 25D for voting");

        plugin.getServer().dispatchCommand(server.getConsoleSender(), "eco give " + player.getName() + " 25");

        if (!player.isOnline())
            {
            return;
            }

        plugin.util.msg(player.getPlayer(), ChatColor.DARK_AQUA + "Thanks for voting!" + ChatColor.LIGHT_PURPLE + "<3");
        plugin.util.msg(player.getPlayer(), ChatColor.DARK_AQUA + "You have voted " + ChatColor.RED + info.getVotes() + ChatColor.DARK_AQUA + " times!");

        if (info.getVotes() % 3 == 0)
            {
            plugin.util.msg(player.getPlayer(), ChatColor.BLUE + "Here, have some diamonds cake for all that hard work.");
            player.getPlayer().getInventory().addItem(new ItemStack(Material.CAKE, 1));
            }
        }
    }
