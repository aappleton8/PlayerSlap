package me.PlayerSlap.Listeners;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class PlayerListener implements Listener {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public PlayerListener(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (plugin.needAcceptPlayers.contains(event.getPlayer().getUniqueId()) == true) {
			String[] command = event.getMessage().split(" "); 
			if ((command.length == 2) || (command.length == 3)) {
				if ((command[0].equalsIgnoreCase("slc")) || (command[0].equalsIgnoreCase("slac")) || (command[0].equalsIgnoreCase("slapack")) || (command[0].equalsIgnoreCase("slapacknowledge"))) {
					if (!((command[1].equalsIgnoreCase("accept")) || (command[1].equalsIgnoreCase("deny")))) {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "You cannot use commands other than the '/slapacknowledge' command until you acknowledge the slap ");
					}
				}
			}
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.needAcceptPlayers.contains(event.getPlayer().getUniqueId()) == true) {
			event.setCancelled(true); 
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot move until you acknowledge the slap "); 
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID pid = event.getPlayer().getUniqueId(); 
		String sid = pid.toString(); 
		if (plugin.yd.configuration.contains("players." + sid)) {
			plugin.yd.configuration.set("players." + sid + ".username", event.getPlayer().getName()); 
			if (plugin.yd.configuration.getBoolean("players." + sid + ".mustaccept") == true) {
				if (plugin.yd.configuration.getBoolean("players." + sid + ".exempt") == true) {
					plugin.yd.configuration.set("players." + sid + ".mustaccept", false); 
				}
				else {
					plugin.needAcceptPlayers.add(pid); 
				}
			}
		}
		else {
			plugin.addPlayer(event.getPlayer().getName(), sid); 
		}
		plugin.yd.save(); 
	}
}
