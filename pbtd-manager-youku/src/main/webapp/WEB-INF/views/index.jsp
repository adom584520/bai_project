<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>分平台_优酷管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/index.js"></script>
<style type="text/css">
.user_font {
	margin-left: auto;
	margin-right: auto;
	max-width: 200px;
	padding: 20px 20px 0px 0px;
	font: 15px Arial, Helvetica, sans-serif;
	color: #666;
}
</style>
</head>
<body>
	<div class="easyui-layout" style="width: 700px; height: 350px;"
		data-options="fit:'true'">
		<div data-options="region:'north'"
			style="height: 60px; background: url('/image/index/pbtd_top.png') no-repeat">
			<div align="right" style="padding-right: 50px;">
				<span>
					<font id="user_font" class="user_font">欢迎,${loginInfo.username}</font>
				</span>
				<br>
				<a id="logininfo_edit" class="easyui-menubutton">账号设置</a>
			</div>
			<div id="logininfo_edit_1" style="width:60px;">
				<div onClick="openSelfInfoDialog()">账号信息</div>
				<div onClick="openEditPasswordDialog()">修改密码</div>
				<div onclick="location='/log_out'"> 注销登录</div>
			</div>
		</div>
		<div data-options="region:'west',split:true" style="width: 200px;">
			<div class="easyui-accordion" style="width: 500px; height: 300px;"
				fit='true'>
				<div title="菜单"
					style="background: url('/image/index/pbtd_menu.png') no-repeat; background-size: 100% 100%;"
					data-options="iconCls:'icon-menu'">
					<ul id="tree"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'"
			style="background: url('system.jpg') no-repeat; background-size: cover;">
			<div id="tabs" class="easyui-tabs" fit=true>
				<div title="主页">
					<div style="margin-top: 10px">
						<div style="width: 100%; float: center;">
							<iframe allowtransparency="true" frameborder="0"
								style="float: center;" width="100%" height="100" scrolling="no"
								src="//tianqi.2345.com/plugin/widget/index.htm?s=2&z=1&t=1&v=0&d=2&bd=0&k=&f=ff0000&q=1&e=1&a=1&c=72025&w=410&h=98&align=center"></iframe>
							<div class="easyui-calendar" flt="true" align="center"
								style="width: 100%; height: 90%"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="selfInfoDialog">
		<input id="usernameCache" value="${loginInfo.username}" type="hidden">
		<input id="realNameCache" value="${loginInfo.realName}" type="hidden">
		<input id="contactInformationCache" value="${loginInfo.contactInformation}" type="hidden">
		<form id="selfInfoForm" method="post">
			<input name="id" value="${loginInfo.id}" type="hidden">
			<table style="padding-top: 10px;padding-left: 10px">
				<tr>
					<td>
						账号名称：<input id="username" name="username" class="easyui-textbox" style="width: 80px;height:25px">
					</td>
				</tr>
				<tr>
					<td>
						真实姓名：<input id="realName" name="realName" class="easyui-textbox" style="width: 80px;height:25px">
					</td>
				</tr>
				<tr>
					<td>
						联系方式：<input id="contactInformation" name="contactInformation" class="easyui-textbox" style="width: 140px;height:25px">
					</td>
				</tr>
			</table>
		</form>
		<div id="selfInfoDialogButtons">
			<a id="selfInfoDialogSave">保存</a>
			<a id="selfInfoDialogCancel">取消</a>
		</div>
	</div>
	<div id="editPasswordDialog">
		<form id="editPasswordForm" method="post">
			<table style="padding-top: 10px;text-align:center">
				<tr>
					<td>
						旧密码：
					</td>
					<td>
						<input id="oldPassword" name="oldPassword" class="easyui-passwordbox" style="width: 140px;height:25px">
					</td>
				</tr>
				<tr>
					<td>
						新密码：
					</td>
					<td>
						<input id="newPassword" name="newPassword" class="easyui-passwordbox" style="width: 140px;height:25px">
					</td>
				</tr>
				<tr>
					<td>
						确认密码：
					</td>
					<td>
						<input id="confirmPassword" name="confirmPassword" class="easyui-passwordbox" style="width: 140px;height:25px">
					</td>
				</tr>
			</table>
		</form>
		<div id="editPasswordDialogButtons">
			<a id="editPasswordDialogSave">保存</a>
			<a id="editPasswordDialogCancel">取消</a>
		</div>
	</div>
</body>
</html>