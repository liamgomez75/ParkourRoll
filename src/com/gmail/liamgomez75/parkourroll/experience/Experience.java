package com.gmail.liamgomez75.parkourroll.experience;

import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.RateConfigUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class with static methods to deal with Experience.
 * 
 * @author James Healey <jameshealey1994@gmail.com>
 * @author Liam Gomez <liamgomez75.gmail.com>
 */
public abstract class Experience {

    /**
     * Returns the amount of exp gained from the last fall.
     * 
     * @param plugin
     * @param player
     * @param fallDist
     * @return
     */
    public static int getExpReward(Plugin plugin, Player player, int fallDist) {
        final World world = player.getWorld();
        final int expRate = RateConfigUtils.getPlayerRate(player, world, plugin);
        final int level = LevelConfigUtils.getPlayerLevel(player, world, plugin);
        return fallDist * expRate + level;
    }

    /**
     * Adds the exp gained to the players current exp and also levels them up if they have the required exp to do so.
     * 
     * @param plugin
     * @param player
     * @param world
     * @param xpGain
     */
    public static void addXP(Plugin plugin, Player player, World world, int xpGain) {
        int currentExp = EXPConfigUtils.getPlayerExp(player, world, plugin);
        currentExp += xpGain;
        player.sendMessage("You have gained " + xpGain + " experience");
        
        int level = LevelConfigUtils.getPlayerLevel(player, world, plugin);
        final int reqExp = getRequiredExp(plugin, level);
        if (currentExp >= reqExp) {
            level++;
            currentExp -= reqExp;
            
            LevelConfigUtils.setPlayerLevel(player, world, level, plugin);
            player.sendMessage("You have leveled up to level " + level + "!");
        }
        EXPConfigUtils.setPlayerExp(player, world, currentExp, plugin);
        
        plugin.saveConfig();
    }

    /**
     * Returns required Experience to get to the next level.
     * 
     * @param plugin
     * @param level
     * @return 
     */
    public static int getRequiredExp(Plugin plugin, int level) {
        return plugin.getConfig().getInt("Level." + level + ".Exp Required");
    }
}           