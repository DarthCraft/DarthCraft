package net.darthcraft.dcmod.addons;

import java.io.File;
import java.io.IOException;
import net.darthcraft.dcmod.Ban;
import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.commands.Permissions;
import org.bukkit.entity.Player;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

public class MetricsPlotter extends DarthCraftAddon {

    Graph bans;
    Metrics metrics;

    public MetricsPlotter(DarthCraft plugin) {
        super(plugin);
    }

    public void start() {
        // metrics
        try {
            metrics = new Metrics(plugin);

            // Graph: Amount of bans
            bans = metrics.createGraph("Bans");

            // Amount of bans
            bans.addPlotter(new Metrics.Plotter("Total") {
                @Override
                public int getValue() {
                    return plugin.banManager.getBans().size();
                }
            });

            // Amount of player bans
            bans.addPlotter(new Metrics.Plotter("Player") {
                @Override
                public int getValue() {
                    int value = 0;

                    for (Ban ban : plugin.banManager.getBans()) {
                        if (ban.getType() == Ban.BanType.PLAYER) {
                            value++;
                        }
                    }

                    return value;
                }
            });

            // Amount of Ip bans
            bans.addPlotter(new Metrics.Plotter("Ip") {
                @Override
                public int getValue() {
                    int value = 0;

                    for (Ban ban : plugin.banManager.getBans()) {
                        if (ban.getType() == Ban.BanType.IP) {
                            value++;
                        }
                    }

                    return value;
                }
            });

            // Graph: Players
            final Graph players = metrics.createGraph("Players");

            // Amount of unique players to have joined the server
            players.addPlotter(new Metrics.Plotter("Unique") {
                @Override
                public int getValue() {
                    try {
                        return server.getOfflinePlayers().length;
                    } catch (NullPointerException e) {
                        return 0;
                    }
                }
            });

            // Amount of online players
            players.addPlotter(new Metrics.Plotter("Online (Total)") {
                @Override
                public int getValue() {
                    return plugin.getServer().getOnlinePlayers().length;
                }
            });

            // Amount of online admins
            players.addPlotter(new Metrics.Plotter("Online (Admin)") {
                @Override
                public int getValue() {
                    int admins = 0;
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (Permissions.PermissionUtils.hasPermission(player, Permissions.Permission.ADMIN)) {
                            admins++;
                        }
                    }

                    return admins;
                }
            });
            
            metrics.start();

        } catch (IOException e) {
            logger.warning("Failed to submit metrics data");
        }
    }
}
