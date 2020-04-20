package com.havasmedia.basetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class BaseConfigurationSetup {
	private static final String USER_DIRECTORY = System.getProperty("user.dir");
	private static final String CONFIG_PROPERTIES = USER_DIRECTORY + "\\jsonFormatterFile";

	private void gsonMap(HashMap<String, List<Map<String, List<String>>>> mapp, String fileName) {
		FileWriter file;
		try {
			JSONObject json = new JSONObject(mapp);
			file = new FileWriter(USER_DIRECTORY + "\\" + fileName);
			file.write(json.toString());
			file.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private ArrayList<HashMap<String, List<String>>> readData() throws FileNotFoundException {
		File file = new File(CONFIG_PROPERTIES);
		Scanner sc = new Scanner(file);
		ArrayList<HashMap<String, List<String>>> testlist = new ArrayList<HashMap<String, List<String>>>();
		HashMap<String, List<String>> hashMapTest = null;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String getliststr = line.replaceAll("\\[\\[", "");
			String getfinallist = getliststr.replaceAll("\\]\\]", "");
			List<String> al = new ArrayList<String>();
			al = Arrays.asList(getfinallist);
			for (String s : al) {
				if (s.contains("ORGANIZATION")) {
					if (s.contains("DATE") || s.contains("DURATION")) {
						String str[] = al.toString().split("], \\[");
						List<String> al1 = new ArrayList<String>();
						List<String> al2 = new ArrayList<String>();
						List<String> al3 = new ArrayList<String>();
						// al1 = Arrays.asList(str);
						hashMapTest = new HashMap<String, List<String>>();
						for (String s1 : str) {
							if (s1.contains("ORGANIZATION")) {
								al2.add(s1.split(",")[0]);
								hashMapTest.put(s1.split(",")[1], al2);
							}

							if (s1.contains("DATE")) {
								al3.add(s1.split(",")[0]);
								hashMapTest.put(s1.split(",")[1], al3);
							}
							if (s1.contains("DURATION")) {
								al1.add(s1.split(",")[0]);
								hashMapTest.put(s1.split(",")[1], al1);
							}

						}
						testlist.add(hashMapTest);
					}
				}
			}

		}
		return testlist;
	}

	public void mapTextOutputToJsonObject() throws FileNotFoundException {
		ArrayList<HashMap<String, List<String>>> maps = readData();

		List<Map<String, List<String>>> listHasmapWork = new ArrayList<Map<String, List<String>>>();
		List<Map<String, List<String>>> listHasmapEducation = new ArrayList<Map<String, List<String>>>();
		HashMap<String, List<Map<String, List<String>>>> mapActualMapWork = new HashMap<String, List<Map<String, List<String>>>>();
		HashMap<String, List<Map<String, List<String>>>> mapActualMapEduction = new HashMap<String, List<Map<String, List<String>>>>();

		for (HashMap<String, List<String>> map : maps) {
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				String strKey = entry.getKey();
				if (strKey.contains("DATE")) {
					if (entry.getValue().size() >= 2) {
						listHasmapWork.add(map);
						mapActualMapWork.put("WORK", listHasmapWork);
					} else if (entry.getValue().size() == 1) {
						listHasmapEducation.add(map);
						mapActualMapEduction.put("EDUCATION", listHasmapEducation);
					}
				} else if (strKey.contains("DURATION")) {
					listHasmapWork.add(map);
					mapActualMapWork.put("WORK", listHasmapWork);
				}
			}

		}
		gsonMap(mapActualMapWork, "WorkJSON.json");
		gsonMap(mapActualMapEduction, "Education.json");
	}
}