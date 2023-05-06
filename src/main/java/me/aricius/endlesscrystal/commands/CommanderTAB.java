package me.aricius.endlesscrystal.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommanderTAB implements TabCompleter {
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
        List<String> completions2 = new ArrayList<>();
        if (strings.length == 1) {
            for (String a : list) {
                if (a.toLowerCase().startsWith(strings[0].toLowerCase()))
                    completions2.add(a);
            }
            return completions2;
        }
        /*List<String> completions3 = new ArrayList<>();
        if (strings.length == 2)
            for (Player p : Bukkit.getOnlinePlayers()) {
                completions3.add(p.getName());
                return completions3;
            }*/
        if (strings.length == 3)
            return Collections.singletonList("<množství>");
        if (strings.length >= 4)
            return Collections.emptyList();
        return completions;
    }
}
