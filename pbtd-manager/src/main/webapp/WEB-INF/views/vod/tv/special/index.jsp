<!--
        *  管理页面
        * 
        * @author zr
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>专题管理</title>
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
            <table id="vodSpecial"></table>
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
                var t = $('#vodSpecial');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtn,sysBtns5];
                	t.domain('datagrid', {
                        title: '专题管理',
                    	url: '${pageContext.request.contextPath}/vod/tv/vodSpecial/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'name', title: '名称', width: 100, align: 'center'}
	                    	,{field: 'id', title: '标识', width: 100, align: 'center', hidden: false}
	                    	,{field: 'imgurl', title: '图片', width: 100, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'backgroundimg', title: '背景图', width: 100, align: 'center', hidden: false,formatter: getbackgroundimg}
	                    	,{field: 'viewPoint', title: '看点', width: 100, align: 'center', hidden: false}
	                    	,{field: 'sequence', title: '排序', width: 100, align: 'center', hidden: false}
	                    	,{field: 'create_time', title: '创建时间', width: 100,sortable: true,  align: 'center', hidden: false,
			                    	formatter: function(value) { 
		                   			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                return datetime;     
								}}
	                    	,{field: 'update_time', title: '更新时间', width: 100, sortable: true,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
							}}
	                    	,{field: 'textcolor', title: '文字颜色', width: 100, align: 'center', hidden: false	,formatter: function(value) { 
								 if(value=="0"){
		                   				return "<span>黑色</span>";
		                   			 }else{
		                   				 return "<span>白色</span>";
		                   			 }
                           
						}}
							,{field: 'status', title: '状态', width: 100, align: 'center', hidden: false	,formatter: function(value) { 
								 if(value=="1"){
		                   				return "<span style='color:green'>上线</span>";
		                   			 }else{
		                   				 return "<span  style='color:red'>下线</span>";
		                   			 }
                             
						}}
							,{field: 'opt',  title: '操作', width: 160,  align: 'center', hidden: false,formatter: getBtn}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                     
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
                $table = $("#vodSpecial");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	 function  getimgurl(e){
     	    	return "<img  style='width:80px;height:40px;' onclick='showimg(\""+e+"\")' src=${maprealm.imgtitle}"+e+"  />";
     	    }
     	    function  getbackgroundimg(e){
     	    	return "<img  style='width:80px;height:40px;' onclick='showimg(\""+e+"\")' src=${maprealm.imgtitle}"+e+"  />";
     	    }
   	     function showimg(img){
  	    	$(parent).domain("openDialog", { 
  	        	iconCls: "icon-view", 
  	        	title: "图片查看", 
  	        	src: "${pageContext.request.contextPath}/uploadPic/showimg?img=${maprealm.imgtitle}"+img,
  	        	width: 650, 
 	        	height: 550,
  	        	onClose: function() { 
  	        		 
  	            }
  	        });
  	    }
        	  function getBtn(value, row, index, text){
	           		 var ckButton='';
	   		    	 ckButton = "<a href='#' onclick='show(\""+row.id+"\",\"imgurl\")' class='easyui-linkbutton'>图片上传</a>";
	   		    	 ckButton += "&nbsp;&nbsp;&nbsp;<a  href='#' onclick='show(\""+row.id+"\",\"backgroundimg\")' class='easyui-linkbutton'>背景图上传</a>";
	   		    	 return ckButton;
	   	   		}
     		 function show(id,imgtype){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "上传", 
	     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname=vodTvSpecial&id="+id+"&imgtype="+imgtype,
	     	        	width: 450, 
	    	        	height: 250,
	     	        	onClose: function() { 
	     	        		getinfo();
	     	            }
	     	        });
	         	}
     		 
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
         	function updateStatus(ids,status){
         		$.ajax({
                   	url:"${pageContext.request.contextPath}/vod/tv/vodSpecial/updateStatus",
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{status:status,id:ids},
                   	async:false,
                   	success:function(){
                   	 top.$.messager.alert("信息", "操作成功！", "info", null, 2000); 
                   		$("#vodSpecial").datagrid("load",{});               		
                   	},
                   	error:function(XHR, status, errorThrow){
                   		
                   	}
                   }); 
         	}
     		
         	//绑定专辑
       	 var  sysBtn=$.extend($.fn.domain.Btnsys,{
            	title:"绑定专辑",
           	text:"绑定专辑",
           	 iconCls:"icon-ok",
       		handler: function() {
       			var nodes =  $table.datagrid("getSelections");
       	    	if (!nodes || nodes.length == 0) {
       	            top.$.messager.alert("信息", "请您选择专题", "info", null, 2000);
       	            return;
       	        } 
       	    	if(nodes.length>1){
       	    		 top.$.messager.alert("信息", "请您选择一条专题信息", "info", null, 2000);
       	    		 return;
       	    	}
       	    	 
       	    	var  id= nodes[0]["id"];
       	    	$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "绑定", 
	     	        	src: "${pageContext.request.contextPath}/vod/tv/vodSpecial/albuminfo/"+id,
	     	        	width: 1250, 
	    	        	height: 650,
	     	        	onClose: function() {
	     	        	 	
	     	            }
	     	        });
               },
               scope:"one"
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
                     	url:"${pageContext.request.contextPath}/integrate/outputjson/tv/specialip",
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
     			for(var i=0;i<nodes.length;i++){
     				ids+=","+nodes[i]["id"];
     				status+=nodes[i]["status"];
     				if(status==1){
         				top.$.messager.alert("信息", "上线记录不允许删除", "info", null, 2000);
         				return;
         			}
     			}        			
  			  $.messager.confirm('确认','确认删除？',function(r){
			           	if(r==false){return false;}
			           	$.ajax({
		                  	url:"${pageContext.request.contextPath}/vod/tv/vodSpecial/deletes",
		                  	type:"GET",
		                  	dataType:"JSON",
		                  	data:{id:ids.substr(1,ids.length)},
		                 	async:false,
		                  	success:function(){
		                  		top.$.messager.alert("信息", "操作成功！", "info", null, 2000);   
		                  		getinfo();         		
		                  	},
		                  	error:function(XHR, status, errorThrow){
		                  		
		                  	}
		                  }); 
 			   })
     		},
     		scope:"one,more"
     	})
        </script>
    </body>
</html>
