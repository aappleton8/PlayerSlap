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
		if ((args.length > 1)) {
			return false; 
		}
		else if (args.length == 1) {
			if ((s.hasPermission("playerslap.accept.others")) || ((s.hasPermission("playerslap.accept")) && (s.getName().equalsIgnoreCase(args[0])))) {
				uid = Bukkit.getPlayer(args[0]).getUniqueId(); 
			}
			else {
				plugin.ms.sendMessage(s, "nopermission", null); 
				return true; 
			}
		}
		else {
			if (s instanceof Player) {
				if (s.hasPermission("playerslap.accept")) {
					uid = ((Player) s).getUniqueId(); 
				}
				else {
					plugin.ms.sendMessage(s, "nopermission", null); 
					return true; 
				}
			}
			else {
				plugin.ms.sendMessage(s, "mustbeplayer", "with no arguments "); 
				return true; 
			}
		}
		if (plugin.needAcceptPlayers.containsKey(uid)) {
			if (plugin.needAcceptPlayers.get(uid).getValue() == true) {
				String personalNoReleaseMessage = plugin.ms.personalNoReleaseMessage; 
				if (plugin.yc.configuration.contains("messages.noreleasepersonal")) {
					personalNoReleaseMessage = plugin.yc.configuration.getString("messages.noreleasepersonal"); 
				}
				personalNoReleaseMessage = dep.formatMessages(personalNoReleaseMessage, plugin.ms.personalNoReleaseMessage, 
						plugin.ms.unknownValue, Bukkit.getPlayer(uid).getName(), plugin.ms.unknownValue, plugin.needAcceptPlayers.get(uid).getValue()); 
			}
			else {
				String acceptSlapMessage = plugin.ms.acceptSlapMessage; 
				if (plugin.yc.configuration.contains("messages.accept")) {
					acceptSlapMessage = plugin.yc.configuration.getString("messages.accept"); 
				}
				acceptSlapMessage = dep.formatMessages(acceptSlapMessage, plugin.ms.acceptSlapMessage, plugin.ms.unknownValue, 
						Bukkit.getPlayer(uid).getName(), plugin.ms.unknownValue, plugin.needAcceptPlayers.get(uid).getValue()); 
				Bukkit.broadcast(acceptSlapMessage, "playerslap.see.accept"); 
				plugin.needAcceptPlayers.remove(uid); 
			}
		}
		else {
			plugin.ms.sendMessage(s, "noneedaccept", null); 
		}
		return true; 
	}
}
