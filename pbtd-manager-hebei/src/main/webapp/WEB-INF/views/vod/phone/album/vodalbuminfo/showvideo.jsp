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
			<br/><br/><br/>
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain"  style="table-layout:fixed;" align="center">
			<tr>
				<td style="text-align: right; width: 20%"  class="tdCaption"  align="center">
		播放剧集
				</td>
				<td class="tdContent" style="width: 50%;">
			 ${map.drama} 	
		</td>
		<td class="tdContent" colspan="2" rowspan="5" style="text-align: right;">
			<div class="aixuexi" style=" height:150px;"> 
				<img id="pic" name="pic"  style="margin-right: 2px;" src="${maprealm.imgtitle}${map.pic}"/>
			</div>
		</td>
		</tr> 
			<tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		 剧集名称
				</td>
				<td class="tdContent" style="word-wrap:break-word;">
				 ${map.dramaname} 	
			</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		 剧集标题
				</td>
				<td class="tdContent" style="word-wrap:break-word;">
			 ${map.title} 	
			</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		 剧集简介
				</td>
				<td class="tdContent" style="word-wrap:break-word;">
		 ${map.dramaviewPoint} 	
		</td>
		</tr>
			<tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		 播放时长
				</td>
				<td class="tdContent">
		 ${map.duration} 	
	</td>
			</tr>
			
			<tr>
						<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
				中兴码率
						</td>
						<td class="tdContent"  colspan="3">
							${map.zxversionlist}	
						</td>
						</tr>
						<tr>
						<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
				中兴已注入码率
						</td>
						<td class="tdContent"  colspan="3">
							${map.zxversion}	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴调度地址
						</td>
						<td class="tdContent" colspan="3" style="word-wrap:break-word;">
							${map.zxdispatchurl}	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴源播放地址
						</td>
						<td class="tdContent" colspan="3" style="word-wrap:break-word;">
							${map.zxfileurl}	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴文件流地址
						</td>
						<td class="tdContent" colspan="3" style="word-wrap:break-word;">
							${map.zxmovieUrl}	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为码率
						</td>
						<td class="tdContent"  colspan="3">
							${map.hwversionlist}	
						</td>
						</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为已注入码率
						</td>
						<td class="tdContent"  colspan="3">
							${map.hwversion}	
						</td>
						</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为源播放地址
						</td>
						<td class="tdContent"  colspan="3" style="word-wrap:break-word;">
						${map.hwmovieUrl}	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为文件流地址
						</td>
						<td class="tdContent" colspan="3" style="word-wrap:break-word;">
						${map.hwfileurl}	
						</td>
					</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为调度地址
						</td>
						<td class="tdContent" colspan="3" style="word-wrap:break-word;">
						${map.hwdispatchurl}	
						</td>
					</tr> 
</table>			 		 
</div>

<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
   <a id="btnClose" href="javascript:void($(parent).domain('closeDialog'))" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false ">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        $('body').css({ visibility: 'visible' });	      
    });		 
</script>
