<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
        *  新建页面
        * 
        * @author JOJO
        *
-->
<!DOCTYPE html>
<html>
<head>
	<title>手机推荐轮播图编辑 </title>
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
	<script type="text/javascript">
		function typeSelect(){
		    	if($("#type").val()==2){
		    		$("#liveListTr").show();
		    		$("#targetCode").prop("disabled",true);
		    		return;
		    	}
		    	$("#liveListTr").hide();
		    	$("#targetCode").val("");
		    	$("#targetCode").prop("disabled",false);
		    };
	</script>
</head>
<body class="easyui-layout" style="visibility:hidden">
	<div data-options="region:'center',border:false" style="padding:0px;">
	    <form id="addAndEditForm"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
			<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
				 	<td style="text-align: right; width: 20%" class="tdCaption" align="center">
						 类型
					</td>
					<td class="tdContent"> 
						<select id="type" name="type"  style="width: 120px;height: 25px" onchange="typeSelect()">
							<option value="" selected="selected">请选择</option>
							<option value="1">点播</option>
							<option value="2">直播</option>
							<option value="3">webUrl</option>
							<option value="4">专题</option>
							<option value="5">图片</option>
							<option value="6">视频</option>
						</select>
					 </td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" class="tdCaption"  align="center">
						 名称
					</td>
					<td class="tdContent"> 
						<input type="text" id="name" name="name" class="easyui-validatebox" validType='text[1,50]' required="true" missingMessage="请输入名称" style="width:120px; height: 24px">
					</td>
				</tr>
				<tr id="liveListTr">
				 	<td style="text-align: right; width: 20%" class="tdCaption" align="center">
						 直播频道列表
					</td>
					<td class="tdContent">
						<select id="liveType" style="width: 120px;height: 25px">
							<option value="" selected="selected">请选择</option>
							<option value="1,CCTV-1-HD">CCTV-1高清</option> 
							<option value="1,CCTV-2-HD">cctv2财经高清</option> 
							<option value="1,CCTV-3">CCTV-3</option> 
							<option value="1,CCTV-4-HD">CCTV4高清</option> 
							<option value="1,CCTV-5">CCTV-5</option> 
							<option value="1,CCTV-5PLUS">CCTV-5+</option> 
							<option value="1,CCTV-6-HD">cctv6电影高清</option> 
							<option value="1,CCTV-7">CCTV-7</option> 
							<option value="1,CCTV-8-HD">cctv8电视剧高清</option> 
							<option value="1,CCTV-9">CCTV-9</option> 
							<option value="1,CCTV-10">CCTV-10</option> 
							<option value="1,CCTV-11">CCTV-11</option> 
							<option value="1,CCTV-12">CCTV-12</option> 
							<option value="1,CCTV-13">CCTV-13</option> 
							<option value="1,CCTV-14">CCTV-14</option> 
							<option value="1,CCTV-15">CCTV-15</option> 
							<option value="1,CCTV-NEWS">CCTV-NEWS</option> 
							<option value="3,KAKU">卡酷少儿</option> 
							<option value="3,JINYING">金鹰卡通</option> 
							<option value="2,ZHEJIANG-HD">浙江卫视高清</option> 
							<option value="2,JIANGSU-HD">江苏卫视高清</option> 
							<option value="2,HUNAN-HD">湖南卫视高清</option> 
							<option value="2,DONGFANG-HD">东方卫视高清</option> 
							<option value="2,BTV-1">北京卫视高清</option> 
							<option value="2,GUANGDONG-HD">广东卫视高清</option> 
							<option value="2,HEILONGJIANG-HD">黑龙江卫视高清</option> 
							<option value="2,SHENZHEN-HD">深圳卫视高清</option> 
							<option value="2,ANHUI">安徽卫视</option> 
							<option value="2,LIAONING-HD">辽宁卫视高清</option> 
							<option value="2,SHANDONG-HD">山东卫视高清</option> 
							<option value="2,GUANGXI">广西卫视</option> 
							<option value="2,LVYOU">旅游卫视</option> 
							<option value="2,CHONGQING-HD">重庆卫视高清</option> 
							<option value="2,SICHUAN">四川卫视</option> 
							<option value="2,HENAN">河南卫视</option> 
							<option value="2,DONGNAN">东南卫视</option> 
							<option value="2,TIANJIN-HD">天津卫视(高清)</option> 
							<option value="2,JIANGXI">江西卫视</option> 
							<option value="2,HUBEI-HD">湖北卫视高清</option> 
							<option value="2,JILIN">吉林卫视</option> 
							<option value="2,SHANXI">山西卫视</option> 
							<option value="2,GUIZHOU">贵州卫视</option> 
							<option value="2,QINGHAI">青海卫视</option> 
							<option value="2,NEIMENGGU">内蒙古卫视</option> 
							<option value="2,YUNNAN">云南卫视</option> 
							<option value="2,HEBEI">河北卫视</option> 
							<option value="2,NINGXIA">宁夏卫视</option> 
							<option value="2,XINJIANG">新疆卫视</option> 
							<option value="2,GANSU">甘肃卫视</option> 
							<option value="3,YOUMAN">优漫卡通</option> 
							<option value="4,CETV-1">CETV1</option> 
							<option value="4,CETV-2">CETV2</option> 
							<option value="1,CCTV-1">CCTV-1</option> 
							<option value="2,ZHEJIANG">浙江卫视</option> 
							<option value="2,JIANGSU">江苏卫视</option> 
							<option value="2,HUNAN">湖南卫视</option> 
							<option value="2,DONGFANG">东方卫视</option> 
							<option value="2,BEIJING">北京卫视</option> 
							<option value="2,GUANGDONG">广东卫视</option> 
							<option value="1,CCTV-2">cctv2财经</option> 
							<option value="2,SHANDONG">山东卫视</option> 
							<option value="2,TIANJIN">天津卫视</option> 
							<option value="2,HUBEI">湖北卫视</option> 
							<option value="2,SHENZHEN">深圳卫视</option> 
							<option value="2,CHONGQING">重庆卫视</option> 
							<option value="2,LIAONING">辽宁卫视</option> 
							<option value="1,CCTV-8">cctv8</option> 
							<option value="1,CCTV-6">cctv6电影</option> 
							<option value="1,CCTV-4">CCTV4</option> 
						</select>
					 </td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" class="tdCaption" align="center">
						id
					</td>
					<td class="tdContent"> 
						<input type="text" id="targetCode" name="targetCode" class="easyui-validatebox" validType="text[0,50]" missingMessage="请输入点播或直播的ID" style="width:120px; height: 24px">
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" align="center" class="tdCaption">
						内容看点
					</td>
					<td class="tdContent"> 
						<input type="text" id="contentPoint" name="contentPoint" class="easyui-validatebox" validType='text[0,200]' missingMessage="请输入内容看点" style="width:400px; height: 24px">
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" align="center" class="tdCaption">
						webUrl
					</td>
					<td class="tdContent"> 
						<input type="text" id="weburl" name="weburl" class="easyui-validatebox" validType='text[0,50]' missingMessage="请输入跳转网页的URL" style="width:350px; height: 24px">
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" align="center" class="tdCaption">
						 视频
					</td>
					<td class="tdContent"> 
						<input type="text" id="playurl" name="playurl" class="easyui-validatebox" validType='text[0,50]' missingMessage="请输入视频URL" style="width:350px; height: 24px">
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" class="tdCaption"  align="center">
						排序
					</td>
					<td class="tdContent"> 
						<input type="text" id="sequence" name="sequence" class="easyui-validatebox" validType="text[1,50]"  style="width:50px; height: 24px">
					</td>
				</tr>
				<tr>
					<td style="text-align: right; width: 20%" class="tdCaption" align="center">
					 	状态
					</td>
					<td class="tdContent"> 
						<select id="status" name="status" style="width: 120px;height: 25px" class="status-select">
							<option value="0">下线</option>					
							<option value="1">上线</option>
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
	        var t = $('#addAndEditForm');
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/phone/slideshow/queryById/'+id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() {
	                	save(); 
	                };
	                chosenCombox(data);  //初始化下拉框
	                var type = $("#type").val();
	                if(type==2){
	                	$("#liveListTr").show();
			    		$("#targetCode").prop("disabled",true);
	                }
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { 
	            		$(parent).domain('closeDialog'); 
                	}
            	},
	        });
	        //保存
	        function save() {
	        	if (!t.form('validate')) {
	        		return false;
	        	}
	            var data = t.domain('collect');
	            console.log(data);
	            $('#btnSave,#btnClose').linkbutton('disable');
			    //如果id为0，是要创建一条新记录，否则是whn
			    var  url= '${pageContext.request.contextPath}/vod/phone/slideshow/'+($.query.getId()=="0"?'insert':'update/'+id);  
			    $.ajax({
					url : url,
					type : "post",
					data : data,
					dataType : "json",
					success : function(data) {
						if (data.success) {
							$.messager.alert('提示信息', data.message, "", function() {
								$.messager.alert('提示信息', data.message);
								$('#btnSave,#btnClose').linkbutton('enable');
								top.$.data(top.document.body, "domain.create.refresh","refresh");
							    $(parent).domain('closeDialog');
							});
						} else {
							$.messager.alert('提示信息', data.message);
							$('#btnSave,#btnClose').linkbutton('enable');
						}
					}
				});
	        }
	        $("#liveListTr").hide();
	   	    $("select#liveType").change(function(){
	   	    	$("#targetCode").val($("#liveType").val());
	   	    });
	   	});
	</script>
</body>
</html>