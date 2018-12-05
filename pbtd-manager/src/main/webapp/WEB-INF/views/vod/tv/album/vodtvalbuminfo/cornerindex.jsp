<!--
        *  管理页面
        * 
        * @author admin
        *
-->
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
    </head>
    <body class="easyui-layout" style="visibility:hidden">
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodCorner"></table>
        </div>
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
                var t = $('#vodCorner');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [sysBtn,'-'];
                	t.domain('datagrid', {
                        title: '角标绑定',
                    	url: '${pageContext.request.contextPath}/vod/vodCorner/albuminfo/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'name', title: '名称', width: 100, sortable: true, align: 'center'}
	                    	,{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'imgurl', title: '图片', width: 100, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
								 if(value=="1"){
		                   				return "<span style='color:green'>上线</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>下线</span>";
		                   			 }
                            
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
                    t.datagrid("load", queryParams);
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
                $table = $("#vodCorner");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });

       	 function  getimgurl(e){
    	    	return "<img  style='width:80px;height:40px;'  src=${maprealm.imgtitle}"+e+"  />";
    	    }
    		
         	function updateStatus(ids,status){
         		$.ajax({
                   	url:"${pageContext.request.contextPath}/vod/vodCorner/updateStatus",
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{status:status,id:ids},
                   	async:false,
                   	success:function(){
                 		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
                 		$("#vodCorner").datagrid("load",{});               		
                 	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   }); 
         	}
         	
         	//绑定角标
       	 	var  sysBtn=$.extend($.fn.domain.Btnsys,{
                   title:"绑定",
                  	text:"绑定",
                  	iconCls: "icon-view", 
              		handler: function() {
              			var nodes =  $table.datagrid("getSelections");
              	    	if (!nodes || nodes.length == 0) {
              	            top.$.messager.alert("信息", "请您选择需要绑定信息", "info", null, 2000);
              	            return;
              	        } 
              	   var idz ="";
                	 for (var i = 0; i < nodes.length; i++) {
       	            idz+=nodes[i]["id"]+",";
       	        }
                	idz=idz.substr(0,idz.length-1);
	     	       $.ajax({
	                   url:"${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updateAlbumCorner",
	                   type:"POST",
	                   dataType:"JSON",
	                   data:{albumid:"${ids}",cornerid:idz},
	                   async:false,
	                   success:function(){
	                   $.messager.alert("信息", "操作成功！", "info", null, 2000);
	                   $("#vodSpecial").datagrid("load",{});               		
	                 },
	              error:function(XHR, status, errorThrow){
	                    }
	               });
	     	      $(top).domain('closeDialog');     	       
                 },
                 scope:"one"
         	});

        </script>
    </body>
</html>
