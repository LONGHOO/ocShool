package testpages;

import java.util.List;

public interface IPage<T> extends Iterable<T>{
	//起始页号
	int getFirstPageNum();
	
	//终止页号
	int getLastPageNum();
	
	//是否是首页
	boolean isFirstPage();
	
	//是否是最后一页
	boolean isLastPage();
	
	//获取当前页号
	int getPageNum();
	
	//获取分页大小
	int getPageSize();
	
	//分页数据
	List<T> getItems();

	//获取总记录数
	int getItemsTotalCount();
	
	//获取总页数
	int getPageTotalCount();
	
	//内容是否为空
	boolean isEmpty();
}
