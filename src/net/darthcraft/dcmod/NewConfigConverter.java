package net.darthcraft.dcmod;

import java.io.File;
import net.darthcraft.dcmod.Ban.BanType;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import org.bukkit.configuration.ConfigurationSection;

public class NewConfigConverter {

    private final DarthCraft plugin;

    private NewConfigConverter(DarthCraft plugin) {
        this.plugin = plugin;
    }

    public void parseOldDCBanConfig() {
        final File configFile = new File(FileUtils.getPluginsFolder(), "DarthCraft/bans.yml");
        if (!configFile.exists()) {
            return;
        }

        plugin.logger.warning("Legacy DarthCraft bans found!");
        plugin.logger.info("Converting bans from legacy DarthCraft System");

        final YamlConfig config = new YamlConfig(plugin, configFile, false);
        config.load();

        int players = 0;
        if (config.isConfigurationSection("players")) {
            for (String banName : config.getConfigurationSection("players").getKeys(false)) {
                try {
                    if (!config.isConfigurationSection("players" + banName)) {
                        return; // Not a ban
                    }

                    final ConfigurationSection cs = config.getConfigurationSection("players" + banName);
                    final Ban ban = new Ban();

                    
                    ban.setType(BanType.UUID);
                    ban.setName(banName);
                    ban.setBy(cs.getString("by"));
                    ban.setReason(cs.getString("reason"));
                    ban.setExpiryDate(net.pravian.bukkitlib.util.TimeUtils.parseString(cs.getString("expires")));
                    ban.addIps(cs.getStringList("ips"));
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

    }

    public static NewConfigConverter getInstance(DarthCraft plugin) {
        return new NewConfigConverter(plugin);
    }
}
