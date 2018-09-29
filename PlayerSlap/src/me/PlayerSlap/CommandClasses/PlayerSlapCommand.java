package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class PlayerSlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public PlayerSlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(plugin, logger, false); 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length < 1) {
			plugin.ms.help(s); 
			return true; 
		}
		else if (args[0].equalsIgnoreCase("help")) {
			if (args.length == 1) {
				plugin.ms.help(s); 
				return true; 
			}
			else if (args.length == 2) {
				plugin.ms.help(s, args[1], "1"); 
				return true; 
			}
			else if (args.length == 3) {
				plugin.ms.help(s, args[1],  args[2]); 
				return true; 
			}
			else {
				plugin.ms.help(s); 
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("version")) {
			if (args.length == 1) {
				plugin.ms.sendMessage(s, "custom", plugin.descriptionFile.getName() + " is version " + plugin.descriptionFile.getVersion()); 
				return true; 
			}
			else {
				return false; 
			}
		}
		else {
			return false; 
		}
	}
}
