package me.aricius.endlesscrystal.event;

import me.aricius.endlesscrystal.EndlessCrystal;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.level.Level;
import world.bentobox.level.events.IslandLevelCalculatedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerJoinEvents implements Listener {
    private EndlessCrystal plugin;

    public PlayerJoinEvents(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        long n = 2L;
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        World w = BentoBox.getInstance().getIWM().getIslandWorld("EndlesSkyBlock");
        if (!plugin.getIsLevelHashMap.containsKey(uuid) && BentoBox.getInstance().getIslands().getIsland(p.getWorld(), uuid) == null) {
            plugin.getIsLevelHashMap.put(uuid, 0L);
        } else if (BentoBox.getInstance().getIslands().getIsland(p.getWorld(), uuid) != null) {
            Long isLvl = this.getIsLevel(w, uuid);
            plugin.getIsLevelHashMap.put(uuid, removeLastNDigits(isLvl, n));
        }
    }
    @EventHandler
    public void onPlayerCalculateLevel(IslandLevelCalculatedEvent e) {
        long n = 2L;
        Long newLvl = e.getLevel();
        UUID uuid = e.getTargetPlayer();
        if (!plugin.getIsLevelHashMap.containsKey(uuid)) {
            plugin.getIsLevelHashMap.put(uuid, removeLastNDigits(newLvl, n));
        } else {
            plugin.getIsLevelHashMap.replace(uuid, removeLastNDigits(newLvl, n));
        }
    }
    public Long getIsLevel(World world, UUID uniqueId) {
        String name = BentoBox.getInstance().getIWM().getAddon(world).map(g -> g.getDescription().getName()).orElse("");
        return BentoBox.getInstance().getAddonsManager().getAddonByName("Level")
                .map(l -> {
                    final Level addon = (Level) l;
                    if (!name.isEmpty() && !addon.getSettings().getGameModes().contains(name)) {
                        return addon.getIslandLevel(world, uniqueId);
                    }
                    return null;
                }).orElse(null);
    }
    public long removeLastNDigits(long x, long n) {
        return (long) (x / Math.pow(10, n));
    }
}
