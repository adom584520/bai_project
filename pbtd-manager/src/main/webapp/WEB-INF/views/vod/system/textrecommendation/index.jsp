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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:75px; width:auto;">
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
            <!--  <br/><hr/>
            <span class="property"> <label class="">下发时间：</label>
					 <input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			 </span> -->
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
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtns5,'-'];
                	t.domain('datagrid', {
                        title: '文字推荐管理',
                    	url: '${pageContext.request.contextPath}/vod/system/textrecommendation/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
                             {field: 'id', title: 'id', width: 100,sortable: true,  align: 'center', hidden: true}
                             ,{field: 'text', title: '文字', width: 100, align: 'center', hidden: false}
                             ,{field: 'channelCode', title: '频道id', width: 100, align: 'center', hidden: false}
                             ,{field: 'seriesCode', title: '专辑id', width: 100, align: 'center', hidden: false}
                             ,{field: 'seriesName', title: '专辑名称', width: 100, align: 'center', hidden: false}
                             ,{field: 'weburl', title: '跳转地址', width: 100, align: 'center', hidden: false}
                             ,{field: 'picStatus', title: '图片是否展示', width: 100, sortable: true, align: 'center', hidden: false,
                            	 formatter: function(value){
 									if (value==0){
 										return "<span  style='color:green'>展示</span>";
 									}else if(value==1){
 										return "<span style='color:red'>不展示</span>";
 									}
 							
 									}}
                             ,{field: 'textPic', title: '图片展示', width: 100, sortable: true, align: 'center', hidden: false,formatter : getimgurl}
                             ,{field: 'playurl', title: '播放地址', width: 100, align: 'center', hidden: false}
                             ,{field: 'type', title: '类型', width: 100, align: 'center', hidden: false,
	                          formatter: function(value,row){
		                       if(value==1){
							    		   return a="<a>点播</a>";
							    	   }
							    	   if(value==2){
							    		   return "<a>直播</a>";
							    	   }
							    	   if(value==3){
							    		   return "<a>webUrl</a>";
							    	   }
							    	   if(value==2){
							    		   return "<a>视频</a>";
							    	   }
										}}
							,{field: 'status', title: '状态', width: 100, align: 'center', hidden: false,
								formatter: function(value,row,index){
									if (value==0) {
										return "<span  style='color:red'>下线</span>";
									}else if(value==1){
										return "<span style='color:green'>上线</span>";
									}
							
									}
								}
							,{field : 'opt', title : '操作', width : 160, align : 'center', hidden : false, formatter : getBtn}
								]],
									onLoadSuccess: function(data, status, XHR) {
								},
									onLoadError: function(XHR, status, errorThrow) {
								},
									names: [
									     ],
									     subject:'文字推荐'
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
                  	url:"${pageContext.request.contextPath}/vod/system/textrecommendation/updateStatus",
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
        	
        	
         	//下发
          	 var  sysBtns5=$.extend($.fn.domain.Btnsys5,{
               	title:"下发",
              	text:"下发",
              	iconCls:"icon-ok",
          		handler: function() {
          			/*  var curtime=$("input[name='curtime']").val();
          			 if(curtime=="" || curtime==null){
          				 top.$.messager.alert("信息", "下发时间不能为空！", "info", null, 2000);
              	            return;
          			 } */
          	      	$.ajax({
                         	url:"${pageContext.request.contextPath}/integrate/outputjson/phone/phonetextrecommendationip",
                         	type:"GET",
                         	dataType:"JSON",
                         	data:{curtime:curtime},
                         	async:false,
                         	success:function(msg){
                         	 top.$.messager.alert("信息", "下发成功！", "info", null, 2000);
              	            return;
                         	},
                         	error:function(XHR, status, errorThrow){
                         		
                         	}
                         });
                  },
                  scope:"all"
          	});
         	
          	function getimgurl(e) {
        		return "<img  style='width:80px;height:40px;'  onclick='showimg(\""
        					+ e + "\")'   src=${maprealm.imgtitle}" + e + "  />";
        		}
            	
        		function showimg(img) {
        			$(parent)
        					.domain(
        							"openDialog",
        							{
        								iconCls : "icon-view",
        								title : "图片查看",
        								src : "${pageContext.request.contextPath}/uploadPic/showimg?img=${maprealm.imgtitle}"
        										+ img,
        								width : 850,
        								height : 550,
        								onClose : function() {
        									//getinfo();
        								}
        							});
        		}
        		
        		function getBtn(value, row, index, text) {
        			var ckButton = '';
        			ckButton = "<a href='#' onclick='show(\"" + row.id
        					+ "\",\"imgurl\")' class='easyui-linkbutton'>图片上传</a>";
        			return ckButton;
        		}
            	
            	function show(id, imgtype) {
        			$(parent)
        					.domain(
        							"openDialog",
        							{
        								iconCls : "icon-view",
        								title : "上传",
        								src : "${pageContext.request.contextPath}/uploadPic/updateimg?picname=textrecommendationimg&id="
        										+ id + "&imgtype=" + imgtype,
        								width : 450,
        								height : 250,
        								onClose : function() {
        									getvodactors();
        								}
        							});
        		}
         	
        </script>
    </body>
</html>
