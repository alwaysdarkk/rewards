package com.github.alwaysdarkk.rewards.common.registry;

import com.github.alwaysdarkk.rewards.common.adapter.RewardConfigurationAdapter;
import com.github.alwaysdarkk.rewards.common.data.Reward;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RewardRegistry {

    private final Object2ObjectMap<String, Reward> rewardMap;

    public RewardRegistry(FileConfiguration configuration) {
        this.rewardMap = new Object2ObjectOpenHashMap<>();

        new RewardConfigurationAdapter(configuration)
                .getRewards().stream().filter(Objects::nonNull).forEach(this::insert);
    }

    public void insert(Reward reward) {
        rewardMap.put(reward.getId(), reward);
    }

    public Reward find(String id) {
        return rewardMap.get(id);
    }

    public List<Reward> findAll() {
        return new ArrayList<>(rewardMap.values());
    }
}