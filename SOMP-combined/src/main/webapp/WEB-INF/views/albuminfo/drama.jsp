<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>己方剧集管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/drama.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="padding:0px;">
		<input id="seriesCode" name="seriesCode" value="${seriesCode}" type="hidden"/>
		<input id="cpDramaId" name="cpDramaId" value="${cpDramaId}" type="hidden"/>
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="confirm">确认</a>
			<a id="cancel">取消</a>
		</div>
	</div>
</body>
</html>