<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>名医平台</title>
		<#include "common/links-tpl.ftl" />
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav = "index" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<!-- banner广告部分 start -->
		<div class="container-fuil">
			<div id="carousel-banner-generi" class="carousel slide el-banner" data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carousel-banner-generi" data-slide-to="0" class="active"></li>
					<li data-target="#carousel-banner-generi" data-slide-to="1"></li>
				</ol>
				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="/images/banner01.jpg" alt="...">
						<div class="carousel-caption"></div>
					</div>
					<div class="item">
						<img src="/images/banner02.jpg" alt="...">
						<div class="carousel-caption"></div>
					</div>
				</div>
				<!-- Controls -->
				<a class="left carousel-control" href="#carousel-banner-generi" role="button" data-slide="prev"> <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#carousel-banner-generi" role="button" data-slide="next"> <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<!-- banner广告部分 end -->
	
	</body>
</html>