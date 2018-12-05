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
	             	归属模板
				<select id="masterplateid" name="masterplateid"  style="width: 120px;" class="chzn-select">
						<option value="">--请选择--</option>
						<c:forEach var="map" items="${masterplatelist }">
						<option value="${map.id }"<c:if test="${masterplateId eq  map.id}">selected="selected"</c:if> >${map.masterplatename }</option>
						</c:forEach>
					</select>
					</span>	
					<span class="property">
	            		<label class="">名称：</label>
	            		<input type="text" id="name" name="name" style="width:200px;"/>
	            	</span>	
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodinfo"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodinfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',sysBtndel];
                	t.domain('datagrid', {
                        title: '模板运营位管理',
                    	url: '${pageContext.request.contextPath}/vod/MasterplateSon/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								{field: 'id', title: '标识', width: 100,  align: 'center', hidden: true}
								,{field: 'masterplatename', title: '模板', width: 100,   align: 'center', hidden: false}
								,{field: 'name', title: '名称', width: 100,   align: 'center', hidden: false }
								,{field: 'masterplatenum', title: '运营位位置', width: 100,align: 'center', hidden: false}
								,{field: 'count',  title: '返回条数', width: 160,  align: 'center', hidden: false }
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
                $table = $("#vodinfo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	 

       	 //同步
	          	var sysBtnreset=$.extend($.fn.domain.reset,{
	          		title:'同步',
	          		text:'同步',
	          		iconCls:"icon-reset",
	          		handler:function(){
	          			$.ajax({
	                        	url:"${pageContext.request.contextPath}/commonreset/restmasterplateSon",
	                        	type:"GET",
	                        	dataType:"JSON",
	                        	data:{},
	                        	async:false,
	                        	success:function(){
	                        		 top.$.messager.alert("信息", "同步成功！", "info", null, 2000); 
	                        	},
	                        	error:function(XHR, status, errorThrow){
	                        		
	                        	}
	                        }); 
	          		},
	          		scope:"all"
	          	})
	          	 //删除
	        	var sysBtndel=$.extend($.fn.domain.del,{
	        		title:'删除',
	        		text:'删除',
	        		iconCls:"icon-remove",
	        		handler:function(){
	        			var nodes=$table.datagrid("getSelections");
	        			if(!nodes||nodes.length==0){
	        				top.$.messager.alert("信息", "请您选择需要删除的记录", "info", null, 2000);
	        				return;
	        			}
	        			var ids="";
	        			for(var i=0;i<nodes.length;i++){
	        				ids+=","+nodes[i]["id"];
	        			}        			
	     			  $.messager.confirm('确认','请确认数据是否已同步，删除后数据无法更新线上已有数据，请核对后谨慎删除，确认删除？',function(r){
				           	if(r==false){return false;}
				           	$.ajax({
			                  	url:"${pageContext.request.contextPath}/vod/MasterplateSon/deletes",
			                  	type:"GET",
			                  	dataType:"JSON",
			                  	data:{id:ids.substr(1,ids.length)},
			                 	async:false,
			                  	success:function(){
			                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
			                  		getinfo();         		
			                  	},
			                  	error:function(XHR, status, errorThrow){
			                  		
			                  	}
			                  }); 
	    			   })
	        		},
	        		scope:"one,more"
	        	})
        </script>
    </body>
</html>
