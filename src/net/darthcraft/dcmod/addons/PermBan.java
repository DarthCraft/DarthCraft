package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public class PermBan extends DarthCraftAddon
{

    public PermBan(DarthCraft plugin)
    {
        super(plugin);
    }

    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();

        // Hard Coded Perm-Ban
        switch (player.getUniqueId().toString())
        {
            case "76093c08-4054-4a54-95c5-961bcfa768c6":
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "FlamedBulletLuke, May I congratulate you on being the first person to be hard-coded into the DarthCraftMod's permban system. I hope you think carefully before threatening this server.");
                break;
            case "6ae9cb8f-5db0-4dc7-bac2-454d33b967af":
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Well NoobMan, it appears that you just killed a hosts animals. May I just inform you of the stupity of that idea, as you can never join this server again. Bravo.  ");
                break;
        }

    }
}
