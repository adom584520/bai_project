<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
         <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
          <link rel="stylesheet" type="text/css" href="/js/common//plugins/AjaxFileUploaderV2.1/ajaxfileupload.css" >
          <script type="text/javascript" src="/js/common//AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
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
						说明：请输入正确数据(第一列：标识id,第二列：播控状态,第三列：类别,第四列：专辑id,第五列：剧集id)
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
			            url: '${pageContext.request.contextPath}/vod/phone/album/playpolicy/importExcel',
			            fileElementId: 'excel',
			            dataType: 'json',
			            data: {},
			            async: true,
	                    cache: false,
			            success: function (result,data, status){
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
