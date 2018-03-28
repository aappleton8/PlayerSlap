package me.PlayerSlap.MainClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
	public List<UUID> needAcceptPlayers =  new ArrayList<>(); 
	
	public final String broadcastSlapMessage = "$Slapped was slapped by $Giver"; 
	public final String personalSlapMessage = "You were slapped by $Giver. Respond with /playerslap <accept|deny>"; 
	public final String deathSlapMessage = "$Slapped was slapped to death"; 
	
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
		Set<String> UUIDs = yd.configuration.getConfigurationSection("players").getKeys(false); 
		for (String i : UUIDs) {
			try {
				if (yd.configuration.getBoolean("players." + i + ".mustaccept") == true) {
					needAcceptPlayers.add(UUID.fromString(i)); 
				}
			}
			catch (NullPointerException e) {
				logger.warning(formattedPluginName + "There is a malformed configuration file entry at: " + i); 
			}
		}
	}
	
	public void noPermission(CommandSender s) {
		s.sendMessage("You do not have permission to use this command "); 
	}
}
