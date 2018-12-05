<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>日志管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/logger/logger.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
        	操作账号：<input id="loginInfoNameSearch" name="loginInfoName" class="easyui-textbox" style="width: 140px; height: 25px">
        	操作信息：<input id="operationInfoSearch" name="operationInfo" class="easyui-textbox" style="width: 140px; height: 25px;padding-left: 5px">
        	开始时间：<input id="startTimeSearch" name="startTime" class="easyui-datebox" style="width: 140px; height: 25px;padding-left: 5px;padding-right: 5px">
        	结束时间：<input id="endTimeSearch" name="endTime" class="easyui-datebox" style="width: 140px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
		<a id="reload">刷新</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<!-- 后台有相应的代码实现 -->
			<!-- <a id="remove">删除</a>
			<a id="conditionRemove">按条件删除</a> -->
		</div>
	</div>
	<div id="conditionRemoveDialog">
		<div style="padding-top: 10px;padding-left: 5px;">
			按日期删除：
			<input id="startTime" class="easyui-datebox" style="width: 140px; height: 25px;padding-left: 5px;padding-top: 10px">
			<input id="endTime" class="easyui-datebox" style="width: 140px; height: 25px;">
		</div>
		<div id="addOrEditDialogButtons">
			<a id="conditionRemoveDialogSave">确认</a>
			<a id="conditionRemoveDialogClose">取消</a>
		</div>
	</div>
	<div id="infoDialog">
		<table id="infoPropertygrid" style="width:300px;padding-top: 10px"></table>
	</div>
</body>
</html>