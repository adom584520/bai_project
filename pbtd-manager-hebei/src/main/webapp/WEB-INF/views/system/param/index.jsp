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
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="tables"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
                var t = $('#tables');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',sysBtns6];
                	t.domain('datagrid', {
                        title: '系统参数管理',
                        url: '${pageContext.request.contextPath}/system/param/page',
                    	toolbar: customToolbar,
                        columns: [[
	                    	 {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
	                    	,{field: 'platform', title: '参数平台', width: 100,   align: 'center',formatter: function(value) { 
	                   			 if(value=="0"){
		                   				return "<span style='color:green'>安卓</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>iOS</span>";
		                   			 }
							}}
	                    	,{field: 'keydata', title: '参数标识', width: 100,   align: 'center'}
	                    	,{field: 'keyName', title: '参数名称', width: 100,   align: 'center'}
	                    	,{field: 'valuedata', title: '参数值标识', width: 100,   align: 'center'}
	                    	,{field: 'valueName', title: '参数值名称', width: 100,   align: 'center'}
	                    	,{field: 'status', title: '上下线状态', width: 100,   align: 'center',formatter: function(value) { 
	                   			 if(value=="1"){
		                   				return "<span style='color:green'>上线</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>下线</span>";
		                   			 }
							}}
	                    	,{field: 'paramdescribe', title: '描述', width: 100,   align: 'center'}
	                    	,{field: 'createTime', title: '创建日期', width: 200,   align: 'center',
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
								}}
	                    	,{field: 'updateTime', title: '修改日期', width: 200,   align: 'center',
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
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
                    t.datagrid("load",{});
                }
            }
        	
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $table = $("#tables");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	
        //选中同步
     	   var  sysBtns6=$.extend($.fn.domain.Btnsys6,{
 	        title:"上线同步",
 	        text:"上线同步",
 	        iconCls:"icon-reset",
     		handler:function(){
     			var nodes =  $table.datagrid("getSelections");
     	    	/* if (!nodes || nodes.length == 0) {
     	            top.$.messager.alert("信息", "请您选择要同步的参数", "info", null, 2000);
     	            return;
     	        }  */
     	    	var ids = "";
        	        for (var i = 0; i < nodes.length; i++) {
     	            ids+=nodes[i]["id"]+",";
     	        }
     			$.ajax({
                   	url:"${pageContext.request.contextPath}/commonreset/restSysParam",
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{'ids':ids},
                   	async:false,
                   	success:function(){
                   		 top.$.messager.alert("信息", "同步完成！", "info", null, 2000); 
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   }); 
     		},
     		scope:"all"
     	})
        
        </script>
    </body>
</html>
