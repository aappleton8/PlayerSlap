package me.PlayerSlap.Listeners;

import java.util.logging.Logger;

import org.bukkit.event.Listener;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class PlayerListener implements Listener {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public PlayerListener(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
}
