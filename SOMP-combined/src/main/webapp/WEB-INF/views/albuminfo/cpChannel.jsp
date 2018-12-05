<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>cp方频道管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/cpChannel.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
       		CP方：<input id="cpCodeSearch" name="cpCode" class="easyui-combobox" style="width: 90px; height: 25px">
        	频道code：<input id="chnCodeSearch" name="chnCode" class="easyui-textbox" style="width: 100px; height: 25px">
        	频道名称：<input id="chnNameSearch" name="chnName" class="easyui-textbox" style="width: 160px; height: 25px">
        	上下线状态：<input id="statusSearch" name="status" class="easyui-combobox" style="width: 90px; height: 25px">
        	绑定状态：<input id="joinStatusSearch" name="joinStatus" class="easyui-combobox" style="width: 90px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="bindingChannel">绑定频道</a>
			<a id="unpinless">清除绑定</a>
		</div>
	</div>
	<div id="addOrEditDialog"></div>
</body>
</html>