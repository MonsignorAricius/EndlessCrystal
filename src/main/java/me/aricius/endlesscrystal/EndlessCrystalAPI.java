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

    @Deprecated
    public boolean give(String playerName, int amount) {
    	boolean success = false;
    	if(playerName != null) {
    		success = give(plugin.translateNameToUUID(playerName), amount);
    	}
    	return success;
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
    
    @Deprecated
    public boolean take(String playerName, int amount) {
    	boolean success = false;
    	if(playerName != null) {
    		success = take(plugin.translateNameToUUID(playerName), amount);
    	}
    	return success;
    }

    public int look(UUID playerId) {
    	int amount = 0;
    	if(playerId != null) {
    		amount = plugin.getModuleForClass(StorageHandler.class).getPoints(playerId.toString());
    	}
    	return amount;
    }
    
    @Deprecated
    public int look(String playerName) {
        return look(plugin.translateNameToUUID(playerName));
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
    
    @Deprecated
    public boolean pay(String sourceName, String targetName, int amount) {
    	boolean success = false;
    	if(sourceName != null && targetName != null) {
    		success = pay(plugin.translateNameToUUID(sourceName), plugin.translateNameToUUID(targetName), amount);
    	}
    	return success;
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
    
    @Deprecated
    public boolean set(String playerName, int amount) {
    	boolean success = false;
    	if(playerName != null) {
    		success = set(plugin.translateNameToUUID(playerName), amount);
    	}
    	return success;
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
    
    @Deprecated
    public boolean reset(String playerName, int amount) {
    	boolean success = false;
    	if(playerName != null) {
    		success = reset(plugin.translateNameToUUID(playerName));
    	}
    	return success;
    }
}
