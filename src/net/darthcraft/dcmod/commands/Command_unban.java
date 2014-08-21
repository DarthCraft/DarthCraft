package net.darthcraft.dcmod.commands;

import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.PlayerUtils;
import net.darthcraft.dcmod.bans.Ban;
import net.darthcraft.dcmod.bans.Ban.BanType;
import net.darthcraft.dcmod.commands.Permissions.Permission;
import net.pravian.bukkitlib.util.IpUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Source(SourceType.ANY)
@Permissions(Permission.ADMIN)
public class Command_unban extends DarthCraftCommand
    {

    @Override
    public boolean run(CommandSender sender, Command cmd, String[] args)
        {
        if (args.length != 1)
            {
            return showUsage(cmd);
            }

        final boolean byIp = IpUtils.isValidIp(args[0]);

        final OfflinePlayer player;
        final String ip;

        if (byIp)
            {
            ip = args[0];
            player = PlayerUtils.getOfflinePlayer(playerManager.getPlayerNameByIp(ip));
            }
        else
            {
            player = PlayerUtils.getOfflinePlayer(args[0], false);
            if (player == null)
                {
                return warn("That player isn't banned.");
                }

            ip = playerManager.getInfo(player).getLastIp();
            }

        boolean nameUnbanned = false;
        boolean ipUnbanned = false;

        // Player ban
        if (player != null)
            {
            final Ban nameBan = banManager.getNameBan(player.getName());

            if (nameBan != null)
                {
                banManager.unban(nameBan);
                nameUnbanned = true;
                }
            }

        // Ip ban
        if (ip != null)
            {

            // Ip-specific ban
            final Ban ipBan = banManager.getIpBan(ip);
            if (ipBan != null)
                {
                banManager.unban(ipBan);
                ipUnbanned = true;
                }

            // Player attached-Ip ban
            for (Ban currentBan : banManager.getBans())
                {
                if (currentBan.getType() != BanType.UUID)
                    {
                    continue;
                    }

                if (currentBan.containsIp(args[0]))
                    {
                    currentBan.removeIp(args[0]);
                    ipUnbanned = true;
                    }
                }

            }

        if (!nameUnbanned && !ipUnbanned)
            {
            return warn(byIp ? "That IP isn't banned." : "That player isn't banned.");
            }

        util.adminAction(sender, "Unbanning " + (!nameUnbanned
                                                 ? "IP " + ip + (player != null ? " (" + player.getName() + ")" : "")
                                                 : player.getName() + (ipUnbanned ? " and IP " + ip : "")));

        /*
         // Unbanning IP
         if (IpUtils.isValidIp(args[0])) {
         final String ip = args[0].trim();

         boolean ipUnbanned = false;
         boolean attachedIpUnbanned = false;

         // Ip entry
         final Ban ipBan = banManager.getIpBan(ip);
         if (ipBan != null) {
         banManager.unban(ipBan);
         ipUnbanned = true;
         }

         // Player entry (contains IP)
         for (Ban currentBan : banManager.getBans()) {
         if (currentBan.getType() != BanType.PLAYER) {
         continue;
         }

         if (currentBan.containsIp(args[0])) {
         currentBan.removeIp(args[0]);
         attachedIpUnbanned = true;
         }
         }

         if (!ipUnbanned && !attachedIpUnbanned) {
         return warn("That IP isn't banned.");
         }

         final String name = plugin.playerManager.getPlayerNameByIp(ip);
         util.adminAction(sender, "Unbanning IP " + ip + (name == null ? "" : "(" + name + ")"));
         return true;
         }

         // Unbanning by name
         final OfflinePlayer player = PlayerUtils.getOfflinePlayer(args[0]);
         if (player == null) {
         return warn("Player not found, or never joined the server.");
         }

         final Ban ban = banManager.getNameBan(player.getName());
         if (ban == null) {
         final PlayerInfo info = plugin.playerManager.getInfo(player);
         final String ip = info.getLastIp();
         final Ban ipBan = banManager.getIpBan(ip);
         if (ipBan == null) {
         return warn("That player isn't banned.");
         }

         final String name = plugin.playerManager.getPlayerNameByIp(ip);
         util.adminAction(sender, "Unbanning IP " + ip + (name == null ? "" : "(" + name + ")"));
         banManager.unban(ipBan);
         return warn("That player isn't banned.");
         }

         final PlayerInfo info = plugin.playerManager.getInfo(player);
         final String ip = info.getLastIp();
         final Ban ipBan = banManager.getIpBan(ip);
         if (ipBan == null) {
         return true;
         }

         final String name = plugin.playerManager.getPlayerNameByIp(ip);
         banManager.unban(ipBan);

         util.adminAction(sender, "Unbanning " + player.getName());
         banManager.unban(ban);
         */
        return true;
        }
    }
