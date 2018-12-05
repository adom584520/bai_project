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
        <script type="text/javascript" src="/js/common/my97datepicker/WdatePicker.js" defer="defer"></script>
        
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
         <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:90px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
				 <span class="property" > <label class="">查询起止时间：</label>
			  <input id="startTime" name="startTime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			  --
			<input id="endTime" name="endTime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			 </span>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getinfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            <hr>
            <span class="property" > <label class="">汇集日期：</label>
				<input id="downloadtime" name="downloadtime" type="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" style="width: 130px;">
			</span>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:insertInfo()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">开始汇集</a>
            </form>
                <label class="">&nbsp;&nbsp;&nbsp;&nbsp;  提示： 未输入开始日期；则默认查询前31天留存率；未输入结束日期；则查询开始日期后的所有留存</label>
			</span>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="tables"></table>
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
               var t = $('#tables');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                    var customToolbar = [$.fn.domain.del];
                    t.domain('datagrid', {
                        title: '用户留存率统计',
                        url: '${pageContext.request.contextPath}/user/getUserKeep',
                        queryParams: queryParams,
                        toolbar: customToolbar,
                        columns: [[
                            {field: 'id', title: '标识', width: 100,   align: 'center', hidden: true}
                            ,{field: 'startTime', title: '开始日期', width: 130,   align: 'center', formatter: formatDatebox}
                            ,{field: 'endTime', title: '结束日期', width: 130,   align: 'center', formatter: formatDatebox}
                            ,{field: 'keepUser', title: '留存用户数', width: 130,   align: 'center'}
                            ,{field: 'startUser', title: '开始日新增用户数', width: 130,   align: 'center'}
                            ,{field: 'endUser', title: '结束日活跃用户数', width: 130,   align: 'center'}
                            ,{field: 'keepPercent', title: '留存率（%）', width: 100,   align: 'center'}
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
                    t.datagrid("load",queryParams);
                }
            }

            function insertInfo(){
                if (confirm("将导入选中日期当天的用户统计数据")) {
                    var queryParams = $form.serializeArray();
                    if(queryParams[2].value  == '' || queryParams[2].value.length == 0){
                        top.$.messager.alert("信息", "请选择手动导入用户数据的开始时间", "info", null, 2000);
                        return false;
                    }else{
                        <%--var url = "${pageContext.request.contextPath}/user/insertUserKeep?endTime="+$form.serialize();--%>
                        var url = "${pageContext.request.contextPath}/user/insertUserKeep?endTime="+ $("#downloadtime").val();
                        //window.location.href = url;
                        $.ajax({
                            url:url,
                            type:"POST",
                            async:true,
                            dataType : "json",
                            data : {},
                            success:function(data) {
                                alert("导入成功");
                                getinfo();
                            },error:function(){
                                alert("导入成功");
                            }
                        });
                    }
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
                $table = $("#tables");
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getinfo();

            });

            Date.prototype.format = function (format) {
                var o = {
                    "M+": this.getMonth() + 1, // month
                    "d+": this.getDate(), // day
                    "h+": this.getHours(), // hour
                    "m+": this.getMinutes(), // minute
                    "s+": this.getSeconds(), // second
                    "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                    "S": this.getMilliseconds()
                    // millisecond
                }
                if (/(y+)/.test(format))
                    format = format.replace(RegExp.$1, (this.getFullYear() + "")
                        .substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(format))
                        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                return format;
            }

            function formatDatebox(value) {

                if (value == null || value == '') {
                    return '';
                }
                var dt;
                if (value instanceof Date) {
                    dt = value;
                } else {
                    dt = new Date(value);
                }

                return dt.format("yyyy-MM-dd");
            }

        </script>


    </body>
</html>
