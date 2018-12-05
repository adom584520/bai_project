 

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
        .datagrid-view{
        	height: 400px;
        }
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
   <%--  <c:import var="data" url="/vod/tv/vodmouldinfo/NewFile" />
${data} --%>
       <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:380px; width:auto;">
         	<form id="formQuery" method="post" action ="${pageContext.request.contextPath}/vod/tv/vodmouldinfo/add"> 
            		<table>
	            		<tr>
		            		<span class="property">
		            			<label class="">频道ID：</label>
			            		<input type="text" id="channel" name="channel" value="${channel}" style="width:200px;" onfocus=this.blur()/>
							</span>
	            		</tr>
	            		 <tr>
		            		<span class="property"  style="display:none">
		            			<label class="">模版ID：</label>
			            		<input type="text" id="moduleId" name="moduleId" value="${moduleId}" style="width:200px;"/>
							</span>
	            		</tr>
            		<br/> 
            		<tr>
            			<span class="property">
            				<label class="">名称：</label>
            				<input type="text" id="name" name="name"  value="${Name}" style="width:200px;"/>
            			</span>	
            		</tr>
            		<br/>
            		<tr>
            		<span class="property"> <label class="">模版选择：</label>
							<select id="masterplateId" name="masterplateId"  style="width: 180px;" class="chzn-select">
							<c:forEach items="${masterplatelist}" var="map">
							 <option value="${map.id }"  <c:if test="${Masterplateid eq map.id}">  selected="selected"</c:if>>${map.masterplatename }</option>
							</c:forEach>
							</select>
				 		</span>
            		</tr>
            		<br/>
            		<tr>
            		<span class="property">
            		 <label class="">对改模块的描述：</label>
							<input type="text" id="describes" name="describes"  value="${Describes}" style="width:200px;"/>
				 		</span>
            		</tr>
            		<br/>
            		<tr>
            		<span class="property">
            		 <label class="">排序：</label>
							<input type="text" id="sequence" name="sequence"  value="${Sequence}" style="width:200px;"/>
				 		</span>
            		</tr>
            		</table>
                <input type ="submit" value ="提交">
            </form>
        </div>
        <div data-options="region:'center',border:true" style="padding:0px;border: 1px solid #617775;">
            <table id="vodchannel" ></table>
        </div>  
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#vodchannel");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	function updateStatus(ids,status){
        		$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/updateStatus",
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{status:status,id:ids},
                  	async:false,
                  	success:function(){
                  		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
                  		$("#vodchannel").datagrid("load",{});               		
                  	},
                  	error:function(XHR, status, errorThrow){
                  		
                  	}
                  }); 
        	}

       	 //当天更新同步
	          	var sysBtnreset=$.extend($.fn.domain.reset,{
	          		title:'当天同步',
	          		text:'当天同步',
	          		iconCls:"icon-reset",
	          		handler:function(){
	          			$.ajax({
	                        	url:"${pageContext.request.contextPath}/tvreset/resetchannel",
	                        	type:"GET",
	                        	dataType:"JSON",
	                        	data:{},
	                        	async:false,
	                        	success:function(){
	                        		 top.$.messager.alert("信息", "同步成功！", "info", null, 2000); 
	                        	},
	                        	error:function(XHR, status, errorThrow){
	                        		
	                        	}
	                        }); 
	          		},
	          		scope:"all"
	          	})
	          	
        </script>
    </body>
</html>
	 