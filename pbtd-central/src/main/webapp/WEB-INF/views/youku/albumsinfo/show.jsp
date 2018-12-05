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
    <form id="GgAlbumsinfo" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			 <tr>
			 	 <td colspan="4">
			   		<table align="center"   >
			   		<tr align="center" >
						<td   align="center"  style="width:25%; "  >
							<div class="aixuexi" style=" height:150px;"> 
								<img id="pictureURL1" class="pictureURL1"  style="margin-right: 2px;" src=""/>
							</div>
								<p>大图 竖图</p>
						</td>
						<td   style="width:25%; " align="center" >
							<div class="aixue" style=" height:150px;"> 
								<img id="pictureURL2"  class="pictureURL2" style="margin-right: 2px;" src=""/>
							</div>
								<p>大图 横图</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixuexi" style=" height:150px;">
								<img  id="pictureURL3"  class="pictureURL3"  style="margin-right: 2px;" src=""/>
							</div>
								<p>小图 竖图</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixue" style=" height:150px;">
								<img  id="pictureURL4" class="pictureURL4"  src=""/>
							</div>
								<p>小图 横图</p>
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
		<input type="text" id="show_id" name="show_id" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="title" name="title" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道id
				</td>
				<td class="tdContent">
		<input type="text" id="categoryId" name="categoryId" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道名称
				</td>
				<td class="tdContent">
		<input type="text" id="category" name="category" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="sub_category" name="sub_category" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			 
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总集数
				</td>
				<td class="tdContent">
		<input type="text" id="episode_count" name="episode_count" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 更新剧集
				</td>
				<td class="tdContent">
		<input type="text" id="count" name="count" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,5]'" />	
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
		<input type="text" id="keyword_list" name="keyword_list" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
				 
			 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="release_date" name="release_date" class="input text easyui-validatebox-disable" style=""  />	
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
		 导演
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="director_list" name="director_list" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="performer_list" name="performer_list" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]'" />	
	</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="area" name="area" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			</tr>
			</table>
			
			<br/>
			<br/><h4>播放剧集</h4>
		<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
					  <th style="text-align: center; width: 5%"  class="tdCaption"  align="center">id</th>
					  <th style="text-align: center; width: 5%"  class="tdCaption"  align="center">剧集</th>
					  <th style="text-align: center; width:30%"  class="tdCaption"  align="center">名称</th>
					   <th style="text-align: center; width: 10%"  class="tdCaption"  align="center">是否vip</th>
					   <th style="text-align: center; width: 10%"  class="tdCaption"  align="center">视频类型</th>
					    <th style="text-align: center; width: 15%"  class="tdCaption"  align="center">创建日期</th>
					  <th style="text-align: center; width: 15%"  class="tdCaption"  align="center">更新日期</th>
				</tr>
				
					<c:forEach items="${gitlist }" var="map" varStatus="c">
						<tr
						<c:if test="${map.paid ne map.paid_next }"> style="background-color: yellow"</c:if>
						<c:if test="${map.status ne map.status_next }"> style="background-color: red"</c:if>
						>
						<td class="tdContent">${map.video_id }</td>
							<td class="tdContent">${map.video_stage }</td>
							<td class="tdContent"><a   onclick="javascript:show('${map.video_id }')" >${map.title }</a></td>
							<td class="tdContent" align="center">
							<c:if test="${map.paid eq  1}">是</c:if>
							<c:if test="${map.paid ne  1}">否</c:if>
							 </td>
							 <td class="tdContent" align="center">
							 ${map.video_type }
							 </td>
							 	<td class="tdContent">${map.create_time }</td>
							<td class="tdContent">${map.update_time }</td>
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
	        var t = $('#GgAlbumsinfo');
	        var id = "${id}";
	        
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/youku/albums/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	             //   $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	                $("#pictureURL1").attr("src",data.huge_vthumb_url);
	                $("#pictureURL2").attr("src",data.huge_thumb_url );
	                $("#pictureURL3").attr("src",data.small_vthumb_url);
	                $("#pictureURL4").attr("src",data.small_thumb_url);
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
        	src: "${pageContext.request.contextPath}/youku/albums/showvideo/"+id,
        	width: 760, 
        	height: 450,
        	onClose: function() { 
            }
        });
	}
</script>
