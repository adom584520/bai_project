<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
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
	width: 60px;
}
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:108px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            		<span class="property"> <label class="">标识：</label> <input
				type="text" id="show_id" name="show_id" style="width: 120px;" />
			</span>
            		<span class="property"> <label class="">名称：</label> <input
				type="text" id="title" name="title" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="director_list" name="director_list" style="width: 120px;" />
			</span>
			<span class="property"> <label class="">主演：</label> <input
				type="text" id="performer_list" name="performer_list" style="width: 120px;" />
			</span>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="categoryId" name="categoryId" onchange="choosechannellabel(this.value);" style="width: 120px;">
					  <option></option>
					</select>
			</span>
			<span class="property"> <label class="">标签：</label>
					<select  id="sub_categoryId" name="sub_categoryId" style="width: 120px;">
					  <option></option>
					 </select>
			 </span>
			<br/><hr/>
			
			<span class="property"> <label class="">汇聚状态：</label> 
					<select  id="isStorage" name="isStorage" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1">已汇聚</option>
					    <option value="0" selected="selected">未汇聚</option>
					</select>
			</span>
			 <span class="property" > <label class="">更新时间：</label>
					 <input id="start_updatetime" name="start_updatetime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
					 <label class="">至</label>
					 <input id="end_updatetime" name="end_updatetime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;" >
			 </span>
			 <span class="property" > <label class="">汇聚时间：</label>
					 <input id="start_time" name="start_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
					 <label class="">至</label>
					 <input id="end_time" name="end_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;" >
			 </span>
			<br/><hr/>
			<span class="property"> <label class="">是否有效：</label> 
					<select  id="status" name="status" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >有效</option>
					    <option value="2">失效</option>
					</select>
			</span>
            <span class="property"> <label class="">有效状态变更：</label> 
					<select  id="status_next" name="status_next" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >有变更</option>
					</select>
			</span>
			
			<span class="property"> <label class="">付费状态变更：</label> 
					<select  id="paid_next" name="paid_next" style="width: 120px;">
					    <option value="">全部</option>
					      <option value="1" >有变更</option>
					</select>
			</span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodalbum"></table>
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
                var t = $('#vodalbum');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	     	 var customToolbar = [ Storagebtn,'-',ustatus,'-',upaid,'-',exports];
                	t.domain('datagrid', {
                        title: '优酷收费数据管理',
                    	url: '${pageContext.request.contextPath}/youku/albums/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                    	idField:"show_id",
                        columns: [[
	                    	{field: 'show_id', title: '唯一标识', width: 220,   align: 'center', hidden: false}
	                    	,{field: 'title', title: '名称', width: 140,   align: 'left', hidden: false,formatter: gettitle}
	                    	,{field: 'director_list', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'performer_list', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'category', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'sub_category', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'episode_count', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'count', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	//,{field: 'viewPoint', title: '看点', width: 120, sortable: true, align: 'left', hidden: false}
	                    	,{field: 'update_time', title: '更新时间', width: 140,  align: 'center', hidden: false}
	                    	,{field: 'status',  title: '状态', width: 60,  align: 'center', hidden: false,formatter: getstatus}
	                    	,{field: 'storagetime', title: '入库时间', width: 140,  align: 'center', hidden: false}
	                    	,{field: 'isStorage',  title: '入库状态', width: 60,  align: 'center', hidden: false,formatter: getisStorage}
	                    	,{field: 'opt',  title: '操作', width: 120,  align: 'center', hidden: false,formatter: getBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        rowStyler:function(index,row){
                        	if(row.status!=row.status_next){
                				return 'background-color:yellow';
                        	}
                        	if(row.paid!=row.paid_next){
                				return 'background-color:red';
                        	}
                        	
                		}
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
                $table = $("#vodalbum");
            	$chnId=$("#categoryId");
    			$tagsids=$("#sub_categoryId");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
                choosechannel();
            });
        	   function getstatus(value){
              	 var ckButton='';
              	 if(value=="1"){
      		    	 ckButton = "<span style='color: red;'>有效</span>";
      		     }else{
      		    	 ckButton = "<span style='color: green;'>无效</span>";
      		     }
              	 return ckButton
              }
        	   
        	   function gettitle(value, row, index, text){
                	 var   ckButton = "<a href='#' onclick='gettitlealbum(\""+row.show_id+"\")'>"+value+"</a>";
                	 return ckButton
                }
        	   
        		 function gettitlealbum(value){
              		$(parent).domain("openDialog", { 
          	        	iconCls: "icon-view", 
          	        	title: "查看", 
          	        	src: "${pageContext.request.contextPath}/jsonYouku/gettitlealbum/"+value,
          	        	width: 1060, 
         	        	height: 550,
          	        	onClose: function() { 
          	            }
          	        });
              	}
        function getisStorage(value){
        	 var ckButton='';
        	 if(value=="1"){
		    	 ckButton = "<span style='color: red;'>已入库</span>";
		     }else{
		    	 ckButton = "<span style='color: green;'>未入库</span>";
		     }
        	 return ckButton
        }
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.isStorage;
			     if(status=="1"){
			    	 ckButton = "<a name='show' onclick='show(\""+row.show_id+"\")' class='easyui-linkbutton'>查看</a>";
			     }else{
			    	 ckButton = "<a name='show' onclick='show(\""+row.show_id+"\")' class='easyui-linkbutton'>查看</a>";
			    	 //ckButton = "<a name='show' onclick='javascript:show("+ row.code + ")' class='easyui-linkbutton'>入库对比</a>";
			     }
			     ckButton += "&nbsp;&nbsp;<a name='show' onclick='showvideo(\""+row.show_id+"\")' class='easyui-linkbutton'>剧集查看</a>";
			     ckButton += "&nbsp;&nbsp;<a name='show' onclick='showgetvideo(\""+row.show_id+"\")' class='easyui-linkbutton'>获取视频</a>";
			     
			     return ckButton;
	   		}
        	 //批量操作入库
        	 var  Storagebtn=$.extend($.fn.domain.btnPhoto,{
             	title:"入库",
            	text:"入库",
        		handler: function() {
        			var nodes =  $table.datagrid("getSelections");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择需要上线信息", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids="";
           	        for (var i = 0; i < nodes.length; i++) {
        	          //  ids+=",'"+nodes[i]["albumId"]+"'";
        	            ids+=","+nodes[i]["show_id"];
        	        }
           	     savestatus(ids.substr(1,ids.length),2)
                },
                scope:"one,more"
        	});
        	 function savestatus(ids,type){
        		 var  url= '${pageContext.request.contextPath}/youku/storage/savesisStorage';	  	 
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:{id:ids,cp:5,type:type},
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
	  		 
        	 function show(code){
         		$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "查看", 
     	        	src: "${pageContext.request.contextPath}/youku/albums/show/"+code,
     	        	width: 1060, 
    	        	height: 550,
     	        	onClose: function() { 
     	            }
     	        });
         	}
        	 function showvideo(code){
          		$(parent).domain("openDialog", { 
      	        	iconCls: "icon-view", 
      	        	title: "剧集查看", 
      	        	src: "${pageContext.request.contextPath}/youku/albums/editvideo/"+code,
      	        	width: 1060, 
     	        	height: 550,
      	        	onClose: function() { 
      	            }
      	        });
          	}
        	 
	  		  //选择频道
	        	function choosechannel(){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelyk",
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
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannellabelyk",
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
	        	  
	        	 //批量操作处理状态变更
	        	 var  ustatus=$.extend($.fn.domain.Sysbtn2,{
	             	title:"状态更新",
	            	text:"状态更新",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要上线信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids="";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=","+nodes[i]["show_id"];
	        	        }
	           	     updatestatus_paid(ids.substr(1,ids.length),'status')
	                },
	                scope:"one,more"
	        	});
	        	 
	        	 
	        	 //批量操作收费变更
	        	 var  upaid=$.extend($.fn.domain.Sysbtn,{
	             	title:"付费更新",
	            	text:"付费更新",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要上线信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids="";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=","+nodes[i]["show_id"];
	        	        }
	           	     updatestatus_paid(ids.substr(1,ids.length),'paid')
	                },
	                scope:"one,more"
	        	});
	        	 
	        	 function updatestatus_paid(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/youku/albums/status_paid';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{id:ids,type:type},
		               	dataType:"JSON",
		               	success:function(result){ 
		               		if(result.values!=null && result.values!=""){
		               		 $.messager.confirm('确认',"以下视频数据状态变更</br>"+result.values+",是否更新为已处理？",function(r){
		               			 if(r){
		               			 	$.ajax({
		    		                  	url:"${pageContext.request.contextPath}/youku/albums/updatestatus_paid",
		    		                  	type:"POST",
		    		                 	data:{id:ids,type:type},
		    			               	dataType:"JSON",
		    		                  	success:function(){
		    		                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
		    		                  		getinfo(); 		
		    		                  	},
		    		                  	error:function(XHR, status, errorThrow){		                  		
		    		                  	}
		    		                  }); 
		               			 }else{
		               				 return false;
		               			 }
		               			 
		               		 })
		               		}else{
		               			$.ajax({
	    		                  	url:"${pageContext.request.contextPath}/youku/albums/updatestatus_paid",
	    		                  	type:"POST",
	    		                 	data:{id:ids,type:type},
	    			               	dataType:"JSON",
	    		                  	success:function(){
	    		                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
	    		                  		getinfo(); 		
	    		                  	},
	    		                  	error:function(XHR, status, errorThrow){		                  		
	    		                  	}
	    		                  }); 
		               		}
		               	},
		               	error:function(XHR, status, errorThrow){
		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
		               	}
	        	   })
	        	 }
	        	 
	        	 
	        	 //批量操作收费变更
	        	 var  exports=$.extend($.fn.domain.Sysbtn2,{
	             	title:"导出",
	            	text:"导出",
	            	iconCls:"icon-export",
	        		handler: function() {
	        			var queryParams = $form.serialize();
	                    var url = "${pageContext.request.contextPath}/youku/albums/exportalbum?"+ queryParams;
	                    window.location.href = url;
	                } 
	        	});
	        	 
	        	 //重新获取视频信息 
	        	 function showgetvideo(showid){
	        		 var  url= '${pageContext.request.contextPath}/jsonYouku/showget/'+showid;	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{showid:showid},
		               	dataType:"JSON",
		               	success:function(result){ 
		        	            top.$.messager.alert("信息", "获取成功", "info", null, 2000);
		        	            return;
		               	}
		  			 })
	        	 }
        </script>
    </body>
</html>
