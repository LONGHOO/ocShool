package com.online.college.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.longhoo.qiniuStoryge.FileTypeHelper;
import com.longhoo.qiniuStoryge.QiniuStorege;
import com.online.college.common.storage.QiniuStorage;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	Logger log = Logger.getLogger(AppTest.class);
	
//	public void testCreate() {
//		IAuthUserService authUserService = (IAuthUserService) SpringBeanFactory.getBean("authUserServiceImpl");
//		AuthUser authUser = new AuthUser();
//		authUser.setUsername("test1");
//		authUser.setRealname("testRealName");
//		authUser.setPassword("testPassword");
//		System.out.println("id="+authUser.getId());
//		authUserService.createSelectivity(authUser);
//		System.out.println("id="+authUser.getId());
//	}
	
//	public void testQuery(){
//		IAuthUserService authUserService = (IAuthUserService) SpringBeanFactory.getBean("authUserServiceImpl");
//		AuthUser byId = authUserService.getById(26L);
//		log.info("userName"+byId.getUsername());
//		log.info("password"+byId.getPassword());
//	}
	
//	public void testUpdate(){
//		IAuthUserService authUserService = (IAuthUserService) SpringBeanFactory.getBean("authUserServiceImpl");
//		AuthUser authUser = authUserService.getById(26L);
//		authUser.setRealname("newRealName");
//		authUserService.updateSelectivity(authUser);
//		log.info("realName"+authUser.getRealname());
//	}
//	public void testPage(){
//		IAuthUserService authUserService = (IAuthUserService) SpringBeanFactory.getBean("authUserServiceImpl");
//		TailPage<AuthUser> tailPage = new TailPage<AuthUser>();
//		tailPage.setPageNum(2);
//		tailPage.setPageSize(5);
//		TailPage<AuthUser> queryPage = authUserService.queryPage(new AuthUser(), tailPage);
//		
//	}
	
//	public void testC(){
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		IConstsSiteCarouselService service =(IConstsSiteCarouselService) ctx.getBean("constsSiteCarouselServiceImpl");
//		List<ConstsSiteCarousel> queryCarousels = service.queryCarousels(4);
//		for(ConstsSiteCarousel item : queryCarousels){
//			System.out.println(item.getPicture());
//		}
//		
//	}
	
//	public void testConstsClassFy(){
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		IConstsClassifyService service =(IConstsClassifyService) ctx.getBean("constsClassifyServiceImpl");
//		TailPage<ConstsClassify> tailPage = service.queryPage(new ConstsClassify(), new TailPage<ConstsClassify>());
//		List<ConstsClassify> ConstsClassifyList = tailPage.getItems();
//		ConstsClassify item1 = ConstsClassifyList.get(0);
//		try {
//			System.out.println(JsonUtil.toJson(item1));
//			String json = JsonUtil.toJson(item1);
//			JSONObject jsonObject = JSONObject.fromObject(json);
//			ConstsClassify bean = (ConstsClassify)JSONObject.toBean(jsonObject, ConstsClassify.class);
//			System.out.println(bean.getName());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	 
//	public void testCollege() throws IOException{
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		IConstsCollegeService service =(IConstsCollegeService) ctx.getBean("constsCollegeServiceImpl");
//		List<ConstsCollege> queryAll = service.queryAll(new ConstsCollege());
//		String json = JsonUtil.toJson(queryAll);
//		System.out.println(json);
//	}
	public void test() throws IOException{
		File file = new File("c:/c1.JPEG");
		byte[] buff = null;
		FileInputStream fis = new FileInputStream(file);
		byte[] b = new byte[1000];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len = fis.read(b)) != -1){
			bos.write(b, 0, len);
		}							
		buff = bos.toByteArray();   
		String uploadImage = QiniuStorege.uploadImage(buff);
		System.out.println(uploadImage);
	}
}
