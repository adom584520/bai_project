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
	      <input  id="id" hidden="true" name="id" />
	      <input  id="seriesCode" name="seriesCode" hidden="true">
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			<tr>
				<td style="text-align: right; width: 20%"  class="tdCaption"  align="center">
				播放剧集
				</td>
				<td class="tdContent" style="width: 60%;">
					<input type="text" id="drama" name="drama"  class="easyui-numberbox"  style="" data-options="required:true" />	
				</td>
				<td style="text-align: right; width: 20%"  class="tdCaption"  align="center">
				剧集排序
				</td>
				<td class="tdContent" style="width: 60%;">
					<input type="text" id="dramasequence" name="dramasequence"  class="easyui-numberbox"  style="" data-options="required:true" />	
				</td>
				</tr>
				<tr>
						<td style="text-align: right; width: 20%"  class="tdCaption"  align="center">
				 剧集名称
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="dramaname" name="dramaname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,150]',required:true" />	
						</td>
					</tr>
				     <tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				标题
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="title" name="title" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				描述信息
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="description" name="description" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					 <tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 剧集简介
						</td>
						<td class="tdContent"  colspan="3">
							<input type="text" id="dramaviewPoint" name="dramaviewPoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,550]'" />	
				</td>
				</tr>
			<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 是否显示
						</td>
						 <td>
		           	 <select  id="isShow" name="isShow"  style="width: 120px;" class="easyui-combobox">
			         <option value="1" selected="selected">是</option>
			         <option value="0">否</option>
			 </select>
			 </td>
			  <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 播放时长
						</td>
						<td class="tdContent">
							<input type="text" id="duration" name="duration"  class="easyui-numberbox"  style="" data-options="" />	
						</td>
			 </tr>
			 <tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				 cp源是否收费
						</td>
						 <td class="tdContent">
		           	 <select  id="isCollectfees" name="isCollectfees"  style="width: 120px;" class="easyui-combobox">
			         <option value="1" >是</option>
			         <option value="0" selected="selected">否</option>
					 </select>
			 </td>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 收费产品包
			 </td>
			 <td class="tdContent">
				<select id="collectfeesbag"  name ="collectfeesbag"   style="width:150px"> </select>
					 <div id="baglist">
				         <div style="padding:10px" id="Collectfeesbaglist" >
					         <c:forEach items="${feelist}" var="item">
					         <input type='checkbox' name='langbag'  onclick='baglmap(this);'   value='${item.code}'><span>${item.name}</span><br/>
							</c:forEach>
				        </div>
			        </div>
				
			 </td>		
			 </tr>
			 <tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				己方是否收费
						</td>
						 <td class="tdContent" colspan="3">
					  <select  id="videopaid" name="videopaid"  style="width: 120px;" class="easyui-combobox">
					         <option value="1" >是</option>
					         <option value="0" selected="selected">否</option>
					 </select>
			 </td>
			 </tr>
			 <tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				分辨率
						</td>
						<td class="tdContent">
							<input type="text" id="Resolution" name="Resolution" class="input text easyui-validatebox-disable" style="" data-options="validType:'nubmer'" />	
						</td>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				清晰度
						</td>
						<td class="tdContent">
							<input type="text" id="Definition" name="Definition" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				清晰度
						</td>
						<td class="tdContent">
							<input type="text" id="SourceType" name="SourceType" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				介质类型
						</td>
						<td class="tdContent">
							<input type="text" id="Type" name="Type" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴码率
						</td>
						<td class="tdContent">
							<input type="text" id="zxversionlist" name="zxversionlist" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴已注入码率
						</td>
						<td class="tdContent">
							<input type="text" id="zxversion" name="zxversion" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				中兴调度地址
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="zxdispatchurl" name="zxdispatchurl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				源播放地址
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="zxfileurl" name="zxfileurl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				文件流地址
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="zxmovieUrl" name="zxmovieUrl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为码率
						</td>
						<td class="tdContent">
							<input type="text" id="hwversionlist" name="hwversionlist" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为已注入码率
						</td>
						<td class="tdContent">
							<input type="text" id="hwversiont" name="hwversion" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
						</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				源播放地址
						</td>
						<td class="tdContent"  colspan="3">
							<input type="text" id="hwmovieUrl" name="hwmovieUrl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				文件流地址
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="hwfileurl" name="hwfileurl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
						<tr>
						<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
				华为调度地址
						</td>
						<td class="tdContent" colspan="3">
							<input type="text" id="hwdispatchurl" name="hwdispatchurl" class="input text easyui-validatebox-disable" style="" data-options="validType:''" />	
						</td>
					</tr>
					
		</table>	
</form>		 
</div>

<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
     <a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:false,disabled:true">保存</a> <a
			id="btnClose" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel',plain:false,disabled:true">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        $('body').css({ visibility: 'visible' });
	        var t = $('#forminfo');
	        var id="${num}";
	        $seriesCode="${seriesCode}";
	        if(id==""){
	        	id=-1;
	        }
	        $('#collectfeesbag').combo({
                editable:true,//是否可编辑
                multiple:true//可否支持多选
            }); 
          
          $('#baglist').appendTo($('#collectfeesbag').combo('panel'));
	      //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/loadvideo/'+id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                var m =  data.collectfeesbag.split(",");
	            	  var inputs = document.getElementsByName("langbag");
	               for (var i = 0; i < m.length; i++) {
	                   for(var j=0;j<inputs.length;j++){
	                      var val = inputs[j].value;
	                      if(m[i]==val){
	                          inputs[j].checked=true;
	                      }else{
	                         // inputs[j].checked=false;
	                      }
	                   }  
					}
	               baglmap();
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog');};
                	
            },
	        });
	      //保存
		    function save() {	
		    	if (t.form('validate') == false) {
		    		return false;
		    	}
		        var data = t.domain('collect');
		        var url="";
		        if(id<1){
		        	var url='${pageContext.request.contextPath}/vod/phone/vodalbuminfo/insertvideo/'+$seriesCode;
		        }else{
		          var  url= '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/updatevideo/'+id;
		        }
			       t.domain('edit', {
		             title: '编辑',
		             url: url,
		             data:data,
		             onBeforeLoad: function(XHR) {
	                 },
	                 onLoadSuccess: function(data, status, XHR) {
	                 	$('#btnSave,#btnClose').linkbutton('enable');
	                 	top.$.data(top.document.body, "domain.create.refresh","refresh");
	                     $(parent).domain('closeDialog');
	                 },
	                 onLoadError: function(XHR, statusText, errorThrow) {
	                 	$('#btnSave,#btnClose').linkbutton('enable');
	                    $(parent).domain('closeDialog');
	                 }
		         });
		    }

    });
  //付费包
	function baglmap(obj){
            var str = "";
            var strid="";
            $("input[name='langbag']").each(function(){    
                if($(this).is(":checked"))    
                {    
                	strid += ","+$(this).val(); 
                	str += ","+$(this).next('span').text();   
                }    
            });    
             $('#collectfeesbag').combo('setValue', strid.substring(1,strid.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
	}	    
</script>
