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
   a.l-btn span span.l-btn-text {
        width:60px;
        }
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
             <input type="hidden" id="channelCode" name="channelCode"/>
		         <span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			</span>
		         <span class="property"> <label class="">名称：</label> 
		      <input type="text" id="seriesName" name="seriesName" style="width: 200px;" />
		   </span>
		   <span class="property"> <label class="">状态：</label>
			<select  id="tvstatus" name="tvstatus" style="width: 120px;">
			  <option value="1" selected="selected">上线</option>
			  <option value="0">下线</option>
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
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtn,'-',sysBtn2,'-',sysBtn3];
                	t.domain('datagrid', {
                        title: '绑定数据',
                    	url: '${pageContext.request.contextPath}/vod/tv/Vodchannel/albuminfopage',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	showFooter:false,
                    	idField:"seriesCode",
                        columns: [[
	                    	{field: 'seriesCode', title: '唯一标识', width: 220,   align: 'center', hidden: false}
	                    	,{field: 'seriesName', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'writerName', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'actorName', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'ChannelName', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'status', title: '状态', width: 60,  align: 'center', hidden: false,formatter:getstatus}
	                    	,{field: 'sequence', title: '排序', width: 60,  align: 'center', hidden: false,
	                    	editor:{
                    			type:"numberbox",
                    			options:{
                    				min:0,   
                    			    precision:0,
                    			    max:100
                    			}
                    		}}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '节目信息',
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
                $table=$("#forminfo");
                $levels="${levels}";    			
    			$channel="${channel}";
    			$parentCode="${parentCode}";    			
    			$channelName="${channelName}";
    			$channelId="${channelid}";
    			if($levels==2){
    				$tagsids="${channel}";
    			}else{
    				$tagsids="${channelid}";
    			}
    			$("#channelCode").val($tagsids);
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	
        function getstatus(value){
        	 var ckButton='';
        	 if(value=="1"){
		    	 ckButton = "<span style='color: green;'>上线</span>";
		     }else{
		    	 ckButton = "<span style='color: red;'>下线</span>";
		     }
        	 return ckButton
        }
        	  
	        	 
       	//绑定专辑
       	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
                   title:"添加节目",
                  	text:"添加节目",
                  	iconCls: "icon-view", 
              		handler: function() {
              			$(parent).domain("openDialog", {
   	     	        	iconCls: "icon-view", 
   	     	        	title: "添加", 
   	     	       		src: "${pageContext.request.contextPath}/vod/tv/vodSpecial/addalbuminfo/"+$tagsids+"?parentCode="+$parentCode+"&channelName="+$channelName+"&levels="+$levels,          
   	     	        	width: 1250, 
   	    	        	height: 650,
   	     	        	onClose: function() { 
   	     	        		var ids = top.$.data(top.document.body, "choose.ids");
   	     	        		console.log(ids);
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/addalbuminfo",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{albumid:ids,id:$tagsids,levels:$levels,parentCode:$parentCode},
   	                           	async:false,
   	                           	success:function(){
   	                          	 	getinfo();               		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           		
   	                           	}
   	                           });
   	     	        	 
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
      	                           	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/delalbuminfo",
      	                           	type:"GET",
      	                           	dataType:"JSON",
      	                           	data:{albumid:ids.substr(1,ids.length),id:$tagsids},
      	                           	async:false,
      	                           	success:function(){
      	                           	 	top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
      	                           		$('#forminfo').datagrid('load',{});             		
      	                           	},
      	                           	error:function(XHR, status, errorThrow){
      	                           	}
      	                           });
      	     	        		getinfo()
              	    		}
              	    	});
                      },
                      scope:"one,more"
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
              	        for (var i = 0; i < nodes.length; i++) {
           	            ids+=","+nodes[i]["seriesCode"];
           	            sequence+=","+nodes[i]["sequence"];
           	        }
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/updatealbumsequence",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{albumid:ids.substr(1,ids.length),id:$tagsids,sequence:sequence.substr(1,sequence.length)},
   	                           	async:false,
   	                           	success:function(){
   	                           	 	top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
   	                          		//$('#forminfo').datagrid('load',{});         		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           	}
   	                           });
   	     	        		getinfo(); 		
                      },
                      scope:"one,more"
              	});
        </script>
    </body>
</html>
