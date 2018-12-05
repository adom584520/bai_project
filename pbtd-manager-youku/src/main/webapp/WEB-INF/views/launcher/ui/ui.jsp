<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>系统UI管理</title>
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
		<div id="toolbar">
			<a id="add">新增</a>
			<a id="edit">编辑</a>
			<a id="remove">删除</a>
		</div>
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
							<a class="fileuploadOpen" data-myid="logo">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							背景海报：<input id="posterBackground" name="posterBackground" class="easyui-textbox" style="width: 160px;height:25px">
							<a class="fileuploadOpen" data-myid="posterBackground">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							选中框：<input id="pickerView" name="pickerView" class="easyui-textbox" style="width: 140px;height:25px">
							<a class="fileuploadOpen" data-myid="pickerView">重新上传</a>
						</td>
					</tr>
					<tr style="height: 30px">
						<td>
							看点背景：<input id="watchBackground" name="watchBackground" class="easyui-textbox" style="width: 140px;height:25px">
							<a class="fileuploadOpen" data-myid="watchBackground">重新上传</a>
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
	<div id="fileuploadDialog">
		<form id="uploadImageForm" method="post" enctype="multipart/form-data">
			<div style="padding-left: 10px;padding-right: 10px;padding-top: 10px">
				<input id="fileupload" name="file" type="text" style="width:270px;height: 25px;padding-top: 10px;padding-left: 20px">
				<label id="fileSize"></label><br>
				<label style="color: red">只支持png、jpg格式,大小不超过1MB</label>
				<div style="padding-top: 10px">
					<div style="width:200px; height:auto; float:left; display:inline">
						<span>预览图：</span><img id="preview" src="" style="width: 180px;height: 180px">
					</div>
					<div style="width:200px; height:auto; float:right; display:inline">
						<span>上传(原)图：</span><img id="echo" src="" style="width: 180px;height: 180px;">
					</div>
				</div>
			</div>
		</form>
		<div id="fileuploadDialogButtons" align="center">
			<a id="fileuploadDialogSave">上传</a>
			<a id="fileuploadDialogClose">返回</a>
		</div>
	</div>
</body>
</html>