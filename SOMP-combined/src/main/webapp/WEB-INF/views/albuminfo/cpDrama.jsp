<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>cp剧集管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/cpDrama.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="padding:0px;">
		<input id="cpCode" name="cpCode" value="${cpCode}" type="hidden"/>
		<input id="cpSeriesCode" name="cpSeriesCode" value="${cpSeriesCode}" type="hidden"/>
		<input id="seriesCode" name="seriesCode" value="${seriesCode}" type="hidden"/>
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="bindingDrama">选择绑定剧集</a>
			<a id="confirm">确认绑定关系</a>
		</div>
	</div>
	<div id="addOrEditDialog"></div>
</body>
</html>