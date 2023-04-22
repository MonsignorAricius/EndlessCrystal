package me.aricius.endlesscrystal.listeners;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.event.PlayerPointsChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RestrictionListener implements Listener {
    
    private EndlessCrystal plugin;
    
    public RestrictionListener(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void validatePlayerChangeEvent(PlayerPointsChangeEvent event) {
        RootConfig config = plugin.getModuleForClass(RootConfig.class);
        if(config.hasPlayedBefore) {
            Player player = plugin.getServer().getPlayer(event.getPlayerId());
            if(player != null) {
                event.setCancelled(!player.hasPlayedBefore());
            }
        }
    }
}
