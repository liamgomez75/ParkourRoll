/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.liamgomez75.parkourroll.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods that interact with a configuration file for Exp Rate values.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 * @author Liam Gomez <liamgomez75.gmail.com>
 */
public abstract class RateConfigUtils {

    /**
     * The string for the value in the config this class is interacting with.
     */
    public static final String RATE_CONFIG_STRING = "ExpRate";

    /**
     * The default value, used if no other values are found.
     */
    public static final int RATE_DEFAULT = 7;

    /**
     * Sets Exp Rate of the passed player in the passed world to the passed
     * state, then saves the config.
     *
     * @param player    player to set the Exp Rate of
     * @param world     world in which to set the player's Exp Rate
     * @param rate    status to change to
     * @param plugin    plugin with the config storing Exp Rate values
     */
    public static void setPlayerRate(Player player, World world, int rate, Plugin plugin) {
        final String path = "Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + RATE_CONFIG_STRING;
        plugin.getConfig().set(path, rate);
        plugin.saveConfig();
    }

    /**
     * Sets the default Exp Rate of the passed world to the passed state,
     * then saves the config.
     *
     * @param world     world to set the default Exp Rate of
     * @param rate    status to change to
     * @param plugin    plugin with the config storing Exp Rate values
     */
    public static void setWorldRate(World world, int rate, Plugin plugin) {
        final String path = "Server.Worlds." + world.getName() + "." + RATE_CONFIG_STRING;
        plugin.getConfig().set(path, rate);
        plugin.saveConfig();
    }

    /**
     * Return the specified player's Exp Rate in a specified world.
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
     * @param plugin    plugin with config which stores Exp Rate data
     * @return          the Exp Rate of player in world
     */
    public static int getPlayerRate(Player player, World world, Plugin plugin) {
        return (plugin.getConfig().getInt("Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + RATE_CONFIG_STRING,
                plugin.getConfig().getInt("Server.Worlds." + world.getName() + ".Players." + player.getName())));
    }

    /**
     * Return the default Exp Rate of a specified world.
     * The method first looks for the default value for the world.
     *
     * If that is not found, the method checks the default value for the server.
     *
     * If there is an error with the default value for the server, the default
     * is returned.
     *
     * Currently the default is false.
     *
     * @param world     the world of the player being checked
     * @param plugin    plugin with config which stores Exp Rate data
     * @return          the default Exp Rate of world
     */
    public static int getWorldRate(World world, Plugin plugin) {
        return  plugin.getConfig().getInt("Server.Worlds." + world.getName() + "." + RATE_CONFIG_STRING,
                plugin.getConfig().getInt("Server.Worlds." + world.getName()));
    }

    /**
     * Return the default Exp Rate of the server.
     * The method first looks for the default value for the server.
     *
     * If there is an error with the default value for the server, the default
     * is returned.
     *
     * Currently the default is false.
     *
     * @param plugin    plugin with config which stores Exp Rate data
     * @return          the default Exp Rate of the server
     */
    public static int getServerRate(Plugin plugin) {
        return  plugin.getConfig().getInt("Server." + RATE_CONFIG_STRING,
                plugin.getConfig().getInt("Server",
                RATE_DEFAULT));
    }
}