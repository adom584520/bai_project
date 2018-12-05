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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:108px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
            	<span class="property">
            		<label class="">名称：</label>
            		<input type="text" id="name" name="name" style="width:200px;"/>
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
            <table id="vodCollectfeesbag"></table>
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
                var t = $('#vodCollectfeesbag');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtns5];
                	t.domain('datagrid', {
                        title: '付费包管理',
                    	url: '${pageContext.request.contextPath}/vod/vodCollectfeesbag/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'id', title: 'id', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'code', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'name', title: '名称', width: 100, sortable: true, align: 'center'}
	                    	,{field: 'starttime', title: '开始时间', width: 140, sortable: true, align: 'center', hidden: false,
			                    	formatter: function(value) { 
			                    		 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
		                                 return datetime;     
								}}
	                    	,{field: 'endtime', title: '结束时间', width: 140,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd HH:mm:ss');
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
         	                   openWidth:document.body.clientWidth-520,
       		                openHeight:parent.document.body.clientHeight-220
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
                $table = $("#vodCollectfeesbag");
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
        			updateStatus(ids.substr(1,ids.length),1)
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
        			updateStatus(ids.substr(1,ids.length),0)
        		},
        		scope:"one,more"
        	})
        	function updateStatus(ids,status){
        		$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/vodCollectfeesbag/updateStatus",
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{status:status,id:ids},
                  	async:false,
                  	success:function(){
                  		 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
                  		$("#vodCollectfeesbag").datagrid("load",{});               		
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
                         	url:"${pageContext.request.contextPath}/integrate/outputjson/phone/phonecollectfeesbagip",
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
        	 
	  		 
        </script>
    </body>
</html>
