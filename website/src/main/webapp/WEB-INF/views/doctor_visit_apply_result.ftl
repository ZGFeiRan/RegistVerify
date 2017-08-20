<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />

		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="el-tip-info">
				<h3>温馨提示</h3>
				<#if !doctorInfo.isBasicInfo>
					<#assign currentNav="doctorInfo" />
                    <p>您目尚未完善个人信息，请先完善个人信息，谢谢！</p>
                    <p><a href="/fillDoctorBaseInfo.do">马上完善个人信息</a> </p>
				<#elseif !doctorInfo.isRealAuth>
					<#assign currentNav="realAuth" />
                    <p>您目尚未进行实名认证，请先进行实名认证，谢谢！</p>
                    <p><a href="/realAuth.do">马上实名认证</a> </p>
				<#else>
					<#if realAuth.state==0>
                        <p>您的实名认证正处于审核状态，我们会在一到两个工作日内审核完毕，请您耐心等待，谢谢！</p>
					<#elseif realAuth.state==2>
                        <p>您的实名认证审核未通过，您可以再次上传相关材料申请再次申请</p>
					</#if>
				</#if>
			</div>
		</div>	
	</body>
</html>