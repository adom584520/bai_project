<!--
        *  热搜管理
        * @author shenjr
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>热搜管理</title>
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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:80px; width:auto;">
      	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
				 <span class="property"> <label class="">专辑标识：</label> <input
				type="text" id="seriesCode" name="seriesCode" style="width: 200px;" />
			    </span>
				 <span class="property">
				 		<label class="">专辑名称：</label>
						<input type="text" id="seriesName" name="seriesName" style="width:200px;"/>
						<a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
				 </span>
                 <br/><hr/>
                <span class="property"> <label class="">下发时间：</label>
					 <input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			    </span>
        </form>
      </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodlabel"></table>
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
            	 
                var t = $('#vodlabel');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined'){
             	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [sysBtn,'-',sysBtn1,'-',sysBtn3,sysBtns5];
                	t.domain('datagrid', {
                		title:'热搜管理',
                    	url:'${pageContext.request.contextPath}/vod/phone/vodHotSearch/page',
                        toolbar:customToolbar,
                        openWidth:1100,
                        openHeight:530,
                    	showFooter:false,
                    	columns: [
                                  [
    								{
    								    field: 'id',
    								    title: 'ID',
    								    width: 100,
    								    align: 'center',
    								    hidden:true
    								},{
    								    field: 'seriesCode',
    								    title: '专辑ID',
    								    width: 100,
    								    align: 'center'
    								},
    								{
    								    field: 'seriesName',
    								    title: '名称',
    								    width: 150,
    								    align: 'center'
    								},{
    								    field: 'writerName',
    								    title: '导演',
    								    width: 100,
    								    align: 'center'
    								},{
    								    field: 'actorName',
    								    title: '主演',
    								    width: 100,
    								    align: 'center'
    								},{
    								    field: 'volumncount',
    								    title: '总集数',
    								    width: 100,
    								    align: 'center'
    								},
    								{
    								    field: 'currentnum',
    								    title: '更新至集数',
    								    width: 100,
    								    align: 'center'
    								},
    								{
    								    field: 'status',
    								    title: '状态',
    								    width: 100,
    								    align: 'center',
    								    hidden:true,
    								    formatter:function(value,row,index){
    								    	var str="";
    								    	if(value==1){
    								    		str="<span style='color:red'>已上线</span>";
    								    	}else{
    								    		str="<span style='color:green'>已下线</span>";
    								    	}
    								    	return str;
    								    }
    								   
    								},
    								{
    								    field: 'operate',
    								    title: '操作',
    								    width: 100,
    								    align: 'center',
    								    hidden:true,
    								    formatter: function(value,row,index){
    								    	var status=row.status;
    								    	var str="";
    								 	   if(status==1){
    								          str="<a href='javascript:void(0)' onclick='downLine("+row.id+")'>下线</a>";
    								        }else{
    								          str="<a href='javascript:void(0)' onclick='upLine("+row.id+")'>上线</a>";   
    								        }
    								 	   return str;
    								    }
    								},
    								{
    								    field: 'sequence',
    								    title: '排序',
    								    width: 100,
    								  
    								    align: 'center',
    								    editor:{
    		                    			type:"numberbox",
    		                    			options:{
    		                    				min:0,   
    		                    			    precision:0,
    		                    			    max:100
    		                    			}
    		                    		}
    								}
                               ]
                           ],
						onLoadSuccess: function(data, status, XHR) {
						},
						onLoadError: function(XHR, status, errorThrow){
						},names: [
						],subject: '绑定管理',
	  		              onClickRow:function(index,row){
	  	                  	$table.datagrid('unselectAll');
	  	                  	$table.datagrid('selectRow', index).datagrid('beginEdit', index);
	  	                  },
	  	                  isHeaderMenu:false
					});
				}else{
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
                $table = $("#vodlabel");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
            });
        	
        //绑定专辑
       	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
                  	title:"添加节目",
                  	text:"添加节目",
                  	iconCls: "icon-view", 
              		handler: function() {
              			$(parent).domain("openDialog",{
   	     	        	iconCls: "icon-view", 
   	     	        	title: "添加", 
   	     	      	 	src: "${pageContext.request.contextPath}/vod/phone/vodSpecial/addalbuminfo/0/",
   	     	        	width: 1250, 
   	    	        	height: 650,
   	     	        	onClose: function(){
   	     	        		var ids = top.$.data(top.document.body, "choose.ids");
   	     	        		$.ajax({
   	                           	url:"${pageContext.request.contextPath}/vod/phone/vodHotSearch/addAll",
   	                           	type:"GET",
   	                           	dataType:"JSON",
   	                           	data:{albumid:ids},
   	                           	async:false,
   	                           	success:function(){
   	                           	getvodactors();		
   	                           	},
   	                           	error:function(XHR, status, errorThrow){
   	                           		
   	                           	}
   	                           });
   	     	        		 getinfo();
   	     	            }
   	     	        });
                      },
                      scope:"one,more"
              	});
     	
        
       //删除
    	 var  sysBtn1=$.extend($.fn.domain.Btnsys1,{
                title:"删除",
               	text:"删除",
               	iconCls: "icon-remove", 
               	scope:"one,more",	
           		handler: function() {
           			$.messager.confirm('确认','确认删除？',function(r){
           				if(r){
           					var nodes =  $table.datagrid("getSelections");
    	           	    	if (!nodes || nodes.length == 0) {
    	           	            top.$.messager.alert("信息", "请您选择需要删除信息", "info", null, 2000);
    	           	            return;
    	           	        } 
    	           	    	var ids = "";
    	           	    	var names = [];
    	           	        for (var i = 0; i < nodes.length; i++) {
    	        	            ids+=","+nodes[i]["seriesCode"];
    	        	        }
    	   	        		$.ajax({
    	                         	url:"${pageContext.request.contextPath}/vod/phone/vodHotSearch/deletes",
    	                         	type:"GET",
    	                         	dataType:"JSON",
    	                      		data:{albumid:ids.substr(1,ids.length)},
    	                         	async:false,
    	                         	success:function(){
    	                         		getvodactors();           		
    	                         	},
    	                         	error:function(XHR, status, errorThrow){
    	                         	}
    	                         });
           				}
           			});
           		}
           	});
       
    	//保存绑定专辑排序
    	 var  sysBtn3=$.extend($.fn.domain.save,{
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
           	            top.$.messager.alert("信息", "请先编辑排序再保存", "info", null, 2000);
           	            return;
           	        } 
           	    	var ids = "";
           	    	var sequence = "";
           	        for (var i = 0; i < nodes.length; i++) {
        	            ids+=","+nodes[i]["seriesCode"];
        	            sequence+=","+nodes[i]["sequence"];
        	        }
	     	        		$.ajax({
	                           	url:"${pageContext.request.contextPath}/vod/phone/vodHotSearch/updateSequence",
	                           	type:"GET",
	                           	dataType:"JSON",
	                           	data:{albumid:ids.substr(1,ids.length),sequences:sequence.substr(1,sequence.length)},
	                           	async:false,
	                           	success:function(){
	                           	 top.$.messager.alert("信息", "排序保存成功！", "info", null, 2000);
	                           		$("#vodSpecial").datagrid("load",{});               		
	                           	},
	                           	error:function(XHR, status, errorThrow){
	                           	}
	                           });
	     	        		getvodactors();
	     	       
                   },
                   scope:"one,more"
           	});
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
                      	url:"${pageContext.request.contextPath}/integrate/outputjson/phone/phonehotsearchip",
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
