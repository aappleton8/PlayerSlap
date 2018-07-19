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
		dep = new CommandDependencies(pluginInstance, loggerInstance, true); 
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length > 2) {
			return false; 
		}
		String type = dep.checkType(s, (args.length == 1) ? args[0] : null); 
		if (type == null) {
			return true; 
		}
		Boolean noWorth = false; 
		if (plugin.yc.configuration.contains("incrementonslapall") == true) {
			noWorth = plugin.yc.configuration.getBoolean("incrementonslapall"); 
		}
		else {
			plugin.ms.sendMessage(s, "noslapworthonall", null); 
		}
		if (args.length == 2) {
			if (args[2].equalsIgnoreCase("noworth")) {
				noWorth = false; 
			}
			else {
				plugin.ms.sendMessage(s, "noworthargumentwrong", null); 
				return false; 
			}
		}
		if (s.hasPermission("playerslap.slap.all") == false) {
			plugin.ms.sendMessage(s, "nopermission", null); 
		}
		else {
			Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers(); 
			for (Player player : onlinePlayers) {
				if (dep.checkPlayerInformation(s, player, type) == true) {
					dep.sendSlap(s, player, type, noWorth); 
				}
			}
		}
		return true; 
	}
}
