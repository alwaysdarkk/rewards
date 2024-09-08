package com.github.alwaysdarkk.rewards.common.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("inventory.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryValue implements ConfigurationInjectable {

    @Getter
    private static final InventoryValue instance = new InventoryValue();

    @ConfigField("settings.name")
    private String inventoryName;

    @ConfigField("settings.size")
    private int inventorySize;

    @ConfigField("icons.no-permission-icon.lore")
    private List<String> noPermissionLore;

    @ConfigField("icons.delay-icon.lore")
    private List<String> delayLore;

    @ConfigField("icons.collect-icon.lore")
    private List<String> collectLore;

    public static <T> T get(Function<InventoryValue, T> function) {
        return function.apply(instance);
    }
}