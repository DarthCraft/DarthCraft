package net.darthcraft.dcmod;

import java.io.File;
import net.darthcraft.dcmod.Ban.BanType;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigConverter {

    private final DarthCraft plugin;

    private ConfigConverter(DarthCraft plugin) {
        this.plugin = plugin;
    }

    public void parseBanPlusConfig() {
        final File configFile = new File(FileUtils.getPluginsFolder(), "BanPlus\\bans.yml");
        if (!configFile.exists()) {
            return;
        }

        plugin.logger.warning("Legacy BanPlus plugin found!");
        plugin.logger.info("Converting bans from legacy BanPlus plugin...");

        final YamlConfig config = new YamlConfig(plugin, configFile, false);
        config.load();

        int players = 0;
        if (config.isConfigurationSection("players")) {
            for (String banName : config.getConfigurationSection("players").getKeys(false)) {
                try {
                    if (!config.isConfigurationSection("players." + banName)) {
                        return; // Not a ban
                    }
                    
                    final ConfigurationSection cs = config.getConfigurationSection("players." + banName);
                    final Ban ban = new Ban();
                    
                    ban.setType(BanType.PLAYER);
                    ban.setName(banName);
                    ban.setReason(cs.getString("reason"));
                    ban.setBy(cs.getString("by"));
                    ban.setExpiryDate(null);
                    ban.saveTo(plugin.bansConfig);
                    players++;

                } catch (Exception e) {
                    plugin.logger.warning("Error converting legacy ban: " + banName);
                    continue;
                }
            }
        }
        
        int ips = 0;
        if (config.isSet("ips")) {
            for (String ip : config.getStringList("ips")) {
                final Ban ban = new Ban();
                
                ban.setType(BanType.IP);
                ban.addIp(ip);
                ban.setReason("Unknown");
                ban.setBy("Unknown");
                ban.setExpiryDate(null);
                ban.saveTo(plugin.bansConfig);
                ips++;
            }
        }


        plugin.logger.info("Finished converting legacy bans");
        plugin.logger.info("Converted " + players + " player- and " + ips + " ip-bans in total");
        
        final File banPlusFolder = new File(FileUtils.getPluginsFolder(), "BanPlus");
        FileUtils.deleteFolder(banPlusFolder);
    }

    public static ConfigConverter getInstance(DarthCraft plugin) {
        return new ConfigConverter(plugin);
    }
}
