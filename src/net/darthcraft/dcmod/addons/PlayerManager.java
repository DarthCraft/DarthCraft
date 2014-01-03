package net.darthcraft.dcmod.addons;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.darthcraft.dcmod.DarthCraft;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.DateUtils;
import net.pravian.bukkitlib.util.IpUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManager extends DarthCraftAddon {

    public Map<String, PlayerInfo> infoMap = new HashMap<String, PlayerInfo>();

    public PlayerManager(DarthCraft plugin) {
        super(plugin);
    }

    public void onUncancelledPlayerJoin(PlayerJoinEvent event) {

        final Date date = new Date();
        final String ip = IpUtils.getIp(event.getPlayer());

        final PlayerInfo info = new PlayerInfo(plugin, event.getPlayer().getName());

        if (info.exists()) {
            info.load();
            info.setLastLogin(date);
            info.setLastIp(ip);
            info.addIp(ip);
            info.setLogins(info.getLogins() + 1);
        } else {
            info.setFirstIp(ip);
            info.setLastIp(ip);
            info.addIp(ip);
            info.setFirstLogin(date);
            info.setLastLogin(date);
            info.setLogins(1);
            info.setVotes(0);
            info.setLastVote(null);

            logger.info("Added new player: " + event.getPlayer().getName());
        }

        info.save();
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        final PlayerInfo info = getInfo(event.getPlayer());
        info.save();
        infoMap.remove(event.getPlayer().getName());
    }

    public final PlayerInfo getInfo(OfflinePlayer player) {
        if (player == null) {
            return null;
        }

        PlayerInfo info = infoMap.get(player.getName());

        if (info == null) {
            info = new PlayerInfo(plugin, player.getName());
            info.load();

            if (player.isOnline()) {
                infoMap.put(player.getName(), info);
            }
        }

        return info;
    }

    public String getPlayerNameByIp(String ip) {
        final File dir = new File(plugin.getDataFolder() + "/players");
        YamlConfig config;
        ip = ip.trim();

        // First round: just for last ips
        for (File file : dir.listFiles()) {
            config = new YamlConfig(plugin, file, false);
            if (ip.equals(config.getString("lastip"))) {
                return file.getName().replace(".yml", "");
            }
        }

        // Second round: all ips
        for (File file : dir.listFiles()) {
            config = new YamlConfig(plugin, file, false);
            if (config.getStringList("ips").contains(ip)) {
                return file.getName().replace(".yml", "");
            }
        }
        return null;
    }

    public static class PlayerInfo {

        private DarthCraft plugin;
        //
        // Saved items 
        private String name;
        private String firstIp;
        private String lastIp;
        private List<String> ips = new ArrayList<String>();
        private int logins;
        private Date firstLogin;
        private Date lastLogin;
        private int votes;
        private Date lastVote;
        //
        // Unsaved items
        private boolean inAdminChat = false;
        private boolean muted = false;
        private boolean busy = false;

        public PlayerInfo(DarthCraft plugin, String name) {
            this.name = name;
            this.plugin = plugin;
        }

        public PlayerInfo(DarthCraft plugin) {
            this.plugin = plugin;
        }

        // ----- METHODS -----
        public void save() {
            if (name == null || name.equals("")) {
                plugin.logger.severe("Could not save player! Player not defined.");
                return;
            }

            YamlConfig config = new YamlConfig(plugin, new File(plugin.getDataFolder() + "/players", name + ".yml"), false);

            config.set("firstip", firstIp);
            config.set("lastip", lastIp);
            config.set("ips", ips);
            config.set("logins", logins);
            config.set("firstlogin", DateUtils.parseDate(firstLogin));
            config.set("lastlogin", DateUtils.parseDate(lastLogin));
            config.set("votes", votes);
            config.set("lastvote", lastVote);
            config.save();
        }

        public void load() {
            if (this.name == null || this.name.equals("")) {
                plugin.logger.severe("Could not load player! Player not defined.");
                return;
            }

            final YamlConfig config = new YamlConfig(plugin, new File(plugin.getDataFolder() + "/players", name + ".yml"), false);
            config.load();

            firstIp = config.getString("firstip");
            lastIp = config.getString("lastip");
            ips = config.getStringList("ips");
            logins = config.getInt("logins");
            firstLogin = DateUtils.parseString(config.getString("firstlogin"));
            lastLogin = DateUtils.parseString(config.getString("lastlogin"));
            votes = config.getInt("votes");
            lastVote = DateUtils.parseString("lastvote");
        }

        public boolean exists() {
            if (name == null || name.equals("")) {
                plugin.logger.severe("Could not check if player exists! Player not defined.");
                return false;
            }
            return new File(plugin.getDataFolder() + "/players", name + ".yml").exists();
        }

        // ----- ITEMS -----
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstIp() {
            return this.firstIp;
        }

        public void setFirstIp(String ip) {
            this.firstIp = ip;
        }

        public String getLastIp() {
            return this.lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }

        public List<String> getIps() {
            return this.ips;
        }

        public void setIps(List<String> ips) {
            this.ips = ips;
        }

        public void addIp(String ip) {
            if (!this.ips.contains(ip)) {
                this.ips.add(ip);
            }
        }

        public int getLogins() {
            return logins;
        }

        public void setLogins(int logins) {
            this.logins = logins;
        }

        public int addLogin() {
            return ++this.logins;
        }

        public Date getFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(Date login) {
            this.firstLogin = login;
        }

        public Date getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(Date login) {
            this.lastLogin = login;
        }

        public boolean isMuted() {
            return muted;
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }

        public boolean isInAdminChat() {
            return inAdminChat;
        }

        public void setInAdminChat(boolean inAdminChat) {
            this.inAdminChat = inAdminChat;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public Date getLastVote() {
            return lastVote;
        }

        public void setLastVote(Date lastVote) {
            this.lastVote = lastVote;
        }
    }
}
