<div class="navbar navbar-default el-navbar">
	<div class="container">
		<div class="navbar-header">
		</div>
		<ul class="nav navbar-nav">
			<#--<li id="index"><a href="/index.do">首页</a></li>-->
			<li id="personal"><a href="/personal.do">个人中心</a></li>
			<li><a href="/newDoctor.do">新手指引</a></li>
			<li><a href="/aboutUs.do:;">关于我们</a></li>
		</ul>
	</div>
</div>

<#if currentNav??>
<script type="text/javascript">
	$("#"+"${currentNav}").addClass("active");
</script>
</#if>