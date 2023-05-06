package me.aricius.endlesscrystal.commands;

import java.util.HashMap;
import java.util.UUID;

import me.aricius.endlesscrystal.EndlessCrystal;
import me.aricius.endlesscrystal.config.RootConfig;
import me.aricius.endlesscrystal.permissions.PermissionHandler;
import me.aricius.endlesscrystal.permissions.PermissionNode;
import me.aricius.endlesscrystal.services.PointsCommand;
import me.aricius.endlesscrystal.utils.CrystalUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements PointsCommand {
    private final HashMap<UUID, Long> cmdcooldown;
    public PayCommand() {
        this.cmdcooldown = new HashMap<>();
    }
    @Override
    public boolean execute(EndlessCrystal plugin, CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cMusíš být hráč.");
            return true;
        }
        if(!PermissionHandler.has(sender, PermissionNode.PAY)) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §4K tomuto příkazu nemáš přístup.");
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §c/krystal give <nick> <množství>");
            return true;
        }
        try {
            final int intanzahl = Integer.parseInt(args[1]);
            if(intanzahl <= 0) {
                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cNemůžeš zaplatit 0 krystal/ů");
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
            if (intanzahl > 9999) {
                if (!cmdcooldown.containsKey(((Player) sender).getUniqueId())) {
                    cmdcooldown.put(((Player) sender).getUniqueId(), System.currentTimeMillis());
                    sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cUpozorňujeme, že nadměrné givování krystalů je zakázáno. Pokud je chceš opravdu poslat, zopakuj příkaz do 10 sekund.");
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (!cmdcooldown.get(((Player) sender).getUniqueId()).equals(1L)) {
                                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cNenapsal jsi příkaz včas, akce byla zrušena.");
                                cmdcooldown.remove(((Player) sender).getUniqueId());
                            } else {
                                cmdcooldown.remove(((Player) sender).getUniqueId());
                            }
                        }
                    }, 200L);
                    return true;
                }
            }
            if(plugin.getAPI().pay(((Player)sender).getUniqueId(), id, intanzahl)) {
                cmdcooldown.put(((Player) sender).getUniqueId(), 1L);
                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §7Hráči §f"+playerName+" §7bylo odesláno "+(ChatColor.of("#9896FD")+ CrystalUtils.formatPoints(Long.parseLong(args[1]))+" §7krystalů."));
                final Player target = Bukkit.getServer().getPlayer(id);
                if(target != null && target.isOnline()) {
                    target.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §7Dostal/a jsi "+(ChatColor.of("#9896FD")+CrystalUtils.formatPoints(Long.parseLong(args[1]))+" §7krystalů od §f"+sender.getName()+"§7."));
                }
            } else {
                sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cNemáš dostatek krystalů!");
            }
        } catch(NumberFormatException notnumber) {
            sender.sendMessage("§8["+(ChatColor.of("#9896FD")+"§lKrystaly")+"§8]"+" §cMnožství není číslo!");
        }
        return true;
    }

}
