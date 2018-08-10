package me.PlayerSlap.CommandClasses;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javafx.util.Pair;
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
	
	UUID getUUIDFromPossibleOfflinePlayer(String username) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(username); 
		if (player != null) {
			return player.getUniqueId(); 
		}
		else if (plugin.yd.configuration.contains("players")) {
			Set<String> playerUUIDStrings  = Collections.emptySet(); 
			try {
				playerUUIDStrings = plugin.yd.configuration.getConfigurationSection("players").getKeys(false); 
			}
			catch (NullPointerException e) {
				return null; 
			}
			if (playerUUIDStrings.isEmpty() == false) {
				for (String i : playerUUIDStrings) {
					if (plugin.yd.configuration.getString("players." + i).equalsIgnoreCase("username")) {
						return UUID.fromString(i); 
					}
				}
			}
			return null; 
		}
		else {
			return null; 
		}
	}
	
	Boolean slapIndividualPlayer(CommandSender s, String[] args, Boolean force) {
		String playerName = args[0]; 
		String type = checkType(s, (args.length >= 2) ? args[1] : null); 
		if (type == null) {
			return true; 
		}
		Boolean canSlap = checkPlayerInformation(s, playerName, type, force); 
		if (canSlap == false) {
			return true; 
		}
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		int worth = checkSlapWorth(s, type, (args.length >= 3) ? args[2] : null); 
		sendSlap(s, player, type, worth); 
		return true; 
	}
	
	Boolean slapMultiplePlayers(CommandSender s, String[] args, Boolean force) {
		String type = checkType(s, (args.length == 1) ? args[0] : null); 
		if (type == null) {
			return true; 
		}
		int worth = checkSlapWorth(s, type, (args.length >= 2) ? args[1] : null); 
		if (plugin.yc.configuration.contains("incrementonslapall") == true) {
			if (plugin.yc.configuration.getBoolean("incrementonslapall") == false) {
				worth = 0; 
			} 
		}
		else {
			plugin.ms.sendMessage(s, "noslapworthonall", null); 
		}
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers(); 
		for (Player player : onlinePlayers) {
			if (!(s.getName().equalsIgnoreCase(player.getName()))) {
				if (checkPlayerInformation(s, player, type, force) == true) {
					sendSlap(s, player, type, worth); 
				}
			}
		}
		return true; 
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
	
	String formatMessages(String message, String defaultMessage, String giver, String receiver, String type, Boolean permanent) {
		if (message != null) {
			String permanentString = permanent ? "permanent " : ""; 
			return message.replaceAll("$Default", defaultMessage)
					.replaceAll("$Giver", giver)
					.replaceAll("$Slapped", receiver)
					.replaceAll("$None", "")
					.replaceAll("$Unknwon", plugin.ms.unknownValue)
					.replaceAll("$Type", type)
					.replaceAll("$Permanent", permanentString); 
		}
		else {
			return "";  
		}
	}
	
	Boolean checkPlayerInformation(CommandSender s, String playerName, String type, Boolean force) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		if (player == null) {
			if (reducedCheck == false) {
				s.sendMessage(ChatColor.RED + "That player cannot be found "); 
			}
			return false; 
		}
		return checkPlayerInformation(s, player, type, force); 
	}
	
	Boolean checkPlayerInformation(CommandSender s, Player player, String type, Boolean force) {
		String playerName = player.getName(); 
		UUID pid = player.getUniqueId(); 
		String sid = pid.toString(); 
		Set<String> players = plugin.yd.configuration.getConfigurationSection("players").getKeys(false); 
		if (player.hasPermission("playerslap.noslap") && (force == false)) {
			if (reducedCheck == false) {
				if ((player.hasPermission("playerslap.noslap.protect")) && (s instanceof Player) && (s.hasPermission("playerslap.noslap") == false)) {
					sendSlap(s, player, type, checkSlapWorth(s, type)); 
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
		Boolean haveValue = false; 
		int worth = plugin.defaultSlapWorth; 
		if (rawWorth != null) {
			try {
				worth = Integer.parseInt(rawWorth); 
				haveValue = true; 
			}
			catch (IllegalArgumentException e) {
				plugin.ms.sendMessage(s, "invalidgivenslapworth", null); 
			}
		}
		if (haveValue != true) {
			if (plugin.yc.configuration.contains("slaptypes." + type + ".worth")) {
				worth = plugin.yc.configuration.getInt("slaptypes." + type + ".worth"); 
				haveValue = true; 
			}
			else {
				plugin.ms.sendMessage(s, "noslapworth", null); 
			}
		}
		return worth; 
	}
	
	// This function slaps the player; it returns true for a success and false for a failure 
	void sendSlap(CommandSender s, Player player, String type, int worth) {
		Boolean mustAccept = plugin.yc.configuration.getBoolean("slaptypes." + type + ".followup.mustaccept"); 
		Boolean isPermanent = plugin.yc.configuration.getBoolean("slaptypes." + type + ".followup.permanent"); 
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
		broadcastSlapMessage = formatMessages(broadcastSlapMessage, plugin.ms.broadcastSlapMessage, s.getName(), player.getDisplayName(), type, isPermanent); 
		personalSlapMessage = formatMessages(personalSlapMessage, plugin.ms.personalSlapMessage, s.getName(), player.getDisplayName(), type, isPermanent); 
		deathSlapMessage = formatMessages(deathSlapMessage, plugin.ms.deathSlapMessage, s.getName(), player.getDisplayName(), type, isPermanent); 
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
		if ((mustAccept == true) || (isPermanent == true)) {
			plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".currentslap.mustaccept", mustAccept); 
			plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".currentslap.permanent", isPermanent); 
			if (plugin.needAcceptPlayers.containsKey(player.getUniqueId()) == true) {
				Pair<Boolean, Boolean> currentValues = plugin.needAcceptPlayers.get(player.getUniqueId()); 
				plugin.needAcceptPlayers.put(player.getUniqueId(), 
						new Pair<Boolean, Boolean>(currentValues.getKey() || mustAccept, currentValues.getValue() || isPermanent)); 
			}
			else {
				plugin.needAcceptPlayers.put(player.getUniqueId(), new Pair<Boolean, Boolean>(mustAccept, isPermanent)); 
			}
		}
		if (plugin.yc.configuration.contains("slaptypes." + type + ".mobs")) {
			Set<String> mobs = plugin.yc.configuration.getConfigurationSection("slaptypes." + type + ".mobs").getKeys(false); 
			for (String i : mobs) {
				try {
					for (int j = 0; j < plugin.yc.configuration.getInt("slaptypes." + type + ".mobs." + i); j++) {
						EntityType entityType = null; 
						Boolean run = true; 
						try {
							entityType = EntityType.valueOf(i); 
						}
						catch (IllegalArgumentException e) {
							run = false; 
							Bukkit.broadcast(ChatColor.RED + plugin.formattedPluginName + "The " + type + " slap has an invalid mob type of " + i, "playerslap.see.config"); 
						}
						catch (NullPointerException e) {
							run = false; 
							Bukkit.broadcast(ChatColor.RED + "The " + type + " slap has a null mob type ", "playerslap.see.config"); 
						}
						if (run == true) {
							player.getWorld().spawnEntity(player.getLocation(), entityType); 
						}
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
	}
}
