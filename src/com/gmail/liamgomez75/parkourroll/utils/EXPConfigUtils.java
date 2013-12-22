package com.gmail.liamgomez75.parkourroll.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods that interact with a configuration file for Exp Values.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 * @author Liam Gomez <liamgomez75.gmail.com>
 */
public abstract class EXPConfigUtils {

    /**
     * The string for the value in the config this class is interacting with.
     */
    public static final String EXP_CONFIG_STRING = "Exp";

    /**
     * The default value, used if no other values are found.
     */
    public static final int EXP_DEFAULT = 0;

    /**
     * Sets the Exp of the passed player in the passed world to the passed
     * state, then saves the config.
     *
     * @param player    player to set the Exp of
     * @param world     world in which to set the player's Exp
     * @param exp    Exp to change to
     * @param plugin    plugin with the config storing Exp values
     */
    public static void setPlayerExp(Player player, World world, int exp, Plugin plugin) {
        final String path = "Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + EXP_CONFIG_STRING;
        plugin.getConfig().set(path, exp);
        plugin.saveConfig();
    }

    /**
     * Return the specified player's Exp in a specified world.
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
     * @param plugin    plugin with config which stores Exp data
     * @return          the Exp of player in world
     */
    public static int getPlayerExp(Player player, World world, Plugin plugin) {
        return plugin.getConfig().getInt("Server.Worlds." + world.getName() + ".Players." + player.getName() + "." + EXP_CONFIG_STRING,
                EXP_DEFAULT);
    }
}