<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>iOS审核管理</title>
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			$.ajax({
				url : "/phoneMessageAction!phoneAudit.action",
				type : 'post',
				success : function(data) {
					var json = JSON. parse(data);
					if (json.isOnCheckTime) {
						console.log($("#updateAuditButton").val());
						document.getElementById("updateAuditButton").innerHTML="审核中";
						$("#isOnCheckTime").val(0);
					} else {
						$("#updateAuditButton").prop("value","未审核！");
						document.getElementById("updateAuditButton").innerHTML="未审核";
						$("#isOnCheckTime").val(1);
					}
				}
			});
		});
		function updateIOSAudit() {
			var value = $("#isOnCheckTime").val();
			$(function() {
				$.ajax({
					url : "/phoneMessage/updateIOSAudit",
					type : 'post',
					data : {
						status : value
					},
					success : function(data) {
						var json = JSON. parse(data);
						if (json.code == 1) {
							if (value == 1) {
								$("#isOnCheckTime").val(0);
								document.getElementById("updateAuditButton").innerHTML="审核中";
							} else {
								$("#isOnCheckTime").val(1);
								document.getElementById("updateAuditButton").innerHTML="未审核";
							}
						} else {
							alert("访问出错，请稍后重试！");
						}
					}
				});
			})
		}
	</script>
	<div style="padding-left: 10px; padding-top: 20px">
		<input id="isOnCheckTime" type="hidden"/>
		<button id="updateAuditButton" type="button" onclick="updateIOSAudit()" style="height: 25px;width: 80px"></button>
	</div>
</body>
</html>