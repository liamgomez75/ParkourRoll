/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.liamgomez75.parkourroll.experience;

import com.gmail.liamgomez75.parkourroll.ParkourRoll;
import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.RateConfigUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Liam Gomez <liamgomez75.gmail.com>
 */
public class Experience {
        private int level;
        private int expRate;
        private int damage;
        private Player player;
        private String worldName = " ";
        private int currentExp = 0;
        private int reqExp = 0;
        World world;
        //Is used to access the config
        
        private ParkourRoll plugin;
        
        //Initializes the config for use in this class.
        
        public Experience(ParkourRoll plugin) {
        this.plugin = plugin;
    }
        
        //The default constructor
        
       //config getters and setters are temporarily placed in the class until I can get the World variable working.
        public Experience(Player p) {
            player = p;
            if (player.getWorld() != null) {
               worldName = player.getLocation().getWorld().getName();
            }
    }
        //Calculates and returns the amount of exp gained from the last fall
        public int expGain(int dmg) {
            world = player.getWorld();
            expRate = RateConfigUtils.getPlayerRate(player,world,plugin);
            level = LevelConfigUtils.getPlayerLevel(player, world ,plugin);
            damage = dmg;
            return level * expRate + damage;
    }
        //Adds the exp gained to the players current exp and also levels them up if they have the required exp to do so.
        public void setXP(int xpGain) {
            level = LevelConfigUtils.getPlayerLevel(player,world,plugin);
            currentExp = EXPConfigUtils.getPlayerExp(player,world,plugin);
            currentExp = currentExp + xpGain;
            reqExp = plugin.getConfig().getInt("Level." + level + ".Exp Required");
            player.sendMessage("You have gained" + xpGain + "experience");
            if(currentExp >= reqExp){
                LevelConfigUtils.setPlayerLevel(player,world,level + 1,plugin);
                EXPConfigUtils.setPlayerExp(player,world,currentExp - reqExp,plugin);
                plugin.saveConfig();
                level = plugin.getConfig().getInt("Server.Worlds." + worldName + "." + player + ".Lvl");
                player.sendMessage("You have leveled up to level " + level + "!");
            }
            
    }
}
