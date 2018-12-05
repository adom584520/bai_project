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
<title>频道管理</title>
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
				type="text" id="chnname" name="chnname" style="width: 200px;" />
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
                	var customToolbar =[/* $.fn.domain.create, '-',*/$.fn.domain.edit,'-',sysExtend];
                	t.domain('datagrid', {
                        title: '商家栏目管理',
                    	url: '${pageContext.request.contextPath}/live/Channel/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"channelid",
                        columns: [[
	                    	 {field: 'sournece', title: '频道来源(0:银河)', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'channelid', title: '频道编号', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'oldchncode', title: '源频道码', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chncode', title: '频道码', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chnname', title: '频道名称', width: 80, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'groupid', title: '频道手机分组', width: 80, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'tagid', title: '频道tv分组', width: 80, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'playurl', title: '播放地址', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packagecover', title: '频道图标', width: 120, sortable: true, align: 'center', hidden: false,formatter: getimgportrait}
	                    	,{field: 'epgstatus', title: '状态', width: 100, sortable: true, align: 'center', hidden: false,formatter: getBtn}
	                 	 	,{field: 'defaultnum', title: '默认排序', width: 150, sortable: true, align: 'center', hidden: false}
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
        	 
	  		function getBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.epgstatus;
			     if(status=="0"){
			    	 ckButton = "<span style='color: green;'>上线</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>下线</span>";
			     }
			     return ckButton;
	   		}
	  		
	  		 function  getimgportrait(e){
     	    	return "<img  style='width:80px;height:40px;'  src="+e+"  />";
     	    }
	  		 
	  		 //操作下发
           	 var  sysExtend=$.extend($.fn.domain.Btnsys,{
                	title:"同步",
               	text:"同步",
               	 iconCls:"icon-ok",
           		handler: function() {
           	    	var  id= 0;
           	    	dataExtend(id)
                   },
           	});
           	 
        	 function dataExtend(id){
     		  	$.ajax({
                    	url:"${pageContext.request.contextPath}/live/DataExtend/channel",
                    	type:"GET",
                    	dataType:"JSON",
                       	async:false,
                       	success:function(msg){
                       	 top.$.messager.alert("信息", "同步成功！", "info", null, 2000);
            	            return;
                       	},
                    	error:function(XHR, status, errorThrow){
                    		
                    	}
                    }); 
     	 }
        </script>
</body>
</html>
