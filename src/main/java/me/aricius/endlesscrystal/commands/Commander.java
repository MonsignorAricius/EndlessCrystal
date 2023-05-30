package me.aricius.endlesscrystal.commands;

import java.util.EnumMap;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.models.Flag;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.CommandHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Commander extends CommandHandler {

    public Commander(EndlessCrystal plugin) {
        super(plugin, "points");
        registerCommand("give", new GiveCommand());
        registerCommand("giveall", new GiveAllCommand());
        registerCommand("take", new TakeCommand());
        registerCommand("look", new LookCommand());
        registerCommand("pay", new PayCommand());
        registerCommand("set", new SetCommand());
        registerCommand("reset", new ResetCommand());
        registerCommand("me", new MeCommand());
        registerCommand("cckrystal", new CrazyCratesCommand());
    }

    @Override
    public boolean noArgs(CommandSender sender, Command command, String label,
            EnumMap<Flag, String> info) {
        if(PermissionHandler.has(sender, PermissionNode.ME)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal me §7: Zobrazí tvoje krystaly");
        }
        if(PermissionHandler.has(sender, PermissionNode.GIVE)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal give <nick> <množství> §7: Dá hráči krystaly");
        }
        if(PermissionHandler.has(sender, PermissionNode.GIVEALL)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal giveall <množství> §7: Dá krystaly všem online hráčům");
        }
        if(PermissionHandler.has(sender, PermissionNode.TAKE)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal take <nick> <množství> §7: Sebere krystaly hráči");
        }
        if(PermissionHandler.has(sender, PermissionNode.PAY)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal pay <nick> <množství> §7: Pošle krystaly hráči");
        }
        if(PermissionHandler.has(sender, PermissionNode.LOOK)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal look <nick> §7: Zobrazí krystaly hráče");
        }
        if(PermissionHandler.has(sender, PermissionNode.SET)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal set <nick> <množství> §7: Nastaví množství krystalu hráče");
        }
        if(PermissionHandler.has(sender, PermissionNode.RESET)) {
            sender.sendMessage(ChatColor.of("#9896FD")+"/krystal reset <nick> §7: Resetuje krystaly hráče na 0");
        }
        return true;
    }

    @Override
    public boolean unknownCommand(CommandSender sender, Command command,
            String label, String[] args, EnumMap<Flag, String> info) {
        info.put(Flag.EXTRA, args[0]);
        sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §7Neznámý příkaz.");
        return true;
    }
}
