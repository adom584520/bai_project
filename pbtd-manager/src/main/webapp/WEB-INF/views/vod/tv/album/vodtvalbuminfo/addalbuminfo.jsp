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
</style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:74px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            <span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			</span>		
            <span class="property"> <label class="">名称：</label> <input
				type="text" id="seriesName" name="seriesName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">导演：</label> <input
				type="text" id="actorName" name="actorName" style="width: 200px;" />
			</span>
			<span class="property"> <label class="">主演：</label> <input
				type="text" id="writerName" name="writerName" style="width: 200px;" />
			</span>
			 <span class="property"> <label class="">状态：</label>
					<select  id="status" name="status" style="width: 120px;">
					  <option value="1" selected="selected">上线</option>
					  <option value="0">下线</option>
					  <option value="">--请选择--</option>
					 </select>
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
					<select  id="label" name="label" style="width: 120px;">
					  <option></option>
					 </select>
			 </span>
			 <span class="property"> 
			<label class="">频道2：</label>
					<select  id="channel2" name="channel2" onchange="choosechannellabel2(this.value);" style="width: 120px;">
						<option value="">--请选择--</option>
						<c:forEach items="${channellist2 }" var="item">
						 	<option value="${item.channelCode}"> ${item.channelName}</option>
						</c:forEach>
					</select>
			</span>
			 <span class="property"> <label class="">标签2：</label>
					<select  id="label2" name="label2" style="width: 120px;">
					  <option></option>
					 </select>
			 </span>
			 <span class="property" > <label class="">更新时间：</label>
					 <input id="start_time" name="start_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;">
					 <label class="">至</label>
					 <input id="end_time" name="end_time" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 130px;" >
			 </span>
 
			 
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="forminfo"></table>
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
                var t = $('#forminfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtn];
                	t.domain('datagrid', {
                        title: 'tv数据管理',
                    	url: '${pageContext.request.contextPath}/vod/tv/vodalbuminfo/page',
                    	queryParams: queryParams,
                    	toolbar:customToolbar,
                    	idField:"id",
                        columns: [[
							{field: 'id', title: 'id', width: 40,   align: 'left', hidden: true}
							,{field: 'cpName', title: 'cp源', width: 40,   align: 'left', hidden: false}
							,{field: 'cpCode', title: 'cp源', width: 40,   align: 'left', hidden: true}
	                    	,{field: 'seriesCode', title: '唯一标识', width: 220,   align: 'center', hidden: false}
	                    	,{field: 'seriesName', title: '名称', width: 140,   align: 'left', hidden: false}
	                    	,{field: 'actorName', title: '导演', width: 60,  align: 'left', hidden: false}
	                    	,{field: 'writerName', title: '演员', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'channelName', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'labelName', title: '标签', width: 120,  align: 'left', hidden: false}
	                    	,{field: 'volumncount', title: '总集数', width: 60,  align: 'center', hidden: false}
	                    	,{field: 'currentnum', title: '更新剧集', width: 60,  align: 'center', hidden: false}
	                    	//,{field: 'viewPoint', title: '看点', width: 120, sortable: true, align: 'left', hidden: false}
	                    	,{field: 'update_time', title: '更新日期', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
	                                 return datetime;     
	                    		
	                    		//return $.fmt.formatDate(value, 'yyyy-MM-dd');
								}}
	                    	,{field: 'update_user', title: '更新用户', width: 60,  align: 'center', hidden: false}
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
                $table = $("#forminfo");
            	$chnId=$("#channel");
    			$tagsids=$("#label");
    			$tags2ids=$("#label2");
    			top.$.data(top.document.body, "choose.ids","");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
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
			    	 ckButton = "<button  onclick='show(\""+row.seriesCode+"\")' class='easyui-linkbutton'>查看</button>";
			     return ckButton;
	   		}
	  		  
	        	  //选择标签1
	        	function choosechannellabel(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/chooselabeltv",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:1,channelCode:value},
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
	        	 //选择标签2
	        	function choosechannellabel2(value){
	                   	$.ajax({
	                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/chooselabeltv",
	                       	type:"GET",
	                       	dataType:"JSON",
	                       	data:{levels:1,channelCode:value},
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

	        	 function show(id){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "查看", 
	     	        	src: "${pageContext.request.contextPath}/vod/tv/vodalbuminfo/show/"+id,
	     	        	width: 1060, 
	    	        	height: 550,
	     	        	onClose: function() { 
	     	            }
	     	        });
	         	}
	        
	        	 
	        	 
	        	//绑定专辑
	        	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
	                    title:"确认",
	                   	text:"确认",
	                   	iconCls: "icon-view", 
	               		handler: function() {
	               			var idnew = $.query.getId();
	               			var nodes = $table.datagrid("getSelections");
	               	    	if (!nodes || nodes.length == 0) {
	               	            top.$.messager.alert("信息", "请您选择需要绑定信息", "info", null, 2000);
	               	            return;
	               	        } 
	               	    	var ids = "";
	               	    	var names = [];
	               	        for (var i = 0; i < nodes.length; i++) {
	            	            ids+=","+nodes[i]["id"];
	            	        }
	               	     	top.$.data(top.document.body, "choose.ids",ids.substr(1,ids.length));
	            	        $(top).domain('closeDialog');
	                       },
	                       scope:"one,more"
	               	});
	        	 
        </script>
    </body>
</html>
