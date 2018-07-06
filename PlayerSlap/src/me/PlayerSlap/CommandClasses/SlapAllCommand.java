package me.PlayerSlap.CommandClasses;

import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapAllCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapAllCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance); 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		String type = null; 
		if (args.length > 1) {
			return false; 
		}
		else if (args.length == 1) {
			type = args[0]; 
		}
		else {
			type = dep.getDefaultType(); 
			if (type == null) {
				plugin.ms.sendMessage(s, "noslaptype", null); 
				return false; 
			}
		}
		Boolean noWorth = false; 
		if (plugin.yc.configuration.contains("incrementonslapall") == true) {
			noWorth = plugin.yc.configuration.getBoolean("incrementonslapall"); 
		}
		else {
			plugin.ms.sendMessage(s, "noslapworth", null); 
		}
		if (s.hasPermission("playerslap.slap.all") == false) {
			plugin.ms.sendMessage(s, "nopermission", null); 
		}
		else {
			Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers(); 
			for (Player player : onlinePlayers) {
				if (player.hasPermission("playerslap.noslap") == false) {
					//dep.slapPlayer(s, player, type, noWorth, true); 
				}
			}
		}
		return true; 
	}
}
