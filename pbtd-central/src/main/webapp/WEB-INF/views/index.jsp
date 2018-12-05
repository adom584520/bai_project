<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>汇聚平台管理</title>
<%@ include file="/js/common/common.jsp"%>
<script type="text/javascript" src="/js/system/index.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width:700px;height:350px;" data-options="fit:'true'">
        <div data-options="region:'north'" style="height: 60px; background: url('/image/index/pbtd_top.png') no-repeat;background-color: #3d9fe4">
        	<div align="right">
				<font>用户名:</font><font>${username}</font>
 	       		<a class="easyui-linkbutton" data-options="iconCls:'icon-logout',plain:false">注销登录</a>
        	</div>
        	<div>
        	</div>
        </div>
        <div data-options="region:'west',split:true"  style="width:200px;" >
            <div class="easyui-accordion" style="width:500px;height:300px;" fit='true'>
		        <div title="菜单"  style="background: url('/image/index/pbtd_menu.png') no-repeat;background-size:100% 100%;" data-options="iconCls:'icon-menu'">
		        	<ul id="tree"></ul>
		        </div>
		    </div>
        </div>
        <div data-options="region:'center',iconCls:'icon-ok'" style="background: url('system.jpg') no-repeat;background-size:cover;">
           	<div id="tabs" class="easyui-tabs" fit=true>
				<div title="主页">
					<div style="margin-top: 10px">
						<div style="width: 100%;float: center;">
							<iframe allowtransparency="true" frameborder="0" style="float: center;" width="100%" height="100" scrolling="no" src="//tianqi.2345.com/plugin/widget/index.htm?s=2&z=1&t=1&v=0&d=2&bd=0&k=&f=ff0000&q=1&e=1&a=1&c=72025&w=410&h=98&align=center"></iframe>
							<div class="easyui-calendar" flt="true" align="center" style="width: 100%;height: 90%"></div>
						</div>
					</div>
				</div>
           	</div>
        </div>
    </div>
</body>
</html>