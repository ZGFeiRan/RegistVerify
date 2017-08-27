<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
        <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){


                var searchForm = $("#searchForm");
                var gridBody = $("#gridBody");
                searchForm.ajaxForm(function(data){
                    gridBody.hide();// 把table的body隐藏起来
                    gridBody.html(data);// 替换table的body中内容
                    gridBody.show(500);// 把table的body以淡入的方式重新展示出来
                });
                searchForm.submit();// 当页面加载完毕的时候,立即发送请求去获取页面中的数据


                //注册对查询按钮点击事件的监听
				$("#queryButton").click(function(){
					$("#currentPage").val(1);
					$("#searchForm").submit();
				});


			});
		</script>
	</head>
	<body>
	
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="visitInfoHistory" />
					<#include "common/leftmenu-tpl.ftl" />		
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<form action="/visitInfoHistory_list.do" id="searchForm" name="searchForm" class="form-inline" method="post">
						<input type="hidden" id="currentPage" name="currentPage" value="1" />
						<div class="form-group">
							<label>坐诊时间范围:</label>
							<input style="height: 30px" type="text" class="form-control Wdate" name="visitBeginDate" value='${(qo.visitBeginDate?string("yyyy-MM-dd HH:mm:ss"))!""}'
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请输入开始时间"/>
						</div>
						<div class="form-group">
                            <label>&nbsp; ~~ &nbsp;</label>
							<input style="height: 30px" type="text" class="form-control Wdate" name="visitEndDate" value='${(qo.visitEndDate?string("yyyy-MM-dd HH:mm:ss"))!""}'
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" placeholder="请输入结束时间"/>
						</div>
						<div class="form-group">
						    <label>状态</label>
						    <select class="form-control" style="height: 35px" name="isOverdue" id="state">
						    	<option value="">全部</option>
						    	<option value="0">已过期</option>
						    	<option value="1">未过期</option>
						    </select>
						    <script type="text/javascript">
						    	$("#state option[value=${(qo.isOverdue)!1}]").attr("selected",true);
						    </script>
						</div>

                        <div class="form-group">
                            <label>发布时间范围:</label>
                            <input style="height: 30px" type="text" class="form-control Wdate" name="beginPublishDate" value='${(qo.beginPublishDate?string("yyyy-MM-dd"))!""}'
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true });" placeholder="请输入开始时间"/>
                        </div>
                        <div class="form-group">
                            <label>&nbsp; ~~ &nbsp;</label>
                            <input style="height: 30px" type="text" class="form-control Wdate" name="endPublishDate" value='${(qo.endPublishDate?string("yyyy-MM-dd"))!""}'
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true });" placeholder="请输入结束时间"/>
                        </div>
						<div class="form-group">
							<button type="button" id="queryButton" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					
					<div class="panel panel-default" style="margin-top: 20px;">
						<div class="panel-heading">
							登录日志
						</div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>坐诊开始时间</th>
									<th>坐诊结束时间</th>
									<th>发布信息时间</th>
									<th>坐诊场地</th>
									<th>是否过期</th>
								</tr>
							</thead>
							<tbody id="gridBody">

							</tbody>
						</table>
						<div style="text-align: center;">
							<ul id="pagination" class="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>