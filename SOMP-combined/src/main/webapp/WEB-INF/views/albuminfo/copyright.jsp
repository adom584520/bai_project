<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>版权控制管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/copyright.js"></script>
</head>
<body>
	<table id="datagrid"></table>
	<div id="toolbar">
		<a id="add">新增</a>
		<a id="edit">编辑</a>
		<a id="remove">删除</a>
	</div>

	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="id" type="hidden" name="id" />
			<div align="center">
				<table>
					<tr>
						<td height="28px">
							cpCode：<input id="cpCode" name="cpCode" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="28px">
							类型：<input id="type" name="type" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="28px">
							是否可用：<input id="status" name="status" class="easyui-combobox" style="width: 140px;height:25px">
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