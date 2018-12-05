<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	width: 28px;
}
button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	background-color:;
}
button:hover {
	text-decoration: none;
}
button:active {
	position: relative;
	top: 1px;
}
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:36px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
		            <input type="hidden" id="show_id" name="show_id"/>
		             <span class="property"> <label class="">标识：</label> <input
						type="text" id="video_id" name="video_id" style="width: 120px;" />
					</span>
		            <span class="property"> <label class="">名称：</label> <input
						type="text" id="title" name="title" style="width: 120px;" />
					</span>
		            <span class="property"> <label class="">剧集：</label> <input
						type="text" id="video_stage" name="video_stage"  style="width: 90px;" />
					</span>
					<span class="property"> <label class="">是否正片：</label> 
							<select id="video_type" name="video_type"  style="width: 90px;">
							    <option value="">全部</option>
								<option value="正片">是</option>
							</select>
					</span>
					<span class="property"> <label class="">是否收费：</label> 
							<select id="paid" name="paid"  style="width: 90px;">
							    <option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
					</span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodvideo"></table>
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
                var t = $('#vodvideo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar ='';
                	t.domain('datagrid', {
                        title: '剧集数据',
                    	url: '${pageContext.request.contextPath}/youku/albums/showvideopage',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"video_id",
                        columns: [[
	                    	{field: 'video_id', title: '唯一标识', width: 120,   align: 'center', hidden: false}
	                    	,{field: 'title', title: '名称', width: 80,   align: 'left', hidden: false,formatter: gettitle}
	                    	,{field: 'video_stage', title: '剧集', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'video_type', title: '类型', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'paid', title: '是否收费', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			if(value=="1"){
	                    				return '是';
	                    			}else{
	                                 return '否';     
	                    			}
								}}
	                    	,{field: 'create_time', title: '更新日期', width: 100,  align: 'center', hidden: false,}
	                    	,{field: 'updatetime', title: '更新日期', width: 100,  align: 'center', hidden: false}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '剧集管理',
        	                    openWidth:document.body.clientWidth+20,
        		                openHeight:parent.document.body.clientHeight-20
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
                $table = $("#vodvideo");
                document.getElementById("show_id").value="${show_id}"; 
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	 function gettitle(value, row, index, text){
            	 var   ckButton = "<a href='#' onclick='show(\""+row.video_id+"\")'>"+value+"</a>";
            	 return ckButton
            } 
	        	     
	        	     function show(id){
	        	 		$(parent).domain("openDialog", { 
	        	         	iconCls: "icon-view", 
	        	         	title: "查看", 
	        	         	src: "${pageContext.request.contextPath}/youku/albums/showvideo/"+id,
	        	         	width: 760, 
	        	         	height: 450,
	        	         	onClose: function() { 
	        	             }
	        	         });
	        	 	} 
	        	  
	        	 
        </script>
    </body>
</html>
