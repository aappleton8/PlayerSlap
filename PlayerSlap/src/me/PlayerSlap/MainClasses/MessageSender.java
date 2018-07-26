package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageSender {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public final String unknownValue = "Unknown"; 
	public final String broadcastSlapMessage = "$Slapped has been given a $Permanentslap by $Giver"; 
	public final String personalSlapMessage = "You were slapped by $Giver"; 
	public final String deathSlapMessage = "$Slapped was slapped to death"; 
	public final String acceptSlapMessage = "$Slapped has accepted a slap"; 
	public final String broadcastReleaseMessage = "$Slapped has been released by $Giver"; 
	public final String personalReleaseMessage = "You have been released by $Giver"; 
	public final String personalNoReleaseMessage = "You have not yet been released from this slap"; 
	
	public MessageSender(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}
	
	public void sendMessage(CommandSender sender, String type, String message) {
		if (type.equalsIgnoreCase("custom")) {
			sender.sendMessage(message); 
		}
		else if (type.equalsIgnoreCase("mustbeplayer") || type.equalsIgnoreCase("mustbeaplayer")) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command "); 
		}
		else if (type.equalsIgnoreCase("configdisabled")) {
			sender.sendMessage(ChatColor.RED + "This feature has been disabled in the config file "); 
		}
		else if (type.equalsIgnoreCase("noslaptype")) {
			sender.sendMessage(ChatColor.RED + "No slap type is specified in the command and no default type could be found "); 
			logger.warning(plugin.formattedPluginName + "No default slap type could be found "); 
		}
		else if (type.equalsIgnoreCase("incorrectslaptype")) {
			sender.sendMessage(ChatColor.RED + "The specified slap type does not exist "); 
		}
		else if (type.equalsIgnoreCase("noslapworth")) {
			sender.sendMessage(ChatColor.RED + "The specified slap type has no worth specified; a default of '" + Integer.toString(plugin.defaultSlapWorth) + "' has been assumed "); 
		}
		else if (type.equalsIgnoreCase("noslapworthonall")) {
			sender.sendMessage(ChatColor.RED + "It could not be determined whether to add slaps performed using the /slapall command to players' totals; yes has been assumed"); 
			logger.warning("It could not be determined whether to add slaps performed using the /slapall command to players' totals; yes has been assumed"); 
		}
		else if (type.equalsIgnoreCase("invalidgivenslapworth")) {
			sender.sendMessage(ChatColor.RED + "The value you gave as the worth of the slap could not be parsed as an integer; the slap's default worth has been assumed "); 
		}
		else if (type.equalsIgnoreCase("nopermission")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command "); 
		}
		else if (type.equalsIgnoreCase("exempt")) {
			sender.sendMessage(ChatColor.RED + "This player is exempt from being slapped "); 
		}
		else if (type.equalsIgnoreCase("configerror")) {
			sender.sendMessage(ChatColor.RED + "There is an error with the " + message); 
			logger.warning(plugin.formattedPluginName + "There is an error with the " + message); 
		}
		else if (type.equalsIgnoreCase("incorrectdefaultslaptype")) {
			sender.sendMessage(ChatColor.RED + "No slap type was provided and the default slap type does not exist "); 
			logger.warning(plugin.formattedPluginName + "The default slap type does not exist "); 
		}
		else if (type.equalsIgnoreCase("noneedaccept")) {
			sender.sendMessage(ChatColor.GREEN + "The specified player has no need to accept any slaps "); 
		}
		else if (type.equalsIgnoreCase("noneedrelease")) {
			sender.sendMessage(ChatColor.RED + "The specified player does not need to be released from any slaps "); 
		}
		else {
			sender.sendMessage(ChatColor.RED + "There was an error when trying to send you an error message "); 
			logger.warning(plugin.formattedPluginName + "There was an error when processing an error message "); 
		}
	}
}
