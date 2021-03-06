<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<style type="text/css">
			#realAuthForm input ,#realAuthForm select{
				width: 260px;
			}
			.idCardItem{
				width: 160px;
				height: 150px;
				float:left;
				margin-right:10px;
				border: 1px solid #e3e3e3;
				text-align: center;
				padding: 5px;
			}
			.uploadImg{
				width: 120px;
				height: 100px;
				margin-top: 5px;
			}
			.swfupload{
				left: 0px;
				cursor: pointer;
			}
			.uploadify{
				height: 20px;
				font-size:13px;
				line-height:20px;
				text-align:center;
				position: relative;
				margin-left:auto;
				margin-right:auto;
				cursor:pointer;
				background-color: #337ab7;
			    border-color: #2e6da4;
			    color: #fff;
			}
			#viewExample{
				position: relative;
				left: 50px;
				top: 60px;
			}
			.uploadExample{
				width: 200px;
				height: 130px;
				margin-bottom: 20px;
			}
		</style>

		<script type="text/javascript">
			$(function(){
				$("#viewExample").popover({
					html:true,
					content:'正面<img src="/images/upload_source_2.jpg" class="uploadExample"/><br/>反面<img src="/images/upload_source_2_1.jpg" class="uploadExample"/>',
					trigger:"hover",
					placement:"top"
				});

				//上传身份证的正面,把上传身份证正面的a标签变成一个uploadify的组件   ;jsessionid=
				$("#uploadBtn1").uploadify({
					auto:true,
					buttonText:"身份证正面",
					fileObjName:"file",
					fileTypeDesc:"身份证正面图片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/realAuthUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data,response){
						$("#uploadImg1").attr("src",data);
						$("#uploadImage1").val(data);
					}
				});

				$("#uploadBtn2").uploadify({
					auto:true,
					buttonText:"身份证反面",
					fileObjName:"file",
					fileTypeDesc:"身份证反面图片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/realAuthUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data){
						$("#uploadImg2").attr("src",data);
						$("#uploadImage2").val(data);
					}
				});
				$("#uploadBtn3").uploadify({
					auto:true,
					buttonText:"上传毕业证",
					fileObjName:"file",
					fileTypeDesc:"上传毕业证图片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/realAuthUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data){
						$("#uploadImg3").attr("src",data);
						$("#uploadImage3").val(data);
					}
				});
				$("#uploadBtn4").uploadify({
					auto:true,
					buttonText:"上传学位证",
					fileObjName:"file",
					fileTypeDesc:"上传学位证图片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/realAuthUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data){
						$("#uploadImg4").attr("src",data);
						$("#uploadImage4").val(data);
					}
				});
				$("#uploadBtn5").uploadify({
					auto:true,
					buttonText:"上传从医资格证",
					fileObjName:"file",
					fileTypeDesc:"上传从医资格证图片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/realAuthUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data){
						$("#uploadImg5").attr("src",data);
						$("#uploadImage5").val(data);
					}
				});

				//Ajax提交表单
				$("#realAuthForm").ajaxForm(function(){
					$.messager.confirm("提示","实名认证申请提交成功!",function(){
						window.location.reload();
					});
				});

				$("#bornDate").addClass("Wdate").click(function(){
					WdatePicker();
				});
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl"/>
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="realAuth"/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							实名认证
						</div>
							<form class="form-horizontal" id="realAuthForm" method="post" action="/realAuth_save.do" novalidate="novalidate">
							<fieldset>
								<div class="form-group">
									<p class="text-center text-danger"><b>请注意：实名认证成功之后不能修改信息，请认真填写！</b></p>
								</div>
								<div class="form-group">
						        	<label class="col-sm-4 control-label ">登录用户名：</label>
					        		<div class="col-sm-8">
						        		<p class="form-control-static">${loginInfo.userName}</p>
						        	</div>
						        </div>
								<div class="form-group">
						        	<label class="col-sm-4 control-label" for="realName">真实姓名：</label>
					        		<div class="col-sm-8">
						        		<input id="realName" name="realName" class="form-control" type="text" value="">
						        	</div>
						        </div>
						        <div class="form-group">
						        	<label class="col-sm-4  control-label" for="sex">性别：</label>
					        		<div class="col-sm-8">
						        		<select id="sex" class="form-control" name="sex" size="1">
											<option value="0">女</option>
											<option value="1">男</option>
										</select>
						        	</div>
						        </div>
						        <div class="form-group">
						        	<label class="col-sm-4  control-label" for="idNumber">分身证号码：</label>
					        		<div class="col-sm-8">
						        		<input id="idNumber" class="form-control" name="idNumber"  type="text" value="">
						        	</div>
						        </div>
						        <div class="form-group">
						        	<label class="col-sm-4  control-label" for="bornDate">出生日期：</label>
					        		<div class="col-sm-8">
						        		<input id="bornDate"  class="form-control" name="bornDate" type="text">
                                        <label style="color:#999;">格式为:"yyyy-MM-dd"，例如:1993-05-04</label>
						        	</div>

						        </div>
						        <div class="form-group">
						        	<label class="col-sm-4  control-label" for="address">身份证地址：</label>
					        		<div class="col-sm-8">
						        		<input placeholder="身份证地址请填写身份证上的地址" id="address" class="form-control" name="address"  type="text" style="max-width: 100%;width:500px;">
						        	</div>
						        </div>
						        
						        <div class="form-group">
						        	<label class="col-sm-4  control-label" for="address">身份证照片</label>
					        		<div class="col-sm-8">
					        			<p class="text-help text-primary"><b style="margin-left: 80px;color:#0000aa;">请上传身份证的正反两面照片</b></p>
					        			<a href="javascript:;" id="viewExample">查看样板</a>
					        			<div class="idCardItem">
					        				<div>
					        					<a href="javascript:;" id="uploadBtn1" >上传正面</a>
					        				</div>
					        				<img alt="" src="" class="uploadImg" id="uploadImg1" />
					        				<input type="hidden" name="image1" id="uploadImage1" />
					        			</div>
					        			<div class="idCardItem">
					        				<div>
					        					<a href="javascript:;" id="uploadBtn2" >上传反面</a>
					        				</div>
					        				<img alt="" src="" class="uploadImg" id="uploadImg2"/>
					        				<input type="hidden" name="image2" id="uploadImage2" />
					        			</div>
					        			<div class="clearfix"></div>
						        	</div>
						        </div>
                                <div class="form-group">
                                    <label class="col-sm-4  control-label" for="address">证书照片</label>
                                    <div class="col-sm-8">
                                        <p class="text-help text-primary"><b style="margin-left: 80px;color:#0000aa;">请上传相关证书的正面清晰照片</b></p>
                                        <div class="idCardItem">
                                            <div>
                                                <a href="javascript:;" id="uploadBtn3" >上传毕业证</a>
                                            </div>
                                            <img alt="" src="" class="uploadImg" id="uploadImg3" />
                                            <input type="hidden" name="image3" id="uploadImage3" />
                                        </div>
                                        <div class="idCardItem">
                                            <div>
                                                <a href="javascript:;" id="uploadBtn4" >上传学位证</a>
                                            </div>
                                            <img alt="" src="" class="uploadImg" id="uploadImg4"/>
                                            <input type="hidden" name="image4" id="uploadImage4" />
                                        </div>
                                        <div class="idCardItem">
                                            <div>
                                                <a href="javascript:;" id="uploadBtn5" >上传从医资格证</a>
                                            </div>
                                            <img alt="" src="" class="uploadImg" id="uploadImg5"/>
                                            <input type="hidden" name="image5" id="uploadImage5" />
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
						        <div class="form-group">
						        	<button type="submit" id="asubmit" class="btn btn-primary col-sm-offset-4" data-loading-text="正在提交" style="margin-left: 430px"><i class="icon-ok"></i> 提交认证</button>
						        </div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>		
	</body>
</html>