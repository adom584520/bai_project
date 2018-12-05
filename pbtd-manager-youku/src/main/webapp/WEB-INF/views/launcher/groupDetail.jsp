<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>分组开机信息管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/launcher/groupDetail/groupDetail.js"></script>
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
			<input id="group_detail_id" type="hidden" name="id" />
			<input id="group_id" type="hidden">
			<div align="center">
				<table>
					<tr>
						<td height="30px">
							选择分组：<input id="groupId" name="groupId" class="easyui-combobox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							LOGO URL：<input id="logoUrl" name="logoUrl" class="easyui-textbox" style="width: 160px;height:25px">
							<a name="logoUrl" class="fileuploadOpen">重新上传</a>
						</td>
					</tr>
					<tr>
						<td height="30px">
							背景图 URL：<input id="backgroundUrl" name="backgroundUrl" class="easyui-textbox" style="width: 160px;height:25px">
							<a name="backgroundUrl" class="fileuploadOpen">重新上传</a>
						</td>
					</tr>
					<tr>
						<td height="30px">
							视频 URL：<input id="videoUrl" name="videoUrl" class="easyui-textbox" style="width: 140px;height:25px">
						</td>
					</tr>
					<tr>
						<td height="30px">
							开机倒计时：<input id="countDown" name="countDown" class="easyui-textbox" style="width: 50px;height:25px">秒
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
			<div align="center"><br>
				<div>
					<input id="fileupload" name="file" type="text" style="width:200px">
					<label id="fileSize"></label>
				</div><br>
				<div>
					<div style="width:190px; height:auto; float:left; display:inline">
						<span>预览图：</span><img id="preview" src="" style="width: 170px;height: 180px">
					</div>
					<div style="width:190px; height:auto; float:right; display:inline">
						<span>上传(原)图：</span><img id="echo" src="" style="width: 170px;height: 180px">
					</div>
				</div>
			</div><br>
		</form>
		<div id="fileuploadDialogButtons" align="center">
			<a id="fileuploadDialogSave">上传</a>
			<a id="fileuploadDialogClose">返回</a>
		</div>
	</div>
	<div id="slideshowDialog">
		<div>
			<a id="slideshowAdd">添加</a>
			<a class="fileuploadOpen" name="imageUrlReceive">重新上传</a>
		</div>
		<div>
			图片Url：<input id="imageUrlReceive" class="easyui-textbox" style="width: 140px;height:25px">
		</div>
		<input id="group_detail_slideshow_id" type="hidden"/>
		<table id="slideshowTable">
			<tr id="slideshowTr" style="width: 482;height: 28">
				<td>
					轮播图URL：<input id="slideshows" class="easyui-validatebox textbox" style="width: 160px;height:25px">
					<a id="slideshowsRemove" class="easyui-linkbutton" data-options="plain:false" onclick="slideshowsRemove(this)">删除</a>
					<a id="slideshowsUp" class="easyui-linkbutton" data-options="plain:false" onclick="slideshowsUp(this)">上移</a>
					<a id="slideshowsDown" class="easyui-linkbutton" data-options="plain:false" onclick="slideshowsDown(this)">下移</a>
				</td>
			</tr>
		</table>
		<div id="slideshowDialogButtons" align="center">
			<a id="slideshowDialogSave">保存</a>
			<a id="slideshowDialogClose">返回</a>
		</div>
	</div>
</body>
</html>