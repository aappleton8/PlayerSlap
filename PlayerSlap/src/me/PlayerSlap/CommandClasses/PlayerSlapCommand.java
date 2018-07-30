package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;
import me.PlayerSlap.MainClasses.YamlFiles;

public class PlayerSlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public PlayerSlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length < 1) {
			return false; 
		}
		else if (args[0].equalsIgnoreCase("help")) {
			if (args.length == 1) {
				plugin.ms.help(s); 
				return true; 
			}
			else {
				plugin.ms.help(s); 
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("version")) {
			if (args.length == 1) {
				plugin.ms.sendMessage(s, "custom", plugin.formattedPluginName + " This plugin is version " + plugin.descriptionFile.getVersion()); 
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("config")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("save")) {
					YamlFiles.fullySaveAll(); 
					return true; 
				}
				else if (args[1].equalsIgnoreCase("reload")) {
					YamlFiles.fullyReloadAll(); 
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
		else {
			return false; 
		}
	}

}
