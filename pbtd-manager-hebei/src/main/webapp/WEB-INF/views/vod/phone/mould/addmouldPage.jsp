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
       			width:156px;
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
         	  <input type="hidden" name="textrecommendpic" id="textrecommendpic" value="${textrecommendpic}"/>
         	  <input type="hidden" name="picture" id="picture" value="${picture}"/>
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
			      <label class="layui-form-label">模板名称</label>
			      <div class="layui-input-block" >
					  <select name="masterplateId" id="masterplateId" lay-verify="validSelected" disabled="disabled">
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
			      <label class="layui-form-label">标题前小图片：</label>
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
			      <label class="layui-form-label">标题前小图是否使用：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="picstatus" id="picstatus" lay-skin="switch" lay-text="使用|不使用" value="1"
						<c:if test="${picstatus eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item">
			      <label class="layui-form-label">标题文字图片图片：</label>
			      <div class="layui-input-block">	
					<button type="button" class="layui-btn layui-btn-sm" id="textpicBtn">
					  <span id="uploadImg1">
					  	<i class="layui-icon">&#xe67c;</i>上传图片
					  </span>
					  <span id="uploadedImg1" style="display:none">
					  	<i class="layui-icon layui-icon-ok"></i>已上传
					  </span> 
					</button>	
		  		  </div>
			  </div>   
			  <div class="layui-form-item">
			      <label class="layui-form-label">标题文字图片是否使用：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="textpicstatus" id="textpicstatus" lay-skin="switch" lay-text="使用|不使用" value="1"
						<c:if test="${textpicstatus eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item linkdiv">
			      <label class="layui-form-label">模版大图片：</label>
			      <div class="layui-input-block">	
					<button type="button" class="layui-btn layui-btn-sm" id="bigpicBtn">
					  <span id="uploadImg2">
					  	<i class="layui-icon">&#xe67c;</i>上传图片
					  </span>
					  <span id="uploadedImg2" style="display:none">
					  	<i class="layui-icon layui-icon-ok"></i>已上传
					  </span> 
					</button>	
		  		  </div>
			  </div>   
			  <div class="layui-form-item linkdiv" >
			      <label class="layui-form-label">是否使用：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="picturestatus" id="picturestatus" lay-skin="switch" lay-text="使用|不使用" value="1"
						<c:if test="${picturestatus eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			   
			  <div class="layui-form-item">
			      <label class="layui-form-label">模块上下线：</label>
			      <div class="layui-input-block">
					<input type="checkbox" name="modulestatus" id="modulestatus" lay-skin="switch" lay-text="上线|下线" value="1"
						<c:if test="${empty modulestatus || modulestatus eq 1}">
							checked
						</c:if>	
					>
		  		  </div>
			  </div> 
			  <div class="layui-form-item directionclass">
			      <label class="layui-form-label">左标签是否显示：</label>
			      <div class="layui-input-block" id = "leftdianjishijian">	
					<input type="checkbox" name="isshowleft" id="isshowleft" lay-skin="switch" lay-text="显示|不显示" value="1"
						<c:if test="${isshowleft eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			    <div class="layui-form-item linktypeclass">
			      <label class="layui-form-label" >左标签跳转类型：</label>
			      <div class="layui-input-block" id="tiaozhuandianjishijiandiv" >	
					<select id="linktype" name="linktype" >
						<option value="1" <c:if test="${linktype eq 1}">selected="selected"	</c:if>>频道</option>
						<option value="2" <c:if test="${linktype eq 2}">selected="selected"	</c:if>>标签</option>
					</select>
		  		  </div>
			  </div> 
			  <div class="layui-form-item channelclass">
			      <label class="layui-form-label">频道：</label>
			      <div class="layui-input-block" id="channeldianjishijian">	
					<select id="linkchannel" name="linkchannel" >
						  <option value="0" >--请选择--</option>
						  <c:forEach items="${linkchannellist}" var="map">
						  	<option value="${map.channelCode}" 
						  		<c:if test="${linkchannel eq map.channelCode}">
						  			selected="selected"
						  		</c:if>
						  		>${map.channelName}</option>
						  </c:forEach>
					</select>
					</div>
			</div> 
			 <div class="layui-form-item labelclass">
			      <label class="layui-form-label">标签：</label>
			      <div class="layui-input-block">	
					<select id="linklabel" name="linklabel">
				 	<option value="0" >--请选择--</option>
						  <c:forEach items="${linklabelllist}" var="map">
						  	<option value="${map.id}"  
						  		<c:if test="${linklabel eq map.id}">
						  			selected="selected"
						  		</c:if>
						  		>${map.name}</option>
						  </c:forEach> 
					</select>
		  		  </div>
			  </div> 
			  
			  <div class="layui-form-item directionclass"  >
			      <label class="layui-form-label">右标签是否显示：</label>
			      <div class="layui-input-block">	
					<input type="checkbox" name="isshowright" id="isshowright" lay-skin="switch" lay-text="显示|不显示" value="1"
						<c:if test="${isshowright eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item linkdiv modulelinktype ">
			      <label class="layui-form-label" >模块跳转类型：</label>
			      <div class="layui-input-block" id="moduletiaozhuandianjishijiandiv" >	
					<select id="modulelinktype" name="modulelinktype" >
						<option value="0" <c:if test="${modulelinktype eq 0}">selected="selected"</c:if>>跳专辑（默认）</option>
						<option value="1" <c:if test="${modulelinktype eq 1}">selected="selected"</c:if>>跳频道</option>
						<option value="2" <c:if test="${modulelinktype eq 2}">selected="selected"</c:if>>跳专题列表</option>
						<option value="3" <c:if test="${modulelinktype eq 3}">selected="selected"</c:if>>跳转具体专题</option>  
						<option value="4" <c:if test="${modulelinktype eq 4}">selected="selected"</c:if>>跳直播</option>  
						<option value="5" <c:if test="${modulelinktype eq 5}">selected="selected"</c:if>>跳网页</option>  
					</select>
		  		  </div>
			  </div> 
			  
			   <div class="layui-form-item linkdiv modulechannelclass">
			      <label class="layui-form-label">模块跳转频道：</label>
			      <div class="layui-input-block">	
					<select id="modulelinkchannel" name="modulelinkchannel">
						  <option value="0" >--请选择--</option>
						  <c:forEach items="${linkchannellist}" var="map">
						  	<option value="${map.channelCode}" 
						  		<c:if test="${modulelinkchannel eq map.channelCode}">
						  			selected="selected"
						  		</c:if>
						  		>${map.channelName}</option>
						  </c:forEach>
					</select>
					</div>
				</div> 
			  
			   <div class="layui-form-item linkdiv modulespecialclass">
			      <label class="layui-form-label">模块跳转专题：</label>
			      <div class="layui-input-block">	
					<select id="modulelinkspecial" name="modulelinkspecial">
						  <option value="0" >--请选择--</option>
						  <c:forEach items="${speciallist}" var="map">
						  	<option value="${map.specialcode}" 
						  		<c:if test="${modulelinkspecial eq map.specialcode}">
						  			selected="selected"
						  		</c:if>
						  		>${map.specialname}</option>
						  </c:forEach>
					</select>
					</div>
			</div> 
			  
			  <div class="layui-form-item linkdiv moduleurlclass">
			    <label class="layui-form-label">模块跳转网址：</label>
			    <div class="layui-input-block">
			      <input type="text" name="modulelinkurl" id="modulelinkurl" placeholder="请输入网站" autocomplete="off" class="layui-input" value="${modulelinkurl}">
			    </div>
			  </div>
			  
			  <div class="layui-form-item linkdiv viewpointstatusclass">
			      <label class="layui-form-label">看点是否显示：</label>
			      <div class="layui-input-block" id = "viewpointdianjishijian">	
					<input type="checkbox" name="viewpointstatus" id="viewpointstatus" lay-skin="switch" lay-text="显示|不显示" value="1"
						<c:if test="${viewpointstatus eq 1}">
							checked
						</c:if>
					/>
		  		  </div>
			  </div> 
			  <div class="layui-form-item linkdiv moduleviewpointclass">
			    <label class="layui-form-label">请输入非专辑看点：</label>
			    <div class="layui-input-block">
			      <input type="text" name="moduleviewpoint" id="moduleviewpoint" placeholder="请输入模版看点" autocomplete="off" class="layui-input" value="${moduleviewpoint}">
			    </div>
			  </div>
			  
			  <div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn  layui-btn-sm" lay-submit lay-filter="formDemo" id="submitBtn">立即提交</button>
			    </div>
			  </div>
          	</form>
        </div>
        
        <script type="text/javascript">
    	$('.linkdiv').hide();
        var isshowleftstatus = ${isshowleft};
        var linktypestatus = ${linktype} ;
        var masterplateidstatus = ${masterplateid} ;
        var modulelinktype = ${modulelinktype} ;
        var viewpointstatus = ${viewpointstatus};
       	//判断所属模版
        if(masterplateidstatus == "108" || masterplateidstatus == "107" || masterplateidstatus == "106" ){
        	$('.directionclass').hide();
        	$('.linkdiv').show();
        }
       	//模版跳转类型
        if(modulelinktype == "0"){
        	$('.modulespecialclass').hide();
        	$('.moduleurlclass').hide();
        	$('.modulechannelclass').hide();
        	$('.viewpointstatusclass').hide();
        	$('.moduleviewpointclass').hide();
        }else if(modulelinktype == "1"){
        	$('.modulespecialclass').hide();
        	$('.moduleurlclass').hide();
        	$('.modulechannelclass').show();
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == 0){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
        }else if(modulelinktype == "2"){
        	$('.modulespecialclass').hide();
        	$('.moduleurlclass').hide();
        	$('.modulechannelclass').hide();
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == 0){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
        }else if(modulelinktype == "3"){
        	$('.modulespecialclass').show();
        	$('.moduleurlclass').hide();
        	$('.modulechannelclass').hide();
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == 0){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
        }else if(modulelinktype == "4"){
        	$('.modulespecialclass').hide();
        	$('.moduleurlclass').hide();
        	$('.modulechannelclass').hide();
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == 0){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
        }else if(modulelinktype == "5"){
        	$('.modulespecialclass').hide();
        	$('.moduleurlclass').show();
        	$('.modulechannelclass').hide();
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == 0){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
        }
       	
       	//左标是否签跳转
        if(isshowleftstatus == "0"){
        	$('.linktypeclass').hide();
        	$('.channelclass').hide();
        	$('.labelclass').hide();
        	$('.urlclass').hide();
        	linktypestatus = 0;
        }
       	//左标签跳转类型
        if(linktypestatus == "1"){
        	$('.linktypeclass').show();
        	$('.channelclass').show();
        	$('.labelclass').hide();
        }else if(linktypestatus == "2"){
        	$('.linktypeclass').show();
        	$('.channelclass').show();
        	$('.labelclass').show();
        }
       	
       	//左标签点击事件
        $("#leftdianjishijian").click(function(){
        	if($(this).find('input').is(':checked')){
        		$('.linktypeclass').show();
        		$('.channelclass').show();
            	$('.labelclass').show();
            	if(linktypestatus == "1"){
                	$('.linktypeclass').show();
                	$('.channelclass').show();
                	$('.labelclass').hide();
                }else if(linktypestatus == "2"){
                	$('.linktypeclass').show();
                	$('.channelclass').show();
                	$('.labelclass').show();
                }
        	}else{
        		$('.linktypeclass').hide();
            	$('.channelclass').hide();
            	$('.labelclass').hide();
            	$('.urlclass').hide();
        	}
        })
        
        //channel点击事件
         $("#channeldianjishijian").click(function(){
        	 $(this).find('dd').click(function(){
        		var channelCode  = $(this).context.getAttribute('lay-value');
        		choosechannellabel(channelCode);
        		
        	 })
        })  
        
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
	    		            		$("#linklabel").html(options);
	    		            		 form.render('select');
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       	}
	                 });
	      }
        
       	//看点是否显示点击事件
        $("#viewpointdianjishijian").click(function(){
        	if($(this).find('input').is(':checked')){
        		$('.moduleviewpointclass').show();
        	}else{
            	$('.moduleviewpointclass').hide();
        	}
        })
        //左标签跳转类型选择
        $("#tiaozhuandianjishijiandiv").click(function(){
        	 $(this).find('dd').click(function(){
        		 if($(this).attr("lay-value") === '1'){
        			 $('.linktypeclass').show();
                 	$('.channelclass').show();
                 	$('.labelclass').hide();
        		 }else if($(this).attr("lay-value") === '2'){
        			 $('.linktypeclass').show();
                 	$('.channelclass').show();
                 	$('.labelclass').show();
        		 }
             }) 
        })
        
        function viewpointfun() {
        	$('.viewpointstatusclass').show();
        	if(viewpointstatus == '0'){
        		$('.moduleviewpointclass').hide();
        	}else{
        		$('.moduleviewpointclass').show();
        	}
		}
        //模版跳转类型选择
        $("#moduletiaozhuandianjishijiandiv").click(function(){
        	 $(this).find('dd').click(function(){
        		//alert($(this).attr("lay-value"));
        		if($(this).attr("lay-value") === '0'){
                 	$('.linkdiv').hide();
                 	$('.modulelinktype').show();
        		 }else if($(this).attr("lay-value") === '1'){
        			$('.linkdiv').hide();
                   	$('.modulelinktype').show();
                   	$('.modulechannelclass').show();
                   	viewpointfun();
         		 }else if($(this).attr("lay-value") === '2'){
        			$('.linkdiv').hide();
                   	$('.modulelinktype').show();
                   	viewpointfun();
         		 }else if($(this).attr("lay-value") === '3'){
        			$('.linkdiv').hide();
                    $('.modulelinktype').show();
                    $('.modulespecialclass').show();
                    viewpointfun();
          		 }else if($(this).attr("lay-value") === '4'){
        			$('.linkdiv').hide();
                    $('.modulelinktype').show();
                    viewpointfun();
          		 }else if($(this).attr("lay-value") === '5'){
        			$('.linkdiv').hide();
                    $('.modulelinktype').show();
                    $('.moduleurlclass').show();
                    viewpointfun();
          		 }
             }) 
        }) 
        	var form = layui.form;
        	form.render();
        	  //监听提交
        	  form.on('submit(formDemo)', function(data){
        	    var moduleId=data.field.moduleId;
        	    var channel=$("#channel").val();
        	    var name=$("#name").val();
        	    var masterplateId=$("#masterplateId").val();
        	    var modulepic=$("#modulepic").val();
        	    var textrecommendpic=$("#textrecommendpic").val();
        	    var picture=$("#picture").val();
        	    var picstatus=data.field.picstatus==undefined?0:data.field.picstatus;
        	    var textpicstatus=data.field.textpicstatus==undefined?0:data.field.textpicstatus;
        	    var picturestatus=data.field.picturestatus==undefined?0:data.field.picturestatus;
        	    var modulestatus=data.field.modulestatus==undefined?0:data.field.modulestatus;
        	    var isshowleft=data.field.isshowleft==undefined?0:data.field.isshowleft;
        	    var isshowright=data.field.isshowright==undefined?0:data.field.isshowright;
        	    var linkstatus=$("#linkstatus").val();
        	    var linktype=$("#linktype").val();
        	    var linkchannel=$("#linkchannel").val();
        	    var linklabel=$("#linklabel").val();
        	    var modulelinktype=$("#modulelinktype").val();
        	    var modulelinkchannel=$("#modulelinkchannel").val();
        	    var modulelinkspecial=$("#modulelinkspecial").val();
        	    var modulelinkurl=$("#modulelinkurl").val();
        	    var viewpointstatus=data.field.viewpointstatus==undefined?0:data.field.viewpointstatus;
        	    var moduleviewpoint=$("#moduleviewpoint").val();
        	    if(isshowleft == 1){
        	    	if(linktype == null){
        	    		$.message({
    	        			message:'左标签跳转类型未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
        	    	}else if(linktype == '1'){
        	    		if(linkchannel == '0'){
        	    			$.message({
        	        			message:'左标签跳转频道未填写 ',
        	        			time:1000,
        	        			type:'error'
        	        		});
        	        		return false;
        	    		}
        	    	}else if(linktype == '2'){
        	    		if(linkchannel == '0' || linklabel == '0' ){
        	    			$.message({
        	        			message:'左标签跳转频道或标签未填写 ',
        	        			time:1000,
        	        			type:'error'
        	        		});
        	        		return false;
        	    		}
        	    	}
	        	}
        	    //模块跳转提交限制
        	    if(modulelinktype == 1){
        	    	if(modulelinkchannel == '0'){
    	    			$.message({
    	        			message:'模版跳转频道未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
        	    	}
        	    }else if(modulelinktype == 3){
        	    	if(modulelinkspecial == '0'){
    	    			$.message({
    	        			message:'模版跳转专题未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
        	    	}
        	    }else if(modulelinktype == 5){
        	    	if(modulelinkurl == '' || modulelinkurl == null){
    	    			$.message({
    	        			message:'模版跳转网址未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
        	    	}
        	    }
        	    $.ajax({
	        		url:'${pageContext.request.contextPath}/vod/phone/vodmouldinfo/add',
	        		type:'post',
	        		data:{
	        			moduleId:moduleId,
	        			channel:channel,
	        			name:name,
	        			masterplateId:masterplateId,
	        			modulepic:modulepic,
	        			textrecommendpic:textrecommendpic,
	        			picture:picture,
	        			picstatus:picstatus,
	        			textpicstatus:textpicstatus,
	        			picturestatus:picturestatus,
	        			modulestatus:modulestatus,
	        			isshowleft:isshowleft,
	        			isshowright:isshowright,
	        			linkstatus:linkstatus,
	        	        linktype:linktype,
	        	        linkchannel:linkchannel,
	        	        linklabel:linklabel,
	        	        modulelinktype:modulelinktype,
	        	        modulelinkchannel:modulelinkchannel,
	        	        modulelinkspecial:modulelinkspecial,
	        	        modulelinkurl:modulelinkurl,
	        	        viewpointstatus:viewpointstatus,
	        	        moduleviewpoint:moduleviewpoint
	        		},
	        		success:function(data){
	        			if(data!=null){
	        				if(data.code==1){
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
	        //执行实例2
	        var uploadInst2 = upload.render({
	          elem: '#textpicBtn', //绑定元素
	          url: '/fileupload/image', //上传接口
	          accept:'images',
	          acceptMime:'image/*',
	          done: function(resp){
	            if(resp!=null){
	            	imgUrl=resp.message;
	            	if(imgUrl!=null){
	            		$('#textrecommendpic').val(imgUrl);
	            		$('#uploadImg1').hide();
	            		$('#uploadedImg1').show();
	            	}
	            }
	          }
	          ,error: function(){
	            console.log("请求异常回调");
	          }
	        });
	        //执行实例3
	        var uploadInst3 = upload.render({
	          elem: '#bigpicBtn', //绑定元素
	          url: '/fileupload/image', //上传接口
	          accept:'images',
	          acceptMime:'image/*',
	          done: function(resp){
	            if(resp!=null){
	            	imgUrl=resp.message;
	            	if(imgUrl!=null){
	            		$('#picture').val(imgUrl);
	            		$('#uploadImg2').hide();
	            		$('#uploadedImg2').show();
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
        		var textrecommendpic=$("#textrecommendpic").val();
        		if(textrecommendpic!=''){
            		$('#uploadImg1').hide();
            		$('#uploadedImg1').show();
        		} 
        		var picturevalue = $("#picture").val();
        		if(picturevalue!=''){
            		$('#uploadImg2').hide();
            		$('#uploadedImg2').show();
        		} 
        		
        		
        		$('.dropdown-menu li').on('click', function(e) {
    	        	var $target = $(e.target);
    	        	$('#masterplateId').text($target.text());
    	        	$('#masterplateId').attr('tabId',$target.attr('tabId'));
    	        })	
    	       
    	        
    	        $('#submitBtn').on('click',function(){
    	        	var channel=$('#channel').val();
    	        	var name=$('#name').val();
    	        	var masterplateId=$('#masterplateId').val();
    	        	if(name==null || name==""){
    	        		$.message({
    	        			message:'名称未填写 ',
    	        			time:1000,
    	        			type:'error'
    	        		});
    	        		return false;
    	        	}
    	        	if(masterplateId==null || masterplateId=="0"){
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
	 