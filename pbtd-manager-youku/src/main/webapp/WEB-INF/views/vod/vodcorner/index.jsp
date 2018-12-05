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
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodCorner"></table>
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
                var t = $('#vodCorner');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtnreset];
                	t.domain('datagrid', {
                        title: '角标管理',
                    	url: '${pageContext.request.contextPath}/vod/vodCorner/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'name', title: '名称', width: 100, sortable: true, align: 'center'}
	                    	,{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'imgurl', title: '图片', width: 100, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'create_time', title: '创建时间', width: 100, sortable: true, align: 'center', hidden: false,
			                    	formatter: function(value) { 
		                   			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                return datetime;     
								}}
	                    	,{field: 'pos', title: '角标位置', width: 100, sortable: true, align: 'center', hidden: false,
		                    	formatter: function(value) { 
		                    		var pos =""; 
	                   			  if(value=="1"){pos="上左 "}
	                   			else if(value=="2"){pos="上右  "}
	                   			else if(value=="3"){pos="中左 "}
	                   			else if(value=="4"){pos="中右 "}
	                   			else if(value=="5"){pos="下左 "}
	                   			else if(value=="6"){pos="下右 "}
	                   			else{
	                   				
	                   			}
	                   			 return pos;
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
                $table = $("#vodCorner");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });

        	
       	 function  getimgurl(e){
    	    	return "<img  style='width:80px;height:40px;'  src=${maprealm.imgtitle}"+e+"  />";
    	    }
       	  function getBtn(value, row, index, text){
	           		 var ckButton='';
	   		    	 ckButton = "<a href='#' onclick='show(\""+row.id+"\",\"imgurl\")' class='easyui-linkbutton'>图片上传</a>";
	   		    	 return ckButton;
	   	   		}
    		 function show(id,imgtype){
	         		$(parent).domain("openDialog", { 
	     	        	iconCls: "icon-view", 
	     	        	title: "上传", 
	     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname=vodCorner&id="+id+"&imgtype="+imgtype,
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
         				top.$.messager.alert("信息", "请您选择需要下线的记录", "info", null, 2000);
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
                   	url:"${pageContext.request.contextPath}/vod/vodCorner/updateStatus",
                   	type:"GET",
                   	dataType:"JSON",
                   	data:{status:status,id:ids},
                   	async:false,
                   	success:function(){
                   		$("#vodCorner").datagrid("load",{});               		
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
                       	url:"${pageContext.request.contextPath}/commonreset/resetcorner",
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
