package com.havasmedia.configpropeties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	Properties properties;

	public Properties loadpropertyFile() {
		try {
			properties = new Properties();
			FileInputStream getFileInstance = new FileInputStream(
					System.getProperty("user.dir") + "\\configs\\Confurigation.properties");
			properties.load(getFileInstance);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}


}
