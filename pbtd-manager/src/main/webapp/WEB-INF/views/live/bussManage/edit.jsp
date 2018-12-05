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
    <form id="vodchannel"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
	<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
		<c:if test="${id>0}">
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		商家编号
				</td>
				<td class="tdContent">
						<input type="text" id="bussid" name="bussid"  <c:if test="${id>0 }"> type="text" disabled="disabled" </c:if> class="input text easyui-numberbox"  style="" data-options="validType:'text[1,127]',required:true" />	
 				</td>
			</tr>
			</c:if> 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 商家名称
				</td>
				<td class="tdContent">
		<input type="text" id="name" name="name"  class="input text easyui-validatebox-disable"  style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		商家地址
				</td>
				<td class="tdContent">
		<input type="text" id="address" name="address" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		商家联系人
				</td>
				<td class="tdContent">
		<input type="text" id="bussuser" name="bussuser" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		商家IP
				</td>
				<td class="tdContent">
		<input type="text" id="projectip" name="projectip" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		商家ID
				</td>
				<td class="tdContent">
		<input type="text" id="projectid" name="projectid" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		联系电话
				</td>
				<td class="tdContent">
		<input type="text" id="bussphone" name="bussphone" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
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
	        var t = $('#vodchannel');
	        $parentcode = $("#parentcode");
	        choosechannel();
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/live/BussManage/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
 
	
        //保存
        function save() {
        	if (t.form('validate') == false) {
        		return false;
        	}
            var data = t.domain('collect');
            $('#btnSave,#btnClose').linkbutton('disable');
	    //如果id为0，是要创建一条新记录，否则是whn
	    var  url= '${pageContext.request.contextPath}/live/BussManage/'+($.query.getId()=="0"?'create':'edit/'+id);  
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
  	});
      //选择频道
    	function choosechannel(){
               	$.ajax({
                   	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelphone",
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{levels:1},
                   	async:false,
                   	success:function(msg){
                   		var options = " <option value=''>--请选择--</option> ";
		            		for(var i=0;i<msg.length;i++){
		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
		            		}
		            		$parentcode.html(options);
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   });
               }
</script>
