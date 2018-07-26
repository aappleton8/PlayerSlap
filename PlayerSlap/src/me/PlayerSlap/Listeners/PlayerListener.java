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

import javafx.util.Pair;
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
		if (plugin.needAcceptPlayers.containsKey(event.getPlayer().getUniqueId()) == true) {
			String[] command = event.getMessage().split(" "); 
			if (command.length == 0) {
				if ((command[0].equalsIgnoreCase("slacc")) || (command[0].equalsIgnoreCase("slaa")) || (command[0].equalsIgnoreCase("slapac")) || (command[0].equalsIgnoreCase("slapaccept"))) {
					event.setCancelled(true); 
					if (plugin.needAcceptPlayers.get(event.getPlayer().getUniqueId()).getValue() == true) {
						event.getPlayer().sendMessage(ChatColor.RED + "You cannot use commands other than '/slapaccept' to acknowledge the slap ");
					}
					else {
						event.getPlayer().sendMessage(ChatColor.RED + "You cannot use commands until you have been released from the slap ");
					}
				}
			}
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.needAcceptPlayers.containsKey(event.getPlayer().getUniqueId()) == true) {
			event.setCancelled(true); 
			if (plugin.needAcceptPlayers.get(event.getPlayer().getUniqueId()).getValue() == true) {
				event.getPlayer().sendMessage(ChatColor.RED + "You cannot move until you have been released from the slap by a server operator "); 
			}
			else {
				event.getPlayer().sendMessage(ChatColor.RED + "You cannot move until you acknowledge the slap (/slapaccept)"); 
			}
		}
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID pid = event.getPlayer().getUniqueId(); 
		String sid = pid.toString(); 
		if (plugin.yd.configuration.contains("players." + sid)) {
			plugin.yd.configuration.set("players." + sid + ".username", event.getPlayer().getName()); 
			Boolean mustAccept = plugin.yd.configuration.getBoolean("players." + sid + ".currentslap.mustaccept"); 
			Boolean isPermanent = plugin.yd.configuration.getBoolean("players." + sid + ".currentslap.permenant"); 
			if ((mustAccept == true) || (isPermanent == true)) {
				if (plugin.yd.configuration.getBoolean("players." + sid + ".exempt") == true) {
					plugin.yd.configuration.set("players." + sid + ".currentslap.mustaccept", false); 
					plugin.yd.configuration.set("players." + sid + ".currentslap.permenant", false); 
				}
				else {
					plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(mustAccept, isPermanent)); 
				}
			}
		}
		else {
			plugin.addPlayer(event.getPlayer().getName(), sid); 
		}
		plugin.yd.save(); 
	}
}
