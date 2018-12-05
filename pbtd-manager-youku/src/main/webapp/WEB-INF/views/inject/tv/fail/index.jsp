<!--
        *  管理页面
        * 
        * @author shenjr
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
                	//var customToolbar = [$.fn.domain.edit,'-',sysBtn,'-',sysBtnz,'-',sysBtns,'-'];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/inject/injectTvFail/page',
                    	queryParams: queryParams,
                    	//toolbar:customToolbar,
                    	idField:"seriesCode",
                    	singleSelect:true,
                        columns: [[
	                    	{field: 'seriesCode', title: '专辑Id', width: 100,   align: 'center', hidden: false}
	                    	,{field: 'id', title: 'id', width: 100,   align: 'center', hidden: true}
	                    	,{field: 'seriesName', title: '专辑名称', width: 200,   align: 'left', hidden: true}
	                    	,{field: 'drama', title: '剧集', width: 50,  align: 'center', hidden: false}
	                    	,{field: 'dramaname', title: '剧集名称', width: 200,  align: 'center', hidden: false}
	                    	,{field: 'create_time', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
								}}
	                    	,{field:'zxPriority',title:'中兴优先级',width:100,align:'center',formatter:showPriority}
	                    	,{field:'hwPriority',title:'华为优先级',width:100,align:'center',formatter:showPriority}
	                    	,{field: 'zxInjectState',  title: '中兴注入状态', width: 100,  align: 'center', hidden: false,formatter: showState}
	                    	,{field: 'hwInjectState',  title: '华为注入状态', width: 100,  align: 'center', hidden: false,formatter: showState}
	                    	,{field: 'operate',  title: '中兴操作', width: 200,  align: 'center', hidden: false,formatter: showOperateZx}
	                    	,{field: 'operateHw',  title: '华为操作', width: 200,  align: 'center', hidden: false,formatter: showOperateHw}
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
    	        		str+="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+",'zx')>设置优先级</button>";
    			    	str+="&nbsp;&nbsp;&nbsp;";
    			    	str+="<button onclick=sendInject("+row.id+","+row.vodId+",'zx')>重注入</button>";
    			     
            		}else{
            			str="";
            		}
    		     	return str;
            	}
            	
            	
            	function showOperateHw(value,row,index){
            		var str="";
            		if(row.hwInjectState==-1){
    	        		str+="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+",'hw')>设置优先级</button>";
    			     
    			     	str+="&nbsp;&nbsp;&nbsp;";
    			     	str+="<button onclick=sendInject("+row.id+","+row.vodId+",'hw')>注入</button>";
            		}else{
            			str+="";
            		}
    		     	return str;
            	}
        	 

	        	 
	        	function showPriorityDialog(id,type){
	        		$(parent).domain("openDialog",{
	        			iconCls:'icon-view',
	        			title:'设置优先级',
	        			src:"${pageContext.request.contextPath}/inject/injectTvFail/showVideoPriority?id="+id+"&type="+type,
	        			width:400,
	        			height:200,
	        			onClose:function(){
	        				getalbumin();
	        			}
	        		});
	        	}
	        	
	        	function sendInject(id,vodId,type){
	        		$.ajax({
	        			type:'post',
	        			url:'${pageContext.request.contextPath}/inject/injectTvFail/SendOutAlbumVideo',
	        			data:{
	        				id:id,
	        				vodId:vodId,
	        				type:type
	        			},
	        			dataType:'json',
	        			success:function(data){
	        				if(data!=null){
								if(data==1){
									$.messager.alert('提示','已发送','info');
									getalbumin();
									//$(parent).domain('closeDialog');
									
								}else if(data==-1){
									$.messager.alert('提示','发送失败','info');
								}
							}
	        			}
	        			
	        		});
	        	}	
	        	
	    
	        	
	        	
	        	
	        	
	        	function deleteFromCdn(id){
	        		$.messager.alert("提示","删除成功",'info');
	        	}
	        
	        
        </script>
    </body>
</html>
