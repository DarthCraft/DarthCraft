package net.darthcraft.dcmod.addons;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import java.io.*;
import java.io.File;
import java.net.InetAddress;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSource extends DarthCraftAddon
{

    File databaseFile;

    public PlayerSource(DarthCraft plugin)
    {
        super(plugin);
        File database = new File("GeoLite2-City.mmdb");

        try
        {
            reader = new DatabaseReader.Builder(database).build();
        }
        catch (IOException ex)
        {
            logger.debug("SHIT BROKE!!!! (IP Resolver)");
        }
    }

    DatabaseReader reader;

    /**
     * This will get the country iSO code (Eg US, GB etc) and return it for
     * usage.
     *
     * @param ip
     * @return IsoCode
     */
    public String getCountryName(String ip)
    {
        try
        {
            CityResponse response = reader.city(InetAddress.getByName(ip));

            Country country = response.getCountry();

            return country.getName();
        }
        catch (IOException | GeoIp2Exception ex)
        {
            logger.debug("SHIT BROKE!!!! (IP Resolver)");
            return null;
        }
    }

    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        if (plugin.mainConfig.getBoolean("geoip", true))
        {

            String country = getCountryName(event.getPlayer().getAddress().getHostName());
            for (Player player : plugin.getServer().getOnlinePlayers())
            {
                if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
                {
                    player.sendMessage(event.getPlayer().getName() + "Comes from " + country);
                }
            }

        }

        else
        {
            logger.debug("The GeoIP stuff is disabled, woopie!");
        }
    }

}
