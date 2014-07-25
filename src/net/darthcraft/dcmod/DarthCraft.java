package net.darthcraft.dcmod;

import net.darthcraft.dcmod.addons.*;
import net.darthcraft.dcmod.listener.PlayerListener;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import net.darthcraft.dcmod.commands.DarthCraftCommand;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.pravian.bukkitlib.config.YamlConfig;
import net.darthcraft.dcmod.commands.Source.SourceUtils;
import net.darthcraft.dcmod.listener.BlockListener;
import net.darthcraft.dcmod.listener.CustomListener;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DarthCraft extends JavaPlugin {

    private DarthCraft plugin;
    //
    public String pluginName;
    public String pluginVersion;
    public String pluginBuildNumber;
    public String pluginBuildDate;
    public String pluginAuthors;
    //
    public boolean debugMode;
    //
    public YamlConfig mainConfig;
    public YamlConfig bansConfig;
    public YamlConfig likersConfig;
    //
    public Util util;
    public BukkitLogger logger;
    //
    public PlayerManager playerManager;
    public BanManager banManager;
    public AdminChat adminChat;
    public ChatFilter chatFilter;
    public TrollMode trollMode;
    public ForceIp forceIp;
    public MetricsPlotter metricsPlotter;
    public LikeSigns likeSigns;
    public AdminBusy adminBusy;
    public BanWarner banWarner;

    @Override
    public void onLoad() {
        plugin = this;

        // Plugin info
        final PluginDescriptionFile pdf = plugin.getDescription();
        pluginName = pdf.getName();
        pluginVersion = pdf.getVersion();
        pluginAuthors = PlayerUtils.concatPlayernames(pdf.getAuthors());

        // Configs
        mainConfig = new YamlConfig(plugin, "config.yml", true);
        bansConfig = new YamlConfig(plugin, "bans.yml", true);
        likersConfig = new YamlConfig(plugin, "likers.yml", true);
        

        // Utilities
        logger = new BukkitLogger(plugin);
        util = new Util(plugin);

        // Addons
        playerManager = new PlayerManager(plugin);
        banManager = new BanManager(plugin);
        adminChat = new AdminChat(plugin);
        chatFilter = new ChatFilter(plugin);
        trollMode = new TrollMode(plugin);
        forceIp = new ForceIp(plugin);
        metricsPlotter = new MetricsPlotter(plugin);
        likeSigns = new LikeSigns(plugin);
        adminBusy = new AdminBusy(plugin);
        banWarner = new BanWarner(plugin);

        // Plugin build-number and build-date
        try {
            final InputStream in = plugin.getResource("build.properties");
            final Properties build = new Properties();

            build.load(in);
            in.close();

            pluginBuildNumber = build.getProperty("program.buildnumber");
            pluginBuildDate = build.getProperty("program.builddate");
        } catch (Exception ex) {
            logger.severe("Could not load build information!");
            logger.severe(ex);
            pluginBuildNumber = "1";
            pluginBuildDate = (new SimpleDateFormat("dd/MM/yyyy hh:mm aa")).format(new Date());
        }
    }

    @Override
    public void onEnable() {

        // Load main config
        mainConfig.load();

        // Debug-mode
        logger.setDebugMode(mainConfig.getBoolean("debug"));
        logger.debug("Debug-mode enabled!"); // So smart ;D

        // Disable the plugin if the config defines so
        if (!mainConfig.getBoolean("enabled", true)) {
            logger.warning("Disabling: defined in config");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        // Load other configs
        bansConfig.load();
        likersConfig.load();
        
        // Parse old DarthCraft ban files
        final UUIDConverter newconverter = UUIDConverter.getInstance(plugin);
        newconverter.parseOldDCBanConfig();
        
        // Cache items from config files
        banManager.loadBans();
        trollMode.loadSettings();
        chatFilter.loadSettings();
        forceIp.loadSettings();
        likeSigns.loadSettings();

        // Register events
        final PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(plugin), plugin);
        pm.registerEvents(new BlockListener(plugin), plugin);
        pm.registerEvents(new CustomListener(plugin), plugin);

        // Start the metrics
        metricsPlotter.start();

        logger.info("Version " + pluginVersion + " by " + pluginAuthors + " is enabled");
    }

    @Override
    public void onDisable() {
        plugin.getServer().getScheduler().cancelTasks(plugin);
        banManager.saveBans();
        logger.info("Version " + pluginVersion + " is disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final DarthCraftCommand dispatcher;

        // Load and initialize class
        try {
            ClassLoader classLoader = DarthCraft.class.getClassLoader();
            dispatcher = (DarthCraftCommand) classLoader.loadClass(String.format("%s.%s", DarthCraftCommand.class.getPackage().getName(), "Command_" + cmd.getName().toLowerCase())).newInstance();

            dispatcher.setPlugin(this);
            dispatcher.setCommandSender(sender);
        } catch (Exception e) {
            logger.severe("Command not loaded: " + cmd.getName());
            logger.severe(e);
            sender.sendMessage(ChatColor.RED + "Command Error: Command not loaded: " + cmd.getName());
            return true;
        }

        // Check for permissions
        try {
            if (!SourceUtils.fromSource(sender, dispatcher.getClass(), plugin)) {
                return (sender instanceof Player ? dispatcher.consoleOnly() : dispatcher.playerOnly());
            }

            if (PermissionUtils.hasPermission(sender, dispatcher.getClass(), plugin)) {
                return dispatcher.run(sender, cmd, args);
            } else {
                return dispatcher.noPerms();
            }
        } catch (Exception e) {
            logger.severe("Unknown command error: " + e.getMessage());
            logger.severe(e);
            sender.sendMessage(ChatColor.RED + "Command Error: " + e.getMessage());
            return true;
        }
    }
}
