package me.aricius.endlesscrystal.services;

import me.aricius.endlesscrystal.EndlessCrystal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface PointsCommand {
    boolean execute(final EndlessCrystal plugin, final CommandSender sender,
                    final Command command, final String label, String[] args);
}
