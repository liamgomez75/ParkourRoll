package com.gmail.liamgomez75.parkourroll.listeners;
 
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
 
import org.bukkit.plugin.Plugin;
 

 
/**
 * 
 * @author Liam Gomez
 */
public class DamageListener implements Listener {
 
    /**
     * Plugin used for customizable values and strings.
     */
    private Plugin plugin;
 
    /**
     * Constructor.
     * @param plugin plugin used to set config values
     */
    public DamageListener(Plugin plugin) {
        this.plugin = plugin;
    }
 
    /**
     * Performs parkour roll.
     * If expected fall damage is below damage threshold, the player will perform a parkour roll to set damage to 0.
     * Else, if player is not killed by the fall, they will perform a parkour roll to reduce the damage.
     * @param e EntityDamageEvent triggered
     */
    @EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player) e.getEntity();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (p.isSneaking() && p.hasPermission("pkr.defaults")) {
                if (e.getDamage() <= plugin.getConfig().getDouble("Damage Threshold")) { // better name than threshold maybe?
                    e.setDamage(0.0);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("No Damage Roll Message")));
                } else {
                    e.setDamage(e.getDamage() * plugin.getConfig().getDouble("Damage Reduction"));
                    if (e.getDamage() < p.getHealth()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Injured Roll Message")));
                    }
                }
            }
        }
    }
}