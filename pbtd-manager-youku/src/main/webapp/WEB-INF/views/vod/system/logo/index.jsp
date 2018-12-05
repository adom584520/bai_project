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
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodlogo"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
            function getinfo() {
                var t = $('#vodlogo');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	//var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',sysBtnreset];
                	var customToolbar = [ $.fn.domain.edit,'-',sysBtnreset];
                	t.domain('datagrid', {
                        title: 'logo管理',
                    	url: '${pageContext.request.contextPath}/vod/system/vodlogo/page',
                    	toolbar: customToolbar,
                        columns: [[
	                    	 {field: 'name', title: '名称', width: 100, sortable: true, align: 'center'}
	                    	,{field: 'id', title: '标识', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'logo', title: 'LOGO', width: 100, sortable: true, align: 'center', hidden: false,formatter: getimgurl}
	                    	,{field: 'made', title: '操作', width: 100, sortable: true, align: 'center', hidden: false,formatter:getBtn}
	                    	,{field: 'instruction', title: '说明', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'create_user', title: '创建人', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'create_time', title: '创建时间', width: 100, sortable: true, align: 'center', hidden: false,
			                    	formatter: function(value) { 
		                   			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
		                                return datetime;     
								}}
	                    	,{field: 'modify_user', title: '更新人', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'modify_time', title: '更新时间', width: 100,  align: 'center', hidden: false,
	                    		formatter: function(value) { 
	                    			 var datetime =Common.formatterDate(value,'yyyy-MM-dd');
	                                 return datetime;     
							}
	                    	}
	                   ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
         	                    subject:'专题信息'
        	                     
                    });
                }
                else {
                    t.datagrid("load",{});
                }
            }
        	
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $table = $("#vodlogo");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();
	            //上传图片
	         	 $("#logopic").linkbutton('enable')[0].onclick=function(){upload(id);};
            });
        	
        	 function  getimgurl(e){
     	    	return "<img  style='width:60px;height:40px;'  src=${maprealm.imgtitle}"+e+"  />";
     	    }
        	 function getBtn(value, row, index, text){
        		 var btn=""
        		 btn="<button  onclick='upload(\""+row.id+"\")' class='easyui-linkbutton'>上传</button>";
        		 return btn;
        	 }
        	 //上传图片
        		function upload(id){
        	        		var picname="vodlogopic";
        	         		$(parent).domain("openDialog", { 
        	     	        	iconCls: "icon-view", 
        	     	        	title: "上传", 
        	     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
        	     	        	width: 450, 
        	    	        	height: 250,
        	    	        	success:function(data){
        	    	        		$("#vodlogo").datagrid("load",{});         
        	    	        	},
        	     	        	onClose: function() { 
        	     	        		$("#vodlogo").datagrid("load",{});         
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
                           	url:"${pageContext.request.contextPath}/phonereset/resetlogo",
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
