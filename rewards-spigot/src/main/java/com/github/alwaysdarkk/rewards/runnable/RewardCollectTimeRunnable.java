package com.github.alwaysdarkk.rewards.runnable;

import com.github.alwaysdarkk.rewards.common.configuration.MessagesValue;
import com.github.alwaysdarkk.rewards.common.data.Reward;
import com.github.alwaysdarkk.rewards.common.registry.RewardRegistry;
import com.github.alwaysdarkk.rewards.common.registry.RewardUserRegistry;
import com.github.alwaysdarkk.rewards.common.repository.RewardUserRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;

public class RewardCollectTimeRunnable implements Runnable {

    private final RewardUserRegistry userRegistry;
    private final RewardUserRepository userRepository;

    private final RewardRegistry rewardRegistry;

    public RewardCollectTimeRunnable(
            Plugin plugin,
            RewardUserRegistry userRegistry,
            RewardUserRepository userRepository,
            RewardRegistry rewardRegistry) {
        this.userRegistry = userRegistry;
        this.userRepository = userRepository;

        this.rewardRegistry = rewardRegistry;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        userRegistry.findAll().stream().filter(Objects::nonNull).forEach(user -> {
            final List<String> canCollectRewards = user.getCanCollectRewards();
            if (canCollectRewards.isEmpty()) {
                return;
            }

            canCollectRewards.forEach(rewardId -> {
                final Reward reward = rewardRegistry.find(rewardId);
                if (reward == null) {
                    return;
                }

                user.removeReward(reward);
                userRepository.update(user);

                final String timeMessage =
                        MessagesValue.get(MessagesValue::collectTime).replace("{reward-name}", reward.getName());

                final Player player = Bukkit.getPlayer(user.getPlayerName());
                player.sendMessage(timeMessage);
            });
        });
    }
}
