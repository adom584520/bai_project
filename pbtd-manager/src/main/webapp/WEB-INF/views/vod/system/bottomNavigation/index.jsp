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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
      <form action="post" id="formQuery">
      <span class="property">
         <label class="">名称：</label>
	       <input type="text" id="name" name="name" style="width:200px;"/>
      </span>
      <span class="property"> <label class="">类型：</label>
			<input id="type" name="type"  style="width: 80px;" type="text" />
	  </span>
                <a href="javascript:getnavigation()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
        </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="navigation"></table>
        </div>
         <div id="dd" title="My Dialog"  class="easyui-window" title="My Window" closed="true" style="width:600px;height:480px;padding:5px;"></div> 
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getnavigation() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#navigation');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '底部导航图片管理',
                    	url: '${pageContext.request.contextPath}/vod/system/bottomnavigation/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								{field: 'id', title: 'id', width: 100,sortable: true,  align: 'center', hidden:true}
								,{field: 'type', title: '类型', width: 100, align: 'center', hidden: false}
								,{field: 'name', title: '名称', width: 100, align: 'center', hidden: false}
								,{field: 'imgNor', title: '未点击图片', width: 100, align: 'center', hidden: false,
									formatter: function(imgNor,row,index){
										if ('' != imgNor && null != imgNor) {
											imgNor = '<img style="width:30px; height:30px" src="${maprealm.imgtitle}'+ imgNor + '">';
											return imgNor;
										}
									}
								}
								,{field: 'imgSelect', title: '已点击图片', width: 100, align: 'center', hidden: false,
									formatter: function(imgSelect,row,index){
										if ('' != imgSelect && null != imgSelect) {
											imgSelect = '<img style="width:30px; height:30px" src="${maprealm.imgtitle}'+ imgSelect +'">';
											return imgSelect;
										}
									}
								}
								,{field: 'madeNor', title: '操作未点击图片', width: 100, align: 'center', hidden: false,
									formatter:function(value,row){
										var a="<a href='javascript:upload1("+row.id+")'>上传图片</a>";
										return a
									}
								}
								,{field: 'madeSelect', title: '操作已点击图片', width: 100,  align: 'center', hidden: false,
									formatter:function(value,row){
										var a="<a href='javascript:upload2("+row.id+")'>上传图片</a>";
										return a
									}	
								}
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
                $table = $("#navigation");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getnavigation();
              
            });       	
        	 function upload1(id){
         		var picname="bottomnavigation_imgNor";
          		$(parent).domain("openDialog", { 
      	        	iconCls: "icon-view", 
      	        	title: "上传", 
      	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
      	        	width: 450, 
     	        	height: 250,
     	        	success:function(data){
     	        		if(data.success){    	        			
     	        		}
     	        	},
      	        	onClose: function() { 
      	        		getnavigation();
      	            }
     	        	
      	        });
          	}
        	 function upload2(id){
          		var picname="bottomnavigation_imgSelect";
           		$(parent).domain("openDialog", { 
       	        	iconCls: "icon-view", 
       	        	title: "上传", 
       	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
       	        	width: 450, 
      	        	height: 250,
      	        	success:function(data){
      	        		if(data.success){
      	        		}
      	        	},
       	        	onClose: function() { 
       	        		getnavigation();
       	            }
       	        });
           	}

            	
        </script>
    </body>
</html>
