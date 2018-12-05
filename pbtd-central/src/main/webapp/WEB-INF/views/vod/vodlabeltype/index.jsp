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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:34px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
          <!--   <span class="property"> <label class="">下发时间：</label>
					 <input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			 </span> -->
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodlabeltype"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getvodactors() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodlabeltype');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-'];
                	t.domain('datagrid', {
                        title: '标签管理',
                    	url: '${pageContext.request.contextPath}/integrate/vodlabeltype/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								{field: 'id', title: '标识', width: 100, align: 'center', hidden: false}
								,{field: 'name', title: '名称', width: 100, align: 'center', hidden: false}
								,{field: 'sequence', title: '排序', width: 100, align: 'center', hidden: false}
								,{field: 'num', title: '数字前缀', width: 100, align: 'center', hidden: false}
								,{field: 'update_time', title: '更新时间', width: 100,  align: 'center', hidden: false,
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
									     subject:'标签分类信息'
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
                $table = $("#vodlabeltype");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
              
            });       
     	 
        </script>
    </body>
</html>
