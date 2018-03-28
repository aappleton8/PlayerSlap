package me.PlayerSlap.CommandClasses;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapCommand implements CommandExecutor {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public SlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	private Boolean slapSelf(CommandSender s) {
		if (s instanceof Player) {
			if (plugin.yc.configuration.getBoolean("slapself") == true) {
				String[] toSlap = {s.getName()}; 
				slapPlayer(s, toSlap); 
			}
			else {
				s.sendMessage(ChatColor.RED + "This feature has been disabled in the config file "); 
			}
		}
		else {
			s.sendMessage(ChatColor.RED + "You must be a player to use this command with no parameters "); 
		}
		return true; 
	}
	
	private Boolean slapPlayer(CommandSender s, String[] args) {
		String playerName = args[0]; 
		String type = null; 
		if (args.length == 2) {
			type = args[1]; 
		}
		else {
			if (plugin.yc.configuration.contains("slapdefault") == true) {
				type = plugin.yc.configuration.getString("slapdefault"); 
			}
			else {
				return false; 
			}
		}
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		if (player == null) {
			s.sendMessage(ChatColor.RED + "That player cannot be found "); 
		}
		else {
			UUID pid = player.getUniqueId(); 
			String sid = pid.toString(); 
			Set<String> players = plugin.yd.configuration.getConfigurationSection("players").getKeys(false); 
			if (players.contains(sid) == true) {
				Boolean isExempt = null; 
				try {
					isExempt = plugin.yd.configuration.getBoolean("players." + sid + ".exempt"); 
				}
				catch (NullPointerException e) {
					logger.warning(plugin.formattedPluginName + "There is an error in the players.yml file concerning the " + sid + " section. "); 
				}
				if (isExempt == false || isExempt == null || player.hasPermission("playerslap.noslap")) {
					s.sendMessage(ChatColor.RED + "This player is exempt from being slapped "); 
				}
				else {
					sendSlap(s, player, type); 
				}
			}
			else {
				plugin.yd.configuration.createSection("players." + sid); 
				plugin.yd.configuration.set("players." + sid + ".exempt", false); 
				plugin.yd.configuration.set("players." + sid + ".times", 0); 
				plugin.yd.configuration.set("players." + sid + ".username", playerName); 
				plugin.yd.configuration.set("players." + sid + ".mustaccept", false);
				sendSlap(s, player, type); 
			}
		}
		return true; 
	}
	
	private Boolean sendSlap(CommandSender s, Player player, String type) {
		if (plugin.yc.configuration.contains("slaptypes." + type) == false) {
			s.sendMessage(ChatColor.RED + "The specified slap type does not exist "); 
			return false; 
		}
		else {
			String broadcastSlapMessage = plugin.broadcastSlapMessage; 
			String personalSlapMessage = plugin.personalSlapMessage; 
			String deathSlapMessage = plugin.deathSlapMessage; 
			if (plugin.yc.configuration.contains("slaptypes." + type + ".messages") == true) {
				if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.broadcast") == true) {
					broadcastSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.broadcast").replaceAll("$Default", plugin.broadcastSlapMessage).replaceAll("$Giver", s.getName()).replaceAll("$Slapped", player.getName()).replaceAll("$None", ""); 
				}
				if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.personal") == true) {
					personalSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.personal").replaceAll("$Default", plugin.personalSlapMessage).replaceAll("$Giver", s.getName()).replaceAll("$Slapped", player.getName()).replaceAll("$None", ""); 
				}
				if (plugin.yc.configuration.contains("slaptypes." + type + ".messages.death") == true) {
					deathSlapMessage = plugin.yc.configuration.getString("slaptypes." + type + ".messages.death").replaceAll("$Default", plugin.deathSlapMessage).replaceAll("$Giver", s.getName()).replaceAll("$Slapped", player.getName()).replaceAll("$None", ""); 
				}
			}
			if (plugin.yc.configuration.contains("slaptypes." + type + ".health") == true) {
				int damage = plugin.yc.configuration.getInt("slaptypes." + type + ".health"); 
				if (damage > 0) {
					double health = player.getHealth(); 
					player.setHealth(health - damage); 
					if (player.getHealth() == 0 && deathSlapMessage != "") {
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
					plugin.needAcceptPlayers.add(player.getUniqueId()); 
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
			Bukkit.broadcast(ChatColor.RED + broadcastSlapMessage, "playerslap.see.slap"); 
			int times = plugin.yd.configuration.getInt("players." + player.getUniqueId() + ".times"); 
			plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".times", times + 1); 
			plugin.yd.configuration.set("players." + player.getUniqueId().toString() + ".username", player.getName()); 
			plugin.yc.save(); 
			plugin.yd.save(); 
			return true; 
		}
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length == 0) {
			return slapSelf(s); 
		}
		else if (args.length < 3) {
			return slapPlayer(s, args);  
		}
		else {
			return false; 
		}
	}
}
