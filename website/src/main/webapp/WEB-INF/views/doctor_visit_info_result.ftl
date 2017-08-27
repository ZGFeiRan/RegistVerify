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
        <!--导航菜单-->
        <div class="col-sm-3">
			<#assign currentMenu="doctorVisit" />
			<#include "common/leftmenu-tpl.ftl" />
        </div>
		<div class="container">
			<div class="el-tip-info">
				<h3>温馨提示</h3>
					<p>您还未发布过出诊信息！！</p>
			</div>
		</div>	
	</body>
</html>