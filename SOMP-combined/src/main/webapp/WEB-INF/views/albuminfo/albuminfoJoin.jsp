<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>节目管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/albuminfoJoin.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
       		CP源：<input id="cpCodeSearch" name="cpCode" class="easyui-combobox" style="width: 90px; height: 25px">
        	节目code：<input id="seriesCodeSearch" name="seriesCode" class="easyui-textbox" style="width: 100px; height: 25px">
        	节目名称：<input id="seriesNameSearch" name="seriesName" class="easyui-textbox" style="width: 160px; height: 25px">
        	是否确认关联：<input id="statusSearch" name="status" class="easyui-combobox" style="width: 80px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="confirmed">批量确认关联</a>
			<a id="unconfirmed">批量取消确认关联</a>
			<a id="remove">删除</a>
			<a id="manual">手动选择关联</a>
		</div>
	</div>
	<div id="addOrEditDialog"></div>
</body>
</html>