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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post">
              <span class="property">
					<label class="">运营位名称：</label>       		    
	            		<select id="position_type" name="position_type"  style="width: 120px;" class="position-select">		
						<option value="">--请选择--</option>
						<c:forEach var="map" items="${positionlist}">
							<option value="${map.position_type}"<c:if test="${position_type eq  map.position_type}">selected="selected"</c:if> >${map.position_name}</option>
							</c:forEach>
						</select>
					</span>
					<span class="property">
	            		<label class="">运营位位置：</label>
	            		<input type="text" id="position" name="position" style="width:200px;"/>
	            	</span>	
             <a href="javascript:getTypes()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="laucherposition"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>     
        <script type="text/javascript"> 
            function getTypes() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#laucherposition');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '运营位打点类型',
                    	url: '${pageContext.request.contextPath}/launcher/position/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								 {field: 'id', title: 'id', width: 100, sortable: true, align: 'center', hidden: true}
								 ,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'position_type', title: '运营类型', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'position_name', title: '运营位名称', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'position', title: '运营位位置', width: 100, sortable: true, align: 'center', hidden: false}
								]],
									onLoadSuccess: function(data, status, XHR) {
								},
									onLoadError: function(XHR, status, errorThrow) {
								},
									names: [
									     ],
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
                $t = $("#laucherposition");
               //加载数据
                if($.fn.domain.defaults.datagrid.auto) getTypes();
            });
        </script>
    </body>
</html>
