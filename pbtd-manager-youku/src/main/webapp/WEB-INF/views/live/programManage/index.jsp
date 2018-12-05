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
			<label class="">节目开始日期：</label>
			<input type="text" id="starttime"  name="starttime"    type="text"  style="width: 160px;"  data-options=" required:true" onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>
			
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
                        title: '商家栏目管理',
                    	url: '${pageContext.request.contextPath}/live/Program/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"programid",
                        columns: [[
	                    	{field: 'programid', title: '节目单编号', width: 50, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'source', title: '节目来源', width: 50, sortable: true, align: 'center', hidden: false,formatter: getBtn}
	                    	,{field: 'starttime', title: '节目开始时间', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'endtime', title: '节目结束时间', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chncode', title: '节目频道', width: 120, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'programname', title: '节目名', width: 280, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'realtime', title: '节目播放时间', width: 200, sortable: true, align: 'center', hidden: false}
	                 	 	,{field: 'createtime', title: '创建时间',	 width: 100, sortable: true, align: 'center', hidden: false}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '节目单管理',
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
			     var status = row.source;
			     if(status=="1"){
			    	 ckButton = "<span style='color: green;'>爬虫</span>";
			     }else{
			    	 ckButton = "<span style='color: green;'>接口</span>";
			     }
			     return ckButton;
	   		}
        </script>
</body>
</html>
