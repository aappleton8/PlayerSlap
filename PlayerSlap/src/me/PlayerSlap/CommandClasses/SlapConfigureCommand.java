package me.PlayerSlap.CommandClasses;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import javafx.util.Pair;
import me.PlayerSlap.MainClasses.PlayerSlapMainClass;
import me.PlayerSlap.MainClasses.YamlFiles;

public class SlapConfigureCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapConfigureCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(plugin, logger, false); 
	}
	
	private void setInt(String location, String number, String permission, CommandSender s, YamlFiles yx) {
		if (s.hasPermission(permission)) {
			int value = 0; 
			Boolean run = true; 
			try {
				value = Integer.parseInt(number); 
			}
			catch (IllegalArgumentException e) {
				run = false; 
				plugin.ms.sendMessage(s, "invalidvalue", "n integer"); 
			}
			if (run == true) {
				yx.configuration.set(location, value); 
				yx.fullSave(); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "nopermission", null); 
		}
	}
	
	private void setBool(String location, String booleanValue, String permission, CommandSender s, YamlFiles yx) {
		if (s.hasPermission(permission)) {
			if (booleanValue.equalsIgnoreCase("true")) {
				yx.configuration.set(location, true); 
				yx.fullSave(); 
			}
			else if (booleanValue.equalsIgnoreCase("false")) {
				yx.configuration.set(location, false); 
				yx.fullSave(); 
			}
			else {
				plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "nopermission", null); 
		}
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length < 1) {
			return false; 
		}
		else if (args[0].equalsIgnoreCase("config")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("save")) {
					if (s.hasPermission("playerslap.config.save")) {
						plugin.yc.fullSave(); 
						plugin.yd.fullSave(); 
						Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Config files saved. ", "playerslap.see.config"); 
					}
					else {
						plugin.ms.sendMessage(s, "nopermission", null); 
					}
					return true; 
				}
				else if (args[1].equalsIgnoreCase("reload")) {
					if (s.hasPermission("playerslap.config.reload")) {
						plugin.yc.fullReload(); 
						plugin.yd.fullReload();
						Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Config files reloaded. ", "playerslap.see.config"); 
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
			else if (args.length == 4) {
				if (args[1].equalsIgnoreCase("set")) {
					if (args[2].equalsIgnoreCase("slapself")) {
						setBool("slapself", args[3], "playerslap.config.setconfig.slapself", s, plugin.yc); 
						return true; 
					}
					else if (args[2].equalsIgnoreCase("slapdefault")) {
						if (s.hasPermission("playerslap.config.setconfig.slapdefault")) {
							if (plugin.yc.configuration.contains("slaptypes." + args[3])) {
								plugin.yc.configuration.set("slapdefault", args[3]);
								Bukkit.broadcast(ChatColor.GREEN + "Default slap type changed to " + args[3], "playerslap.see.config"); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The specified slap type does not exit "); 
							}
						}
						else {
							plugin.ms.sendMessage(s, "nopermission", null); 
						}
						return true; 
					}
					else if (args[2].equalsIgnoreCase("incrementonslapall")) {
						setBool("incrementonslapall", args[3], "playerslap.config.setconfig.incrementonslapall", s, plugin.yc); 
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
			else {
				return false; 
			}
		}
		else if (args[0].equalsIgnoreCase("slap")) {
			if (args.length == 3) {
				if (args[1].equalsIgnoreCase("new")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						plugin.ms.sendMessage(s, "slapalreadydefined", null); 
					}
					else {
						if (s.hasPermission("playerslap.config.newslap")) {
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
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "New slap type added", "plugin.see.config"); 
						}
						else {
							plugin.ms.sendMessage(s, "nopermission", null); 
						}
					}
					return true; 
				}
				else if (args[1].equalsIgnoreCase("remove")) {
					if (plugin.yc.configuration.contains("slaptypes." + args[2])) {
						if (s.hasPermission("playerslap.config.removeslap")) {
							plugin.yc.configuration.set("slaptypes." + args[2], null); 
							plugin.yc.save(); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap type removed", "plugin.see.config"); 
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
							setInt("slaptypes." + args[2] + ".health", args[4], "playerslap.config.setslap.health", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("worth")) {
							setInt("slaptypes." + args[2] + ".worth", args[4], "playerslap.config.setslap.worth", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("mustaccept")) {
							setBool("slaptypes." + args[2] + ".followup.mustaccept", args[4], "playerslap.config.setslap.mustaccept", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("permanent")) {
							setBool("slaptypes." + args[2] + ".followup.permanent", args[4], "playerslap.config.setslap.permanent", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("lightning")) {
							setBool("slaptypes." + args[2] + ".lightning", args[4], "playerslap.config.setslap.lightning", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("smoke")) {
							setInt("slaptypes." + args[2] + ".smoke", args[4], "playerslap.config.setslap.smoke", s, plugin.yc); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
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
							if (s.hasPermission("playerslap.config.setslap.mob")) {
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
											Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
										}
										else {
											plugin.yc.configuration.set("slaptypes." + args[2] + ".mobs." + entityType.toString(), num); 
											plugin.yc.save(); 
											Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Slap updated", "plugin.see.config"); 
										}
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
		else if (args[0].equalsIgnoreCase("player")) {
			if (args.length == 3) {
				if (args[1].equalsIgnoreCase("remove")) {
					if (s.hasPermission("playerslap.config.removeplayer")) {
						UUID pid = dep.getUUIDFromPossibleOfflinePlayer(args[2]); 
						if (pid == null) {
							plugin.ms.sendMessage(s, "noplayerconfig", null); 
						}
						else {
							plugin.yd.configuration.set("players." + pid.toString(), null); 
							if (plugin.needAcceptPlayers.containsKey(pid)) {
								plugin.needAcceptPlayers.remove(pid); 
								Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You are no longer slapped "); 
							}
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Player removed", "plugin.see.config"); 
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
			else if (args.length == 5) {
				if (args[1].equalsIgnoreCase("set")) {
					UUID pid = dep.getUUIDFromPossibleOfflinePlayer(args[2]); 
					if (pid == null) {
						plugin.ms.sendMessage(s, "noplayerconfig", null); 
					}
					else {
						if (args[3].equalsIgnoreCase("times")) {
							if (args[4].equalsIgnoreCase("0")) {
								setInt("players." + pid.toString() + ".times", "0", "playerslap.config.setplayer.times.reset", s, plugin.yd); 
							}
							else {
								setInt("players." + pid.toString() + ".times", args[4], "playerslap.config.setplayer.times", s, plugin.yd); 
							}
							plugin.yd.save(); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Player updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("exempt")) {
							setBool("players." + pid.toString() + ".exempt", args[4], "playerslap.config.setplayer.exempt", s, plugin.yd); 
							plugin.yd.save(); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + "Player updated", "plugin.see.config"); 
						}
						else if (args[3].equalsIgnoreCase("mustaccept") || args[3].equalsIgnoreCase("ispermanent")) {
							return unSlap(s, pid, Arrays.copyOf(args, args.length - 1), false); 
						}
						else {
							return false; 
						}
					}
					return true; 
				}
				else {
					return false; 
				}
			}
			else if (args.length == 6) {
				if (args[1].equalsIgnoreCase("set")) {
					if (args[3].equalsIgnoreCase("mustaccept") || args[3].equalsIgnoreCase("ispermanent")) {
						UUID pid = dep.getUUIDFromPossibleOfflinePlayer(args[2]); 
						if (pid == null) {
							plugin.ms.sendMessage(s, "noplayerconfig", null); 
						}
						else {
							Boolean decrementTimes = null; 
							if (args[5].equalsIgnoreCase("true")) {
								decrementTimes = true; 
							}
							else if (args[5].equalsIgnoreCase("false")) {
								decrementTimes = false; 
							}
							if (decrementTimes == null) {
								plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
							}
							else {
								return unSlap(s, pid, Arrays.copyOf(args, args.length - 1), decrementTimes); 
							}
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
			else {
				return false; 
			}
		}
		else {
			return false; 
		}
	}
	
	private Boolean unSlap(CommandSender s, UUID pid, String[] args, Boolean decrementTimes) {
		if (args[3].equalsIgnoreCase("mustaccept")) {
			Boolean hasPermission = false; 
			if (decrementTimes == false) {
				if (s.hasPermission("playerslap.unslap.mustaccept")) {
					hasPermission = true; 
				}
			}
			else {
				if (s.hasPermission("playerslap.unslap.mustaccept.decrement")) {
					hasPermission = true; 
				}
			}
			if (hasPermission == true) {
				if (args[4].equalsIgnoreCase("false")) {
					plugin.yd.configuration.set("players." + pid.toString() + ".currentslap.mustaccept", false); 
					if (decrementTimes == true) {
						int times = plugin.yd.configuration.getInt("players." + pid.toString() + ".times"); 
						plugin.yd.configuration.set("players." + pid.toString() + ".times", times - 1); 
					}
					if (plugin.needAcceptPlayers.containsKey(pid) == true) {
						Boolean isPermanent = plugin.needAcceptPlayers.get(pid).getValue(); 
						Boolean mustAccept = plugin.needAcceptPlayers.get(pid).getKey(); 
						plugin.needAcceptPlayers.remove(pid); 
						if (isPermanent == true) {
							plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(false, true)); 
						}
						else {
							if (mustAccept == true) {
								Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You no longer have to accept the slap "); 
							}
						}
					}
					s.sendMessage(ChatColor.GREEN + "Player status updated"); 
				}
				else if (args[4].equalsIgnoreCase("true")) {
					plugin.yd.configuration.set("players." + pid.toString() + ".currentslap.mustaccept", true); 
					if (decrementTimes == true) {
						int times = plugin.yd.configuration.getInt("players." + pid.toString() + ".times"); 
						plugin.yd.configuration.set("players." + pid.toString() + ".times", times - 1); 
					}
					if (plugin.needAcceptPlayers.containsKey(pid) == true) {
						Boolean isPermanent = plugin.needAcceptPlayers.get(pid).getValue(); 
						Boolean mustAccept = plugin.needAcceptPlayers.get(pid).getKey(); 
						plugin.needAcceptPlayers.remove(pid); 
						if (isPermanent == true) {
							plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(true, true)); 
						}
						else {
							if (mustAccept == false) {
								Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You now have to accept a slap "); 
							}
						}
						s.sendMessage(ChatColor.GREEN + "Player status updated"); 
					}
					else {
						plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(true, false)); 
						Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You now have to accept a slap "); 
						
					}
					s.sendMessage(ChatColor.GREEN + "Player status updated"); 
				}
				else {
					plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
				}
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
			}
		}
		else if (args[3].equalsIgnoreCase("ispermanent")) {
			Boolean hasPermission = false; 
			if (decrementTimes == false) {
				if (s.hasPermission("playerslap.unslap.ispermanent")) {
					hasPermission = true; 
				}
			}
			else {
				if (s.hasPermission("playerslap.unslap.ispermanent.decrement")) {
					hasPermission = true; 
				}
			}
			if (hasPermission == true) {
				if (args[4].equalsIgnoreCase("false")) {
					plugin.yd.configuration.set("players." + pid.toString() + ".currentslap.permanent", false); 
					if (decrementTimes == true) {
						int times = plugin.yd.configuration.getInt("players." + pid.toString() + ".times"); 
						plugin.yd.configuration.set("players." + pid.toString() + ".times", times - 1); 
					}
					if (plugin.needAcceptPlayers.containsKey(pid) == true) {
						Boolean isPermanent = plugin.needAcceptPlayers.get(pid).getValue(); 
						Boolean mustAccept = plugin.needAcceptPlayers.get(pid).getKey(); 
						plugin.needAcceptPlayers.remove(pid); 
						if (mustAccept == true) {
							plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(true, false)); 
						}
						else {
							if (isPermanent == true) {
								Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You no longer have to to be released from the slap "); 
							}
						}
					}
					s.sendMessage(ChatColor.GREEN + "Player status updated"); 
				}
				else if (args[4].equalsIgnoreCase("true")) {
					plugin.yd.configuration.set("players." + pid.toString() + ".currentslap.permanent", true); 
					if (decrementTimes == true) {
						int times = plugin.yd.configuration.getInt("players." + pid.toString() + ".times"); 
						plugin.yd.configuration.set("players." + pid.toString() + ".times", times - 1); 
					}
					if (plugin.needAcceptPlayers.containsKey(pid) == true) {
						Boolean isPermanent = plugin.needAcceptPlayers.get(pid).getValue(); 
						Boolean mustAccept = plugin.needAcceptPlayers.get(pid).getKey(); 
						plugin.needAcceptPlayers.remove(pid); 
						if (mustAccept == true) {
							plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(true, true)); 
						}
						else {
							if (isPermanent == false) {
								Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You have now been permanently slapped "); 
							}
						}
					}
					else {
						plugin.needAcceptPlayers.put(pid, new Pair<Boolean, Boolean>(false, true)); 
						Bukkit.getPlayer(pid).sendMessage(ChatColor.GREEN + "You have now been permanently slapped "); 
					}
					s.sendMessage(ChatColor.GREEN + "Player status updated"); 
				}
				else {
					plugin.ms.sendMessage(s, "invalidvalue", " boolean"); 
				}
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
			}
		}
		return true; 
	}
}
