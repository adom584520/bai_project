<%@ page isELIgnored="false" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="f" %>
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

.aixuexi img{width:117px;height:150px;}
.aixue img{width:150px;height:110px;}
.tblMain{border:0px; }
</style>
</head>
<body class="easyui-layout" style="visibility:hidden">

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="VodAlbuminfo" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			 <tr>
			 	 <td colspan="4">
			   		<table align="center"   >
			   		<tr align="center" >
						<td   align="center"  style="width:25%; "  >
							<div class="aixuexi" style=" height:150px;"> 
								<img id="pictureurl1" class="pictureurl1"  style="margin-right: 2px;" src=""/>
							</div>
								<p>小图竖图</p>
						</td>
						<td   style="width:25%; " align="center" >
							<div class="aixue" style=" height:150px;"> 
								<img id="pictureurl2"  class="pictureurl2" style="margin-right: 2px;" src=""/>
							</div>
								<p>小图横图</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixuexi" style=" height:150px;">
								<img  id="pictureurl3"  class="pictureurl3"  style="margin-right: 2px;" src=""/>
							</div>
								<p>大图竖图</p>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixue" style=" height:150px;">
								<img  id="pictureurl4" class="pictureurl4"  src=""/>
							</div>
								<p>大图横图</p>
						</td>
						</tr>
					</table>
	</td>
			 
			 </tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑code
				</td>
				<td class="tdContent">
		<input type="text" id="seriescode" disabled="disabled" name="seriescode" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]',required:true" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="seriesname" name="seriesname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道
				</td>
				<td class="tdContent">
				 <select  id="channel" name="channel" onchange="choosechannellabel();"  style="width: 120px;" class="chzn-select">
				 </select>
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent"  colspan="3">
				<input type="hidden" id="label" name="label"/>
				<input type="hidden" id="labelnames" name="labelnames"/>
				<select id="labelname" style="width:150px"></select>
				 <div id="labellist">
			         <div style="padding:10px" id="labellistmap" >
			        </div>
		        </div>
			</td>
			 
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总集数
				</td>
				<td class="tdContent">
		<input type="text" id="volumncount" name="volumncount"  class="easyui-numberbox" style="" data-options="validType:'text[1,5]',required:true" />	
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 更新剧集
				</td>
				<td class="tdContent">
		<input type="text" id="currentnum" name="currentnum"  class="easyui-numberbox" style="" data-options="validType:'text[1,5]',required:true" />	
	</td>
			</tr>
		 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑简介
				</td>
				<td class="tdContent" colspan="3">
				<textarea  id="description" name="description" style="width:95%;height:90px;"rows="60" cols="4" ></textarea>
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	         专辑看点
				</td>
				<td class="tdContent"  colspan="3">
		<input type="text" id="viewpoint" name="viewpoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
				 
			 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="releaseyear" name="releaseyear"  type="text" class="easyui-datebox" />	
	</td>
		<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总时长
				</td>
				<td class="tdContent">
		<input type="text" id="duration" name="duration"  class="easyui-numberbox" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="actor" name="actor"/>
		<input type="text" id="actorname" name="actorname" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]',required:true" />
		<a href="#" onclick="showactors('actor');">请选择......</a>	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 导演
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="writer" name="writer"/>
		<input type="text" id="writername" name="writername" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,500]',required:true" />	
				<a href="#" onclick="showactors('writer');">请选择......</a>	
	</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent" colspan="3">
		<input type="text" id="originalcountry" name="originalcountry" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
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
	        $('body').css({ visibility: 'visible' });
	        var t = $('#VodAlbuminfo');
	        var id = "${id}";
	        $channel=$("#channel");
			$label=$("#labelname");
			choosechannel();
			choosechannellabel();
		    //下拉框多选
	          $s=",";//设定分隔付
	          $sid=",";
            $('#labelname').combo({
                required:true,//是否验证
                editable:true,//是否可编辑
                multiple:true//可否支持多选
            }); 
            $('#labellist').appendTo($('#labelname').combo('panel'));
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/albuminfo/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	             //   $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	                alert(data.seriescode);
	                $("#pictureurl1").attr("src",data.pictureurl1);
	                $("#pictureurl2").attr("src",data.pictureurl2);
	                $("#pictureurl3").attr("src",data.pictureurl3);
	                $("#pictureurl4").attr("src",data.pictureurl4);
	            }, onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
	        //保存
	        function save( ) {
	        	if (t.form('validate') == false) {
	        		return false;
	        	}
	            var data = t.domain('collect');
	            var channel=document.getElementById("channel");
				  data.channelname=channel.options[channel.selectedIndex].text;
				  data.labelname=document.getElementById("labelnames").value;
	            $('#btnSave,#btnClose').linkbutton('disable');
	        //如果id为0，是要创建一条新记录，否则是whn
	        var  url= '${pageContext.request.contextPath}/vod/albuminfo/'+(id=="0"?'create':'edit/'+id);  
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
	
	
  //频道   
	function choosechannel(){
	  var name="";
		//  name="choosechanneltv";
		  name="choosechannelphone";
           	$.ajax({
               	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
               	type:"GET",
               	dataType:"JSON",
               	data:{levels:1},
               	async:false,
               	success:function(msg){
               		var options = "";
	            		for(var i=0;i<msg.length;i++){
	            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	            		}
	            	 $channel.html(options);
	               	   
	            		
               	},
               	error:function(XHR, status, errorThrow){
               		
               	}
               });
           }
  
	//标签或者标签
	function choosechannellabel(){
	  var name="";
	  var chnid="";
		//  name="choosechanneltv";
		  name="choosechannelphone";
		  chnid=$channel.val();
           	$.ajax({
               	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
               	type:"GET",
               	dataType:"JSON",
               //	data:{levels:2,parentcode:chnid},
               	data:{levels:2},
               	async:false,
               	success:function(msg){
               		//var options = " <option value=''>--请选择--</option> ";
               		var options = "";
	            		for(var i=0;i<msg.length;i++){
	            			options+="<input type='checkbox' name='lang'  onclick='labelmap(this);'   value='"+msg[i].value+"'><span>"+msg[i].text+"</span><br/>";
	            		}
	               		     $("#labellistmap").html(options);
               	},
               	error:function(XHR, status, errorThrow){
               		
               	}
               });
           }
	//多选下拉框
	function labelmap(obj){
	                        var str = "";
	                        var strid="";
	                        $("input[name='lang']").each(function(){    
	                            if($(this).is(":checked"))    
	                            {    
	                            	strid += ","+$(this).val(); 
	                            	str += ","+$(this).next('span').text();   
	                            }    
	                        });    
	                        document.getElementById("label").value=strid.substring(1,strid.length);   
	                        document.getElementById("labelnames").value=str.substring(1,str.length);   
                          $('#labelname').combo('setValue', str.substring(1,str.length)).combo('setText', str.substring(1,str.length)).combo('showPanel');//将值赋值给文本框并在文本里显示出来
	}
	
	//演员 导演选择
	 	 function showactors(code){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/vod/albuminfo/actorslist",
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	        		var ids = top.$.data(top.document.body, "choose.ids");
        	        		var names = top.$.data(top.document.body, "choose.names");
        	        		if(code=='actor'){
        	        			 document.getElementById("actor").value=ids; 
            	        		 document.getElementById("actorname").value=names;
        	        		}else{
        	        			 document.getElementById("writer").value=ids; 
            	        		 document.getElementById("writername").value=names;
        	        		}
        	        	 
	     	            }
	     	        });
	         	}
</script>
