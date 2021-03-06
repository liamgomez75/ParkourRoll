package com.gmail.liamgomez75.parkourroll.listeners;

import com.gmail.liamgomez75.parkourroll.ParkourRoll;
import com.gmail.liamgomez75.parkourroll.experience.Experience;
import com.gmail.liamgomez75.parkourroll.localisation.Localisation;
import com.gmail.liamgomez75.parkourroll.localisation.LocalisationEntry;
import com.gmail.liamgomez75.parkourroll.utils.LevelConfigUtils;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

/**
 * Damage Listener for Parkour Roll.
 *
 * @author Liam Gomez <liamgomez75.gmail.com>
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public class DamageListener implements Listener {

    /**
     * Plugin used for customizable values and localisation.
     */
    private ParkourRoll plugin;

    /**
     * Constructor - Initializes plugin.
     *
     * @param plugin    plugin used to set config values and localisation
     */
    public DamageListener(ParkourRoll plugin) {
        this.plugin = plugin;
    }

    /**
     * Performs parkour roll.
     * If expected fall damage is below damage threshold, the player will perform a parkour roll to set damage to 0.
     * Else, if player is not killed by the fall, they will perform a parkour roll to reduce the damage.
     *
     * @param e     EntityDamageEvent triggered
     */
    @EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        
        final Player p = (Player) e.getEntity();
        int lvl = LevelConfigUtils.getPlayerLevel(p,p.getWorld(),plugin);
        final Localisation localisation = plugin.getLocalisation();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (p.isSneaking() && p.hasPermission("pkr.defaults")) {
                if (p.getFallDistance() <= plugin.getConfig().getDouble("Level." + lvl  +".Damage Threshold")) { // TODO better name than threshold maybe?
                    e.setDamage(0.0);
                    p.sendMessage(localisation.get(LocalisationEntry.MSG_SUCCESSFUL_ROLL));
                } else {
                    e.setDamage(e.getDamage() - e.getDamage() * plugin.getConfig().getDouble("Level." + lvl + ".Damage Reduction"));
                    if (e.getDamage() < p.getHealth()) {
                        p.sendMessage(localisation.get(LocalisationEntry.MSG_INJURED_BUT_SUCCESSFUL_ROLL));
                    } else {
                        return;
                    }
                }
                
                Random r = new Random();
                int randomNum =(int) p.getFallDistance() + r.nextInt((5 - 1) + 1) + 1;
                final int xpGained = Experience.getExpReward(plugin, p, randomNum);
                Experience.addXP(plugin, p, p.getWorld(), xpGained);
            }
        }
    }
}