<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="recommandPic"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
	<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
				 	<td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					  类型
					</td>
					<td class="tdContent"> 
					<select id="type" name="type"  style="width: 120px;" >
					<option value="1">点播</option>
					<option value="2">直播</option>
					<option value="3">web图片</option>
					<option value="4">专题</option>
					<option value="5">图片</option>
					<option value="6">视频</option>
					</select>
					 </td>
				</tr>
				<tr>
				<td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					  频道
					</td>
					<td class="tdContent"> 
					<select id="channel_id" name="channel_id"  style="width: 120px;" class="channel-select">		
                     <option value="${map.channelCode }"<c:if test="${channel_id eq  map.channelCode}">selected="selected"</c:if> >${map.channelName }</option>
					</select>
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 名称
					</td>
					<td class="tdContent"> 
					<input type="text" id="name" name="name"   class="easyui-validatebox" validType='text[1,50]' required="true" missingMessage="请输入，必须为文字">
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 看点
					</td>
					<td class="tdContent"> 
					<input type="text" id="viewPoint" name="viewPoint"   class="easyui-validatebox" validType='text[1,127]' >
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 id
					</td>
					<td class="tdContent"> 
					<input type="text" id="target_code" name="target_code"   class="easyui-validatebox" validType="text[1,50]" required="true" missingMessage="请输入">
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					排序
					</td>
					<td class="tdContent"> 
					<input type="text" id="sequence" name="sequence"   class="easyui-validatebox" validType="text[1,50]">
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 webUrl
					</td>
					<td class="tdContent"> 
					<input type="text" id="weburl" name="weburl"   class="easyui-validatebox" validType='text[1,50]'>
				 </td>
				</tr>
				<tr>
				  <td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 视频
					</td>
					<td class="tdContent"> 
					<input type="text" id="playurl" name="playurl"   class="easyui-validatebox" validType='text[1,50]' >
				 </td>
				</tr>
				<tr>
				<td style="text-align: right; width: 20%"   style="width: 120px;" class="tdCaption"  align="center">
					 状态
					</td>
					<td class="tdContent"> 
					<select id="status" name="status"  style="width: 120px;" class="status-select">
						<option value="1">上线</option>
						<option value="0">下线</option>						
					</select>
				 </td>
				</tr>
		</table>
			
    </form>
</div>
<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
   <a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false,disabled:true">保存</a>
   <a id="btnClose" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false,disabled:true">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        var id = $.query.getId();
	        $('body').css({ visibility: 'visible' });
	        var t = $('#recommandPic');
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/system/recommandpic/load/'+id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            },
	        });
	            //获取频道列表
	               $('#channel_id').combobox({     
	                   url:'${pageContext.request.contextPath}/vod/system/recommandpic/queryChannelInfo',    
	                   editable:false, //不可编辑状态    
	                   cache: false,    
	                  // panelHeight: 'auto',//自动高度适合    
	                   valueField:'channelCode',       
	                   textField:'channelName',  
	               });
        
 
	
        //保存
        function save() {
        	if (t.form('validate') == false) {
        		return false;
        	}
            var data = t.domain('collect');
            console.log(data);
            $('#btnSave,#btnClose').linkbutton('disable');
	    //如果id为0，是要创建一条新记录，否则是whn
	    var  url= '${pageContext.request.contextPath}/vod/system/recommandpic/'+($.query.getId()=="0"?'create':'edit/'+id);  
		     t.domain('edit', {
	             title: ' ',
	             url: url,
	             data: data,
	             onBeforeLoad: function(XHR){
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
   	});
</script>
