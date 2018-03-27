package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.PlayerSlap.CommandClasses.ForceSlapCommand;
import me.PlayerSlap.CommandClasses.PlayerSlapCommand;
import me.PlayerSlap.CommandClasses.SlapAllCommand;
import me.PlayerSlap.CommandClasses.SlapCommand;
import me.PlayerSlap.Listeners.PlayerListener;

public class PlayerSlapMainClass extends JavaPlugin {
	public static PlayerSlapMainClass plugin; 
	public final Logger logger = Logger.getLogger("Minecraft"); 
	
	public YamlFiles yc = new YamlFiles(this, logger, "config.yml", "config.txt");  
	public YamlFiles yd = new YamlFiles(this, logger, "players.yml", "players.txt");  
	
	private final PlayerListener pl = new PlayerListener(this, logger); 
	public PluginDescriptionFile descriptionFile; 
	public String formattedPluginName; 
	
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
		getCommand("slap").setExecutor(new SlapCommand(plugin, logger));  
		getCommand("slapall").setExecutor(new SlapAllCommand(plugin, logger)); 
		getCommand("playerslap").setExecutor(new PlayerSlapCommand(plugin, logger)); 
		getCommand("forceslap").setExecutor(new ForceSlapCommand(plugin, logger)); 
	}
	
	public void noPermission(CommandSender s) {
		s.sendMessage("You do not have permission to use this command "); 
	}
}
