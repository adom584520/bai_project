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
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodinfo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [/* $.fn.domain.create, */'-',$.fn.domain.edit,'-',sysBtndel,'-',];
                	t.domain('datagrid', {
                        title: '模板管理',
                    	url: '${pageContext.request.contextPath}/vod/Masterplate/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
								{field: 'id', title: '标识', width: 100,  align: 'center', hidden: true}
								,{field: 'masterplatename', title: '名称', width: 100,   align: 'center', hidden: false}
								,{field: 'masterplatephoto', title: '图片', width: 100,   align: 'center', hidden: false,formatter: getimgurl}
								,{field: 'masterplatenum', title: '运营位个数', width: 100, sortable: true, align: 'center', hidden: false}
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
            function getBtn(value, row, index, text){
          		 var ckButton='';
  		    	 ckButton = "<a href='#' onclick='show(\""+row.id+"\",\"imgurl\")' class='easyui-linkbutton'>图片上传</a>";
  		    	 return ckButton;
  	   		}
            //图片上传
            function show(id,imgtype){
         		$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "上传", 
     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname=vodMasterplate&id="+id,
     	        	width: 450, 
    	        	height: 250,
     	        	onClose: function() { 
     	        		getinfo();
     	            }
     	        });
         	}
            
            
            function  getimgurl(e){
     	    	return "<img  style='width:80px;height:40px;'  onclick='showimg(\""+e+"\")'    src=${maprealm.imgtitle}"+e+"  />";
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
            $(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $form = $("#formQuery");
                $table = $("#vodinfo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
              
            });
        	
        	 

       	 //同步
	          	var sysBtnreset=$.extend($.fn.domain.reset,{
	          		title:'同步',
	          		text:'同步',
	          		iconCls:"icon-reset",
	          		handler:function(){
	          			$.ajax({
	                        	url:"${pageContext.request.contextPath}/tvreset/resetlabel",
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
	        			var ids="";
	        			for(var i=0;i<nodes.length;i++){
	        				ids+=","+nodes[i]["id"];
	        			}        			
	     			  $.messager.confirm('确认','请确认数据是否已同步，删除后数据无法更新线上已有数据，请核对后谨慎删除，确认删除？',function(r){
				           	if(r==false){return false;}
				           	$.ajax({
			                  	url:"${pageContext.request.contextPath}/vod/Masterplate/deletes",
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
