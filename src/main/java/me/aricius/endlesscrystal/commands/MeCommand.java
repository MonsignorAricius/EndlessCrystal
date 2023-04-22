package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MeCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cMusíš být hráč.");
            return true;
        }
        if(!PermissionHandler.has(sender, PermissionNode.ME)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        UUID hrac = plugin.translateNameToUUID(sender.getName());
        sender.sendMessage("§7Máš "+ChatColor.of("#9999ff")+plugin.getAPI().look(hrac)+" §7krystal/ů");
        return true;
    }

}
