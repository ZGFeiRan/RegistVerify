<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<#include "common/links-tpl.ftl" />

		<link rel="stylesheet" href="/css/bank.css">
		<script type="text/javascript" src="/js/bank.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){

				$('#pagination').twbsPagination({
					totalPages : ${pageResult.totalPage}||1,
					startPage : ${pageResult.currentPage},
                    visiblePages : 5,
                    first : "首页",
                    prev : "上一页",
                    next : "下一页",
                    last : "最后一页",
					onPageClick : function(event, page) {
						$("#currentPage").val(page);
						$("#searchForm").submit();
					}
				});

				$("#query").click(function(){
					$("#currentPage").val(1);
					$("#searchForm").submit();
				});

				$(".beginDate,.endDate").click(function(){
					WdatePicker();
				});
			});
	 </script>
	</head>
	<body>

		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<#assign currentNav="account" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="recharge" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<form action="/accountFlow_list.do" name="searchForm" id="searchForm" class="form-inline">
						<input type="hidden" id="currentPage" name="currentPage" value="" />
						<div class="form-group">
							<label>时间范围</label>
							<input type="text" class="form-control beginDate" name="beginDate" value="${(qo.beginDate?string('yyyy-MM-dd'))!''}"/>
						</div>
						<div class="form-group">
							<label></label>
							<input type="text" class="form-control endDate" name="endDate" value="${(qo.endDate?string('yyyy-MM-dd'))!''}"/>
						</div>

						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="pull-left" style="line-height: 35px;">账户流水明细</span>
							<div class="clearfix"></div>
						</div>
						<table class="table">
							<thead>
								<tr>
									<th>说明</th>
									<th>时间</th>
									<th>变动金额</th>
									<th>可用余额</th>
									<th>冻结金额</th>
								</tr>
							</thead>
							<tbody>
								<#list pageResult.listData as data>
									<tr>
										<td>${data.note}></div></td>
										<td>${data.vdate?string("yyyy-MM-dd HH:mm:ss")}</td>
								        <td>${data.amount}元</td>
										<td>${data.useableAmoumt}元</td>
								        <td>${data.freezedAmount} 元</td>
									</tr>
								</#list>
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