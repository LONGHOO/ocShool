package com.longhoo.qiniuStoryge;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.util.StringUtils;

import com.longhoo.utils.PropertiesUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


public class QiniuWrapper {
	
	private static final String BUCKET="bucket";
	private static final String CDNS="cdns";
	private static final String ACCESS_KEY="AccessKey";
	private static final String SECRET_KEY="SecretKey";
	
	private static final Auth auth;
	private static String buckName;
	private static String cdn;
	private static final UploadManager uploadManager;
	static{
		Properties properties = PropertiesUtil.getDefaultProperties();
		Configuration cfg = new Configuration(Zone.zone1());
		buckName = properties.getProperty(BUCKET);
		cdn = properties.getProperty(CDNS);
		System.out.println(properties.getProperty(ACCESS_KEY)+"      "+properties.getProperty(SECRET_KEY));
		auth = Auth.create(properties.getProperty(ACCESS_KEY), properties.getProperty(SECRET_KEY));
		uploadManager = new UploadManager(cfg);
	}

	/**
	 * 获取上传资源的token
	 */
	public static String getUploadToken(){
		return auth.uploadToken(buckName);
	}
	
	/**
	 * 获取更新资源的token，只能用于更新参数key所代表的额资源
	 */
	
	public static String getUploadToken(String key){
		return auth.uploadToken(buckName, key);
	}
	
	/**
	 * 将文件上传到服务器
	 * @param buff 图片字节码
	 * @param key  key
	 * @param update 是否为更新
	 * @return
	 */
	public static String upload(byte[] buff,String key,boolean update){
		String token = update?auth.uploadToken(buckName, key):auth.uploadToken(buckName);
		try {
			Response response = uploadManager.put(buff, getFullKey(buff,key), token);
			DefaultPutRet ret = response.jsonToObject(DefaultPutRet.class);
			return ret.key;
		} catch (QiniuException e) {
			System.out.println("上传失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给文件加上后缀名
	 * @param data
	 * @param key
	 * @return
	 */
	private static String getFullKey(byte[] data, String key) {
		FileType type = FileTypeHelper.getType(data);
		if(type != null){
			return key+"."+type.name().toLowerCase();
		}else{
			return null;
		}
	}
	
	public static String getUrl(String key,String model){
		return getUrl(key,model,3600);
	}

	private static String getUrl(String key, String model, int time) {
		if(StringUtils.hasText(model)){
			return auth.privateDownloadUrl(cdn+"/@"+key+"?"+model, time);
		}else{
			return auth.privateDownloadUrl(cdn+"/@"+key, time);
		}
	}
	
	public static String getUrl(String key){
		if(!StringUtils.isEmpty(key)){
			return auth.privateDownloadUrl(cdn+"/@"+key);
		}else{
			return null;
		}
	}
	
	public static String getUrl(String key,Long expiress){
		if(!StringUtils.isEmpty(key)){
			return auth.privateDownloadUrl(cdn + "/@"+key,expiress);
		}else{
			return null;
		}
	}
	
	public static List<String> getUrls(String keys,String model){
		List<String> list = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(keys)){
			list = new ArrayList<String>();
			for(String key : keys.split(",")){
				list.add(getUrl(key,model,3600));
			}
		}
		return list;
	}
}
