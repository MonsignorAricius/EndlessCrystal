package me.aricius.endlesscrystal.services;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.models.Flag;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandHandler implements CommandExecutor {
    protected final Map<String, PointsCommand> registeredCommands = new HashMap<>();
    protected final Map<String, CommandHandler> registeredHandlers = new HashMap<>();
    protected EndlessCrystal plugin;
    protected String cmd;

    public CommandHandler(EndlessCrystal plugin, String cmd) {
        this.plugin = plugin;
        this.cmd = cmd;
    }

    public void registerCommand(String label, PointsCommand command) {
        if(registeredCommands.containsKey(label)) {
            plugin.getLogger().warning(
                    "Replacing existing command for: " + label);
        }
        registeredCommands.put(label, command);
    }

    public void unregisterCommand(String label) {
        registeredCommands.remove(label);
    }

    public void registerHandler(CommandHandler handler) {
        if(registeredHandlers.containsKey(handler.getCommand())) {
            plugin.getLogger().warning(
                    "Replacing existing handler for: " + handler.getCommand());
        }
        registeredHandlers.put(handler.getCommand(), handler);
    }

    public void unregisterHandler(String label) {
        registeredHandlers.remove(label);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        EnumMap<Flag, String> info = new EnumMap<>(Flag.class);
        info.put(Flag.TAG, EndlessCrystal.TAG);
        if(args.length == 0) {
            return noArgs(sender, command, label, info);
        } else {
            final String subcmd = args[0].toLowerCase();
            final CommandHandler handler = registeredHandlers.get(subcmd);
            if(handler != null) {
                return handler.onCommand(sender, command, label,
                        shortenArgs(args));
            }

            final PointsCommand subCommand = registeredCommands.get(subcmd);
            if(subCommand == null) {
                return unknownCommand(sender, command, label, args, info);
            }

            boolean value = true;
            try {
                value = subCommand.execute(plugin, sender, command, label,
                        shortenArgs(args));
            } catch(ArrayIndexOutOfBoundsException e) {
                sender.sendMessage(ChatColor.RED + "Missing parameters.");
            }
            return value;
        }
    }

    public abstract boolean noArgs(CommandSender sender, Command command,
            String label, EnumMap<Flag, String> info);

    public abstract boolean unknownCommand(CommandSender sender,
            Command command, String label, String[] args,
            EnumMap<Flag, String> info);

    protected String[] shortenArgs(String[] args) {
        if(args.length == 0) {
            return args;
        }
        final List<String> argList = new ArrayList<String>();
        for(int i = 1; i < args.length; i++) {
            argList.add(args[i]);
        }
        return argList.toArray(new String[0]);
    }

    public String getCommand() {
        return cmd;
    }
}
