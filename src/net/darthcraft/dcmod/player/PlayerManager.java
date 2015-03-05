package net.darthcraft.dcmod.player;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.addons.DarthCraftAddon;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.IpUtils;
import net.pravian.bukkitlib.util.TimeUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManager extends DarthCraftAddon
{

    public Map<String, PlayerInfo> infoMap = new HashMap<>();

    public PlayerManager(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event)
    {

        final Date date = new Date();
        final String ip = IpUtils.getIp(event.getPlayer());

        final PlayerInfo info = new PlayerInfo(plugin, event.getPlayer().getName());

        if (info.exists())
        {
            info.load();
            info.setLastLogin(date);
            info.setLastIp(ip);
            info.addIp(ip);
            info.setLogins(info.getLogins() + 1);
        }
        else
        {
            info.setFirstIp(ip);
            info.setLastIp(ip);
            info.addIp(ip);
            info.setFirstLogin(date);
            info.setLastLogin(date);
            info.setLogins(1);
            info.setVotes(0);
            info.setLastVote(null);
            info.setWarnings(0);
            info.setWarningLevel(0);

            logger.log(Level.INFO, "Added new player: {0}", event.getPlayer().getName());
        }

        info.save();
    }

    public void onPlayerQuit(PlayerQuitEvent event)
    {
        final PlayerInfo info = getInfo(event.getPlayer());
        info.save();
        infoMap.remove(event.getPlayer().getName());
    }

    public final PlayerInfo getInfo(OfflinePlayer player)
    {
        if (player == null)
        {
            return null;
        }

        PlayerInfo info = infoMap.get(player.getName());

        if (info == null)
        {
            info = new PlayerInfo(plugin, player.getName());
            info.load();

            if (player.isOnline())
            {
                infoMap.put(player.getName(), info);
            }
        }

        return info;
    }

    public String getPlayerNameByIp(String ip)
    {
        final File dir = new File(plugin.getDataFolder() + "/players");
        YamlConfig config;
        ip = ip.trim();

        // First round: just for last ips
        for (File file : dir.listFiles())
        {
            config = new YamlConfig(plugin, file, false);
            if (ip.equals(config.getString("lastip")))
            {
                return file.getName().replace(".yml", "");
            }
        }

        // Second round: all ips
        for (File file : dir.listFiles())
        {
            config = new YamlConfig(plugin, file, false);
            if (config.getStringList("ips").contains(ip))
            {
                return file.getName().replace(".yml", "");
            }
        }
        return null;
    }

    public static class PlayerInfo
    {

        private final DarthCraft plugin;

        // Saved items 
        private String name;
        private UUID uuid;
        private String firstIp;
        private String lastIp;
        private List<String> ips = new ArrayList<>();
        private int logins;
        private Date firstLogin;
        private Date lastLogin;
        private int votes;
        private Date lastVote;
        private int warnings;
        private int warninglevel;
        private String logintitle;
        private List<String> warningreasons = new ArrayList<>();
        private List<String> warningwaivereasons = new ArrayList<>();
        //
        // Unsaved items
        private boolean inAdminChat = false;
        private boolean inTradeChat = false;
        private boolean muted = false;
        private boolean busy = false;

        public PlayerInfo(DarthCraft plugin, String name)
        {
            this.uuid = plugin.util.playerToUUID(name);
            this.name = name;
            this.plugin = plugin;
        }

        public PlayerInfo(DarthCraft plugin)
        {
            this.plugin = plugin;
        }

        // ----- METHODS -----
        public void save()
        {
            if (uuid == null || uuid.toString().equals(""))
            {
                plugin.logger.severe("Could not save player! Player not defined.");
                return;
            }

            YamlConfig config = new YamlConfig(plugin, new File(plugin.getDataFolder() + "/players", uuid.toString() + ".yml"), false);

            config.set("lastuser", name);
            config.set("firstip", firstIp);
            config.set("lastip", lastIp);
            config.set("ips", ips);
            config.set("logins", logins);
            config.set("firstlogin", TimeUtils.parseDate(firstLogin));
            config.set("lastlogin", TimeUtils.parseDate(lastLogin));
            config.set("votes", votes);
            config.set("lastvote", lastVote);
            config.set("warnings", warnings);
            config.set("warninglevel", warninglevel);
            config.set("logintitle", logintitle);
            config.set("reasons", warningreasons);
            config.set("waivereasons", warningwaivereasons);
            config.save();
        }

        public void load()
        {
            if (this.uuid == null || this.uuid.toString().equals(""))
            {
                plugin.logger.severe("Could not load player! Player not defined.");
                return;
            }

            final YamlConfig config = new YamlConfig(plugin, new File(plugin.getDataFolder() + "/players", uuid.toString() + ".yml"), false);
            config.load();

            firstIp = config.getString("firstip");
            lastIp = config.getString("lastip");
            ips = config.getStringList("ips");
            logins = config.getInt("logins");
            firstLogin = TimeUtils.parseString(config.getString("firstlogin"));
            lastLogin = TimeUtils.parseString(config.getString("lastlogin"));
            votes = config.getInt("votes");
            lastVote = TimeUtils.parseString("lastvote");
            warnings = config.getInt("warnings");
            warninglevel = config.getInt("warninglevel");
            logintitle = config.getString("logintitle");
            warningreasons = config.getStringList("reasons");
            warningwaivereasons = config.getStringList("waivereasons");
        }

        public boolean exists()
        {
            if (uuid == null || uuid.toString().equals(""))
            {
                plugin.logger.severe("Could not check if player exists! Player not defined.");
                return false;
            }
            return new File(plugin.getDataFolder() + "/players", uuid.toString() + ".yml").exists();
        }

        // ----- ITEMS -----
        /**
         * The setUuid methood allows you to set the UUID of a player on the
         * server.
         *
         * @param uuid
         */
        public void setUuid(UUID uuid)
        {
            this.uuid = uuid;
        }

        /**
         * This will return the current players UUID that has been resolved from
         * within the plugin.
         *
         * @return uuid
         */
        public UUID getUuid()
        {
            return uuid;
        }

        /**
         * This will return the name of the player's information file you are
         * currently accessing.
         *
         * @return name
         */
        public String getName()
        {
            return name;
        }

        /**
         * This allows you to set the username of the players data file that you
         * are currently accessing.
         *
         * @param name
         */
        public void setName(String name)
        {
            this.name = name;
        }

        /**
         * This will return the first IP that the player has ever joined the
         * server on.
         *
         * @return ip
         */
        public String getFirstIp()
        {
            return this.firstIp;
        }

        /**
         * This will set the first IP from when the player first joined the
         * server.
         *
         * @param ip
         */
        public void setFirstIp(String ip)
        {
            this.firstIp = ip;
        }

        /**
         * This will get the stored login title for the player.
         *
         * @return logintitle
         */
        public String getLoginMessage()
        {
            return this.logintitle;
        }

        /**
         * This will set the current login message for the player.
         *
         * @param loginmessage
         */
        public void setLoginMessage(String loginmessage)
        {
            this.logintitle = loginmessage;
        }

        /**
         * This will return the last IP that the player joined the server on
         *
         * @return ip
         */
        public String getLastIp()
        {
            return this.lastIp;
        }

        /**
         * This allows you to set the last IP that the player joined the server
         * on.
         *
         * @param lastIp
         */
        public void setLastIp(String lastIp)
        {
            this.lastIp = lastIp;
        }

        /**
         * This will return the list of IP's that the player has ever joined on.
         *
         * @return ips
         */
        public List<String> getIps()
        {
            return this.ips;
        }

        /**
         * This will set the list of IP's that the player has ever joined on.
         *
         * @param ips
         */
        public void setIps(List<String> ips)
        {
            this.ips = ips;
        }

        /**
         * This will check if the requested IP is already added and if it is not
         * will add it to the list of IP's.
         *
         * @param ip
         */
        public void addIp(String ip)
        {
            if (!this.ips.contains(ip))
            {
                this.ips.add(ip);
            }
        }

        /**
         * This will get the reasons that the player has received warnings.
         *
         * @return warningreasons
         */
        public List<String> getReasons()
        {
            return this.warningreasons;
        }

        /**
         * This will add a reason to the list of warning reasons.
         *
         * @param reason
         */
        public void addReason(String reason)
        {
            this.warningreasons.add(reason);
        }

        /**
         * This will get a list of reasons that the warnings have been waived.
         *
         * @return warningwaivereasons
         */
        public List<String> getWaiveReasons()
        {
            return this.warningwaivereasons;
        }

        /**
         * This will add a reason to the waived warning list.
         *
         * @param reason
         */
        public void addWaiveReason(String reason)
        {
            this.warningwaivereasons.add(reason);
        }

        /**
         * This will get the amount of logins that the player has had to the
         * server.
         *
         * @return logins
         */
        public int getLogins()
        {
            return logins;
        }

        /**
         * This will set the amount of logins that the player has had to the
         * server.
         *
         * @param logins
         */
        public void setLogins(int logins)
        {
            this.logins = logins;
        }

        /**
         * This gets the warning points that the player has.
         *
         * @return
         */
        public int getWarnings()
        {
            return warnings;
        }

        /**
         * This sets the amount of warning points that the player currently has.
         *
         * @param warnings
         */
        public void setWarnings(int warnings)
        {
            this.warnings = warnings;
        }

        /**
         * This will get the warning level of the player.
         *
         * @return warninglevel
         */
        public int getWarningLevel()
        {
            return warninglevel;
        }

        /**
         * This sets the amount of warning level that the player currently has.
         *
         * @param warninglevel
         */
        public void setWarningLevel(int warninglevel)
        {
            this.warninglevel = warninglevel;
        }

        /**
         * This will add a login to the login counter.
         *
         * @return logins
         */
        public int addLogin()
        {
            return ++this.logins;
        }

        /**
         * This will get the first login date to the server
         *
         * @return firstLogin
         */
        public Date getFirstLogin()
        {
            return firstLogin;
        }

        /**
         * This will set the first login date to the server.
         *
         * @param login
         */
        public void setFirstLogin(Date login)
        {
            this.firstLogin = login;
        }

        /**
         * This will get the last login date of the player.
         *
         * @return lastLogin
         */
        public Date getLastLogin()
        {
            return lastLogin;
        }

        /**
         * This will set the last login date that the player joined the server.
         *
         * @param login
         */
        public void setLastLogin(Date login)
        {
            this.lastLogin = login;
        }

        /**
         * This will return if the player is or is not muted.
         *
         * @return muted
         */
        public boolean isMuted()
        {
            return muted;
        }

        /**
         * This will set the player to either muted or not muted.
         *
         * @param muted
         */
        public void setMuted(boolean muted)
        {
            this.muted = muted;
        }

        /**
         * This will check if the player is currently in admin chat or not
         *
         * @return inAdminChat
         */
        public boolean isInAdminChat()
        {
            return inAdminChat;
        }

        /**
         * This will set if the player is in admin chat or not.
         *
         * @param inAdminChat
         */
        public void setInAdminChat(boolean inAdminChat)
        {
            this.inAdminChat = inAdminChat;
        }
        
         /**
         * This will check if the player is currently in Trade chat or not
         *
         * @return inTradeChat
         */
        public boolean isInTradeChat()
        {
            return inTradeChat;
        }

        /**
         * This will set if the player is in Trade chat or not.
         *
         * @param inTradeChat
         */
        public void setInTradeChat(boolean inTradeChat)
        {
            this.inTradeChat = inTradeChat;
        }

        /**
         * This will check if the player has busy status on or off.
         *
         * @return busy
         */
        public boolean isBusy()
        {
            return busy;
        }

        /**
         * This will set the busy status of a player.
         *
         * @param busy
         */
        public void setBusy(boolean busy)
        {
            this.busy = busy;
        }

        /**
         * This will get the number of votes that the player has cast.
         *
         * @return votes
         */
        public int getVotes()
        {
            return votes;
        }

        /**
         * This will set the number of votes that the player has cast.
         *
         * @param votes
         */
        public void setVotes(int votes)
        {
            this.votes = votes;
        }

        /**
         * This will get the date of the last vote that the player cast.
         *
         * @return lastVote
         */
        public Date getLastVote()
        {
            return lastVote;
        }

        /**
         * This will set the date of the last vote that the player cast.
         *
         * @param lastVote
         */
        public void setLastVote(Date lastVote)
        {
            this.lastVote = lastVote;
        }
    }
}
