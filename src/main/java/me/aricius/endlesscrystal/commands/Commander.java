package me.aricius.endlesscrystal.commands;

import java.util.EnumMap;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.models.Flag;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.CommandHandler;
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
    }

    @Override
    public boolean noArgs(CommandSender sender, Command command, String label,
            EnumMap<Flag, String> info) {
        if(PermissionHandler.has(sender, PermissionNode.ME)) {
            sender.sendMessage("§b/krystal me §f: Zobrazí tvoje krystaly");
        }
        if(PermissionHandler.has(sender, PermissionNode.GIVE)) {
            sender.sendMessage("§b/krystal give <nick> <množství> §f: Dá hráči krystaly");
        }
        if(PermissionHandler.has(sender, PermissionNode.GIVEALL)) {
            sender.sendMessage("§b/krystal giveall <množství> §f: Dá krystaly všem online hráčům");
        }
        if(PermissionHandler.has(sender, PermissionNode.TAKE)) {
            sender.sendMessage("§b/krystal take <nick> <množství> §f: Sebere krystaly hráči");
        }
        if(PermissionHandler.has(sender, PermissionNode.PAY)) {
            sender.sendMessage("§b/krystal pay <nick> <množství> §f: Pošle krystaly hráči");
        }
        if(PermissionHandler.has(sender, PermissionNode.LOOK)) {
            sender.sendMessage("§b/krystal look <nick> §f: Zobrazí krystaly hráče");
        }
        if(PermissionHandler.has(sender, PermissionNode.SET)) {
            sender.sendMessage("§b/krystal set <nick> <množství> §f: Nastaví množství krystalu hráče");
        }
        if(PermissionHandler.has(sender, PermissionNode.RESET)) {
            sender.sendMessage("§b/krystal reset <nick> §f: Resetuje krystaly hráče na 0");
        }
        return true;
    }

    @Override
    public boolean unknownCommand(CommandSender sender, Command command,
            String label, String[] args, EnumMap<Flag, String> info) {
        info.put(Flag.EXTRA, args[0]);
        sender.sendMessage("§b[Krystaly] Neznámý příkaz.");
        return true;
    }

}
