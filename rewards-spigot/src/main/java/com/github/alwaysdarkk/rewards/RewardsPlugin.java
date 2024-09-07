package com.github.alwaysdarkk.rewards;

import com.github.alwaysdarkk.rewards.common.registry.RewardRegistry;
import com.github.alwaysdarkk.rewards.common.registry.RewardUserRegistry;
import com.github.alwaysdarkk.rewards.common.repository.RewardUserRepository;
import com.github.alwaysdarkk.rewards.common.repository.provider.RepositoryProvider;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardsPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        final RewardRegistry rewardRegistry = new RewardRegistry(getConfig());

        final SQLConnector sqlConnector = RepositoryProvider.of(this).setup();
        final SQLExecutor sqlExecutor = new SQLExecutor(sqlConnector);

        final RewardUserRepository userRepository = new RewardUserRepository(sqlExecutor);
        final RewardUserRegistry userRegistry = new RewardUserRegistry();

    }
}