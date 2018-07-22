package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class ForceSlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public ForceSlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if ((args.length > 3) || (args.length < 1)) {
			return false;
		}
		else {
			if (s.hasPermission("playerslap.force")) {
				return dep.slapIndividualPlayer(s, args, true); 
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
				return true; 
			}
		}
	}
}
