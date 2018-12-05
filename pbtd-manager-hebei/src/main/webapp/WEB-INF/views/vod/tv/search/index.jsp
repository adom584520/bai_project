<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>TV搜索管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	//呈现顶端遮罩
	if (top.showMask)
		top.showMask();
</script>
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 60px;
}
</style>
</head>
<body>

	<div style="margin: 20px 0">
		<h1>下面是专辑搜索操作:</h1>
	</div>
	<div>
		<a href="${pageContext.request.contextPath}/vod/tv/search/Index/indexImport"
			onclick="return confirm('确定导入吗！')" class="Index">
			<input type="button" value="导入专辑数据到索引库"></a> 
		<a href="${pageContext.request.contextPath}/vod/tv/search/Delete/deleteIndex"
			onclick="return confirm('确定清空吗！')" class="delete">
			<input type="button" value="清空专辑索引"></a>
		<a href="${pageContext.request.contextPath}/vod/tv/search/Optimize/IndexOptimize"
			onclick="return confirm('确定优化 吗！')" class="delete">
			<input type="button" value="优化专辑索引"></a>
	</div>
	<br/><hr/>
	<%-- <div style="margin: 20px 0">
		<h1>下面是搜索提示操作:</h1>
	</div>
	<div>
		<a href="${pageContext.request.contextPath}/vod/tv/search/Index/titleIndexImport"
			onclick="return confirm('确定导入吗！')" class="Index">
			<input type="button" value="导入专辑标题到索引库"></a> 
		<a href="${pageContext.request.contextPath}/vod/tv/search/Delete/deleteTitleIndex"
			onclick="return confirm('确定清空吗！')" class="delete">
			<input type="button" value="清空标题索引"></a>
		<a href="${pageContext.request.contextPath}/vod/tv/search/Optimize/titleIndexOptimize"
			onclick="return confirm('确定优化 吗！')" class="delete">
			<input type="button" value="优化标题索引"></a>
	</div> --%>
	
	<div style="margin: 20px 0">
		<h1>下面是相关推荐缓存管理:</h1>
	</div>
	<div>
		<a href="${pageContext.request.contextPath}/vod/tv/search/redis/tvDelRedis"
			onclick="return confirm('确定清空吗！')" class="delete">
			<input type="button" value="清空相关推荐缓存"></a>
	</div>
	<script type="text/javascript">
	 $(function() {
			var flag = ${flag};
			if ( flag == 1) {
				//成功
				alert("发送索引导入命令成功！", "提醒！");
			}
			if (flag == 0) {
				//失败
				alert("发送索引导入命令失败！", "提醒！");
			}
		}); 
	</script>

</body>
</html>
