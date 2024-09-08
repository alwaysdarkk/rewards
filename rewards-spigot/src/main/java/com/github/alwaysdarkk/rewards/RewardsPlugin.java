package com.github.alwaysdarkk.rewards;

import com.github.alwaysdarkk.rewards.command.RewardsCommand;
import com.github.alwaysdarkk.rewards.common.command.CustomCommand;
import com.github.alwaysdarkk.rewards.common.registry.ConfigurationRegistry;
import com.github.alwaysdarkk.rewards.common.registry.RewardRegistry;
import com.github.alwaysdarkk.rewards.common.registry.RewardUserRegistry;
import com.github.alwaysdarkk.rewards.common.repository.RewardUserRepository;
import com.github.alwaysdarkk.rewards.common.repository.provider.RepositoryProvider;
import com.github.alwaysdarkk.rewards.listener.UserConnectionListener;
import com.github.alwaysdarkk.rewards.runnable.RewardCollectTimeRunnable;
import com.github.alwaysdarkk.rewards.view.RewardsView;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.SneakyThrows;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class RewardsPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        ConfigurationRegistry.of(this).setup();
    }

    @Override
    public void onEnable() {
        final RewardRegistry rewardRegistry = new RewardRegistry();

        final SQLConnector sqlConnector = RepositoryProvider.of(getDataFolder()).setup();
        final SQLExecutor sqlExecutor = new SQLExecutor(sqlConnector);

        final RewardUserRepository userRepository = new RewardUserRepository(sqlExecutor);
        final RewardUserRegistry userRegistry = new RewardUserRegistry();

        Bukkit.getPluginManager().registerEvents(new UserConnectionListener(userRepository, userRegistry), this);

        final ViewFrame viewFrame = ViewFrame.of(this, new RewardsView(rewardRegistry, userRegistry, userRepository))
                .register();

        registerCommands(new RewardsCommand(viewFrame));

        new RewardCollectTimeRunnable(this, userRegistry, userRepository, rewardRegistry);
    }

    @SneakyThrows
    private void registerCommands(CustomCommand... customCommands) {
        final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        for (CustomCommand customCommand : customCommands) {
            commandMap.register(customCommand.getName(), customCommand);
        }
    }
}