package com.longhoo.qiniuStoryge;

import java.util.Arrays;

import org.springframework.util.Assert;

public class FileTypeHelper {

	public static FileType getType(byte[] buff){
		Assert.isTrue(null != buff && buff.length >28);
		byte[] bytes = Arrays.copyOfRange(buff, 0, 28);
		String str = bytesToHex(bytes);
		System.err.println(str);
		for(FileType type : FileType.values()){
			if(str.startsWith(type.getValue())) return type;
		}
		return null;
	}
	
	public static boolean isImage(byte[] buff){
		FileType type = getType(buff);
		return FileType.JPEG.equals(type) || FileType.PNG.equals(type);
	}
	
	public static String bytesToHex(byte[] src){
		if(src == null || src.length <=0) return null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<src.length;i++){
			int x = src[i] & 0xFF;
			String hexString = Integer.toHexString(x);
			if(hexString.length()<2){
				sb.append(0);
			}
			sb.append(hexString);
		}
		return sb.toString().toUpperCase();
	}
}
