<!--
        * 欠费名单导入
        * 
        * @author 周三龙
        *
-->
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>导入</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/themes/default/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/themes/default/chosen.css" >
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/themes/default/bootstrapSwitch.css" >
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugins/AjaxFileUploaderV2.1/ajaxfileupload.css" >
    <jsp:include page="../../../common.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
	<script type="text/javascript">
		function showMask() {
	        $("#loading-mask").hide().show();
	        $("#loading").hide().show();
	    }
	
	    function hideMask() {
	        window.setTimeout(function () {
	            $("#loading-mask").fadeOut("fast", function () {
	            });
	            $("#loading").hide();
	        }, 400);
	    }
	    if (window.showMask) window.showMask();
	</script>
	<style type="text/css">
        	td.tdCaption{
                text-align: center;
            }
            td.tdCaption span{
                color: #ff0000;
            }
            select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input {
				display: inline-block;
				height: 20px;
				padding: 0px;
				margin-bottom: 2px;
				margin-top: 2px;
				font-size: 14px;
				line-height: 20px;
				color: #555555;
				-webkit-border-radius: 4px;
				-moz-border-radius: 4px;
				border-radius: 4px;
				vertical-align: middle;
			}
			a.l-btn span span.l-btn-text{
				width:35px;
			}
			
			.tdContent input{
				width:300px;
			}
			#btnDownload span .l-btn-text{
        		width:60px;
        	}
        </style>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
     	<div id="loading-mask"></div>
 		<div id="loading"><div class="loading-text"></div></div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <form id="sjk" style="margin:10px; padding:0;" method="post" autoTypeset="false" columnSize="2">
                <input type="hidden" id="id" name="id" />
				<table width="98%" cellspacing="1" cellpadding="3" class="tblMain" align="center">
				<tr>
					<td style="text-align: right; width: 15%"  class="tdCaption"  align="center">
            		EXCEL文件<span>*</span>
					</td>
					<td class="tdContent" >
	            		<input type="file" id="excel" name="excel" class="input number easyui-validatebox-disable" style="" />
	            		&nbsp;
	            		<a id="btnImport" href="javascript:Upload()" class="easyui-linkbutton" data-options="iconCls:'icon-importBtn',plain:false">导入</a>
	            	</td>
				</tr>
				<tr>
					<td style="text-align: left; width: 15%;color:red;" colspan="2"  class="tdContent" >
						说明：请输入正确数据(第一列：专辑id,第二列：更新状态,第三列：收费状态)
					</td>
				</tr>
				</table>
            </form>
        </div>

        <script type="text/javascript">
            $(function(){
            	//解析页面
                $.parser.parse();
                $('body').css({ visibility: 'visible' });
                var t = $('#sjk');
                if (window.hideMask) window.hideMask();
            });
            
                //文件上传
            function Upload() {
            	var excel = $("#excel").val();
            	if(excel.length==0){
            		$.messager.alert('提示', '请选择EXCEL文件！',"info");
            		return;
            	}
            	var end = excel.lastIndexOf(".");
       			var extend = excel.substring(end+1,excel.length);
       		    if(extend!="xls"){
       		    	$.messager.alert('提示', '请选择EXCEL文件，扩展名为xls！',"info");
            		return;
       		    }else{
		            $("#loading").ajaxStart(function () {
		    	        showMask();
				    }).ajaxComplete(function () {
				        hideMask();
				    });
					
            		$.ajaxFileUpload({
			            url: '${pageContext.request.contextPath}/vod/phone/vodalbuminfostatus/importExcel',
			            fileElementId: 'excel',
			            dataType: 'json',
			            data: {},
			            async: true,
	                    cache: false,
			            success: function (result,data, status){
			            	result=result.substr(1,result.length-2);
			            	if(result=="1"){
			            		$.messager.alert('提示', '导入成功！',"info",function(){
			                 		$(top).domain('closeDialog')
			            		});
			       
			            	}else{
			            		$.messager.alert('提示', result+'信息导入失败！',"info",function(){
			            		});
			            	}
			            },
			            error: function (data, status, e) {
			            	$.messager.alert('提示', '导入失败！',"info");
			            }		
	       			})
       		    }
            	
            	
            	
            }
    	</script>
	</body>
</html>
