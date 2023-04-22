package me.aricius.endlesscrystal.commands;

import java.util.ArrayList;
import java.util.List;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAllCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.GIVEALL)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage("§7/krystaly giveall <množství>");
            return true;
        }
        try {
            final int anzahl = Integer.parseInt(args[0]);
            List<String> unsuccessful = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player != null) {
                    if(plugin.getAPI().give(player.getUniqueId(), anzahl)) {
                        player.sendMessage("§7Dostal/a jsi "+ ChatColor.of("#9999ff")+args[0]+" §7krystal/ů od §f"+sender.getName());
                    } else {
                        unsuccessful.add(player.getName());
                    }
                }
            }
            sender.sendMessage("§7Všichni online hráči dostali "+ChatColor.of("#9999ff")+args[0]+" §7krystal/ů");
            if(!unsuccessful.isEmpty()) {
                sender.sendMessage("§cNepodařilo se dát krystaly hráčům §f"+unsuccessful);
            }
        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§cMnožství není číslo!");
        }
        return true;
    }

}
