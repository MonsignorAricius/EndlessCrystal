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

import java.util.UUID;

public class LookCommand implements PointsCommand {
    public String PREFIX = CrystalUtils.hex(" &f&l∣#9f9afb&lᴋ#a49af8&lʀ#a99bf4&lʏ#ae9bf1&ls#b49bee&lᴛ#b99beb&lᴀ#be9ce7&lʟ#c39ce4&lʏ&f&l≫  ");

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.LOOK)) {
            sender.sendMessage(PREFIX+"§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage(PREFIX+"§c/krystal look <nick>");
            return true;
        }
        String playerName = null;
        if(plugin.getModuleForClass(RootConfig.class).autocompleteOnline) {
            playerName = plugin.expandName(args[0]);
        }
        if(playerName == null) {
            playerName = args[0];
        }
        UUID target = plugin.translateNameToUUID(args[0]);
        sender.sendMessage(PREFIX+"§7Hráč §f"+playerName+" §7má "+ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(plugin.getAPI().look(target))+" §7krystalů.");
        return true;
    }

}
