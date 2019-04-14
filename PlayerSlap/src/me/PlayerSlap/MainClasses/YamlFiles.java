package me.PlayerSlap.MainClasses;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;

public class YamlFiles extends YamlFilesBase {
	
	private final String configPermission = "playerslap.see.config"; 
	private static Set<String> slapPermissions = Collections.emptySet(); 

	public YamlFiles(PlayerSlapMainClass pluginInstance, Logger loggerInstance, String outFileName, String inFileName) {
		super(pluginInstance, loggerInstance, outFileName, inFileName);
		saveNewFile(); 
	}
	
	@Override
	protected void saveNewFile() {
		if (configuration != null) {
			save(); 
			logger.info("[" + plugin.getDescription().getName() + "] " + "Configuration file " + theOutFile.getName() + " loaded "); 
			configList.put(theOutFile.getName(), this); 
		}
		else {
			logger.severe("[" + plugin.getDescription().getName() + "] " + "Configuration file " + theOutFile.getName() + " could not be loaded or created "); 
		}
	}

	@Override
	public void fullReload() {
		reload(); 
		Bukkit.broadcast(ChatColor.GREEN + "[" + plugin.getDescription().getName() + "] " + theOutFile.getName() + " configuration reloaded ", configPermission); 
	}
	
	@Override
	public void fullSave() {
		Boolean saved = save(); 
		if (saved == true) {
			Bukkit.broadcast(ChatColor.GREEN + "[" + plugin.getDescription().getName() + "] " + theOutFile.getName() + " configuration saved ", configPermission); 
		}
		else {
			Bukkit.broadcast(ChatColor.RED + "[" + plugin.getDescription().getName() + "] " + theOutFile.getName() + " configuration could not be saved ", configPermission); 
		}
	}
	
	public static void fullyReloadAll() {
		Set<String> configs = configList.keySet(); 
		for (String i : configs) {
			configList.get(i).fullReload(); 
		}
	}
	
	public static void fullySaveAll() {
		Set<String> configs = configList.keySet(); 
		for (String i : configs) {
			configList.get(i).fullSave(); 
		}
	}
	
	static void checkPermissions(Set<String> slapTypes, PluginManager pm) {
		for (String i : slapTypes) {
			if (slapPermissions.contains(i) == false) {
				pm.addPermission(new org.bukkit.permissions.Permission("playerslap.type.defaultworth." + i)); 
				pm.addPermission(new org.bukkit.permissions.Permission("playerslap.type.noworth." + i)); 
				pm.addPermission(new org.bukkit.permissions.Permission("playerslap.type.anyworth." + i)); 
			}
		}
		for (String i : slapPermissions) {
			if (slapTypes.contains(i) == false) {
				pm.removePermission("playerslap.type.defaultworth." + i); 
				pm.removePermission("playerslap.type.noworth." + i); 
				pm.removePermission("playerslap.type.anyworth." + i); 
			}
		}
	}
}
