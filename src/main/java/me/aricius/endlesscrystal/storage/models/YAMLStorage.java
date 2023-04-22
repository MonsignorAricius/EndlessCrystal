package me.aricius.endlesscrystal.storage.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.services.ExecutorModule;
import me.aricius.endlesscrystal.storage.IStorage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLStorage implements IStorage {
    private EndlessCrystal plugin;
    private File file;
    private YamlConfiguration config;
    private SaveTask saveTask;
    private static final String POINTS_SECTION = "Krystaly.";

    public YAMLStorage(EndlessCrystal pp) {
        plugin = pp;
        file = new File(plugin.getDataFolder().getAbsolutePath()
                + "/storage.yml");
        config = YamlConfiguration.loadConfiguration(file);
        saveTask = new SaveTask();
        save();
    }

    public void save() {
        plugin.getModuleForClass(ExecutorModule.class).submit(saveTask);
    }

    public void reload() {
        try {
            config.load(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean setPoints(String id, int points) {
        config.set(POINTS_SECTION + id, points);
        save();
        return true;
    }

    @Override
    public int getPoints(String id) {
        int points = config.getInt(POINTS_SECTION + id, 0);
        return points;
    }

    @Override
    public boolean playerEntryExists(String id) {
        return config.contains(POINTS_SECTION + id);
    }

    @Override
    public boolean removePlayer(String id) {
        config.set(POINTS_SECTION + id, null);
        save();
        return true;
    }

    @Override
    public Collection<String> getPlayers() {
    	Collection<String> players = Collections.emptySet();
    	
    	if(config.isConfigurationSection("Krystaly")) {
    		players = config.getConfigurationSection("Krystaly").getKeys(false);
    	}
        return players;
    }

    @Override
    public boolean destroy() {
        Collection<String> sections = config.getKeys(false);
        for(String section: sections) {
            config.set(section, null);
        }
        save();
        return true;
    }

    @Override
    public boolean build() {
        boolean success = false;
        try {
            success = file.createNewFile();
        } catch(IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create storage file!", e);
        }
        return success;
    }

    private final class SaveTask implements Runnable {
        @Override
        public void run() {
            try {
                config.save(file);
            } catch(IOException e1) {
                plugin.getLogger().warning(
                        "File I/O Exception on saving storage.yml");
                e1.printStackTrace();
            }
        }
    }

}
