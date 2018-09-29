package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageSender {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public final String unknownValue = "Unknown"; 
	public final String broadcastSlapMessage = "__Slapped has been given a __Permanentslap by __Giver"; 
	public final String personalSlapMessage = "You were slapped by __Giver"; 
	public final String deathSlapMessage = "__Slapped was slapped to death"; 
	public final String acceptSlapMessage = "__Slapped has accepted a slap"; 
	public final String broadcastReleaseMessage = "__Slapped has been released by __Giver"; 
	public final String personalReleaseMessage = "You have been released by __Giver"; 
	public final String personalNoReleaseMessage = "You have not yet been released from this slap"; 
	public final String senderNoReleaseMessage = "__Slapped has not yet been released from this slap"; 
	
	public final String padding = "====="; 
	public final String separator = "-"; 
	
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
		else if (type.equalsIgnoreCase("nomobs")) {
			sender.sendMessage(ChatColor.RED + "No mobs are defined for this slap type "); 
		}
		else if (type.equalsIgnoreCase("noplayerconfig")) {
			sender.sendMessage(ChatColor.RED + "That player could not be found in the config file "); 
		}
		else if (type.equalsIgnoreCase("slapalreadydefined")) {
			sender.sendMessage(ChatColor.RED + "That slap type is already defined "); 
		}
		else if (type.equalsIgnoreCase("invalidvalue")) {
			sender.sendMessage(ChatColor.RED + "That value is invalid; it must be a" + message + " "); 
		}
		else if (type.equalsIgnoreCase("invalidproperty")) {
			sender.sendMessage(ChatColor.RED + "That property does not exist "); 
		}
		else if (type.equalsIgnoreCase("badhelpsection")) {
			sender.sendMessage(ChatColor.RED + "The specified help section does not exist "); 
		}
		else {
			sender.sendMessage(ChatColor.RED + "There was an error when trying to send you an error message "); 
			logger.warning(plugin.formattedPluginName + "There was an error when processing an error message "); 
		}
	}
	
	public void help(CommandSender s) {
		help(s, "general", "1"); 
	}
	
	public void help(CommandSender s, String section, String page) {
		String topper = ChatColor.AQUA + padding + " " + ChatColor.DARK_PURPLE + plugin.descriptionFile.getName() + " help" + ChatColor.AQUA + " | " + ChatColor.DARK_PURPLE + section + " section" + ChatColor.AQUA + " | " + ChatColor.DARK_PURPLE + "page " + page + "/__totalpages " + ChatColor.AQUA + padding; 
		if (section.equalsIgnoreCase("general")) {
			s.sendMessage(topper.replace("__totalpages", "1")); 
			s.sendMessage(ChatColor.BLUE + "/slap [<player>] [<type>] [<worth>] " + ChatColor.WHITE + separator + ChatColor.AQUA + " Slap a player "); 
			s.sendMessage(ChatColor.BLUE + "/slapall [<type>] [<worth>] " + ChatColor.WHITE + separator + ChatColor.AQUA + " Slap every player "); 
			s.sendMessage(ChatColor.BLUE + "/forceslap <player> [<type>] [<worth>] " + ChatColor.WHITE + separator + ChatColor.AQUA + " Slap a player with the playerslap.noslap permission "); 
			s.sendMessage(ChatColor.BLUE + "/slapaccept [<player>] " + ChatColor.WHITE + separator + ChatColor.AQUA + " Accept a slap (if the slap requires it) "); 
			s.sendMessage(ChatColor.BLUE + "/slaprelease <player> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Release a player from a permanent slap "); 
			s.sendMessage(ChatColor.BLUE + "/slapinfo slaplist|{slap <type>}|{slapmessages <type>}|{slapmobs <type>}|{player <player>}|{times <player>}|general " + ChatColor.WHITE + separator + ChatColor.AQUA + " Get information about the specified object "); 
			s.sendMessage(ChatColor.BLUE + "/playerslap [{help [{config|general} [<page>]]}|version] " + ChatColor.WHITE + separator + ChatColor.AQUA + " The basic command to get help and the version "); 
			s.sendMessage(ChatColor.BLUE + "/slapconfigure {config save|reload|{set <property> <value>}}|{slap {set <type> <property> [<sub-property>] <value>}|{new <type>}|{remove <type>}}|{player {set <player> <property> <value> [<modifier>]}|{remove <player>}} " + ChatColor.WHITE + separator + ChatColor.AQUA + " The command to set config options ");
		}
		else if (section.equalsIgnoreCase("config") || section.equalsIgnoreCase("configure")) {
			s.sendMessage(topper.replace("__totalpages", "2")); 
			if (page.equalsIgnoreCase("1")) {
				s.sendMessage(ChatColor.BLUE + "/slapconfigure config save " + ChatColor.WHITE + separator + ChatColor.AQUA + " Save the config "); 
				s.sendMessage(ChatColor.BLUE + "/slapconfigure config reload " + ChatColor.WHITE + separator + ChatColor.AQUA + " Reload the config "); 
				s.sendMessage(ChatColor.BLUE + "/slapconfigure config set <property> <value> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Configure the specified config value "); 
				s.sendMessage(ChatColor.DARK_PURPLE + "<property> is one of 'slapself', 'slapdefault' and 'incrementonslapall' "); 
			}
			else {
				s.sendMessage(ChatColor.BLUE + "/slapconfigure slap set <type> <property> [<sub-property>] <value> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Configure the specified config slap value "); 
				s.sendMessage(ChatColor.DARK_PURPLE + "<property> is one of 'health', 'worth', 'mustaccept', 'permanent', 'mob', 'lightning' and 'smoke' ");
				s.sendMessage(ChatColor.BLUE + "/slapconfigure slap new <type> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Add a new slap type "); 
				s.sendMessage(ChatColor.BLUE + "/slapconfigure slap remove <type> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Remove a slap type ");
				s.sendMessage(ChatColor.BLUE + "/slapconfigure player set <player> <property> <value> [<modifier>] " + ChatColor.WHITE + separator + ChatColor.AQUA + " Set a config player value ");
				s.sendMessage(ChatColor.DARK_PURPLE + "<property> is one of 'mustaccept', 'ispermanent', 'times' and 'exempt' "); 
				s.sendMessage(ChatColor.BLUE + "/slapconfigure player remove <player> " + ChatColor.WHITE + separator + ChatColor.AQUA + " Remove a player from the config ");
			}
		}
		else {
			sendMessage(s, "badhelpsection", null); 
		}
	}
}
