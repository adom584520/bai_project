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
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="AlbumsWithBLOBs" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent">
		<input type="text" id="actors" name="actors" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="albumattributes" name="albumattributes" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑code
				</td>
				<td class="tdContent" style="width: 60px;">
			<input type="text" id="albumId" name="albumId" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
		</td>
		<td class="tdContent" colspan="2" rowspan="6" style="text-align: right; width: 30%">
			<div class="aixuexi" style=" height:150px;"> 
				<img id="picUrl" class="picUrl"  style="margin-right: 2px;" src=""/>
			</div>
		</td>
		</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="albumName" name="albumName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道id
				</td>
				<td class="tdContent">
		<input type="text" id="chnId" name="chnId" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td></tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道名称
				</td>
				<td class="tdContent">
		<input type="text" id="chnName" name="chnName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent"  >
		<input type="text" id="tags" name="tags" class="input text easyui-validatebox-disable" style="width:80%" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 导演
				</td>
				<td class="tdContent"  >
		<input type="text" id="directorname" name="directorname" class="input text easyui-validatebox-disable" style="width:80%" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent"  >
		<input type="text" id="actorname" name="actorname" class="input text easyui-validatebox-disable" style="width:80%" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 cpid
				</td>
				<td class="tdContent">
		<input type="text" id="cpId" name="cpId" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 cp源
				</td>
				<td class="tdContent">
		<input type="text" id="cplist" name="cplist" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总集数
				</td>
				<td class="tdContent">
		<input type="text" id="sets" name="sets" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 更新剧集
				</td>
				<td class="tdContent">
		<input type="text" id="currentCount" name="currentCount" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="currshowPlayOrder" name="currshowPlayOrder" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 is3d
				</td>
				<td class="tdContent">
		<input type="text" id="is3d" name="is3d" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总时长
				</td>
				<td class="tdContent">
		<input type="text" id="duration" name="duration" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑简介
				</td>
				<td class="tdContent" colspan="3">
				<textarea id="albumDesc" name="albumDesc"  style="width:95%;height:90px;"rows="60" cols="4" ></textarea>
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑看点
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="focus" name="focus" class="input text easyui-validatebox-disable" style="width:80%" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<!--
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 是否付费
				</td>
				<td class="tdContent">
		<input type="text" id="isPurchase" name="isPurchase" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 是否购买
				</td>
				<td class="tdContent">
		<input type="text" id="isPurchaseOwn" name="isPurchaseOwn" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>
				<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 是否
				</td>
				<td class="tdContent">
		<input type="text" id="isSeries" name="isSeries" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>
		 <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="leaftags" name="leaftags" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr> 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="maxset" name="maxset" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="phase" name="phase" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>-->
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="pid" name="pid" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="playcnt" name="playcnt" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		   豆瓣评分
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="score" name="score" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="scorelabel" name="scorelabel" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr> 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="season" name="season" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>-->
			<!-- 	<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="superscripts" name="superscripts" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 首映日期
				</td>
				<td class="tdContent">
		<input type="text" id="showDate" name="showDate" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,15]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent">
		<input type="text" id="timestamp" name="timestamp" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,15]'" />	
	</td>
			</tr>
			<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="bz" name="bz" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,2]'" />	
	</td>
			</tr> -->
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="originalCountry" name="originalCountry" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>
			<!--
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="tagsids" name="tagsids" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>  
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="actorids" name="actorids" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr> 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="originaids" name="originaids" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>  
			 <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 
				</td>
				<td class="tdContent">
		<input type="text" id="directorids" name="directorids" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>  
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 状态
				</td>
				<td class="tdContent">
		<input type="text" id="status" name="status" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,2]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 是否入库
				</td>
				<td class="tdContent">
		<input type="text" id="isstorage" name="isstorage" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,1]'" />	
	</td>
			</tr>-->
			</table>
			<br/>
			<br/><h4>播放剧集</h4>
		<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
					  <th style="text-align: center; width: 5%"  class="tdCaption"  align="center">剧集</th>
					 <th style="text-align: center; width: 50%"  class="tdCaption"  align="center">名称</th>
					  <th style="text-align: center; width: 40%"  class="tdCaption"  align="center">更新日期</th>
				</tr>
				
					<c:forEach items="${gitlist }" var="map" varStatus="c">
						<tr>
							<td class="tdContent">${map.playOrder }</td>
							<td class="tdContent"><a   onclick="javascript:show('${map.playOrder }','${map.tvId }','${map.parentId }')" >${map.tvName }</a></td>
							<td class="tdContent">${map.updatetime }</td>
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
	        var t = $('#AlbumsWithBLOBs');
	        var id = "${id}";
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/yinhe/albums/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                //   $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	                $("#picUrl").attr("src",data.picUrl);
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
       
    });
	
    function show(playOrder,tvId,parentId){
		$(parent).domain("openDialog", { 
        	iconCls: "icon-view", 
        	title: "查看", 
        	src: "${pageContext.request.contextPath}/yinhe/albums/showvideo/"+playOrder+"/"+tvId+"/"+parentId,
        	width: 760, 
        	height: 450,
        	onClose: function() { 
            }
        });
	}
</script>
