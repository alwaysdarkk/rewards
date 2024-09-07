package com.github.alwaysdarkk.rewards.common.configuration;

import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private final Plugin plugin;
    private final File file;
    private final String fileName;

    @Delegate
    private FileConfiguration fileConfiguration;

    private YamlConfig(Plugin plugin, String fileName) {
        if (!fileName.endsWith(".yml")) {
            fileName += ".yml";
        }

        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);

        load();
    }

    public static YamlConfig of(Plugin plugin) {
        return of(plugin, "config");
    }

    public static YamlConfig of(Plugin plugin, String fileName) {
        return new YamlConfig(plugin, fileName);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}