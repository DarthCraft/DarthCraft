package net.darthcraft.dcmod.addons;

import java.util.List;
import java.util.Random;
import net.darthcraft.dcmod.DarthCraft;

public class TopicGenerator extends DarthCraftAddon
    {

    List<String> topics;

    public TopicGenerator(DarthCraft plugin)
        {
        super(plugin);
        topics = (List<String>) plugin.topicsConfig.getList("topics");
        }

    public String getTopic()
        {
        Random random = new Random();
        int numtopics = topics.size();
        return topics.get(random.nextInt(numtopics));
        }

    }
