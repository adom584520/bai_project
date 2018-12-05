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
        <style type="text/css">
        a.l-btn span span.l-btn-text{
        width: 60px
        }
        
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            	<span class="property">
            		<label class="">分类：</label>
	            		<select id="type" name="type"  style="width: 120px;" class="chzn-select">
							<option value="">--请选择--</option>
							<c:forEach var="map" items="${labeltypelist }">
							<option value="${map.id }"<c:if test="${type eq  map.id}">selected="selected"</c:if> >${map.name }</option>
							</c:forEach>
						</select>
					</span>
					<span class="property">
	            		<label class="">名称：</label>
	            		<input type="text" id="name" name="name" style="width:200px;"/>
	            	</span>	
				 <span class="property"> <label class="">状态：</label>
						<select id="status" name="status"  style="width: 80px;" class="chzn-select">
						<option value="">--请选择--</option>
						<option value="0">下线</option>
						<option value="1">上线</option>
					</select>
				 </span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodlabel"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	  var channel="${channel}";
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodlabel');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtn];
                	t.domain('datagrid', {
                        title: '标签管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/Vodlabel/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								 {field: 'type', title: '分类标识', width: 100, sortable: true, align: 'center', hidden: false}
							    ,{field: 'typename', title: '分类名称', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'sequence', title: '排序', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'create_time', title: '创建时间', width: 100, sortable: true, align: 'center', hidden: false,
								    	formatter: function(value) { 
											 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
								            return datetime;     
									}}
								,{field: 'update_time', title: '更新时间', width: 100,  align: 'center', hidden: false,
									formatter: function(value) { 
										 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
								         return datetime;     
								}}
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
									     subject:'标签信息'
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
                $table = $("#vodlabel");
                var channel ="${channel}";
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
	          
	 	  		 
	            //绑定标签
	        	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
	                    title:"确认",
	                   	text:"确认",
	                   	iconCls: "icon-view", 
	               		handler: function() {
	               			var nodes = $table.datagrid("getSelections");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要绑定信息", "info", null, 2000);
	               	            return;
	               	        } 
	               	    	var ids = "";
	               	    	var labeltype = "";
	               	    	var names = [];
	               	        var channel="${channel}";
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            ids+=","+nodes[i]["id"];
	            	            labeltype+=","+nodes[i]["type"];
	            	           
	            	        }
	               	          $.ajax({
								url : "${pageContext.request.contextPath}/vod/tv/Vodchannel/addbindinglabel",
								type : "GET",
								dataType : "JSON",
								data : {channel :channel,id :ids.substr(1,ids.length),labeltype:labeltype.substr(1,ids.length)},
								async : false,
								success : function() {
									$(top).domain('closeDialog');
								},
								error : function(XHR,status,errorThrow) {
									top.$.messager.alert("信息","操作失败！","info",null,2000);
								},
								onClose: function() { 
								}
							});
	                       },
	                       scope:"one,more"
	               	});
        </script>
    </body>
</html>
