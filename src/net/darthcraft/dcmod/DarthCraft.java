package net.darthcraft.dcmod;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import me.husky.mysql.MySQL;
import net.darthcraft.dcmod.addons.*;
import net.darthcraft.dcmod.addons.AdminBusy;
import net.darthcraft.dcmod.addons.BanManager;
import net.darthcraft.dcmod.addons.BanWarner;
import net.darthcraft.dcmod.addons.LoginTitles;
import net.darthcraft.dcmod.chat.AdminChat;
import net.darthcraft.dcmod.chat.ChatFilter;
import net.darthcraft.dcmod.commands.DarthCraftCommand;
import net.darthcraft.dcmod.commands.Permissions.PermissionUtils;
import net.darthcraft.dcmod.commands.Source.SourceUtils;
import net.darthcraft.dcmod.listeners.BlockListener;
import net.darthcraft.dcmod.listeners.CustomListener;
import net.darthcraft.dcmod.listeners.PlayerListener;
import net.darthcraft.dcmod.player.PlayerManager;
import net.darthcraft.dcmod.player.UUIDConverter;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

public class DarthCraft extends BukkitPlugin
{

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
    public YamlConfig topicsConfig;
    //
    public DC_Utils util;
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
    public TopicGenerator topicGenerator;
    public LoginTitles loginTitles;
    public WarningSystem warningSystem;
    public TabColors tabColors;
    public PermBan permBan;
    //
    public static MySQL mySQL;
    public String mysqlport;
    public boolean mysqlenabled;
    public String mysqlhostname;
    public String mysqlpassword;
    public String mysqldatabase;
    public String mysqlusername;

    @Override
    public void onLoad()
    {
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
        topicsConfig = new YamlConfig(plugin, "topics.yml", true);

        // Utilities
        logger = new BukkitLogger(plugin);
        util = new DC_Utils(plugin);

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
        loginTitles = new LoginTitles(plugin);
        warningSystem = new WarningSystem(plugin);
        tabColors = new TabColors(plugin);
        permBan = new PermBan(plugin);

        // Plugin build-number and build-date
        try
        {
            final InputStream in = plugin.getResource("build.properties");
            final Properties build = new Properties();

            build.load(in);
            in.close();

            pluginBuildNumber = build.getProperty("program.buildnumber");
            pluginBuildDate = build.getProperty("program.builddate");
        }
        catch (IOException ex)
        {
            logger.severe("Could not load build information!");
            logger.severe(ex);
            pluginBuildNumber = "1";
            pluginBuildDate = (new SimpleDateFormat("dd/MM/yyyy hh:mm aa")).format(new Date());
        }
    }

    @Override
    public void onEnable()
    {

        // Load main config
        mainConfig.load();

        // Debug-mode
        logger.setDebugMode(mainConfig.getBoolean("debug"));
        logger.debug("Debug-mode enabled!"); // So smart ;D

        // Disable the plugin if the config defines so
        if (!mainConfig.getBoolean("enabled", true))
        {
            logger.warning("Disabling: defined in config");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        // Load other configs
        bansConfig.load();
        likersConfig.load();
        topicsConfig.load();
        topicGenerator = new TopicGenerator(plugin);

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

        if (mysqlenabled)
        {
            mySQL = new MySQL(plugin, this.mysqlhostname = plugin.mainConfig.getString("forceip.hostname"), this.mysqlport = plugin.mainConfig.getString("forceip.port"), this.mysqldatabase = plugin.mainConfig.getString("forceip.hostname"), this.mysqlusername = plugin.mainConfig.getString("forceip.hostname"), this.mysqlpassword = plugin.mainConfig.getString("forceip.hostname"));
            logger.info("Success - MySQL connection has been established");
        }
        else
        {
            logger.warning("MySQL has not been started. Please chcek your config to ensure you have enabled it");
        }

        // Start the metrics
        metricsPlotter.start();

        logger.log(Level.INFO, "Version {0} by {1} is enabled", new Object[]
           {
               pluginVersion, pluginAuthors
        });
    }

    @Override
    public void onDisable()
    {
        plugin.getServer().getScheduler().cancelTasks(plugin);
        banManager.saveBans();
        logger.log(Level.INFO, "Version {0} is disabled", pluginVersion);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        final DarthCraftCommand dispatcher;

        // Load and initialize class
        try
        {
            ClassLoader classLoader = DarthCraft.class.getClassLoader();
            dispatcher = (DarthCraftCommand) classLoader.loadClass(String.format("%s.%s", DarthCraftCommand.class.getPackage().getName(), "Command_" + cmd.getName().toLowerCase())).newInstance();

            dispatcher.setPlugin(this);
            dispatcher.setCommandSender(sender);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            logger.log(Level.SEVERE, "Command not loaded: {0}", cmd.getName());
            logger.severe(e);
            sender.sendMessage(ChatColor.RED + "Command Error: Command not loaded: " + cmd.getName());
            return true;
        }

        // Check for permissions
        try
        {
            if (!SourceUtils.fromSource(sender, dispatcher.getClass(), plugin))
            {
                return (sender instanceof Player ? dispatcher.consoleOnly() : dispatcher.playerOnly());
            }

            if (PermissionUtils.hasPermission(sender, dispatcher.getClass(), plugin))
            {
                return dispatcher.run(sender, cmd, args);
            }
            else
            {
                return dispatcher.noPerms();
            }
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Unknown command error: {0}", e.getMessage());
            logger.severe(e);
            sender.sendMessage(ChatColor.RED + "Command Error: " + e.getMessage());
            return true;
        }
    }

}
