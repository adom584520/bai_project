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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:74px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            <span class="property"> <label class="">专辑名称：</label> <input
				type="text" id="seriesName" name="seriesName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">更新时间：</label>
					 <input id="update_time" name="update_time" type="text" class="easyui-datebox">
			 </span>
			<span class="property"> 
			<label class="">是否收費：</label>
					<select  id="isCollectfees" name="isCollectfees" style="width: 120px;">
						<option value="">--全部--</option>
						<option value=1>收費</option>
						<option value=0>不收費</option>		  
					</select>
			</span>
			 <span class="property"> 
			<label class="">优先级：</label>
					<select  id="zxPriority" name="zxPriority"  style="width: 120px;">
						<option value="">--全部--</option>
						<option value=1>普通优先级</option>
						<option value=2>中级优先级</option>
						<option value=3>高级优先级</option>
					</select>
			</span>
			 <span class="property"> <label class="">状态：</label>
				<select  id="zxInjectState" name="zxInjectState" style="width: 120px;">
					  <option value="">--全部--</option>
					  <option value=0>未发送</option>
					  <option value=3>已发送</option>
					  <option value=2>正在注入</option>
					  <option value=1>注入成功</option>
					  <option value=-1>注入失败</option>
					  <!-- 
					  <option value=-10>暂停注入</option>
					   -->
				</select>
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
                	//var customToolbar = [$.fn.domain.edit,'-',sysBtn,'-',sysBtnz,'-',sysBtns,'-'];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/inject/injectTvZx/page',
                    	queryParams: queryParams,
                    //	toolbar:customToolbar,
                    	idField:"seriesCode",
                    	singleSelect:true,
                        columns: [[
	                    	{field: 'seriesCode', title: '专辑Id', width: 100,   align: 'center', hidden: false}
	                    	,{field: 'id', title: 'id', width: 100,   align: 'center', hidden: true}
	                    	,{field: 'seriesName', title: '专辑名称', width: 200,   align: 'left', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'update_time', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
								}}
	                    	,{field:'isCollectfees',title:'是否收费',width:100,align:'center',formatter:showCollectfees}
	                    	,{field:'zxPriority',title:'优先级',width:100,align:'center',formatter:showPriority}
	                    	,{field: 'zxInjectState',  title: '注入状态', width: 100,  align: 'center', hidden: false,formatter: showState}
	                    	,{field: 'operate',  title: '操作', width: 200,  align: 'center', hidden: false,formatter: showOperate}
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
            	$chnId=$("#channel");
    			$tagsids=$("#label");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getalbumin();
                
            });
        	
        	function showCollectfees(value,row,index){
        		var str="";
        		switch(value){
        			case 1:
        				str="收费";
        				break;
        			case 0:
        				str="不收费";
        				break;
        		}
        		return str;
        	}
        	
        	
        	function showPriority(value,row,index){
        		var str="";
        		//var btnPriority="&nbsp;&nbsp;&nbsp;<button onclick=showPriorityDialog("+row.id+")>设置优先级</button>";
        		//var btnShowVideo="&nbsp;&nbsp;&nbsp;<button onclick=showVideoDialog("+row.seriesCode+")>设置剧集优先级</button>";
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
        	 
        	 
        	 
        	 
        	 function showOperate(value,row,index){
        		 var str="";
        		 var zxInjectState=row.zxInjectState;
        		 var btnShowVideo="&nbsp;&nbsp;&nbsp;<button onclick=showVideoDialog("+row.seriesCode+")>剧集优先级</button>";
        		 var str="";

        		 str+="&nbsp;&nbsp;&nbsp;";
			     str+="<button onclick=deleteFromCdn("+row.id+")>删除</button>";
        		 
        		/*  if(zxInjectState==-1){
			    	 str+="&nbsp;&nbsp;&nbsp;";
			    	 str+="<button onclick=reInject("+row.id+")>重注入</button>";
			     } */

			     return btnShowVideo+str;
        	 }

	        	 
	        	function showPriorityDialog(id){
	        		$(parent).domain("openDialog",{
	        			iconCls:'icon-view',
	        			title:'设置优先级',
	        			src:"${pageContext.request.contextPath}/inject/injectTvZx/showPriority?id="+id,
	        			width:400,
	        			height:200,
	        			onClose:function(){
	        				$('#phonealbuminfo').datagrid('load',{});
	        			}
	        		});
	        	}
	        	
	        	function showVideoDialog(seriesCode){
	        		$(parent).domain("openDialog",{
	        			iconCls:'icon-view',
	        			title:'设置剧集优先级',
	        			src:"${pageContext.request.contextPath}/inject/injectTvZx/videoIndex?seriesCode="+seriesCode,
	        			width:1100,
	        			height:600,
	        			onClose:function(){
	        				$('#phonealbuminfo').datagrid('load',{});
	        			}
	        		});
	        	}
	        
	        	function reInject(id){
	        		
	        	}	
	        	
	        	function deleteFromCdn(id){
	        		$.messager.alert("提示","删除成功",'info');
	        	}
	        
	       
	        	
        </script>
    </body>
</html>
