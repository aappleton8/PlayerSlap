package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}
	
	private Boolean slapSelf(CommandSender s) {
		if (s instanceof Player) {
			if (plugin.yc.configuration.getBoolean("slapself") == true) {
				String[] toSlap = {s.getName()}; 
				slapPlayer(s, toSlap); 
			}
			else {
				plugin.ms.sendMessage(s, "configdisabled", null); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "mustbeplayer", "with no arguments "); 
		}
		return true; 
	}
	
	private Boolean slapPlayer(CommandSender s, String[] args) {
		String playerName = args[0]; 
		String type = dep.checkType(s, (args.length >= 2) ? args[1] : null); 
		if (!((s.hasPermission("playerslap.slap.others")) || (s.hasPermission("playerslap.slap") && (s.getName().equalsIgnoreCase(playerName))))) {
			plugin.ms.sendMessage(s, "nopermission", null); 
			return true; 
		}
		Boolean canSlap = dep.checkPlayerInformation(s, playerName, type); 
		if (canSlap == false) {
			return true; 
		}
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName); 
		Boolean noWorth = false; 
		if (args.length == 3) {
			if (args[2].equalsIgnoreCase("noworth")) {
				noWorth = true; 
			}
			else {
				plugin.ms.sendMessage(s, "noworthargumentwrong", null); 
				return false; 
			}
		}
		dep.sendSlap(s, player, type, noWorth); 
		return true; 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length == 0) {
			return slapSelf(s); 
		}
		else if (args.length < 4) {
			return slapPlayer(s, args);  
		}
		else {
			return false; 
		}
	}
}
