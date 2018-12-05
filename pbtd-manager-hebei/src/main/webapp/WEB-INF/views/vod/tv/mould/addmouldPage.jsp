<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title> 管理</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=10" />
         <script type="text/javascript" src="/js/common/Bootstrap/js/jqueryV1.9.1.js"></script>
        <script type="text/javascript" src="/js/common/Bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="/js/common/Messager/message.min.js"></script>
        <script type="text/javascript" src="/js/common/layui/layui.all.js"></script>
        <link rel="stylesheet" href="/js/common/Bootstrap/css/bootstrap.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="/js/common/Messager/message.css">
        <link rel="stylesheet" type="text/css" href="/js/common/layui/css/layui.css">
       	<style type="text/css">
       		.layui-form-label{
       			width:130px;
       		}
       		.layui-input-block{
       			margin-left:160px;
       		}
       		
       	</style>
    </head>
    <body>
       <div style="width:100%;height:100%;">
         	<form id="formQuery" class="layui-form" method="post"> 
         	  <input type="hidden" name="moduleId" value="${moduleId}"/>
         	  <input type="hidden" name="modulepic" id="modulepic" value="${modulepic}"/>
			  <div class="layui-form-item">
			    <label class="layui-form-label">频道ID：</label>
			    <div class="layui-input-block">
			      <input type="text" name="channel" id="channel" required  lay-verify="required" placeholder="请输入频道" autocomplete="off" class="layui-input" value="${channel}" disabled>
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">名称：</label>
			    <div class="layui-input-block">
			      <input type="text" name="name" id="name" required  lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input" value="${Name}">
			    </div>
			  </div>
			  <div class="layui-form-item">
			      <label class="layui-form-label">模板选择：</label>
			      <div class="layui-input-block">
					  <select name="masterplateId" id="masterplateId" lay-verify="validSelected">
						  <option value="">[请选择模板]</option>
						  <c:forEach items="${masterplatelist}" var="map">
						  	<option value="${map.id}" 
						  		<c:if test="${masterplateid eq map.id}">
						  			selected
						  		</c:if>
						  		>${map.masterplatename}</option>
						  </c:forEach>
					  </select>
				  </div>
			  </div>	
			  <div class="layui-form-item">
			      <label class="layui-form-label">模板图片：</label>
			      <div class="layui-input-block">	
					<button type="button" class="layui-btn layui-btn-sm" id="modulepicBtn">
					  <span id="uploadImg">
					  	<i class="layui-icon">&#xe67c;</i>上传图片
					  </span>
					  <span id="uploadedImg" style="display:none">
					  	<i class="layui-icon layui-icon-ok"></i>已上传
					  </span>
					</button>	
		  		  </div>
			  </div>    
			   <div class="layui-form-item">
			      <label class="layui-form-label">模块图片是否使用：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="picstatus" id="picstatus" lay-skin="switch" lay-text="使用|不使用" value="1"
						<c:if test="${picstatus eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item">
			      <label class="layui-form-label">模块是否上线：</label>
			      <div class="layui-input-block">
					<input type="checkbox" name="modulestatus" id="modulestatus" lay-skin="switch" lay-text="上线|下线" value="1"
						<c:if test="${empty modulestatus || modulestatus eq 1}">
							checked
						</c:if>	
					>
		  		  </div>
			  </div> 
			  <div class="layui-form-item">
			      <label class="layui-form-label">左标签是否显示：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="isshowleft" id="isshowleft" lay-skin="switch" lay-text="显示|不显示" value="1"
						<c:if test="${isshowleft eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item">
			      <label class="layui-form-label">右标签是否显示：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="isshowright" id="isshowright" lay-skin="switch" lay-text="显示|不显示" value="1"
						<c:if test="${isshowright eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			
			  <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn  layui-btn-sm" lay-submit lay-filter="formDemo" id="submitBtn11">立即提交</button>
			    </div>
			  </div>
          	</form>
        </div>
        
        <script type="text/javascript"> 
        
        	var form = layui.form;
        	form.render();
        	
        	  //监听提交
        	  form.on('submit(formDemo)', function(data){
        	    //layer.msg(JSON.stringify(data.field));
        	    console.log(data.field);
        	    var moduleId=data.field.moduleId;
        	    var channel=$("#channel").val();
        	    var name=$("#name").val();
        	    var masterplateId=$("#masterplateId").val();
        	    var modulepic=$("#modulepic").val();
        	    var picstatus=data.field.picstatus==undefined?0:data.field.picstatus;
        	    var modulestatus=data.field.modulestatus==undefined?0:data.field.modulestatus;
        	    var isshowleft=data.field.isshowleft==undefined?0:data.field.isshowleft;
        	    var isshowright=data.field.isshowright==undefined?0:data.field.isshowright;
        	    
        	    $.ajax({
	        		url:'${pageContext.request.contextPath}/vod/tv/vodmouldinfo/add',
	        		type:'post',
	        		data:{
	        			moduleId:moduleId,
	        			channel:channel,
	        			name:name,
	        			masterplateId:masterplateId,
	        			modulepic:modulepic,
	        			picstatus:picstatus,
	        			modulestatus:modulestatus,
	        			isshowleft:isshowleft,
	        			isshowright:isshowright
	        		},
	        		success:function(data){
	        			console.log(data);
	        			if(data!=null){
	        				if(data.code==1){
								/* console.log(parent); */
	        					parent.refreshTree(null);
	        					setTimeout("parent.layer.closeAll('iframe')",500);
	        				}else{
	        					
	        				}
	        			}
	        		}
	        	});
        	    return false;
        	  });
        
	        var upload = layui.upload;
	        //执行实例
	        var uploadInst = upload.render({
	          elem: '#modulepicBtn', //绑定元素
	          url: '/fileupload/image', //上传接口
	          accept:'images',
	          acceptMime:'image/*',
	          done: function(resp){
	            console.log(resp);
	            if(resp!=null){
	            	imgUrl=resp.message;
	            	if(imgUrl!=null){
	            		$('#modulepic').val(imgUrl);
	            		$('#uploadImg').hide();
	            		$('#uploadedImg').show();
	            	}
	            }
	          }
	          ,error: function(){
	            console.log("请求异常回调");
	          }
	        });
        
	        form.verify({
	        	validSelected:function(value,item){
	        		if(value=="" || value==undefined){
	        			return "您未选择模板";
	        		}
	        	}
	        });
        
        
        	$(document).ready(function(){
        		
        		var modulepicValue=$("#modulepic").val();
        		if(modulepicValue!=''){
            		$('#uploadImg').hide();
            		$('#uploadedImg').show();
        		}
        		
        		
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
    	        
    	        });
        	});
        	
        		
  	
        </script>
    </body>
</html>
	 