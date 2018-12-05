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
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			 <tr>
			 	 <td colspan="4">
			   		<table align="center"   >
			   		<tr align="center" >
						<td   align="center"  style="width:25%; "  >
							<div class="aixuexi" style=" height:150px;"> 
								<img id="pictureurl1" class="pictureurl1"  style="margin-right: 2px;" src=""/>
							</div>
								<p>小图图片</p>
						</td>
						<td   style="width:25%; " align="center" >
							<div class="aixue" style=" height:150px;"> 
								<img id="pictureurl2"  class="pictureurl2" style="margin-right: 2px;" src=""/>
							</div>
								<p>小图图片</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixuexi" style=" height:150px;">
								<img  id="pictureurl3"  class="pictureurl3"  style="margin-right: 2px;" src=""/>
							</div>
								<p>大图竖图</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixue" style=" height:150px;">
								<img  id="pictureurl4" class="pictureurl4"  src=""/>
							</div>
								<p>大图横图</p>
						</td>
						</tr>
					</table>
	</td>
			 
			 </tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑code
				</td>
				<td class="tdContent">
		<input type="text" id="seriescode" name="seriescode" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="seriesname" name="seriesname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道id
				</td>
				<td class="tdContent">
		<input type="text" id="channel" name="channel" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道名称
				</td>
				<td class="tdContent">
		<input type="text" id="channelname" name="channelname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="labelname" name="labelname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			 
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总集数
				</td>
				<td class="tdContent">
		<input type="text" id="volumncount" name="volumncount" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 更新剧集
				</td>
				<td class="tdContent">
		<input type="text" id="currentnum" name="currentnum" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
			</tr>
		 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑简介
				</td>
				<td class="tdContent" colspan="3">
				<textarea  id="description" name="description" style="width:95%;height:90px;"rows="60" cols="4" ></textarea>
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	         专辑看点
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="viewpoint" name="viewpoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
				 
			 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="releaseyear" name="releaseyear" class="easyui-datebox" style="" data-options="validType:'text[1,15]'" />	
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
		 演员
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="actorname" name="actorname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 导演
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="writername" name="writername" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="originalcountry" name="originalcountry" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>
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
							<td class="tdContent">${map.drama }</td>
							<td class="tdContent"><a   onclick="javascript:show('${map.id }')" >${map.dramaname }</a></td>
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
	        var t = $('#VodAlbuminfo');
	        var id = "${id}";
	        
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/albuminfo/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	             //   $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	                var imgtitle="${maprealm.imgtitle}";
	                $("#pictureurl1").attr("src",imgtitle+data.pictureurl1);
	                $("#pictureurl2").attr("src",imgtitle+data.pictureurl2);
	                $("#pictureurl3").attr("src",imgtitle+data.pictureurl3);
	                $("#pictureurl4").attr("src",imgtitle+data.pictureurl4);
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
    });
	
    function show(id){
		$(parent).domain("openDialog", { 
        	iconCls: "icon-view", 
        	title: "查看", 
        	src: "${pageContext.request.contextPath}/vod/albuminfo/showvideo/"+id,
        	width: 760, 
        	height: 450,
        	onClose: function() { 
            }
        });
	}
</script>
