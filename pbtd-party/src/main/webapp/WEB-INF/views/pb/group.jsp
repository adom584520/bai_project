<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>launcher分组管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/pb/group.js"></script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	 <div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
        	<span class="property">
	        	分组名称：<input id="groupNameSearch" name="groupName" style="width:120px;height: 23px"/>
	        	MAC名称：<input id="macNameSearch" name="macName" style="width:120px;height: 23px"/>
        	</span>
			<a id="conditionSearch">搜索</a>
			<a id="reload">刷新</a>
        </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="add">新增</a>
			<a id="remove">删除</a>
		</div>
	</div>

	<div id="macDialog"></div>
</body>
</html>