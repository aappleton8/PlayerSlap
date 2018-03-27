package me.PlayerSlap.CommandClasses;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapCommand implements CommandExecutor {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public SlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	private Boolean slapSelf(Player s) {
		if (plugin.yc.configuration.getBoolean("slapself") == true) {
			
		}
		else {
			s.sendMessage(ChatColor.RED + "This feature has been disabled in the config file "); 
		}
		return true; 
	}
	
	private Boolean slapPlayer(CommandSender s, String playerName) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		if (player == null) {
			s.sendMessage(ChatColor.RED + "That player cannot be found "); 
		}
		else {
			UUID pid = player.getUniqueId(); 
			String sid = pid.toString(); 
			Set<String> players = plugin.yd.configuration.getConfigurationSection("players").getKeys(true); 
		}
		return true; 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Boolean returnValue = true; 
		if (args.length == 0) {
			if (s instanceof Player) {
				returnValue = slapSelf((Player) s); 
			}
			else {
				s.sendMessage(ChatColor.RED + "You must be a player to use this command with no parameters "); 
			}
		}
		else if (args.length == 1) {
			if (s instanceof Player) {
				slapSelf((Player) s); 
			}
			else {
				returnValue = slapPlayer(s, args[0]); 
			}
		}
		else if (args.length == 2) {
			
		}
		else {
			return false; 
		}
		plugin.yc.save(); 
		return returnValue; 
	}
}
