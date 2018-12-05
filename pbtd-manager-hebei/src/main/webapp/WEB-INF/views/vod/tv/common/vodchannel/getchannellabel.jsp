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
        a.l-btn span span.l-btn-text {
        width:60px;
        }
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:38px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
                <span class="property">
            		<label class="">标签分类：</label>
	            		<select id="typeid" name="typeid"  style="width: 120px;" class="chzn-select">
							<option value="">--请选择--</option>
							<c:forEach var="map" items="${labeltypelist }">
							<option value="${map.id}"<c:if test="${id eq  map.id}">selected="selected"</c:if> >${map.name }</option>
							</c:forEach>
						</select>
					</span>
                <a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodinfo"></table>
        </div> 
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
            	  var f = $('#formQuery');
            	  var channel = "${channel}";
            	 var queryParams = f.domain('collect');
            	 queryParams.channel=channel;
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodinfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtn,'-',sysBtndel,'-',sysBtn1];
                	t.domain('datagrid', {
                        title: '频道绑定标签管理',
                    	url: '${pageContext.request.contextPath}/vod/tv/Vodchannel/getchannellabelpage',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden:true}
	                    	,{field: 'typeid', title: '标签分类id', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'label', title: '标签id', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'labelname', title: '标签名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'typename', title: '分类名称', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'sequence', title: '排序', width: 100, sortable: true, align: 'center', hidden: false,
	                    		editor:{
	                    			type:"numberbox",
	                    			options:{
	                    				min:0,   
	                    			    precision:0,
	                    			    max:100
	                    			}}}     
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
         	                   subject: '绑定管理',
       		                   onClickRow:function(index,row){
       	                    	$table.datagrid('unselectAll');
       	                    	$table.datagrid('selectRow', index).datagrid('beginEdit', index);
       	                    },
       	                    isHeaderMenu:false
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
                $table = $("#vodinfo");
                var channel = "${channel}";
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	//删除
    		var sysBtndel = $.extend($.fn.domain.del,{
							title : '删除',
							text : '删除',
							iconCls : "icon-remove",
							handler : function() {
								var nodes = $table.datagrid("getSelections");
								if (!nodes || nodes.length == 0) {
									top.$.messager.alert("信息", "请您选择需要删除的记录","info", null, 2000);
									return;
								}
								var ids = "";
								var labeltype = "";
								for (var i = 0; i < nodes.length; i++) {
									ids += "," + nodes[i]["id"];
									labeltype+= "," + nodes[i]["typeid"];
								}
								$.messager.confirm('确认','确认删除？',function(r) {
					if (r == false) {
						return false;
					}
					$.ajax({
								url : "${pageContext.request.contextPath}/vod/tv/Vodchannel/deletebindinglabel",
								type : "GET",
								dataType : "JSON",
								data : {id : ids.substr(1,ids.length),labeltype : labeltype.substr(1,ids.length),channel:"${channel}"},
								async : false,
								success : function() {
									top.$.messager.alert("信息","操作成功！","info",null,2000);
									getinfo();
								},
								error : function(XHR,status,errorThrow) {
								}
								
							});
					     getinfo();
				})
							},
							scope : "one,more"
						})
    		//绑定标签
    		var sysBtn = $.extend($.fn.domain.Btnsys,{
			title : "添加标签",
			text : "添加标签",
			iconCls : "icon-view",
			handler : function() {
			$(parent).domain("openDialog",{
				iconCls : "icon-view",
				title : "添加",
				src : "${pageContext.request.contextPath}/vod/tv/Vodchannel/addchannellabel/"+"${channel}",
				width : 1250,
				height : 650,
				onClose: function() { 
					getinfo(); 
				}
    			})
			}
    		})
    		//保存绑定专辑排序
       	 var  sysBtn1=$.extend($.fn.domain.save,{
                   title:"保存",
                  	text:"保存",
                  	iconCls: "icon-save", 
              		handler: function() {
              			var nodes = $table.datagrid("getRows");
       				for(var i=0;i<nodes.length;i++){
       					$table.datagrid("endEdit",i);
       				}
       				nodes = $table.datagrid("getChanges");
              	    	if (!nodes || nodes.length == 0) {
              	            top.$.messager.alert("信息", "请您选择需要保存信息", "info", null, 2000);
              	            return;
              	        } 
              	    	var ids = "";
              	    	var sequence = "";
              	    	var id="";
              	    	var labeltype="";
              	        for (var i = 0; i < nodes.length; i++) {
           	            ids+=","+nodes[i]["id"];
           	            sequence+=","+nodes[i]["sequence"];
           	            labeltype+=","+nodes[i]["typeid"]
           	        }
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/tv/Vodchannel/updatelabelsequence",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{id:ids.substr(1,ids.length),sequence:sequence.substr(1,sequence.length),labeltype:labeltype.substr(1,sequence.length),channel:"${channel}"},
   	                           	async:false,
   	                           	success:function(){
   	                           	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000);
   	                              getinfo();               		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           	}
   	                           });
   	     	        		 getinfo();
   	     	       
                      },
                      scope:"one,more"
              	})
    	 
        </script>
    </body>
</html>
