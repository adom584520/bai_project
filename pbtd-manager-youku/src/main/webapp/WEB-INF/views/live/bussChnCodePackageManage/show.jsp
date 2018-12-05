<!--
        *  管理页面
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>版本管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
</head>
<body class="easyui-layout" style="visibility: hidden">
	<div data-options="region:'north',border:false,split:true"
		style="padding: 0px; border-bottom: 1px so lid #99BBE8; height: 38px; width: auto;">
		<form id="formQuery" style="margin: 0; padding: 0" action=""
			method="post">
			<span class="property"> <label class="">频道：</label> <input
				type="text" id="chncode" name="chncode" style="width: 200px;" />
			</span> <a href="javascript:getVodChannels()" id="btnSearch"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding: 0px;">
		<table id="vodchannels"></table>
	</div>
	<script type="text/javascript"
		src="/js/common/scripts/jquery-1.8.0.min.js"></script>
	<script type="text/javascript"
		src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
	<script type="text/javascript"
		src="/js/common/scripts/jquery-domain.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>

	<script type="text/javascript"> 
            function getVodChannels() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',sys1Btn];
                	t.domain('datagrid', {
                        title: '商家频道管理',
                    	url: '${pageContext.request.contextPath}/live/BussChnCodePackage/page/${bussId}',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"channelid",
                        columns: [[
                            {field: 'bussid', title: '商家编号', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'bussname', title: '商家名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'channelid', title: '频道id', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chncode', title: '可看频道', width: 230, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'createtime', title: '创建时间', width: 150, sortable: true, align: 'center', hidden: false,
		                    	formatter: function(value) { 
		                   			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                return datetime;     
								}}
	                    	,{field: 'updatetime', title: '修改时间', width: 150, sortable: true, align: 'center', hidden: false,
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
        	                    subject: '频道管理',
        	                    onClickRow:function(index,row){
        	                    	$table.datagrid('unselectAll');
        	                    	$table.datagrid('selectRow', index).datagrid('beginEdit', index);
        	                    },
        	                    isHeaderMenu:false
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
                $table = $("#vodchannels");
                /* document.getElementById("bussId").value="${bussId}";  */
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getVodChannels();
              
            });
        	
        	
        	
        	$.fn.domain.create={
        	    	id: "btnCreate",
        	        text: "新建",
        	        iconCls: "icon-add",
        	        disabled: false,
        	        handler: function(){ 
        	        	var t = $(this).parent().next().find(">table:hidden");
        	            var opts = t.datagrid("options");
        	            var title = opts.title || opts.subject;
        	            var url = "${pageContext.request.contextPath}/live/BussChnCodePackage/create/${bussId}";
        	            $(parent).domain("openDialog", {
        	            	iconCls: "icon-add", 
        	            	title: "新建 " + title, 
        	            	src: url, 
        	            	width: opts.openWidth+120, 
        	            	height: opts.openHeight+150, 
        	            	onClose: function() { 
        	            		getVodChannels();
        	                }
        	            });
        	        },
        	        scope: "all"
        	    }; 
        	
        	//删除
       	 var  sys1Btn=$.extend($.fn.domain.del,{
            	title:"删除",
           	text:"删除",
       		handler: function() {
       			var nodes =  $table.datagrid("getSelections");
       	    	if (!nodes || nodes.length == 0) {
       	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
       	            return;
       	        }
       	    	var ids="";
       	    	var cpcode=false;
          	        for (var i = 0; i < nodes.length; i++) {
       	            ids+=","+nodes[i]["channelid"];
       	            
       	        }
          	      var m = "即将删除 <font style='color:red'>"+ nodes.length+"</font> 条数据，且不能恢复，确定吗？";
          	      top.$.messager.confirm("确认", m, function(result) {
                      if (result) {
          	     		deletes(ids.substr(1,ids.length));
                      }
          	      });
               },
               scope:"one,more"
       	});
       	 function deletes(ids){
       		 var  url= '${pageContext.request.contextPath}/live/BussChnCodePackage/deletes';	  	
       		 var ss = ${bussId};
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:{id:ids,bussid:ss},
	               	dataType:"JSON",
	               	success:function(result){
	               			$.messager.alert("信息","删除成功！","info",function(){
	               				$table.datagrid("load");
	               			});
	               	},
	               	error:function(XHR, status, errorThrow){
	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
	               	}
       	   })
       	 }

        </script>
</body>
</html>
