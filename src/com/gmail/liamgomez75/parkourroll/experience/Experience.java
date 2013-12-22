package com.gmail.liamgomez75.parkourroll.experience;

import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.EXPConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import com.gmail.liamgomez75.parkourroll.utils.RateConfigUtils;
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
     * @param damage
     * @return
     */
    public static int getExpReward(Plugin plugin, Player player, int damage) {
        final World world = player.getWorld();
        final int expRate = RateConfigUtils.getPlayerRate(player, world, plugin);
        final int level = LevelConfigUtils.getPlayerLevel(player, world ,plugin);
        return level * expRate + damage;
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
        int level = LevelConfigUtils.getPlayerLevel(player, world, plugin);
        int currentExp = EXPConfigUtils.getPlayerExp(player, world, plugin);
        currentExp += xpGain;
        final int reqExp = plugin.getConfig().getInt("Level." + level + ".Exp Required");
        player.sendMessage("You have gained" + xpGain + "experience");
        if (currentExp >= reqExp) {
            level++;
            LevelConfigUtils.setPlayerLevel(player, world, level, plugin);
            
            final int exp = currentExp - reqExp;
            EXPConfigUtils.setPlayerExp(player, world, exp, plugin);
            plugin.saveConfig();
            level = plugin.getConfig().getInt("Server.Worlds." + world.getName() + "." + player + ".Lvl");
            player.sendMessage("You have leveled up to level " + level + "!");
        }
    }
}