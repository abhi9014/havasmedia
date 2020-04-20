package com.havasmedia.basetest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import org.json.JSONObject;

public class BaseConfigurationSetup {
	public static Properties configFileLoc;
	public static final String USER_DIRECTORY = System.getProperty("user.dir");
	public static final String CONFIG_PROPERTIES = USER_DIRECTORY + "\\src\\main\\resources\\ConfigProperties\\test";


	public String gsonMap(HashMap<String, List<Map<String, List<String>>>> mapp,String fileName) {
		String JSONObject = "";
		FileWriter file;
		try {
			JSONObject json = new JSONObject(mapp);
			  file = new FileWriter(USER_DIRECTORY+"\\"+fileName);
			  file.write(json.toString());
			  file.close();
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		return JSONObject;
	}

	public ArrayList<HashMap<String, List<String>>> readData() throws FileNotFoundException {
		File file = new File(CONFIG_PROPERTIES);
		Scanner sc = new Scanner(file);
		ArrayList<HashMap<String, List<String>>> testlist = new ArrayList<HashMap<String, List<String>>>();
		HashMap<String, List<String>> hashMapTest = null;
		HashMap<String, Map<String, List<String>>> hashMapTest1 = null;
		HashMap<String, HashMap<String, List<String>>> map = null;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String getliststr = line.replaceAll("\\[\\[", "");
			String getfinallist = getliststr.replaceAll("\\]\\]", "");
			List<String> al = new ArrayList<String>();
			al = Arrays.asList(getfinallist);
			for (String s : al) {
				if (s.contains("ORGANIZATION")) {
					if (s.contains("DATE")) {
						String str[] = al.toString().split("], \\[");
						List<String> al1 = new ArrayList<String>();
						List<String> al2 = new ArrayList<String>();
						List<String> al3 = new ArrayList<String>();
						al1 = Arrays.asList(str);
						hashMapTest = new HashMap<String, List<String>>();
						map = new HashMap<String, HashMap<String, List<String>>>();
						for (String s1 : al1) {
							if (s1.contains("ORGANIZATION")) {
								al2.add(s1.split(",")[0]);
								hashMapTest.put(s1.split(",")[1], al2);
							}

							if (s1.contains("DATE")) {
								al3.add(s1.split(",")[0]);
								hashMapTest.put(s1.split(",")[1], al3);
							}
						}
						testlist.add(hashMapTest);
					}
				}
			}

		}
		return testlist;
	}

	public HashMap<String, List<Map<String, List<String>>>> getMapEnity() throws FileNotFoundException {
		ArrayList<HashMap<String, List<String>>> maps = readData();
		List<Map<String, List<String>>> listHasmapWork =new ArrayList<Map<String, List<String>>>();
		List<Map<String, List<String>>> listHasmapEducation =new ArrayList<Map<String, List<String>>>();
		HashMap<String, List<Map<String, List<String>>>> mapActualMapWork=new HashMap<String, List<Map<String, List<String>>>>();
		HashMap<String, List<Map<String, List<String>>>> mapActualMapEduction=new HashMap<String, List<Map<String, List<String>>>>();
		for (HashMap<String, List<String>> map : maps) {
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				String strKey=entry.getKey();
				if (strKey.contains("DATE")) {
					if (entry.getValue().size()==2) {
						listHasmapWork.add(map);
						mapActualMapWork.put("WORK", listHasmapWork);
					}else if (entry.getValue().size()==1) {
						listHasmapEducation.add(map);
						mapActualMapEduction.put("EDUCATION", listHasmapEducation);
					}
				}
			}

		}
		//System.out.println("mapActualMapEduction ::::" + mapActualMapEduction);
		gsonMap(mapActualMapWork, "WorkJSON");
		//System.out.println("mapActualMapWork ::::" + mapActualMapWork);
		gsonMap(mapActualMapEduction, "Education");
		return mapActualMapEduction;
	}
}