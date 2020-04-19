package com.havasmedia.basetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	}
