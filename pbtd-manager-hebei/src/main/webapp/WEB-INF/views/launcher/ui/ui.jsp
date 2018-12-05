<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>界面UI管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/launcher/ui/ui.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
        	分组：<input id="groupIdSearch" name="groupId" class="easyui-combobox" style="width: 140px; height: 25px">
       	</span>
		<a id="conditionSearch">搜索</a>
		<a id="reload">刷新</a>
    </div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
	</div>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="ui_id" type="hidden" name="id" />
			<input id="group_id" type="hidden">
			<input id="ui_status" type="hidden">
			<div align="center" style="padding-top:10px">
				<table>
					<tr style="height: 30px">
						<td>
							选择分组：<input id="groupId" name="groupId" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							LOGO：<input id="logo" name="logo" class="easyui-textbox" style="width: 160px;height:25px">
							<a data-myid="logo" class="easyui-linkbutton" onclick="fileuploadOpen(this)">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							背景海报：<input id="posterBackground" name="posterBackground" class="easyui-textbox" style="width: 160px;height:25px">
							<a data-myid="posterBackground" class="easyui-linkbutton" onclick="fileuploadOpen(this)">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							选中框：<input id="pickerView" name="pickerView" class="easyui-textbox" style="width: 140px;height:25px">
							<a data-myid="pickerView" class="easyui-linkbutton" onclick="fileuploadOpen(this)">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							看点背景：<input id="watchBackground" name="watchBackground" class="easyui-textbox" style="width: 140px;height:25px">
							<a data-myid="watchBackground" class="easyui-linkbutton" onclick="fileuploadOpen(this)">重新上传</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id="fileuploadDialog"></div>
</body>
</html>