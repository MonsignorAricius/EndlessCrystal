package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ResetCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!PermissionHandler.has(sender, PermissionNode.RESET)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage("§c/krystal reset <nick>");
            return true;
        }//ChatColor.of("#a4e6fb")+"§oHráč "+playerName+" §omá teď §b§l§o"+plugin.getAPI().look(plugin.translateNameToUUID(playerName))+ChatColor.of("#a4e6fb")+" §okrystalů.");
        if(plugin.getAPI().reset(plugin.translateNameToUUID(args[0]))) {
            sender.sendMessage(ChatColor.of("#a4e6fb")+"§oKrystaly hráče "+args[0]+" §obyly resetované");
        } else {
            sender.sendMessage("§cNastala chyba při resetu");
        }
        return true;
    }

}
