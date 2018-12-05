<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>专辑管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/albuminfo.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
       		专辑code：<input id="seriesCodeSearch" name="seriesCode" class="easyui-textbox" style="width: 120px; height: 25px">
        	专辑名称：<input id="seriesNameSearch" name="seriesName" class="easyui-textbox" style="width: 140px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<input id="cpAlbuminfoId" name="cpAlbuminfoId" value="${cpAlbuminfoId}" type="hidden"/>
		<input id="cpCode" name="cpCode" value="${cpCode}" type="hidden"/>
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="confirm">确认</a>
			<a id="cancel">取消</a>
		</div>
	</div>
</body>
</html>