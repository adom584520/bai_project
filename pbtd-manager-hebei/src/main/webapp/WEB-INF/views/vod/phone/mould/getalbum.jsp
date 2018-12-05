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
        <title>专题管理</title>
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
 
.datagrid-row {
    height: 82px;
}

a.l-btn span span.l-btn-text {
	width: 28px;
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
  /*  a.l-btn span span.l-btn-text {
        width:60px;
        } */
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
             <input type="hidden" id="id" name="id"/>
              <input type="hidden" id="moduleId" name="moduleId" value="${curid}"/>
               <input type="hidden" id="masterplateNum" name="masterplateNum" value="${num }"/>
             <span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			</span>
             <span class="property"> <label class="">名称：</label> 
			       <input type="text" id="seriesName" name="seriesName" style="width: 200px;" />
			   </span>
			   <span class="property"> <label class="">状态：</label>
				<select  id="status" name="status" style="width: 120px;">
				  <option value="1" selected="selected">上线</option>
				  <option value="0">下线</option>
				  <option value="">--请选择--</option>
				</select>
			 </span>
             <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="forminfo"></table>
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
                var t = $('#forminfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [sysBtn,'-',sysBtn2,'-',sysBtn3,'-',sysBtn4,'-',sysBtn5];
                	t.domain('datagrid', {
                        title: '绑定数据',
                    	url: '${pageContext.request.contextPath}/vod/phone/vodmouldinfo/albuminfopage',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	showFooter:false,
                    	idField:"seriesCode",
                        columns: [[
	                    	{field: 'id', title: 'ID', width: 0,   align: 'left', hidden: true}
	                    	,{field: 'seriesCode', title: '专辑ID', width: 60,fit:true,  height: 132,  align: 'center', hidden: false}
	                    	,{field: 'seriesName', title: '名称', width: 80,   align: 'left', hidden: false}
	                    	,{field: 'writerName', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'actorName', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'pictureurl1', title: '专辑图片1', width: 90,  align: 'left', hidden: false,formatter: getimgurl}
	                    	,{field: 'pictureurl2', title: '专辑图片2', width: 90,  align: 'left', hidden: false,formatter: getimgurl}
	                    	,{field: 'ChannelName', title: '频道', width: 30, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 45,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 50,  align: 'center', hidden: false}
	                    	,{field: 'status', title: '专辑上下线状态', width: 100,  align: 'center', hidden: false,formatter:function(value) { 
								 if(value=="1"){
		                   				return "<span style='color:green'>上线</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>下线</span>";
		                   			 }
                         
						}}
	                    	,{field: 'sequence', title: '排序', width: 30,  align: 'center', hidden: false,
	                    	editor:{
                    			type:"numberbox",
                    			options:{
                    				min:0,   
                    			    precision:0,
                    			    max:100
                    			}
                    		}}
	                    	,{field: 'imgurl1', title: '图片1', width: 120, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'imgurl2', title: '图片2', width: 120, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'imgurl3', title: '图片3', width: 120, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'imgurl4', title: '图片4', width: 120, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'imgStatus', title: '运营位图启用状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
								 if(value=="1"){
		                   				return "<span style='color:green'>使用</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>未用</span>";
		                   			 }
                            
						}}
							,{field: 'opt',  title: '操作', width: 260,  align: 'center', hidden: false,formatter: getBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '绑定管理',
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
    			$tagsids="${specialid}";
    			$table=$("#forminfo");
    			$("#id").val($tagsids);
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	
        function getstatus(value){
        	 var ckButton='';
		     return value;
        }
        	  
        var widthnum = $(window).width();
		var heightnum = $(window).height();
	        	//绑定专辑
	        	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
	                    title:"添加节目",
	                   	text:"添加节目",
	                   	iconCls: "icon-view", 
	               		handler: function() {
	               			$(parent).domain("openDialog", { 
	    	     	        	iconCls: "icon-view", 
	    	     	        	title: "添加", 
	    	     	        	src: "${pageContext.request.contextPath}/vod/phone/vodSpecial/addalbuminfo/"+1,
	    	     	        	width: widthnum, 
	    	    	        	height: 550,
	    	     	        	onClose: function() { 
	    	     	        		var ids = top.$.data(top.document.body, "choose.ids");
	    	     	        		$.ajax({
	    	                           	url:"${pageContext.request.contextPath}/vod/phone/vodmouldinfo/addalbuminfo/${curid}/${num}",
	    	                           	type:"GET",
	    	                           	dataType:"JSON",
	    	                           	data:{albumid:ids},
	    	                           	async:false,
	    	                           	success:function(){
	    	                           		$("#vodSpecial").datagrid("load",{});               		
	    	                           	},
	    	                           	error:function(XHR, status, errorThrow){
	    	                           		
	    	                           	}
	    	                           });
	    	     	        		 getinfo();
	    	     	            }
	    	     	        });
	                       },
	                       scope:"one,more"
	               	});
	        	//删除绑定专辑
	        	 var  sysBtn2=$.extend($.fn.domain.del,{
	                    title:"删除节目",
	                   	text:"删除节目",
	                   	iconCls: "icon-remove", 
	               		handler: function() {
	               			var nodes =  $table.datagrid("getSelections");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
	               	            return;
	               	        } 
	               	    	$.messager.confirm('确认','确认删除？',function(r){
	               	    		if(r){
	               	    			var ids = "";
			               	    	var names = [];
			               	        for (var i = 0; i < nodes.length; i++) {
			            	            ids+=","+nodes[i]["seriesCode"];
			            	        }
		   	     	        		$.ajax({
		   	                           	url:"${pageContext.request.contextPath}/vod/phone/vodmouldinfo/deletealbuminfo/${curid}/${num}",
		   	                           	type:"GET",
		   	                           	dataType:"JSON",
		   	                           	data:{albumid:ids.substr(1,ids.length)},
		   	                           	async:false,
		   	                           	success:function(){
		   	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
		   	                           		$("#vodSpecial").datagrid("load",{});               		
		   	                           	},
		   	                           	error:function(XHR, status, errorThrow){
		   	                           	}
		   	                           });
		   	     	        		 getinfo();	
	               	    		}
	               	    	});
	                       },
	                       scope:"one,more"
	               	});
	        	//启用运营位图片
	        	 var  sysBtn4=$.extend($.fn.domain.Btnsys,{
	                    title:"启用运营位图片",
	                   	text:"启用运营位图片",
	                   	iconCls: "icon-ok", 
	               		handler: function() {
	               			var nodes =  $table.datagrid("getSelections");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要启用的专辑信息", "info", null, 2000);
	               	            return;
	               	        }
	               	    	if (nodes.length > 1) {
								top.$.messager.alert("信息", "请您选择一条信息",
										"info", null, 2000);
								return;
							}
	               	    	$.messager.confirm('确认','确认启用？',function(r){
	               	    		if(r){
	               	    			var ids = nodes[0]["id"];
		   	     	        		$.ajax({
		   	                           	url:"${pageContext.request.contextPath}/vod/phone/vodmouldinfo/shangxianalbuminfo",
		   	                           	type:"GET",
		   	                           	dataType:"JSON",
		   	                           	data:{albumid:ids,status:1},
		   	                           	async:false,
		   	                           	success:function(){
		   	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
		   	                           		$("#vodSpecial").datagrid("load",{});               		
		   	                           	},
		   	                           	error:function(XHR, status, errorThrow){
		   	                           	}
		   	                           });
		   	     	        		 getinfo();	
	               	    		}
	               	    	});
	                       },
	                       scope:"one"
	               	});
	        	
	        	//禁用运营位图片
	        	 var  sysBtn5=$.extend($.fn.domain.btnno,{
	                    title:"禁用运营位图片",
	                   	text:"禁用运营位图片",
	                   	iconCls: "icon-no", 
	               		handler: function() {
	               			var nodes =  $table.datagrid("getSelections");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要禁用的专辑信息", "info", null, 2000);
	               	            return;
	               	        }
	               	    	if (nodes.length > 1) {
								top.$.messager.alert("信息", "请您选择一条信息",
										"info", null, 2000);
								return;
							}
	               	    	$.messager.confirm('确认','确认启用？',function(r){
	               	    		if(r){
	               	    			var ids = nodes[0]["id"];
		   	     	        		$.ajax({
		   	                           	url:"${pageContext.request.contextPath}/vod/phone/vodmouldinfo/shangxianalbuminfo",
		   	                           	type:"GET",
		   	                           	dataType:"JSON",
		   	                           	data:{albumid:ids,status:0},
		   	                           	async:false,
		   	                           	success:function(){
		   	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
		   	                           		$("#vodSpecial").datagrid("load",{});               		
		   	                           	},
		   	                           	error:function(XHR, status, errorThrow){
		   	                           	}
		   	                           });
		   	     	        		 getinfo();	
	               	    		}
	               	    	});
	                       },
	                       scope:"one"
	               	});
	        	
	        	
	        	//保存绑定专辑排序
	        	 var  sysBtn3=$.extend($.fn.domain.save,{
	                    title:"保存",
	                   	text:"保存",
	                   	iconCls: "icon-save", 
	               		handler: function() {
	               			var nodes = $table.datagrid("getRows");
	        				for(var i=0;i<nodes.length;i++){
	        					$table.datagrid("endEdit",i);
	        				}
	        				nodes = $table.datagrid("getChanges");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要保存信息", "info", null, 2000);
	               	            return;
	               	        } 
	               	    	var ids = "";
	               	    	var sequence = "";
	               	    	var moduleid = ${curid};
	               	    	//alert(moduleid);
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            ids+=","+nodes[i]["seriesCode"];
	            	            sequence+=","+nodes[i]["sequence"];
	            	        }
	    	     	        		$.ajax({
	    	                           	url:"${pageContext.request.contextPath}/vod/phone/vodmouldinfo/updatemodulealbumsequence",
	    	                           	type:"GET",
	    	                           	dataType:"JSON",
	    	                           	data:{albumid:ids.substr(1,ids.length),id:moduleid,sequence:sequence.substr(1,sequence.length)},
	    	                           	async:false,
	    	                           	success:function(){
	    	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
	    	                           		$("#vodSpecial").datagrid("load",{});               		
	    	                           	},
	    	                           	error:function(XHR, status, errorThrow){
	    	                           	}
	    	                           });
	    	     	        		 getinfo();
	    	     	       
	                       },
	                       scope:"one,more"
	               	});
	        	
	        	 function  getimgurl(e,row){
	        		 var imgStatus=row.imgStatus;
	        		 var str="<div><img  style='width:80px;height:60px;'  onclick='showimg(\""+e+"\")'   src=${maprealm.imgtitle}"+e+"  />";
	        		 var zIndex=0;
	        		 if(imgStatus==1){
	        			 if(e==row.pictureurl1 || e==row.pictureurl2){
	        				 str+="<div style='position:relative;width:80px;height:60px;background:rgba(255,255,255,0.7);z-index:10;top:-80px;'></div>";
	        			 }else{
	        				 str+="<div style='position:relative;width:80px;height:60px;background:rgba(0,0,0,0);z-index:-10;top:0;'></div>";
	        			 }
	        		 }else{
	        			 if(e==row.imgurl1 || e==row.imgurl2 || e==row.imgurl3 || e==row.imgurl4 ){
	        				 str+="<div style='position:relative;width:80px;height:60px;background:rgba(0,0,0,0.5);z-index:10;top:-80px;left:20px'></div>";
	        			 }else{	  
	        				 str+="<div style='position:relative;width:80px;height:60px;background:rgba(0,0,0,0);z-index:-10;top:0;'></div>";
	        			 }
	        		 }
	        		 str+="</div>";
	      	    	return str;
	      	    }
	        	  function getBtn(value, row, index, text){
		           		 var ckButton='';
		   		    	 ckButton = "<a href='#' onclick='show(\""+row.id+"\",\"yywpimgurl1\")' class='easyui-linkbutton'>图1上传</a>";
		   		    	 ckButton += "&nbsp;&nbsp;&nbsp;<a  href='#' onclick='show(\""+row.id+"\",\"yywpimgurl2\")' class='easyui-linkbutton'>图2上传</a>";
		   		    	 ckButton += "&nbsp;&nbsp;&nbsp;<a  href='#' onclick='show(\""+row.id+"\",\"yywpimgurl3\")' class='easyui-linkbutton'>图3上传</a>";
		   		    	 ckButton += "&nbsp;&nbsp;&nbsp;<a  href='#' onclick='show(\""+row.id+"\",\"yywpimgurl4\")' class='easyui-linkbutton'>图4上传</a>";
		   		    	 return ckButton;
		   	   		}
	        		 function show(id,imgtype){
	 	         		$(parent).domain("openDialog", { 
	 	     	        	iconCls: "icon-view", 
	 	     	        	title: "上传", 
	 	     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+imgtype+"&id="+id+"&imgtype="+imgtype,
	 	     	        	width: 450, 
	 	    	        	height: 250,
	 	     	        	onClose: function() { 
	 	     	        		getinfo();
	 	     	            }
	 	     	        });
	 	         	}
	        		 
	        		 function showimg(img){
	          	    	$(parent).domain("openDialog", { 
	          	        	iconCls: "icon-view", 
	          	        	title: "图片查看", 
	          	        	src: "${pageContext.request.contextPath}/uploadPic/showimg?img=${maprealm.imgtitle}"+img,
	          	        	width: 650, 
	         	        	height: 550,
	          	        	onClose: function() { 
	          	            }
	          	        });
	          	    }
        </script>
    </body>
</html>
