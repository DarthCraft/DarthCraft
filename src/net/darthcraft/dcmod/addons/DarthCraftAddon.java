package net.darthcraft.dcmod.addons;

import net.darthcraft.dcmod.DarthCraft;
import net.darthcraft.dcmod.Util;
import net.pravian.bukkitlib.implementation.PluginLogger;

public class DarthCraftAddon {

    protected final DarthCraft plugin;
    protected final Util util;
    protected final PluginLogger logger;

    public DarthCraftAddon(DarthCraft plugin) {
        this.plugin = plugin;
        this.util = plugin.util;
        this.logger = plugin.logger;
    }
}
