<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>APK版本升级管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/launcher/upgrade/upgrade.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
       		APK类型：<input id="typeSearch" name="typeSearch" class="easyui-combobox" style="width: 140px;height:25px">
       	</span>
		<a id="conditionSearch">搜索</a>
		<a id="reload">刷新</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="add">新增</a>
			<a id="edit">编辑</a>
			<a id="remove">删除</a>
		</div>
	</div>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="id" type="hidden" name="id" />
			<div align="center" style="padding-top:10px">
				<table>
					<tr style="height: 30px">
						<td>
							版本名称：<input id="gradeName" name="gradeName" class="easyui-textbox" style="width: 160px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							下载链接：<input id="download" name="download" class="easyui-textbox" style="width: 180px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							APK类型：<input id="type" name="type" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							版本号 ：<input id="version" name="version" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							版本描述：<input id="versionInfo" name="versionInfo" class="easyui-textbox" style="width: 200px;height:80px">
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div id="addOrEditDialogButtons" align="center">
			<a id="dialogSave">保存</a>
			<a id="dialogClose">取消</a>
		</div>
	</div>
</body>
</html>