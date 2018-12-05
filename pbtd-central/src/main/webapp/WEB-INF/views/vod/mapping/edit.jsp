<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
        *  新建页面
        * 
        * @author admin
        *
-->
<!DOCTYPE html>
<html>
<head>
<title>编辑 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" /> 
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrap.css"></link>
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrapSwitch.css"></link>
  		<link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
     
    	<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       
<style type="text/css">
td.tdCaption {
	text-align: center;
}
td.tdCaption span {
	color: #ff0000;
}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input {
	display: inline-block;
	height: 20px;
	padding: 0px;
	margin-bottom: 2px;
	margin-top: 2px;
	font-size: 14px;
	line-height: 20px;
	color: #555555;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	vertical-align: middle;
}
a.l-btn span span.l-btn-text {
	width: 30px;
}
.tdContent input {
	width: 300px;
}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="vodmapping"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
	<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			
				<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		cp源
				</td>
				<td class="tdContent"  colspan="3">
				  <select onchange="cpchoosechannel(0,1);" id="cpcode" name="cpcode" style="width: 90px;"  class="input text easyui-validatebox-disable">
							<c:forEach items="${cpsource}" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		cp频道
				</td>
				<td class="tdContent">
				   <select  onchange="cpchooselabel(0,1);" id="cp_chnId" name="cp_chnId" style="width: 120px;" class="chzn-select">
					</select>
				</td>
				
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		频道
				</td>
				<td class="tdContent"  >
				    <select onchange="choosechannellabel('phone','0');" id="phone_chnId" name="phone_chnId" style="width: 120px;" class="chzn-select">
					</select>
	</td>
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 cp标签
				</td>
				<td class="tdContent">
				    <select id="cp_tagId" name="cp_tagId" style="width: 120px;" class="chzn-select">
					</select>
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		标签
				</td>
				<td class="tdContent">
				<select id="phone_tagId" name="phone_tagId" style="width: 120px;" class="chzn-select">
				</select>
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 状态
				</td>
				<td class="tdContent"  colspan="3">
				<select id="status" name="status" style="width: 120px;" class="chzn-select">
						<option value="1">上线</option>
						<option value="0" selected="selected">下线</option>
					</select>
	</td>
			</tr>  
				 <tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 备注
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="bz" name="bz" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
			</table>
    </form>
</div>
<div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
   <a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false,disabled:true">保存</a>
   <a id="btnClose" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false,disabled:true">关闭</a>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();
	        $id = $.query.getId();
	         var id = $.query.getId();
	        $('body').css({ visibility: 'visible' });
	        var t = $('#vodmapping');
	        $cp_chnId = $("#cp_chnId");
			$cp_tagId=$("#cp_tagId");
		    $phone_chnId=$("#phone_chnId");
			$phone_tagId=$("#phone_tagId");
			$tv_chnId=$("#tv_chnId");
			$tv_tagId=$("#tv_tagId");
			$cpchnid=""; //cp频道
			$cptagId="";//cp标签
			$tvchnid="";//tv频道
			$phonechnid="";//手机频道
			$code="";//cp源code
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/integrate/mapping/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                $code=data.cpcode;
	                $cpchnid=data.cp_chnId;
	                $cptagId=data.cp_tagId;
	    			//$tvchnid=data.tv_chnId;
	    			$phonechnid=data.phone_chnId;
	    			//加载下拉框
	    			cpchoosechannel(1,0);//获取cp频道
	    	        cpchooselabel($cpchnid,0);//获取cp标签
	    	        
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
	        choosechannel("phone");//获取手机频道
	        choosechannellabel("phone",$phonechnid);//获取手机标签
	       // choosechannel("tv");//获取电视频道
	     //   choosechannellabel("tv",$tvchnid);//获取电视标签
        //保存
        function save() {
        	if (t.form('validate') == false) {
        		return false;
        	}
            var data = t.domain('collect');
            if(data.phone_tagId.length>0){
            	 if(data.cp_tagId.length<1){
            		 top.$.messager.alert("信息", "标签映射请选中cp标签信息", "info", null, 2000);
          		   return ;
            	 }
            	if(data.phone_chnId.length>0){
            		   top.$.messager.alert("信息", "标签映射请取消选中频道信息", "info", null, 2000);
            		   return ;
            	}
            }
            $('#btnSave,#btnClose').linkbutton('disable');
	    //如果id为0，是要创建一条新记录，否则是whn
	    var  url= '${pageContext.request.contextPath}/integrate/mapping/'+($.query.getId()=="0"?'create':'edit/'+id);  
		     t.domain('edit', {
	             title: ' ',
	             url: url,
	             data: data,
	             onBeforeLoad: function(XHR) {
	             },
	             onLoadSuccess: function(data, status, XHR) {
	             	$('#btnSave,#btnClose').linkbutton('enable');
	             	top.$.data(top.document.body, "domain.create.refresh","refresh");
	                 $(parent).domain('closeDialog');
	             },
	             onLoadError: function(XHR, statusText, errorThrow) {
	             	$('#btnSave,#btnClose').linkbutton('enable');
	             }
	         });
        }
   	});
      //选择cp频道
    	function cpchoosechannel(type,cptype){
    		var cpcode=$("#cpcode").val();
    		if($id>0 && type>0){ cpcode=$code; }
    	    var name="";
    	  if(cpcode=='1'){
    		  name="choosechannelyh";
    	  }else if(cpcode=='2'){
    		  name="choosechannelgg";
    	  }else if(cpcode=='3'){
    		  
    	  }else if(cpcode=='4'){
    		  name="choosechannelguttv";
    	  }else if(cpcode=='5'){
    		  name="choosechannelyk";
    	  }
    	  else{  }
               	$.ajax({
                   	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{levels:1},
                   	async:false,
                   	success:function(msg){
                   		var options = "";
		            		for(var i=0;i<msg.length;i++){
		            			options+='<option  value='+msg[i].value;
		            			if(msg[i].value==$cpchnid){
		            				options	+=' selected="selected" ';
		                   		}
		            			options+='>'+msg[i].text+'</option>';
		            		}
		            		$cp_chnId.html(options);
		            		 cpchooselabel(0,cptype); 
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   });
               }
    	//选择cp标签
    	function cpchooselabel(chnId,cptype){
    		var cpcode=$("#cpcode").val();
    		if($id>0 && cptype!='1'){
    			cpcode=$code;
    		}
    	    var cp_chnId="";
    	    var name="";
    	  if(chnId=="0"){  cp_chnId=$cp_chnId.val(); }
    	  else{    cp_chnId=chnId;    	  }
    	  if(cpcode=='1'){
    		  name="choosechannellabelyh";
    	  }else if(cpcode=='2'){
    		  name="choosechannellabelgg";
    	  }else if(cpcode=='3'){
    		  
    	  }else if(cpcode=='4'){
    		  name="choosechannellabelguttv";
    	  }else if(cpcode=='5'){
    		  name="choosechannellabelyk";
    	  }else{
    		  
    	  }
               	$.ajax({
                   	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{chnId:cp_chnId},
                   	async:false,
                   	success:function(msg){
                   		var options = " <option value=''>--请选择--</option> ";
		            		for(var i=0;i<msg.length;i++){
		            			options+='<option  value='+msg[i].value;
		            			if(msg[i].value==$cptagId){
		            				options	+=' selected="selected" ';
		                   		}
		            			options+='>'+msg[i].text+'</option>';
		            		}
		            		$cp_tagId.html(options);
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   });
               }
    	//频道   
    	function choosechannel(type){
    	  var name="";
    	  if(type=="tv"){
    		  name="choosechanneltv";
    	  }else {
    		  name="choosechannelphone";
    	  } 
               	$.ajax({
                   	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{levels:1},
                   	async:false,
                   	success:function(msg){
                   		var options = " <option value=''>--请选择--</option> ";
		            		for(var i=0;i<msg.length;i++){
		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
		            		}
		            		 if(type=="tv"){
		            			 $tv_chnId.html(options);
		               	  }else {
		               		     $phone_chnId.html(options);
		               	  } 
		            		
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   });
               }
    	//标签或者标签
    	function choosechannellabel(type,tvphonechnid){
    	  var name="";
    	  var chnid="";
    	  if(type=="tv"){
    		  name="choosechanneltv";
    		  chnid=$tv_chnId.val();
    	  }else {
    		  name="choosechannelphone";
    		  chnid=$phone_chnId.val();
    	  } 
    	  if(tvphonechnid!='0'){chnid=tvphonechnid;}
               	$.ajax({
                   	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                   	type:"GET",
                   	dataType:"JSON",
                  // 	data:{levels:2,parentcode:chnid},
                   	data:{levels:2},
                   	async:false,
                   	success:function(msg){
                   		var options = " <option value=''>--请选择--</option> ";
		            		for(var i=0;i<msg.length;i++){
		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
		            		}
		            	  if(type=="tv"){
		            			 $tv_tagId.html(options);
		               	  }else {
		               		     $phone_tagId.html(options);
		               	  } 
		            		
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   });
               }

	
	
</script>
