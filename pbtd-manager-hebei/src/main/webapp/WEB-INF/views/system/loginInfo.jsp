<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/loginInfo/loginInfo.js"></script>
<link rel="stylesheet" type="text/css" href="/buttons/buttons.css">
<style type="text/css">
</style>
</head>
<body>
	<table id="datagrid"></table>
	<div id="addOrEditDialog">
		<form id="addOrEditFrom" method="post">
		<input id="loginInfo_id" type="hidden" name="id"/>
		<div align="center">
			<table>
				<tr style="height: 30px">
					<td>账号名称：</td>
					<td>
						<input id="username" name="username" class="easyui-textbox" style="width: 140px;height:25px">
					</td>
				</tr>
				<tr style="height: 30px">
					<td>员工姓名：</td>
					<td >
						<input id="realName" name="realName" class="easyui-textbox" style="width: 140px;height:25px"> 
					</td>
				</tr>
				<tr style="height: 30px">
					<td>联系方式：</td>
					<td>
						<input id="contactInformation" name="contactInformation" class="easyui-textbox" style="width: 140px;height:25px"> 
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
	<div id="assignDialog">
		<form id="assignDialogFrom" method="post">
		<input id="assignLoginInfoId" type="hidden" name="loginInfoId"/>
		<table>
		<tr>
			<td align="left">
				未拥有角色：<select id="allRole" style="width:120px;height:200px" size="10" multiple="multiple"></select>
			</td>
			<td align="center">
				<a id="right_linkbutton" class="easyui-linkbutton" 
					data-options="iconCls:'icon-right'" onclick="javascript:exchangeRole('allRole','selfRole')">赋予选中角色</a>
				<a id="left_linkbutton" class="easyui-linkbutton" 
					data-options="iconCls:'icon-left'" onclick="javascript:exchangeRole('selfRole','allRole')">收回选中角色</a><br/>
				<a id="right_all_linkbutton" class="easyui-linkbutton" 
					data-options="iconCls:'icon-right-all'" onclick="javascript:exchangeRole('allRole','selfRole',true)">赋予所有角色</a><br/>
				<a id="left_all_linkbutton" class="easyui-linkbutton" 
					data-options="iconCls:'icon-left-all'" onclick="javascript:exchangeRole('selfRole','allRole',true)">收回所有角色</a>
			</td>
			<td align="right">
				拥有角色：<select id="selfRole" style="width:120px;height:200px" size="10" multiple="multiple"></select>
			</td>
		</tr>
		</table>
		</form>
	</div> 
</body>
</html>