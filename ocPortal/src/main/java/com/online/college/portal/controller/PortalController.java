package com.online.college.portal.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.util.JsonUtil;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.auth.service.impl.AuthUserServiceImpl;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsSiteCarousel;
import com.online.college.core.consts.service.IConstsSiteCarouselService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalBussiness;
import com.online.college.portal.vo.ConstsClassifyVO;

/**
 * 网站主页
 */
@Controller
@RequestMapping()
public class PortalController {
	
	/**
	 * 首页
	 */
	@Autowired
	private IConstsSiteCarouselService siteCarouselService;
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IPortalBussiness portalBussiness;
	
	@Autowired
	private IAuthUserService authUserService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		//获取轮播信息
		List<ConstsSiteCarousel> carouselList = siteCarouselService.queryCarousels(4);
		mv.addObject("carouselList", carouselList);
		
		//获取课程分类(一级分类）
		List<ConstsClassifyVO> classifys = portalBussiness.queryAllClassify();
		
		//课程推荐
		portalBussiness.prepareRecomdCourse(classifys);
		mv.addObject("classifys", classifys);
		
		//获取5们实战课推荐，根据权重（weight）进行排序
		CourseQueryDto courseQueryDto = new CourseQueryDto();
		courseQueryDto.setCount(5);
		courseQueryDto.setFree(CourseEnum.FREE_NOT.value());
		courseQueryDto.descSortField("weight");
		List<Course> actionCourseList = courseService.queryList(courseQueryDto);
		mv.addObject("actionCourseList",actionCourseList);
		
		//获取5门免费课程推荐，根据权重排序（weight）
		courseQueryDto.setCount(5);
		courseQueryDto.setFree(CourseEnum.FREE.value());
		List<Course> freeCourseList = courseService.queryList(courseQueryDto);
		mv.addObject("freeCourseList", freeCourseList);
		
		//获取7门java课程，根据权重（学习数量studyCount）进行排序
		courseQueryDto.setCount(7);
		courseQueryDto.descSortField("studyCount");
		courseQueryDto.setSubClassify("java");
		courseQueryDto.setFree(null);//部分免费或收费
		List<Course> javaCourseList = this.courseService.queryList(courseQueryDto);
		mv.addObject("javaCourseList", javaCourseList);
		
		//获取页面上的名校讲师5名
		List<AuthUser> queryRecomd = authUserService.queryRecomd();
		mv.addObject("recomdTeacherList", queryRecomd);
		return mv;
	}
	
}
