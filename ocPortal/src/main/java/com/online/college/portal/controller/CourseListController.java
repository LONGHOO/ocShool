package com.online.college.portal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.util.JsonUtil;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalBussiness;
import com.online.college.portal.vo.ConstsClassifyVO;

import net.sf.json.util.JSONUtils;

/**
 * 课程分类页
 */

@Controller
@RequestMapping("/course")
public class CourseListController {
	
	@Autowired
	private IConstsClassifyService constsClassifyService;
	
	@Autowired
	private IPortalBussiness portalBusiness;
	
	@Autowired
	private ICourseService courseService;
	
	/**
	 * 课程分类页
	 * @param c 分类code
	 * @param sort 排序
	 * @param page 分页
	 * @throws IOException 
	 */
	@RequestMapping("/list")
	public ModelAndView list(String c, String sort, TailPage<Course> page) throws IOException{
		ModelAndView mv = new ModelAndView("list");
		//当前方向code
		String curCode = "-1";
		//当前分类code
		String curSubCode = "-2";
		
		//加载所有课程分类
		Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		//所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
				
		//当前分类
		ConstsClassify curClassify = constsClassifyService.getByCode(c);
		
		if(null == curClassify){//没有此分类，加载所有二级分类
			List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
			for(ConstsClassifyVO vo : classifyMap.values()){
				subClassifys.addAll(vo.getSubClassifyList());
			}
			mv.addObject("subClassifys", subClassifys);
		}else{
			if(!"0".endsWith(curClassify.getParentCode())){//当前是二级分类
				curSubCode = curClassify.getCode();
				curCode = curClassify.getParentCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getParentCode()).getSubClassifyList());//此分类平级的二级分类
			}else{//当前是一级分类
				curCode = curClassify.getCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getCode()).getSubClassifyList());//此分类下的二级分类
			}
		}
		mv.addObject("curCode", curCode);
		mv.addObject("curSubCode", curSubCode);
		
		//分页排序数据
		//分页的分类参数
		Course queryEntity = new Course();
		//
		if(!"-1".equals(curCode)){
			queryEntity.setClassify(curCode);
		}
		if(!"-2".equals(curSubCode)){
			queryEntity.setSubClassify(curSubCode);
		}
		
		//排序参数   默认排序是最热
		if("pop".equals(sort)){//最热
			page.descSortField("studyCount");
		}else{
			sort = "last";
			page.descSortField("id");
		}
		mv.addObject("sort", sort);
		
		//分页参数
		queryEntity.setOnsale(CourseEnum.ONSALE.value());
		page = this.courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		return mv;
	}
}
