package com.github.alwaysdarkk.rewards.common.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RewardUser {

    private final String playerName;
    private final Map<String, Long> rewardMap;

    public RewardUser(String playerName) {
        this.playerName = playerName;
        this.rewardMap = new HashMap<>();
    }

    public void addReward(Reward reward) {
        final long expireDate = System.currentTimeMillis() + reward.getDelay();
        rewardMap.put(reward.getId(), expireDate);
    }

    public void removeReward(Reward reward) {
        rewardMap.remove(reward.getId());
    }

    public boolean canCollect(Reward reward) {
        final String rewardId = reward.getId();
        if (!rewardMap.containsKey(rewardId)) {
            return true;
        }

        final long expireDate = rewardMap.get(rewardId);
        return System.currentTimeMillis() >= expireDate;
    }
}