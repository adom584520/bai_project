<!--
        *  管理页面
        * 
        * @author JOJO
        *
-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>手机推荐轮播图管理</title>
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
      <div data-options="region:'north',border:false,split:true" style="padding:0px; border-bottom:1px solid #99BBE8; height:75px; width:auto;">
         	<form id="formQuery" style="margin:0; padding:0" action="" method="post"> 
				<span class="property"> 
						<label class="">状态：</label>
					<select id="status" name="status"  style="width: 80px;" class="status-select">
						<option value="">--请选择--</option>
						<option value="0">已下线</option>
						<option value="1">已上线</option>
				</select>
				</span>
				<span class="property"> <label class="">专辑id：</label>
					<input id="target_code" name="targetCode"  style="width: 80px;" type="text" />
				 </span>
				<a href="javascript:getvodactors()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
               <br/><hr/>
            <span class="property"> <label class="">下发时间：</label>
					 <input id="curtime" name="curtime" type="text" class="easyui-datebox"  >
			 </span>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="recommandPic"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/common.js"></script>
        
        <script type="text/javascript">
        	function formatterImage(value,row,index){
				if (value) {
					value = '<img style="width:30px; height:30px" src="${maprealm.imgtitle}'+ value + '">';
					return value;
				}
        	}
        	function formatterType(value,row,index){
        		console.log(value);
        		if (value==1) {
					return "点播";
				}else if(value==2){
					return "直播";
				}else if(value==3){
					return "webUrl";
				}else if(value==4){
					return "视频";
				}
        	}
        	function dateFormatter(value, row, index) {
        		if (value) {
        			return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
        		}
        		return "";
        	}
        	function formatterStatus(value,row,index){
        		if (value==0) {
					return "<span  style='color:red'>下线</span>";
				}else if(value==1){
					return "<span style='color:green'>上线</span>";
				}else{
					return "<span  style='color:red'>下线</span>";
				}
        	}
        	function formatterMade(value,row,index){
        		return "<a href='javascript:upload("+row.id+")'>上传图片</a>";
        	}
            function getvodactors() {
				var f = $('#formQuery');
				var queryParams = f.domain('collect');
				if (f.form('validate') == false) {
					return false;
				}
                var t = $('#recommandPic');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,sysBtnup,'-',sysBtnno,'-',sysBtns5,'-'];
                	t.domain('datagrid', {
                        title: '专区推荐轮播图管理',
                    	url: '${pageContext.request.contextPath}/vod/phone/slideshow/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
										{field: 'id', title: 'id', width: 100,align: 'center', hidden: true},
										{field: 'name', title: '名称', width: 100,align: 'center', hidden: false},
										{field: 'type', title: '类型', width: 60,align: 'center', hidden: false,formatter:formatterType},
										{field: 'imageUrl', title: '图片Url', width: 120,align: 'center', hidden: false,formatter:formatterImage},
										{field: 'targetCode', title: '专辑id', width: 80,align: 'center', hidden: false},
										{field: 'playurl', title: '播放地址', width: 120,align: 'center', hidden: false},
										{field: 'weburl', title: '跳转地址', width: 120,align: 'center', hidden: false},
										{field: 'sequence', title: '排序', width: 40,align: 'center', hidden: false},
										{field: 'content_point', title: '内容看点', width: 40,align: 'center', hidden: false},
										{field: 'status', title: '状态', width: 60,align: 'center', hidden: false,formatter:formatterStatus},
										{field: 'updateUser', title: '修改用户', width: 60,align: 'center', hidden: false},
										{field: 'updateTime', title: '修改时间', width: 120,align: 'center', hidden: false,formatter:dateFormatter},
										{field: 'made', title: '操作图片', width: 100,align: 'center',value:'', hidden: false,formatter:formatterMade}
								]],
						subject:'专区推荐轮播图'
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
                $t = $("#recommandPic");
               //加载数据
                if($.fn.domain.defaults.datagrid.auto) getvodactors();
            });
        	function upload(id){
        		var picname="slideshow";
         		$(parent).domain("openDialog", { 
     	        	iconCls: "icon-view", 
     	        	title: "上传", 
     	        	src: "${pageContext.request.contextPath}/uploadPic/updateimg?picname="+picname+"&id="+id,
     	        	width: 450, 
    	        	height: 250,
    	        	success:function(data){
    	        		$("#recommandPic").datagrid("load",{});         
    	        	},
     	        	onClose: function() { 
     	        		$("#recommandPic").datagrid("load",{});         
     	            }
     	        });
         	}      		
    	
        	//批量上线
        	var sysBtnup=$.extend($.fn.domain.btnup,{
        		title:'上线',
        		text:'上线',
        		iconCls:"icon-ok",
        		handler:function(){
        			var nodes=$t.datagrid("getSelections");
        			if(!nodes||nodes.length==0){
        				top.$.messager.alert("信息", "请您选择需要上线的记录", "info", null, 2000);
        				return;
        			}
        			var arrt = [];
        			for(var i=0;i<nodes.length;i++){
        				arrt[i] = nodes[i]["id"];
        			}
        			updateStatus(JSON.stringify(arrt),1)
        		},
        		scope:"one,more"
        	})
        	//批量下线
        	var sysBtnno=$.extend($.fn.domain.btnno,{
        		title:'下线',
        		text:'下线',
        		iconCls:"icon-no",
        		handler:function(){
        			var nodes=$t.datagrid("getSelections");
        			if(!nodes||nodes.length==0){
        				tob.$.messager.alert("信息","请您选择需要上线的记录","info",null,2000);
        				return;
        			}
        			var arrt = [];
        			for(var i=0;i<nodes.length;i++){
        				arrt[i] = nodes[i]["id"];
        			}
        			updateStatus(JSON.stringify(arrt),0)
        		},
        		scope:"one,more"
        	})
        	function updateStatus(ids,status){
        		$.ajax({
                  	url:"${pageContext.request.contextPath}/vod/phone/slideshow/update_status",
                  	type:"GET",
                  	dataType:"JSON",
                  	data:{status:status,ids:ids},
                  	async:false,
                  	success:function(data){
                  		top.$.messager.alert("信息", data.message, "info", null, 2000); 
                  		$("#recommandPic").datagrid("load",{});               		
                  	},
                  }); 
        	}
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
                        	url:"${pageContext.request.contextPath}/integrate/outputjson/phone/phoneslideshowip",
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
