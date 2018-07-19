package me.PlayerSlap.MainClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

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
	protected final Logger logger = Logger.getLogger("Minecraft"); 
	
	public YamlFiles yc = new YamlFiles(this, logger, "config.yml", "config.txt");  
	public YamlFiles yd = new YamlFiles(this, logger, "players.yml", "players.txt");  
	
	public MessageSender ms = new MessageSender(this, logger); 
	private final PlayerListener pl = new PlayerListener(this, logger); 
	
	public PluginDescriptionFile descriptionFile; 
	public String formattedPluginName; 
	public List<UUID> needAcceptPlayers =  new ArrayList<>(); 
	
	public final int defaultSlapWorth = 1; 
	
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
		getCommand("slap").setExecutor(new SlapCommand(plugin, logger));  
		getCommand("slapall").setExecutor(new SlapAllCommand(plugin, logger)); 
		getCommand("playerslap").setExecutor(new PlayerSlapCommand(plugin, logger)); 
		getCommand("forceslap").setExecutor(new ForceSlapCommand(plugin, logger)); 
		Set<String> UUIDs = yd.configuration.getConfigurationSection("players").getKeys(false); 
		for (String i : UUIDs) {
			try {
				if (yd.configuration.getBoolean("players." + i + ".mustaccept") == true) {
					needAcceptPlayers.add(UUID.fromString(i)); 
				}
			}
			catch (NullPointerException e) {
				logger.warning(formattedPluginName + "There is a malformed configuration file entry at: players." + i); 
			}
		}
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been enabled "); 
	}
	
	public void addPlayer(String playerName, String sid) {
		yd.configuration.createSection("players." + sid); 
		yd.configuration.set("players." + sid + ".exempt", false); 
		yd.configuration.set("players." + sid + ".times", 0); 
		yd.configuration.set("players." + sid + ".username", playerName); 
		yd.configuration.set("players." + sid + ".mustaccept", false);
	}
}
