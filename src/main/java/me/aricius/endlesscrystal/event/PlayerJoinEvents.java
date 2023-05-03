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
    public Map<UUID, Long> getIsLevelHashMap = new HashMap<>();

    public PlayerJoinEvents(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        World w = BentoBox.getInstance().getIWM().getIslandWorld("EndlesSkyBlock");
        if (!this.getIsLevelHashMap.containsKey(uuid) && BentoBox.getInstance().getIslands().getIsland(p.getWorld(), uuid) == null) {
            this.getIsLevelHashMap.put(uuid, 0L);
            plugin.getLogger().warning("Player "+p+" not in map, no island, put success");
        } else if (!this.getIsLevelHashMap.containsKey(uuid) && BentoBox.getInstance().getIslands().getIsland(p.getWorld(), uuid) != null){
            Long isLvl = this.getIsLevel(w, uuid);
            this.getIsLevelHashMap.put(uuid, isLvl);
            plugin.getLogger().warning("Player "+p+" not in map, has island, put success. Value: "+isLvl);
        } else {
            plugin.getLogger().warning("Player already there. "+this.getIsLevelHashMap.get(uuid));
            plugin.getLogger().warning("HashMap : "+this.getIsLevelHashMap);
        }
    }
    @EventHandler
    public void onPlayerCalculateLevel(IslandLevelCalculatedEvent e) {
        Long newLvl = e.getLevel();
        UUID uuid = e.getTargetPlayer();
        if (!this.getIsLevelHashMap.containsKey(uuid)) {
            //WTF?
            this.getIsLevelHashMap.put(uuid, newLvl);
            plugin.getLogger().warning("Player somehow not in map, level calculate, put success. Value: "+newLvl);
            plugin.getLogger().warning("Player value now: "+this.getIsLevelHashMap.get(uuid));
        } else {
            this.getIsLevelHashMap.replace(uuid, newLvl);
            plugin.getLogger().warning("Player level calculate, replaced with new value of "+newLvl);
            plugin.getLogger().warning("Player value now: "+this.getIsLevelHashMap.get(uuid));
            plugin.getLogger().warning("HashMap : "+this.getIsLevelHashMap);
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
}
