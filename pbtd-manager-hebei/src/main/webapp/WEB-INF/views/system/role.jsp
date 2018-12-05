<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>角色管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/role/role.js"></script>
<style type="text/css">
.myCss {
	margin: 0px 30px 0px 0px;
	padding-top: 0px;
	padding-bottom: 0px;
	line-height: 22px;
	width: 139px;
}

.l-btn {
	text-decoration: none;
	display: inline-block;
	overflow: hidden;
	margin: 0;
	padding: 0px 5px;
	cursor: pointer;
	outline: none;
	text-align: center;
	vertical-align: middle;
}
</style>
</head>
<body>
	<table id="datagrid"></table>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<input id="role_id" type="hidden" name="id" />
			<div align="center">
				<table>
					<tr>
						<td height="50px">
							角色名称：<input id="name" name="name" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="50px">
							角色描述：<br>
							<input id="descriptor" name="descriptor" class="easyui-textbox" style="width: 250px;height:100px">
						</td>
					</tr>
					<tr>
						<td height="50px">
							菜单列表：<br>
							<ul id="menuTree" style="align: center"></ul>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>