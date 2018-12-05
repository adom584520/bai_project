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
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 60px;
}
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:74px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            		<span class="property"> <label class="">名称：</label> <input
				type="text" id="name" name="name" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="actorDisplay" name="actorDisplay" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">主演：</label> <input
				type="text" id="writerDisplay" name="writerDisplay" style="width: 200px;" />
			</span>
			<br/><hr/>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="programTypeids" name="programTypeids" onchange="choosechannellabel(this.value);" style="width: 120px;">
					  <option></option>
					</select>
			</span>
			<span class="property"> <label class="">标签：</label>
					<select  id="programType2ids" name="programType2ids" style="width: 120px;">
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
					<select  id="name2" name="name2" style="width: 120px;">
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
            	
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodchannels"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	     	 var customToolbar = [ Storagebtn];
                	t.domain('datagrid', {
                        title: '国广源数据管理',
                    	url: '${pageContext.request.contextPath}/cibn/albums/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                    	idField:"code",
                        columns: [[
	                    	{field: 'code', title: '唯一标识', width: 220,   align: 'center', hidden: false}
	                    	,{field: 'name', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'actorDisplay', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'writerDisplay', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'programType', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'programType2', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'volumnCount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentNum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	//,{field: 'viewPoint', title: '看点', width: 120, sortable: true, align: 'left', hidden: false}
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
        	                    subject: '频道管理',
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
                $table = $("#vodchannels");
            	$chnId=$("#programTypeids");
    			$tagsids=$("#programType2ids");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
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
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.isStorage;
			     if(status=="1"){
			    	 ckButton = "<a name='show' onclick='show(\""+row.code+"\")' class='easyui-linkbutton'>查看</a>";
			     }else{
			    	 ckButton = "<a name='show' onclick='show(\""+row.code+"\")' class='easyui-linkbutton'>查看</a>";
			    	 //ckButton = "<a name='show' onclick='javascript:show("+ row.code + ")' class='easyui-linkbutton'>入库对比</a>";
			    	 
			     }
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
        	            ids+=","+nodes[i]["code"];
        	        }
           	     savestatus(ids.substr(1,ids.length),2)
                },
                scope:"one,more"
        	});
        	 function savestatus(ids,type){
        		 var  url= '${pageContext.request.contextPath}/guoguang/strategy/albuminfoid';	  	 
	  			 $.ajax({
	               	url:url,
	               	type:"POST",
	               	data:{id:ids,cp:2,type:type},
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
     	        	src: "${pageContext.request.contextPath}/cibn/albums/show/"+code,
     	        	width: 1060, 
    	        	height: 550,
     	        	onClose: function() { 
     	            }
     	        });
         	}
        	 
	  		  //选择频道
	        	function choosechannel(){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelgg",
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
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannellabelgg",
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
