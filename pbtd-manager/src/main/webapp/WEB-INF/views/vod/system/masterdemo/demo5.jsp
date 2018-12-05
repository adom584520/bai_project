<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<title>模版五</title>
		<link rel="stylesheet" href="/js/system/masterdemo/css/style.css" />
		<link rel="stylesheet" href="/js/system/masterdemo/css/swiper.min.css">
		<link rel="stylesheet" type="text/css" href="/js/common/Bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/js/common/Messager/message.css">
	    <style type="text/css"></style>
	</head>

	<body>
		<div class="box4">
    	    	   <div class="list" style="width: 300px;margin-left: 0;" data-id="${curid}" onclick="changeUrl(this,1)">点击编辑</div>
    	    	   <div class="list" data-id="${curid}" onclick="changeUrl(this,2)">点击编辑</div>
    	    	   <div class="list" data-id="${curid}" onclick="changeUrl(this,3)">点击编辑</div>
    	    	   <div class="list" data-id="${curid}" onclick="changeUrl(this,4)">点击编辑</div>
    	    	   <div class="list" data-id="${curid}" onclick="changeUrl(this,5)">点击编辑</div>
	    </div>
    	<div style="width:120px;height:100px;margin-left:640px;margin-top:30px;">
			<button class="btn btn-default" data-id="${curid}"  id="mouidSynBtn">同步线上</button>
		</div>
		<script type="text/javascript" src="/js/system/masterdemo/js/jquery-2.1.0.js"></script>
		<script type="text/javascript" src="/js/system/masterdemo/js/swiper.min.js"></script>
		<script type="text/javascript" src="/js/system/masterdemo/js/template.js"></script>
		<script type="text/javascript" src="/js/system/masterdemo/js/template.js"></script>
		
		<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
        <script type="text/javascript" src="/js/common/Messager/message.min.js"></script>
        <script type="text/javascript" src="/js/mould/mouldSyn.js"></script>
		<script type="text/javascript">
		function changeUrl(obj,num){
			var pid=$(obj).attr("data-id");
			 $(parent).domain("openDialog", { 
  	        	iconCls: "icon-view", 
  	        	title: "绑定", 
  	        	src: "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/getalbum/"+pid+"/"+num,
  	        	width: 1250, 
 	        	height: 650,
  	        	onClose: function() {
  	        		return $('#moban').load('/vod/tv/vodmouldinfo/index');

  	            }
  	        });
		}
		
		</script>
	</body>
</html>