package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import com.gmail.liamgomez75.parkourroll.localisation.Localisable;
import com.gmail.liamgomez75.parkourroll.localisation.Localisation;
import com.gmail.liamgomez75.parkourroll.localisation.LocalisationEntry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Parkour Roll plugin for Bukkit.
 *
 * @author Liam Gomez <liamgomez75.gmail.com>
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public class ParkourRoll extends JavaPlugin implements Localisable {

    /**
     * The current localisation for the plugin.
     */
    private Localisation localisation = new Localisation(this);
    
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
            sender.sendMessage(localisation.get(LocalisationEntry.MSG_CONFIG_RELOADED));
            return true;
        } else {
            sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED));
        }
        return false;
    }

    @Override
    public Localisation getLocalisation() {
        return localisation;
    }

    @Override
    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }
}