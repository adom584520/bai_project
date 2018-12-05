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
        width:40px;
        }
        </style>
    </head>
    <body   style="visibility:visible;width: 98%">
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:74px; width:auto;">
         	<form id="formQuery" style="margin:38px; padding:0;" action="" method="post" > 
             
			 <span class="property"> <label class="">接口：</label>
					<select id="status" name="status"  style="width: 180px;"  url="data/city_data.json"   class="easyui-combotree" >
					</select>
			 </span>
                <a href="#" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:false,disabled:false">删除</a>
            </form>
        </div>
          <div style="height: 79px;background-color:#FF0F5 ">
                 <span style="margin-left: 34px;">1.你的世界我来过</span> 
                  <br/>
                  <span style="margin-left: 34px;">2.从你的世界走过</span> 
            </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
          <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript"> 
         
        	
        	$(function(){
        		//解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
               
              
            });
        	
        	 
	 	  		 
        </script>
    </body>
</html>
