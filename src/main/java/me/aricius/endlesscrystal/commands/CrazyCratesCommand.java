package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrazyCratesCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.CRAZYCRATES)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        try {
            final String hrac = args[1];
            final String odmena = args[2];
            final String crate = args[3];
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player != null) {
                    player.sendMessage(CrystalUtils.hex("&f&l∣#fb4400&lʙ#fc3300&lᴇ#fc2200&lᴅ#fd1100&lɴ#fd0000&lʏ&f&l≫  ")+hrac+" otvoril "+CrystalUtils.hex(crate)+" a vyhral "+odmena);
                }
            }

        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§cMnožství není číslo!");
        }
        return true;
    }
}
