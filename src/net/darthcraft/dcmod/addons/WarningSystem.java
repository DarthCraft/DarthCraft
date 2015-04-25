package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.BanManager;
import net.darthcraft.dcmod.player.PlayerManager;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class WarningSystem extends DarthCraftAddon
{

    public WarningSystem(DarthCraft plugin)
    {
        super(plugin);
    }

    /**
     * This will execute a warning check to see how many points the player has,
     * and then take the appropriate action / sanction against the player.
     *
     * @param target
     */
    public void warningCheck(OfflinePlayer target)
    {
        final PlayerManager.PlayerInfo info = plugin.playerManager.getInfo(target);

        // Checks if they have less than zero warnings and will set it to 0.
        if (info.getWarnings() < 0)
        {
            info.setWarnings(0);
        }

        // The actual warning system thingie.
        if (info.getWarnings() > 5 && info.getWarnings() < 10)
        {
            if (info.getWarningLevel() != 1)
            {
                if(target.isOnline())
                {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "kick " + target + " \"You have been kicked for reaching 10 or more warning points. Please be more careful when playing here on DarthCraft.");
                }
                info.setWarningLevel(1);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a kick.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 15 && info.getWarnings() < 25)
        {
            if (info.getWarningLevel() != 2)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 12h");
                info.setWarningLevel(2);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 12 hour ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 25 && info.getWarnings() < 30)
        {
            if (info.getWarningLevel() != 3)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 24h");
                info.setWarningLevel(3);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 24 hour ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 30 && info.getWarnings() < 50)
        {
            if (info.getWarningLevel() != 4)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 3d");
                info.setWarningLevel(4);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 3 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }
        if (info.getWarnings() >= 50 && info.getWarnings() < 70)
        {
            if (info.getWarningLevel() != 5)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 7d");
                info.setWarningLevel(5);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 7 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 70 && info.getWarnings() < 100)
        {
            if (info.getWarningLevel() != 5)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 14d");
                info.setWarningLevel(5);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 14 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 100 && info.getWarnings() < 120)
        {
            if (info.getWarningLevel() != 6)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 28d");
                info.setWarningLevel(6);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 28 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 120 && info.getWarnings() < 150)
        {
            if (info.getWarningLevel() != 6)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 42d");
                info.setWarningLevel(6);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 42 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 150 && info.getWarnings() < 180)
        {
            if (info.getWarningLevel() != 7)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 84d");
                info.setWarningLevel(7);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 84 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() >= 180 && info.getWarnings() < 200)
        {
            if (info.getWarningLevel() != 8)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "tempban " + target + " 168d");
                info.setWarningLevel(8);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a 168 days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }

        if (info.getWarnings() <= 200)
        {
            if (info.getWarningLevel() != 9)
            {
                Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "ban" + target);
                info.setWarningLevel(9);
                logger.debug(target.getName() + " has been warned and has hit the first infraction level of a infinate days ban.");
            }
            else
            {
                logger.debug(target.getName() + " has been warned however has already been appropriatly sanctioned for this warning.");
            }
        }
    }

}
