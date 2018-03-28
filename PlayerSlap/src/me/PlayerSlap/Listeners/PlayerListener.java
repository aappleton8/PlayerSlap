package me.PlayerSlap.Listeners;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class PlayerListener implements Listener {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public PlayerListener(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.needAcceptPlayers.contains(event.getPlayer().getUniqueId()) == true) {
			event.setCancelled(true); 
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot move until you accept the slap (/playerslap accept) "); 
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID pid = event.getPlayer().getUniqueId(); 
		String sid = pid.toString(); 
		if (plugin.yd.configuration.contains("players." + sid)) {
			plugin.yd.configuration.set("players." + sid + ".username", event.getPlayer().getName()); 
			if (plugin.yd.configuration.getBoolean("players." + sid + ".exempt") == true) {
				if (plugin.yd.configuration.getBoolean("players." + sid + ".mustaccept") == true) {
					plugin.yd.configuration.set("players." + sid + ".mustaccept", false); 
				}
			}
			else {
				if (plugin.yd.configuration.getBoolean("players." + sid + ".mustaccept") == true) {
					plugin.needAcceptPlayers.add(pid); 
				}
			}
		}
		else {
			plugin.yd.configuration.createSection("players." + sid); 
			plugin.yd.configuration.set("players." + sid + ".exempt", false); 
			plugin.yd.configuration.set("players." + sid + ".times", 0); 
			plugin.yd.configuration.set("players." + sid + ".username", event.getPlayer().getName()); 
			plugin.yd.configuration.set("players." + sid + ".mustaccept", false);
		}
		plugin.yd.save(); 
	}
}
