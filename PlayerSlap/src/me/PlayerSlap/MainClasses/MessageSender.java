package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageSender {
	public PlayerSlapMainClass plugin; 
	public Logger logger; 
	
	public final String broadcastSlapMessage = "$Slapped was slapped by $Giver"; 
	public final String personalSlapMessage = "You were slapped by $Giver. Respond with /playerslap <accept|deny>"; 
	public final String deathSlapMessage = "$Slapped was slapped to death"; 
	
	public MessageSender(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	public void sendMessage(CommandSender sender, String type, String message) {
		if (type.equalsIgnoreCase("custom")) {
			sender.sendMessage(message); 
		}
		else if (type.equalsIgnoreCase("mustbeplayer")) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command "); 
		}
		else if (type.equalsIgnoreCase("configdisabled")) {
			sender.sendMessage(ChatColor.RED + "This feature has been disabled in the config file "); 
		}
		else if (type.equalsIgnoreCase("noslaptype")) {
			sender.sendMessage(ChatColor.RED + "No slap type is specified in the command and no default type could be found "); 
		}
		else if (type.equalsIgnoreCase("incorrectslaptype")) {
			sender.sendMessage(ChatColor.RED + "The specified slap type does not exist "); 
		}
	}
}
