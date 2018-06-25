package testpages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.online.college.common.util.BeanUtil;

public abstract class AbstractPage<T> implements IPage<T> {
	
	public static final int DEFAULT_FIRST_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	protected int pageSize = DEFAULT_PAGE_SIZE; //每页显示的记录数
	protected int pageNum = DEFAULT_FIRST_PAGE_NUM;//当前的页号
	protected int itemsTotalCount = 0; //总记录数
	protected int pageTotalCount = 0; //总页数
	protected List<T> items; //记录列表
	protected boolean firstPage; //是否是第一页
	protected boolean lastPage; //是否是最后一页
	protected int startIndex; //开始的序号
	
	private String sortField = "update_time"; //排序字段
	private String sortDirction = "DESC"; //排序方式
	
	@Override
	public Iterator<T> iterator() {
		return this.items.iterator();
	}
	@Override
	public int getFirstPageNum() {
		return DEFAULT_FIRST_PAGE_NUM;
	}
	@Override
	public int getLastPageNum() {
		return pageTotalCount;
	}
	
	@Override
	public boolean isFirstPage() {
		firstPage = (getPageNum() <= getFirstPageNum());
		return firstPage;
	}
	
	@Override
	public boolean isLastPage() {
		return lastPage;
	}
	
	public int getPrePageNum(){
		return isFirstPage()?getFirstPageNum():getPageNum()-1;
	}
	
	public int getNextPageNum(){
		return isLastPage()?getPageNum():getPageNum()+1;
	}
	
	@Override
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum){
		if(pageNum < DEFAULT_FIRST_PAGE_NUM) pageNum = DEFAULT_FIRST_PAGE_NUM;
		this.pageNum = pageNum;
	}
	
	@Override
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pagesize){
		this.pageSize = pagesize;
	}
	
	@Override
	public List<T> getItems() {
		return this.items;
	}
	
	public void setItems(List<T> items){
		if(items == null) items = Collections.emptyList();
		this.items = new ArrayList<T>(items);
		this.firstPage = pageNum == DEFAULT_FIRST_PAGE_NUM;
		this.lastPage = pageNum == this.pageTotalCount;
	}
	
	public void setItemsTotalCount(int itemsTotalCount){
		this.itemsTotalCount = itemsTotalCount;
		if(this.itemsTotalCount % pageSize ==0){
			this.pageTotalCount = this.itemsTotalCount / this.pageSize;
		}else{
			this.pageTotalCount = this.itemsTotalCount / this.pageSize +1;
		}
		if(this.pageNum > this.pageTotalCount){
			this.pageNum = DEFAULT_FIRST_PAGE_NUM;
		}
		if(this.itemsTotalCount <= pageSize){
			this.firstPage = true;
			this.lastPage = true;
		}
	}
	
	@Override
	public int getItemsTotalCount() {
		return itemsTotalCount;
	}
	
	@Override
	public int getPageTotalCount() {
		return this.pageTotalCount;
	}
	
	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public int getStartIndex(){
		this.startIndex = (this.pageNum - 1) * this.pageSize;
		if(this.startIndex <=0){
			this.startIndex = 0;
		}
		return this.startIndex;
	}
	
	public void ascSortField(String sourtField){
		if(StringUtils.isNotEmpty(sourtField)){
			this.sortField = BeanUtil.fieldToColumn(sourtField);
			this.sortDirction = "ASC";
		}
	}
	
	/**
	 * 按照sortField降序
	 * @param sortField ：指java bean中的属性
	 */
	public void descSortField(String sortField) {
		if(StringUtils.isNotEmpty(sortField)){
			this.sortField = BeanUtil.fieldToColumn(sortField);
			this.sortDirction = " DESC ";
		}
	}
	
	public String getSortDirection() {
		return sortDirction;
	}
	
	public void setSortDirection(String sortDirection) {
		this.sortDirction = sortDirection;
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	@Override
    public String toString() {
        return "Page[" + this.getPageNum() + "]:" + items.toString();
    }
}
