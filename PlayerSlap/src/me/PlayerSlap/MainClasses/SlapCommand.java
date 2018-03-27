package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.Commands.PlayerSlapMainClass;

public class SlapCommand implements CommandExecutor {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public SlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
