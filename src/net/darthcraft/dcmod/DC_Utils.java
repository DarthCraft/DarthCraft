package net.darthcraft.dcmod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static net.darthcraft.dcmod.DarthCraft.mySQL;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.darthcraft.dcmod.player.NameFetcher;
import net.darthcraft.dcmod.player.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DC_Utils
{

    public static final List<String> HOSTS = Arrays.asList("pbgben", "DarthSalamon", "KickAssScott", "Wild1145");
    public static final List<String> HEADADMINS = Arrays.asList("JabbaTheJake", "boulos");
    public static ArrayList<String> DOOMHAMMERS = new ArrayList<>();

    private final DarthCraft plugin;
    private final Server server;

    public DC_Utils(DarthCraft plugin)
    {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    public void adminAction(String admin, String action, ChatColor color)
    {
        server.broadcastMessage(color + admin + " - " + action);
    }

    public void adminAction(CommandSender sender, String action)
    {
        adminAction(sender.getName(), action, ChatColor.RED);
    }

    @Deprecated
    public void msg(CommandSender sender, String message)
    {
        sender.sendMessage(ChatColor.YELLOW + message);

    }
    
    public static ItemStack getDoomHammer()
    {
        ItemStack banhammer = new ItemStack(Material.GOLD_AXE, 1);
        banhammer.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        ItemMeta banhammermeta = banhammer.getItemMeta();
        banhammermeta.setLore(Arrays.asList(DC_Messages.ABDUCT_HAMMER));
        banhammermeta.setDisplayName(ChatColor.RED + "Stealer!");
        banhammer.setItemMeta(banhammermeta);
        return banhammer;
    }

    public ChatColor getChatColor(Player player)
    {
        if (PermissionUtils.hasPermission(player, Permission.HOST))
        {
            return ChatColor.LIGHT_PURPLE;
        }

        if (PermissionUtils.hasPermission(player, Permission.ADMIN))
        {
            return ChatColor.GOLD;
        }

        if (PermissionUtils.hasPermission(player, Permission.MEMBER))
        {
            return ChatColor.WHITE;
        }

        return ChatColor.GRAY;
    }

    public static String decolorize(String string)
    {
        return string.replaceAll("\\u00A7(?=[0-9a-fk-or])", "&");
    }

    public static String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void sendSyncMessage(final CommandSender sendTo, final String message)
    {
        Bukkit.getScheduler().runTask(plugin, new Runnable()
                              {
                                  @Override
                                  public void run()
                                  {
                                      sendTo.sendMessage(message);
                                  }
        });
    }

    public UUID playerToUUID(String player)
    {
        UUID playerID = null;
        try
        {
            playerID = UUIDFetcher.getUUIDOf(player);
        }
        catch (Exception ex)
        {
        }
        return playerID;
    }

    public UUID playerToUUID(Player player)
    {
        UUID playerID = null;
        try
        {
            playerID = UUIDFetcher.getUUIDOf(player.getName());
        }
        catch (Exception ex)
        {
        }
        return playerID;
    }

    public String UUIDToPlayer(UUID uuid)
    {
        NameFetcher fetcher = new NameFetcher(Arrays.asList(uuid));
        Map<UUID, String> response = null;

        try
        {
            response = fetcher.call();
        }
        catch (Exception e)
        {
        }

        String playerName = response.get(uuid);

        return playerName;
    }

    public static void updateDatabase(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        statement.executeUpdate(SQLquery);
    }

    public void getValueFromDB(String SQLquery) throws SQLException
    {
        Connection c = mySQL.openConnection();
        Statement statement = c.createStatement();
        ResultSet res = statement.executeQuery(SQLquery);
        res.next();
    }
}
