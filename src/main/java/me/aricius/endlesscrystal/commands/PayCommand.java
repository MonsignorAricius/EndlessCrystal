package me.aricius.endlesscrystal.commands;

import java.util.UUID;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements PointsCommand {

    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cMusíš být hráč.");
            return true;
        }
        if(!PermissionHandler.has(sender, PermissionNode.PAY)) {
            sender.sendMessage("§4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage("§c/krystal give <nick> <množství>");
            return true;
        }
        try {
            final int intanzahl = Integer.parseInt(args[1]);
            if(intanzahl <= 0) {
                sender.sendMessage("§cNemůžeš zaplatit 0 krystal/ů");
                return true;
            }
            String playerName = null;
            if(plugin.getModuleForClass(RootConfig.class).autocompleteOnline) {
                playerName = plugin.expandName(args[0]);
            }
            if(playerName == null) {
                playerName = args[0];
            }
            UUID id = plugin.translateNameToUUID(playerName);
            if(plugin.getAPI().pay(((Player)sender).getUniqueId(), id, intanzahl)) {
                sender.sendMessage(ChatColor.of("#a4e6fb")+"§oHráči "+playerName+ChatColor.of("#a4e6fb")+" §obylo odesláno "+"§b§l§o"+args[1]+ChatColor.of("#a4e6fb")+" §okrystalů.");
                final Player target = Bukkit.getServer().getPlayer(id);
                if(target != null && target.isOnline()) {
                    target.sendMessage(ChatColor.of("#a4e6fb")+"§oDostal/a jsi "+"§b§l§o"+args[1]+ChatColor.of("#a4e6fb")+" §okrystalů od "+ChatColor.of("#a4e6fb")+"§o"+sender.getName()+".");
                }
            } else {
                sender.sendMessage("§cNemáš dostatek krystalů!");
            }
        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§cMnožství není číslo!");
        }
        return true;
    }

}
