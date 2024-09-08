package com.github.alwaysdarkk.rewards.common.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigValue implements ConfigurationInjectable {

    @Getter
    private static final ConfigValue instance = new ConfigValue();

    @ConfigField("database.type")
    private String databaseType;

    @ConfigField("database.mysql")
    private ConfigurationSection mysqlSection;

    @ConfigField("database.sqlite")
    private ConfigurationSection sqliteSection;

    @ConfigField("rewards")
    private ConfigurationSection rewardsSection;

    public static <T> T get(Function<ConfigValue, T> function) {
        return function.apply(instance);
    }
}