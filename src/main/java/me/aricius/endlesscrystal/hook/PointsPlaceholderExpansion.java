package me.aricius.endlesscrystal.hook;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;

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
