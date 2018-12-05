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
<title>节目单管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
	
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
			<span class="property"> <label class="">频道名：</label> <input
				type="text" id="chncode" name="chncode" style="width: 200px;" />
			</span>
			 <a href="javascript:getVodChannels()" id="btnSearch"
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
                	var customToolbar = [$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '节目包管理',
                    	url: '${pageContext.request.contextPath}/live/Package/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"packageid",
                        columns: [[
	                    	{field: 'packageid', title: '节目包编号', width: 80, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packagename', title: '节目包名称', width: 170, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chncode', title: '节目播放频道', width: 110, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'starttime', title: '节目开始时间', width: 110, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'endtime', title: '节目结束时间', width: 110, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packageorder', title: '节目包排序', width: 80, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packagecover', title: '节目封面', width: 270, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packageposter', title: '节目海报', width: 250, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packagecode', title: '节目包code', width: 240, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'packagestats', title: '节目包状态', width: 90, sortable: true, align: 'center', hidden: false,formatter: getBtn}
	                    	,{field: 'status',  title: '操作', width: 160,  align: 'center', hidden: false,formatter: getBtns}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '节目包管理',
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
			     if(value==1){
			    	 ckButton = "<span style='color: green;'>上线</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>下线</span>";
			     }
			     return ckButton;
	   		}
        	 
        	 

       	  function getBtns(value, row, index, text){
       		 var ckButton='';
		    	 ckButton = "<button  onclick='show(\""+row.packageid+"\")' class='easyui-linkbutton'>查看剧集</ >";
		     return ckButton;
	   		} 
       	  
       	 function show(code){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/live/Package/show/"+code,
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	            }
	     	        });
	         	}
        </script>
</body>
</html>
