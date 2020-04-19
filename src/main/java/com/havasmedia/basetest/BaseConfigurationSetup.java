package com.havasmedia.basetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class BaseConfigurationSetup{
	public static Properties configFileLoc;
	public static final String USER_DIRECTORY = System.getProperty("user.dir");
	public static final String CONFIG_PROPERTIES = USER_DIRECTORY + "\\src\\main\\resources\\ConfigProperties\\test";
	

	public static void main(String[] args) {
		BaseConfigurationSetup config=new BaseConfigurationSetup();
		config.readFile();
	}
	

	public void  getData() throws IOException {
		FileChannel channel=null;
		try {
			
			 RandomAccessFile file = new RandomAccessFile(CONFIG_PROPERTIES, "r");

			 channel = file.getChannel();

		        System.out.println("File size is: " + channel.size());

		        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());

		        channel.read(buffer);

		        buffer.flip();//Restore buffer to position 0 to read it

		        System.out.println("Reading content and printing ... ");

		        for (int i = 0; i < channel.size(); i++) {
		            System.out.print((char) buffer.get());
		        }

		        channel.close();
		        file.close();

		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readFile() {
		File file = 
			      new File(CONFIG_PROPERTIES); 
		Scanner sc;
		try {
			sc = new Scanner(file);
		HashMap<String, String> map=  new HashMap<String, String>();
			while (sc.hasNext()) {
				String string = sc.nextLine();
				String[] str=string.split(",");
				List<String>	arrList=new ArrayList<String>();
				for (int i = 0; i < str.length; i++) {
						arrList.add(str[i]);
				}
				System.out.println(arrList);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
	  } 
	
	public String gsonMap(HashMap<String, List<String>> mapp){
		String JSONObject="";
		try {
			GsonBuilder gsonMapBuilder = new GsonBuilder();
			 
			Gson gsonObject = gsonMapBuilder.create();
	 
			 JSONObject = gsonObject.toJson(mapp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return JSONObject;
		
	}
	
	public  HashMap<String, List<String>> readData() throws FileNotFoundException {
		File file = new File(CONFIG_PROPERTIES); 
	    Scanner sc = new Scanner(file);
	    HashMap<String, List<String>> hashMapTest = null;
	    HashMap<String, Map<String, List<String>>> hashMapTest1 = null;
	    HashMap<String, HashMap<String, List<String>>> map=null;
	    while (sc.hasNextLine()) {
	    	   String line = sc.nextLine();
	    	   String getliststr = line.replaceAll("\\[\\[", "");
	    	   String getfinallist = getliststr.replaceAll("\\]\\]", "");
				  List<String> al = new ArrayList<String>(); 
				  al = Arrays.asList(getfinallist); 	
				 for(String s: al)
				 { 
					 if(s.contains("ORGANIZATION")) 
					 {
					  if(s.contains("DATE")) 
					  {
						  //System.out.println(al); 
						  String str[] = al.toString().split("], \\["); 
						  List<String> al1 = new ArrayList<String>(); 
						  List<String> al2 = new ArrayList<String>(); 
						  List<String> al3 = new ArrayList<String>(); 
						  al1 = Arrays.asList(str);
						  hashMapTest = new HashMap<String, List<String>>();
						  map  = new HashMap<String, HashMap<String,List<String>>>();
						  for(String s1: al1){
				    			if(s1.contains("ORGANIZATION")) {
				    				al2.add(s1.split(",")[0]);
				    				hashMapTest.put(s1.split(",")[1],al2 );
				    			}
				    		   
				    			if(s1.contains("DATE"))
				    			{
				    				al3.add(s1.split(",")[0]);
				    				hashMapTest.put(s1.split(",")[1],al3 );
				    			}
				    		}
						  System.out.println(hashMapTest);
					  }
					  }
				 }
				  
				 
	    	}
		return hashMapTest; 
	}
	
	public void getMapEnity() throws FileNotFoundException {
		HashMap<String, List<String>> map= readData();
		for (Map.Entry<String,List<String>> entry : map.entrySet())  {
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue()); 
           
    } 
	}
}