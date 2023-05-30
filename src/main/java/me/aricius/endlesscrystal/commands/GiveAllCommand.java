package me.aricius.endlesscrystal.commands;

import java.util.ArrayList;
import java.util.List;

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

public class GiveAllCommand implements PointsCommand {
    public String PREFIX = CrystalUtils.hex(" &f&l∣#9f9afb&lᴋ#a49af8&lʀ#a99bf4&lʏ#ae9bf1&ls#b49bee&lᴛ#b99beb&lᴀ#be9ce7&lʟ#c39ce4&lʏ&f&l≫  ");

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.GIVEALL)) {
            sender.sendMessage(PREFIX+"§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage(PREFIX+"§c/krystal giveall <množství>");
            return true;
        }
        try {
            final int anzahl = Integer.parseInt(args[0]);
            List<String> unsuccessful = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player != null) {
                    if(plugin.getAPI().give(player.getUniqueId(), anzahl)) {
                        player.sendMessage(PREFIX+"§7Dostal/a jsi "+(ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(Long.parseLong(args[1]))+" §7krystalů"));
                    } else {
                        unsuccessful.add(player.getName());
                    }
                }
            }
            sender.sendMessage(PREFIX+"§7Všichni online hráči dostali "+(ChatColor.of("#9896FD")+CrystalUtils.formatPoints(Long.parseLong(args[1]))+" §7krystalů."));
            if(!unsuccessful.isEmpty()) {
                sender.sendMessage(PREFIX+"§cNepodařilo se dát krystaly hráčům §f"+unsuccessful+"§7.");
            }
        } catch(NumberFormatException notnumber) {
            sender.sendMessage(PREFIX+"§cMnožství není číslo!");
        }
        return true;
    }

}
