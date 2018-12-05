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
<title>频道tv分组管理</title>
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
			<span class="property"> <label class="">名称：</label> <input
				type="text" id="groupname" name="groupname" style="width: 200px;" />
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

	<script type="text/javascript"> 
            function getVodChannels() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '商家栏目管理',
                    	url: '${pageContext.request.contextPath}/live/Group/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"groupid",
                        columns: [[
	                    	 {field: 'groupid', title: '分组编号', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'groupname', title: '分组名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'grouporder', title: '分组顺序', width: 100, sortable: true, align: 'center', hidden: false}
	                 	 	,{field: 'createtime', title: '创建时间', width: 150, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'updatetime', title: '修改时间', width: 150, sortable: true, align: 'center', hidden: false}
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

                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getVodChannels();
              
            });
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.status;
			     if(status=="1"){
			    	 ckButton = "<span style='color: green;'>上线</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>下线</span>";
			     }
			     return ckButton;
	   		}
        	 //批量操作状态
        	 var savestatusstartBtn=$.extend($.fn.domain.btnPhoto,{
             	title:"上线",
            	text:"上线",
        		handler: function() {
        			var nodes =  $table.datagrid("getSelections");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要上线信息", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids = [];
           	    	var names = [];
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids.push(nodes[i]["id"]);
        	        }
           	     savestatus(ids,1)
                },
                scope:"one,more"
        	});
        	 //下线
        	 var savestatusendBtn=$.extend($.fn.domain.synBtn,{
             	title:"下线",
            	text:"下线",
        		handler: function() {
        			var nodes =  $table.datagrid("getSelections");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要下线信息", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids ;
           	    	var names = [];
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids+=","+nodes[i]["id"];
        	        }
           	     savestatus(ids,1)
                },
                scope:"one,more"
        	});
        	 function savestatus(ids,type){
        		 var params = {};
	  			 params.ids = ids;
	  			 params.type = type; 
        		 var  url= '${pageContext.request.contextPath}/integrate/vodtvchannel/savestatus';	  	 
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:$.serialize(params),
	               	dataType:"JSON",
	               	async: true,
	                cache: false,
	                contentType: "application/json; charset=utf-8",
	               	success:function(result){
	               		var state = parseInt(result);
	               		if(state>0){
	               			$.messager.alert("信息","保存成功！","info",function(){
	               				$table.datagrid("load");
	               			});
	               		}
	               		else{
	               			
	               		}
	               		
	               	},
	               	error:function(XHR, status, errorThrow){
	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
	               	}
        	   })
        	 }
        	 
        	 //保存
	    	 var saveBtn = $.extend($.fn.domain.saveGrid,{
	    		iconCls:"icon-save",
	    		handler:function(){
    				var rows = $table.datagrid("getRows");
    				for(var i=0;i<rows.length;i++){
    					$table.datagrid("endEdit",i);
    				}
	    			rows = $table.datagrid("getChanges");
	    			if(rows.length==0){
	    				$.messager.alert("提示","列表数据没有发生变化！","info");
	    			}else{
	    				rows = $table.datagrid("getChanges");
	    				saveSubmit(rows,"save");
	    			}
	    		}
	    	 });
	  		 
	  		 //保存 
	  		 function saveSubmit(rows,flag){
	  			 var params = {};
	  			 params.rows = rows;
	  			 var  url= '${pageContext.request.contextPath}/integrate/vodtvchannel/save';	  	 
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:$.serialize(params),
	               	dataType:"JSON",
	               	async: true,
	                   cache: false,
	                   contentType: "application/json; charset=utf-8",
	               	success:function(result){
	               		var state = parseInt(result);
	               		if(state>0){
	               			$.messager.alert("信息","保存成功！","info",function(){
	               				$table.datagrid("load");
	               			});
	               		}
	               		else{
	               			
	               		}
	               		
	               	},
	               	error:function(XHR, status, errorThrow){
	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
	               	}
	               });
	  		 }
        </script>
</body>
</html>
