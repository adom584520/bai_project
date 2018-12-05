<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"
	src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>

<script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 60px;
}
</style>
</head>
<body class="easyui-layout" style="visibility: hidden">
	<div data-options="region:'north',border:false,split:true"
		style="padding: 0px; border-bottom: 1px solid #99BBE8; height: 0px; width: auto;">
		<form id="formQuery" style="margin: 0; padding: 0" action=""
			method="post">
			<input id="seriesCode" name="seriesCode" type="text"
				value="${seriesCode}" style="width: 200px; display: none"></input> <input
				id="mobile" name="mobile" type="text" value="${mobile}"
				style="width: 200px; display: none"></input> <input id="starttime"
				name="starttime" type="text" value="${createtime}"
				style="width: 130px; display: none"></input> <input id="endtime"
				name="endtime" type="text" value="${endtime}"
				style="width: 130px; display: none"> </input>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding: 0px;">
		<table id="tables"></table>
		<script type="text/javascript"
			src="/js/common/scripts/jquery-1.8.0.min.js"></script>
		<script type="text/javascript"
			src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
		<script type="text/javascript"
			src="/js/common/scripts/jquery-domain.min.js"></script>
		<script type="text/javascript"
			src="/js/common/jquery-easyui-1.3.1/common.js"></script>

		<script type="text/javascript"> 
            function getinfo() {
            	var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
               var t = $('#tables');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	t.domain('datagrid', {
                        title: '用户专辑点击详情',
                        url: '${pageContext.request.contextPath}/user/mobilepagecount/page',
                    	queryParams: queryParams,
                        columns: [[
                                   {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
        	                    	,{field: 'createTime', title: '播放剧集日期', width: 150,   align: 'center', hidden: false,
       	                    		formatter: function(value) { 
       	                    			var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
       	                                 return datetime;     
       								}}
        	                    	,{field: 'dramaname', title: '剧集名称', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
      		                    			 var name =decodeURI(value);
      		                                 return name;     
      									}}
        	                    	,{field: 'dram', title: '播放剧集', width: 100,   align: 'center'}
        	                    	,{field: 'seriesName', title: '专辑名称', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
   		                    			 var name =decodeURI(value);
   		                                 return name;     
   									}}
        	                    	,{field: 'channelName', title: '频道名称', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
      		                    			 var name =decodeURI(value);
      		                                 return name;     
      									}}
        	                    	,{field: 'playLength', title: '播放时长(秒)', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
        	                    			if(value == null){
         		                                 return "无数据";     
        	                    			}else{
      		                    			 var minvalue =value/1000;
      		                                 return minvalue;     
        	                    			}
      									}}
        	                    	,{field: 'netStatus', title: '网络状态', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
        	                    			 if(value == 0){
         		                                 return "4G";     
         		                    			 }else if(value == 1){
         		                                 return "wifi";     
         		                    			 }else {
         		                                 return "无数据";     
         		                    			 }  
     									}}
        	                    	,{field: 'definition', title: '清晰度', width: 100,   align: 'center',
        	                    		formatter: function(value) { 
     		                    			 if(value == 0){
     		                                 return "自动";     
     		                    			 }else if(value == 1){
     		                                 return "省流";     
     		                    			 }else if(value == 2){
         		                                 return "标清";     
     		                    			 }else if(value == 3){
         		                                 return "高清";     
     		                    			 }else if(value == 4){
         		                                 return "超清";     
     		                    			 }else if(value == 5){
         		                                 return "蓝光";     
     		                    			 }else{
         		                                 return "无数据";     
     		                    			 };
     									}}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
                    });
                }
                else {
                    t.datagrid("load",queryParams);
                }
            }
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#tables");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        
        
        </script>
</body>
</html>
