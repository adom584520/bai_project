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
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodbussinfo"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	  var w = document.body.clientWidth-520;
            	  	var h = parent.document.body.clientHeight-220; 
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodbussinfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',sysBtns5,'-'];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
                	t.domain('datagrid', {
                        title: '项目管理',
                    	url: '${pageContext.request.contextPath}/vod/vodbussinfo/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                    	idField:"bussId",
                        columns: [[
								{field: 'bussId', title: '标识', width: 100, sortable: true, align: 'center', hidden: true}
								,{field: 'name', title: '分平台code', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'bussUser', title: '分平台名称', width: 100, sortable: true, align: 'center', hidden: false}
								,{field: 'address', title: 'url', width: 100, sortable: true, align: 'center', hidden: false}
								/* ,{field: 'groupId', title: '项目分组', width: 100, sortable: true, align: 'center', hidden: false} 
								,{field: 'bussPhone', title: '电话', width: 100, sortable: true, align: 'center', hidden: false}		*/
								,{field: 'create_time', title: '创建时间', width: 100, sortable: true, align: 'center', hidden: false,
								    	formatter: function(value) { 
											 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
								            return datetime;     
									}}
								,{field: 'update_time', title: '更新时间', width: 100,  align: 'center', hidden: false,
									formatter: function(value) { 
										 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
								         return datetime;     
								}
								}
								,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
									 if(value=="1"){
			                   				return "<span style='color:green'>上线</span>";
			                   			 }else{
			                   				 return "<span  style='color:red'>下线</span>";
			                   			 }
	                             
							}}
								,{field: 'opt', title: '操作', width: 100, sortable: true, align: 'center', hidden: false,
									formatter: function(value,row) { 
										if(row.status==1){
										 var sta ="<a href='javascript:control(0)'>下线</a>";
										}else{
											var sta ="<a href='javascript:control(1)'>上线</a>";
										}
								         return sta;     
									
									}}								 
								]],
									onLoadSuccess: function(data, status, XHR) {
								},
									onLoadError: function(XHR, status, errorThrow) {
								},
								  names: [
						                    ],
						                    subject: '项目信息',
						                    openWidth:w, 
							                openHeight:h
							});
						}else {
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
                $table = $("#vodbussinfo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	
           	 $.fn.domain.edit={
            	    	id: "btnEdit",
            	        text: "编辑",
            	        iconCls: "icon-edit",
            	        disabled: false,
            	        handler: function() {
            	        	var nodes =  $table.datagrid("getSelections");
            	        	var bussId=nodes[0]["bussId"];
            	        	var t = $(this).parent().next().find(">table:hidden");
            	            var opts = t.datagrid("options");
            	            var title = opts.title || opts.subject;
            	            var url = "${pageContext.request.contextPath}/vod/vodbussinfo/edit/"+bussId;
            	            $(parent).domain("openDialog", { 
            	            	iconCls: "icon-add", 
            	            	title: "编辑 " , 
            	            	src: url, 
            	            	width: opts.openWidth+20, 
            	            	height: opts.openHeight-20, 
            	            	onClose: function() { 
            	            		if (top.$.data(top.document.body, "domain.create.refresh")) {
            	            			top.$.removeData(top.document.body, "domain.create.refresh");
            	            			t.datagrid("reload");
            	                    }
            	                }
            	            });
            	        },
            	        scope: "one"
            	    }; 
           	$.fn.domain.del={
        	    	id: "btnDel",
        	        text: "删除",
        	        iconCls: "icon-edit",
        	        disabled: false,
        	        handler: function() {
        	        	var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	$.messager.confirm("确认","确认删除?",function(r){
	        	    		if(r==false){return false;}
	        	    		var ids="";
		        	    	var cpcode=false;
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=","+nodes[i]["bussId"];
		        	        }
		           	        ids=ids.substr(1,ids.length);
	        	            var url = "${pageContext.request.contextPath}/vod/vodbussinfo/deletes";
	        	            $.ajax({
	    		               	url:url,
	    		               	type:"POST",
	    		               	data:{id:ids},
	    		               	dataType:"JSON",
	    		               	success:function(result){
	    		               			$.messager.alert("信息","删除成功！","info",function(){
	    		               				$table.datagrid("load");
	    		               			});
	    		               	},
	    		               	error:function(XHR, status, errorThrow){
	    		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
	    		               	}
	    	        	   })	
	        	    	})
	        	    	
        	        },
        	        scope: "one,more"
        	    }; 
      function control(sta){
    	  var nodes =  $table.datagrid("getSelections");
    	  var id=nodes[0].bussId;
    	  var url = "${pageContext.request.contextPath}/vod/vodbussinfo/updateSta";
    	  $.ajax({
             	url:url,
             	type:"POST",
             	data:{bussId:id,status:sta},
             	dataType:"JSON",
             	success:function(result){
             			$.messager.alert("信息","操作成功！","info",function(){
             				$table.datagrid("load");
             			});
             	},
             	error:function(XHR, status, errorThrow){
             		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
             	}
  	   })
 }
        	
    	//下发同步
    	 var  sysBtns5=$.extend($.fn.domain.Btnsys5,{
	        title:"下发",
	        text:"下发",
	        iconCls:"icon-ok",
    		handler:function(){
    			var nodes =  $table.datagrid("getSelections");
    	    	if (!nodes || nodes.length == 0) {
    	            top.$.messager.alert("信息", "请您选择要同步的项目", "info", null, 2000);
    	            return;
    	        } 
    	    	var ids = "";
       	        for (var i = 0; i < nodes.length; i++) {
    	            ids+=nodes[i]["bussId"]+",";
    	        }
    			$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/vodbussinfo/searchBussId/"+ids,
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{},
                  	async:false,
                  	success:function(){
                  		 top.$.messager.alert("信息", "同步完成！", "info", null, 2000); 
                  	},
                  	error:function(XHR, status, errorThrow){
                  		
                  	}
                  }); 
    		},
    		scope:"all"
    	})
         	
        </script>
    </body>
</html>
