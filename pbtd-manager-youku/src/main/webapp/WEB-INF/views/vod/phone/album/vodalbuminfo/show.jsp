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
								<p>图片1</p>
						</td>
						<td   style="width:25%; " align="center" >
							<div class="aixue" style=" height:150px;"> 
								<img id="pictureurl2"  class="pictureurl2" style="margin-right: 2px;" src=""/>
							</div>
								<p>图片2</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixuexi" style=" height:150px;">
								<img  id="pictureurl3"  class="pictureurl3"  style="margin-right: 2px;" src=""/>
							</div>
								<p>图片3</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixue" style=" height:150px;">
								<img  id="pictureurl4" class="pictureurl4"  src=""/>
							</div>
								<p>图片4</p>
						</td>
						</tr>
					</table>
	</td>
			 
			 </tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑code
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="seriesCode" name="seriesCode" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]',required:true" />	
	</td>
	<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="seriesName" name="seriesName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
	
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑原名称
				</td>
				<td class="tdContent">
		<input type="text" id="originalName" name="originalName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
	
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			  拼音
			</td>
			 <td class="tdContent">
		 <input type="text" id="pinyin" name="pinyin"class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,600]',required:true"/>	
 	     </td>
 	     <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		  拼音缩写
			</td>
			<td class="tdContent">
		 <input type="text" id="pinyinsuoxie" name="pinyinsuoxie" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]',required:true"/>	
 	     </td>	
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			专辑英文名称
			</td>
			<td class="tdContent">
		 <input type="text" id="enName" name="enName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]',required:true"/>	
 	     </td>
 	     <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		  副标题
			</td>
			 <td class="tdContent">
			 <input type="text" id="vname" name="vname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]'"/>	
	 	     </td>			
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道
				</td>
				<td class="tdContent">
				 <input id="channelName" name="channelName" class="input text easyui-validatebox-disable" style="" >
            	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent">
				<input  id="labelName" name="labelName" class="input text easyui-validatebox-disable" style="" />
			</td>
			 
			</tr>
				<!-- <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道2
				</td>
				<td class="tdContent">
				 <input  id="channelName" name="channelName" class="input text easyui-validatebox-disable" style=""  />
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签2
				</td>
				<td class="tdContent">
				<input  id="labelName" name="labelName" class="input text easyui-validatebox-disable" style="" />		
			</td>
			</tr> -->
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 关联标签
				</td>
				<td class="tdContent">
                <input id="tag" name="tag" class="input text easyui-validatebox-disable"  data-options="validType:'text[1,600]'"/>
	           </td>
	           	<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 关键词
				</td>
				<td class="tdContent">
                <input id="keyWord" name="keyWord" class="input text easyui-validatebox-disable" data-options="validType:'text[1,600]'"/>
	           </td>
			</tr>			
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			角标
			</td>
			  <td class="tdContent">
				 <input  id="corner" name="corner" class="input text easyui-validatebox-disable" style="" />
			 </td>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 是否Vip
			 </td>
			 <td>
				<select  id="isvip" name="isvip"  disabled="disabled" style="width: 120px;" class="easyui-combobox">
					 <option value="0" selected="selected">否</option>
					 <option value="1">是</option>
			 </select>
			 </td>			
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			是否收费
			</td>
			  <td class="tdContent">
			 <select  id="isCollectfees" name="isCollectfees" disabled="disabled" style="width: 120px;" class="easyui-combobox">
				  <option value="0" selected="selected">否</option>
			      <option value="1">是</option>
				 </select>
			 </td>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 收费产品包
			 </td>
			 <td class="tdContent">			
		      <input id="collectfeesbag"  name ="collectfeesbag" class="input text easyui-validatebox-disable" style=""  >		
			 </td>			
			</tr>
			<tr>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 含税定价
			 </td>
			  <td class="tdContent">
			  <input id="price" name="price" class="easyui-numberbox" value="100" data-options="min:0,precision:2"/>
			  </td>
			  <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			  评分
			  </td>
			   <td class="tdContent">
			  <input id="score" name="score"   class="easyui-numberbox" value="100" data-options="min:0,precision:1"/>
			  </td>
			</tr>
			
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		    总集数
				</td>
				<td class="tdContent">
		<input type="text" id="volumncount" name="volumncount"  class="easyui-numberbox" style="" data-options="validType:'text[1,5]',required:true" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 更新剧集
				</td>
				<td class="tdContent">
		<input type="text" id="currentnum" name="currentnum"  class="easyui-numberbox" style="" data-options="validType:'text[1,5]',required:true" />	
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
		<input type="text" id="viewPoint" name="viewPoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
				 
			 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="releaseYear" name="releaseYear"  type="text" class="easyui-datebox" />	
	</td>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 首映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="orgairDate" name="orgairDate"  type="text" class="easyui-datebox" />	
	</td>
	</tr>
	<tr>
	<td  style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	          清晰度
	           </td>
	           <td class="tdContent">
	            <input id="definition" name="definition" class="easyui-numberbox"  data-options="validType:'number',required:true"/>
	           </td>
		<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总时长
				</td>
				<td class="tdContent">
			<input type="text" id="duration" name="duration"  class="easyui-numberbox" style="" data-options="validType:'text[1,20]',required:true" />		
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 导演
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="writer" name="writer"/>
		<input type="text" id="writerName" name="writerName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]',required:true" />
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="actor" name="actor"/>
		 <input  id="actorName" name="actorName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,500]',required:true" />	
	</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent"  >
		<input type="text" id="originalCountry" name="originalCountry" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		所含奖项
				</td>
				<td class="tdContent" >
		<input type="text" id="awards" name="awards" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,130]'" />	
	        </td>
			</tr>
		<tr>
	   <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	      码率
	   </td>
	   	<td class="tdContent" >
		<input type="text" id="hwversionlist" name="hwversionlist" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,130]'" />	
	    </td>
	   <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	           已注入码率
	   </td>
	   <td class="tdContent" >
	   <input type="text" id="hwversion" name="hwversion" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,130]'" />	
	    </td>
	   </tr>
			</table>
			
			<br/>
			<br/><h4>播放剧集</h4>
		<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
					  <th style="text-align: center; width: 5%"  class="tdCaption"  align="center">剧集</th>
					 <th style="text-align: center; width: 50%"  class="tdCaption"  align="center">名称</th>
					  <th style="text-align: center; width:10%"  class="tdCaption"  align="center">是否显示</th>
					   <th style="text-align: center; width: 10%"  class="tdCaption"  align="center">是否收费</th>
					  <th style="text-align: center; width: 20%"  class="tdCaption"  align="center">更新日期</th>
				</tr>
				
					<c:forEach items="${gitlist }" var="map" varStatus="c">
						<tr>
							<td class="tdContent">${map.drama }</td>
							<td class="tdContent"><a   onclick="javascript:show('${map.id }')" >${map.dramaname }</a></td>
							<td class="tdContent" style="text-align: center;">
								<c:if test="${map.isShow eq 1 }">是</c:if>
								<c:if test="${map.isShow ne 1 }">否</c:if>
							</td> 
							<td class="tdContent" style="text-align: center;">
								<c:if test="${map.isCollectfees eq 1 }">是</c:if>
								<c:if test="${map.isCollectfees ne 1 }">否</c:if>
							</td>
							<td class="tdContent" style="text-align: center;">${map.update_time }</td>
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
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	             //   $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	                var img="${maprealm.imgtitle}";
	                $("#pictureurl1").attr("src",img+data.pictureurl1);
	                $("#pictureurl2").attr("src",img+data.pictureurl2);
	                $("#pictureurl3").attr("src",img+data.pictureurl3);
	                $("#pictureurl4").attr("src",img+data.pictureurl4);
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
        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/showvideo/"+id,
        	width: 760, 
        	height: 450,
        	onClose: function() { 
            }
        });
	}
</script>
