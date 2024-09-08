package com.github.alwaysdarkk.rewards.common.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("messages.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesValue implements ConfigurationInjectable {

    @Getter
    private static final MessagesValue instance = new MessagesValue();

    @ConfigField("messages.no-permission")
    private String noPermission;

    @ConfigField("messages.in-delay")
    private String inDelay;

    @ConfigField("messages.reward-collected")
    private String rewardCollected;

    @ConfigField("messages.collect-time")
    private String collectTime;

    public static <T> T get(Function<MessagesValue, T> function) {
        return function.apply(instance);
    }
}