package net.darthcraft.dcmod.addons;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import java.io.*;
import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSource extends DarthCraftAddon
{
    
    File databaseFile;
    File dataFolder;
    
    public PlayerSource(DarthCraft plugin)
    {
        super(plugin);
        File database = new File("GeoLite2-City.mmdb");
        
        this.dataFolder = dataFolder;
        
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
        String country = getCountryName(event.getPlayer().getAddress().getHostName());
        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN))
            {
                player.sendMessage(event.getPlayer().getName() + "Comes from " + country);
            }
        }
        
    }
    
    public void downloadDatabase()
    {
        if (databaseFile.exists())
        {
            logger.debug("The file exsists... Lets do nothing");
        }
        else
        {
            logger.debug("PANIC!!! The file is missing... Lets see what we can do about that.");
            try
            {
                databaseFile = new File(dataFolder, "GeoLite2-City.mmdb");
                String url = "https://www.superior-networks.com/maxmind/GeoLite2-City.mmdb";
                logger.debug("Something important can be put here... What exactly nobody knows, but oh well.");
                URL downloadUrl = new URL(url);
                URLConnection conn = downloadUrl.openConnection();
                conn.setConnectTimeout(10000);
                conn.connect();
                InputStream input = conn.getInputStream();
                
                input = new GZIPInputStream(input);
                OutputStream output = new FileOutputStream(databaseFile);
                byte[] buffer = new byte[2048];
                int length = input.read(buffer);
                while (length >= 0)
                {
                    output.write(buffer, 0, length);
                    length = input.read(buffer);
                }
                output.close();
                input.close();
            }
            catch (MalformedURLException ex)
            {
                logger.debug("The URL is fucked up... Wild broke something again - Sorry :)");
                return;
            }
            catch (IOException ex)
            {
                logger.debug("Errr... This looks like Ben Broken something with the network I think... As we cannot connect to the URL... Shout at Ben please :)");
            }
        }
    }
}
