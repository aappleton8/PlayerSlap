package me.PlayerSlap.CommandClasses;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javafx.util.Pair;
import me.PlayerSlap.MainClasses.PlayerSlapMainClass;

public class SlapReleaseCommand implements CommandExecutor {
	protected PlayerSlapMainClass plugin; 
	protected Logger logger; 
	protected CommandDependencies dep; 
	
	public SlapReleaseCommand(PlayerSlapMainClass pluginInstance, Logger loggerInstance) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		dep = new CommandDependencies(pluginInstance, loggerInstance, false); 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length != 1) {
			return false;
		}
		else if (s.hasPermission("playerslap.release") == false) {
			plugin.ms.sendMessage(s, "nopermission", null); 
			return true; 
		}
		else {
			@SuppressWarnings("deprecation")
			Player player = Bukkit.getPlayer(args[0]); 
			if (plugin.needAcceptPlayers.containsKey(player.getUniqueId())) {
				if (plugin.needAcceptPlayers.get(player.getUniqueId()).getValue() == true) {
					String personalReleaseMessage = plugin.ms.personalReleaseMessage; 
					String broadcastReleaseMessage = plugin.ms.broadcastReleaseMessage; 
					if (plugin.yc.configuration.contains("messages.releasepersonal")) {
						personalReleaseMessage = plugin.yc.configuration.getString("messages.releasepersonal"); 
					}
					if (plugin.yc.configuration.contains("messages.releasebroadcast")) {
						personalReleaseMessage = plugin.yc.configuration.getString("messages.releasebroadcast"); 
					}
					personalReleaseMessage = dep.formatMessages(personalReleaseMessage, plugin.ms.personalReleaseMessage, s.getName(), 
							player.getName(), plugin.ms.unknownValue, true);
					broadcastReleaseMessage = dep.formatMessages(broadcastReleaseMessage, plugin.ms.personalReleaseMessage, s.getName(), 
							player.getName(), plugin.ms.unknownValue, true);
					Bukkit.broadcast(broadcastReleaseMessage, "playerslap.see.release"); 
					player.sendMessage(ChatColor.GREEN + personalReleaseMessage); 
					Boolean mustAccept = plugin.needAcceptPlayers.get(player.getUniqueId()).getKey(); 
					plugin.needAcceptPlayers.put(player.getUniqueId(), new Pair<Boolean, Boolean>(mustAccept, false)); 
				}
				else {
					plugin.ms.sendMessage(s, "noneedrelease", null); 
				}
			}
			else {
				plugin.ms.sendMessage(s, "noneedrelease", null); 
			}
			return true; 
		}
	}
}
