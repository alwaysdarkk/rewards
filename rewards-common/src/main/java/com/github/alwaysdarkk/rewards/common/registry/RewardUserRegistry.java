package com.github.alwaysdarkk.rewards.common.registry;

import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;

public class RewardUserRegistry {

    private final Object2ObjectMap<String, RewardUser> userMap = new Object2ObjectOpenHashMap<>();

    public void insert(RewardUser user) {
        userMap.put(user.getPlayerName(), user);
    }

    public void remove(RewardUser user) {
        userMap.remove(user.getPlayerName());
    }

    public RewardUser find(String playerName) {
        return userMap.get(playerName);
    }

    public List<RewardUser> findAll() {
        return new ArrayList<>(userMap.values());
    }
}