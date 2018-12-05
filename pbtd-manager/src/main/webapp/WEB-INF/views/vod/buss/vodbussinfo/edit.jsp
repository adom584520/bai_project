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
	width: 35px;
}
.tdContent input {
	width: 300px;
}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="vodbussinfo"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
	<input type="hidden" id="bussId" name="bussId" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		分平台code
				</td>
				<td class="tdContent">
				<input type="text" id="name"name="name"<%--  <c:if test="${id>0 }"> disabled="disabled" </c:if> --%> class="input text easyui-validatebox-disable"    style="" data-options="validType:'text[1,80]',required:true" />
				</td>
			</tr>
				<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		项目分组
				</td>
				<td class="tdContent">
				<input type="text" id="groupId"name="groupId"    <c:if test="${bussId>0 }"> disabled="disabled" </c:if>   class="easyui-numberbox" style="" data-options="validType:'text[1,10]',required:true" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		分平台名称
				</td>
				<td class="tdContent">
				<input type="text" id="bussUser"name="bussUser"  class="input text easyui-validatebox-disable"    style="" data-options="validType:'text[1,10]',required:true" />
				</td>
			   </tr>
				<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		url
				</td>
				<td class="tdContent">
				<input type="text" id="address"name="address" class="input text easyui-validatebox-disable"    style="" data-options="validType:'text[1,50]',required:true" />
				</td>
			</tr>
			<!-- 	<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		联系方式
				</td>
				<td class="tdContent">
				<input type="text" id="bussPhone"name="bussPhone"  class="input text easyui-validatebox-disable"    style="" data-options="validType:'text[1,11]',required:true" />
				</td>
			</tr> -->
			<tr>
			<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
			 cp源
			 </td>
			 <td class="tdContent">
				<select id="cpsource"  name ="cpsource"   style="width:250px"> </select>
					 <div id="cpsourcediv">
				         <div style=" " id="cpsourcelist" >
					         <c:forEach items="${cplist}" var="item">
					         <input type='checkbox' name='langbag'  onclick='cpmap(this);'   value='${item.id}'><span>${item.name}</span><br/>
							</c:forEach>
				        </div>
			        </div>
			 </td>		
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		状态
				</td>
				<td class="tdContent">
				  <select id="status" name="status">
				  	<option value="1" >上线</option>
				  	<option value="0" >下线</option>
				  </select>
				</td>
			</tr>
			</table>
			  <div data-options="region:'center',border:false" style="padding:0px;">
		<table width="98%" align="center" cellspacing="0" cellpadding="0"   id="tbl2" name="tbl2">
				<tr>
					<td>
					<div class="alert alert-info" style="height: 30px; margin-bottom: 5px ; font-size:14px">
						<strong>选择频道</strong> 能一次选取多个频道，操作完成后点击保存完成选取！ </div>
					</td>
				</tr>
				<tr>
				<td>
					<table width="98%">
						<tr height="20px">
							<td><strong>待选</strong></td>
							<td></td>
							<td><strong>已选</strong></td>
						</tr>
						<tr>
							<td width="280px" >  
								<div class="controls"  style="width: 280px; height: 280px" >
								<select multiple id="dxDept" name="dxDept"  ondblclick="choose('add','Dept')"   style="width: 280px; height: 280px"  > 
								</select> </div>
							</td>
							<td align="center" width="80px" >
							<input type="button"  class="btn btn-success" value="添加 -&gt;"   onclick="choose('add','Dept')" />
							 <br>
							<br>
							<input type="button" value="&lt;- 删除"   class="btn btn-success" onclick="choose('del','Dept')"  />
							<br><br>
							<input type="button" value="添加-&gt;&gt;"   class="btn btn-success" onclick="choose('addAll','Dept')" />
							 <br> <br>
							<input type="button" value="&lt;&lt;-删除"   class="btn btn-success" onclick="choose('delAll','Dept')"  />
							 </td>
							<td width="280px" >
							<div class="controls"  style="width: 280px; height: 280px">
								<select multiple="multiple" id="yxDept" ondblclick="choose('del','Dept')" name="yxDept" style="width: 280px; height: 280px"  > 
								</select> </div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
        </div>
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
	        var id = "${id}";
	        $('body').css({ visibility: 'visible' });
	        var t = $('#vodbussinfo');
	        $('#cpsource').combo({
                editable:true,//是否可编辑
                multiple:true//可否支持多选
            }); 
          $('#cpsourcediv').appendTo($('#cpsource').combo('panel'));
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/vodbussinfo/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                var cparray=data.cpsource.split(",");
	                for (var i = 0; i < cparray.length; i++) {
	                	   $("input[name='langbag']").each(function(){    
	                           if($(this).val()==cparray[i])    
	                           {    
	                        	   $(this).context.checked=true;
	                           }    
	                       });  
					}
	                cpmap("");   
	               
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
	     	loadDx();
           	loadYx();
	
        //保存
        function save() {
        	if (t.form('validate') == false) {
        		return false;
        	}
        	var list=document.getElementById("yxDept");
    		var channelids="";
    		for(var i = 0;   i < list.length; i++){
    			channelids+=","+list.options[i].value;
    			//names+=","+list.options[i].text;
    		} 
            var data = t.domain('collect');
            data.channelCode=channelids;
            $('#btnSave,#btnClose').linkbutton('disable');
	    //如果id为0，是要创建一条新记录，否则是whn
	    var  url= '${pageContext.request.contextPath}/vod/vodbussinfo/'+(id=="0"?'create':'edit/'+id);  
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
	
   	function loadDx(){ 
    	var id = "${id}";
    	url="${pageContext.request.contextPath}/pbtd/dictionary/choosebusschannelno";
		$.ajax({
			 url : url,
             type:"get",
             dataType:"json", 
             data:{bussId:id},
             success:function(data, status, XHR){  
        		var src="";
        		if(data.length > 0){
                	for(var i=0;i<data.length;i++){
                		src = src+"<option value='"+data[i].value+"'>"+data[i].value+'-'+data[i].text+"</option>" ;
                	}
        		}
                $('#dxDept').html(src);
        	
             },
             error:function(){ 
             }
         }); 
    }
    function loadYx(){
    	var id = "${id}";
      	url="${pageContext.request.contextPath}/pbtd/dictionary/choosebusschannel";
		$.ajax({
			 url : url,
             type:"get",
             dataType:"json",
             data:{bussId:id},
             success:function(data, status, XHR){  
        	if(data.length > 0){
        		var src="";
            	for(var i=0;i<data.length;i++){
            		src = src+"<option value='"+data[i].value+"'>"+data[i].value+'-'+data[i].text+"</option>" ;
            	}
                $('#yxDept').html(src);
        	}
             },
             error:function(){ 
             }
         }); 
    }
  //从左边加到右边
 	function choose(flag,objs){  
		if (flag=="add"||flag=="addAll"){
      		var destList = document.getElementById("yx"+objs) ; 
      		var srcList = document.getElementById("dx"+objs); 
		}
		else{
			var srcList = document.getElementById("yx"+objs) ; 
	      	var destList = document.getElementById("dx"+objs); 
		}
        var len = destList.length;   
      	for(var i = 0;   i < srcList.length; i++){ 
    	  	if(((srcList.options[i] != null)&&(srcList.options[i].selected))||(flag=="addAll"||flag=="delAll")){ 
    	  		var status='1';
    	  		for (var j=0;j<destList.length;j++){
    	  			if (srcList.options[i].value==destList.options[j].value){
    	  				status='0';
    	  				break;
    	  			}
    	  		}
    	  		if (status=='1'){
        	  		destList.options[len] = new Option(srcList.options[i].text,srcList.options[i].value);   
    		  		len++; 
    	  		}
    	    }   
       	}   
      	for (var i=srcList.length-1;i>=0;i--){
      		if(((srcList.options[i] != null)&&(srcList.options[i].selected))||(flag=="addAll"||flag=="delAll"	)){ 
      			srcList.options.remove(i); 	
      		}
      	}
      }  
  
  
 	//cp源
	function cpmap(obj){
            var str = "";
            var strid="";
            $("input[name='langbag']").each(function(){    
                if($(this).is(":checked"))    
                {    
                	strid += ","+$(this).val(); 
                	str += ","+$(this).next('span').text();   
                }    
            });    
             $('#cpsource').combo('setValue', strid.substring(1,strid.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
	}
</script>
