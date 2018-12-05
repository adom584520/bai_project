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
a.l-btn span span.l-btn-text{
   width:60px;
}
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:0px; width:auto;">
         	<input type="hidden" id="seriesCode" name="seriesCode" value="${seriesCode}"/> 
         	
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="phonealbumVideo"></table>
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
            	 var seriesCode="${seriesCode}";
            	 
                var t = $('#phonealbumVideo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.edit,'-',sysBtn,'-',sysBtnz,'-',sysBtns,'-'];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/inject/injectPhoneFail/page',
                    	queryParams: queryParams,
                    	//toolbar:customToolbar,
                    	idField:"seriesCode",
                    	singleSelect:true,
                        columns: [[
	                    	{field: 'seriesCode', title: '专辑Id', width: 100,   align: 'center', hidden: false}
	                    	,{field: 'id', title: 'id', width: 100,   align: 'center', hidden: true}
	                    	,{field: 'seriesName', title: '专辑名称', width: 200,   align: 'left', hidden: true}
	                    	,{field: 'drama', title: '剧集', width: 100,  align: 'center', hidden: false}
	                    	,{field: 'dramaname', title: '剧集名称', width: 200,  align: 'center', hidden: false}
	                    	,{field: 'update_time', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
								}}
	                    	,{field:'zxPriority',title:'优先级',width:100,align:'center',formatter:showPriority}
	                    	,{field: 'zxInjectState',  title: '中兴注入状态', width: 100,  align: 'center', hidden: false,formatter: showState}
	                    	,{field: 'hwInjectState',  title: '华为注入状态', width: 100,  align: 'center', hidden: false,formatter: showState}
	                    	,{field: 'operate',  title: '操作', width: 200,  align: 'center', hidden: false,formatter: showOperateZx}
	                    	,{field: 'operateHw',  title: '操作', width: 200,  align: 'center', hidden: false,formatter: showOperateHw}
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
                $table = $("#phonealbumVideo");
            	$chnId=$("#channel");
    			$tagsids=$("#label");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) 
                	//$('#phonealbumVideo').domain('load',{seriesCode:$('#seriesCode').val()});
                	getalbumin();
                
            });
        	
        	function showPriority(value,row,index){
        		var str="";
        		//var btnPriority="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+")>设置优先级</button>";
        		switch(value){
        			case 1:
        				str="普通优先级";
        				break;
        			case 2:
        				str="中级优先级";
        				break;
        			case 3:
        				str="高级优先级";
        				break;
        		}
        		return str;
        	}
        	
        	 function showState(value, row, index){
			     var str="";
			     switch(value){
			     	case 1:
			     		str="<span style='color:green'>注入成功</span>";
			     		break;
			     	case -1:
			     		str="<span style='color:red'>注入失败</span>";
			     		break;
			     	case 2:
			     		str="正在注入";
			     		break;
			     	case 3:
			     		str="已发送";
			     		break;
			     	case 0:
			     		str="未发送";
			     		break;			     		
			     }
			     
			   /*   if(value==-1){
			    	 str+="&nbsp;&nbsp;&nbsp;";
			    	 str+="<button onclick=reInject("+row.id+")>重注入</button>";
			     }
			     
				 str+="&nbsp;&nbsp;&nbsp;";
			     str+="<button onclick=deleteFromCdn("+row.id+")>删除</button>"; */
			    
			   // ckButton += "&nbsp;&nbsp;&nbsp;<button  onclick='editvideo(\""+row.id+"\")' class='easyui-linkbutton'>编辑剧集</button>";
			     return str;
	   		}
        	
        	 
        		function showOperateZx(value,row,index){
            		var str="";
            		if(row.zxInjectState==-1){
    	        		str+="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+")>设置优先级</button>";
    	        		if(value==-1){
    			    	 str+="&nbsp;&nbsp;&nbsp;";
    			    	 str+="<button onclick=reInject("+row.id+")>重注入</button>";
    			     	}
    			     
    			     	str+="&nbsp;&nbsp;&nbsp;";
    			     	str+="<button onclick=reInject("+row.id+")>注入</button>";
            		}else{
            			str="";
            		}
    		     	return str;
            	}
            	
            	
            	function showOperateHw(value,row,index){
            		var str="";
            		if(row.hwInjectState==-1){
    	        		str+="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+")>设置优先级</button>";
    	        		if(value==-1){
    			    	 str+="&nbsp;&nbsp;&nbsp;";
    			    	 str+="<button onclick=reInject("+row.id+")>重注入</button>";
    			     	}
    			     
    			     	str+="&nbsp;&nbsp;&nbsp;";
    			     	str+="<button onclick=reInject("+row.id+")>注入</button>";
            		}else{
            			str+="";
            		}
    		     	return str;
            	}
        	 

	        	  //选择标签
	        function choosechannellabel(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/chooselabel",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:2,channelCode:value},
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

	        	 
	        	function showPriorityDialog(id){
	        		$(parent).domain("openDialog",{
	        			iconCls:'icon-view',
	        			title:'设置优先级',
	        			src:"${pageContext.request.contextPath}/inject/injectPhoneZx/showVideoPriority?id="+id,
	        			width:400,
	        			height:200,
	        			onClose:function(){
	        				$('#phonealbumVideo').datagrid('load',{seriesCode:$('#seriesCode').val()});
	        			}
	        		});
	        	}
	        	
	        	function showVideoDialog(id){
	        		$(parent).domain("openDialog",{
	        			iconCls:'icon-view',
	        			title:'设置剧集优先级',
	        			src:"${pageContext.request.contextPath}/inject/injectPhoneZx/showVideoPriority?id="+id,
	        			width:400,
	        			height:200,
	        			onClose:function(){
	        				$('#phonealbumVideo').datagrid('load',{});
	        			}
	        		});
	        	}
	        
	        	function reInject(id){
	        		$.messager.alert("提示","已经重新注入？",'info');
	        	}	
	        	
	        	function deleteFromCdn(id){
	        		$.messager.alert("提示","删除成功",'info');
	        	}
	        
	        	 //编辑剧集
	        	 function editvideo(code){
		         		$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "编辑剧集", 
		     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/editvideo/"+code,
		     	        	width: 1060, 
		    	        	height: 550,
		     	        	onClose: function() { 
		     	            }
		     	        });
		         	}
	        	//节目关联推荐
	        	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
	             	title:"关联推荐",
	            	text:"关联推荐",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	if(nodes.length>1){
	        	    		 top.$.messager.alert("信息", "请您选择一条专辑信息", "info", null, 2000);
	        	    		 return;
	        	    	}	        	    	 
	        	    	var  id= nodes[0]["id"];
	        	    	$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/albuminforecommand/"+id,
		     	        	width: 1250, 
		    	        	height: 650,
		     	        	onClose: function() {
		     	        	 	
		     	            }
		     	        });
	                },
	                scope:"one"
	        	});	 
	        	
	        	  	//绑定角标
	        	 var  sysBtnz=$.extend($.fn.domain.Btnsys,{
	             	title:"角标绑定",
	            	text:"角标绑定",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 

	        	    	var ids = "";
               	        for (var i = 0; i < nodes.length; i++) {
            	            ids+=nodes[i]["id"]+",";
            	        }
	        	    	$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/vodCorner/albuminfo/phoneCorner/"+ids,
		     	        	width: 650, 
		    	        	height: 400,
		     	        	onClose: function() {
		     	        	 	
		     	            }
		     	        });
	                },
	                scope:"one"
	        	});
	        	
	        	//绑定付费包
	        	 var  sysBtns=$.extend($.fn.domain.Btnsys,{
	             	title:"付费绑定",
	            	text:"付费绑定",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids = "";
               	        for (var i = 0; i < nodes.length; i++) {
            	            ids+=nodes[i]["id"]+",";
            	        }
               	     $(parent).domain("openDialog", {
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/vodCollectfeesbag/albuminfo/phoneCollectfeesbag/"+ids,
		     	        	width: 650, 
		    	        	height: 400,
		     	        	onClose: function() {
 	     	        		
		     	            }
		     	        });   
	                },
	                scope:"one"
	        	});
	        	
        </script>
    </body>
</html>
