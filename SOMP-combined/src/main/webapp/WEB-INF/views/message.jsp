<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>消息管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/albuminfo/cpInfo.js"></script>
</head>
<body>
	<table id="datagrid"></table>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
			<div align="center">
				<table>
					<tr>
						<td height="28px">
							key值：<input id="code" name="code" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="28px">
							value值：<input id="name" name="name" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="28px">
							版权状态：<input id="status" name="status" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>