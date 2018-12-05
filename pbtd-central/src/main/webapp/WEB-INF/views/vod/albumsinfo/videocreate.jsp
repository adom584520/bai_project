<%@ page isELIgnored="false" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
        *  新建页面
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
.aixuexi img{width:117px;height:170px;}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
	<form id="forminfo" style="margin:10px; padding:0;" method="post" autoTypeset="false" columnSize="2">
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				播放剧集
				</td>
				<td class="tdContent" style="width: 60%;">
					<input type="text" id="drama" name="drama"  class="easyui-numberbox"  style="" data-options="validType:'text[1,10]',required:true" />	
				</td>
				<td class="tdContent" colspan="2" rowspan="5" style="text-align: right;">
					<div class="aixuexi" style=" height:150px;"> 
						<img id="pic" name="pic"  style="margin-right: 2px;" src="${map.pic}"/>
					</div>
				</td>
				</tr> 
				<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 剧集排序
						</td>
						<td class="tdContent">
							<input type="text" id="dramasequence" name="dramasequence"   class="easyui-numberbox"  style="" data-options="validType:'text[1,10]',required:true" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 剧集名称
						</td>
						<td class="tdContent">
							<input type="text" id="dramaname" name="dramaname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,150]',required:true" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 剧集标题
						</td>
						<td class="tdContent">
							<input type="text" id="title" name="title" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,150]',required:true" />	
					</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 剧集简介
						</td>
						<td class="tdContent">
							<input type="text" id="dramaviewPoint" name="dramaviewPoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,550]'" />	
				</td>
				</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 播放时长
						</td>
						<td class="tdContent">
							<input type="text" id="duration" name="duration"  class="easyui-numberbox"  style="" data-options="validType:'text[1,10]'" />	
						</td>
					</tr>
					<tr>
					<td style="text-align: right; width: 15%" class="tdCaption"
								align="center">cp源</td>
							<td class="tdContent" colspan="3"> 
								<select id="SourceType" name="SourceType" style="width: 90px;" class="input text easyui-validatebox-disable">
									<c:forEach items="${cpsource}" var="map">
										<option value="${map.ID }">${map.NAME }</option>
									</c:forEach>
								</select>
								</td>
				</tr>
				<tr>		
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				码率
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="version" name="version"   class="input text easyui-validatebox-disable"  style="" data-options="validType:'text[1,20]',required:true" />	
						</td>
							
						</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
						 播放地址
								</td>
								<td class="tdContent" colspan="3">
						 <textarea  id="movieUrl" name="movieUrl" style="width:60%;height:90px;" data-options="validType:'text[1,255]',required:true" rows="60" cols="4" >
								</textarea>
					</td>	
			</tr>
			<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
							 文件流地址
									</td>
									<td class="tdContent"  colspan="3"  >
									<textarea id="fileurl" name="fileurl"  data-options="validType:'text[1,255]',required:true" style="width:60%;height:90px;" rows="60" cols="4" >
									</textarea>
									
						</td>
					</tr>  
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
							 cp源标识
									</td>
									<td class="tdContent"  colspan="3"  >
									<textarea id="bz" name="bz"  data-options="validType:'text[1,255]',required:true" style="width:60%;height:90px;" rows="60" cols="4" >
									</textarea>
									
						</td>
					</tr> 
		</table>	
</form>		 
</div>

<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
     <a id="btnSave" href="javascript:save()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false">保存</a>
   <a id="btnClose" href="javascript:void($(parent).domain('closeDialog'))" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false ">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        $('body').css({ visibility: 'visible' });
	        var id = "${seriesCode}"; 
	        var centralcode="${centralcode}";
	        var t = $('#forminfo');
    });
		    //保存
		    function save() {
		    	var t = $('#forminfo');
		    	if (t.form('validate') == false) {
		    		return false;
		    	}
		        var data = t.domain('collect');
		        if(data.movieUrl=='' ||data.movieUrl==null){
		        	alert("播放地址不能为空！"); return;
		        }
		        $('#btnSave,#btnClose').linkbutton('disable');
		    var  url= '${pageContext.request.contextPath}/vod/albuminfo/video/insert/${seriesCode}/${centralcode}';
			     t.domain('edit', {
		             title: ' ',
		             url: url,
		             data: data,
		             onBeforeLoad: function(XHR) {
		             },
		             onLoadSuccess: function(data, status, XHR) {
		             	$('#btnSave,#btnClose').linkbutton('enable');
		             	top.$.data(top.document.body, "domain.create.refresh","refresh");
		                 $(parent).domain('closeDialog');
		             },
		             onLoadError: function(XHR, statusText, errorThrow) {
		             	$('#btnSave,#btnClose').linkbutton('enable');
		             }
		         });
		    }

</script>
