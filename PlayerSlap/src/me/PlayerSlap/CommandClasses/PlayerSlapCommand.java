package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class PlayerSlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public PlayerSlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
