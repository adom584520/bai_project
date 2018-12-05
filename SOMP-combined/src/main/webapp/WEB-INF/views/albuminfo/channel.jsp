<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>己方頻道管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/channel.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
       		頻道code：<input id="chnCodeSearch" name="chnCode" class="easyui-textbox" style="width: 120px; height: 25px">
        	頻道名称：<input id="chnNameSearch" name="chnName" class="easyui-textbox" style="width: 140px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<input id="cpChannelId" name="cpChannelId" value="${cpChannelId}" type="hidden"/>
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="confirm">确认</a>
			<a id="cancel">取消</a>
		</div>
	</div>
</body>
</html>