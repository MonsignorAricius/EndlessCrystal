package me.aricius.endlesscrystal.hook;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.event.PlayerJoinEvents;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.level.Level;

import java.util.UUID;

public class PointsPlaceholderExpansion extends PlaceholderExpansion {
    private final EndlessCrystal plugin;

    public PointsPlaceholderExpansion(EndlessCrystal plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, String placeholder) {
        if (player != null) {
            World w = BentoBox.getInstance().getIWM().getIslandWorld("EndlesSkyBlock");
            switch (placeholder.toLowerCase()) {
                case "krystaly":
                    return String.valueOf(this.plugin.getAPI().look(player.getUniqueId()));
                case "krystaly_formatted":
                    return CrystalUtils.formatPoints(this.plugin.getAPI().look(player.getUniqueId()));
                case "skyblock_size":
                    return this.getIslandSizePlaceholder(w, player);
                case "rank":
                    return String.valueOf(plugin.getIsLevelHashMap.get(player.getUniqueId()));
                case "skyblock_level":
                    return this.getIslandLevelPlaceholder(w, player);
            }
        }
        return null;
    }

    public String getIslandSizePlaceholder(World w, OfflinePlayer player) {
        if (BentoBox.getInstance().getIslands().getIsland(w, player.getUniqueId()) != null) {
            return String.valueOf(BentoBox.getInstance().getIslands().getIsland(w, player.getUniqueId()).getProtectionRange() * 2);
        } else {
            return "-";
        }
    }

    public String getIslandLevelPlaceholder(World w, OfflinePlayer player) {
        if (BentoBox.getInstance().getIslands().getIsland(w, player.getUniqueId()) != null) {
            return String.valueOf(getLevel(w, player.getUniqueId()));
        } else {
            return "0";
        }
    }

    public Long getLevel(World world, UUID uniqueId) {
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

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return this.plugin.getDescription().getName().toLowerCase();
    }

    @Override
    public String getAuthor() {
        return this.plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

}
