<%@ page isELIgnored="false" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
        *  优先级页面
        * 
        * @author admin
        *
-->
<!DOCTYPE html>
<html>
<head>
<title>编辑 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" /> 
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrap.css"></link>
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrapSwitch.css"></link>
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
 
<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
<script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
<style type="text/css">
td.tdCaption {
	text-align: center;
}
td.tdCaption span {
	color: #ff0000;
}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input {
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
	width: 300px;
}

</style>
</head>
<body class="easyui-layout">

<div data-options="region:'center',border:false" style="padding:0px;">
	<div style="margin-top:20px;margin-left:120px;margin-bottom:50px;">
		<input type="hidden" id="id" name="id" value="${id}"/> 
		<label for="zxPriority">优先级：</label>
		<select id="zxPriority" name="zxPriority" class="easyui-combobox" style="width:150px;">
			<option value=0>请选择</option>
			<option value=1>普通优先级</option>
			<option value=2>中级优先级</option>
			<option value=3>高级优先级</option>
		</select>
	</div>
<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
	<a id="btnSubmit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
   <a id="btnClose" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<script type="text/javascript">
	
	$(function(){
		//解析页面
		$.parser.parse();	
		
		$("#btnSubmit").click(function(){
			var value=$('#zxPriority').combobox('getValue');
			if(value==0){
				$.messager.alert('提示','您没有选择','info');
			}else{
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/inject/injectPhoneZx/updateVideoPriority',
					dataType:'json',
					data:{
						id:$('#id').val(),
						zxPriority:value
					},
					success:function(data){
						if(data!=null){
							if(data==1){
								$.messager.alert('提示','保存成功','info');
								$(parent).domain('closeDialog');
								
							}else if(data==-1){
								$.messager.alert('提示','保存失败','info');
							}
						}
					}
				});
			}
		});
		
		$("#btnClose").click(function(){
			 $(parent).domain('closeDialog'); 
		});
		
	})
	
	
</script>
