package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MeCommand implements PointsCommand {
    public String PREFIX = CrystalUtils.hex(" &f&l∣#9f9afb&lᴋ#a49af8&lʀ#a99bf4&lʏ#ae9bf1&ls#b49bee&lᴛ#b99beb&lᴀ#be9ce7&lʟ#c39ce4&lʏ&f&l≫  ");

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(PREFIX+"§cMusíš být hráč.");
            return true;
        }
        if(!PermissionHandler.has(sender, PermissionNode.ME)) {
            sender.sendMessage(PREFIX+"§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        UUID hrac = plugin.translateNameToUUID(sender.getName());
        sender.sendMessage(PREFIX+"§7Máš "+ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(plugin.getAPI().look(hrac))+" §7krystalů.");
        return true;
    }
}