package me.PlayerSlap.CommandClasses;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapInfoCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapInfoCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}
	
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length < 1) {
			return false; 
		}
		else if (args[0].equalsIgnoreCase("slap")) {
			if (args.length == 2) {
				if (s.hasPermission("playerslap.info.slap")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[1])) {
						s.sendMessage(plugin.ms.padding + "Information for the " + args[1] + " slap" + plugin.ms.padding); 
						s.sendMessage("Health removed: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".health")); 
						s.sendMessage("Slap worth: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".worth")); 
						s.sendMessage("Server operator must release player from slap: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".followup.permanent"));
						s.sendMessage("Player must accept slap: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".followup.mustaccept"));
						s.sendMessage("Produces lightning: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".lightning"));
						s.sendMessage("Radius of smoke produced: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".smoke"));
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
					}
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null); 
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("slapmessages")) {
			if (args.length == 2) {
				if (s.hasPermission("playerslap.info.slapmessages")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[1])) {
						s.sendMessage(plugin.ms.padding + "Information for the " + args[1] + " slap" + plugin.ms.padding); 
						s.sendMessage("Personal slap message: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".messages.slappersonal").replaceAll("$Default", plugin.ms.personalSlapMessage));
						s.sendMessage("Broadcast slap message: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".messages.slapbroadcast").replaceAll("$Default", plugin.ms.broadcastSlapMessage));
						s.sendMessage("Death slap message: " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".messages.death").replaceAll("$Default", plugin.ms.deathSlapMessage)); 
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
					}
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null);
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("slapmobs")) {
			if (args.length == 2) {
				if (s.hasPermission("playerslap.info.slapmobs")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[1])) {
						s.sendMessage(plugin.ms.padding + "Information for the " + args[1] + " slap" + plugin.ms.padding); 
						if (plugin.yc.configuration.contains("slaptypes." + args[1] + ".mobs")) {
							Set<String> mobs = Collections.emptySet(); 
							try {
								mobs = plugin.yc.configuration.getConfigurationSection("slaptypes." + args[1] + ".mobs").getKeys(false); 
							}
							catch (NullPointerException e) {
								plugin.ms.sendMessage(s, "nomobs", null);
							}
							if (mobs.isEmpty() == false) {
								for (String i : mobs) {
									s.sendMessage(i + ": " + plugin.yc.configuration.getString("slaptypes." + args[1] + ".mobs." + i));  
								}
							}
						}
						else {
							plugin.ms.sendMessage(s, "nomobs", null);
						}
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
					}
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null);
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("slaplist")) {
			if (args.length == 1) {
				Set<String> slapTypes = Collections.emptySet(); 
				try {
					slapTypes = plugin.yc.configuration.getConfigurationSection("slaptypes").getKeys(false); 
				}
				catch (NullPointerException e) {
					s.sendMessage(ChatColor.RED + "No slap types are defined "); 
				}
				if (slapTypes.isEmpty() == false ) {
					s.sendMessage(plugin.ms.padding + "There are the following slap types: " + plugin.ms.padding); 
					for (String i : slapTypes) {
						s.sendMessage(ChatColor.AQUA + "- " + i); 
					}
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("player")) {
			if (args.length == 2) {
				if (s.hasPermission("playerslap.info.player")) {
					UUID pid = dep.getUUIDFromPossibleOfflinePlayer(args[1]); 
					if (pid == null) {
						plugin.ms.sendMessage(s, "noplayerconfig", null); 
					}
					else {
						String sid = pid.toString(); 
						s.sendMessage(ChatColor.AQUA + "The player " + args[1] + " has the following information: ");
						s.sendMessage("Is exempt: " + plugin.yd.configuration.getString("players." + sid + ".exempt"));
						s.sendMessage("This player has been slapped " + plugin.yd.configuration.getString("player." + sid + ".times") + " times "); 
						if (plugin.needAcceptPlayers.containsKey(pid)) {
							s.sendMessage(ChatColor.RED + "This player is currently under a slap ");
							s.sendMessage("Player must accept slap: " + plugin.needAcceptPlayers.get(pid).getKey().toString()); 
							s.sendMessage("Player needs an operator to release it from a slap: " + plugin.needAcceptPlayers.get(pid).getValue().toString()); 
						}
						else {
							s.sendMessage(ChatColor.GREEN + "This player is not currently under a slap "); 
						}
					}
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null);
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("general")) {
			if (args.length == 1) {
				if (s.hasPermission("playerslap.info.general")) {
					s.sendMessage("General " + plugin.descriptionFile.getName() + " plugin config information: "); 
					s.sendMessage("Players can slap themselves: " + plugin.yc.configuration.getString("slapself")); 
					s.sendMessage("Default slap type: " + plugin.yc.configuration.getString("slapdefault"));
					s.sendMessage("Player slap counts incremented on '/slapall' command uses: " + plugin.yc.configuration.getString("incrementonslapall")); 
					s.sendMessage("Slap accept broadcast message: " + plugin.yc.configuration.getString("messages.accept").replaceAll("$Default", plugin.ms.acceptSlapMessage));
					s.sendMessage("Personal slap release message: " + plugin.yc.configuration.getString("messages.releasepersonal").replaceAll("$Default", plugin.ms.personalReleaseMessage));
					s.sendMessage("Broadcast slap release message: " + plugin.yc.configuration.getString("messages.releasebroadcast").replaceAll("$Default", plugin.ms.broadcastReleaseMessage));
					s.sendMessage("Personal slap no release message: " + plugin.yc.configuration.getString("messages.noreleasepersonal").replaceAll("$Default", plugin.ms.personalNoReleaseMessage));
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null); 
				}
				return true; 
			}
			else {
				return false; 
			}
		}
		else {
			return false;
		}
	}

}
