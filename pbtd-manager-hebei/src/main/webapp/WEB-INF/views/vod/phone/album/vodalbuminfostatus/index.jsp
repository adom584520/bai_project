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
			 <span class="property"> <label class="">是否独播:</label>
			<select id="exclusive" name="exclusive" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">是</option>
			  <option value="0">否</option>
			</select></span>
			<span class="property"> <label class="">phone状               态:</label>
			<select id="status" name="status" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">上线</option>
			  <option value="0">下线</option>
			</select>
			</span>
			 <span class="property"> <label class="">tv状               态:</label>
			<select id="tvstatus" name="tvstatus" style="width:100px;">
			  <option value="">--请选择--</option>
			  <option value="1">上线</option>
			  <option value="0">下线</option>
			</select>
			</span>
				  <br/><hr/>
			 <span class="property" > <label class="">更新时间：</label>
					 <input id="start_time" name="start_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 140px;">
					 <label class="">至</label>
					 <input id="end_time" name="end_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 140px;" >
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
                	var customToolbar = [ sysBtnexport,'-',sysBtn,'-',sysBtn2,'-',sysBtn3 ];
                	t.domain('datagrid', {
                        title: 'phone节目管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/page',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"seriesCode",
                        columns: [[
							 {field: 'cpName', title: 'cp源', width: 40,   align: 'left', hidden: false}
							 ,{field: 'status', title: 'phone状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
	                   			 if(value=="1"){
	                   				return "<span style='color:green'>上线</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>下线</span>";
	                   			 }
						}}
	                    	,{field: 'tvstatus', title: 'tv状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
	                   			 if(value=="1"){
	                   				return "<span style='color:green'>上线</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>下线</span>";
	                   			 }
						}},{field: 'status_next', title: '变更状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
                  			 if(value=="1"){
	                   				return "<span style='color:green'>上线</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>下线</span>";
	                   			 }
						}}
						,{field: 'isCollectfees', title: '收费状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
                 			 if(value=="1"){
	                   				return "<span style='color:green'>是</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>否</span>";
	                   			 }
						}}
						,{field: 'paid_next', title: '变更收费状态', width: 100, sortable: true, align: 'center', hidden: false	,formatter: function(value) { 
                 			 if(value=="1"){
	                   				return "<span style='color:green'>是</span>";
	                   			 }else{
	                   				 return "<span  style='color:red'>否</span>";
	                   			 }
						}}
							 ,{field: 'seriesCode', title: '唯一标识', width: 120,   align: 'center', hidden:false  }
	                    	,{field: 'id', title: 'id', width: 220,   align: 'center', hidden: true}
	                    	,{field: 'seriesName', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'writerName', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'actorName', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'channelName', title: '频道', width: 100,  sortable: true,align: 'center', hidden: false}
	                    	,{field: 'labelName', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	/* ,{field: 'create_time', title: '创建日期', width: 100, sortable: true, align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
								}}
	                    	,{field: 'update_time', title: '更新日期', width: 100,sortable: true,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
	                    		//return $.fmt.formatDate(value, 'yyyy-MM-dd');
								}} */
	                    	/* ,{field: 'Sequence', title: '排序', width: 60,  align: 'center', hidden: false} */
	                    	,{field: 'opt',  title: '操作', width: 60,  align: 'center', hidden: false,formatter: getBtn}
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
	        	 
	          
	 	  	 
	 	  	
	 	   var sysBtnexport = $.extend($.fn.domain.importBtn,{
       		text:"导入数据",
       		handler:function(){
       			$(top).domain("openDialog", { 
                   	iconCls: "icon-importBtn", 
                   	title: "导入数据", 
                   	src: '${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/importalbum', 
                   	width:600, 
                   	height: 300,
                   	onClose: function() {
                   		getalbumin();
                       }
                   });
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
	       	 
	     	 	//phone状态
	       	 	var  sysBtn=$.extend($.fn.domain.Btnsys,{
	                   title:"phone状态",
	                  	text:"phone状态",
	                  	iconCls: "icon-ok", 
	              		handler: function() {
	              			var nodes =  $table.datagrid("getSelections");
	              			if (!nodes || nodes.length == 0) {
	              	            top.$.messager.alert("信息", "请您选择需要的信息", "info", null, 2000);
	              	            return;
	              	        } 
	              	    	var idz = "";
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            idz+=nodes[i]["seriesCode"]+",";
	            	        }
	               	  	idz=idz.substr(0,idz.length-1);
		     	        $.ajax({
		                   url:"${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/updateAlbumstatus",
		                   type:"POST",
		                   dataType:"JSON",
		                   data:{albumid:idz,type:'phone'},
		                   async:false,
		                   success:function(){
		                   $.messager.alert("信息", "操作成功！", "info", null, 2000);
		                   getalbumin();            		
		                 },
		              error:function(XHR, status, errorThrow){
		                    }
		               });
		     	    getinfo();		     	       
	                 },
	                 scope:"all"
	         	});
	     	 	
	       	//tv状态
	       	 	var  sysBtn2=$.extend($.fn.domain.Btnsys2,{
	                   title:"tv状态",
	                  	text:"tv状态",
	                  	iconCls: "icon-ok", 
	              		handler: function() {
	              			var nodes =  $table.datagrid("getSelections");
	              			if (!nodes || nodes.length == 0) {
	              	            top.$.messager.alert("信息", "请您选择需要的信息", "info", null, 2000);
	              	            return;
	              	        } 
	              	    	var idz = "";
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            idz+=nodes[i]["seriesCode"]+",";
	            	        }
	               	  	idz=idz.substr(0,idz.length-1);
		     	        $.ajax({
		     	        	 url:"${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/updateAlbumstatus",
		                   type:"POST",
		                   dataType:"JSON",
		                   data:{albumid:idz,type:'tv'},
		                   async:false,
		                   success:function(){
		                   $.messager.alert("信息", "操作成功！", "info", null, 2000);
		                   getalbumin();             		
		                 },
		              error:function(XHR, status, errorThrow){
		                    }
		               });
		     	    getinfo();		     	       
	                 },
	                 scope:"all"
	         	});
	       	
	       	//收费状态
	       	 	var  sysBtn3=$.extend($.fn.domain.Btnsys3,{
	                   title:"收费状态",
	                  	text:"收费状态",
	                  	iconCls: "icon-ok", 
	              		handler: function() {
	              			var nodes =  $table.datagrid("getSelections");
	              			if (!nodes || nodes.length == 0) {
	              	            top.$.messager.alert("信息", "请您选择需要的信息", "info", null, 2000);
	              	            return;
	              	        } 
	              	    	var idz = "";
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            idz+=nodes[i]["seriesCode"]+",";
	            	        }
	               	  	idz=idz.substr(0,idz.length-1);
		     	        $.ajax({
		     	        	 url:"${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/updateAlbumpaid",
		                   type:"POST",
		                   dataType:"JSON",
		                   data:{albumid:idz},
		                   async:false,
		                   success:function(){
		                   $.messager.alert("信息", "操作成功！", "info", null, 2000);
		                   getalbumin();               		
		                 },
		              error:function(XHR, status, errorThrow){
		                    }
		               });
		     	    getinfo();		     	       
	                 },
	                 scope:"all"
	         	});
        </script>
    </body>
</html>
