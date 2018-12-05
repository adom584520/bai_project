<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>MAC管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/pb/mac.js"></script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="padding:0px;height: 160px;">
		<div align="center" style=";height: 120px;">
			<form id="groupAddOrEditFrom">
		   		<input id="groupId" name="id" value="${group.id}" type="hidden"/>
				<table>
					<tr>
						<td height="32px" align="left">
							分组名称：<input id="group_name" name="groupName" class="easyui-textbox" style="width: 140px;height:25px" value="${group.groupName}">
						</td>
						<td height="32px" align="right" >
							联系人：<input id="contacts" name="contacts" class="easyui-textbox" style="width: 140px;height:25px" value="${group.contacts}">
						</td>
					</tr>
					<tr>
						<td height="32px" align="left">
							联系号码：<input id="contacts_number" name="contactsNumber" class="easyui-textbox" style="width: 140px;height:25px" value="${group.contactsNumber}">
						</td>
						<td height="32px" align="right">
							联系地址：<input id="address" name="address" class="easyui-textbox" style="width: 140px;height:25px" value="${group.address}">
						</td>
					</tr>
				</table>
			</form>
			<div align="center">
				<a id="groupDialogSave">保存</a>
				<a id="groupDialogClose">关闭</a>
			</div>
		</div>
		<div style="height: 25px;">
		<hr>
		   	<span class="property" style=" margin-left: 10px">
		    	MAC名称：<input id="macNameSearch" name="macName" style="width:120px;height: 23px"/>
		   	</span>
			<a id="conditionSearch">搜索</a>
			<a id="reload">刷新</a>
		</div>
	</div>
	<div data-options="region:'center',border:false" style="padding:0px;">
		<table id="datagrid"></table>
		<div id="toolbar">
			<a id="add">新增</a>
			<a id="batchImport">批量导入</a>
			<a href="#" id="batchExport">全部导出</a>
			<a id="remove">删除</a>
		</div>
	</div>
	<div id="macAddOrEditDialog">
		<form id="macAOrEditFrom" method="post">
			<input id="macGroupId" name="groupId" value="${group.id}" type="hidden"/>
			<div align="center" style="padding-top:10px">
				MAC名称：<input id="mac_name" name="macName" class="easyui-textbox" style="width: 160px;height:25px">
			</div>
		</form>
		<div id="macAddOrEditDialogButtons" align="center" style="padding-top:20px">
			<a id="macDialogSave">保存</a>
			<a id="macDialogClose">关闭</a>
		</div>
	</div>
	<div id="macBatchImportDialog">
		<form id="macBatchImportFrom" method="post" enctype="multipart/form-data">
			<input id="macGroupId" name="groupId" value="${group.id}" type="hidden"/>
			<div align="center" style="padding-top:10px">
				<input id="fileName" name="file" class="easyui-filebox" style="width: 250px;height:25px">
			</div>
		</form>
		<div id="macBatchImportDialogButtons" align="center" style="padding-top:20px">
			<a id="macBatchImportUpload">上传</a>
			<a id="macBatchImportClose">取消</a>
		</div>
	</div>
</body>
</html>