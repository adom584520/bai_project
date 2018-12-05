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
         <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:70px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
			<span class="property"> <label class="">手机号：</label> <input
				type="text" id="mobile" name="mobile" style="width: 200px;" />
			</span>
			 <span class="property" > <label class="">查询起止时间：</label>
			  <input id="starttime" name="starttime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd  HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="endtime" name="endtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd  HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            <hr>
            <label class="">&nbsp;&nbsp;&nbsp;&nbsp;  提示：请输入手机号查询~~! &nbsp;&nbsp;未输入日期；则默认查询昨日数据</label>
			</span>
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
                	var customToolbar = [$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '用户数据统计',
                        url: '${pageContext.request.contextPath}/user/mobilecount/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
      	                    	 {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
     	                    	,{field: 'createTime', title: '播放专辑日期', width: 100,   align: 'center', hidden: false,
    	                    		formatter: function(value) { 
    	                    			var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
    	                                return datetime;     
    	                    		//return $.fmt.formatDate(value, 'yyyy-MM-dd');
    								}}
     	                    	,{field: 'userId', title: '手机号', width: 100,   align: 'center'}
     	                    	,{field: 'seriesCode', title: '专辑code', width: 100,   align: 'center'}
     	                    	,{field: 'seriesName', title: '专辑名称', width: 100,   align: 'center',
     	                    		formatter: function(value) { 
		                    			 var name =decodeURI(value);
		                                 return name;     
									}}
     	                    	,{field: 'playcount', title: '播放次数', width: 100,   align: 'center'}
     	                    	,{field: 'channellist', title: '所属频道', width: 100,   align: 'center'}
     	                    	,{field: 'channelName', title: '频道名称', width: 100,   align: 'center',
     	                    		formatter: function(value) { 
		                    			 var name =decodeURI(value);
		                                 return name;     
									}}
    	                    	,{field: 'opt',  title: '操作', width: 230,  align: 'center', hidden: false,formatter: getBtn}

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
     	
         	 function getBtn(value, row, index, text){
			     var ckButton='';
			    	 ckButton = "<button  onclick='show(\""+row.seriesCode+"\")' class='easyui-linkbutton'>查看剧集点播</button>";
			     return ckButton;
	   		}
         	 
          	 function show(seriesCode){
     			//var queryParams = $form.serialize();
     			var queryParams = $form.serializeArray();	
     			var mobile = queryParams[0].value;
     			var starttime = queryParams[1].value;
     			var endtime = queryParams[2].value;
     			
     			$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/user/mobileseriescount/page/"+seriesCode+"?mobile="+mobile+"&starttime="+starttime+"&endtime="+endtime,
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	            }
	     	        });
	         	}
        
        </script>
    </body>
</html>
