<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title> 管理</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
        <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
<style type="text/css">
a.l-btn span span.l-btn-text {
	width: 28px;
}
button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	background-color:;
}
button:hover {
	text-decoration: none;
}
button:active {
	position: relative;
	top: 1px;
}
a.l-btn span span.l-btn-text{
   width:60px;
        }
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:120px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            	<span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			</span>
            		<span class="property"> <label class="">名称：</label> <input
				type="text" id="seriesName" name="seriesName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="writerName" name="writerName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">主演：</label> <input
				type="text" id="actorName" name="actorName" style="width: 200px;" />
			</span>
			<br/><hr/>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="channel" name="channel" onchange="choosechannellabel(this.value);" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${channellist }" var="item">
						 	<option value="${item.channelCode}"> ${item.channelName}</option>
						</c:forEach>					  
					</select>
			</span>
			<span class="property"> <label class="">标签：</label>
					<select  id="label" name="label" style="width: 100px;">
					  <option></option>
					 </select>
			 </span>
			<%--  <span class="property"> 
			<label class="">频道2：</label>
					<select  id="channel2" name="channel2" onchange="choosechannellabel2(this.value);" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${channellist2 }" var="item">
						 	<option value="${item.channelCode}"> ${item.channelName}</option>
						</c:forEach>
					</select>
			</span>
			 <span class="property"> <label class="">标签2：</label>
					<select  id="label2" name="label2" style="width: 100px;"> 
					  <option></option>
					 </select>
			 </span> --%>
			 <span class="property" > <label class="">更新时间：</label>
					 <input id="start_time" name="start_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 140px;">
					 <label class="">至</label>
					 <input id="end_time" name="end_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 140px;" >
			 </span>
			 <br/><hr/>
			 <span class="property"> <label class="">状               态:</label>
			<select id="status" name="status" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">上线</option>
			  <option value="0">下线</option>
			</select>
			</span>
			<span class="property"> <label class="">注入状态:</label>
			<select id="injectstatus" name="injectstatus" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">已注入</option>
			  <option value="2">注入中</option>
			  <option value="0">未注入</option>
			</select>
			</span>
			<span class="property"> 
			<label class="">cp源：</label>
					<select  id="cpCode" name="cpCode" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${sourceTypelist }" var="item">
						 	<option value="${item.code}"> ${item.name}</option>
						</c:forEach>
					</select>
			</span>
                <a href="javascript:getalbumin()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="phonealbuminfo"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        <script type="text/javascript"> 
            function getalbumin() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#phonealbuminfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.edit,'-',sysBtn,'-',sysBtnz,'-',sysBtns,'-',sysBtnup,'-',sysBtnno,'-',sysBtnreset,'-',sysBtnexport,sysBtns6,'-'];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/vodalbuminfo/page',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"seriesCode",
                        columns: [[
							 {field: 'cpName', title: 'cp源', width: 40,   align: 'left', hidden: false}
	                    	,{field: 'seriesCode', title: '唯一标识', width: 120,   align: 'center', hidden:false  }
	                    	,{field: 'id', title: 'id', width: 220,   align: 'center', hidden: true}
	                    	,{field: 'seriesName', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'writerName', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'actorName', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'channelName', title: '频道', width: 100,  sortable: true,align: 'center', hidden: false}
	                    	,{field: 'labelName', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'corner', title: '角标', width: 40,  align: 'center', hidden: false}
	                    	,{field: 'create_time', title: '创建日期', width: 100, sortable: true, align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
								}}
	                    	,{field: 'update_time', title: '更新日期', width: 100,sortable: true,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
	                    		//return $.fmt.formatDate(value, 'yyyy-MM-dd');
								}}
	                    	
	                    	,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
	                   			 if(value=="1"){
	                   				return "<span style='color:green'>上线</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>下线</span>";
	                   			 }
                            
						}}
	                    	/* ,{field: 'Sequence', title: '排序', width: 60,  align: 'center', hidden: false} */
	                    	,{field: 'opt',  title: '操作', width: 160,  align: 'center', hidden: false,formatter: getBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '节目集管理',
        	                    openWidth:document.body.clientWidth+20,
        		                openHeight:parent.document.body.clientHeight-20
                    });
                }
                else {
                    t.datagrid("load", queryParams);
                }
            }
        	
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#phonealbuminfo");
            	$chnId=$("#channel");
    			$tagsids=$("#label");
    			/* $tags2ids=$("#label2"); */
    			choosechannellabel($chnId);
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getalbumin();
            });
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			    	 ckButton = "<button  onclick='show(\""+row.id+"\")' class='easyui-linkbutton'>查看</button>";
			    	 ckButton += "&nbsp;&nbsp;&nbsp;<button  onclick='editvideo(\""+row.id+"\")' class='easyui-linkbutton'>编辑剧集</button>";
			     return ckButton;
	   		}

	        	  //选择标签
	        	function choosechannellabel(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/chooselabel",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:2},
	                       	async:false,
	                       	success:function(msg){
	                       		var options = " <option value=''>--请选择--</option> ";
	    		            		for(var i=0;i<msg.length;i++){
	    		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	    		            		}
	    		            		$tagsids.html(options);
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       		
	                       	}
	                       });
	                   }
	        	/*  //选择标签2
	        	function choosechannellabel2(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/chooselabel",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:2,channelCode:value},
	                       	async:false,
	                       	success:function(msg){
	                       		var options = " <option value=''>--请选择--</option> ";
	    		            		for(var i=0;i<msg.length;i++){
	    		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	    		            		}
	    		            		$tags2ids.html(options);
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       		
	                       	}
	                       });
	                   }
  */ 
	        
	        	 //编辑剧集
	        	 function editvideo(code){
		         		$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "编辑剧集", 
		     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/editvideo/"+code,
		     	        	width: 1060, 
		    	        	height: 550,
		     	        	onClose: function() { 
		     	            }
		     	        });
		         	}
	        	//节目关联推荐
	        	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
	             	title:"关联推荐",
	            	text:"关联推荐",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	if(nodes.length>1){
	        	    		 top.$.messager.alert("信息", "请您选择一条专辑信息", "info", null, 2000);
	        	    		 return;
	        	    	}	        	    	 
	        	    	var  id= nodes[0]["id"];
	        	    	$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/albuminforecommand/"+id,
		     	        	width: 1250, 
		    	        	height: 650,
		     	        	onClose: function() {
		     	        	 	
		     	            }
		     	        });
	                },
	                scope:"one"
	        	});	 
	        	
	        	  	//绑定角标
	        	 var  sysBtnz=$.extend($.fn.domain.Btnsys,{
	             	title:"角标绑定",
	            	text:"角标绑定",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 

	        	    	var ids = "";
               	        for (var i = 0; i < nodes.length; i++) {
            	            ids+=nodes[i]["id"]+",";
            	        }
	        	    	$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/vodCorner/albuminfo/phoneCorner/"+ids,
		     	        	width: 650, 
		    	        	height: 400,
		     	        	onClose: function() {
		     	        		getalbumin();
		     	            }
		     	        });
	                },
	                scope:"one"
	        	});
	        	
	        	//绑定付费包
	        	 var  sysBtns=$.extend($.fn.domain.Btnsys,{
	             	title:"付费绑定",
	            	text:"付费绑定",
	            	iconCls:"icon-ok",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择专辑", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids = "";
               	        for (var i = 0; i < nodes.length; i++) {
            	            ids+=nodes[i]["id"]+",";
            	        }
               	     $(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "绑定", 
		     	        	src: "${pageContext.request.contextPath}/vod/vodCollectfeesbag/albuminfo/phoneCollectfeesbag/"+ids,
		     	        	width: 650, 
		    	        	height: 400,
		     	        	onClose: function() {
 	     	        		
		     	            }
		     	        });   
	                },
	                scope:"one"
	        	});
	        	 //批量操作上线
	           	 var  sysBtnup=$.extend($.fn.domain.btnup,{
	                title:"上线",
	               	text:"上线",
	               	iconCls:"icon-ok",
	           		handler: function() {
	           			var nodes =  $table.datagrid("getSelections");
	           	    	if (!nodes || nodes.length == 0) {
	           	            top.$.messager.alert("信息", "请您选择需要上线的记录", "info", null, 2000);
	           	            return;
	           	        } 
	           	    	var ids="";
	              	        for (var i = 0; i < nodes.length; i++) {
	           	            ids+=","+nodes[i]["id"];
	           	        }
	              	      $.messager.confirm('确认','确认上线？',function(r){
	            	        	if(r==false){return false;}
	            	           udpatezt(ids.substr(1,ids.length),1)
	            	        })
	                   },
	                   scope:"one,more"
	           	});
	           	 //批量操作下线
	           	 var  sysBtnno=$.extend($.fn.domain.btnno,{
	                title:"下线",
	               	text:"下线",
	            	iconCls:"icon-no",
	           		handler: function() {
	           			var nodes =  $table.datagrid("getSelections");
	           	    	if (!nodes || nodes.length == 0) {
	           	            top.$.messager.alert("信息", "请您选择需要下线的记录", "info", null, 2000);
	           	            return;
	           	        } 
	           	    	var ids="";
	              	        for (var i = 0; i < nodes.length; i++) {
	           	            ids+=","+nodes[i]["id"];
	           	        }
	              	      $.messager.confirm('确认','确认下线？',function(r){
	           	        	if(r==false){return false;}
	           	        	 udpatezt(ids.substr(1,ids.length),0)
	           	        })
	                   },
	                   scope:"one,more"
	           	}); 
	           	 
	           	 
	           	 function udpatezt(ids,status){
	           		  	$.ajax({
	                          	url:"${pageContext.request.contextPath}/vod/phone/vodalbuminfo/updateStatus",
	                          	type:"GET",
	                          	dataType:"JSON",
	                          	data:{status:status,id:ids},
	                          	async:false,
	                          	success:function(){
	                          		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
	                          		getalbumin();            		
	                          	},
	                          	error:function(XHR, status, errorThrow){
	                          		
	                          	}
	                          }); 
	           	 }
	           	 
	           //当天更新同步
	          	var sysBtnreset=$.extend($.fn.domain.reset,{
	          		title:'当天同步',
	          		text:'当天同步',
	          		iconCls:"icon-reset",
	          		handler:function(){
	          			$.ajax({
	                        	url:"${pageContext.request.contextPath}/phonereset/resetalbum",
	                        	type:"GET",
	                        	dataType:"JSON",
	                        	data:{},
	                        	async:false,
	                        	success:function(){
	                        		 top.$.messager.alert("信息", "同步成功！", "info", null, 2000); 
	                        	},
	                        	error:function(XHR, status, errorThrow){
	                        		
	                        	}
	                        }); 
	          		},
	          		scope:"all"
	          	})
	          	//选中同步
	        	   var  sysBtns6=$.extend($.fn.domain.Btnsys6,{
	    	        title:"选中同步",
	    	        text:"选中同步",
	    	        iconCls:"icon-reset",
	        		handler:function(){
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择要同步的专辑", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids = "";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=nodes[i]["id"]+",";
	        	        }
	        			$.ajax({
	                      	url:"${pageContext.request.contextPath}/phonereset/resetalbum",
	                      	type:"GET",
	                      	dataType:"JSON",
	                      	data:{'ids':ids},
	                      	async:false,
	                      	success:function(){
	                      		 top.$.messager.alert("信息", "同步完成！", "info", null, 2000); 
	                      	},
	                      	error:function(XHR, status, errorThrow){
	                      		
	                      	}
	                      }); 
	        		},
	        		scope:"all"
	        	})
	 	  	var sysBtnexport =	  $.extend($.fn.domain.exports,{
	    		 text:"导出数据",
	    		 title:"导出数据",
	    		 iconCls:"icon-export",
	    		 handler:function(){
	    			var queryParams = $form.serialize();
                    var url = "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/exportalbum?"+ queryParams;
                    window.location.href = url;
	    		 }
	    	 });
	          	 function show(code){
		         		$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "查看", 
		     	        	src: "${pageContext.request.contextPath}/vod/phone/vodalbuminfo/show/"+code,
		     	        	width: 1060, 
		    	        	height: 550,
		     	        	onClose: function() { 
		     	            }
		     	        });
		         	}
        </script>
    </body>
</html>
