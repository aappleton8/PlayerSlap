package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;
import me.PlayerSlap.MainClasses.YamlFiles;

public class PlayerSlapCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	
	public PlayerSlapCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length < 1) {
			return false; 
		}
		else if (args[0].equalsIgnoreCase("help")) {
			if (args.length == 1) {
				plugin.ms.help(s); 
				return true; 
			}
			else {
				plugin.ms.help(s); 
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("version")) {
			if (args.length == 1) {
				plugin.ms.sendMessage(s, "custom", plugin.formattedPluginName + " This plugin is version " + plugin.descriptionFile.getVersion()); 
				return true; 
			}
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("config")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("save")) {
					YamlFiles.fullySaveAll(); 
					return true; 
				}
				else if (args[1].equalsIgnoreCase("reload")) {
					YamlFiles.fullyReloadAll(); 
					return true; 
				}
				else {
					return false; 
				}
			}
			else if (args.length == 3) {
				if (args[1].equalsIgnoreCase("new")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						plugin.ms.sendMessage(s, "slapalreadydefined", null); 
					}
					else {
						if (true /*check permission here*/) {
							plugin.yc.configuration.createSection("slaptypes." + args[2]); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".health", 1); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".worth",  1); 
							plugin.yc.configuration.createSection("slaptypes." + args[2] + ".followup"); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.mustaccept", true); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.permanent", false); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".lightning", true); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".smoke", 10); 
							plugin.yc.configuration.createSection("slaptypes." + args[2] + ".messages"); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".messages.slapbroadcast", "$Default"); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".messages.slappersonal", "$Default"); 
							plugin.yc.configuration.set("slaptypes." + args[2] + ".messages.death", "$Default"); 
							plugin.yc.configuration.createSection("slaptypes." + args[2] + ".mobs"); 
							plugin.yc.save(); 
						}
						else {
							plugin.ms.sendMessage(s, "nopermission", null); 
						}
					}
					return true; 
				}
				else if (args[1].equalsIgnoreCase("remove")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						if (true /*check permission here*/) {
							plugin.yc.configuration.set("slaptypes." + args[2], null); 
							plugin.yc.save(); 
						}
						else {
							plugin.ms.sendMessage(s, "nopermission", null); 
						}
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
					}
					return true; 
				}
				else {
					return false; 
				}
			}
			else if (args.length == 5) {
				if (args[1].equalsIgnoreCase("set")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						if (args[3].equalsIgnoreCase("health")) {
							if (true /*check permission here*/) {
								int health = 0; 
								Boolean run = true; 
								try {
									health = Integer.parseInt(args[4]); 
								}
								catch (IllegalArgumentException e) {
									run = false; 
									plugin.ms.sendMessage(s, "invalidvalue", "n integer"); 
								}
								if (run == true) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".health", health); 
									plugin.yc.save(); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("worth")) {
							if (true /*check permission here*/) {
								int worth = 0; 
								Boolean run = true; 
								try {
									worth = Integer.parseInt(args[4]); 
								}
								catch (IllegalArgumentException e) {
									run = false; 
									plugin.ms.sendMessage(s, "invalidvalue", "n integer"); 
								}
								if (run == true) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".worth", worth); 
									plugin.yc.save(); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("mustaccept")) {
							if (true /*check permission here*/) {
								if (args[4].equalsIgnoreCase("true")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.mustaccept", true); 
									plugin.yc.save(); 
								}
								else if (args[4].equalsIgnoreCase("false")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.mustaccept", false); 
									plugin.yc.save(); 
								}
								else {
									plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("permanent")) {
							if (true /*check permission here*/) {
								if (args[4].equalsIgnoreCase("true")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.permanent", true); 
									plugin.yc.save(); 
								}
								else if (args[4].equalsIgnoreCase("false")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".followup.permanent", false); 
									
								}
								else {
									plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("lightning")) {
							if (true /*check permission here*/) {
								if (args[4].equalsIgnoreCase("true")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".lightning", true); 
									plugin.yc.save(); 
								}
								else if (args[4].equalsIgnoreCase("false")) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".lightning", false); 
									plugin.yc.save(); 
								}
								else {
									plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("smoke")) {
							if (true /*check permission here*/) {
								int smoke = 0; 
								Boolean run = true; 
								try {
									smoke = Integer.parseInt(args[4]); 
								}
								catch (IllegalArgumentException e) {
									run = false; 
									plugin.ms.sendMessage(s, "invalidvalue", "n integer"); 
								}
								if (run == true) {
									plugin.yc.configuration.set("slaptypes." + args[2] + ".smoke", smoke); 
									plugin.yc.save(); 
								}
							}
							else {
								plugin.ms.sendMessage(s, "nopermission", null); 
							}
						}
						else {
							plugin.ms.sendMessage(s, "invalidproperty", null); 
						}
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
					}
					return true; 
				}
				else {
					return false; 
				}
			}
			else if (args.length == 6) {
				if (args[1].equalsIgnoreCase("set")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						if (args[3].equalsIgnoreCase("mob")) {
							EntityType entityType = null; 
							Boolean run = true; 
							try {
								entityType = EntityType.valueOf(args[4]); 
							}
							catch (IllegalArgumentException e) {
								run = false; 
								s.sendMessage(ChatColor.RED + args[4] + " is not a valid mob type "); 
							}
							catch (NullPointerException e) {
								run = false; 
								s.sendMessage(ChatColor.RED + args[4] + " is not a valid mob type ");  
							}
							if (run == true) {
								int num = 0; 
								try {
									num = Integer.parseInt(args[5]); 
								}
								catch (IllegalArgumentException e) {
									run = false; 
									plugin.ms.sendMessage(s, "invalidvalue", "n integer"); 
								}
								if (run == true) {
									if (plugin.yc.configuration.contains("slaptypes." + args[2] + ".mobs") == false) {
										plugin.yc.configuration.createSection("slaptypes." + args[2] + ".mobs"); 
									}
									if (num == 0) {
										plugin.yc.configuration.set("slaptypes." + args[2] + ".mobs." + entityType.toString(), null); 
										plugin.yc.save(); 
									}
									else {
										plugin.yc.configuration.set("slaptypes." + args[2] + ".mobs." + entityType.toString(), num); 
										plugin.yc.save(); 
									}
								}
							}
							return true; 
						}
						else {
							return false; 
						}
					}
					else {
						plugin.ms.sendMessage(s, "incorrectslaptype", null); 
						return true; 
					}
				}
				else {
					return false; 
				}
			}
			else {
				return false; 
			}
		}
		// player slap times reset etc. here 
		else {
			return false; 
		}
	}
}
