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
		style="padding: 0px; border-bottom: 1px so lid #99BBE8; height:  2px; width: auto;">
	</div>
	<div data-options="region:'center',border:false" style="padding: 0px;">
		<table id="vodchannels"></table>
	</div>
	<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
	<script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>

	<script type="text/javascript"> 
            function getVodChannels() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
            	 var id = $.query.getId();
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined'){
                	var customToolbar = [sys1Btn];
                	t.domain('datagrid', {
                        title: '未新增频道',
                    	url: '${pageContext.request.contextPath}/live/BussChnCodePackage/pages/${bussId}',
                    	queryParams: queryParams,
                    	toolbar: customToolbar, 
                    	idField:"channelid",
                        columns: [[
                            {field: 'channelid', title: '频道id', width: 90, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chncode', title: '可看频道', width: 120, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'chnname', title: '频道名称', width: 130, sortable: true, align: 'center', hidden: false}
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

        	
        	//删除
          	 var  sys1Btn=$.extend($.fn.domain.del,{
               	title:"新增",
              	text:"新增",
          		handler: function() {
          			var nodes =  $table.datagrid("getSelections");
          	    	if (!nodes || nodes.length == 0) {
          	            top.$.messager.alert("信息", "请您选择需要添加的信息", "info", null, 2000);
          	            return;
          	        }
          	    	var ids="";
          	    	var cpcode=false;
             	        for (var i = 0; i < nodes.length; i++) {
          	            ids+=","+nodes[i]["channelid"];
          	            
          	        }
             	      var m = "新增 <font style='color:red'>"+ nodes.length+"</font> 条数据，确定吗？";
             	      top.$.messager.confirm("确认", m, function(result) {
                         if (result) {
             	     		creates(ids.substr(1,ids.length));
                         }
             	      });
                  },
                  scope:"one,more"
          	});
          	 function creates(ids){
          		 var  url= '${pageContext.request.contextPath}/live/BussChnCodePackage/creates';	  	
          		 var ss = ${bussId};
   	  			 $.ajax({
   	               	url:url,
   	               	type:"POST",
   	               	data:{id:ids,bussid:ss},
   	               	dataType:"JSON",
   	               	success:function(result){
   	               			$.messager.alert("信息","新增成功！","info",function(){
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
