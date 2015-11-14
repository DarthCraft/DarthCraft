package net.darthcraft.darthcraftmod;

import net.pravian.aero.plugin.AeroPlugin;

public class DarthCraftMod extends AeroPlugin<DarthCraftMod>
  {

    public static DarthCraftMod plugin;

    @Override
    public void load()
    {
        DarthCraftMod.plugin = this;
    }

    @Override
    public void enable()
    {
        DarthCraftMod.plugin = this;
    }

    @Override
    public void disable()
    {
        DarthCraftMod.plugin = null;
    }

  }
