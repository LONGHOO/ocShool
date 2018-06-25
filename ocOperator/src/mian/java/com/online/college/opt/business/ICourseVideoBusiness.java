package com.online.college.opt.business;


import org.springframework.web.multipart.MultipartFile;

//课程视频导入处理
public interface ICourseVideoBusiness {
	
	//导入文件
	public void doInportVideo(String courseName,MultipartFile videoPackage,String fileName);
	
	//解压文件
	void unrar(String srcRarPath, String dstDirectoryPath) throws Exception;

}
