<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title> 管理</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            	<span class="property">
            		<label class="">频道：</label>            		    
	            		<select id="channel_id" name="channel_id"  style="width: 120px;" class="channel-select">		
						<option value="">--请选择--</option>
						<c:forEach var="map" items="${channellist }">
							<option value="${map.channelCode }"<c:if test="${channel_id eq  map.channelCode}">selected="selected"</c:if> >${map.channelName }</option>
							</c:forEach>
						</select>
					</span>
					<span class="property">
	            		<label class="">专辑名称：</label>
	            		<input type="text" id="name" name="name" style="width:200px;"/>
	            	</span>	
				  	<span class="property"> <label class="">专辑id：</label>
						<input id="target_code" name="target_code"  style="width: 80px;" type="text" />
				 </span>
				 <span class="property"> <label class="">状态：</label>
						<select id="status" name="status"  style="width: 80px;" class="status-select">
						<option value="">--请选择--</option>
						<option value="0">已下线</option>
						<option value="1">已上线</option>
					</select>
				 </span>
             <a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="recommandPic"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getvodactors() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#recommandPic');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtnreset];
                	t.domain('datagrid', {
                        title: '频道轮播图管理',
                    	url: '${pageContext.request.contextPath}/vod/system/recommandpic/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
                             {field: 'id', title: 'id', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'channel_id', title: '频道ID', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'target_code', title: '专辑id', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'sequence', title: '排序', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'weburl', title: '跳转地址', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'playurl', title: '播放地址', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'viewPoint', title: '看点', width: 100, sortable: true, align: 'center', hidden: false}
                             ,{field: 'type', title: '类型', width: 100, sortable: true, align: 'center', hidden: false,
	                          formatter: function(value,row){
		                       if(value==1){
							    		   return a="<a>点播</a>";
							    	   }
							    	   if(value==2){
							    		   return "<a>直播</a>";
							    	   }
							    	   if(value==3){
							    		   return "<a>web页面</a>";
							    	   }
							    	   if(value==4){
							    		   return "<a>专题</a>";
							    	   }
							    	   if(value==5){
							    		   return "<a>图片</a>";
							    	   }
							    	   if(value==6){
							    		   return "<a>视频</a>";
							    	   }
										}}
							,{field: 'imageUrl', title: '图片Url', width: 100, sortable: true, align: 'center', hidden: false,
								formatter: function(imageUrl,row,index){
									if ('' !=imageUrl && null !=imageUrl) {
										imageUrl = '<img style="width:30px; height:30px" src="${maprealm.imgtitle}'+ imageUrl + '">';
										return imageUrl;
									}
								}
							}
							,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false,
								formatter: function(value,row,index){
									if (value==0) {
										return "<span  style='color:red'>下线</span>";
									}else if(value==1){
										return "<span style='color:green'>上线</span>";
									}
							
									}
								}
							,{field: 'made', title: '操作图片', width: 100, sortable: true, align: 'center',value:'', hidden: false,
								formatter: function(value,row) { 
									return "<a href='javascript:upload("+row.id+")'>上传图片</a>";  
									 
							}
							}
								]],
									onLoadSuccess: function(data, status, XHR) {
								},
									onLoadError: function(XHR, status, errorThrow) {
								},
									names: [
									     ],
									     subject:'频道推荐轮播图片'
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
                $t = $("#recommandPic");
               //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
            });
        	function upload(id){
        		var picname="recommandPic";
         		$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "上传", 
     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
     	        	width: 450, 
    	        	height: 250,
    	        	success:function(data){
    	        		$("#recommandPic").datagrid("load",{});         
    	        	},
     	        	onClose: function() { 
     	        		$("#recommandPic").datagrid("load",{});         
     	            }
     	        });
         	}      		
    	
        	//批量上线
        	var sysBtnup=$.extend($.fn.domain.btnup,{
        		title:'上线',
        		text:'上线',
        		iconCls:"icon-ok",
        		handler:function(){
        			var nodes=$t.datagrid("getSelections");
        			if(!nodes||nodes.length==0){
        				top.$.messager.alert("信息", "请您选择需要上线的记录", "info", null, 2000);
        				return;
        			}
        			var ids="";
        			for(var i=0;i<nodes.length;i++){
        				ids+=","+nodes[i]["id"];
        			}
        			 $.messager.confirm('确认','确认上线？',function(r){
         	        	if(r==false){return false;}
         	        	updateStatus(ids.substr(1,ids.length),1)
         	        })
        		},
        		scope:"one,more"
        	})
        	//批量下线
        	var sysBtnno=$.extend($.fn.domain.btnno,{
        		title:'下线',
        		text:'下线',
        		iconCls:"icon-no",
        		handler:function(){
        			var nodes=$t.datagrid("getSelections");
        			if(!nodes||nodes.length==0){
        				tob.$.messager.alert("信息","请您选择需要上线的记录","info",null,2000);
        				return;
        			}
        			var ids="";
        			for(var i=0;i<nodes.length;i++){
        				ids+=","+nodes[i]["id"];
        			}
        			$.messager.confirm('确认','确认下线？',function(r){
         	        	if(r==false){return false;}
         	        	updateStatus(ids.substr(1,ids.length),0)
         	        })
        		},
        		scope:"one,more"
        	})
        	function updateStatus(ids,status){
        		$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/system/recommandpic/updateStatus",
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{status:status,id:ids},
                  	async:false,
                  	success:function(){
                  		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000); 
                  		$("#recommandPic").datagrid("load",{});               		
                  	},
                  	error:function(XHR, status, errorThrow){
                  		
                  	}
                  }); 
        	}
        	
        	
        	//同步
        	var sysBtnreset=$.extend($.fn.domain.reset,{
        		title:'同步',
        		text:'同步',
        		iconCls:"icon-reset",
        		handler:function(){
        			$.ajax({
                      	url:"${pageContext.request.contextPath}/phonereset/resetrecommandpic",
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
        </script>
    </body>
</html>
