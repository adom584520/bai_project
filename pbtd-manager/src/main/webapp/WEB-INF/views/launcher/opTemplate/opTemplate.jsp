<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>运营位模板管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/launcher/opTemplate/opTemplate.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,split:true" style="padding:10px; border-bottom:0px solid #99BBE8; height:50px; width:auto;">
       	<span class="property">
        	分组-导航：<input id="groupIdSearch" name="groupId" class="easyui-combobox" style="width: 140px; height: 25px">
        	<font>-</font>
        	<input id="navIdSearch" name="navId" class="easyui-combobox" style="width: 140px; height: 25px;padding-left: 2px">
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
			<a id="copy">数据拷贝</a>
		</div>
	</div>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="temp_id" type="hidden" name="id" />
			<input id="nav_id" type="hidden"/>
			<input id="group_id" type="hidden"/>
			<input id="isCoyp" type="hidden"/>
			<div align="center" style="padding-top:10px">
				<table>
					<tr style="height: 30px">
						<td colspan="2">
							<input id="groupId" name="groupId" class="easyui-combobox" style="width: 140px;height:25px">
							<font>-</font>
							<input id="navId" name="navId" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr style="height: 30px">
						<td colspan="2">
							运营位模板名称：<input id="tempName" name="tempName" class="easyui-textbox" style="width: 120px;height:25px">
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