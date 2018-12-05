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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:36px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
       <span class="property"> <label class="">频道：</label> <select
				id="channelCode" name="channelCode" style="width: 120px;"
				class="chzn-select">
					<option value="">--请选择--</option>
					<c:forEach var="map" items="${channellist }">
						<option value="${map.channelCode }"
							<c:if test="${channelCode eq  map.channelCode}">selected="selected"</c:if>>${map.channelName }</option>
					</c:forEach>
			</select>
			</span> 
            	<span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			</span>
            		<span class="property"> <label class="">名称：</label> <input
				type="text" id="seriesName" name="seriesName" style="width: 200px;" />
			</span>
			    <a href="javascript:getalbumin()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodpaidalbum"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getalbumin() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodpaidalbum');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [sysBtn,'-',$.fn.domain.del,'-',sysBtns5];
                	t.domain('datagrid', {
                        title: '专辑收费管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/Vodpaidalbum/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: true}
								,{field: 'seriesCode', title: '专辑标识', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'seriesName', title: '名称', width: 140,   align: 'left', hidden: false}
		                    	,{field: 'writerName', title: '导演', width: 60,  align: 'left', hidden: false}
		                    	,{field: 'actorName', title: '演员', width: 120,  align: 'left', hidden: false}
		                    	,{field: 'ChannelName', title: '频道', width: 100,  sortable: true,align: 'center', hidden: false}
		                    	,{field: 'labelName', title: '标签', width: 120,  align: 'left', hidden: false}
		                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
		                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
								,{field: 'create_time', title: '添加时间', width: 100,  align: 'center', hidden: false,
									formatter: function(value) { 
										 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
								         return datetime;     
								}}
								]],
									onLoadSuccess: function(data, status, XHR) {
								},
									onLoadError: function(XHR, status, errorThrow) {
								},
									names: [
									     ],
									     subject:'专辑收费信息'
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
                $table = $("#vodpaidalbum");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getalbumin();
              
            });
        	
        	//绑定专辑
         	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
                    	title:"添加节目",
                    	text:"添加节目",
                    	iconCls: "icon-view", 
                		handler: function() {
                			$(parent).domain("openDialog",{
     	     	        	iconCls: "icon-view", 
     	     	        	title: "添加", 
     	     	      	 	src: "${pageContext.request.contextPath}/vod/phone/vodSpecial/addalbuminfo/0/",
     	     	        	width: 1250, 
     	    	        	height: 650,
     	     	        	onClose: function(){
     	     	        		var ids = top.$.data(top.document.body, "choose.ids");
     	     	        		$.ajax({
     	                           	url:"${pageContext.request.contextPath}/vod/phone/Vodpaidalbum/addAll",
     	                           	type:"GET",
     	                           	dataType:"JSON",
     	                           	data:{albumid:ids},
     	                           	async:false,
     	                           	success:function(){
     	                           	getalbumin();		
     	                           	},
     	                           	error:function(XHR, status, errorThrow){
     	                           		
     	                           	}
     	                           });
     	     	        		getalbumin();
     	     	            }
     	     	        });
                        },
                        scope:"one,more"
                	});
        	//同步
       	 var  sysBtns5=$.extend($.fn.domain.Btnsys5,{
            title:"同步",
           	text:"同步",
           	iconCls:"icon-reset",
       		handler: function() {
       	      	$.ajax({
                      	url:"${pageContext.request.contextPath}/phonereset/phonepidalbuminfo",
                      	type:"GET",
                      	dataType:"JSON",
                      	data:{},
                      	async:false,
                      	success:function(msg){
                      	 top.$.messager.alert("信息", "同步成功！", "info", null, 2000);
           	            return;
                      	},
                      	error:function(XHR, status, errorThrow){
                      		
                      	}
                      });
               },
               scope:"all"
       	});
     	 
        </script>
    </body>
</html>
