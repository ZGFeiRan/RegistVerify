package com.feiran.zg.core.base.page;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class PageResult {
	private List<?> listData;// 查询出来的数据
	private Integer totalCount;// 数据的总条数
	
	private Integer totalPage;// 数据总页数
	private Integer prevPage;// 当前页
	private Integer nextPage;// 下一页

	private Integer currentPage = 1;// 当前页是第几页,由用户传入

	private Integer pageSize = 5;// 页面大小是多少,即每一页显示几条数据,由用户传入

	public PageResult(List<?> listData, Integer totalCount, Integer currentPage, Integer pageSize) {
		super();
		this.listData = listData;
		this.totalCount = totalCount;


		if (pageSize<=0){
			this.pageSize = 10;
		}else {
			this.pageSize = pageSize;
		}

		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

		// 如果查询出来的符合条件的总数据条数totalCount为0,则计算出来的totalPage也为0,当计算出来的totalPage为0时将其赋值为1
		if (totalPage<=0){
			this.totalPage = 1;
		}

		// 如果页面传进来的currentPage为0的话,则将当前页设置为1
		if (currentPage<=0){
			this.currentPage = 1;
		}else {
			this.currentPage = currentPage;
		}
		// 判断一下从页面传进来的currentPage是否大于计算出来的totalPage,如果大于总页数的话,则将当前页设置为totalPage
		if (this.currentPage > totalPage){
			this.currentPage = totalPage;
		}

		this.prevPage = currentPage - 1 >= 1 ? currentPage - 1 : 1;
		this.nextPage = currentPage + 1 <= totalPage ? currentPage + 1 : totalPage;
	}


	public static PageResult empty(Integer pageSize) {
		return new PageResult(Collections.EMPTY_LIST, 0, 1, pageSize);
	}
	
	
	
}
