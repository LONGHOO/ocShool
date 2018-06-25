package com.longhoo.baiduORc;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class BaiduAip {
	
	private static final String APP_ID="10893813";
	private static final String APP_KEY="h6bPR8GrdGbMDqg8OMVzH8UI";
	private static final String SECRIT_KEY="EkPZ92gPlyuYzitGSPQbiD1rnmiqXRRU";
	
	public static void main(String[] args) {
		AipOcr client = new AipOcr(APP_ID, APP_KEY, SECRIT_KEY);
		   client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);
	        HashMap<String, String> options = new HashMap<String, String>();
	        options.put("language_type", "CHN_ENG");
	        options.put("detect_direction", "true");
	        options.put("detect_language", "true");
	        options.put("probability", "true");

	        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
	        // 也可以直接通过jvm启动参数设置此环境变量
	        System.setProperty("aip.log4j.conf", "classpath:log4j.properties");

	        // 调用接口
	        String path = "c:/2.JPG";
	        JSONObject res = client.basicAccurateGeneral(path,options);
	        System.out.println(res.toString(2));
		
	}
}
