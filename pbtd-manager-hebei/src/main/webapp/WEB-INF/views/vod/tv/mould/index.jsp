<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<TITLE></TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/js/zTreeStyle/css/demo.css" type="text/css">
<link rel="stylesheet" href="/js/zTreeStyle/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/js/common/Bootstrap/css/bootstrap.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/js/common/Messager/message.css">
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: middle;
	*vertical-align: middle
}
.ztree li span.button.synchro {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -111px -80px;
	vertical-align: middle;
	*vertical-align: middle
}
.ztree li span.button.yulan {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -145px -64px;
	vertical-align: middle;
	*vertical-align: middle
}
#exchangeDiv {
	position: fixed;
	/* bottom: 120px; */
	/* right: 0px; */
	top:16px;
	left:0px;
	z-index: 9999;
	width: 20px;
	height: 20px;
	text-align: center;
	line-height: 18px;
	vertical-align:middle;
	background: #efefef;
	color: #333;
	cursor: pointer;
	border-radius: 50%;
	text-decoration: none;
	transition: opacity .2s ease-out;
	border: 1px solid #ddd;
	box-shadow: 0px 10px 10px gray;
	font-size: 18px;
} 
 #iframe1 {
    float：left;
	margin-left: 0.5%;
} 
.topDivCase{
	
}
</style>
</head>

<body style="height:100%;">
<div id="leftMenu" style="width: 17%; height: 100%; float: left;border:1px dashed #fff">
	<div id="exchangeDiv"  dataNum=0>-</div>
	<div style="margin-left:18px;margin-top:10px;">
		<button id="synAllBtn" type="button" class="btn btn-info" style="width:120px;">全部同步</button>
	</div>
		<div style="height: 100%; float: left;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>
<div id="divIframe" style="float:left;margin-left:0.5%;width:82%;height:100%;">
	<div style="height：40px;vertical-align:middle;">
		<div id="leftText" style="margin-left:33%;float:left;font-size:16px;margin-bottom:15px;margin-top:15px;"></div>
		<div id="rightText" style="margin-right:13%;float:right;font-size:12px;margin-bottom:10px;margin-top:15px;"></div>
	</div>
	<iframe id="iframe" width="100%" height="100%" 
		src="${pageContext.request.contextPath}/vod/tv/vodmouldinfo/basePage" scrolling="yes"></iframe>
</div> 
 <!-- <div id="float">点击试试</div>  -->
 
 <!-- <div class="con_right" style="width: 80%; float: left; margin-left: 2%;height:auto;">
	<div id="con_right_left" style="width: 70%;height:100%;float: left;overflow:auto;"></div>
	<div id="con_right_right" style="width: 30%;height:100%;float: right;overflow:auto;"></div>
</div> -->

<script type="text/javascript" src="/js/common/Bootstrap/js/jqueryV1.9.1.js"></script>
<!-- <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script> -->
<script type="text/javascript" src="/js/common/layer/layer.js"></script>
<script type="text/javascript" src="/js/common/Messager/message.min.js"></script>
<script	type="text/javascript" src="/js/zTreeStyle/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/zTreeStyle/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="/js/zTreeStyle/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="/js/common/Bootstrap/js/bootstrap.js"></script>

<SCRIPT type="text/javascript">
	 /* function changeFrameHeight() {  onload="javascript:changeFrameHeight()"
	  var h = document.documentElement.offsetHeight || document.body.offsetHeight
	  var bodyHeight = document.getElementById("iframe"); //寻找ID为content的对象
	  bodyHeight.style.height = (h + 250) + "px"; //你想要自适应高度的对象
	}  */

	var setting = {
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRemoveBtn : showRemoveBtn,
			showRenameBtn : showRenameBtn,
			drag:{
				isCopy : false,
				isMove : true,
				prev : true,
				next : true,
				inner : true
			}
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		async:{
			enable:true,
			url: "/vod/tv/vodmouldinfo/getModuleList"
		},
		callback : {
			beforeDrop:zTreeBeforeDrop,
			onDrop: zTreeOnDrop,
			beforeDrag : beforeDrag,
			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			onRemove : onRemove,
			onRename : onRename
		}
	};

	var zNodes = ${modlulist};
	var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return true;
	}
	function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType, isCopy){
		var treeNode=treeNodes[0];
		console.log(treeNode);
		console.log(targetNode);
		
		//父2父 父2子 
		if(treeNode.parentTId==null && moveType=="inner"){
			return false;
		}
		if(treeNode.parentTId==null && moveType!="inner"){
			if(targetNode.parentTId==null){
				return true;
			}else{
				return false;
			}
		}
		//子2子 
		if(treeNode.parentTId!=null && targetNode.parentTId!=null){
			if(moveType=="inner"){
				return false;
			}else{
				if(treeNode.parentTId==targetNode.parentTId){
					return true;
				}else{
					var r=confirm("确定移动到其他频道？");
					if(r){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		//子2父
		if(treeNode.parentTId!=null && targetNode.parentTId==null){
			if(moveType!="inner"){
				return false;
			}else{  
				if(treeNode.parentTId==targetNode.TId){
					return true;
				}else{
					 var r=confirm("确定移动到其他频道？");
					 if(r){
						 return true;
					 }else{
						 return false;
					 }
				}
			}
		}
		return true;
	}
	
	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var childNodeArr = treeObj.transformToArray(treeObj.getNodes());
		
		var nodeArr=[];
		var treeNodeArr=[];
		var targetNodeArr=[];
		treeNodeArr[0]=treeNodes[0].pId;
		treeNodeArr[1]=treeNodes[0].id;
		targetNodeArr[0]=targetNode.pId;
		targetNodeArr[1]=targetNode.id;
		
		console.log(treeNodes[0]);
		console.log(targetNode);
		
		console.log(treeNodeArr);
		console.log(targetNodeArr);
		
		for(var i=0;i<childNodeArr.length;i++){
			nodeArr[i]=new Array();
			nodeArr[i][0]=childNodeArr[i].id;
			nodeArr[i][1]=childNodeArr[i].pId;
			nodeArr[i][2]=i;
		}
		
		var index1 = layer.load(1, {
			  shade: [0.4,'#fff']
		});
		
		if(nodeArr.length>0){
			$.ajax({
				url:'/vod/tv/vodmouldinfo/updateMoudleSeq',
				type:'post',
				data:{
					nodes:JSON.stringify(nodeArr),
					treeNodeArr:JSON.stringify(treeNodeArr),
					targetNodeArr:JSON.stringify(targetNodeArr)
				},
				dataType:'json',
				success:function(data){
					if(data!=null){
						if(data.code==1){
							/* refreshTree(targetNode);
							refreshTree(treeNodes); */
							refreshTree(null);
							addRedDot(treeNodes[0]);
							layer.close(index1);
							var pNode=findNode("id",treeNodes[0].pId);
							if(pNode!=null){
								document.getElementById("iframe").contentWindow.Loginchannel(pNode.id,pNode.name);	
							}
						}
					}
				}
			});
		}
	}
	
	function refreshTree(node){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.reAsyncChildNodes(node, "refresh");
	}
	
	function addRedDot(treeNode){
		console.log(treeNode);
		var $span=$("#"+treeNode.tId+"_span");
		var redDot="<span style='color: red'>*</span>";
		var existDot=$span.find("span:first").text();
		if(existDot!="*"){
			$span.prepend(redDot);	
		}
		if(treeNode.parentTId!=null){
			var $spanParent=$("#"+treeNode.parentTId+"_span");
			var existPDot=$spanParent.find("span:first").text();
			if(existPDot!="*"){
				$spanParent.prepend(redDot);	
			}
		}
	}
	
	function findNode(key,value){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByParam(key, value, null);
		var node;
		if(nodes!=[]){
			node=nodes[0];
		}
		console.log(node.name);
		return node;
	}
	
	function searchNode(value){
		var node = findNode("id",value);
		addRedDot(node);
	}
	
	function beforeEditName(treeId, treeNode) {
		var addIndex=layer.open({
			  type: 2,
			  title: '编辑模块',
			  closeBtn:1,
			  shadeClose: false,
			  shade: 0.3,
			  area: ['450px', '90%'],
			  content: '/vod/tv/vodmouldinfo/addmould/' + treeNode.id //iframe的url
			}); 
		return false;
	}
	
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		if(confirm("确认删除 节点 -- " + treeNode.name + " 吗？")){
			return true;
			/* return $('#con_right_left').load(
					'/vod/tv/vodmouldinfo/deletemould/' + treeNode.id) */
		}else{
			return false;
		};
	}
	function onRemove(e, treeId, treeNode) {
		if(treeNode.id!=null){
			$.ajax({
				url:'/vod/tv/vodmouldinfo/deleteSingleModule',
				type:'post',
				data:{
					id:treeNode.id
				},success:function(data){
					if(data!=null){
						if(data.code==1){
							addRedDot(treeNode);
							 $.message({
								message:data.message,
								time:1000,
								type:'success'
							}); 
						}else{
							 $.message({
								message:data.message,
								time:1000,
								type:'error'
							});
						} 
					}
				}
			});
		}
		showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "+ treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
	 	className = (className === "dark" ? "" : "dark");
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
				+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
		if (newName.length == 0) {
			setTimeout(function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.cancelEditName();
				alert("节点名称不能为空.");
			}, 0);
			return false;
		}
		return true;
	}
	
	function onRename(e, treeId, treeNode, isCancel) {
		if(!isCancel && treeNode.id!=null){
			$.ajax({
				url:'/vod/tv/vodmouldinfo/renameSingleModule',
				type:'post',
				data:{
					id:treeNode.id,
					name:treeNode.name
				},success:function(data){
					if(data!=null){
					 	if(data.code==1){
						 	$.message({
								message:data.message,
								time:1000,
								type:'success'
							});
							addRedDot(treeNode);
							
						}else{
							 $.message({
								message:data.message,
								time:1000,
								type:'error'
							});
						}
					}
				}
			});
		}
		//document.getElementById("iframe").contentWindow.sayHello();
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
				+ " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
	}
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isFirstNode;
	}
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.isLastNode;
	}
	
	
	function showLog(str) {
	 	if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	
	function getTime() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
				.getSeconds(), ms = now.getMilliseconds();
		return (h + ":" + m + ":" + s + " " + ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add'  style='margin-left:10px;' id='addBtn_"
				+ treeNode.tId + "' title='添加' onfocus='this.blur();'></span>";
		var addStr2 = "<span class='button synchro' style='margin-left:10px;' id='synchroBtn_"
				+ treeNode.tId + "' title='同步' onfocus='this.blur();'></span>";
		/* var addStr3 = "<span class='button yulan' style='margin-left:10px;' id='yulanBtn_"
				+ treeNode.tId + "' title='预览' onfocus='this.blur();'></span>"; */
		sObj.after(addStr + addStr2 /* + addStr3 */);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				var addIndex=layer.open({
					  type: 2,
					  title: '新增模块',
					  closeBtn:1,
					  shadeClose: false,
					  shade: 0.3,
					  area: ['450px', '90%'],
					  content: '/vod/tv/vodmouldinfo/addmould/' + treeNode.id //iframe的url
					}); 
			});

		var btn2 = $("#synchroBtn_" + treeNode.tId);
		if (btn2)
			btn2.bind("click", function() {
				/* return $('#con_right_left').load(
						'/tvreset/resetmodule/' + treeNode.id); */
				var r=confirm("确认同步当前频道？");
				if(r){
					var index = layer.load(1, {
						  shade: [0.4,'#fff'] 
					});
					$.ajax({
						url:'/tvreset/resetmodule/'+treeNode.id,
						type:'get',
						success:function(data){
							if(data!=null){
								layer.close(index);
								if(data.code==1){
									refreshTree(null);
									$.message({
										message:data.message,
										time:1000,
										type:'success'
									});
								}else{
									$.message({
										message:data.message,
										time:1000,
										type:'error'
									});
								}
							}
						}
					});
				}
			});
		
		/* var btn3 = $("#yulanBtn_" + treeNode.tId);
		if (btn3)
			btn3.bind("click", function() {
			/* 	$('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmould/1/' + treeNode.id+'/'+treeNode.name);
				 return  $('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmould/2/' + treeNode.id+'/'+treeNode.name); */
			/*	 var url = "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/basePage?channel="+treeNode.id+"&channelname="+treeNode.name;
				alert(url);
				$("#iframe").attr("src",url); 
			}); */
	};
	
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
		$("#synchroBtn_" + treeNode.tId).unbind().remove();
		$("#yulanBtn_" + treeNode.tId).unbind().remove();
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	function addleftText(obj){
		   $('#leftText').empty();
		   $('#leftText').append(obj);
		   addrightText(obj);
		}
	function addrightText(obj){ 
		   $('#rightText').empty();
		   $('#rightText').append(obj.replace("本地","线上"));
		}
	
	$(document).ready(function() {
	/* 	$("#float").click(function() {
		    $("html,body").animate({scrollTop:0}, 500);
		});  */
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$("#selectAll").bind("click", selectAll);
		
		$("#synAllBtn").click(function(){
			var r=confirm("确认同步所有频道？");
			if(r){
				var index = layer.load(1, {
					  shade: [0.4,'#fff'] //0.1透明度的白色背景
				});
				$.ajax({
					url:'/tvreset/resetAllmodule',
					type:'get',
					success:function(data){
						if(data!=null){
							layer.close(index);
							if(data.code==1){
								refreshTree(null);
								$.message({
									message:data.message,
									time:1000,
									type:'success'
								});
							}else{
								$.message({
									message:data.message,
									time:1000,
									type:'error'
								});
							}
						}
					}
				});
			}
		});
		
		$("#exchangeDiv").click(function(){
			var dataNum=$("#exchangeDiv").attr("dataNum");
			var btnTxt;
			if(dataNum==0){
				$("#leftMenu").css("width","0%");
				$("#leftMenu").css("margin-top","50px");
		        $("#divIframe").css("width","98%");
		        dataNum=1;
		        btnTxt="+";
			}else if(dataNum==1){
				$("#leftMenu").css("width","17%");
				$("#leftMenu").css("margin-top","0px");
		        $("#divIframe").css("width","82%");
		        dataNum=0;
		        btnTxt="-";
			}
			$("#exchangeDiv").attr("dataNum",dataNum);
			$("#exchangeDiv").text(btnTxt);
		});
		
		
		$("#synAllBtn").bind("contextmenu", function(e){
			refreshTree(null);
			e.preventDefault();
			/* findNode("2");
	        $("#leftMenu").animate({width:"60%"});
	        $("#iframe").animate({width:"38%"}); */
	        
	        /* $("#leftMenu").css("width","2%");
	        $("#iframe").css("width","96%"); */
		})
	
		/* $('#con_right_left').load('/vod/tv/vodmouldinfo/yulanmouldforfirst/1');
		$('#con_right_right').load('/vod/tv/vodmouldinfo/yulanmouldforfirst/2');  */
	});
	
	
</SCRIPT>
</body>
</html>
