package com.github.alwaysdarkk.rewards.common.adapter;

import com.github.alwaysdarkk.rewards.common.configuration.ConfigValue;
import com.github.alwaysdarkk.rewards.common.data.Reward;
import com.github.alwaysdarkk.rewards.common.item.util.SkullUtil;
import com.github.alwaysdarkk.rewards.common.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RewardConfigurationAdapter {

    public List<Reward> getRewards() {
        final ConfigurationSection section = ConfigValue.get(ConfigValue::rewardsSection);
        if (section == null) {
            return null;
        }

        return section.getKeys(false).stream()
                .map(section::getConfigurationSection)
                .filter(Objects::nonNull)
                .map(this::adaptReward)
                .collect(Collectors.toList());
    }

    private Reward adaptReward(ConfigurationSection section) {
        final String id = section.getName();

        final String name = ColorUtil.colored(section.getString("name"));
        final String permission = section.getString("permission");
        final String command = section.getString("command");

        final ItemStack itemStack = adaptItemStack(section.getString("icon"));

        final long delay = TimeUnit.MINUTES.toMillis(section.getInt("delay"));
        final int slot = section.getInt("slot");

        return new Reward(id, name, permission, command, itemStack, delay, slot);
    }

    private ItemStack adaptItemStack(String path) {
        if (!path.contains(":")) {
            return SkullUtil.getSkullByUrl(path);
        }

        final String[] splitMaterial = path.split(":");
        if (splitMaterial.length != 2) {
            return null;
        }

        final String rawMaterial = splitMaterial[0];
        final Material material = Material.matchMaterial(rawMaterial);

        return new ItemStack(material, 1, Byte.parseByte(splitMaterial[1]));
    }
}