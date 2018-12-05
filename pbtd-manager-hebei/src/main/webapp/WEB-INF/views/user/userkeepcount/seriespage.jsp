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

.aixuexi img{width:117px;height:150px;}
.aixue img{width:150px;height:110px;}
.tblMain{border:0px; }
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="VodAlbuminfo" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
			<br/>
			<h4>点播记录剧集</h4>
			<span class="property"><label class="">总条数：${allcount}</label></span>
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain"
				align="center">
				<tr>
					<th style="text-align: center; width: 14%" class="tdCaption" align="center">点播日期</th>
					<th style="text-align: center; width: 25%" class="tdCaption" align="center">剧集名称</th>
					<th style="text-align: center; width: 5%" class="tdCaption" align="center">剧集</th>
					<th style="text-align: center; width: 16%" class="tdCaption" align="center">专辑名称</th>
					<th style="text-align: center; width: 8%" class="tdCaption" align="center">频道名称</th>
					<th style="text-align: center; width: 8%" class="tdCaption" align="center">播放时长</th>
					<th style="text-align: center; width: 8%" class="tdCaption" align="center">网络类型</th>
					<th style="text-align: center; width: 8%" class="tdCaption" align="center">清晰度</th>
					<th style="text-align: center; width: 8%" class="tdCaption" align="center">使用版本</th>
				</tr>

				<c:forEach items="${serieslist }" var="map" varStatus="c">
					<tr>
						<td class="tdContent" style="text-align: center;">${map.createTime }</td>
						<td class="tdContent">${map.dramaname }</td>
						<td class="tdContent">${map.dram }</td>
						<td class="tdContent">${map.seriesName }</td>
						<td class="tdContent">${map.channelName }</td>
						<td class="tdContent">
						<c:if test="${empty map.playLength}">0</c:if>
						<c:if test="${!empty map.playLength}">${map.playLength}</c:if>
						</td>
						<td class="tdContent" style="text-align: center;">
								<c:if test="${map.netStatus eq 0 }">4G</c:if>
								<c:if test="${map.netStatus eq 1 }">wifi</c:if>
								<c:if test="${empty map.netStatus}">无数据</c:if>
						</td> 
						<td class="tdContent" style="text-align: center;">
								<c:if test="${map.definition eq 0 }">自动</c:if>
								<c:if test="${map.definition eq 1 }">省流</c:if>
								<c:if test="${map.definition eq 2 }">标清</c:if>
								<c:if test="${map.definition eq 3 }">高清</c:if>
								<c:if test="${map.definition eq 4 }">超清</c:if>
								<c:if test="${map.definition eq 5 }">蓝光</c:if>
								<c:if test="${empty map.definition}">无数据</c:if>
								
						</td> 
						<td class="tdContent">${map.versionNum }</td>
					</tr>
				</c:forEach>
			</table>
    </form>
</div>
<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
   <a id="btnClose" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false,disabled:true">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        $('body').css({ visibility: 'visible' });
	        var t = $('#VodAlbuminfo');
	        var id = "${id}";
	        
	         //加载数据
	        t.domain('load', {
	            url: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/load/" + id,
	            onLoadSuccess: function(data, status, XHR) {
	            },
        }); 
    });
	
</script>
