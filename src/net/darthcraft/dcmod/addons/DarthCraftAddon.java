package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.DC_Utils;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import org.bukkit.Server;

public class DarthCraftAddon
{

    protected final DarthCraft plugin;
    protected final Server server;
    protected final DC_Utils util;
    protected final BukkitLogger logger;

    public DarthCraftAddon(DarthCraft plugin)
    {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.util = plugin.util;
        this.logger = plugin.logger;
    }
}
