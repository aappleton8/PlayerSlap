package me.PlayerSlap.MainClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlFiles {
	private static PlayerSlapMainClass plugin; 
	private static Logger logger;  
	
	public YamlConfiguration configuration; 
	public final File theOutFile; 
	private final String theInFile; 
	public YamlFiles(PlayerSlapMainClass pluginInstance, Logger loggerInstance, String outFileName, String inFileName) {
		plugin = pluginInstance; 
		logger = loggerInstance; 
		if (plugin.getDataFolder().exists() == false) {
			plugin.getDataFolder().mkdir(); 
		}
		theOutFile = new File(plugin.getDataFolder(), outFileName); 
		theInFile = inFileName; 
		configuration = loadFiles(); 
		save(); 
		logger.info("Configuration file " + theOutFile.getName() + " loaded "); 
	}
	
	public void reload() {
		configuration = loadAConfiguration(theOutFile); 
		Bukkit.broadcastMessage(plugin.descriptionFile.getName() + " configuration reloaded "); 
	}
	public static YamlConfiguration loadAConfiguration(File file) {
		new YamlConfiguration(); 
		YamlConfiguration currentConfigurationFile = YamlConfiguration.loadConfiguration(file); 
		return currentConfigurationFile; 
	}
	private static void copy(InputStream src, OutputStream dst) throws IOException{
		byte[] bytes = new byte[2048]; 
		int transfer; 
		while ((transfer = src.read(bytes)) > 0) {
			dst.write(bytes, 0, transfer); 
		}
		dst.close(); 
		src.close(); 
	}
	private static String getFileHeader(InputStream src) throws IOException {
		String header = null; 
		String line; 
		StringBuilder sb = new StringBuilder(); 
		sb.append("\n"); 
		BufferedReader br = new BufferedReader(new InputStreamReader(src));
		while ((line = br.readLine()) != null){  
			sb.append(line); 
			sb.append("\n"); 
		}
		br.close(); 
		src.close(); 
		header = sb.toString(); 
		return header; 
	}
	
	public YamlConfiguration loadFiles() {
		YamlConfiguration theConfiguration = null; 
		logger.info("Attempting to load the configuration file " + theOutFile.getName()); 
		if (theOutFile.exists() != true) {
			try {
				copy(plugin.getResource(theInFile), new FileOutputStream(theOutFile)); 
				logger.info("Configuration file " + theOutFile.getName() + " created "); 
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, "Unable to create a configuration file", e); 
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Unable to create a configuration file", e); 
			} 
			theConfiguration = loadAConfiguration(theOutFile); 
			fileHeaders(theConfiguration); 
		}
		else {
			theConfiguration =  loadAConfiguration(theOutFile); 
		}
		return theConfiguration; 
	}
	public void fileHeaders (YamlConfiguration theConfiguration) {
		FileConfigurationOptions options = theConfiguration.options(); 
		String theHeader = null; 
		try {
			theHeader = getFileHeader(plugin.getResource(theOutFile.getName().split("\\.")[0] + "Header.txt")); 
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to create a header file", e); 
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to create a header file", e); 
		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, "Unable to create a header file", e); 
		}
		options.header(theHeader); 
		options.copyHeader(true); 
	}
	public void save() {
		try {
			configuration.save(theOutFile);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to save file " + theOutFile, e); 
		} 
	}
}
