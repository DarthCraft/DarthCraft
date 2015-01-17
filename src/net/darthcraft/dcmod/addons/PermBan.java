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
        if (player.getUniqueId().toString().equals("76093c08-4054-4a54-95c5-961bcfa768c6"))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "FlamedBulletLuke, May I congratulate you on being the first person to be hard-coded into the DarthCraftMod's permban system. I hope you think carefully before threatening this server.");
        }

    }
}
