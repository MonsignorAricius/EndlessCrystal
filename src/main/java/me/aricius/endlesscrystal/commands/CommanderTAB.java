package me.aricius.endlesscrystal.commands;

import me.aricius.endlesscrystal.EndlessCrystal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommanderTAB implements TabCompleter {
    private final EndlessCrystal plugin;
    public CommanderTAB(EndlessCrystal plugin) {
        this.plugin = plugin;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = Arrays.asList("give", "giveall", "take", "look", "pay", "set", "reset", "me");
        String input = strings[0].toLowerCase();
        List<String> completions = null;
        for (String strs : list) {
            if (s.startsWith(input)) {
                if (completions == null) {
                    completions = new ArrayList<>();
                }
                if (sender.hasPermission("endlesscrystal."+strs)) {
                    completions.add(strs);
                }
            }
        }
        if (completions != null) {
            Set<String> oldVals = new HashSet<>(completions);
            completions.clear();
            for (String str : oldVals) {
                if (str.trim().startsWith(strings[0].toLowerCase())) {
                    completions.add(str);
                }
            }
            if (strings.length == 3)
                return Collections.singletonList("<množství>");
            if (strings.length > 4)
                return Collections.emptyList();
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }
}
