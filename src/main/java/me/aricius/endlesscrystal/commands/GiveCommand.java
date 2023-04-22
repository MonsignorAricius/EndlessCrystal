package me.aricius.endlesscrystal.commands;

import java.util.UUID;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.GIVE)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage("§7/krystaly give <nick> <množství>");
            return true;
        }
        try {
            final int anzahl = Integer.parseInt(args[1]);
            String playerName = null;
            if(plugin.getModuleForClass(RootConfig.class).autocompleteOnline) {
                playerName = plugin.expandName(args[0]);
            }
            if(playerName == null) {
                playerName = args[0];
            }
            UUID id = plugin.translateNameToUUID(playerName);
            if(plugin.getAPI().give(id, anzahl)) {
                sender.sendMessage("§7Hráč §f"+playerName+" §7obdržel "+ ChatColor.of("#9999ff")+args[1]+" §7krystal/ů");
                final Player target = Bukkit.getServer().getPlayer(id);
                if(target != null && target.isOnline()) {
                    target.sendMessage("§7Dostal/a jsi "+ChatColor.of("#9999ff")+args[1]+" §7krystal/ů od §f"+sender.getName());
                }
            } else {
                sender.sendMessage("§cTransakce selhala");
            }
        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§cMnožství není číslo!");
        }
        return true;
    }

}
