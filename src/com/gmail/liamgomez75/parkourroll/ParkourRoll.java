package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Parkour Roll plugin for Bukkit.
 *
 * @author Liam Gomez
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public class ParkourRoll extends JavaPlugin {

    @Override
    public void onEnable() {
        final DamageListener fallDamage = new DamageListener(this);
        getServer().getPluginManager().registerEvents(fallDamage, this);
        saveDefaultConfig();
    }

    /**
     * Executes the command.
     *
     * @param sender            sender of the command
     * @param cmd               command sent
     * @param commandLabel      exact command string sent
     * @param args              arguments given with the command
     * @return                  if the command was executed correctly
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Parkourroll")) {
            if (args.length > 0) {
                if ((args[0].equalsIgnoreCase("reload"))) {
                    return reload(sender);
                }
            }
        }
        return false;
    }

    /**
     * Reloads the configuration.
     *
     * @param sender    sender of the command
     * @return          if the sender has the correct permissions and reloaded
     *                  the configuration correctly
     */
    public boolean reload(CommandSender sender) {
        if (sender.hasPermission("pkr.admin")) {
            reloadConfig();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Configuration reloaded.");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
        }
        return false;
    }
}