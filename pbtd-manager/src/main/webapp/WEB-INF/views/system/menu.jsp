<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>菜单管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/menu/menu.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<table id="treegrid"></table>
	<div id="toolbar">
		<a id="add">新增</a>
		<a id="edit">编辑</a>
		<a id="remove">删除</a>
	</div>

	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="menu_id" type="hidden" name="id" />
			<input id="parentId_hidden" type="hidden"/>
			<div align="center">
				<table>
					<tr>
						<td height="30px" id="parentTd">
							菜单父节点：<input id="parentId" name="parentId">
						</td>
					</tr>
					<tr>
						<td height="30px">
							菜单名称：<input id="text" name="text" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							菜单页面地址：<input id="url" name="url" class="easyui-textbox" style="width: 160px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							是否展开：<input id="state" name="state" class="easyui-combobox textbox" style="width: 100px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							菜单图标：<input id="iconCls" name="iconCls" class="easyui-combobox textbox" style="width: 150px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							菜单顺序：<input id="sort" name="sort" class="easyui-textbox" style="width: 50px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							菜单权限表达式：<input id="permission" name="permission" style="width: 160px;height:25px">
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div id="addOrEditDialogButtons" align="center">
			<a id="dialogSave">保存</a> <a id="dialogClose">关闭</a>
		</div>
	</div>
</body>
</html>