<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>完善个人信息</title>
    <#include "common/links-tpl.ftl" />
    <link type="text/css" rel="stylesheet" href="/css/account.css" />
    <#--<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>-->
    <#--<script type="text/javascript" src="/js/widget-master/public/script/jquery.min.js"></script>-->
    <#--<link rel="stylesheet" type="text/css" href="/js/widget-master/public/style/cssreset-min.css">-->
    <#--<link rel="stylesheet" type="text/css" href="/js/widget-master/public/style/common.css">-->
    <#--<link rel="shortcut icon" href="/js/widget-master/public/image/favicon.png">-->

    <#--<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>-->

    <#--<script type="text/javascript" src="/js/widget-master/code/jquery.citys.js"></script>-->
    <#--<script type="text/javascript" src="/js/area.js"></script>-->

    <#--<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>-->
        <style type="text/css">
            #doctorVisitForm input ,#doctorVisitForm select{
                width: 260px;
            }

            /*body{ background:#EEEEEE;margin:0; padding:0; font-family:"微软雅黑", Arial, Helvetica, sans-serif; }*/
            /*a{ color:#006600; text-decoration:none;}*/
            /*a:hover{color:#990000;}*/
            /*.top{ margin:5px auto; color:#990000; text-align:center;}*/
            /*.info select{ border:1px #993300 solid; background:#FFFFFF;}*/
            /*.info{ margin:5px; text-align:center;}*/
            /*.info #show{ color:#3399FF; }*/
            /*.bottom{ text-align:right; font-size:12px; color:#CCCCCC; width:1000px;}*/


            /*.citys{*/
                /*margin-bottom: 10px;*/
            /*}*/
            /*.citys p{*/
                /*line-height: 28px;*/
            /*}*/
            /*.warning{*/
                /*color: #c00;*/
            /*}*/
            /*.main a{*/
                /*margin-right: 8px;*/
                /*color: #369;*/
            /*}*/


            /*.el-login-form{*/
                /*width:600px;*/
                /*margin-left:auto;*/
                /*margin-right:auto;*/
                /*margin-top: 30px;*/
            /*}*/
            /*.el-login-form .form-control{*/
                /*width: 220px;*/
                /*display: inline;*/
            /*}*/
        </style>
        <#--<script type="text/javascript">-->
            <#--var Gid  = document.getElementById ;-->
            <#--var showArea = function(){-->
                <#--Gid('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" +-->
                        <#--Gid('s_city').value + " - 县/区" +-->
                        <#--Gid('s_county').value + "</h3>"-->
            <#--};-->
            <#--Gid('s_county').setAttribute('onchange','showArea()');-->
        <#--</script>-->
        <script type="text/javascript">
//			$(function() {
//                //AJAX提交表单
//                $("#doctorVisitForm").ajaxForm(function () {
//                    window.location.reload();
//                });

//                $(".beginDate,.endDate").addClass("Wdate").click(function () {
//                    WdatePicker();
//                });

//                $("#bidBtn").on("click",function(){
//                    var amount = parseFloat($("#amount").val());
//                    if(!amount){
//                        $.messager.popup("请输入投资金额");
//                        return;
//                    }
//                    if(parseFloat($("#usableAmount").val())<amount){
//                        $.messager.popup("投资金额已超过账户余额");
//                        return;
//                    }
//                    if(parseFloat($("#minBidAmount").val())>amount){
//                        $.messager.popup("投资金额低于最小投标金额");
//                        return;
//                    }
//                    if(parseFloat($("#maxBidAmount").val())<amount){
//                        $.messager.popup("投资金额已超过借款标金额");
//                        return;
//                    }
//                    $.post("/",{
//
//                    },function(data){
//                        if(data.success){
//                            $.messager.popup("恭喜你,投标成功");
//                            location.reload();
//                        }else{
//                            $.messager.popup(data.message);
//                        }
//                    });
//                });

//                var doctorVisitForm = $("#doctorVisitForm");

//                doctorVisitForm.ajaxForm({
//                    success:function (data) {
//                        if (data.success){
//                            $.messager.confirm("温馨提示","信息发布成功,点击确认按钮进入个人坐诊信息发布历史记录",function () {
//                                window.location.href="doctorVisitHistory.do";
//                            });
//                        }else {
//                            $.messager.popup(data.msg);
//                        }
//                    }
//                });
//
//                doctorVisitForm.validate({
//                    rules:{
//                        beginDate:"required",
//                        endDate:"required",
//                        town:"required",
//                        toplimit:"required"
//                    },
//                    messages:{
//                        beginDate:"开始时间不能为空",
//                        endDate:"结束时间不能为空",
//                        town:"坐诊场地不能为空"
//                        toplimit:"就诊上限人数不能为空"
//                    },
//                    errorClass:"text-danger",
//                    highlight:function (element) {
//                        $(element).closest("div.form-group").addClass("has-error");
//                    },
//                    unhighlight:function (element) {
//                        $(element).closest("div.form-group").addClass("has-success");
//                    }
//                });
//            }

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
						<form id="doctorVisitForm" class="form-horizontal el-login-form" action="/doctorVisitInfo_save.do" method="post" style="width: 900px;">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">坐诊时间：</label>
                                <div class="col-sm-8">
									<input id="beginDate" style="width: 170px; height: 30px" type="text" class="Wdate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true , maxDate:'#F{$dp.$D(\'endDate\')}'});"  placeholder="请输入开始时间"/>
									&nbsp;~&nbsp;<input id="endDate" style="width: 170px; height: 30px" type="text" class="Wdate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true , minDate:'#F{$dp.$D(\'beginDate\')}'});" placeholder="请输入结束时间"/>
                                </div>
                            </div>

							<div class="form-group">
								<label class="col-sm-2 control-label">
									坐诊场地：
								</label>
                                <div>
                                    <#--<div class="col-sm-8">-->
                                        <#--<select style="width: 120px" id="s_province" name="s_province"></select>  -->
                                        <#--<select style="width: 120px" id="s_city" name="s_city" ></select>  -->
                                        <#--<select style="width: 120px" id="s_county" name="s_county"></select>-->
                                        <#--<script class="resources library" src="/js/area.js" type="text/javascript"></script>-->

                                        <#--<script type="text/javascript">_init_area();</script>-->
                                    <#--</div>-->
                                    <#--<div id="show"></div>-->

                                    <div id="location" class="citys col-sm-8">
                                        <p>您现在的位置：
                                            <select style="width: 120px" name="province"></select>
                                            <select style="width: 120px" name="city"></select>
                                            <select style="width: 120px" name="area"></select>
                                        </p>
                                        <script type="text/javascript">
                                            if(remote_ip_info){
                                                $('#location').citys({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
                                            }
                                        </script>
                                        <br/>
                                        <div id="position" class="citys col-sm-30">
                                            <p>您坐诊的地点：
                                                <select style="width: 120px" name="province"></select>
                                                <select style="width: 120px" name="city"></select>
                                                <select style="width: 120px" name="area"></select>
                                                <select style="width: 120px" name="town"></select>
                                                <p id="place">请选择地区</p>
                                            </p>
                                            <script type="text/javascript">
                                                var $town = $('#position select[name="town"]');
                                                var townFormat = function(info){
                                                    $town.hide().empty();
                                                    if(info['code']%1e4&&info['code']<7e5){	//是否为“区”且不是港澳台地区
                                                        $.ajax({
                                                            url:'http://passer-by.com/data_location/town/'+info['code']+'.json',
                                                            dataType:'json',
                                                            success:function(town){
                                                                $town.show();
                                                                for(i in town){
                                                                    $town.append('<option value="'+i+'">'+town[i]+'</option>');
                                                                }
                                                            }
                                                        });
                                                    }
                                                };

                                                $('#position').citys({
                                                    province:'福建',
                                                    city:'厦门',
                                                    area:'思明',
                                                    required:false,
                                                    nodata:'disabled',
                                                    onChange:function(info){
                                                        var text = info['direct']?'(直辖市)':'';
                                                        $('#place').text('当前选中地区：'+info['province']+text+' '+info['city']+' '+info['area']);
                                                        townFormat(info);
                                                    }
                                                },function(api){
                                                    var info = api.getInfo();
                                                    townFormat(info);
                                                });

                                                var flage = 0;
                                                var tempText = '';

                                                $('#position select[name="province"]').click(function(){
                                                    flage = 0;
                                                    tempText = '';
                                                    alert("province被点击"+flage+"  "+tempText);
                                                });

                                                $('#position select[name="town"]').click(function(){
                                                    alert(flage+"  "+tempText);
                                                    console.log()
                                                    if(flage==0){
                                                        tempText = $('#place').text();
                                                    }
                                                    $('#place').text(tempText+' '+$('select[name="town"] option:selected').text());
                                                    flage = flage + 1;
                                                });
                                            </script>
                                        </div>
                                    </div>
                                </div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">
									就诊人数上限：
								</label>
                                <div class="col-sm-8">
                                    <input type="text" name="toplimit" autocomplete="off" class="form-control" autocomplete="off" value=""/>
                                    <p class="help-block"><strong>在坐诊时间内预计最多可接待多少就诊</strong></p>
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



