package me.aricius.endlesscrystal.storage;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.storage.models.YAMLStorage;

import java.util.Objects;

public class StorageGenerator {
    private EndlessCrystal plugin;

    public StorageGenerator(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    public IStorage createStorageHandlerForType(StorageType type) {
        IStorage storage = null;
        if (Objects.requireNonNull(type) == StorageType.YAML) {
            storage = new YAMLStorage(plugin);
        }
        return storage;
    }
}
