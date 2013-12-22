package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.experience.Experience;
import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import com.gmail.liamgomez75.parkourroll.localisation.Localisable;
import com.gmail.liamgomez75.parkourroll.localisation.Localisation;
import com.gmail.liamgomez75.parkourroll.localisation.LocalisationEntry;
import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.RateConfigUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
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
                } else if ((args[0].equalsIgnoreCase("level"))) {
                    if (sender instanceof Player) {
                        final Player p = (Player) sender;
                        final World world = p.getWorld();
                        final int lvlNum = LevelConfigUtils.getPlayerLevel(p, world, this);
                        final int expNum = EXPConfigUtils.getPlayerExp(p, world, this);
                        final int reqExp = Experience.getRequiredExp(this, lvlNum);
                        final int rate = RateConfigUtils.getPlayerRate(p, world, this);
                        sender.sendMessage("You are level " + lvlNum + ".");
                        sender.sendMessage("Exp: " + expNum + "/" + reqExp);
                        sender.sendMessage("Exp Rate: " + rate); // TODO remove?
                        return true;
                    } else {
                        sender.sendMessage("You can't run that command from the console!");
                        return true;
                    }
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
        } else {
            sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED));
        }
        return true;
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