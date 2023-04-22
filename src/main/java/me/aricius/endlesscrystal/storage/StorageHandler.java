package me.aricius.endlesscrystal.storage;

import java.util.Collection;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.services.IModule;

public class StorageHandler implements IStorage, IModule {
    private EndlessCrystal plugin;
    private StorageGenerator generator;
    IStorage storage;

    public StorageHandler(EndlessCrystal plugin) {
        this.plugin = plugin;

    }

    @Override
    public int getPoints(String name) {
        return storage.getPoints(name);
    }

    @Override
    public boolean setPoints(String name, int points) {
        return storage.setPoints(name, points);
    }

    @Override
    public boolean playerEntryExists(String id) {
        return storage.playerEntryExists(id);
    }

    @Override
    public boolean removePlayer(String id) {
        return storage.removePlayer(id);
    }

    @Override
    public Collection<String> getPlayers() {
        return storage.getPlayers();
    }

    @Override
    public void starting() {
        generator = new StorageGenerator(plugin);
        storage = generator.createStorageHandlerForType(plugin
                .getModuleForClass(RootConfig.class).getStorageType());
    }

    @Override
    public void closing() {
    }

    @Override
    public boolean destroy() {
        return storage.destroy();
    }

    @Override
    public boolean build() {
        return storage.build();
    }

}
