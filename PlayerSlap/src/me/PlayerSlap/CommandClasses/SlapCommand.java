package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

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
		dep = new CommandDependencies(pluginInstance, loggerInstance); 
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
		String type = null; 
		if (args.length == 2) {
			type = args[1]; 
		}
		else {
			type = dep.getDefaultType(); 
			if (type == null) {
				plugin.ms.sendMessage(s, "noslaptype", null); 
				return false; 
			}
		}
		if (!((s.hasPermission("playerslap.slap.others")) || (s.hasPermission("playerslap.slap") && (s.getName().equalsIgnoreCase(playerName))))) {
			plugin.ms.sendMessage(s, "nopermission", null); 
			return true; 
		}
		//dep.slapPlayer(s, playerName, type, false, false); 
		return true; 
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
