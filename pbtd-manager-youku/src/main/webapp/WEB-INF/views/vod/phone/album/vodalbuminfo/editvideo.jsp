<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 80px;
}

button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	background-color:;
}
button:hover {
	text-decoration: none;
}
button:active {
	position: relative;
	top: 1px;
}
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:36px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
		            <input type="hidden" id="seriesCode" name="seriesCode"/>
		            <span class="property"> <label class="">名称：</label> <input
						type="text" id="dramaname" name="dramaname" style="width: 200px;" />
					</span>
		            		<span class="property"> <label class="">剧集：</label> <input
						type="text" id="drama" name="drama"  class="easyui-numberbox"  style="width: 120px;" />
					</span>
					<span class="property"> <label class="">是否显示:</label>
						<select id="isShow" name="isShow" style="width:120px;">
						  <option value="">--请选择--</option>
						  <option value="1">是</option>
						  <option value="0">否</option>
						</select>
						</span>
						 <span class="property"> <label class="">是否正片：</label>
						<select  id="isPositive" name="isPositive"  style="width: 120px;">
						<option value="">--请选择--</option>
						<option value="1">--是--</option>
						<option value="0">--否--</option>				  
					    </select>
					</span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodalbumvinfoideo"></table>
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
            	 var id=$.query.getId();
                var t = $('#vodalbumvinfoideo');
                var seriesCode="";
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',sys1Btn,'-',sys2Btn,'-',sys2Btn1,sys3Btn,'-',sys3Btn1,'-'];
                	t.domain('datagrid', {
                        title: '剧集信息',
                    	url: '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/videopage/'+id,
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"id",
                        columns: [[
                             {field: 'seriesCode', title: '专辑Code', width: 120,   align: 'center', hidden:true}
	                    	,{field: 'id', title: '唯一标识', width: 120,   align: 'center', hidden: false}	                    
	                    	,{field: 'dramaname', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'drama', title: '剧集', width: 120,  align: 'center', hidden: false}
	                    	,{field: 'hwversionlist', title: '码率', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'hwversion', title: '已注入码率', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'isShow', title: '是否显示', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 if(value=="1"){
	                    				 return '是';
	                    			 }else{
	                    				 return '否';
	                    			 }
								}}
	                    	,{field: 'isPositive', title: '是否正片', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 if(value=="1"){
	                    				 return '是';
	                    			 }else{
	                    				 return '否';
	                    			 }
								}}
	                    	,{field: 'isCollectfees', title: '是否付费', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 if(value=="1"){
	                    				 return '是';
	                    			 }else{
	                    				 return '否';
	                    			 }
								}}
	                    	,{field: 'dramaviewPoint', title: '看点', width: 120,  align: 'left', hidden: false}	          
	                     	,{field: 'pic', title: '图片', width: 100, sortable: true, align: 'center', hidden: false,
	                    		formatter: function(value,row,index){
									if ('' !=value && null !=value) {
										value = '<img style="width:30px; height:30px" src="${maprealm.imgtitle}'+ value + '">';
										return value;
									}
								}
	                    	}  
	                    	,{field: 'made', title: '操作', width: 100, sortable: true, align: 'center', hidden: false, value:'',
	                    		formatter: function(value,row) { 
						    		return "<a href='javascript:upload("+row.id+")'>上传图片</a>";  
									 
							}
	                    	}
	                    	,{field: 'create_time', title: '创建日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
								}}
	                    	,{field: 'update_time', title: '更新日期', width: 100,  align: 'center', hidden: false,
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
        	                    subject: '剧集管理',
        	                    openWidth:document.body.clientWidth+20,
        		                openHeight:parent.document.body.clientHeight-20
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
                $table = $("#vodalbumvinfoideo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });  
        	//新增
        	$.fn.domain.create={
        	    	id: "btnCreate",
        	        text: "新增",
        	        iconCls:"icon-add",
        	        disabled: false,
        	        handler: function() {
        	            var url ="${pageContext.request.contextPath}/vod/phone/vodalbuminfo/video/edit/"+$.query.getId()+"/0";
        	            $(parent).domain("openDialog", { 
        	            	iconCls: "icon-edit", 
        	            	title: "编辑 ", 
        	            	src: url, 
        	            	width:900, 
        	            	height:600, 
        	            	onClose: function() { 
        	            		if (top.$.data(top.document.body, "domain.create.refresh")) {
        	            			top.$.removeData(top.document.body, "domain.create.refresh");
        	            			$("#vodalbumvinfoideo").datagrid("load",{});
        	                    }
        	                }
        	            });
        	        },
        	        scope: "all"
        	    }; 	
        	//编辑
        	$.fn.domain.edit={
        	    	id: "btnEdit",
        	    	idFiled:"id",
        	        text: "编辑",
        	        iconCls:"icon-edit",
        	        disabled: false,
        	        handler: function() {   
        	        	var nodes =$table.datagrid("getSelections");        		        
        		        if (!nodes|| nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择一条记录！", "info", null, 2000);
	        	            return;
	        	        } 
        		        var code=nodes[0].id;
        	            var url ="${pageContext.request.contextPath}/vod/phone/vodalbuminfo/video/edit/"+$.query.getId()+"/"+code;
        	            $(parent).domain("openDialog", { 
        	            	iconCls: "icon-edit", 
        	            	title: "编辑 ", 
        	            	src: url, 
        	            	width:900, 
        	            	height:600, 
        	            	onClose: function() { 
        	            		if (top.$.data(top.document.body, "domain.create.refresh")) {
        	            			top.$.removeData(top.document.body, "domain.create.refresh");
        	            			$("#vodalbumvinfoideo").datagrid("load",{});
        	                    }
        	                }
        	            });
        	        },
        	        scope: "all"
        	    }; 	
	        	 
	        	 //删除
	        	 var  sys1Btn=$.extend($.fn.domain.del,{
	             	title:"删除",
	            	text:"删除",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	$.messager.confirm('确认','确认删除？',function(r){
	        	    		if(r==false){return false;}
		        	    	var ids="";
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=",'"+nodes[i]["id"]+"'";
		        	        }
		           	     deletes(ids.substr(1,ids.length),2);
	        	    	});
	                },
	                scope:"one,more"
	        	});
	        	 function deletes(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/video/deletes';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{'id':ids},
		               	dataType:"JSON",
		               	success:function(){
		               			$.messager.alert("信息","删除成功！","info",function(){
		               				$table.datagrid("load");
		               			});
		               	},
		               	error:function(XHR, status, errorThrow){
		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
		               	}
	        	   })
	        	 }
	        	 
	        	 
	        	 //显示
	        	 var  sys2Btn=$.extend($.fn.domain.Sysbtn1,{
	             	title:"显示",
	            	text:"显示",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要更改信息", "info", null, 2000);
	        	            return;
	        	        } 
		        	    	var ids="";
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=",'"+nodes[i]["id"]+"'";
		        	        }
		           	     updateisshow(ids.substr(1,ids.length),1);
	                },
	                scope:"one,more"
	        	});
	        	 //显示
	        	 var  sys2Btn1=$.extend($.fn.domain.Sysbtn2,{
	             	title:"不显示",
	            	text:"不显示",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要更改信息", "info", null, 2000);
	        	            return;
	        	        } 
		        	    	var ids="";
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=",'"+nodes[i]["id"]+"'";
		        	        }
		           	     updateisshow(ids.substr(1,ids.length),0);
	                },
	                scope:"one,more"
	        	});
	        	 function updateisshow(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/video/updateisshow';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{id:ids,isshow:type},
		               	dataType:"JSON",
		               	success:function(){
		               			$.messager.alert("信息","修改成功！","info",function(){
		               				$table.datagrid("load");
		               			});
		               	},
		               	error:function(XHR, status, errorThrow){
		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
		               	}
	        	   })
	        	 }
	        	 
	        	 //上传图片
	 			function upload(id){			
				    var picname="phonevideopic";
		 		$(parent).domain("openDialog", { 
			        	iconCls: "icon-view", 
			        	title: "上传", 
			        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
			        	width: 450, 
		        	    height: 250,
		        	success:function(data){		        		
		        	},
			        	onClose: function() { 
			        		$("#vodalbumvinfoideo").datagrid("load",{});
			            }
			        });
		 	}
	        	 //正片
	        	 var  sys3Btn=$.extend($.fn.domain.Sysbtn1,{
	             	title:"正片",
	            	text:"正片",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要更改信息", "info", null, 2000);
	        	            return;
	        	        } 
		        	    	var ids="";
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=","+nodes[i]["id"]+"";
		        	        }
		           	     updateisPositive(ids.substr(1,ids.length),1);
	                },
	                scope:"one,more"
	        	});
	        	 //不是正片
	        	 var  sys3Btn1=$.extend($.fn.domain.Sysbtn2,{
	             	title:"不是正片",
	            	text:"不是正片",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要更改信息", "info", null, 2000);
	        	            return;
	        	        } 
		        	    	var ids="";
		           	        for (var i = 0; i < nodes.length; i++) {
		        	            ids+=","+nodes[i]["id"]+"";
		        	        }
		           	     updateisPositive(ids.substr(1,ids.length),0);
	                },
	                scope:"one,more"
	        	});
	        	 function updateisPositive(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/video/updateisPositive';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{id:ids,isPositive:type},
		               	dataType:"JSON",
		               	success:function(){
		               			$.messager.alert("信息","修改成功！","info",function(){
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
