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
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}
	
	private Boolean slapSelf(CommandSender s) {
		if (s instanceof Player) {
			if (s.hasPermission("playerslap.slap")) {
				if (plugin.yc.configuration.getBoolean("slapself") == true) {
					String[] toSlap = {s.getName()}; 
					return dep.slapIndividualPlayer(s, toSlap, false); 
				}
				else {
					plugin.ms.sendMessage(s, "configdisabled", null); 
				}
			} 
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "mustbeplayer", "with no arguments "); 
		}
		return true; 
	}
	
	private Boolean slapPlayer(CommandSender s, String[] args) {
		return dep.slapIndividualPlayer(s, args, false); 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length == 0) {
			return slapSelf(s); 
		}
		else if (args.length < 4) {
			if (s.hasPermission("playerslap.slap.others")) {
				return slapPlayer(s, args);  
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
				return true; 
			}
		}
		else {
			return false; 
		}
	}
}
