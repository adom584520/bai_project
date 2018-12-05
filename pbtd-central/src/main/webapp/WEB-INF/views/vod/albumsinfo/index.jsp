<!--
        *  管理页面
        * 
        * @author admin
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
 a.l-btn span span.l-btn-text {
	width: 60px;
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
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:108px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
			  <span class="property"> <label class="">cp源标识：</label> <input
				type="text" id="seriescode" name="seriescode" style="width: 200px;" />
			</span>
			 <span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="centralcode" name="centralcode" style="width: 200px;" />
			</span>
			
            <span class="property"> <label class="">名称：</label> <input
				type="text" id="seriesname" name="seriesname" style="width: 200px;" />
			</span>
			
			<br/><hr/>
			<span class="property"> <label class="">演员：</label> <input
				type="text" id="actorname" name="actorname" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="writername" name="writername" style="width: 200px;" />
			</span>
			<span class="property"> 
			<label class="">频道：</label>
					<select  id="channel" name="channel" onchange="choosechannellabel(this.value);" style="width: 120px;">
					  <option></option>
					</select>
			</span>
			<span class="property"> <label class="">标签：</label>
					<select  id="label" name="label" style="width: 120px;">
					  <option></option>
					 </select>
			 </span>
			 <span class="property"> <label class="">是否独播：</label>
					<select id="exclusive" name="exclusive" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="">请选择</option>
								<option value="0" >否</option>
								<option value="1">是</option>
					</select>
			 </span>
			 <br/><hr/>
			 
			 <span class="property"> <label class="">更新时间：</label>
					 <input id="updatetime1" name="updatetime1" type="text" class="easyui-datebox" style="width: 90px;" >
					 至
					  <input id="updatetime2" name="updatetime2" type="text" class="easyui-datebox" style="width: 90px;" >
			 </span>
			<span class="property"> <label class="">是否下发：</label>
					<select id="issue" name="issue" style="width: 90px;" class="input text easyui-validatebox-disable">
								<option value="">请选择</option>
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
					</select>
			 </span>
			 <span class="property"> <label class="">cp源：</label>
					<select id="cpcode" name="cpcode" style="width: 90px;" class="input text easyui-validatebox-disable">
							<option value="">请选择</option>
							<c:forEach items="${cpsource}" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
						</select>
			 </span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodchannels"></table>
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
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	//var customToolbar = [$.fn.domain.create,'-',sys1Btn,'-',sysBtn,'-',SysBtn11];
                	var customToolbar = [$.fn.domain.create,'-',sys1Btn,'-',sysBtn];
                	t.domain('datagrid', {
                        title: 'phone数据管理',
                    	url: '${pageContext.request.contextPath}/vod/albuminfo/page',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"centralcode",
                        columns: [[
							{field: 'cpname', title: 'cp源', width: 40,   align: 'left', hidden: false}
							,{field: 'cpcode', title: 'cp源', width: 40,   align: 'left', hidden: true}
							,{field: 'centralcode', title: '唯一标识', width: 120,   align: 'center', hidden: false}
							,{field: 'seriescode', title: 'cp源标识', width: 120,   align: 'center', hidden: false}
	                    	,{field: 'seriesname', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'actorname', title: '演员', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'writername', title: '导演', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'channelname', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'labelname', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'issue', title: '是否下发', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 if(value=='1'){
	                    				 return '是';
	                    			 }else{
	                    				 return '否';
	                    			 }
								}}
	                    	//,{field: 'viewPoint', title: '看点', width: 120, sortable: true, align: 'left', hidden: false}
	                    	,{field: 'updatetime', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
	                    		
	                    		//return $.fmt.formatDate(value, 'yyyy-MM-dd');
								}}
	                    	,{field: 'status',  title: '操作', width: 160,  align: 'center', hidden: false,formatter: getBtn}
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
                $table = $("#vodchannels");
            	$chnId=$("#channel");
    			$tagsids=$("#label");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
                choosechannel();
            });
        	
        function getisStorage(value){
        	 var ckButton='';
        	 if(value=="1"){
		    	 ckButton = "<span style='color: red;'>已入库</span>";
		     }else{
		    	 ckButton = "<span style='color: green;'>未入库</span>";
		     }
        	 return ckButton
        }
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			    	 ckButton = "<button  onclick='show(\""+row.centralcode+"\")' class='easyui-linkbutton'>查看</button>";
			    	 ckButton += "&nbsp;&nbsp;&nbsp;<button  onclick='editvideo(\""+row.seriescode+"\",\""+row.centralcode+"\")' class='easyui-linkbutton'>编辑剧集</button>";
			     return ckButton;
	   		}
	  		  //选择频道
	        	function choosechannel(){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelphone",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:1},
	                       	async:false,
	                       	success:function(msg){
	                       		var options = " <option value=''>--请选择--</option> ";
	    		            		for(var i=0;i<msg.length;i++){
	    		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
	    		            		}
	    		            		$chnId.html(options);
	                       	},
	                       	error:function(XHR, status, errorThrow){
	                       		
	                       	}
	                       });
	                   }
	        	  //选择标签
	        	function choosechannellabel(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/choosechannelphone",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       //	data:{levels:2,parentcode:value},
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

	        	 function show(code){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/vod/albuminfo/show/"+code,
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	            }
	     	        });
	         	}
	        
	        	 //编辑剧集
	        	 function editvideo(code,centralcode){
		         		$(parent).domain("openDialog", { 
		     	        	iconCls: "icon-view", 
		     	        	title: "查看", 
		     	        	src: "${pageContext.request.contextPath}/vod/albuminfo/editvideo/"+code+"/"+centralcode,
		     	        	width: 1060, 
		    	        	height: 550,
		     	        	onClose: function() { 
		     	            }
		     	        });
		         	}
 
	        	 
	        	 //删除
	        	 var  sys1Btn=$.extend($.fn.domain.del,{
	             	title:"删除",
	            	text:"删除",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids="";
	        	    	var cpcode=false;
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=","+nodes[i]["seriescode"];
	        	            if(nodes[i]["cpcode"]!='3'){
	        	            	cpcode=true;
	        	            }
	        	            
	        	        }
	           	        if(cpcode){
		           	     	$.messager.alert("信息","请选择自增的数据进行删除！","info",function(){
	               			}); 
	           	     	return ;
	           	        }
	           	       
	           	     deletes(ids.substr(1,ids.length),2)
	                },
	                scope:"one,more"
	        	});
	        	 function deletes(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/albuminfo/deletes';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{id:ids},
		               	dataType:"JSON",
		               	success:function(result){
		               			$.messager.alert("信息","删除成功！","info",function(){
		               				$table.datagrid("load");
		               			});
		               	},
		               	error:function(XHR, status, errorThrow){
		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
		               	}
	        	   })
	        	 }
	        	  
	        	 //批量操作下发
	        	 var  sysBtn=$.extend($.fn.domain.btnPhoto,{
	             	title:"下发",
	            	text:"下发",
	        		handler: function() {
	        			var nodes =  $table.datagrid("getSelections");
	        	    	if (!nodes || nodes.length == 0) {
	        	            top.$.messager.alert("信息", "请您选择需要下发信息", "info", null, 2000);
	        	            return;
	        	        } 
	        	    	var ids="";
	           	        for (var i = 0; i < nodes.length; i++) {
	        	            ids+=","+nodes[i]["centralcode"];
	        	        }
	           	     save(ids.substr(1,ids.length),2)
	                },
	                scope:"one,more"
	        	});
	        	 
	        	 
	        	 function save(ids,type){
	        		 var  url= '${pageContext.request.contextPath}/vod/albuminfo/savesissue';	  	 
		  			 $.ajax({
		               	url:url,
		               	type:"POST",
		               	data:{id:ids},
		               	dataType:"JSON",
		               	success:function(result){
		               			$.messager.alert("信息","下发成功！","info",function(){
		               				$table.datagrid("load");
		               			});
		               	},
		               	error:function(XHR, status, errorThrow){
		               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
		               	}
	        	   })
	        	 }
	        	   //手动汇聚当天数据
           	  var  SysBtn11= $.extend($.fn.domain.btnSyn,{
                  	title:"每日汇聚",
                 	text:"每日汇聚",
             		handler: function() {
             			curdayalbum();
                     },
                     scope:"one,more"
             	}); 
             	 
           	  function curdayalbum(){
           		  var  url= '${pageContext.request.contextPath}/vod/albuminfo/curdayalbum';	  	 
       	  			 $.ajax({
       	               	url:url,
       	               	type:"POST",
       	               	data:{},
       	               	dataType:"JSON",
       	               	success:function(result){
       	               			$.messager.alert("信息","汇聚成功！","info",function(){
       	               			getVodChannels();
       	               			});
       	               	},
       	               	error:function(XHR, status, errorThrow){
       	               		$.messager.alert("错误信息","系统异常，请联系管理员！","error",function(){});
       	               	}
               	   })
           	  }
           	   
        </script>
    </body>
</html>
