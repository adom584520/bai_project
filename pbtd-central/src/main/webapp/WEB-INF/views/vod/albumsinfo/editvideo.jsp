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
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:36px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
		            <input type="hidden" id="seriesCode" name="seriesCode"/>
		               <input type="hidden" id="centralcode" name="centralcode"/>
		            <span class="property"> <label class="">名称：</label> <input
						type="text" id="dramaname" name="dramaname" style="width: 200px;" />
					</span>
		            		<span class="property"> <label class="">剧集：</label> <input
						type="text" id="drama" name="drama"  class="easyui-numberbox"  style="width: 120px;" />
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
                var t = $('#vodalbumvinfoideo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',sys1Btn];
                	t.domain('datagrid', {
                        title: '剧集数据',
                    	url: '${pageContext.request.contextPath}/vod/albuminfo/videopage',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"id",
                        columns: [[
	                    	{field: 'id', title: '唯一标识', width: 220,   align: 'center', hidden: true}
	                    	,{field: 'dramaname', title: '名称', width: 80,   align: 'left', hidden: false}
	                    	,{field: 'drama', title: '剧集', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'dramaviewPoint', title: '看点', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'duration', title: '时长', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'isCollectfees', title: '是否vip', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			if(value=="1"){
	                    				return '是';
	                    			}else{
	                                 return '否';     
	                    			}
								}}
	                    	,{field: 'updatetime', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
								}}
	                    	,{field: 'status',  title: '操作', width: 160,  align: 'center', hidden: false,formatter: getBtn}
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
                document.getElementById("seriesCode").value="${seriesCode}"; 
                document.getElementById("centralcode").value="${centralcode}"; 
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
            });
        	
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			    	 ckButton = "<button  onclick='show(\""+row.id+"\")' class='easyui-linkbutton'>查看</button>";
			     return ckButton;
	   		}
	        	     function show(id){
						$(parent).domain("openDialog", { 
				        	iconCls: "icon-view", 
				        	title: "查看", 
				        	src: "${pageContext.request.contextPath}/vod/albuminfo/showvideo/"+id,
				        	width: 760, 
				        	height: 450,
				        	onClose: function() { 
				            }
				        });
					}
	        	     
	        	 	$.fn.domain.create={
	            	    	id: "btnCreate",
	            	        text: "新建",
	            	        iconCls: "icon-add",
	            	        disabled: false,
	            	        handler: function() {
	            	        	var t = $(this).parent().next().find(">table:hidden");
	            	            var opts = t.datagrid("options");
	            	            var title = opts.title || opts.subject;
	            	            var url = "${pageContext.request.contextPath}/vod/albuminfo/video/create/${seriesCode}/${centralcode}";
	            	            $(parent).domain("openDialog", { 
	            	            	iconCls: "icon-add", 
	            	            	title: "新建 " + title, 
	            	            	src: url, 
	            	            	width: opts.openWidth-70, 
	            	            	height: opts.openHeight-150, 
	            	            	onClose: function() { 
	            	            		if (top.$.data(top.document.body, "domain.create.refresh")) {
	            	            			top.$.removeData(top.document.body, "domain.create.refresh");
	            	            			t.datagrid("reload");
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
	        	    	var ids="";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=",'"+nodes[i]["id"]+"'";
	        	        }
	           	     deletes(ids.substr(1,ids.length),2)
	                },
	                scope:"one,more"
	        	});
	        	 function deletes(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/albuminfo/video/deletes';	  	 
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
	         
	        	  
	        	 
        </script>
    </body>
</html>
