/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.liamgomez75.parkourroll.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods that interact with a configuration file for Level values.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 * @author Liam Gomez <liamgomez75.gmail.com>
 */
public abstract class LevelConfigUtils {

    /**
     * The string for the value in the config this class is interacting with.
     */
    public static final String LVL_CONFIG_STRING = "Lvl";

    /**
     * The default value, used if no other values are found.
     */
    public static final int LVL_DEFAULT = 1;

    /**
     * Sets the Level of the passed player in the passed world to the passed
     * state, then saves the config.
     *
     * @param player    player to set the Level of
     * @param world     world in which to set the player's Level
     * @param level    Level to change to
     * @param plugin    plugin with the config storing Level values
     */
    public static void setPlayerLevel(Player player, World world, int level, Plugin plugin) {
        final String path = "Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + LVL_CONFIG_STRING;
        plugin.getConfig().set(path, level);
        plugin.saveConfig();
    }

    /**
     * Return the specified player's Level in a specified world.
     * The method first looks for a specific value for the player in the world.
     *
     * If that is not found, the method checks the default value for the world.
     *
     * If that is not found, the method checks the default value for the server.
     *
     * If there is an error with the default value for the server, the default
     * is returned.
     *
     * Currently the default is false.
     *
     * @param player    the player being checked
     * @param world     the world of the player being checked
     * @param plugin    plugin with config which stores Level data
     * @return          the Level of player in world
     */
    public static int getPlayerLevel(Player player, World world, Plugin plugin) {
        return (plugin.getConfig().getInt("Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + LVL_CONFIG_STRING, LVL_DEFAULT));
    }

}