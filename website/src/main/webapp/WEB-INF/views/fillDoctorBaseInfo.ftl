<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>完善个人信息</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
        <script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript" src="/js/jquery/jquery-2.1.3.js"></script>
		<script type="text/javascript" src="/js/jquery/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="/js/widget-master/code/jquery.inputFormat.js"></script>
        <style type="text/css">
            #doctorBaseInfoForm input ,#doctorBaseInfoForm select{
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
        </style>
        <script type="text/javascript">
			$(function(){
				// 格式化输入框
                $('input[name="phoneNumber"]').inputFormat({type:'mobile'});

                //AJAX提交表单
				$("#doctorBaseInfoForm").ajaxForm(function(){
						window.location.reload();
				});

				//上传证件照
				$("#uploadBtn1").uploadify({
					auto:true,
					buttonText:"上传证件照片",
					fileObjName:"file",
					fileTypeDesc:"上传证件照片",
					fileTypeExts:"*.gif; *.jpg; *.png",
					multi:false,
					swf:"/js/plugins/uploadify/uploadify.swf",
					uploader:"/doctorInfoUpload.do",
					// 覆盖默认的效果
					overrideEvents:["onUploadSuccess","onSelect"],
					// 文件上传成功后的回调
					onUploadSuccess:function(file,data,response){
						$("#uploadImg1").attr("src",data);
						$("#uploadImage1").val(data);
					}
				});
			})
		</script>		
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav="personal"/>
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="doctorInfo" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							个人资料
						</div>
						<form id="doctorBaseInfoForm" class="form-horizontal" action="/basicInfo_save.do" method="post" style="width: 700px;" novalidate="novalidate">
                            <fieldset>
							<div class="form-group">
								<label class="col-sm-4 control-label">
									登录用户名
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">${loginInfo.userName}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">
									真实姓名
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<#if (doctorInfo.isRealAuth)>
											${doctorInfo.doctorName}
										<#else>
											未实名认证
											<a href="/realAuth.do">[马上认证]</a>
										</#if>
									</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									身份证号码
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">	
										<#if (doctorInfo.isRealAuth)>
											${realAuth.idNumber}
										<#else>
											未实名认证
											<a href="/realAuth.do">[马上认证]</a>
										</#if>
									</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									手机号码
								</label>
								<div class="col-sm-8">
									<input type="text" name="phoneNumber" autocomplete="off" class="form-control" value="${(doctorInfo.phoneNumber)!''}"></input>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									性别
								</label>
								<div class="col-sm-8">
									<select class="form-control" id="doctor_sex" name="doctorSex" style="width: 70px" autocomplate="off">
										<option value="1">男</option>
										<option value="0">女</option>
									</select>
									<script type="text/javascript">
										$("#doctor_sex option[value=${(doctorInfo.doctorSex)!1}]").attr("selected",true);
									</script>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									所属医院:
								</label>
                                <div class="col-sm-8">
                                    <input type="text" name="hospitalName" autocomplete="off" class="form-control" value="${(doctorInfo.hospitalName)!''}">
                                </div>
								<#--<div class="col-sm-8">-->

									<#--<select class="form-control" id="hospital_name" name="hospital_name" style="width: 180px" autocomplate="off" >-->
										<#--<#list incomeGrades as item>-->
											<#--<option value="${item.id}">${item.title}</option>-->
										<#--</#list>-->
									<#--</select>-->
									<#--<script type="text/javascript">-->
										<#--$("#incomeGrade option[value=${(doctorInfo.incomeGrade.id)!-1}]").attr("selected",true);-->
									<#--</script>-->
								<#--</div>-->
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">
									所属科室:
								</label>
                                <div class="col-sm-8">
                                    <input type="text" name="officesName" autocomplete="off" class="form-control" value="${(doctorInfo.officesName)!''}">
                                </div>
							</div>

							<div class="form-group">
                                <label class="col-sm-4  control-label" for="address">证件照片</label>
                                <div class="col-sm-8">
                                    <p class="text-help text-primary"><b style="margin-left: 80px;color:#0000aa;">请上传证件照片</b></p>
									<div class="idCardItem">
										<div>
											<a href="javascript:;" id="uploadBtn1" >上传证件照片</a>
										</div>
										<img alt="" src="" class="uploadImg" id="uploadImg1" />
										<input type="hidden" name="doctorImg" id="uploadImage1" />
									</div>
                                </div>
							</div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">
                                    医生职称:
                                </label>
                                <div class="col-sm-8">

                                    <select class="form-control" id="doctor_title" name="doctorTitle" style="width: 180px" autocomplate="off" >
									<#list doctorTitleList as item>
                                        <option value="${item.doctorTitle}">${item.doctorTitle}</option>
									</#list>
                                    </select>
                                    <script type="text/javascript">
                                        $("#doctor_title option[value==${(item.doctorTitle)!'其他'}]").attr("selected",true);
                                    </script>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-4 control-label">
                                    医生学位:
                                </label>
                                <div class="col-sm-8">

                                    <select class="form-control" id="doctor_degree" name="doctorDegree" style="width: 180px" autocomplate="off" >
									<#list doctorDegreeList as item>
                                        <option value="${item.doctorDegree}">${item.doctorDegree}</option>
									</#list>
                                    </select>
                                    <script type="text/javascript">
                                        $("#doctor_degree option[value==${(item.doctorDegree)!'其他'}]").attr("selected",true);
                                    </script>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-4 control-label">
                                    医生特长:
                                </label>
                                <div class="col-sm-8">
									<textarea rows="5" cols="50" name="doctorForte" id="doctorForte" style="resize:none" >${(doctorInfo.doctorForte)!''}</textarea>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-4 control-label">
                                    医生简介:
                                </label>
                                <div class="col-sm-8">
									<textarea rows="5" cols="50" name="doctorAbout" id="doctorAbout" style="resize:none">${(doctorInfo.doctorAbout)!''}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
								<button id="submitBtn" type="submit" class="btn btn-primary col-sm-offset-5" data-loading-text="数据保存中" autocomplate="off"
                                        style="margin-left: 430px">
									保存数据
								</button>
							</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>		
	</body>
</html>