package net.darthcraft.darthcraftmod;

import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitPlugin;

public class DarthCraftMod extends BukkitPlugin
{

    private DarthCraftMod plugin;
    //
    //
    public YamlConfig mainConfig;
    //

    //
    @Override
    public void onLoad()
    {
        mainConfig = new YamlConfig(plugin, "config.yml");
    }
    
    @Override
    public void onEnable()
    {
        mainConfig.load();
    }
}
