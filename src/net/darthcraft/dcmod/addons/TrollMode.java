package net.darthcraft.dcmod.addons;

import java.util.List;
import net.darthcraft.dcmod.DarthCraft;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TrollMode extends DarthCraftAddon
    {

    private boolean enabled = false;
    private boolean available;
    private List<String> phrases;

    public TrollMode(DarthCraft plugin)
        {
        super(plugin);
        }

    public void loadSettings()
        {
        this.available = plugin.mainConfig.getBoolean("chat.trollmode.enabled");
        this.phrases = plugin.mainConfig.getStringList("chat.trollmode.phrases");
        }

    public void toggleTrollMode()
        {
        enabled = !enabled;
        }

    public void onPlayerChat(AsyncPlayerChatEvent event)
        {
        if (!enabled || !available)
            {
            return;
            }

        String message = event.getMessage();

        for (String replacement : phrases)
            {
            message = message.replaceAll(replacement.split(";")[0], replacement.split(";")[1]);
            }

        event.setMessage(message);
        }
    }
