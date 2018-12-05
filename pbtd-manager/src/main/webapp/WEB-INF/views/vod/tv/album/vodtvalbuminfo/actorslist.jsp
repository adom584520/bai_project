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
            		<label class="">名称：</label>
            		<input type="text" id="name" name="name" style="width:200px;"/>
            	</span>	
            		<span class="property"> <label class="">是否演员：</label>
					<select id="actor" name="actor" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="">请选择</option>
								<option value="0" >否</option>
								<option value="1">是</option>
					</select>
			 </span>
			  	<span class="property"> <label class="">是否导演：</label>
					<select id="director" name="director" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="">请选择</option>
								<option value="0" >否</option>
								<option value="1">是</option>
					</select>
			 </span>
                <a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodactors"></table>
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
                var t = $('#vodactors');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [Storagebtn];
                	t.domain('datagrid', {
                		   title: '演员管理',
                       	url: '${pageContext.request.contextPath}/vod/vodactors/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'code', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'actor', title: '是否演员', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'director', title: '是否导演', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'imgportrait', title: '头像图片', width: 100, sortable: true, align: 'center', hidden: false,formatter: getimgportrait}
	                    	,{field: 'backgroundimg', title: '背景图片', width: 100, sortable: true, align: 'center', hidden: false,formatter: getbackgroundimg}
	                    	,{field: 'updatetime', title: '更新日期', width: 100,  align: 'center', hidden: false,
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
                $table = $("#vodactors");
                top.$.data(top.document.body, "choose.ids","");
       	     	top.$.data(top.document.body, "choose.names",""); 
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
              
            });
        	 function  getimgportrait(e){
        	    	return "<img  style='width:80px;height:40px;'  src=${maprealm.imgtitle}"+e+"  />";
        	    }
        	    function  getbackgroundimg(e){
        	    	return "<img  style='width:80px;height:40px;'  src=${maprealm.imgtitle}"+e+"  />";
        	    }
        	    
        	    //批量操作入库
           	 var  Storagebtn=$.extend($.fn.domain.btnPhoto,{
                	title:"确认",
               	text:"确认",
           		handler: function() {
           			var nodes =  $table.datagrid("getSelections");
           	    	if (!nodes || nodes.length == 0) {
           	            top.$.messager.alert("信息", "请您选择需要上线信息", "info", null, 2000);
           	            return;
           	        } 
           	    	var ids = [];
           	    	var names = [];
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids.push(nodes[i]["code"]);
        	            names.push(nodes[i]["name"]);
        	        }
           	     	top.$.data(top.document.body, "choose.ids",ids);
           	     	top.$.data(top.document.body, "choose.names",names); 
        	        $(top).domain('closeDialog');
                   },
                   scope:"one,more"
           	});
        </script>
    </body>
</html>
