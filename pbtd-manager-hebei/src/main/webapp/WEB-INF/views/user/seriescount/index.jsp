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
         <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:100px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
			 <span class="property"> <label class="">top排行榜:</label>
			<select id="topstatus" name="topstatus" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">TOP - - 1</option>
			  <option value="3">TOP - - 3</option>
			  <option value="5">TOP - - 5</option>
			  <option value="10">TOP - -10</option>
			  <option value="100">TOP-- 100</option>
			</select>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="channel" name="channel" onchange="choosechannellabel(this.value);" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${channellist }" var="item">
						 	<option value="${item.channel}"> ${item.channelName}</option>
						</c:forEach>					  
					</select>
			</span>
			 <span class="property" > <label class="">查询起止时间：</label>
			  <input id="starttime" name="starttime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="endtime" name="endtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            
            <hr>
            <span class="property" > <label class="">汇集日期：</label>
			  <input id="downloadtime" name="downloadtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:downloadseries()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">开始汇集</a>
			 
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
                        url: '${pageContext.request.contextPath}/user/seriescount/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
      	                    	 {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
     	                    	,{field: 'createtime', title: '日期', width: 100,   align: 'center'}
     	                    	,{field: 'seriesCode', title: '专辑code', width: 100,   align: 'center'}
     	                    	,{field: 'seriesName', title: '专辑名称', width: 100,   align: 'center',
     	                    		formatter: function(value) { 
		                    			 var name =decodeURI(value);
		                                 return name;     
									}
     	                    	}
     	                    	,{field: 'playtime', title: '播放时长（min）', width: 100,   align: 'center'}
     	                    	,{field: 'playcount', title: '播放次数', width: 100,   align: 'center'}
     	                    	,{field: 'playusercount', title: '点播专辑人数', width: 100,   align: 'center'}
     	                    	,{field: 'channel', title: '所属频道', width: 100,   align: 'center'}
     	                    	,{field: 'channleName', title: '频道名称', width: 100,   align: 'center',
     	                    		formatter: function(value) { 
		                    			 var cname =decodeURI(value);
		                                 return cname;     
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
     	
     	 //导出操作 
        	 var  exports=$.extend($.fn.domain.Sysbtn2,{
             	title:"导出excel",
            	text:"导出excel",
            	iconCls:"icon-export",
        		handler: function() {
        			var queryParams = $form.serialize();
                    var url = "${pageContext.request.contextPath}/user/seriescount/export?"+ queryParams;
                    window.location.href = url;
                } 
        	});
     	 
        /* //手动获取开始日期的数据操作 
        	 var  download=$.extend($.fn.domain.importBtn,{
             	title:"汇集数据",
            	text:"汇集数据",
            	iconCls:"icon-export",
        		handler: function() {
        	        alert("将导入开始日期当天的专辑统计数据");
        			var queryParams = $form.serializeArray();
        			if(queryParams[2].value  == '' || queryParams[2].value.length == 0){
        	            top.$.messager.alert("信息", "请选择手动导入专辑数据的开始时间", "info", null, 2000);
        	            return false;
        			}else{
                    var url = "${pageContext.request.contextPath}/user/bbbbb?"+$form.serialize();
                    window.location.href = url;
        				
        			}
                } 
        	}); */
        
        	function downloadseries(){
        		if (confirm("将导入开始日期当天的专辑统计数据")) {  
        			var queryParams = $form.serializeArray();
        			if(queryParams[4].value  == '' || queryParams[4].value.length == 0){
        				top.$.messager.alert("信息", "请选择手动导入专辑数据的开始时间", "info", null, 2000);
        				return false;
        			}else{
        				var url = "${pageContext.request.contextPath}/user/bbbbb?"+$form.serialize();
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
