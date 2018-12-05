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
								<p>图片1</p>
								<a class='easyui-linkbutton' id="pic1">上传</a>
						</td>
						<td   style="width:25%; " align="center" >
							<div class="aixue" style=" height:150px;"> 
								<img id="pictureurl2"  class="pictureurl2" style="margin-right: 2px;" src=""/>
							</div>
								<p>图片2</p>
								<a class='easyui-linkbutton' id="pic2">上传</a>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixuexi" style=" height:150px;">
								<img  id="pictureurl3"  class="pictureurl3"  style="margin-right: 2px;" src=""/>
							</div>
								<p>图片3</p>
								<a class='easyui-linkbutton' id="pic3">上传</a>
						</td>
						<td style="width:25%; "  align="center" >
							<div    class="aixue" style=" height:150px;">
								<img  id="pictureurl4" class="pictureurl4"  src=""/>
							</div>
								<p>图片4</p>
								<a class='easyui-linkbutton' id="pic4">上传</a>
						</td>
						</tr>
					</table>
	</td>
			 
			 </tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑code
				</td>
				<td class="tdContent"  >
		<input type="text" id="seriesCode" name="seriesCode" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]',required:true" />	
	</td>
	<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 排序
				</td>
				<td class="tdContent"  >
		<input type="text" id="sequence" name="sequence"  class="easyui-numberbox"   data-options="min:1,precision:0,required:true"/>	
	</td>
	<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑名称
				</td>
				<td class="tdContent">
		<input type="text" id="seriesName" name="seriesName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
	
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 专辑原名称
				</td>
				<td class="tdContent">
		<input type="text" id="originalName" name="originalName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]',required:true" />	
	</td>
	
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			  拼音
			</td>
			 <td class="tdContent">
		 <input type="text" id="pinyin" name="pinyin"class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,600]'"/>	
 	     </td>
 	     <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		  拼音缩写
			</td>
			<td class="tdContent">
		 <input type="text" id="pinyinsuoxie" name="pinyinsuoxie" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]'"/>	
 	     </td>	
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			专辑英文名称
			</td>
			<td class="tdContent">
		 <input type="text" id="enName" name="enName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]'"/>	
 	     </td>
 	     <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		  副标题
			</td>
			 <td class="tdContent">
			 <input type="text" id="vName" name="vName" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,200]'"/>	
	 	     </td>			
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道
				</td>
				<td class="tdContent">
				 <select  id="channel" name="channel" onchange="choosechannellabel(-1);"  style="" class="chzn-select">
				<c:forEach items="${channellist }" var="item">
					<option value="${item.channelCode }">${item.channelName }</option>
				</c:forEach>
				 </select>
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签
				</td>
				<td class="tdContent">
				<input type="hidden" id="label" name="label"/>
				<input type="hidden" id="labelnames" name="labelnames"/>
				<select id="labelName"   name ="labelName" style="width:150px"></select>
				 <div id="labellist">
			         <div style="padding:10px" id="labellistmap" >
			        </div>
		        </div>
			</td>
			 
			</tr>
				<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 频道2
				</td>
				<td class="tdContent">
				 <select  id="channel2" name="channel2" onchange="choosechannellabel2(-1);"  style="" class="chzn-select">
						<option value="">--请选择--</option>
					 <c:forEach items="${channellist2 }" var="item">
						<option value="${item.channelCode }">${item.channelName }</option>
					</c:forEach>
				 </select>
	</td>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 标签2
				</td>
				<td class="tdContent">
				<input type="hidden" id="label2" name="label2"/>
				<input type="hidden" id="labelNames2" name="labelNames2"/>
				<select id="labelName2"  name ="labelName2"   style="width:150px"></select>
				 <div id="labellist2">
			         <div style="padding:10px" id="labellistmap2" >
			        </div>
		        </div>
			</td>
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 关联标签
				</td>
				<td class="tdContent">
                <input id="tag" name="tag" class="input text easyui-validatebox-disable"  data-options="validType:'text[1,600]'"/>
	           </td>
	           	<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 关键词
				</td>
				<td class="tdContent">
                <input id="keyWord" name="keyWord" class="input text easyui-validatebox-disable" data-options="validType:'text[1,600]'"/>
	           </td>
			</tr>			
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			角标
			</td>
			  <td class="tdContent">
				 <select  id="corner" name="corner"  style="width: 120px;" >
				 </select>
				 <div id="cornerslist">
				         <div style="padding:10px" id="cornerlist" >
					       <c:forEach items="${cornerlist}" var="item">
							 <input type='checkbox' name='langcorner'  onclick='cornermap(this);'   value='${item.id}'><span>${item.name}</span><br/>
						    </c:forEach>
				        </div>
			        </div>
			 </td>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 是否Vip
			 </td>
			 <td>
			 <select  id="isvip" name="isvip"  style="width: 120px;" class="easyui-combobox">
					 <option value="0" selected="selected">否</option>
					 <option value="1">是</option>
			 </select>
			 </td>			
			</tr>
			<tr>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			是否收费
			</td>
			  <td class="tdContent">
				 <select  id="isCollectfees" name="isCollectfees"  style="width: 120px;" class="easyui-combobox">
				 	 <option value="">--请选择--</option>
				  <option value="0" selected="selected">否</option>
			      <option value="1">是</option>
				 </select>
			 </td>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 收费产品包
			 </td>
			 <td class="tdContent">
				<select id="collectfeesbag"  name ="collectfeesbag"   style="width:150px"> </select>
					 <div id="baglist">
				         <div style="padding:10px" id="Collectfeesbaglist" >
					         <c:forEach items="${feelist}" var="item">
					         <input type='checkbox' name='langbag'  onclick='baglmap(this);'   value='${item.code}'><span>${item.name}</span><br/>
							</c:forEach>
				        </div>
			        </div>
				
			 </td>			
			</tr>
			<tr>
			 <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			 含税定价
			 </td>
			  <td class="tdContent">
			  <input id="price" name="price" class="easyui-numberbox" value="100" data-options="min:0,precision:2"/>
			  </td>
			  <td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
			  评分
			  </td>
			   <td class="tdContent">
			  <input id="score" name="score"   class="easyui-numberbox" value="100" data-options="min:0,precision:1"/>
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
		<input type="text" id="viewPoint" name="viewPoint" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,127]'" />	
	</td>
			</tr>
				 
			 
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 上映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="releaseYear" name="releaseYear"  type="text" class="easyui-datebox" />	
	</td>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 首映日期
				</td>
				<td class="tdContent"  >
		<input type="text" id="orgairDate" name="orgairDate"  type="text" class="easyui-datebox" />	
	</td>
	</tr>
	<tr>
	<td  style="text-align: right; width: 15%"  class="tdCaption"  align="center">
	          清晰度
	           </td>
	           <td class="tdContent">
	            <input id="definition" name="definition" class="easyui-numberbox"  data-options="validType:'number'"/>
	           </td>
		<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 总时长
				</td>
				<td class="tdContent">
			<input type="text" id="duration" name="duration"  class="easyui-numberbox" style="" data-options="validType:'text[1,20]'" />		
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 演员
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="writer" name="writer"/>
		<input type="text" id="writerName" name="writerName" disabled="disabled"  class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,100]',required:true" />
	<a href="#" onclick="reset('writer');">清空</a>	
				&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="showactors('writer');">请选择......</a>	
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		导演 
				</td>
				<td class="tdContent" colspan="3">
				<input type="hidden" id="actor" name="actor"/>
		<input type="text" id="actorName" name="actorName" disabled="disabled"  class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,500]',required:true" />	
			<a href="#" onclick="reset('actor');">清空</a>	
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="showactors('actor');">请选择......</a>	
	</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		 地区
				</td>
				<td class="tdContent"  >
		<input type="text" id="originalCountry" name="originalCountry" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,50]'" />	
	</td>
			<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		所含奖项
				</td>
				<td class="tdContent" >
		<input type="text" id="awards" name="awards" class="input text easyui-validatebox-disable" style="" data-options="validType:'text[1,130]'" />	
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
	         $channel2=$("#channel2");
			$label=$("#labelName");
			$label2=$("#labelName2");
			$channelids="";  
			$channelids2="";
			$labelids="";  
			$labelids2="";  
		    //下拉框多选
	         $s=",";//设定分隔付
	          $sid=",";
	          $('#labelName').combo({
	                required:true,//是否验证
	                editable:true,//是否可编辑
	                multiple:true//可否支持多选
	            });  
	          $('#labelName2').combo({
	                editable:true,//是否可编辑
	                multiple:true//可否支持多选
	            }); 
	          $('#collectfeesbag').combo({
	        	    editable:true,//是否可编辑
	                multiple:true//可否支持多选
	            }); 
	          $('#corner').combo({
	                editable:true,//是否可编辑
	                multiple:true//可否支持多选
	            }); 
	          $('#labellist').appendTo($('#labelName').combo('panel'));
	          $('#labellist2').appendTo($('#labelName2').combo('panel'));
	          $('#baglist').appendTo($('#collectfeesbag').combo('panel'));
	          $('#cornerlist').appendTo($('#corner').combo('panel'));
	        //加载数据
	        t.domain('load', {
	            url: "${pageContext.request.contextPath}/vod/tv/vodalbuminfo/load/" + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	            	$channelids= data.channel;
	            	$channelids2= data.channel2;
	            	$labelids=data.label;  
	            	$labelids2=data.label2; 
	            	$("#vName").val(data.vname);
	                 $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                 //更改图片按钮
		    	        $("#pic1").linkbutton('enable')[0].onclick=function(){upload(1,id);};
		    	        $("#pic2").linkbutton('enable')[0].onclick=function(){upload(2,id);};
		    	        $("#pic3").linkbutton('enable')[0].onclick=function(){upload(3,id);};
		    	        $("#pic4").linkbutton('enable')[0].onclick=function(){upload(4,id);};
	                choosechannellabel($channelids);
	                 choosechannellabel2($channelids2);
	                 $('#corner').combo('setValue', data.corner).combo('setText', data.corner);
	                 $('#collectfeesbag').combo('setValue', data.collectfeesbag).combo('setText', data.collectfeesbag); 
	                $("#pictureurl1").attr("src",data.pictureurl1);
	                $("#pictureurl2").attr("src",data.pictureurl2);
	                $("#pictureurl3").attr("src",data.pictureurl3);
	                $("#pictureurl4").attr("src",data.pictureurl4);
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
	    		  data.channelName=channel.options[channel.selectedIndex].text;
	    		  var channel2=document.getElementById("channel2");
	    		  data.channelName2=channel2.options[channel2.selectedIndex].text;
	            $('#btnSave,#btnClose').linkbutton('disable');
	        //如果id为0，是要创建一条新记录，否则是whn
	        var  url= '${pageContext.request.contextPath}/vod/tv/vodalbuminfo/'+(id=="0"?'create':'edit/'+id);  
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
  
	
  
	//标签 1
	function choosechannellabel(chnidfalg){
	  var name="";
	  var chnid="";
		  name="chooselabeltv";
		  if(chnidfalg==-1){
			  chnid=$("#channel").val();
		  }else{
			  chnid=$channel.val();
		  }
           	$.ajax({
               	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
               	type:"GET",
               	dataType:"JSON",
               	data:{channelCode:chnid},
               	async:false,
               	success:function(msg){
               		//var options = " <option value=''>--请选择--</option> ";
               		var options = "";
               		var labelnameidsname="";
               		var labelids=$labelids.split(",");
	            		for(var i=0;i<msg.length;i++){
	            			 options+="<input type='checkbox' name='lang'  onclick='labelmap(this);'  ";
	            			 for(var j=0;j<labelids.length;j++){
	            				 if(labelids[j]==msg[i].value){
		            				 labelnameidsname+=","+msg[i].text;
		 	            			options+="  checked='checked' ";
		            			 break;
	            				 } 
	            			 }
	            			 options+=" value='"+msg[i].value+"'><span>"+msg[i].text+"</span><br/>";
	            		}
	            		 //将值赋值给文本框并在文本里显示出来
	            		 $('#labelName').combo('setValue', labelnameidsname.substring(1,labelnameidsname.length)).combo('setText', labelnameidsname.substring(1,labelnameidsname.length));
	               		     $("#labellistmap").html(options);
               	},
               	error:function(XHR, status, errorThrow){
               		
               	}
               });
           }
	//多选下拉框1
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
             $('#labelName').combo('setValue', str.substring(1,str.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
	}
	
	
	
	//标签 2
	function choosechannellabel2(chnidfalg){
	  var name="";
	  var chnid="";
		  name="chooselabeltv";
		  if(chnidfalg==-1){
			  chnid=$("#channel2").val();
		  }else{
			  chnid=$channel2.val();
		  }
		
           	$.ajax({
               	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
               	type:"GET",
               	dataType:"JSON",
               	data:{channelCode:chnid},
               	async:false,
               	success:function(msg){
               		//var options = " <option value=''>--请选择--</option> ";
               		var options = "";
               		var labelnameidsname="";
               		var labelids=$labelids2.split(",");
	            		for(var i=0;i<msg.length;i++){
	            			 options+="<input type='checkbox' name='lang2'  onclick='labelmap2(this);'  ";
	            			 for(var j=0;j<labelids.length;j++){
	            				 if(labelids[j]==msg[i].value){
		            				 labelnameidsname+=","+msg[i].text;
		 	            			options+="  checked='checked' ";
		            			 break;
	            				 } 
	            			 }
	            			 options+=" value='"+msg[i].value+"'><span>"+msg[i].text+"</span><br/>";
	            		}
	            		 //将值赋值给文本框并在文本里显示出来
	            		 $('#labelName2').combo('setValue', labelnameidsname.substring(1,labelnameidsname.length)).combo('setText', labelnameidsname.substring(1,labelnameidsname.length));
	               		     $("#labellistmap2").html(options);
               	},
               	error:function(XHR, status, errorThrow){
               		
               	}
               });
           }
	//多选下拉框2
	function labelmap2(obj){
            var str = "";
            var strid="";
            $("input[name='lang2']").each(function(){    
                if($(this).is(":checked"))    
                {    
                	strid += ","+$(this).val(); 
                	str += ","+$(this).next('span').text();   
                }    
            });    
            document.getElementById("label2").value=strid.substring(1,strid.length);   
            document.getElementById("labelNames2").value=str.substring(1,str.length);   
             $('#labelName2').combo('setValue', str.substring(1,str.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
	}
	
	//演员 导演选择
	 	 function showactors(code){
    		$(parent).domain("openDialog", { 
	        	iconCls: "icon-view", 
	        	title: "查看", 
	        	src: "${pageContext.request.contextPath}/vod/tv/vodalbuminfo/actorslist",
	        	width: 1060, 
        	height: 550,
	        	onClose: function() { 
	        		var ids = top.$.data(top.document.body, "choose.ids");
	        		var names = top.$.data(top.document.body, "choose.names");
	        	    var  id="";
	        	    var name="";
	        		if(code=='actor'){
	        			if($("#actor").val()=='' || $("#actor").val()==null){
	        				id=ids;
	        				name=names;
	        			}else{
	        				id=$("#actor").val()+','+ids;
	        				name=$("#actorName").val()+','+names;
	        			}
	        			 document.getElementById("actor").value=id; 
    	        		 document.getElementById("actorName").value=name;
	        		}else{
	        			if($("#writer").val()=='' || $("#writer").val()==null){
	        				id=ids;
	        				name=names;
	        			}else{
	        				id=$("#writer").val()+','+ids;
	        				name=$("#writerName").val()+','+names;
	        			}
	        			 document.getElementById("writer").value=id; 
    	        		 document.getElementById("writerName").value=name;
	        		}
	        	 
 	            }
	        });
     	}
	function reset(type){
	if(type=='actor'){
		 document.getElementById("actor").value=''; 
		 document.getElementById("actorName").value='';
	}else{
		 document.getElementById("writer").value=''; 
		 document.getElementById("writerName").value='';
	}
 
}
	 	
		//付费包
		function baglmap(obj){
	            var str = "";
	            var strid="";
	            $("input[name='langbag']").each(function(){    
	                if($(this).is(":checked"))    
	                {    
	                	strid += ","+$(this).val(); 
	                	str += ","+$(this).next('span').text();   
	                }    
	            });    
	             $('#collectfeesbag').combo('setValue', strid.substring(1,strid.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
		}
		//角标
		function cornermap(obj){
	            var str = "";
	            var strid="";
	            $("input[name='langcorner']").each(function(){    
	                if($(this).is(":checked"))    
	                {    
	                	strid += ","+$(this).val(); 
	                	str += ","+$(this).next('span').text();   
	                }    
	            });    
	             $('#corner').combo('setValue', strid.substring(1,strid.length)).combo('setText', str.substring(1,str.length));//将值赋值给文本框并在文本里显示出来
		}
		function upload(type,id){
			var picname="tvalbumpic"+type;
	 		$(parent).domain("openDialog", { 
		        	iconCls: "icon-view", 
		        	title: "上传", 
		        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
		        	width: 450, 
	        	height: 250,
	        	success:function(data){
	        	},
		        	onClose: function() { 
		            }
		        });
	 	}

</script>
