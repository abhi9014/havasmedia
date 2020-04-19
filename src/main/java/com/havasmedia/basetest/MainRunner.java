package com.havasmedia.basetest;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.ls.LSInput;

public class MainRunner{
	
	public static void main(String[] args) throws FileNotFoundException {
		BaseConfigurationSetup instance = new BaseConfigurationSetup();
		//HashMap<String, List<String>> hasMap = instance.readData();
		instance.getMapEnity();
		//instance.gsonMap(hasMap);
	}

}
	