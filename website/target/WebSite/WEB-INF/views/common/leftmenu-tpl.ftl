<ul id="menu" class="list-group">
	<li class="list-group-item">
		<a href="javascript:;"><span class="text-title">我的信息</span></a>
		<ul class="in">
			<li name="personal"><a href="/personal.do">个人信息</a></li>
			<li name="realAuth"><a href="/realAuth.do">实名认证</a></li>
			<li name="doctorInfo"><a href="/fillDoctorBaseInfo.do">完善个人信息</a></li>
			<li name="doctorVisit"><a href="/doctorVisitApply.do">发布坐诊</a></li>
			<li name="visitInfoHistory"><a href="/visitInfoHistory.do">坐诊记录</a></li>
			<li name="ipLog"><a href="/ipLog.do">登录记录</a></li>
			<#--<li name="userInfo"><a href="/basicInfo.do"> <span>个人资料</span></a></li>-->
		</ul>
	</li>
</ul>

<#if currentMenu??>
<script type="text/javascript">
	$(".list-group-item li[name=${currentMenu}]").addClass("active");
</script>
</#if>