<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title></title>
<link rel="stylesheet" type="text/css"
	href="/js/bossModule/css/style_new2.css" />
<link rel="stylesheet" type="text/css"
	href="/js/bossModule/css/swiper.min.css" />
</head>
<body>
	<%-- <h2 style="text-align: center; margin-top: 20px;">${channelname}${zhanshi}预览</h2> --%>
	<div class="module2"></div>
	<!-- <script type="text/javascript" src="/js/bossModule/js/jquery.min.js"></script> -->
	<script type="text/javascript" src="/js/bossModule/js/swiper.min.js"></script>
	<script type="text/javascript" src="/js/bossModule/js/template.js"></script>
	<script type="text/javascript" src="/js/bossModule/js/module.js"></script>
	<script type="text/javascript">	
		//var urlStr="http://192.168.0.220:8083/tv/vod/module/";
		var urlStr="${urlStr}";
		var channel='${channel}';
		var moduleSequence='${moduleSequence}';
		var tag= "module2";
		//alert(urlStr,channel,tag);
		//alert(moduleSequence);
		try
			{
			module(urlStr,channel,moduleSequence,tag);
			}
		catch(err)
		{
		alert("线上接口请求失败");
		}
		//module("1525982558","6");
		</script>
</body>
</html>
