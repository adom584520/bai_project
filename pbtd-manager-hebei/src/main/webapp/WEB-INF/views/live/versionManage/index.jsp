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
			<span class="property"> <label class="">版本名：</label> <input
				type="text" id="versionname" name="versionname" style="width: 200px;" />
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
                	var customToolbar = [$.fn.domain.edit,'-',sysExtend];
                	t.domain('datagrid', {
                        title: '直播版本管理',
                    	url: '${pageContext.request.contextPath}/live/Version/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"versionid",
                        columns: [[
	                    	{field: 'versionid', title: '版本号', width: 70, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'versionname', title: '版本名称', width: 90, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'showtype', title: '台号显示类型', width: 180, sortable: true, align: 'center', hidden: false,formatter: getBtnsshowtype}
	                    	,{field: 'isnumchanage', title: '是否支持数字换台', width: 140, sortable: true, align: 'center', hidden: false,formatter: getBtns}
	                    	,{field: 'isshowlivelist', title: '是否显示直播列表', width: 140, sortable: true, align: 'center', hidden: false,formatter: getBtns}
	                    	,{field: 'isplaycontinue', title: '是否弹出播放继续框 ', width: 140, sortable: true, align: 'center', hidden: false,formatter: getBtnsisplay}
	                    	,{field: 'state', title: '状态', width: 100, sortable: true, align: 'center', hidden: false,formatter: getBtn}
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
			     var status = row.state;
			     if(status=="1"){
			    	 ckButton = "<span style='color: green;'>上线</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>下线</span>";
			     }
			     return ckButton;
	   		}
        	 function getBtns(value){
			     var ckButton='';
			     if(value =="1"){
			    	 ckButton = "<span style='color: green;'>√</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>×</span>";
			     }
			     return ckButton;
	   		}
        	 function getBtnsisplay(value){
			     var ckButton='';
			     if(value =="0"){
			    	 ckButton = "<span style='color: green;'>√</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>×</span>";
			     }
			     return ckButton;
	   		}
        	 function getBtnsshowtype(value){
			     var ckButton='';
			     if(value =="0"){
			    	 ckButton = "<span style='color: green;'>显示数字台号</span>";
			     }else if(value =="1"){
			    	 ckButton = "<span style='color: blue;'>显示频道名称</span>";
			     }else if(value =="2"){
			    	 ckButton = "<span style='color: blue;'>显示频道图标</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>数字、名称、台标都不显示</span>";
			     }
			     return ckButton;
	   		}
        	 
        	 //操作下发
           	 var  sysExtend=$.extend($.fn.domain.Btnsys,{
                	title:"同步",
               	text:"同步",
               	 iconCls:"icon-ok",
           		handler: function() {
           			var nodes =  $table.datagrid("getSelections");
           	    	if (!nodes || nodes.length == 0) {
           	            top.$.messager.alert("信息", "请您选择一条信息进进行下发操作", "info", null, 2000);
           	            return;
           	        } 
           	    	if(nodes.length>1){
           	    		 top.$.messager.alert("信息", "请只选择一条信息", "info", null, 2000);
           	    		 return;
           	    	}
           	    	var  id= nodes[0].bussid;
           	    	dataExtend(id)
                   },
                   scope:"one"
           	});
           	 
           	 
        	 function dataExtend(id){
     		  	$.ajax({
                    	url:"${pageContext.request.contextPath}/live/DataExtend/version",
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
