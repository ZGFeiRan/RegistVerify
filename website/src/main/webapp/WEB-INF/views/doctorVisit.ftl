<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>完善个人信息</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
        <script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
        <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="/js/area.js"></script>
        <style type="text/css">
            #doctorVisit input ,#doctorVisit select{
                width: 260px;
            }

            body{ background:#EEEEEE;margin:0; padding:0; font-family:"微软雅黑", Arial, Helvetica, sans-serif; }
            a{ color:#006600; text-decoration:none;}
            a:hover{color:#990000;}
            .top{ margin:5px auto; color:#990000; text-align:center;}
            .info select{ border:1px #993300 solid; background:#FFFFFF;}
            .info{ margin:5px; text-align:center;}
            .info #show{ color:#3399FF; }
            .bottom{ text-align:right; font-size:12px; color:#CCCCCC; width:1000px;}
        </style>

        <script type="text/javascript">
            var Gid  = document.getElementById ;
            var showArea = function(){
                Gid('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" +
                        Gid('s_city').value + " - 县/区" +
                        Gid('s_county').value + "</h3>"
            }
            Gid('s_county').setAttribute('onchange','showArea()');
        </script>
        <script type="text/javascript">
			$(function() {
                //AJAX提交表单
                $("#doctorVisit").ajaxForm(function () {
                    window.location.reload();
                });

                $(".beginDate,.endDate").addClass("Wdate").click(function () {
                    WdatePicker();
                });
            }

		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />

		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="doctorVisit" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<#--<input type="hidden" value="${current.id}">-->
						<div class="panel-heading">
							发布坐诊信息
						</div>
						<form id="doctorVisit" class="form-horizontal" action="/doctorVisitInfo_save.do" method="post" style="width: 700px;" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">坐诊时间：</label>
                                <div class="col-sm-8">
									<input id="beginDate" style="width: 170px; height: 30px" type="text" class="Wdate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true , maxDate:'#F{$dp.$D(\'endDate\')}'});"  placeholder="请输入开始时间"/>
									&nbsp;~&nbsp;<input id="endDate" style="width: 170px; height: 30px" type="text" class="Wdate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true , minDate:'#F{$dp.$D(\'beginDate\')}'});" placeholder="请输入结束时间"/>
                                </div>
                            </div>

							<div class="form-group">
								<label class="col-sm-4 control-label">
									坐诊场地：
								</label>
                                <div>
                                    <div class="col-sm-8">
                                        <select style="width: 120px" id="s_province" name="s_province"></select>  
                                        <select style="width: 120px" id="s_city" name="s_city" ></select>  
                                        <select style="width: 120px" id="s_county" name="s_county"></select>
                                        <script class="resources library" src="/js/area.js" type="text/javascript"></script>

                                        <script type="text/javascript">_init_area();</script>
                                    </div>
                                    <div id="show"></div>
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
									所属医院:
								</label>
                                <div class="col-sm-8">
                                    <input type="text" name="hospitalName" autocomplete="off" class="form-control" value="${(doctorInfo.hospitalName)!''}">
                                </div>
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
                                <label class="col-sm-4 control-label">
                                    医生职称:
                                </label>
                                <div class="col-sm-8">

                                    <select class="form-control" id="doctor_title" name="doctorTitle" style="width: 180px" autocomplate="off" >
									<#--<#list doctorTitleList as item>-->
                                        <#--<option value="${item.doctorTitle}">${item.doctorTitle}</option>-->
									<#--</#list>-->
                                    </select>
                                    <script type="text/javascript">
                                        <#--$("#doctor_title option[value==${(item.doctorTitle)!'其他'}]").attr("selected",true);-->
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
						</form>
					</div>
				</div>
			</div>
		</div>		
	</body>
</html>