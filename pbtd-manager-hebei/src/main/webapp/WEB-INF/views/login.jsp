<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="/js/system/login/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="/js/system/login/css/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="/js/system/login/css/component.css" />
<script src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script src="/js/common/custom/core.js"></script>
<script src="/js/common/custom/enc-base64.js"></script>
<script src="/js/common/custom/cipher-core.js"></script>
<script src="/js/common/custom/aes.js"></script>
<script src="/js/common/custom/x64-core.js"></script>
<script src="/js/common/custom/pad-zeropadding.js"></script>
<script src="/js/common/custom/enc-utf8.js"></script>
</head>
<body>
	<div class="container demo-1">
		<div class="content">
			<div id="large-header" class="large-header">
				<canvas id="demo-canvas"></canvas>
				<div class="logo_box">
					<h3>溥博泰道</h3>
					<form id="loginForm" method="post">
						<div class="input_outer">
							<span class="u_user"></span>
							<input id="username" name="username"  class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入用户名">
						</div>
						<div class="input_outer">
							<span class="us_uer"></span>
							<input id="password" name="password"  class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;" type="password" placeholder="请输入密码">
						</div>
						<div class="mb2">
							 <a class="act-but submit" href="javascript:loginValidate();" style="color: #FFFFFF">登录</a>
						</div>
						<div id="nameIsNull" style="display: none;text-align:center;" ><font style="color: white;text-align:center;">用户名不能为空！</font></div>
						<div id="passwordIsNull" style="display: none;text-align:center;" ><font style="color: white;text-align:center;">密码不能为空！</font></div>
						<div id="userError" style="display: none;text-align:center;" ><font id="erroeInfo" style="color: white;text-align:center;">密码或用户名错误！</font></div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/system/login/js/TweenLite.min.js"></script>
	<script src="/js/system/login/js/EasePack.min.js"></script>
	<script src="/js/system/login/js/rAF.js"></script>
	<script src="/js/system/login/js/demo-1.js"></script>
	<script src="/js/system/login/js/login.js"></script>
</body>
</html>