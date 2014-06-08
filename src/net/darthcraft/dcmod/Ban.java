package net.darthcraft.dcmod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import net.darthcraft.dcmod.Ban.BanType;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.IpUtils;
import net.pravian.bukkitlib.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Ban {

    public enum BanType {

        UUID("Player-ban"), // Name-ban, but may have attached Ips
        IP("IP-ban"); // IP-specific ban, unknown player
        private String type;

        private BanType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
    //
    private BanType type = null;
    private String name = null;
    private UUID uuid = null;
    private String reason = null;
    private String by = null;
    private Date expiry = null;
    final private List<String> ips;

    public Ban() {
        ips = new ArrayList<String>();
    }
    
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
    public UUID getUuid() {
        return uuid;
    }

    public void setType(BanType type) {
        this.type = type;
    }

    public BanType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setBy(String name) {
        this.by = name;
    }

    public String getBy() {
        return by;
    }

    public void setExpiryDate(Date expiry) {
        this.expiry = expiry;
    }

    public Date getExpiryDate() {
        return expiry;
    }

    public boolean hasIps() {
        return ips != null && !ips.isEmpty();
    }

    @Deprecated
    public void setIps(List<String> ips) {
        this.ips.clear();
        this.ips.addAll(ips);
    }

    @Deprecated
    public void setIp(String ip) {
        this.ips.clear();
        this.ips.add(ip.trim());
    }

    public void addIp(String ip) {
        if (!ips.contains(ip.trim())) {
            this.ips.add(ip.trim());
        }
    }

    public void addIps(List<String> ips) {
        for (String ip : ips) {
            addIp(ip);
        }
    }

    public void clearIps() {
        ips.clear();
    }

    public boolean containsIp(String ip) {
        return ips.contains(ip.trim());
    }

    public void removeIp(String ip) {
        if (containsIp(ip)) {
            ips.remove(ip.trim());
        }
    }

    public List<String> getIps() {
        return ips;
    }

    public String getIp() {
        return (ips.size() > 0 ? ips.get(0) : null);
    }

    public String getTarget() {
        if (type == BanType.UUID) {
            return name;
        } else if (type == BanType.IP) {
            return getIp();
        }
        return name;
    }

    public String getKickMessage() {
        return ChatColor.RED
                + "Your " + (type == BanType.IP ? "IP-Address" : "UUID") + " is banned from this server.\n"
                + "Reason: " + reason + "\n"
                + "Expires: " + TimeUtils.parseDate(expiry) + "\n"
                + "Banned by: " + by;
    }

    public boolean isExpired() {
        if (expiry == null) {
            return false;
        }

        if (expiry.after(new Date())) {
            return false;
        }

        return true;
    }

    public void saveTo(YamlConfig config) {
        if (type == BanType.UUID) {
            final String path = "uuid." + uuid.toString();

            if (!config.isConfigurationSection(path)) {
                config.createSection(path);
            }

            final ConfigurationSection cs = config.getConfigurationSection(path);

            cs.set("player", name.toLowerCase());
            cs.set("by", by);
            cs.set("reason", reason);
            cs.set("expires", TimeUtils.parseDate(expiry));
            cs.set("ips", ips);

        } else if (type == BanType.IP) {
            final String path = "ips." + IpUtils.toEscapedString(getIp());

            if (!config.isConfigurationSection(path)) {
                config.createSection(path);
            }

            final ConfigurationSection cs = config.getConfigurationSection(path);

            cs.set("by", by);
            cs.set("reason", reason);
            cs.set("expires", TimeUtils.parseDate(expiry));
        }
        config.save();
    }

    public void deleteFrom(YamlConfig config) {
        if (type == BanType.UUID) {
            config.set("uuid." + uuid.toString(), null);
        } else if (type == BanType.IP) {
            config.set("ips." + IpUtils.toEscapedString(getIp()), null);
        }
        config.save();
    }
}
