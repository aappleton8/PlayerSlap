package me.PlayerSlap.MainClasses;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javafx.util.Pair;
import me.PlayerSlap.CommandClasses.ForceSlapCommand;
import me.PlayerSlap.CommandClasses.PlayerSlapCommand;
import me.PlayerSlap.CommandClasses.SlapAcknowledgeCommand;
import me.PlayerSlap.CommandClasses.SlapAllCommand;
import me.PlayerSlap.CommandClasses.SlapCommand;
import me.PlayerSlap.CommandClasses.SlapConfigureCommand;
import me.PlayerSlap.CommandClasses.SlapInfoCommand;
import me.PlayerSlap.CommandClasses.SlapReleaseCommand;
import me.PlayerSlap.Listeners.PlayerListener;

public class PlayerSlapMainClass extends JavaPlugin {
	public static PlayerSlapMainClass plugin; 
	protected final Logger logger = Logger.getLogger("Minecraft"); 
	
	public YamlFiles yc = new YamlFiles(this, logger, "config.yml", "config.yml");  
	public YamlFiles yd = new YamlFiles(this, logger, "players.yml", "players.yml");  
	
	public MessageSender ms = new MessageSender(this, logger); 
	private final PlayerListener pl = new PlayerListener(this, logger); 
	
	public PluginDescriptionFile descriptionFile; 
	public String formattedPluginName; 
	public HashMap<UUID, Pair<Boolean, Boolean>> needAcceptPlayers =  new HashMap<>(); 
	
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
		setCommandExecutors(); 
		getCurrentlySlappedPlayers(); 
		registerExtraPermissions(pm); 
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been enabled "); 
	}
	
	private void setCommandExecutors() {
		getCommand("slap").setExecutor(new SlapCommand(this, logger));  
		getCommand("slapall").setExecutor(new SlapAllCommand(this, logger)); 
		getCommand("playerslap").setExecutor(new PlayerSlapCommand(this, logger)); 
		getCommand("forceslap").setExecutor(new ForceSlapCommand(this, logger)); 
		getCommand("slapaccept").setExecutor(new SlapAcknowledgeCommand(this, logger)); 
		getCommand("slaprelease").setExecutor(new SlapReleaseCommand(this, logger)); 
		getCommand("slapinfo").setExecutor(new SlapInfoCommand(this, logger)); 
		getCommand("slapconfigure").setExecutor(new SlapConfigureCommand(this, logger)); 
	}
	
	private void getCurrentlySlappedPlayers() {
		Set<String> UUIDs = Collections.emptySet(); 
		try {
			UUIDs = yd.configuration.getConfigurationSection("players").getKeys(false); 
		}
		catch (NullPointerException e) {
			logger.warning(formattedPluginName + "Could not load information from the 'players' key in the 'players.yml' file. "); 
		}
		for (String i : UUIDs) {
			try {
				Boolean mustAccept = yd.configuration.getBoolean("players." + i + ".currentslap.mustaccept"); 
				Boolean isPermanent = yd.configuration.getBoolean("players." + i + ".currentslap.permenant"); 
				if ((mustAccept == true) || (isPermanent == true)) {
					needAcceptPlayers.put(UUID.fromString(i), new Pair<Boolean, Boolean>(mustAccept, isPermanent)); 
				}
			}
			catch (NullPointerException e) {
				logger.warning(formattedPluginName + "There is a malformed configuration file entry at: players." + i); 
			}
		}
	}
	
	private void registerExtraPermissions(PluginManager pm) {
		Set<String> slapTypes = Collections.emptySet(); 
		try {
			slapTypes = plugin.yc.configuration.getConfigurationSection("slaptypes").getKeys(false); 
		}
		catch (NullPointerException e) {
			logger.warning(formattedPluginName + "Could not register extra permissions as the config.yml file could not be loaded : " + e.toString());  
		}
		if ((slapTypes != null) && (slapTypes.isEmpty() == false)) {
			YamlFiles.checkPermissions(slapTypes, pm); 
		}
	}
	
	public void addPlayer(String playerName, String sid) {
		if (yd.configuration.contains("players") == false) {
			yd.configuration.createSection("players"); 
		}
		yd.configuration.createSection("players." + sid); 
		yd.configuration.set("players." + sid + ".exempt", false); 
		yd.configuration.set("players." + sid + ".times", 0); 
		yd.configuration.set("players." + sid + ".username", playerName); 
		yd.configuration.createSection("players." + sid + ".currentslap"); 
		yd.configuration.set("players." + sid + ".currentslap.mustaccept", false); 
		yd.configuration.set("players." + sid + ".currentslap.permanent", false); 
	}
}
