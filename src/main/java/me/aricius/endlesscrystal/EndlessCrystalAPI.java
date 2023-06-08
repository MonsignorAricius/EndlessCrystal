package me.aricius.endlesscrystal;

import java.util.UUID;

import me.aricius.endlesscrystal.event.PlayerPointsChangeEvent;
import me.aricius.endlesscrystal.event.PlayerPointsResetEvent;
import me.aricius.endlesscrystal.storage.StorageHandler;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndlessCrystalAPI {
    private final EndlessCrystal plugin;

    public EndlessCrystalAPI(EndlessCrystal p) {
        this.plugin = p;
    }

    public boolean give(UUID playerId, int amount) {
    	if(playerId == null) {
    		return false;
    	}
        PlayerPointsChangeEvent event = new PlayerPointsChangeEvent(playerId,
                amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            final int total = look(playerId)
                    + event.getChange();
            return plugin.getModuleForClass(StorageHandler.class).setPoints(
                    playerId.toString(), total);
        }
        return false;
    }

    public boolean take(UUID playerId, int amount) {
        final int points = look(playerId);
        int take = amount;
        if(take > 0) {
            take *= -1;
        }
        if((points + take) < 0) {
            return false;
        }
        return give(playerId, take);
    }

    public int look(UUID playerId) {
    	int amount = 0;
    	if(playerId != null) {
    		amount = plugin.getModuleForClass(StorageHandler.class).getPoints(playerId.toString());
    	}
    	return amount;
    }

    public boolean pay(UUID source, UUID target, int amount) {
        if(take(source, amount)) {
            if(give(target, amount)) {
                return true;
            } else {
                give(source, amount);
            }
        }
        return false;
    }

    public boolean set(UUID playerId, int amount) {
    	if(playerId == null) {
    		return false;
    	}
        PlayerPointsChangeEvent event = new PlayerPointsChangeEvent(playerId,
                amount - look(playerId));
        plugin.getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            return plugin.getModuleForClass(StorageHandler.class).setPoints(
                    playerId.toString(),
                    look(playerId) + event.getChange());
        }
        return false;
    }

    public boolean reset(UUID playerId) {
    	if(playerId == null) {
    		return false;
    	}
        PlayerPointsResetEvent event = new PlayerPointsResetEvent(playerId);
        plugin.getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            return plugin.getModuleForClass(StorageHandler.class).setPoints(
                    playerId.toString(), event.getChange());
        }
        return false;
    }
}
