<!--
        *  热搜管理
        * @author shenjr
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>热搜管理</title>
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
				 		<label class="">专辑名称：</label>
						<input type="text" id="seriesName" name="seriesName" style="width:200px;"/>
				 </span>
				 <span class="property">
				 		<label>频道：</label>
				 		<input type="text" id="" name="" style="width:200px;"/>
				 </span>
				
				 <a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
        </form>
      </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodlabel"></table>
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
            	 
                var t = $('#vodlabel');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined'){
             	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtnAdd];
                	t.domain('datagrid', {
                		title:'热搜管理',
                    	url:'${pageContext.request.contextPath}/vod/phone/vodHotSearch/page',
                        toolbar:customToolbar,
                        openWidth:1100,
                        openHeight:520,
                        pageNumber: 1,
                        pageSize: 10,
                        pageList:[10,30,50,100],
                        columns: [
                                  [
    								{
    								    field: 'seriesCode',
    								    title: '专辑code',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'seriesName',
    								    title: '专辑名称',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'volumncount',
    								    title: '总集数',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},
    								{
    								    field: 'currentnum',
    								    title: '更新至集数',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'writerName',
    								    title: '导演',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'actorName',
    								    title: '主演',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'orgairDate',
    								    title: '首映日期',
    								    width: 100,
    								    sortable: true,
    								    align: 'center'
    								},{
    								    field: 'update_time',
    								    title: '更新时间',
    								    width: 150,
    								    sortable: true,
    								    align: 'center',
    								    formatter:function(value,row,index){
    								    	var str=Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
    								    	return str;
    								    }
    								},{
    								    field: 'status',
    								    title: '状态',
    								    width: 100,
    								    sortable: true,
    								    align: 'center',
    								    formatter:function(value,row,index){
    								    	var str="";
    								    	if(value==1){
    								    		str="<span style='color:red'>已上线</span>";
    								    	}else{
    								    		str="<span style='color:green'>未上线</span>";
    								    	}
    								    	return str;
    								    }
    								},{
    								    field: 'isCollectfees',
    								    title: '是否收费',
    								    width: 100,
    								    sortable: true,
    								    align: 'center',
    								    formatter:function(value,row,index){
    								    	var str="";
    								    	if(value==1){
    								    		str="<span style='color:red'>是</span>";
    								    	}else{
    								    		str="<span style='color:green'>否</span>";
    								    	}
    								    	return str;
    								    }
    								}
                               ]
                           ],
						onLoadSuccess: function(data, status, XHR) {
						},
						onLoadError: function(XHR, status, errorThrow){
						}
					});
				}else{
						t.datagrid("load", queryParams);
					}
				}
            
            
            var  sysBtnAdd=$.extend($.fn.domain.btnAdd,{
             	title:"确认",
            	text:"确认",
            	iconCls:"icon-ok",
        		handler: function() {
        			var nodes =  $table.datagrid("getChecked");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要添加的记录", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids="";
        	    	var len=nodes.length;
           	        for (var i = 0; i < len; i++) {
        	            ids+=nodes[i]["seriesCode"]+",";
        	        }
           	        ids=ids.substring(0,ids.length-1);
           	     	addAll(ids);
                },
                scope:"one,more"
        	});
            
            
            function addAll(ids){
       		  	$.ajax({
                      	url:"${pageContext.request.contextPath}/vod/phone/vodHotSearch/addAll",
                      	type:"POST",
                      	dataType:"JSON",
                      	data:{ids:ids},
                      	async:false,
                      	success:function(){
                      		 top.$.messager.alert("信息", "添加成功！", "info", null, 2000);   
                      		$("#vodlabel").datagrid("load",{});               		
                      	},
                      	error:function(XHR, status, errorThrow){
                      		
                      	}
               }); 
       	 } 
            
            
            
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#vodlabel");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
              
            });
        </script>
    </body>
</html>
