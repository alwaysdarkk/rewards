package com.github.alwaysdarkk.rewards.listener;

import com.github.alwaysdarkk.rewards.common.data.RewardUser;
import com.github.alwaysdarkk.rewards.common.registry.RewardUserRegistry;
import com.github.alwaysdarkk.rewards.common.repository.RewardUserRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class UserConnectionListener implements Listener {

    private final RewardUserRepository userRepository;
    private final RewardUserRegistry userRegistry;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        RewardUser user = userRepository.find(player.getName());
        if (user == null) {
            user = new RewardUser(player.getName());
            userRepository.insert(user);
        }

        userRegistry.insert(user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final RewardUser user = userRegistry.find(player.getName());

        if (user == null) {
            return;
        }

        userRegistry.remove(user);
    }
}