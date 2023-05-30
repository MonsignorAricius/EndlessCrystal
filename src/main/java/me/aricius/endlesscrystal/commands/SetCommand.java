package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetCommand implements PointsCommand {
    public String PREFIX = CrystalUtils.hex(" &f&l∣#9f9afb&lᴋ#a49af8&lʀ#a99bf4&lʏ#ae9bf1&ls#b49bee&lᴛ#b99beb&lᴀ#be9ce7&lʟ#c39ce4&lʏ&f&l≫  ");

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.SET)) {
            sender.sendMessage(PREFIX+"§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage(PREFIX+"§c/krystal set <nick> <množství>");
            return true;
        }
        try {
            int intanzahl = Integer.parseInt(args[1]);
            String playerName = null;
            if(plugin.getModuleForClass(RootConfig.class).autocompleteOnline) {
                playerName = plugin.expandName(args[0]);
            }
            if(playerName == null) {
                playerName = args[0];
            }
            if(plugin.getAPI().set(plugin.translateNameToUUID(playerName), intanzahl)) {
                sender.sendMessage(PREFIX+"§7Hráč §f"+playerName+" §7má teď "+ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(plugin.getAPI().look(plugin.translateNameToUUID(playerName)))+" §7krystalů.");
            } else {
                sender.sendMessage(PREFIX+"§cTransakce selhala.");
            }

        } catch(NumberFormatException notnumber) {
            sender.sendMessage(PREFIX+"§cMnožství není číslo!");
        }
        return true;
    }

}
