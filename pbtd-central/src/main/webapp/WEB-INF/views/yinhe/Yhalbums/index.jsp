<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
<link rel="stylesheet" type="text/css"
	href="/js/common/themes/default/base.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 60px;
}
</style>
</head>
<body class="easyui-layout" style="visibility: hidden">
	<div data-options="region:'north',border:false,split:true"
		style="padding: 0px; border-bottom: 1px solid #99BBE8; height: 74px; width: auto;">
		<form id="formQuery" style="margin: 0; padding: 0" action=""
			method="post">
			<span class="property"> <label class="">名称：</label> <input
				type="text" id="albumName" name="albumName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="directorname" name="directorname" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">主演：</label> <input
				type="text" id="actorname" name="actorname" style="width: 200px;" />
			</span>
			<br/><hr/>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="chnId" name="chnId" onchange="choosechannellabel(this.value);" style="width: 120px;">
					  <option></option>
					</select>
			</span>
			<span class="property"> <label class="">标签：</label>
					<select  id="tagsids" name="tagsids" style="width: 120px;">
					  <option></option>
					 </select>
			 </span>
			<span class="property"> <label class="">汇聚状态：</label> 
					<select  id="isStorage" name="isStorage" style="width: 120px;">
					    <option value="-1">全部</option>
					    <option value="1">已汇聚</option>
					    <option value="0" selected="selected">未汇聚</option>
					</select>
			</span>
			<span class="property"> <label class="">付费状态：</label> 
					<select  id="isPurchase" name="isPurchase" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1">付费</option>
					    <option value="0">免费</option>
					</select>
			</span>
			<span class="property"> <label class="">是否有效：</label> 
					<select  id="status" name="status" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" selected="selected">有效</option>
					    <option value="0">失效</option>
					</select>
			</span>
			
			 <a href="javascript:getVodChannels()" id="btnSearch"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="padding: 0px;">
		<table id="vodchannels"></table>
	</div>
	<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
	<script type="text/javascript" 	src="/js/common/scripts/jquery-domain.min.js"></script>


	<script type="text/javascript"> 
            function getVodChannels() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	// var customToolbar = [ Storagebtn ,'-',SysBtn1];
                	 var customToolbar = [ Storagebtn ];
                	t.domain('datagrid', {
                        title: '银河源数据管理',
                    	url: '${pageContext.request.contextPath}/yinhe/albums/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                    	idField:"albumId",
                        columns: [[
	                    	{field: 'albumId', title: '唯一标识', width: 100,  align: 'center', hidden: false}
	                    	,{field: 'albumName', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'directorname', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'actorname', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'chnName', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'tags', title: '标签', width: 140,  align: 'left', hidden: false}
	                    	,{field: 'sets', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentCount', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	//,{field: 'focus', title: '看点', width: 150, sortable: true, align: 'left', hidden: false}
	                    	//,{field: 'score', title: '豆瓣评分', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'updatetime', title: '更新时间', width: 140,  align: 'center', hidden: false}
	                    	,{field: 'storagetime', title: '入库时间', width: 140,  align: 'center', hidden: false}
	                    	,{field: 'isStorage',  title: '状态', width: 60,  align: 'center', hidden: false,formatter: getisStorage}
	                    	,{field: 'status',  title: '操作', width: 80,  align: 'center', hidden: false,formatter: getBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '频道管理'
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
                $table = $("#vodchannels");
    			$chnId=$("#chnId");
    			$tagsids=$("#tagsids");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getVodChannels();
                choosechannel();
            });
        	
            function getisStorage(value){
            	 var ckButton='';
            	 if(value=="1"){
    		    	 ckButton = "<span style='color: red;'>已入库</span>";
    		     }else{
    		    	 ckButton = "<span style='color: green;'>未入库</span>";
    		     }
            	 return ckButton
            }
            
            //手动汇聚当天数据
            	  var  SysBtn1= $.extend($.fn.domain.btnSyn,{
                   	title:"每日爬取",
                  	text:"每日爬取",
              		handler: function() {
              			curdayalbum();
                      },
                      scope:"one,more"
              	}); 
              	 
            	  function curdayalbum(){
            		  var  url= '${pageContext.request.contextPath}/yinhe/QuartzController/quartz/curdayalbum';	  	 
        	  			 $.ajax({
        	               	url:url,
        	               	type:"POST",
        	               	data:{},
        	               	dataType:"JSON",
        	               	success:function(result){
        	               			$.messager.alert("信息","汇聚成功！","info",function(){
        	               			getVodChannels();
        	               			});
        	               	},
        	               	error:function(XHR, status, errorThrow){
        	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
        	               	}
                	   })
            	  }
            	  
            	 function getBtn(value, row, index, text){
    			     var ckButton='';
    			     var status = row.isStorage;
    			     if(status=="1"){
    			    	 ckButton = "<a name='show' onclick='javascript:show("+ row.albumId + ")' class='easyui-linkbutton'>查看</a>";
    			     }else{
    			    	 ckButton = "<a name='show' onclick='javascript:show("+ row.albumId + ")' class='easyui-linkbutton'>查看</a>";
    			    	 //ckButton = "<a name='show' onclick='javascript:show("+ row.code + ")' class='easyui-linkbutton'>入库对比</a>";
    			    	 
    			     }
    			     return ckButton;
    	   		}
        	 //批量操作入库
        	 var  Storagebtn= $.extend($.fn.domain.btnPhoto,{
             	title:"入库",
            	text:"入库",
        		handler: function() {
        			var nodes =  $table.datagrid("getSelections");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要入库信息", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids="";
           	        for (var i = 0; i < nodes.length; i++) {
        	          //  ids+=",'"+nodes[i]["albumId"]+"'";
        	            ids+=","+nodes[i]["albumId"];
        	        }
           	     savestatus(ids.substr(1,ids.length),1)
                },
                scope:"one,more"
        	}); 
        	 
        	 function savestatus(ids,type){
        		 var  url= '${pageContext.request.contextPath}/yinhe/strategy/albuminfoid';	  	 
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:{id:ids,cp:1,type:type},
	               	dataType:"JSON",
	               	success:function(result){
	               			$.messager.alert("信息","入库成功！","info",function(){
	               				$table.datagrid("load");
	               			});
	               	},
	               	error:function(XHR, status, errorThrow){
	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
	               	}
        	   })
        	 }
        	 
        	 
	    	 
	    	    function show(id){
            		$(parent).domain("openDialog", { 
        	        	iconCls: "icon-view", 
        	        	title: "查看", 
        	        	src: "${pageContext.request.contextPath}/yinhe/albums/show/"+id,
        	        	width: 1060, 
        	        	height: 550,
        	        	onClose: function() { 
        	            }
        	        });
            	}
	    	    
	    	  //选择频道
	        	function choosechannel(){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelyh",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{level:1},
	                       	async:false,
	                       	success:function(msg){
	                       		var options = " <option value=''>--请选择--</option> ";
	    		            		for(var i=0;i<msg.length;i++){
	    		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	    		            		}
	    		            		$chnId.html(options);
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       		
	                       	}
	                       });
	                   }
	        	  //选择标签
	        	function choosechannellabel(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannellabelyh",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{chnId:value},
	                       	async:false,
	                       	success:function(msg){
	                       		var options = " <option value=''>--请选择--</option> ";
	    		            		for(var i=0;i<msg.length;i++){
	    		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	    		            		}
	    		            		$tagsids.html(options);
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       		
	                       	}
	                       });
	                   }
        </script>
</body>
</html>
