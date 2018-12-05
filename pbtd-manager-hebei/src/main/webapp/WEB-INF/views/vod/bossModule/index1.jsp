<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title></title>
<link rel="stylesheet" type="text/css" href="/js/bossModule/css/style_new.css" />
<link rel="stylesheet" type="text/css" href="/js/bossModule/css/swiper.min.css" />
<style>
.bg {
	position: fixed;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.6);
	top: 0;
	z-index: 9999;
	display: none;
}

#moduleIframe {
	position: fixed;
	top: 100px;
	right: 100px;
	width: 1250px;
	height: 850px;
}
</style>

<link rel="stylesheet" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css" type="text/css">
<link rel="stylesheet" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
<script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>

<script type="text/javascript" src="/js/bossModule/js/swiper.min.js"></script>
<script type="text/javascript" src="/js/bossModule/js/template.js"></script>
<script type="text/javascript" src="/js/bossModule/js/module.js"></script>
<script type="text/javascript">
	var urlStr = "${urlStr}";
	var channel = '${channel}';
	var moduleSequence = '${moduleSequence}';
	var tag = "module";

	module(urlStr, channel, moduleSequence, tag);
	$(function(){
		var obj = "${channelname}"+"本地预览";
		window.parent.addleftText(obj);
	});
	function getScrollTop() {
		var scrollTop = 0;
		if (document.documentElement && document.documentElement.scrollTop) {
			scrollTop = document.documentElement.scrollTop;
		} else if (document.body) {
			scrollTop = document.body.scrollTop;
		}
		return scrollTop;
	}

	function childUpDownFunction(channelname, channel, upmoduleSequence) {
		var obj = channelname+"本地预览";
		window.parent.addleftText(obj);
		var moduleSequence = upmoduleSequence;
		if (upmoduleSequence == "" || upmoduleSequence == ''
				|| upmoduleSequence.length == 0) {
			moduleSequence = "5"
		}
		if (channel == $(".module").attr("channle")) {
			$(".module>h3").each(function(i, ele) {
				var sequence = $(ele).attr("sequence");
				if (moduleSequence === sequence) {
					var scrolltop = $(ele).offset().top;
					$('html,body').animate({ scrollTop : scrolltop }, 'slow');
				}
			});
		} else {
			Loginchannel(channel, channelname);
			setTimeout(function() {
				$(".module>h3").each(function(i, ele) {
					var sequence = $(ele).attr("sequence");
					if (moduleSequence === sequence) {
						var scrolltop = $(ele).offset().top;
						$('html,body').animate({
							scrollTop : scrolltop
						}, 'slow');
					}
				});
			}, 1000);
		}
	}

	function edit(obj) {
		var k = $(obj).attr("value");
		var num = k + 1;
		var moduleId = $(obj).attr("moduleId");
		var scrolltop = $(document).scrollTop() + 100;
		var mId = obj.getAttribute("mId");
		$(".bg").show();
		var url = "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/getalbum/"+ mId + "/" + num;
		var scrollTop = $(document).scrollTop();
		var widthnum = $(window).width();
		var height = $(window).height();
		$(window).domain( "openDialog", {
				iconCls : "icon-view",
				title : "绑定",
				src : "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/getalbum/"+ mId + "/" + num,
				width : widthnum - 20,
				height : 550,
				top : scrolltop,
				onClose : function() {
								var url = "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/getchannelflag";
								$.ajax({
									type : "post",
									url : url,
									data : {"moduleId" : mId},
									cache : false,
									async : false,
									dataType : "json",
									success : function(data, textStatus) {
										if (1 == data) {
											//parent.location.reload(); //刷新父窗口中的网页
											window.parent.searchNode(mId)
											loginModuleId(mId);
											setTimeout(function() {
//												alert(1);
												$(".module>h3").each(function(i, ele) {
														var sequence = $(ele).attr("sequence");
														if (moduleSequence === sequence) {
															var scrolltop = $(ele).offset().top;
															$('html,body').animate({scrollTop : scrolltop},'slow');
														}
													});
											}, 2000);
											return true;
										} else {
											return false;
										}
									},
								error : function(XMLHttpRequest,
										textStatus, errorThrown) {
									alert("请求频道当前状态失败！");
								}
							});
			}})
	};

	function loginModuleId(moduleid) {
		//console.log("根据模版加载");
		$('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmouldbyid/1/' + moduleid);
		$('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmouldbyid/2/' + moduleid)
	};
</script>
</head>
<body>
<%-- 	<h2 style="text-align: center; margin-top: 20px;">${channelname}${zhanshi}预览</h2> --%>
	<div class="module" channle=${channel} style="margin-top:2rem position：relative; top: 100px"></div>
	
	<div id="dd"></div>
	<!-- <div class="bg">
		  <iframe id="moduleIframe" name="moduleIframe" src="" fremeborder="0" scrolling="no"></iframe>
		</div> -->


</body>
</html>
