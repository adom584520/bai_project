<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title> 管理</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
         <script type="text/javascript" src="/js/common/Bootstrap/js/jqueryV1.9.1.js"></script>
        <script type="text/javascript" src="/js/common/Bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="/js/common/Messager/message.min.js"></script>
        <script type="text/javascript" src="/js/common/layer/layer.js"></script>
        <link rel="stylesheet" href="/js/common/Bootstrap/css/bootstrap.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="/js/common/Messager/message.css">
    </head>
    <body>
       <div style="width:90%;height:100%;">
         	<form id="formQuery" method="post"> 
				<div class="form-group">
			    	<label for="channel" class="col-sm-2 control-label">频道ID：</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" id="channel" value="${channel}">
			    	</div>
			    	
			    <%-- 	<label for="moduleId" class="col-sm-2 control-label">模版ID：</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" id="moduleId" value="${moduleId}">
			    	</div> --%>
			    	
			    	<label for="name" class="col-sm-2 control-label">名称：</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" id="name" value="${Name}" placeholder="请输入名字">
			    	</div>
			    	
			    	<label for="masterplateId" class="col-sm-2 control-label">模版选择：</label>
					<div class="dropdown col-sm-10">
						<button type="button" class="btn dropdown-toggle" id="masterplateId" 
								data-toggle="dropdown">
							[请选择模板]
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
							<c:forEach items="${masterplatelist}" var="map">
								<li role="presentation">
									<a role="menuitem" tabId="${map.id}" href="#">${map.masterplatename}</a>
								</li>
							</c:forEach>
						</ul>
					</div>

				    
				<%-- 		<select id="masterplateId" name="masterplateId"  style="width: 180px;" class="chzn-select">
						<c:forEach items="${masterplatelist}" var="map">
						 <option value="${map.id }"  <c:if test="${Masterplateid eq map.id}">  selected="selected"</c:if>>${map.masterplatename }</option>
						</c:forEach>
						</select> --%>
				<div class="col-sm-10">
					<button type="button" class="btn btn-default" id="submitBtn" >提交</button>	
				</div>
                 
            </form>
        </div>
        
        <script type="text/javascript"> 
        	$(document).ready(function(){
        		$('.dropdown-menu li').on('click', function(e) {
    	        	var $target = $(e.target);
    	        	$('#masterplateId').text($target.text());
    	        	$('#masterplateId').attr('tabId',$target.attr('tabId'));
    	        })	
    	       
    	        
    	        $('#submitBtn').on('click',function(){
    	        	var channel=$('#channel').val();
    	        	var name=$('#name').val();
    	        	var masterplateId=$('#masterplateId').attr('tabId');
    	        	if(name==null || name==""){
    	        		$.message({
    	        			message:'名称未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
    	        	}
    	        	if(masterplateId==null || name==""){
    	        		$.message({
    	        			message:'模板未选择',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
    	        	}
    	        	
    	        	$.ajax({
    	        		url:'${pageContext.request.contextPath}/vod/tv/vodmouldinfo/add',
    	        		type:'post',
    	        		data:{
    	        			channel:channel,
    	        			name:name,
    	        			masterplateId:masterplateId
    	        		},
    	        		success:function(data){
    	        			if(data!=null){
    	        				if(data.code==1){
    	        					$.message({
    	        	        			message:data.message,
    	        	        			type:'success'
    	        	        			
    	        	        		});
									/* console.log(parent); */
    	        					parent.refreshTree(null);
    	        					setTimeout("parent.layer.closeAll('iframe')",500);
    	        				}else{
    	        					$.message({
    	        	        			message:data.message,
    	        	        			time:1000,
    	        	        			type:'error'
    	        	        		});
    	        				}
    	        			}
    	        		}
    	        	});
    	        });
        	});
        	
        		
  	
        </script>
    </body>
</html>
	 