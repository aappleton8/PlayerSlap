package me.PlayerSlap.CommandClasses;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapAcknowledgeCommand implements CommandExecutor{
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapAcknowledgeCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		UUID uid; 
		if ((args.length > 2) || (args.length < 1)) {
			return false; 
		}
		else if (args.length == 2) {
			uid = Bukkit.getPlayer(args[1]).getUniqueId(); 
		}
		else {
			if (s instanceof Player) {
				uid = ((Player) s).getUniqueId(); 
			}
			else {
				plugin.ms.sendMessage(s, "mustbeplayer", "with one argument "); 
				return true; 
			}
		}
		if (plugin.needAcceptPlayers.contains(uid)) {
			//if (plugin.yc.configuration.contains("slaptypes.))
		}
		else {
			plugin.ms.sendMessage(s, "noneedaccept", null); 
		}
		return true; 
	}
}
