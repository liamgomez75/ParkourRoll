package com.gmail.liamgomez75.parkourroll;

import com.gmail.liamgomez75.parkourroll.listeners.DamageListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ParkourRoll extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        final DamageListener fallDamage = new DamageListener(this);
        getServer().getPluginManager().registerEvents(fallDamage, this);
        saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("Parkourroll"))
        {
            if (args.length > 0)
            {
                if ((args[0].equalsIgnoreCase("reload")))
                {
                    return reload(sender);
                }
            }
        }
        return false;
    }

    public boolean reload(CommandSender sender)
    {
        if (sender.hasPermission("pkr.admin"))
        {
            reloadConfig();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Configuration reloaded.");
            return true;
        }
        else
        {
         sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
        }    
        return false;
    }
}
