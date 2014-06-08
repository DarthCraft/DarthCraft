package net.darthcraft.dcmod;

import java.io.File;
import java.util.UUID;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.apache.commons.io.FilenameUtils;

public class PlayerConverter {

    private final DarthCraft plugin;

    private PlayerConverter(DarthCraft plugin) {
        this.plugin = plugin;
    }

    public void convert() {
      for (File file : new File(FileUtils.getPluginDataFolder(plugin) + "/players").listFiles()) {
          YamlConfig config = new YamlConfig(plugin, file, false);
          
          String name = FilenameUtils.removeExtension(file.getName());
          UUID uuid = plugin.util.playerToUUID(name);
          
          YamlConfig newConfig = new YamlConfig(plugin, new File(FileUtils.getPluginDataFolder(plugin) + "/players/" + uuid.toString() + ".yml"), false);
          newConfig.set("nlastuser", name);
          newConfig.set("nlastip", config.getString("lastip"));
          newConfig.getStringList("nips").addAll(config.getStringList("ips"));
          newConfig.set("nlogins", config.getInt("logins"));
          newConfig.set("nfirstlogin", config.getString("firstlogin"));
          newConfig.set("nlastlogin", config.getString("lastlogin"));
          newConfig.set("nvotes", config.getInt("votes"));
          newConfig.set("nlastvote", config.getString("lastvote"));
          newConfig.save();
          file.delete();
          
          LoggerUtils.info(plugin, "Converted player config " + name + " to " + uuid.toString() + " successfully.");
      }
    }

    public static PlayerConverter getInstance(DarthCraft plugin) {
        return new PlayerConverter(plugin);
    }
}
