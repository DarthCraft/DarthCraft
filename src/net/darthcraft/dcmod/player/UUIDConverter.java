package net.darthcraft.dcmod.player;

import java.io.File;
import net.darthcraft.dcmod.player.Ban.BanType;
import net.darthcraft.dcmod.DarthCraft;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class UUIDConverter
    {

    private final DarthCraft plugin;

    private UUIDConverter(DarthCraft plugin)
        {
        this.plugin = plugin;
        }

    public void parseOldDCBanConfig()
        {
        final File configFile = new File(FileUtils.getPluginsFolder(), "DarthCraft/bans.yml");
        if (!configFile.exists())
            {
            return;
            }

        plugin.logger.warning("Legacy DarthCraft bans found!");
        plugin.logger.info("Converting bans from legacy DarthCraft System");

        final YamlConfig config = new YamlConfig(plugin, configFile, false);
        config.load();

        int players = 0;
        if (config.isConfigurationSection("players"))
            {
            for (String banName : config.getConfigurationSection("players").getKeys(false))
                {
                try
                    {
                    if (!config.isConfigurationSection("players." + banName))
                        {
                        continue; // Not a ban
                        }

                    final ConfigurationSection cs = config.getConfigurationSection("players." + banName);
                    final Ban ban = new Ban();

                    ban.setType(BanType.UUID);
                    ban.setUuid(Bukkit.getOfflinePlayer(banName).getUniqueId());
                    ban.setName(banName);
                    ban.setBy(cs.getString("by"));
                    ban.setReason(cs.getString("reason"));
                    ban.setExpiryDate(net.pravian.bukkitlib.util.TimeUtils.parseString(cs.getString("expires")));
                    ban.addIps(cs.getStringList("ips"));
                    ban.saveTo(config);
                    players++;

                    }
                catch (Exception e)
                    {
                    plugin.logger.warning("Error converting legacy ban: " + banName);
                    continue;
                    }
                }
            config.set("players", null);
            config.save();
            }

        plugin.logger.info("Finished converting legacy bans");
        plugin.logger.info("Converted " + players + " player- and ");

        }

    public static UUIDConverter getInstance(DarthCraft plugin)
        {
        return new UUIDConverter(plugin);
        }
    }
