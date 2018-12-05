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
    <form id="vodchannel"  accept-charset="utf-8" style="margin:10px; padding:0; visibility:hidden" method="post" autoTypeset="false" columnSize="2">
	<input type="hidden" id="id" name="id" />
			<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
			<tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		归属频道
				</td>
				<td class="tdContent">
				<select id="parentCode" name="parentCode"  style="width: 120px;" class="chzn-select">
						<option value="0">--请选择--</option>
						
						<c:forEach var="map" items="${channellist }">
						<option value="${map.channelCode }"<c:if test="${parentCode eq  map.channelCode}">selected="selected"</c:if> >${map.channelName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		唯一标识
				</td>
				<td class="tdContent">
				<input type="text" id="channelCode" disabled="disabled" name="channelCode"  <c:if test="${id >0 }">disabled="disabled"</c:if> class="easyui-numberbox"    style=""  data-options="min:10000,max:9999999999,required:true" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		名称
				</td>
				<td class="tdContent">
				<input type="text" id="channelName" name="channelName"   class="input text easyui-validatebox-disable"     style="" data-options="validType:'text[1,30]',required:true" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"   style="width: 120px;" class="tdCaption"  align="center">
		别名
				</td>
				<td class="tdContent">
				<input type="text" id="anotherName" name="anotherName"   class="input text easyui-validatebox-disable"     style="" data-options="validType:'text[1,30]',required:true" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		层级
				</td>
				<td class="tdContent">
				<select id="levels" name="levels"  style="width: 120px;" class="chzn-select">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		排序
				</td>
				<td class="tdContent">
		<input type="text" id="sequence" name="sequence"class="easyui-numberbox"   style="width: 60px" data-options="min:1,max:999,required:true" />	
	</td>
			</tr>
						<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		模板类型
				</td>
				<td class="tdContent">
				<select id="type" name="type"  style="width: 120px;" class="chzn-select" onchange="choosecount(this.value);">
						<option value="1">6张竖图</option>
						<option value="2">4张小横图</option>
						<option value="3">1大横图，4小横图</option>
						<option value="4">1大横图，6竖图</option>
						<option value="5">1大横图，6小横图</option>
						<option value="6">9竖图</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		默认返回条数
				</td>
				<td class="tdContent">
				<input type="text" id="count" name="count"   style="width: 60px"  />
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		是否显示图片
				</td>
				<td class="tdContent">
				<select id="isshow_img" name="isshow_img"  style="width: 120px;" class="chzn-select">
						<option value="0" >否</option>
						<option value="1">是</option>
					</select>
	</td>
			</tr>
				<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		是否显示左侧按钮
				</td>
				<td class="tdContent">
				<select id="isshow_left" name="isshow_left"  style="width: 120px;" class="chzn-select">
						<option value="0"  >否</option>
						<option value="1">是</option>
					</select>
	</td>
			</tr>
				<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		是否显示右侧按钮
				</td>
				<td class="tdContent">
				<select id="isshow_right" name="isshow_right"  style="width: 120px;" class="chzn-select">
						<option value="0" >否</option>
						<option value="1">是</option>
					</select>
	</td>
			</tr>
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		导航栏显示类型
				</td>
				<td class="tdContent">
				<select id="navigationtype" name="navigationtype"  style="width: 120px;" class="chzn-select">
						<option value="1">一级频道</option>
						<option value="2">二级频道</option>
						<option value="3">热播频道</option>
						<option value="4">其他</option>
					</select>
	</td>
			</tr>
			 <tr>
				<td style="text-align: right; width: 25%"  class="tdCaption"  align="center">
		    标签类型
				</td>
				<td class="tdContent">
				
				<select id="labelType" name="labelType"  style="width: 120px;" class="chzn-select">
				
						<option value="0">--请选择--</option>
						
						<c:forEach var="map" items="${labeltypelist}">
						
						<option value="${map.id}"<c:if test="${labelType eq  map.id}">selected="selected"</c:if> >${map.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
		状态
				</td>
				<td class="tdContent">
				<select id="status" name="status"  style="width: 120px;" class="chzn-select">
						<option value="0">下线</option>
						<option value="1">上线</option>
					</select>
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
	        var id = $.query.getId();
	        $('body').css({ visibility: 'visible' });
	        var t = $('#vodchannel');
	        //加载数据
	        t.domain('load', {
	            url: '${pageContext.request.contextPath}/vod/phone/Vodchannel/load/' + id,
	            names: [],
	            onLoadSuccess: function(data, status, XHR) {
	                $('#btnSave').linkbutton('enable')[0].onclick = function() { save(); };
	                if(id<1){
	               		 $("#count").val(6);
	                }
	                $('#count').attr("disabled",true); 
	                chosenCombox(data);  //初始化下拉框
	            },
	            onLoadComplete: function() {
	            	//关闭按钮
                	$('#btnClose').linkbutton('enable')[0].onclick = function() { $(parent).domain('closeDialog'); 
                	}
            }
        });
 
	
        //保存
        function save() {
        	if (t.form('validate') == false) {
        		return false;
        	}
            var data = t.domain('collect');
            $('#btnSave,#btnClose').linkbutton('disable');
	    //如果id为0，是要创建一条新记录，否则是whn
	    var  url= '${pageContext.request.contextPath}/vod/phone/Vodchannel/'+($.query.getId()=="0"?'create':'edit/'+id);  
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
	//设置返回条数
	function choosecount(value){
		var count="";
		if(value==1){
			count=6;
			$('#count').attr("disabled",true); 
		}else if(value==2){
			count=4
			$('#count').attr("disabled",true); 
		}else if(value==3){
			count=5
			$('#count').attr("disabled",true); 
		}else if(value==4){
			count=7
			$('#count').attr("disabled",true); 
		}else if(value==5){
			count=7
			$('#count').attr("disabled",true); 
		}else if(value==6){
			count=9
			$('#count').attr("disabled",true); 
		}else{
			count="";
			$('#count').attr("disabled",false); 
			$('#count').validatebox({ 
				required:true,
				validType:'number'
				});
		}
		$("#count").val(count);
	}
</script>
