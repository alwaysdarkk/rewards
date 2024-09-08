package com.github.alwaysdarkk.rewards.common.registry;

import com.github.alwaysdarkk.rewards.common.configuration.ConfigValue;
import com.github.alwaysdarkk.rewards.common.configuration.InventoryValue;
import com.github.alwaysdarkk.rewards.common.configuration.MessagesValue;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data(staticConstructor = "of")
public class ConfigurationRegistry {

    private final Plugin plugin;

    public void setup() {
        final BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);
        configurationInjector.saveDefaultConfiguration(plugin, "config.yml", "inventory.yml", "messages.yml");
        configurationInjector.injectConfiguration(
                ConfigValue.instance(), InventoryValue.instance(), MessagesValue.instance());
    }
}