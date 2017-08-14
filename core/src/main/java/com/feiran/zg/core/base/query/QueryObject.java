package com.feiran.zg.core.base.query;


//import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class QueryObject {
	private Integer currentPage = 1;// 当前页是第几页,由用户传入

	private Integer pageSize = 10;// 页面大小是多少,即每一页显示几条数据,由用户传入

	public Integer getStart(){
		if (currentPage<0 || currentPage==null){
			return 0;
		}
		if (pageSize<=0){
			this.pageSize = 5;
		}
		return (currentPage - 1) * pageSize;
	}






//	private List<String> condition = new ArrayList<String>();
//	private List<Object> params = new ArrayList<Object>();
//
//	private Boolean picedFlage = false;
//
//	private void inited(){
//		if (!picedFlage) {
//			this.customerizedQuery();
//			picedFlage = true;
//		}
//	}
//
//	public List<Object> getParams(){
//		this.inited();
//		return this.params;
//	}
//
//	protected void customerizedQuery(){
//
//	}
//
//	public String getQuery(){
//		this.inited();
//		if (condition.size() == 0) {
//			return "";
//		}
//		StringBuffer sb = new StringBuffer();
//		for (String s : condition) {
//			sb.append(s).append(" and ");
//		}
//
//		return " where "+sb.toString();
//
////		return " where " + StringUtils.join(condition, " and ");
//	}
//
//	public void addConditionAndParams(String condition,Object...params){
//		this.condition.add(condition);
//		this.params.addAll(Arrays.asList(params));
//	}
//
//	protected boolean stringHasLength(String param){
//		return param!=null && !"".equals(param.trim());
//	}
}
