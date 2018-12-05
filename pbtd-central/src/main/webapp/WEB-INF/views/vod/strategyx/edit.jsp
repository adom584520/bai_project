<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--
        *  新建页面
        * 
        * @author admin
        *
-->
<!DOCTYPE html>
<html>
<head>
<title>编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/bootstrapSwitch.css"></link>
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">

<script type="text/javascript"
	src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
<script type="text/javascript"
	src="/js/common/scripts/jquery-domain.min.js"></script>
<style type="text/css">
td.tdCaption {
	text-align: center;
}

td.tdCaption span {
	color: #ff0000;
}

select, textarea, input[type="text"], input[type="password"], input[type="datetime"],
	input[type="datetime-local"], input[type="date"], input[type="month"],
	input[type="time"], input[type="week"], input[type="number"], input[type="email"],
	input[type="url"], input[type="search"], input[type="tel"], input[type="color"],
	.uneditable-input {
	display: inline-block;
	height: 20px;
	padding: 0px;
	margin-bottom: 2px;
	margin-top: 2px;
	font-size: 14px;
	line-height: 20px;
	color: #555555;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	vertical-align: middle;
}

a.l-btn span span.l-btn-text {
	width: 35px;
}

.tdContent input {
	width: 100px;
}
</style>
</head>
<body class="easyui-layout" style="visibility: hidden">

	<div data-options="region:'center',border:false" style="padding: 0px;">
		<form id="strategy"
			style="margin: 10px; padding: 0; visibility: hidden" method="post"
			autoTypeset="false" columnSize="2">
			<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain"
				align="center">
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">名称</td>
					<td class="tdContent" colspan="3"><input type="text" id="name" name="name"
						class="input text easyui-validatebox-disable" style=""
						data-options="validType:'text[1,50]'" /></td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">优先级</td>
					<td class="tdContent"  colspan="3">
					<select id="priority" name="priority" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="1">高级</option>
								<option value="2" selected="selected">中级</option>
								<option value="3">低级</option>
						</select> 
						</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段id</td>
					<td class="tdContent">
					<select id="field1" name="field1"   style="width: 120px;" onchange="changename('field1','fieldName1')" class="input text easyui-validatebox-disable">
							<option value="">--请选择--</option>
							<c:forEach items="${ablumsmap }" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
					</select></td>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段名称</td>
					<td class="tdContent">
					<input type="text" id="fieldName1" name="fieldName1" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,15]'" /></td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段id</td>
					<td class="tdContent">
					<select id="field2" name="field2"   style="width: 120px;"
					 onchange="changename('field2','fieldName2')" class="input text easyui-validatebox-disable">
							<option  value="">--请选择--</option>
							<c:forEach items="${ablumsmap }" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
					</select></td>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段名称</td>
					<td class="tdContent"><input type="text" id="fieldName2"
						name="fieldName2" class="input text easyui-validatebox-disable"
						style="" data-options="validType:'text[1,15]'" /></td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段id</td>
					<td class="tdContent">
					<select id="field3" name="field3"   style="width: 120px;"
					 onchange="changename('field3','fieldName3')" class="input text easyui-validatebox-disable">
							<option  value="">--请选择--</option>
							<c:forEach items="${ablumsmap }" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
					</select></td>
				
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段名称</td>
					<td class="tdContent"><input type="text" id="fieldName3"
						name="fieldName3" class="input text easyui-validatebox-disable"
						style="" data-options="validType:'text[1,15]'" /></td>
					</tr>
				<tr>
				<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段id</td>
					<td class="tdContent">
				<select id="field4" name="field4"   style="width: 120px;"
					 onchange="changename('field4','fieldName4')" class="input text easyui-validatebox-disable">
							<option  value="">--请选择--</option>
							<c:forEach items="${ablumsmap }" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
					</select></td>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">字段名称</td>
					<td class="tdContent"><input type="text" id="fieldName4"
						name="fieldName4" class="input text easyui-validatebox-disable"
						style="" data-options="validType:'text[1,15]'" /></td>
				</tr>
				<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
						align="center">cp源优先级</td>
					<td class="tdContent"> 
						<select id="cpcode" name="cpcode" style="width: 90px;" class="input text easyui-validatebox-disable">
							<c:forEach items="${cpsource}" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
						</select>
						</td>
					<td style="text-align: right; width: 15%" class="tdCaption" align="center">
					状态</td>
					<td class="tdContent"> 
						<select id="status" name="status" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="1">启用</option>
								<option value="0" selected="selected">禁用</option>
						</select>
						</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false"
		style="height: 35px; text-align: right; padding: 5px 5px 0 0; background-color: #efefef;">
		<a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:false,disabled:true">保存</a> <a
			id="btnClose" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel',plain:false,disabled:true">关闭</a>
	</div>
	<script type="text/javascript">
		//页面初始化
		$(function() {
			//解析页面
			$.parser.parse();
			var id = $.query.getId();
			$('body').css({
				visibility : 'visible'
			});
			var t = $('#strategy');
			//加载数据
			t
					.domain(
							'load',
							{
								url : '${pageContext.request.contextPath}/integrate/strategy/load/'
										+ id,
								names : [],
								onLoadSuccess : function(data, status, XHR) {
									$('#btnSave').linkbutton('enable')[0].onclick = function() {
										save();
									};
									chosenCombox(data); //初始化下拉框
								},
								onLoadComplete : function() {
									//关闭按钮
									$('#btnClose').linkbutton('enable')[0].onclick = function() {
										$(parent).domain('closeDialog');
									}
								}
							});
			//保存
			function save() {
				if (t.form('validate') == false) {
					return false;
				}
				var data = t.domain('collect');
				$('#btnSave,#btnClose').linkbutton('disable');
				//如果id为0，是要创建一条新记录，否则是whn
				var url = '${pageContext.request.contextPath}/integrate/strategy/'
						+ ($.query.getId() == "0" ? 'create' : 'edit/' + id);
				t.domain('edit', {
					title : ' ',
					url : url,
					data : data,
					onBeforeLoad : function(XHR) {
					},
					onLoadSuccess : function(data, status, XHR) {
						//保存或成功后执行代码
						top.$.data(top.document.body, "domain.create.refresh",
								"refresh"); //设置列表页面刷新
						$(parent).domain('closeDialog'); //关闭窗口
					},
					onLoadError : function(XHR, statusText, errorThrow) {
						//保存或失败时执行代码
						$('#btnSave,#btnClose').linkbutton('enable');
					}
				});
			}
		});
		
		function  changename(type,typename){
			  var sel=document.getElementById(type);
			var name=sel.options[sel.selectedIndex].text;
			document.getElementById(typename).value=name;
		}
	</script>