<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title> 管理</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
        <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
        
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
        <style type="text/css">
        a.l-btn span span.l-btn-text {
        width:60px;
        }
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
         <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:90px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
			 <span class="property" > <label class="">查询起止时间：</label>
			  <input id="starttime" name="starttime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="endtime" name="endtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            <hr>
            <span class="property" > <label class="">汇集日期：</label>
				<input id="downloadtime" name="downloadtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:downloaduser()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">开始汇集</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="tables"></table>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
               var t = $('#tables');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.del,'-',exports];
                	t.domain('datagrid', {
                        title: '用户数据统计',
                        url: '${pageContext.request.contextPath}/user/usercount/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	 {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
	                    	,{field: 'createtime', title: '日期', width: 120,   align: 'center',
		                    		formatter: function(value) { 
		                    			if(value == "统计"){
			                                 return value;     
		                    			}
		                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                 return datetime;     
									}}
	                    	,{field: 'usercount', title: '注册用户数', width: 80,   align: 'center'}
	                    	,{field: 'addusercount', title: '新增注册', width: 80,   align: 'center'}
	                    	,{field: 'playusercount', title: '点播用户数', width: 80,   align: 'center'}
	                    	,{field: 'addplayusercount', title: '新增点播用户', width: 70,   align: 'center'}
	                    	,{field: 'playcount', title: '点播次数', width: 80,   align: 'center'}
	                    	,{field: 'addplaycount', title: '新增点播次数', width: 70,   align: 'center'}
	                    	,{field: 'activecount', title: '活跃次数', width: 120,   align: 'center'}
	                    	,{field: 'addactivecount', title: '新增活跃次数', width: 70,   align: 'center'}
	                    	,{field: 'activeusercount', title: '活跃用户数', width: 100,   align: 'center'}
	                    	,{field: 'addactiveusercount', title: '新增活跃用户数', width: 70,   align: 'center'}
	                    	,{field: 'playtime', title: '点播总时长（min）', width: 120,   align: 'center'}
	                    	,{field: 'activedegree', title: '活跃度（%）', width: 100,   align: 'center'}
	                    	,{field: 'playactivedegree', title: '点播占比（%）', width: 110,   align: 'center'}
	                    	,{field: 'fourgplaytime', title: '4g播放时长（min）', width: 80,   align: 'center'}
	                    	,{field: 'wifiplaytime', title: 'wifi播放时长（min）', width: 80,   align: 'center'}
	                    	,{field: 'playtimetoone', title: '人均播放时长（min）', width: 80,   align: 'center'}
	                    	,{field: 'fourgplayusercount', title: '4g点播用户数', width: 80,   align: 'center'}
	                    	,{field: 'wifiplayusercount', title: 'wifi点播用户数', width: 80,   align: 'center'}
	                    	
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
     	
     	 //导出操作 
        	 var  exports=$.extend($.fn.domain.Sysbtn2,{
             	title:"导出",
            	text:"导出",
            	iconCls:"icon-export",
        		handler: function() {
        			var queryParams = $form.serialize();
                    var url = "${pageContext.request.contextPath}/user/usercount/export?"+ queryParams;
                    window.location.href = url;
                } 
        	});
     	 
       		function downloaduser(){
       			if (confirm("将导入选中日期当天的用户统计数据")) {  
       				var queryParams = $form.serializeArray();
       				if(queryParams[2].value  == '' || queryParams[2].value.length == 0){
       					top.$.messager.alert("信息", "请选择手动导入用户数据的开始时间", "info", null, 2000);
       					return false;
       				}else{
       					var url = "${pageContext.request.contextPath}/user/aaaaa?"+$form.serialize();
       					//window.location.href = url;
       					$.ajax({
       					   url:url,
       					   type:"POST",
       					   async:true,
       					   dataType : "json",
       					   data : {},
       					   success:function(data) {
       					        alert("导入成功");
       					    	getinfo();
       					   },error:function(){
       							alert("导入成功");
       					   }
       					  });
       				}  
       			}
       		}
          	 	   
        
        </script>
    </body>  
</html>
