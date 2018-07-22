package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapAllCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapAllCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, true); 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length > 2) {
			return false; 
		}
		else {
			if (s.hasPermission("playerslap.slap.all")) {
				return dep.slapMultiplePlayers(s, args, false); 
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
				return true; 
			}
		}
	}
}
