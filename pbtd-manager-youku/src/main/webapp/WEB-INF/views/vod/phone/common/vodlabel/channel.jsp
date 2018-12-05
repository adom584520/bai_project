<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 40px;
}
</style>
</head>
<body class="easyui-layout" style="visibility: hidden">
	<div data-options="region:'north',border:false,split:true"
		style="padding: 0px; border-bottom: 1px solid #99BBE8; height: 34px; width: auto;">
		<form id="formQuery" style="margin: 0; padding: 0" action=""
			method="post">
			<span		class="property">   <input
				type="radio" name="flag" checked="checked" value="phone" onchange="getinfo();">手机频道 <input
				type="radio" name="flag" value="tv" onchange="getinfo();">tv频道
			</span>  
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding: 0px;">
		<table id="vodchannel"></table>
	</div>
	<script type="text/javascript"		src="/js/common/scripts/jquery-1.8.0.min.js"></script>
	<script type="text/javascript"		src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"		src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
	<script type="text/javascript"		src="/js/common/scripts/jquery-domain.min.js"></script>
	<script type="text/javascript"		src="/js/common/jquery-easyui-1.3.1/common.js"></script>

	<script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
            	 var flag="";
            	 var obj = document.getElementsByName("flag");  
                 for(i=0; i<obj.length;i++)  {
                 if(obj[i].checked)  { 
                	 flag= obj[i].value; 
                 } 
               } 
            	 var url='${pageContext.request.contextPath}/vod/phone/Vodlabel/pagechannel';
                var t = $('#vodchannel');
                	var customToolbar = [sysBtnup,'-',sysBtn2];
                	t.domain('datagrid', {
                        title: '频道',
                    	url:url ,
                    	queryParams: {type:flag,label:"${label}"},
                    	toolbar: customToolbar,
                    	idField:"id",
                        columns: [[
	                    	{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'channel', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'channelName', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'type', title: '类型', width: 100, sortable: true, align: 'center', hidden: false}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
         	                    subject:'频道信息'
        	                     
                    });
               
            }
        	
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#vodchannel");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	 //添加
       	 var  sysBtnup=$.extend($.fn.domain.btnup,{
            title:"添加",
           	text:"添加",
           	iconCls:"icon-ok",
       		handler: function() {
       			$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "绑定", 
     	        	src: "${pageContext.request.contextPath}/vod/phone/Vodlabel/addchannel/${label}",
     	        	width: 750, 
    	        	height: 450,
     	        	onClose: function() {
     	        		getinfo();
     	            }
     	        });
               },
               scope:"all"
       	});
       //删除绑定
    	 var  sysBtn2=$.extend($.fn.domain.del,{
                title:"删除",
               	text:"删除",
               	iconCls: "icon-remove", 
           		handler: function() {
           			var nodes =  $table.datagrid("getSelections");
           	    	if (!nodes || nodes.length == 0) {
           	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
           	            return;
           	        } 
           	    	$.messager.confirm('确认','确认删除？',function(r){
           	    		if(r){
           	    			var ids = "";
	               	    	var names = [];
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            ids+=","+nodes[i]["id"];
	            	        }
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/phone/Vodlabel/deletelabelchannel",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{ids:ids.substr(1,ids.length)},
   	                           	async:false,
   	                           	success:function(){
   	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
   	                         		 getinfo();              		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           	}
   	                           });
   	     	        		 getinfo();
           	    		}
           	    	});

                   },
                   scope:"one,more"
           	});
    	
        </script>
</body>
</html>
