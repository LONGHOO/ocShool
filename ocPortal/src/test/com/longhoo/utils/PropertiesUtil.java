package com.longhoo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private static final String FILE = "qiniu.properties";
	private static Map<String,Properties> properMap = new HashMap<String,Properties>();
	public static Properties getDefaultProperties(){
		return getProperties(FILE);
	}
	
	public static Properties getProperties(String fileName){
		if(properMap.get(fileName) == null){
			try {
				Properties properties = new Properties();
				properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(FILE));
				properMap.put(fileName, properties);
				return properties;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return properMap.get(fileName);
		}
		return null;
	}
}
