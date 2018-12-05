<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>更新版本管理</title>
<!--  Include ext-2.2  -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ext-2.2/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ext-2.2/ext-all.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ext-2.2/source/widgets/grid/GridPanel.js"></script>

<!-- Include ext-2.2 stylesheets here: -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/ext-2.2/resources/css/ext-all.css"></link>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/self/version.js"></script>

<!--<style type="text/css">
.newVer {
	background-color: #00FFFF;
	font-weight: bold;
	font-size: 10;
	color: #003333;
}
</style>  -->

<style type="text/css">
.x-grid3-row td,.x-grid3-summary-row td {
	line-height: 13px;
	vertical-align: middle;
	padding-left: 1px;
	padding-right: 1px;
	-moz-user-select: none;
	-khtml-user-select: none;
	-webkit-user-select: ignore;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	overflow:hidden;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
    padding:3px 3px 3px 5px;
    white-space:normal;
}
</style>
</head>
<body>

</body>
</html>