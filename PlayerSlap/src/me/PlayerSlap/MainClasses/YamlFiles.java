package me.PlayerSlap.MainClasses;

import java.util.logging.Logger;

public class YamlFiles extends YamlFilesBase {

	public YamlFiles(PlayerSlapMainClass pluginInstance, Logger loggerInstance, String outFileName, String inFileName) {
		super(pluginInstance, loggerInstance, outFileName, inFileName);
	}
	
	@Override
	protected void saveNewFile() {
		if (configuration != null) {
			save(); 
			logger.info("Configuration file " + theOutFile.getName() + " loaded "); 
			configList.put(theOutFile.getName(), this); 
		}
		else {
			logger.severe("Configuration file " + theOutFile.getName() + " could not be loaded or created "); 
		}
	}

	@Override
	public void fullReload() {
		reload(); 
	}
}
