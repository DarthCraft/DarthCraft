package net.darthcraft.dcmod;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import me.husky.mysql.MySQL;
import net.darthcraft.dcmod.addons.*;
import net.darthcraft.dcmod.addons.AdminChat;
import net.darthcraft.dcmod.addons.ChatFilter;
import net.darthcraft.dcmod.addons.TradeChat;
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
    public YamlConfig dataFile;
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
    public AbductionHammer abductionHammer;
    public VisitorCounter visitorCounter;
    public PlayerSource playerSource;
    public TradeChat tradeChat;
    public VoteToPlay voteToPlay;
    public TreeDetector treeDetector;
    public MOTD motd;
    public Backup backup;
    public TaxSystem taxSystem;
    public BeaconTax beaconTax;
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
        dataFile = new YamlConfig(plugin, "data.yml", true);

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
        abductionHammer = new AbductionHammer(plugin);
        visitorCounter = new VisitorCounter(plugin);
        playerSource = new PlayerSource(plugin);
        tradeChat = new TradeChat(plugin);
        voteToPlay = new VoteToPlay(plugin);
        treeDetector = new TreeDetector(plugin);
        motd = new MOTD(plugin);
        backup = new Backup(plugin);
        beaconTax = new BeaconTax(plugin);
        taxSystem = new TaxSystem(plugin);

        // Plugin build-number and build-date
        try
        {
            final Properties build;
            try (InputStream in = plugin.getResource("build.properties"))
            {
                build = new Properties();
                build.load(in);
            }

            pluginBuildNumber = build.getProperty("program.buildnumber");
            pluginBuildDate = build.getProperty("program.builddate");
        }
        catch (IOException ex)
        {
            logger.severe(DC_Messages.CANNOT_LOAD_BUILD_INFO);
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
        logger.debug(DC_Messages.DEBUG_ENABLED); // So smart ;D

        // Disable the plugin if the config defines so
        if (!mainConfig.getBoolean("enabled", true))
        {
            logger.warning(DC_Messages.PLUGIN_DISABLED);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        // Load other configs
        bansConfig.load();
        likersConfig.load();
        topicsConfig.load();
        dataFile.load();
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
            logger.info(DC_Messages.MYSQL_ENABLED);
        }
        else
        {
            logger.warning(DC_Messages.MYSQL_NOT_ENABLED);
        }

        if (mainConfig.getBoolean("geoip", true))
        {
            logger.warning("Attention: If you have NOT manually downloaded and installed the required MaxMind Dependencies, you will get a clusterfuck of errors. Please make sure that you do this BEFORE enabling this plugin again!");
        }

        // Start the metrics
        {
            metricsPlotter.start();
        }

        logger.log(Level.INFO, DC_Messages.PLUGIN_ENABLED, new Object[]
        {
            pluginVersion, pluginAuthors
        });
    }

    @Override
    public void onDisable()
    {
        plugin.getServer().getScheduler().cancelTasks(plugin);
        banManager.saveBans();
        logger.log(Level.INFO, DC_Messages.PLUGIN_DISABLED, pluginVersion);
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
            logger.log(Level.SEVERE, DC_Messages.COMMAND_NOT_LOADED_LOG, cmd.getName());
            logger.severe(e);
            sender.sendMessage(DC_Messages.COMMAND_NOT_LOADED + cmd.getName());
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
            logger.log(Level.SEVERE, DC_Messages.UNKOWN_COMMAND_ERROR, e.getMessage());
            logger.severe(e);
            sender.sendMessage(ChatColor.RED + DC_Messages.COMMAND_ERROR + e.getMessage());
            return true;
        }
    }

}
