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
        <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
<style type="text/css">
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
a.l-btn span span.l-btn-text{
   width:60px;
        }
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:110px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            <span class="property"> <label class="">cp专辑标识：</label>
            	 <input	type="text" id="showid" name="showid" style="width: 200px;" />
			</span>
            <span class="property"> <label class="">名称：</label>
            	 <input	type="text" id="name" name="name" style="width: 200px;" />
			</span>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="channel" name="channel" onchange="choosechannellabel(this.value);" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${channellist }" var="item">
						 	<option value="${item.channelCode}"> ${item.channelName}</option>
						</c:forEach>					  
					</select>
			</span>
			<span class="property"> <label class="">phone状               态:</label>
			<select id="status" name="status" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">上线</option>
			  <option value="0">下线</option>
			</select>
			</span>
			 <span class="property"> <label class="">tv状               态:</label>
			<select id="tvstatus" name="tvstatus" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">上线</option>
			  <option value="0">下线</option>
			</select>
			</span>
			<br/>
			<hr/>
			</span>
			<span class="property"> <label class="">类别：</label> 
					<select  id="type" name="type" style="width: 120px;">
					    <option value=""  selected="selected">全部</option>
					    <option value="1">专辑</option>
					    <option value="2">剧集</option>
					</select>
			</span>
			<span class="property"> <label class="">状态：</label> 
					<select  id="play" name="play" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >解控</option>
					    <option value="0">播控</option>
					</select>
			</span>
			<span class="property"> <label class="">剧集类型：</label> 
					<select  id="isPositive" name="isPositive" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >正片</option>
					    <option value="0">非正片</option>
					</select>
			</span>
			<span class="property"> <label class="">是否显示:</label>
						<select id="isShow" name="isShow" style="width:120px;">
						  <option value="">--请选择--</option>
						  <option value="1">是</option>
						  <option value="0">否</option>
						</select>
						</span>
			<hr/>
			 <span class="property" > <label class="">更新时间：</label>
			  <input id="start_create_time" name="start_create_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="end_create_time" name="end_create_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
                <a href="javascript:getalbumin()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="phonealbuminfo"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        <script type="text/javascript"> 
            function getalbumin() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#phonealbuminfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [ sysBtnexport,'-',sys2Btn,'-',sys2Btn1 ];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/album/playpolicy/page',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"mediaId",
                        columns: [[
							 {field: 'id', title: '标识', width: 220,   align: 'center', hidden: true}
	                    	,{field: 'mediaId', title: '唯一标识', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'title', title: '名称', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'drama', title: '剧集', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'play',  title: '播控状态', width: 60,  align: 'center', hidden: false,formatter: getplay}
	                    	,{field: 'isShow', title: '是否显示', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 if(value=="1"){
	                    				 return '是';
	                    			 }else{
	                    				 return '否';
	                    			 }
								}}
	                    	,{field: 'isPositive', title: '类型', width: 100,  align: 'center', hidden: false,formatter: getisPositive}
	                    	,{field: 'channelname', title: '频道', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'showid', title: '专辑id', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'create_time', title: '更新时间', width: 140,   align: 'left', hidden: false}
	                    	]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '节目集管理',
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
                $table = $("#phonealbuminfo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getalbumin();
            });
        	 function getplay(value){
              	 var ckButton='';
              	 if(value=="1"){ 
      		    	 ckButton = "<span style='color: green;'>解控</span>";
      		     }else{
      		    	 ckButton = "<span style='color: red;'>被播控</span>";
      		     }
              	 return ckButton
              }
        	 function getisPositive(value){
              	 var ckButton='';
              	 if(value=="1"){ 
      		    	 ckButton = "<span style='color: green;'>正片</span>";
      		     }else{
      		     }
              	 return ckButton
              }
        	 
	 	   var sysBtnexport = $.extend($.fn.domain.importBtn,{
       		text:"导入数据",
       		handler:function(){
       			$(top).domain("openDialog", { 
                   	iconCls: "icon-importBtn", 
                   	title: "导入数据", 
                   	src: '${pageContext.request.contextPath}/vod/phone/album/playpolicy/importalbum', 
                   	width:600, 
                   	height: 300,
                   	onClose: function() {
                   		getalbumin();
                       }
                   });
       		}
       	});

	 	   
	 	  	
	       	 function show(code){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/show/"+code,
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	            }
	     	        });
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
	        	            ids+=",'"+nodes[i]["mediaId"]+"'";
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
        	    	
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要更改信息", "info", null, 2000);
        	            return;
        	        } 
        	    	
	        	    	var ids="";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=",'"+nodes[i]["mediaId"]+"'";
	        	        }
	           	     updateisshow(ids.substr(1,ids.length),0);
                },
                scope:"one,more"
        	});
        	 
        	 
        	 function updateisshow(ids,type){
        		 var  url= '${pageContext.request.contextPath}/vod/phone/album/playpolicy/video/updateisshow';	  	 
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
	       	 </script>
    </body>
</html>
