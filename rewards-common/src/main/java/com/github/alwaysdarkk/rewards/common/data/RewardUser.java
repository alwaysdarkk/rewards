package com.github.alwaysdarkk.rewards.common.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public long getRemainingTime(Reward reward) {
        final String rewardId = reward.getId();
        if (!rewardMap.containsKey(rewardId)) {
            return 0L;
        }

        final long expireDate = rewardMap.get(rewardId);
        return expireDate - System.currentTimeMillis();
    }

    public boolean canCollect(String rewardId) {
        if (!rewardMap.containsKey(rewardId)) {
            return true;
        }

        final long expireDate = rewardMap.get(rewardId);
        return System.currentTimeMillis() >= expireDate;
    }

    public List<String> getCanCollectRewards() {
        return rewardMap.keySet().stream()
                .filter(Objects::nonNull)
                .filter(this::canCollect)
                .collect(Collectors.toList());
    }
}