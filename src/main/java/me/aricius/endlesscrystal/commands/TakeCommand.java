package me.aricius.endlesscrystal.commands;

import java.util.UUID;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TakeCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.TAKE)) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §c/krystal take <nick> <množství>");
            return true;
        }
        try {
            final int intanzahl = Integer.parseInt(args[1]);
            String playerName = null;
            if(plugin.getModuleForClass(RootConfig.class).autocompleteOnline) {
                playerName = plugin.expandName(args[0]);
            }
            if(playerName == null) {
                playerName = args[0];
            }
            UUID id = plugin.translateNameToUUID(playerName);
            if(plugin.getAPI().take(id, intanzahl)) {
                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §7Hráč §f"+playerName+" §7má teď "+ChatColor.of("#9896FD")+CrystalUtils.formatPoints(plugin.getAPI().look(plugin.translateNameToUUID(playerName)))+" §7krystalů.");
                final Player target = Bukkit.getServer().getPlayer(id);
                if(target != null && target.isOnline()) {
                    target.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §7Bylo ti odebráno "+ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(Long.parseLong(args[1]))+" §7krystalů.");
                }
            } else {
                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cTransakce selhala.");
            }

        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cMnožství není číslo!");
        }
        return true;
    }

}
