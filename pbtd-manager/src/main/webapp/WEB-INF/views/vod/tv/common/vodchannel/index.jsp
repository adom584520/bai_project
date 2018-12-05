
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
        <style type="text/css">
        a.l-btn span span.l-btn-text{
        	width:60px;
        }
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:80px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            		<span class="property">
            		<label class="">频道：</label>
	            		<select id="parentCode" name="parentCode"  style="width: 120px;" class="chzn-select">
							<option value="">--请选择--</option>
							<c:forEach var="map" items="${channellist }">
							<option value="${map.channelCode }"<c:if test="${channelCode eq  map.channelCode}">selected="selected"</c:if> >${map.channelName }</option>
							</c:forEach>
						</select>
					</span>
            	<span class="property">
            		<label class="">名称：</label>
            		<input type="text" id="channelName" name="channelName" style="width:200px;"/>
            	</span>	
			  	<span class="property"> <label class="">层级：</label>
					<select id="levels" name="levels"  style="width: 80px;" class="chzn-select">
						<option value="">--请选择--</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</select>
			 </span>
			 <span class="property"> <label class="">状态：</label>
					<select id="status" name="status"  style="width: 80px;" class="chzn-select">
						<option value="">--请选择--</option>
						<option value="0">下线</option>
						<option value="1">上线</option>
					</select>
			 </span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
               <br/><hr/>
             <span class="property"> <label class="">下发时间：</label>
				<input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			 </span>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodchannel"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannel');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtnBind,'-',sysBtnbq,'-',sysBtns5,'-',sysBtn4,'-'];
                	t.domain('datagrid', {
                        title: '频道管理',
                    	url: '${pageContext.request.contextPath}/vod/tv/Vodchannel/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'name', title: '归属频道', width: 100, sortable: true, align: 'center'}
	                    	,{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'channelCode', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'channelName', title: '名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'levels', title: '层级', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'sequence', title: '排序', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field : 'isshow_img',title : '显示图片',width : 100,sortable : true,align : 'center',hidden : false,
								formatter : function(value) {
									if (value == '1') {
										return '是';
									} else {
										return '否';
									}
								}
							},{field : 'isshow_left',title : '显示左侧按钮',width : 100,sortable : true,align : 'center',hidden : false,
								formatter : function(value) {
									if (value == '1') {
										return '是';
									} else {
										return '否';
									}
								}
							},
							{field : 'isshow_right',title : '显示右侧按钮',width : 100,sortable : true,align : 'center',hidden : false,
								formatter : function(value) {
									if (value == '1') {
										return '是';
									} else {
										return '否';
									}
								}
							}
	                    	,{field: 'count', title: '默认返回条数', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'navigationtype', title: '导航栏显示类型', width: 100, sortable: true, align: 'center', hidden: false,
		                    	formatter: function(value) { 
	                   			if(value=='1'){
	                   				return '一级频道';
	                   			}else if(value=='2'){
	                   				return '二级频道';
	                   			}else if(value=='3'){
	                   				return '热播频道';
	                   			}else if(value=='4'){
	                   				return '其他';
	                   			}else{
	                   				return '';
	                   			}
							}}
						,{field: 'create_time', title: '创建时间', width: 100, sortable: true, align: 'center', hidden: false,
			                    	formatter: function(value) { 
		                   			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                return datetime;     
								}}
	                    	,{field: 'update_time', title: '更新时间', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
							}}
							,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
								 if(value=="1"){
		                   				return "<span style='color:green'>上线</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>下线</span>";
		                   			 }
                             
						}}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
         	                    subject:'频道信息'
        	                     
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
                $table = $("#vodchannel");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	//批量上线
        	var sysBtnup=$.extend($.fn.domain.btnup,{
        		title:'上线',
        		text:'上线',
        		iconCls:"icon-ok",
        		handler:function(){
        			var nodes=$table.datagrid("getSelections");
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
        			var nodes=$table.datagrid("getSelections");
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
        	
        	//绑定专辑
    		var sysBtnBind=$.extend($.fn.domain.Btnsys,{
	         	title:"绑定专辑",
	        	text:"绑定专辑",
	        	iconCls:"icon-ok",
	    		handler: function() {
	    			var nodes =  $table.datagrid("getSelections");
	    	    	if (!nodes || nodes.length == 0) {
	    	            top.$.messager.alert("信息", "请您选择一条信息进行绑定专辑", "info", null, 2000);
	    	            return;
	    	        } 
	    	    	if(nodes.length>1){
	    	    		 top.$.messager.alert("信息", "请您只选择一条信息", "info", null, 2000);
	    	    		 return;
	    	    	}
	    	    	//if( nodes[0]["levels"]==1){
	    	    	//	 top.$.messager.alert("信息", "请您选择二级信息进行绑定", "info", null, 2000);
	    	    	//	 return;
	    	    	//}
	    	    var levels=nodes[0]["levels"];    
	    	    if(levels==2){
	    	    	if(nodes[0]["name"]=="" ||nodes[0]["name"]==null || nodes[0]["parentCode"]=="" || nodes[0]["parentCode"]==null){
	    	    		top.$.messager.alert("信息","请先编辑归属频道再绑定专辑","info",null,2000);
	    	    		return;
	    	    	}
	    	    	var channelName=nodes[0]["name"];
	    	    	var  id= nodes[0]["parentCode"];
	    	    	var  channel=nodes[0]["channelCode"];
	    	    }else{
	    	    		 var channelName=nodes[0]["channelName"];
	    	    	     var  id= nodes[0]["channelCode"];
	    	    	     var  channel=0;
	    	    	}
	    		var parentCode=nodes[0]["parentCode"];   
	    	    	$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "绑定", 
	     	        	src: "${pageContext.request.contextPath}/vod/tv/Vodchannel/albuminfo/"+id+"?parentCode="+parentCode+"&channelName="+channelName+"&levels="+levels+"&channel="+channel,
	     	        	width: 1250, 
	    	        	height: 650,
	     	        	onClose: function() {
	     	        	 	
	     	            }
	     	        });
	            },
	            scope:"one"
    	});
        	
        	function updateStatus(ids,status){
        		$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/updateStatus",
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{status:status,id:ids},
                  	async:false,
                  	success:function(){
                  		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
                  		$("#vodchannel").datagrid("load",{});               		
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
       			 var curtime=$("input[name='curtime']").val();
       			 if(curtime=="" || curtime==null){
       				 top.$.messager.alert("信息", "下发时间不能为空！", "info", null, 2000);
	        	            return;
       			 }
       	      	$.ajax({
                      	url:"${pageContext.request.contextPath}/integrate/outputjson/tv/tvchannelip",
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
        	
     	//删除
     	var sysBtndel=$.extend($.fn.domain.del,{
     		title:'删除',
     		text:'删除',
     		iconCls:"icon-remove",
     		handler:function(){
     			var nodes=$table.datagrid("getSelections");
     			if(!nodes||nodes.length==0){
     				top.$.messager.alert("信息", "请您选择需要删除的记录", "info", null, 2000);
     				return;
     			}
                 var status="";
     			var ids="";
     			var levels="";
     			var parentCode="";
     			for(var i=0;i<nodes.length;i++){
     				ids+=","+nodes[i]["id"];
     				status+=nodes[i]["status"];
     				status+=","+nodes[i]["status"];
     				levels+=","+nodes[i]["levels"];
     				var num=nodes[i]["parentCode"];
     				if(num==0){
     				parentCode+=","+1;
     				}else{
     				parentCode+=","+nodes[i]["parentCode"];
     				}
     				if(status==1){
         				top.$.messager.alert("信息", "上线记录不允许删除", "info", null, 2000);
         				return;
         			}
     			}        			
  			  $.messager.confirm('确认','确认删除？',function(r){
			           	if(r==false){return false;}
			           	$.ajax({
		                  	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/deletes",
		                  	type:"GET",
		                  	dataType:"JSON",
		                  	data:{id:ids.substr(1,ids.length),levels:levels.substr(1,levels.length),parentCode:parentCode.substr(1,parentCode.length)},
		                 	async:false,
		                  	success:function(){
		                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
		                  		$("#vodchannel").datagrid("load",{});               		
		                  	},
		                  	error:function(XHR, status, errorThrow){
		                  		
		                  	}
		                  }); 
 			   })
     		},
     		scope:"one,more"
     	})
     	
	  
	        	     	 //绑定标签
    	 var  sysBtnbq=$.extend($.fn.domain.sysBtnbq,{
         	title:"绑定标签",
        	text:"绑定标签",
        	 iconCls:"icon-ok",
    		handler: function() {
    			var nodes =  $table.datagrid("getSelections");
    	    	if (!nodes || nodes.length == 0) {
    	    		top.$.messager.alert("信息", "请您选择一级栏目信息进行绑定", "info", null, 2000);
      	    		 return;
    	        } 
    	    	if(nodes.length>1){
    	    		top.$.messager.alert("信息", "请您选择一级栏目信息进行绑定", "info", null, 2000);
      	    		 return;
    	    	}
    	    	var levels=nodes[0]["levels"];    	    	
    	        if(levels==2){
    	        	top.$.messager.alert("信息", "请您选择一级栏目信息进行绑定", "info", null, 2000);
   	    		 return;
     	        }else{
	    	        var channelName=nodes[0]["channelName"];
	    	        var  id= nodes[0]["channelCode"];
	    	        var  channel=0;
    	    	$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "绑定", 
     	        	src: "${pageContext.request.contextPath}/vod/tv/Vodchannel/getchannellabel/"+id+"?channelName="+channelName+"&levels="+levels+"&channel="+channel,
     	        	width: 1250, 
    	        	height: 650,
     	        	onClose: function() {
     	        	 	
     	            }
     	        });
    	        }
            },
            scope:"one"
    	});
     	 //选中下发
     	var sysBtn4=$.extend($.fn.domain.Sysbtn4,{
     		title:'选中下发',
     		text:'选中下发',
     		iconCls:"icon-ok",
     		handler:function(){
     			var nodes=$table.datagrid("getSelections");
     			if(!nodes||nodes.length==0){
     				top.$.messager.alert("信息", "请您选择需要下发的记录", "info", null, 2000);
     				return;
     			}
                 var status="";
     			var ids="";
     			for(var i=0;i<nodes.length;i++){
     				status+=","+nodes[i]["status"];
     				ids+=","+nodes[i]["channelCode"];
     				if(1!=nodes[i]["status"]){
         				top.$.messager.alert("信息", "请选择上线数据下发", "info", null, 2000);
         				return;
         			}
     			}        			
			           	$.ajax({
		                  	url:"${pageContext.request.contextPath}/integrate/outputjson/tv/tvchannelip",
		                  	type:"GET",
		                  	dataType:"JSON",
		                  	data:{ids:ids.substr(1,ids.length)},
		                 	async:false,
		                  	success:function(){
		                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
		                  		getinfo();		
		                  	},
		                  	error:function(XHR, status, errorThrow){		                  		
		                  	}
		                  });  
 			  
     		},	        		
     		scope:"one,more"
     	})
	        			 
        </script>
    </body>
</html>
