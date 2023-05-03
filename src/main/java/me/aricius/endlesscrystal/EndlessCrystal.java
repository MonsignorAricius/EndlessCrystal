package me.aricius.endlesscrystal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.aricius.endlesscrystal.commands.Commander;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.event.PlayerJoinEvents;
import me.aricius.endlesscrystal.hook.PointsPlaceholderExpansion;
import me.aricius.endlesscrystal.listeners.RestrictionListener;
import me.aricius.endlesscrystal.services.ExecutorModule;
import me.aricius.endlesscrystal.services.IModule;
import me.aricius.endlesscrystal.storage.StorageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EndlessCrystal extends JavaPlugin {
    public static final String TAG = "";
    private EndlessCrystalAPI api;
    private final Map<Class<? extends IModule>, IModule> modules = new HashMap<>();

    @Override
    public void onEnable() {
        RootConfig rootConfig = new RootConfig(this);
        registerModule(RootConfig.class, rootConfig);
        registerModule(ExecutorModule.class, new ExecutorModule());
        registerModule(StorageHandler.class, new StorageHandler(this));
        api = new EndlessCrystalAPI(this);
        final Commander commander = new Commander(this);
        if(getDescription().getCommands().containsKey("krystal")) {
            getCommand("krystal").setExecutor(commander);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new PointsPlaceholderExpansion(this).register();
        final PluginManager pm = getServer().getPluginManager();
        if(rootConfig.vault) {
            registerModule(EndlessCrystalVaultLayer.class,
                    new EndlessCrystalVaultLayer(this));
        }
        pm.registerEvents(new RestrictionListener(this), this);
        pm.registerEvents(new PlayerJoinEvents(this), this);
    }

    @Override
    public void onDisable() {
        List<Class<? extends IModule>> clazzez = new ArrayList<>();
        clazzez.addAll(modules.keySet());
        for(Class<? extends IModule> clazz : clazzez) {
            this.deregisterModuleForClass(clazz);
        }
    }

    public EndlessCrystalAPI getAPI() {
        return api;
    }

    public <T extends IModule> void registerModule(Class<T> clazz, T module) {
        if(clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        } else if(module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        } else if(modules.containsKey(clazz)) {
            this.getLogger().warning(
                    "Overwriting module for class: " + clazz.getName());
        }
        modules.put(clazz, module);
        module.starting();
    }

    public <T extends IModule> T deregisterModuleForClass(Class<T> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        T module = clazz.cast(modules.get(clazz));
        if(module != null) {
            module.closing();
        }
        return module;
    }

    public <T extends IModule> T getModuleForClass(Class<T> clazz) {
        return clazz.cast(modules.get(clazz));
    }

    public String expandName(String name) {
        int m = 0;
        String Result = "";
        final Collection<? extends Player> online = getServer().getOnlinePlayers();
        for(Player player : online) {
            String str = player.getName();
            if(str.matches("(?i).*" + name + ".*")) {
                m++;
                Result = str;
                if(m == 2) {
                    return null;
                }
            }
            if(str.equalsIgnoreCase(name)) {
                return str;
            }
        }
        if(m == 1)
            return Result;
        if(m > 1) {
            return null;
        }
        return name;
    }

    public UUID translateNameToUUID(String name) {
        UUID id = null;
        if(name == null) {
        	return id;
        }

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for(Player p : players) {
            if(p.getName().equalsIgnoreCase(name)) {
                id = p.getUniqueId();
                break;
            }
        }

        if(id == null && !Bukkit.getServer().getOnlineMode()) {
            id = Bukkit.getServer().getOfflinePlayer(name).getUniqueId();
        }
        return id;
    }
}
