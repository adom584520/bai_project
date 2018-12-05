<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" pageEncoding="UTF-8" %>
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
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="cpsource"></table>
        </div>
  		<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       
        <script type="text/javascript">
            function getcpsource() {
                var queryParams = {};
                var t = $('#cpsource');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        //title: ' ',
                    	url: '${pageContext.request.contextPath}/integrate/cpsource/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'id', title: ' ', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'code', title: 'code', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false, formatter: getstatusBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
	                    ],
	                    subject: ' '
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
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getcpsource();
            });
        	
        	function getstatusBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.status;
			     if(status=="1"){
			    	 ckButton = "<span style='color: green;'>启用</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>禁用</span>";
			     }
			     return ckButton;
	   		}
        	
        </script>
    </body>
</html>
