package com.online.college.opt.business.impl;


import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online.college.opt.business.ICourseVideoBusiness;

//视频业务层
@Service
public class CourseVideoBusinessImpl implements ICourseVideoBusiness {

	@Override
	public void doInportVideo(String courseName,MultipartFile videoPackage,String path){
		try {
			String parent = path+courseName;
			new File(parent,videoPackage.getOriginalFilename()).mkdirs();
			videoPackage.transferTo(new File(parent,videoPackage.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	 public void unrar(String srcRarPath,String dstDirectoryPath){  
		
    }  
}
