package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.experience.Experience;
import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import com.gmail.liamgomez75.parkourroll.localisation.Localisable;
import com.gmail.liamgomez75.parkourroll.localisation.Localisation;
import com.gmail.liamgomez75.parkourroll.localisation.LocalisationEntry;
import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.RateConfigUtils;
import org.bukkit.ChatColor;
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
                        
                        if (args.length > 2) {
                            
                            final String p = args[1];
                            
                            final String world = args[2];
                            
                            if (LevelConfigUtils.getPlayerLevel(p, world, this, (Player) sender) > 0 && EXPConfigUtils.getPlayerExp(p, world, this, (Player) sender) >= 0 ) {
                                
                                final int lvlNum = LevelConfigUtils.getPlayerLevel(p, world, this, (Player) sender);
                                
                                final int expNum = EXPConfigUtils.getPlayerExp(p, world, this, (Player) sender);
                                
                                final int reqExp = Experience.getRequiredExp(this, lvlNum);
                                
                                sender.sendMessage(ChatColor.GRAY + args[1] + " is level " + lvlNum + ".");
                                
                                sender.sendMessage(ChatColor.GRAY + "Exp: " + expNum + "/" + reqExp);
                                
                                return true;
                            
                            }    
                            
                        
                        } else {
                            
                            final Player p = (Player) sender;
                        
                            final World world = p.getWorld();
                        
                            final int lvlNum = LevelConfigUtils.getPlayerLevel(p, world, this);
                        
                            final int expNum = EXPConfigUtils.getPlayerExp(p, world, this);
                        
                            final int reqExp = Experience.getRequiredExp(this, lvlNum);
                        
                            final int rate = RateConfigUtils.getPlayerRate(p, world, this);
                       
                            sender.sendMessage(ChatColor.GRAY + "You are level " + lvlNum + ".");
                        
                            sender.sendMessage(ChatColor.GRAY + "Exp: " + expNum + "/" + reqExp);
                       
                            return true;
                        
                        }
                        
                   
                    } else if (args.length > 2) {
                           
                        final String p = args[1];
                           
                            final String world = args[2];
                          
                            if (LevelConfigUtils.getPlayerLevel(p, world, this, sender) > 0 && EXPConfigUtils.getPlayerExp(p, world, this, sender) >= 0 ) {
                               
                                final int lvlNum = LevelConfigUtils.getPlayerLevel(p, world, this, sender);
                                
                                final int expNum = EXPConfigUtils.getPlayerExp(p, world, this, sender);
                                
                                final int reqExp = Experience.getRequiredExp(this, lvlNum);
                               
                                sender.sendMessage(ChatColor.GRAY + args[1] + " is level " + lvlNum + ".");
                                
                                sender.sendMessage(ChatColor.GRAY + "Exp: " + expNum + "/" + reqExp);
                              
                                return true;
                           
                            }
                    
                    } else {
                        
                        sender.sendMessage("You can't run that command from the console!");
                        
                        return true;
                   
                    }
               
                } else if(args[0].equalsIgnoreCase("setlevel")) {
                    
                    if (args.length > 3) {
                        
                        if ((sender instanceof Player) && (sender.hasPermission("pkr.admin"))) {
                            
                            final String target = args[1];
                            
                            final String worldName = args [2];
                            
                            if (target != null) {
                            
                                try {
                                    
                                    int level = Integer.parseInt(args[3]);
                                    
                                    if(level <= 100 && level >= 1) {
                                        
                                        LevelConfigUtils.setPlayerLevel(target, worldName,level,this,(Player) sender);
                                    
                                    } else {
                                        sender.sendMessage(ChatColor.RED + "Only levels 1 through 100 are allowed!");
                                    }
                                    
                                    if(LevelConfigUtils.getPlayerLevel(target, worldName, this, (Player) sender) > 0) {
                                        
                                        sender.sendMessage(ChatColor.GRAY + args[1] + " has been set to level " + LevelConfigUtils.getPlayerLevel(target, worldName, this, (Player) sender) );
                                        
                                        return true;
                                    }
                                }
                                catch(NumberFormatException ex) {
                                    
                                    sender.sendMessage(ChatColor.RED + "Incorrect Format!");
                                }
                            } else {
                                
                                sender.sendMessage(ChatColor.RED + "The specified player does not exist.");
                            }
                        } else {
                            
                            final String target = args[1];
                            
                            final String worldName = args [2];
                            
                            if (target != null) {
                                
                                try {
                                    
                                    int level = Integer.parseInt(args[3]);
                                    
                                    if(level <= 100 && level >= 1) {
                                    
                                        LevelConfigUtils.setPlayerLevel(target, worldName,level,this, sender);
                                    
                                    } else {
                                        
                                        sender.sendMessage(ChatColor.RED + "Only levels 1 through 100 are allowed!");
                                    
                                    }
                                    
                                    if(LevelConfigUtils.getPlayerLevel(target, worldName, this, sender) > 0) {
                                        
                                        sender.sendMessage(ChatColor.GRAY + args[1] + " has been set to level " + LevelConfigUtils.getPlayerLevel(target, worldName, this, sender) );
                                        
                                        return true;
                                    }
                                }
                                catch(NumberFormatException ex) {
                                    sender.sendMessage(ChatColor.RED + "Incorrect Format!");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "The specified player does not exist.");
                            }
                        }   
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    
                    sender.sendMessage(ChatColor.GOLD + "--------------Commands--------------");
                    
                    sender.sendMessage(ChatColor.GRAY + "/Parkourroll help - Displays the list of commands.");
                    
                    sender.sendMessage(ChatColor.GRAY + "/Parkourroll level - Displays your level in the current world.");
                    
                    sender.sendMessage(ChatColor.GRAY + "/Parkourroll level <player> [world] - Displays a player's level in a world.");
                    
                    sender.sendMessage(ChatColor.GRAY + "/Parkourroll setlevel <player> [world] <integer> - Sets a player's level for a world.");
                    
                    sender.sendMessage(ChatColor.GRAY + "/Parkourroll reload - Reloads the config.");
                    
                    return true;
                
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