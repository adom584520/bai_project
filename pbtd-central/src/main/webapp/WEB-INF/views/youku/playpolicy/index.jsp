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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:72px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
         	<span class="property"> <label class="">优酷专辑标识：</label>
            	 <input	type="text" id="showid" name="showid" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">名称：</label>
            	 <input	type="text" id="name" name="name" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">剧集类型：</label> 
					<select  id="isPositive" name="isPositive" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >正片</option>
					    <option value="0">非正片</option>
					</select>
			</span>
			</br>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="categoryId" name="categoryId"   style="width: 120px;">
					  <option></option>
					</select>
			</span>
			<span class="property"> <label class="">类别：</label> 
					<select  id="type" name="type" style="width: 120px;">
					    <option value="" selected="selected">全部</option>
					    <option value="1">专辑</option>
					    <option value="2" >剧集</option>
					</select>
			</span>
			<span class="property"> <label class="">状态：</label> 
					<select  id="play" name="play" style="width: 120px;">
					    <option value="">全部</option>
					    <option value="1" >解控</option>
					    <option value="0">播控</option>
					</select>
			</span>
			 <span class="property" > <label class="">更新时间：</label>
			  <input id="start_create_time" name="start_create_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="end_create_time" name="end_create_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
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
                	     	 var customToolbar = [ Storagebtn,'-',exports];
                	t.domain('datagrid', {
                        title: '播控数据管理',
                    	url: '${pageContext.request.contextPath}/youku/playpolicy/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                    	idField:"id",
                        columns: [[
	                    	{field: 'id', title: '标识', width: 220,   align: 'center', hidden: true}
	                    	,{field: 'mediaId', title: '唯一标识', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'title', title: '名称', width: 120,  align: 'left', hidden: false,formatter:gettitle}
	                    	,{field: 'video_stage', title: '剧集', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'play',  title: '播控状态', width: 60,  align: 'center', hidden: false,formatter: getplay}
	                    	,{field: 'video_type', title: '类型', width: 100,  align: 'center', hidden: false}
	                    	,{field: 'type', title: '类型', width: 100,  align: 'center', hidden: true}
	                    	,{field: 'create_time', title: '更新时间', width: 140,  align: 'center', hidden: false}
	                    	]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        } 
                    });
                }
                else {
                    t.datagrid("load", queryParams);
                }
            }
            function gettitle(value, row, index, text){
           	 var   ckButton = "<a href='#' onclick='gettitlealbum(\""+row.mediaId+"\",\""+row.type+"\")'>"+value+"</a>";
           	 return ckButton
           }
   	   
   		 function gettitlealbum(value,type){
   			 var url="${pageContext.request.contextPath}/jsonYouku/gettitlealbum/"+value;
   			 if(type==2 || type=="2"){
   				  url="${pageContext.request.contextPath}/jsonYouku/gettitlealbumvideo/"+value;
   			 }
         		$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "查看", 
     	        	src: url,
     	        	width: 1060, 
    	        	height: 550,
     	        	onClose: function() { 
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
                $table = $("#vodalbum");
            	$chnId=$("#categoryId");
    			$tagsids=$("#sub_categoryId");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
                choosechannel();
            });
        	
        	
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
        	
        	   function getplay(value){
              	 var ckButton='';
              	 if(value=="1"){ 
      		    	 ckButton = "<span style='color: green;'>解控</span>";
      		     }else{
      		    	 ckButton = "<span style='color: red;'>被播控</span>";
      		     }
              	 return ckButton
              }
       
        	 //批量操作 
        	 var  Storagebtn=$.extend($.fn.domain.btnPhoto,{
             	title:"删除",
            	text:"删除",
        		handler: function() {
        			var nodes =  $table.datagrid("getSelections");
        	    	if (!nodes || nodes.length == 0) {
        	            top.$.messager.alert("信息", "请您选择数据信息", "info", null, 2000);
        	            return;
        	        } 
        	    	var ids="";
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids+=","+nodes[i]["id"];
        	        }
           	     savestatus(ids.substr(1,ids.length))
                },
                scope:"one,more"
        	});
        	 function savestatus(ids){
        		 var  url= '${pageContext.request.contextPath}/youku/playpolicy/deletes';	  	 
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
        	 }
        	 
        	 //批量操作 
        	 var  exports=$.extend($.fn.domain.Sysbtn2,{
             	title:"导出",
            	text:"导出",
            	iconCls:"icon-export",
        		handler: function() {
        			var queryParams = $form.serialize();
                    var url = "${pageContext.request.contextPath}/youku/playpolicy/exportalbum?"+ queryParams;
                    window.location.href = url;
                } 
        	});
        </script>
    </body>
</html>
