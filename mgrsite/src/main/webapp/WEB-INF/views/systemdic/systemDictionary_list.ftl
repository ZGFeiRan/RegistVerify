<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>名医平台(系统管理平台)</title>
	<#include "../common/header.ftl"/>
	<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$(function() {
			$("#pagination").twbsPagination({
				totalPages:${pageResult.totalPage},
				visiblePages:5,
				startPage:${qo.currentPage},
				first:"首页",
				prev:"上一页",
				next:"下一页",
				last:"尾页",
				onPageClick:function(event,page){
					$("#currentPage").val(page);
					$("#searchForm").submit();
				}
			});
			
			$("#queryButton").click(function(){
				$("#currentPage").val(1);
				$("#searchForm").submit();
			});

			
//			//点击保存
//			$("#editForm").ajaxForm(function(data){
//				$.messager.confirm("提示","保存成功",function(){
//					window.location.reload();
//				});
//			});
//			$("#saveBtn").click(function(){
//				$("#editForm").submit();
//			});
//
//			//点击修改按钮
//			$(".edit_Btn").click(function(){
//				var json=$(this).data("jsonstr");
//				$("#systemDictionaryId").val(json.id);
//				$("#title").val(json.title);
//				$("#sn").val(json.sn);
//				$("#systemDictionaryModal").modal("show");
//			});
//


			// 监听"添加数据字典"按钮的点击事件
			$("#addSystemDictionaryBtn").click(function () {
                // 先清空form表单
                $("#editForm")[0].reset();
				// 清空隐藏域中的数据,因为清空form表单时不能把隐藏域中的数据清空
                $("#systemDictionaryId").val("");
                // 显示模态窗口
				$("#systemDictionaryModal").modal("show");
            });

			// 监听模态框"保存"按钮的点击事件
			$("#saveButton").click(function () {
				$("#editForm").ajaxSubmit({
					success:function (data) {
						if(data.success){
							$.messager.confirm("温馨提示","保存成功 ",function () {
								window.location.reload();
                            })
						}else{
							$.messager.popup(data.msg);
						}
                    }
				});
            });

			// 对模态框的输入域进行校验
//			$("#editForm").validate({
//                rules:{
//                    title:{
//                        required:true,
//                    },
//                    sn:{
//                        required:true,
//                    }
//                },
//                messages:{
//                    userName:{
//                        required:"标题不能为空",
//                    },
//                    password:{
//                        required:"sn不能为空",
//                    }
//                },
//                errorClass:"text-danger",
//                highlight:function (element) {
//                    console.log($(element).closest("div.form-group span").children("span"));
//                    $(element).closest("div.form-group").removeClass("has-success");
//                    $(element).closest("div.form-group").addClass("has-error");
//
//                    $(element).closest("div.col-sm-10").children("span").removeClass("glyphicon-ok");
//                    $(element).closest("div.col-sm-10").children("span").addClass("glyphicon-remove");
//                },
//                unhighlight:function (element,errorClass) {
//                    $(element).closest("div.form-group").removeClass("has-error");
//                    $(element).closest("div.form-group").addClass("has-success");
//
//                    $(element).closest("div.col-sm-10").children("span").removeClass("glyphicon-remove");
//                    $(element).closest("div.col-sm-10").children("span").addClass("glyphicon-ok");
//                }
//			});
			$(".edit_Btn").click(function () {
                // 先清空form表单
                $("#editForm")[0].reset();
				// 往form表单中填充数据
				var json = $(this).data("jsonstring");
				$("#systemDictionaryId").val(json.id);
				$("#sn").val(json.sn);
				$("#title").val(json.title);
				// 显示模态窗口
                $("#systemDictionaryModal").modal("show");
            });
		});
	});
	</script>
</head>

<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3">
				<#assign currentMenu = "systemDictionary" />
				<#include "../common/menu.ftl" />
			</div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>数据字典管理</h3>
				</div>
				<div class="row">
					<!-- 提交分页的表单 -->
					<form id="searchForm" class="form-inline" method="post" action="/systemDictionary_list.do">
						<input type="hidden" name="currentPage" id="currentPage" value=""/>
						<div class="form-group">
						</div>
						<div class="form-group">
						    <label>关键字</label>
						    <input class="form-control" type="text" name="keyWord" value="${(qo.keyWord)!''}">
						</div>
						<div class="form-group">
							<button id="queryButton" type="button" class="btn btn-success"><i class="icon-search"></i> 查询</button>
							<a href="javascript:void(-1);" class="btn btn-success" id="addSystemDictionaryBtn">添加数据字典</a>
						</div>
					</form>
				</div>
				<div class="row">
					<table class="table">
						<thead>
							<tr>
								<th>名称</th>
								<th>编码</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list pageResult.listData as vo>
							<tr>
								<td>${vo.title}</td>
								<td>${vo.sn}</td>
								<td>
									<a href="javascript:void(-1);" class="edit_Btn" data-jsonstring='${(vo.jsonString)!""}'>修改</a>
								</td>
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
	
	<div id="systemDictionaryModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">编辑/增加</h4>
	      </div>
	      <div class="modal-body">
	      		<form id="editForm" class="form-horizontal" method="post" action="/systemDictionary_update.do" style="margin: -3px 118px">
			    <input id="systemDictionaryId" type="hidden" name="id" value="" />
			   	<div class="form-group">
				    <label class="col-sm-2 control-label">名称</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="title" name="title" placeholder="字典分类名称">
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-sm-2 control-label">编码</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="sn" name="sn" placeholder="字典分类编码">
				    </div>
				</div>
		   </form>
		  </div>
	      <div class="modal-footer">
	      	<a href="javascript:void(0);" class="btn btn-success" id="saveButton" aria-hidden="true">保存</a>
		    <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>