package me.PlayerSlap.Commands;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.PlayerSlap.Listeners.PlayerListener;

public class PlayerSlapMainClass extends JavaPlugin {
	public static PlayerSlapMainClass plugin; 
	public final Logger logger = Logger.getLogger("Minecraft"); 
	
	public final PlayerListener pl = new PlayerListener(this, logger); 
	private PluginDescriptionFile descriptionFile; 
	protected String formattedPluginName; 
	
	@Override
	public void onDisable() {
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been disabled ");  
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager(); 
		pm.registerEvents(pl, this); 
		descriptionFile = getDescription(); 
		formattedPluginName = "[" + descriptionFile.getName() + "] "; 
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been enabled "); 
		getCommand("slap").setExecutor(); 
	}
}
