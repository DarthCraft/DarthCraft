package net.darthcraft.dcmod.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import java.util.Date;
import java.util.Random;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.player.PlayerManager.PlayerInfo;
import net.pravian.bukkitlib.util.LoggerUtils;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
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

        Bukkit.broadcastMessage(ChatColor.GREEN + "A Thank you goes to " + player.getName() + " who has just voted for the server on " + ChatColor.DARK_PURPLE + vote.getServiceName().toLowerCase() + ChatColor.GREEN + " and has recived some money as a reward");

        plugin.getServer().dispatchCommand(server.getConsoleSender(), "eco give " + player.getName() + " 75");

        // Random Reward Thing
        Random random = new Random();
        int number = random.nextInt(100);

        Random subrandom = new Random();
        int subrandomnum = subrandom.nextInt(3);

        if (number < 30)
        {
            LoggerUtils.info(player + " has voted but got nothing cool and extra :(");
            player.getPlayer().sendMessage(ChatColor.DARK_RED + "Sorry :( You got nothing cool or extra this time...");
        }
        else if (number <= 30 && number < 55)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 5));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has recieved 3 cooked steak as a random reward for voting. Congratulations. ");
        }
        else if (number <= 55 && number < 57)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.DIRT, 1));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won nothing of value. They can have this dirt as a sign of condolence");
        }
        else if (number <= 57 && number < 60)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK, 1));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won an ehcnanted book. If this will work properly, nobody knows, but we may we well try!");
        }
        else if (number <= 60 && number < 65)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
            player.getPlayer().awardAchievement(Achievement.DIAMONDS_TO_YOU);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won something of vague value. THEY HAS A DIAMOND!");
        }
        else if (number <= 65 && number < 75)
        {
            if (subrandomnum == 0)
            {
                player.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won something... That something just happens to be Iron Leggins, so have fun!");
            }
            else if (subrandomnum == 1)
            {
                player.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_BOOTS, 1));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won something... That something just happens to be Iron BOOTS!!! So have fun!");
            }
            else if (subrandomnum == 2)
            {
                player.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won something... That something just happens to be an Iron Chestplate. May the odds agaisnt the mobs be ever in your favor.!");
            }
            else if (subrandomnum == 3)
            {
                player.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_HELMET, 1));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won something... That something just happens to be an Iron Helmet. Its better than tin foil, but cant quite pick up the local wi-fi :(");
            }
            else
            {
                LoggerUtils.severe(player.getName() + " should have recieved a reward, but some shit broke... Contact Wild.");
                player.getPlayer().sendMessage(ChatColor.DARK_RED + "Sorry, but something has gone haywire. Please contact an admin and ask them to inform Wild.");
            }

        }
        else if (number <= 75 && number > 80)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.EMERALD, 1));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won the very presegious (Or however that word should be spelt, I dont know) thingie.");
        }
        else if (number <= 80 && number > 90)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.PUMPKIN_PIE, 5));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won " + Math.PI);
        }
        else if (number <= 90 && number > 100)
        {
            player.getPlayer().getInventory().addItem(new ItemStack(Material.LOG, 16));
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + " has voted and has won some sort of log... But again, I have no idea what type of logs");
        }

        if (!player.isOnline())
        {
            return;
        }

        plugin.util.msg(player.getPlayer(), ChatColor.DARK_AQUA + "Thanks for voting!" + ChatColor.LIGHT_PURPLE + "<3");
        plugin.util.msg(player.getPlayer(), ChatColor.DARK_AQUA + "You have voted " + ChatColor.RED + info.getVotes() + ChatColor.DARK_AQUA + " times!");

        if (info.getVotes() % 3 == 0)
        {
            plugin.util.msg(player.getPlayer(), ChatColor.BLUE + "Here, have some cake for all that hard work.");
            player.getPlayer().getInventory().addItem(new ItemStack(Material.CAKE, 1));
        }
    }
}
