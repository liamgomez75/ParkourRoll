package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import com.gmail.liamgomez75.parkourroll.localisation.Localisable;
import com.gmail.liamgomez75.parkourroll.localisation.Localisation;
import com.gmail.liamgomez75.parkourroll.localisation.LocalisationEntry;
import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
    private ParkourRoll plugin;
    
    
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
                } else if ((args[0].equalsIgnoreCase("level"))) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        World world = p.getWorld();
                        int lvlNum = LevelConfigUtils.getPlayerLevel(p,world,plugin);
                        int expNum = EXPConfigUtils.getPlayerExp(p,world,plugin);
                        int reqExp = plugin.getConfig().getInt("Level." + lvlNum + ".Exp Required");
                        sender.sendMessage("You are level " + lvlNum + ".");
                        sender.sendMessage("Exp: " + expNum + "/" + reqExp);
                        return true;
                    } else {
                        sender.sendMessage("You can't run that command from the console!");
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