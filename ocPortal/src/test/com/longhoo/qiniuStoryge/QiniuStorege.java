package com.longhoo.qiniuStoryge;

import javax.swing.text.html.ImageView;

import com.online.college.common.storage.QiniuImg;
import com.online.college.common.storage.ThumbModel;

public class QiniuStorege {
	

	/**
	 * 上传单张图片；返回上传图片的key
	 * @param buff
	 */
	public static String uploadImage(byte[] buff){
		String key = QiniuKeyGenerator.generateKey();
		String uploadKey = QiniuWrapper.upload(buff, key, false);
		return uploadKey;
	}
	
	/**
	 * 上传单张图片；返回上传图片的url，此url会过期，切记不要存储在数据库中；
	 * @param buff
	 * @param img
	 * @return QiniuImg
	 */
	public static QiniuImg uploadImage(byte[] buff,QiniuImg img){
		String key = QiniuWrapper.upload(buff, img.getUploadKey(), false);
		img.setKey(key);
		return img;
	}
	
	
	/**
	 * 上传多个图片
	 * @param imageBuffs 图片字节数组
	 * @param img 分组图片的属性
	 * @return
	 */
	public static QiniuImg[] uploadImages(byte[][] imageBuffs,QiniuImg img){
		QiniuImg[] image = new QiniuImg[imageBuffs.length];
		for(int i=0;i<image.length;i++){
			QiniuImg imag = new QiniuImg();
			QiniuImg uploadImage = uploadImage(imageBuffs[1],imag);
			image[i]=uploadImage;
		}
		return image;
	}
	
	
	/**
	 * 获取图片链接
	 * @param key
	 * @return
	 */
	public static String getUrl(String key){
		return null;
	}
	
	/**
	 * 获取有有效期的图片链接
	 * @param key
	 * @param expires 单位：秒
	 * @return
	 */
	public static String getUrl(String key,long expires){
		return null;
	}
	
	
	/**
	 * 获取图片链接
	 * @param key
	 * @return
	 */
	public static String getUrl(String key,ThumbModel model){
		return null;
	}
	
	/**
	 * 获取有有效期的图片链接
	 * @param key
	 * @param expires 单位：秒
	 * @return
	 */
	public static String getUrl(String key,ThumbModel model,long expires){
		return null;
	}
	
}
