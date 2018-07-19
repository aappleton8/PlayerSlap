package me.PlayerSlap.CommandClasses;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

class CommandDependencies {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	private Boolean reducedCheck; 
	
	CommandDependencies(PlayerSlapMainClass pluginInstance, Logger loggerInstance, Boolean reducedCheckValue) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		reducedCheck = reducedCheckValue; 
	}
	
	String checkType(CommandSender s, String type) {
		String obtainedType = type;
		if (type == null) {
			obtainedType = getDefaultType(); 
			if (obtainedType == null) {
				plugin.ms.sendMessage(s, "noslaptype", null); 
				return null; 
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + obtainedType) == false) {
			if (type == null) {
				plugin.ms.sendMessage(s, "incorrectdefaultslaptype", null); 
			}
			else {
				plugin.ms.sendMessage(s, "incorrectslaptype", null); 
			}
			return null; 
		}
		else {
			return type; 
		}
	}
	
	private String getDefaultType() {
		String type = null; 
		if (plugin.yc.configuration.contains("slapdefault") == true) {
			type = plugin.yc.configuration.getString("slapdefault"); 
		}
		return type; 
	}
	
	private String formatMessages(String message, String defaultMessage, String giver, String receiver) {
		if (message != null) {
			return message.replaceAll("$Default", defaultMessage)
					.replaceAll("$Giver", giver)
					.replaceAll("$Slapped", receiver)
					.replaceAll("$None", ""); 
		}
		else {
			return "";  
		}
	}
	
	Boolean checkPlayerInformation(CommandSender s, String playerName, String type) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		if (player == null) {
			if (reducedCheck == false) {
				s.sendMessage(ChatColor.RED + "That player cannot be found "); 
			}
			return false; 
		}
		return checkPlayerInformation(s, player, type); 
	}
	
	Boolean checkPlayerInformation(CommandSender s, Player player, String type) {
		String playerName = player.getName(); 
		UUID pid = player.getUniqueId(); 
		String sid = pid.toString(); 
		Set<String> players = plugin.yd.configuration.getConfigurationSection("players").getKeys(false); 
		if (player.hasPermission("playerslap.noslap")) {
			if (reducedCheck == false) {
				if ((player.hasPermission("playerslap.noslap.protect")) && (s instanceof Player) && (s.hasPermission("playerslap.noslap") == false)) {
					// Slap sending player 
					
				}
				plugin.ms.sendMessage(s, "exempt", null); 
			}
			return false; 
		}
		else if (players.contains(sid) == true) {
			Boolean isExempt = null; 
			try {
				isExempt = plugin.yd.configuration.getBoolean("players." + sid + ".exempt"); 
			}
			catch (NullPointerException e) {
				plugin.ms.sendMessage(s, "configerror", "players.yml file concerning the 'players." + sid + "' section. "); 
			}
			catch (IllegalArgumentException e) {
				plugin.ms.sendMessage(s, "configerror", "players.yml file concerning the 'players." + sid + "' section. "); 
			}
			if (isExempt == true) {
				if (reducedCheck == false) {
					plugin.ms.sendMessage(s, "exempt", null); 
				}
				return false; 
			}
			else if (isExempt == null) {
				return false; 
			}
		}
		else {
			plugin.addPlayer(playerName, sid); 
		}
		return true; 
	}
	
	int checkSlapWorth(CommandSender s, String type) {
		return checkSlapWorth(s, type, null); 
	}
	
	int checkSlapWorth(CommandSender s, String type, String rawWorth) {
		int worth = plugin.defaultSlapWorth; 
		if (rawWorth == null) {
			
		}
		else {
			try {
				worth = Integer.parseInt(rawWorth); 
			}
			catch (IllegalArgumentException e) {
				plugin.ms.sendMessage(s, "noslapworth", null); 
			}
		}
		
		return worth; 
	}
	
	// This function slaps the player; it returns true for a success and false for a failure 
	Boolean sendSlap(CommandSender s, Player player, String type, Boolean noWorth) {
		int worth = plugin.defaultSlapWorth; 
		if ((noWorth == false) && (plugin.yc.configuration.contains("slaptypes." + type + ".worth") == true)) {
			try {
				worth = Integer.valueOf(plugin.yc.configuration.getInt("slaptypes." + type + ".worth")); 
			}
			catch (IllegalArgumentException e) {
				plugin.ms.sendMessage(s, "noslapworth", null); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "noslapworth", null); 
		}
		String broadcastSlapMessage = plugin.ms.broadcastSlapMessage; 
		String personalSlapMessage = plugin.ms.personalSlapMessage; 
		String deathSlapMessage = plugin.ms.deathSlapMessage; 
		if (plugin.yc.configuration.contains("slaptypes." + type + ".messages") == true) {
			if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.broadcast") == true) {
				broadcastSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.broadcast");  
			}
			if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.personal") == true) {
				personalSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.personal"); 
			}
			if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.death") == true) {
				deathSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.death"); 
			}
		}
		formatMessages(broadcastSlapMessage, plugin.ms.broadcastSlapMessage, s.getName(), player.getDisplayName()); 
		formatMessages(personalSlapMessage, plugin.ms.personalSlapMessage, s.getName(), player.getDisplayName()); 
		formatMessages(deathSlapMessage, plugin.ms.deathSlapMessage, s.getName(), player.getDisplayName()); 
		if (plugin.yc.configuration.contains("slaptypes." + type + ".health") == true) {
			int damage = plugin.yc.configuration.getInt("slaptypes." + type + ".health"); 
			if (damage > 0) {
				double health = player.getHealth(); 
				player.setHealth(health - damage); 
				if ((player.getHealth() <= 0) && (deathSlapMessage != "")) {
					Bukkit.broadcast(ChatColor.RED + deathSlapMessage, "playerslap.see.slap"); 
				}
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + type + ".lightning") == true) {
			if (plugin.yc.configuration.getBoolean("slaptypes." + type + ".lightning") == true) {
				player.getWorld().strikeLightning(player.getLocation()); 
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + type + ".smoke") == true) {
			if (plugin.yc.configuration.getInt("slaptypes." + type + ".smoke") > 0) {
				player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, (plugin.yc.configuration.getInt("slaptypes." + type + ".smoke"))); 
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + type + ".mustaccept") == true) {
			if (plugin.yc.configuration.getBoolean("slaptypes." + type + ".mustaccept") == true) {
				plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".mustaccept", true); 
				if (plugin.needAcceptPlayers.contains(player.getUniqueId()) == false) {
					plugin.needAcceptPlayers.add(player.getUniqueId()); 
				}
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + type + ".mobs")) {
			Set<String> mobs = plugin.yc.configuration.getConfigurationSection("slaptypes." + type + ".mobs").getKeys(false); 
			for (String i : mobs) {
				try {
					for (int j = 0; j < plugin.yc.configuration.getInt("slaptypes." + type + ".mobs." + i); j++) {
						player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(i)); 
					}
				}
				catch (IllegalArgumentException e) {
					logger.warning(plugin.formattedPluginName + "There is an invalid entity type specified for the " + type + " slap. "); 
				}
			}
		}
		player.sendMessage(ChatColor.RED + personalSlapMessage); 
		if (reducedCheck == false) {
			Bukkit.broadcast(ChatColor.RED + broadcastSlapMessage, "playerslap.see.slap"); 
		}
		int times = plugin.yd.configuration.getInt("players." + player.getUniqueId() + ".times"); 
		plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".times", times + worth); 
		plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".username", player.getName()); 
		plugin.yc.save(); 
		plugin.yd.save(); 
		return true; 
	}
}
