<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
		<#include "common/links-tpl.ftl" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		
		<script type="text/javascript">
			$(function(){
				// 点击了立刻绑定手机号的按钮
				$("#showBindPhoneModal").click(function () {
					$("#bindPhoneModal").modal("show");
                });
				
				// 监听"发送验证码"按钮的点击事件
				$("#sendVerifyCode").click(function () {
					var phoneNumber = $("#phoneNumber").val();
					var _this = $(this);
                    _this.attr("disabled",true);
					if (phoneNumber){
						$.ajax({
							type:"POST",
							dataType:"json",
							url:"/sendVerifyCode.do",
							data:{
                                phoneNumber:phoneNumber
							},
							success:function(data){
								if (data.success){
									var count = 5;
									var timer = window.setInterval(function () {
										count--;
										if (count<=0){
											// 定时器清空
											window.clearInterval(timer);
											// 倒计时结束,发送按钮可以使用
                                            _this.attr("disabled",false);
											// 重新设置显示的文字
                                            _this.text("重新发送验证码");
										}else{
											_this.text(count+"秒之后重新发送");
										}
                                    },1000);
								}else{
									// 显示错误信息
									$.messager.popup(data.msg);
									// 让发送按钮可以使用
                                    _this.attr("disabled",false);
								}
							}
						});
					}else {
                        // 显示提示信息
						$.messager.popup("请输入手机号");
                        // 让发送按钮可以使用
                        _this.attr("disabled",false);
					}
                });

				$("#bindPhone").click(function () {
					$("#bindPhoneForm").ajaxSubmit({
						success:function (data) {
							if (data.success){
								window.location.reload();
                                $.messager.popup(data.msg);
							}else{
								$.messager.popup(data.msg);
							}
                        }
					});
                });

				// 监听对"绑定邮箱"按钮的点击事件
				$("#showBindEmailModal").click(function () {
                    $("#bindEmailModal").modal("show");
                });

				$("#bindEmail").click(function () {
                    $("#bindEmailForm").ajaxSubmit({
                        success:function (data) {
                            if (data.success){
                            	window.location.reload();
                            }else{
                                $.messager.popup(data.msg);
                            }
                        }
                    });
                });
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫currentNav,变量的值叫personal -->
		<#assign currentNav="personal"/>
		<#include "common/navbar-tpl.ftl"  />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="personal"/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-body el-account">
							<div class="el-account-info">
								<div class="pull-left el-head-img">
									<img class="icon" src="/images/logo.png" style="width: 100px;height: 100px;"/>
								</div>
								<div class="pull-left el-head">
                                    <table style="width: 100%;height: 100px;">
                                        <tr>
											<td><span>账户名称：${loginInfo.userName}</span></td>
                                            <td><span>真实姓名： ${realAuth.realName}</span></td>
                                            <td><span>性别：${realAuth.sexDisplay}</span></td>
                                        </tr>
                                        <tr>
                                            <td><span>证件号码： ${realAuth.idNumber}</span></td>
                                            <td><span>出生日期：${(realAuth.bornDate?string('yyyy-MM-dd'))!''}</span></td>
											<td><span>所属医院：${loginInfo.userName}</span></td>
                                        </tr>
                                    </table>
									<p>
                                        <span>所属科室：${loginInfo.userName}</span>
                                        <span style="margin-left: 100px">医生职称：${loginInfo.userName}</span>
									</p>
									<p>
                                        <span>学位职称：${loginInfo.userName}</span>
                                        <span style="margin-left: 100px">行政职称：${loginInfo.userName}</span>
									</p>
                                    <p>
                                        <span>我的特长：${loginInfo.userName}</span>
                                    </p>
                                    <p>
                                        <span>我的简介：${loginInfo.userName}</span>
                                    </p>
									<p>上次登录时间：${loginInfo.lastLoginDateTime?string('yyyy-MM-dd HH:mm:ss')}</p>
								</div>
								<div class="clearfix"></div>
							</div>

							<div class="el-account-info top-margin">
								<div class="row">
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shiming.png" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>实名认证</h5>
												<#if userInfo.isRealAuth>
                                                    <p>
                                                        已认证
                                                        <a href="/realAuth.do" id="">查看</a>
                                                    </p>
												<#else>
													<p>
														未认证
														<a href="/realAuth.do" id="">立刻绑定</a>
													</p>

												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">实名认证之后才能在平台投资</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shouji.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>手机认证</h5>
												<#if userInfo.isBindPhone>
                                                    <p>
                                                        已认证
                                                        <a href="javascript:;" id="changeBindPhoneModal">修改手绑定的机号</a>
                                                    </p>
												<#else>
													<p>
														未认证
														<a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
													</p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">可以收到系统操作信息,并增加使用安全性</p>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/youxiang.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>邮箱认证</h5>
												<#if userInfo.isBindEmail>
													<p>
														已绑定
														<a href="javascript:;" id="changeBindEmailModal">解绑</a>
													</p>
												<#else>
                                                    <p>
                                                        未绑定
                                                        <a href="javascript:;" id="showBindEmailModal">去绑定</a>
                                                    </p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">您可以设置邮箱来接收重要信息</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<#if !userInfo.isBindPhone>
        <div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
                            <div class="form-group">
                                <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" />
                                    <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码</button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="verifyCode" name="verifyCode" />
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="bindPhone">保存</button>
                    </div>
                </div>
            </div>
        </div>
		</#if>


		<#if !userInfo.isBindEmail>
        <div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail.do">
                            <div class="form-group">
                                <label for="email" class="col-sm-2 control-label">Email:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="email" name="email" />
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="bindEmail">保存</button>
                    </div>
                </div>
            </div>
        </div>
		</#if>
	</body>
</html>