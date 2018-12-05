<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style type="text/css">
#float {
	position: fixed;
	bottom: 120px;
	right: 0px;
	z-index: 9999;
	width: 60px;
	height: 60px;
	text-align: center;
	line-height: 60px;
	background: #efefef;
	color: #333;
	cursor: pointer;
	border-radius: 50%;
	text-decoration: none;
	transition: opacity .2s ease-out;
	border: 1px solid #ddd;
	box-shadow: 0px 10px 10px gray;
	font-size: 13px;
}
</style>
</head>
<body>

	<div id="con_right_left" style="width: 70%; height: 600px; float: left;" ></div>
	<div id="con_right_right" style="width: 29%; height: 600px; float: right;"></div>
	<div id="float">回到顶部</div>
<link rel="stylesheet" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css" type="text/css">
<link rel="stylesheet" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
<script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
<script type="text/javascript">
	function sayHello(){
		alert("hello,handsome boy");
	}

	$(function(){
		$("#float").click(function() {
		    $("html,body").animate({scrollTop:0}, 500);
		}); 
		var channel = "${channel}";
		var channelname = "${channelname}";
		var moduleId = "${moduleId}";
			//console.log(channel +" <==||==> "+moduleId);
			if(channel == '' || channel == null || channel == "" ||  channel.length == 0){
				if(moduleId == '' || moduleId == null || moduleId == "" ||  moduleId.length == 0 ){
					firstLogin();
				}else{
					console.log("进入模块加载");
					$('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmouldbyid/1/'+moduleId);
					$('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmouldbyid/2/'+moduleId);
				}
			}else{
				Loginchannel(channel,channelname);
			}
	});

	function firstLogin(){
		console.log("进入首次加载");
		$('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmouldforfirst/1');
		$('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmouldforfirst/2');
	}
	function Loginchannel(channel,channelname){
		console.log("进入频道加载");
		$('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmould/1/' + channel+'/'+channelname);
		$('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmould/2/' + channel+'/'+channelname)
	}
</script>

</body>
</html>