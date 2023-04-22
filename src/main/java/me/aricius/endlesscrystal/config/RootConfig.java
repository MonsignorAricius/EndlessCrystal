package me.aricius.endlesscrystal.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.services.IModule;
import me.aricius.endlesscrystal.storage.StorageType;
import org.bukkit.configuration.ConfigurationSection;

public class RootConfig implements IModule {
    private EndlessCrystal plugin;
    public boolean vault, hasPlayedBefore, autocompleteOnline;

    public StorageType backend;

    public RootConfig(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        final ConfigurationSection config = plugin.getConfig();
        loadSettings(config);
    }

    private void loadSettings(ConfigurationSection config) {
        vault = config.getBoolean("vault", false);
        hasPlayedBefore = config.getBoolean("restrictions.hasPlayedBefore",
                false);
        autocompleteOnline = config.getBoolean("restrictions.autocompleteOnline", false);
    }

    private void loadStorageSettings(ConfigurationSection config) {
        final String back = config.getString("storage");
        if(back.equalsIgnoreCase("YAML")) {
            backend = StorageType.YAML;
        } else {
            plugin.getLogger().severe("Storage is null! Set to YAML");
            backend = StorageType.YAML;
        }
    }

    public StorageType getStorageType() {
        return backend;
    }

    @Override
    public void starting() {
        final ConfigurationSection config = plugin.getConfig();
        final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
        defaults.put("storage", "YAML");
        defaults.put("restrictions.autocompleteOnline", false);
        defaults.put("restrictions.hasPlayedBefore", false);
        defaults.put("vault", false);
        defaults.put("version", plugin.getDescription().getVersion());
        for(final Entry<String, Object> e : defaults.entrySet()) {
            if(!config.contains(e.getKey())) {
                config.set(e.getKey(), e.getValue());
            }
        }

        plugin.saveConfig();
        loadSettings(config);
        loadStorageSettings(config);
    }

    @Override
    public void closing() {
    }
}
