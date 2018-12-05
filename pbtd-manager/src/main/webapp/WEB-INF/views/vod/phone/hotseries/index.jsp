<!--
        *  热搜管理
        * @author shenjr
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>热播推荐管理</title>
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
      		  width:60px;
        }
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:75px; width:auto;">
      	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
				 <span class="property">
				 		<label class="">热播名称：</label>
						<input type="text" id="name" name="name" style="width:200px;"/>
						<a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
				 </span>
		    <br/><hr/>
            <span class="property"> <label class="">下发时间：</label>
					 <input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			 </span>
        </form>
      </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodhotseries"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getforminfo() {
            	 var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
            	 
                var t = $('#vodhotseries');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined'){
             	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',sysBtndel,sysBtnup,'-',sysBtnno,'-',sysBtn,'-',sysBtns5,'-'];
                	t.domain('datagrid', {
                		title:'热搜管理',
                    	url:'${pageContext.request.contextPath}/vod/phone/vodhotseries/page',
                        toolbar:customToolbar,
                        openWidth:1100,
                        openHeight:530,
                    	showFooter:false,
                    	idField:"id",
                    	columns: [[
							{field: 'id', title: '唯一标识', width: 100, align: 'center'}
						   ,{field: 'name', title: '热播名称', width: 100, align: 'center'}
						   ,{field: 'channelCode', title: '频道', width: 100, align: 'center'}
						   ,{field: 'count', title: '显示条数', width: 100, align: 'center'}
						   ,{field: 'type', title: '模板', width: 100, align: 'center'}
						   ,{field: 'create_time', title: '创建时间', width: 100, align: 'center',
							   formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;   }}
						   ,{field: 'create_user', title: '创建人', width: 100, align: 'center'}
						   ,{field: 'update_time', title: '更新时间', width: 100, align: 'center',
							   formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;   }}
						   ,{field: 'update_user', title:' 更新人', width: 100, align: 'center'}
						   ,{field: 'status', title: '状态', width: 100, align: 'center',
							   formatter:function(value,row,index){
							    	var str="";
							    	if(value==1){
							    		str="<span style='color:green'>已上线</span>";
							    	}else{
							    		str="<span style='color:red'>已下线</span>";
							    	}
							    	return str;
							    }
						    }
                           ]],
						onLoadSuccess: function(data, status, XHR) {
						},
						onLoadError: function(XHR, status, errorThrow){
						},names: [
						],subject: '热播管理',
	  		              onClickRow:function(index,row){
	  	                  	$table.datagrid('unselectAll');
	  	                  	$table.datagrid('selectRow', index).datagrid('beginEdit', index);
	  	                  },
	  	                  isHeaderMenu:false
					});
				}else{
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
                $table = $("#vodhotseries");
                
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getforminfo();
            });
        	
        //绑定专辑
       	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
                  	title:"绑定专辑",
                  	text:"绑定专辑",
                  	iconCls: "icon-view",
              		handler: function() {
              			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择热播", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	if(nodes.length>1){
	        	    		 top.$.messager.alert("信息", "请您选择一条热播信息", "info", null, 2000);
	        	    		 return;
	        	    	}	        	    	 
	        	    	var  id= nodes[0]["id"];
              			$(parent).domain("openDialog",{
   	     	        	iconCls: "icon-view", 
   	     	        	title: "添加", 
   	     	      	 	src: "${pageContext.request.contextPath}/vod/phone/vodhotseries/addalbum/"+id,
   	     	        	width: 1250, 
   	    	        	height: 650,
   	     	        	onClose: function(){
   	     	        		var ids = top.$.data(top.document.body, "choose.ids");
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/phone/Vodhotseries/addalbuminfo",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{hot_id:ids},
   	                           	async:false,
   	                           	success:function(){
   	                           	getvodactors();		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           		
   	                           	}
   	                           });
   	     	        	getforminfo();
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
           	            top.$.messager.alert("信息", "请先编辑排序再保存", "info", null, 2000);
           	            return;
           	        } 
           	    	var ids = "";
           	    	var sequence = "";
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids+=","+nodes[i]["seriesCode"];
        	            sequence+=","+nodes[i]["sequence"];
        	        }
	     	        		$.ajax({
	                           	url:"${pageContext.request.contextPath}/vod/phone/vodhotseries/updateSequence",
	                           	type:"GET",
	                           	dataType:"JSON",
	                           	data:{albumid:ids.substr(1,ids.length),sequences:sequence.substr(1,sequence.length)},
	                           	async:false,
	                           	success:function(){
	                           	 top.$.messager.alert("信息", "排序保存成功！", "info", null, 2000);
	                           		$("#vodSpecial").datagrid("load",{});               		
	                           	},
	                           	error:function(XHR, status, errorThrow){
	                           	}
	                           });
	     	        		getvodactors();
	     	       
                   },
                   scope:"one,more"
           	});
    	 
    	 //批量操作上线
       	 var  sysBtnup=$.extend($.fn.domain.btnup,{
            title:"上线",
           	text:"上线",
           	iconCls:"icon-ok",
       		handler: function() {
       			var nodes =  $table.datagrid("getSelections");
       	    	if (!nodes || nodes.length == 0) {
       	            top.$.messager.alert("信息", "请您选择需要上线的记录", "info", null, 2000);
       	            return;
       	        } 
       	    	if (!nodes || nodes.length > 1) {
       	            top.$.messager.alert("信息", "请您选择一条记录", "info", null, 2000);
       	            return;
       	        } 
       	    
       	    	  var ids=nodes[0]["id"];
          	      $.messager.confirm('确认','确认上线？',function(r){
      	        	if(r==false){return false;}
      	            udpatezt(ids,1)
      	        })
               },
               scope:"one,more"
       	});
       	 //批量操作下线
       	 var  sysBtnno=$.extend($.fn.domain.btnno,{
            title:"下线",
           	text:"下线",
        	iconCls:"icon-no",
       		handler: function() {
       			var nodes =  $table.datagrid("getSelections");
       	    	if (!nodes || nodes.length == 0) {
       	            top.$.messager.alert("信息", "请您选择需要下线的记录", "info", null, 2000);
       	            return;
       	        } 
       	    	 var ids=nodes[0]["id"];
          	      $.messager.confirm('确认','确认下线？',function(r){
       	        	if(r==false){return false;}
       	        	 udpatezt(ids,0)
       	        })
               },
               scope:"one,more"
       	}); 

       	 function udpatezt(ids,status){
       		  	$.ajax({
                      	url:"${pageContext.request.contextPath}/vod/phone/vodhotseries/updateStatus",
                      	type:"GET",
                      	dataType:"JSON",
                      	data:{status:status,id:ids},
                      	async:false,
                      	success:function(){
                      		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
                      	     getforminfo();            		
                      	},
                      	error:function(XHR, status, errorThrow){
                      		
                      	}
                      }); 
       	 }
       	 
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
	                    var status="";
	        			var ids="";
	        			for(var i=0;i<nodes.length;i++){
	        				ids+=","+nodes[i]["id"];
	        				status+=nodes[i]["status"];
	        				if(status==1){
	            				top.$.messager.alert("信息", "上线记录不允许删除", "info", null, 2000);
	            				return;
	            			}
	        			}        			
	     			  $.messager.confirm('确认','请确认数据是否已同步，删除后数据无法更新线上已有数据，请核对后谨慎删除，确认删除？',function(r){
				           	if(r==false){return false;}
				           	$.ajax({
			                  	url:"${pageContext.request.contextPath}/vod/phone/vodhotseries/deletes",
			                  	type:"GET",
			                  	dataType:"JSON",
			                  	data:{id:ids.substr(1,ids.length)},
			                 	async:false,
			                  	success:function(){
			                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
			                  		getforminfo();         		
			                  	},
			                  	error:function(XHR, status, errorThrow){
			                  		
			                  	}
			                  }); 
	    			   })
	        		},
	        		scope:"one,more"
	        	})
	        	//下发
         	 var  sysBtns5=$.extend($.fn.domain.Btnsys5,{
              	title:"下发",
             	text:"下发",
             	iconCls:"icon-ok",
         		handler: function() {
         			 var curtime=$("input[name='curtime']").val();
         			 if(curtime=="" || curtime==null){
         				 top.$.messager.alert("信息", "下发时间不能为空！", "info", null, 2000);
             	            return;
         			 }
         	      	$.ajax({
                        	url:"${pageContext.request.contextPath}/integrate/outputjson/phone/phonehotseriesip",
                        	type:"GET",
                        	dataType:"JSON",
                        	data:{curtime:curtime},
                        	async:false,
                        	success:function(msg){
                        	 top.$.messager.alert("信息", "下发成功！", "info", null, 2000);
             	            return;
                        	},
                        	error:function(XHR, status, errorThrow){
                        		
                        	}
                        });
                 },
                 scope:"all"
         	});
        </script>
    </body>
</html>
