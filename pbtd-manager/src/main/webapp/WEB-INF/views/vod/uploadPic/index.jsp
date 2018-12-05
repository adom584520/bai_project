<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
        *  新建页面
        * 
        * @author admin
        *
-->
<!DOCTYPE html>
<html>
<head>
<title>编辑 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10" /> 
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrap.css"></link>
<link rel="stylesheet" type="text/css" href="/js/common/themes/default/bootstrapSwitch.css"></link>
  		<link rel="stylesheet" type="text/css" href="/js/common/themes/default/base.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/js/common/jquery-easyui-1.3.1/themes/icon.css">
     
    	<script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
</head>
<body>

<div data-options="region:'center',border:false" style="padding:0px;">
    <form id="form"  enctype="multipart/form-data"    style="margin:30px; padding:0;" method="post" columnSize="2">
	<input type="hidden" id="id" name="id" />
			<table align="center">
				<tr>
				<td>
		上传图片
				 	<input id="file" name="file" type="file" style="width:200px">
				 	<a id="uploadbutton" href="javascript:startUpload()" class="easyui-linkbutton">上传</a>
				</td>			
			</tr>
			<tr>
				<td>
					<label style="color: red">只支持png、jpg、gif格式,大小不超过10MB</label>
				</td>
			</tr>
			</table>
    </form>
</div>
<script type="text/javascript">
	//页面初始化
   	$(function(){
	    	//解析页面
	        $.parser.parse();	
	    	$('body').css({ visibility: 'visible' });  
  	});  	
   	function startUpload() {
   		var picname ="${picname}";
   		var imgtype ="${imgtype}";
   		var id = "${id}";
   		$("#form").form('submit',{
   			url : "/fileupload/image",
   			onSubmit:function(){
   				var name= $("#file").val();
   				if(name!=''){
   				    var suffix=name.substring(name.lastIndexOf("."));
   					if (suffix != ".jpg" && suffix != ".JPG" && suffix != ".png"
   							&& suffix != ".PNG" 	&& suffix !=".gif" && suffix !=".GIF") {
   						alert("只支持jpg(JPG)/png(PNG)/gif(GIF)上传", "文件限制");
   						return false;
   					}
   				}else{
   					alert('请选择文件!');
   					return false;
   				}
   			},
   			success :function(data) {
   				data = $.parseJSON(data);
   				if (data.success) {
   					var url="";
   					var imgUrl=data.message ;
   					if (picname=="recommandPic") {
   						  url="${pageContext.request.contextPath}/vod/system/recommandpic/recommandPic_updateImg";
   					}else if(picname=="bottomnavigation_imgNor"){
   	   				       url="${pageContext.request.contextPath}/vod/system/bottomnavigation/updateImgNor";
   					}else if(picname=="bottomnavigation_imgSelect"){
   						url="${pageContext.request.contextPath}/vod/system/bottomnavigation/updateImgSelect";
   					}else if (picname=="vodactors"){
   					       url= '${pageContext.request.contextPath}/vod/vodactors/editimg/'+id; 
   					}else if (picname=="vodSpecial"){
   					       url= '${pageContext.request.contextPath}/vod/phone/vodSpecial/editimg/'+id; 
   					}else if(picname=="vodCorner"){
   					       url= '${pageContext.request.contextPath}/vod/vodCorner/editimg/'+id; 
   					}else if(picname=="vodTvSpecial"){
   						   url= '${pageContext.request.contextPath}/vod/tv/vodSpecial/editimg/'+id; 
   					}else if(picname=="phonealbumpic"){
						   url='${pageContext.request.contextPath}/vod/phone/vodalbuminfo/updatepic';
   					}else if(picname=="phonevideopic"){
						   url='${pageContext.request.contextPath}/vod/phone/vodalbuminfo/updatevideopic';
   					}else if(picname=="tvalbumpic1"){
						   url='${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updatepic/1';
					}else if(picname=="tvalbumpic2"){
						   url='${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updatepic/2';
					}else if(picname=="tvalbumpic3"){
						   url='${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updatepic/3';
					}else if(picname=="tvalbumpic4"){
						   url='${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updatepic/4';
					}else if(picname=="tvvideopic"){
						   url='${pageContext.request.contextPath}/vod/tv/vodalbuminfo/updatevideopic';
					}else if(picname=="slideshow"){
						   url='${pageContext.request.contextPath}/vod/phone/slideshow/update_iamge_url';
					}else if(picname=="livecover"){
						   url='${pageContext.request.contextPath}/live/Channel/editimg/'+id;
					}else if(picname=="startslideshow"){
						   url='${pageContext.request.contextPath}/vod/phone/startslideshow/update_iamge_url';
					}else if(picname=="vodphonechannelimg"){
						url = '${pageContext.request.contextPath}/vod/phone/Vodchannel/editimg';
					}else if(picname=="vodtvchannelimg"){
						url = '${pageContext.request.contextPath}/vod/tv/Vodchannel/editimg';
					}else if(picname=="vodphonelabelimg"){
						url = '${pageContext.request.contextPath}/vod/phone/Vodlabel/editimg';
					}else if(picname=="textrecommendationimg"){
						url = '${pageContext.request.contextPath}/vod/system/textrecommendation/editimg';
					}
   					addOrUpdate(url,id,imgUrl,imgtype);
   				} else {
   					$.messager.alert('提示信息', data.message);
   				}
   			}
   		});   		
   	}
   	
   	function addOrUpdate(url,id,imgUrl,imgtype) {
   		$.ajax({
			 url :url,
             type:"post",
            dataType:"json", 
            data:{id:id,imgUrl:imgUrl,imgtype:imgtype},
            success:function(data, status, XHR){  
            	 $.messager.alert("操作提示", "上传成功！", "info", function () {
            		 $(parent).domain('closeDialog');    
                 });
            	   	
            },
            error:function(){ 
            }
        }); 
   	};
</script>
</body>
</html>