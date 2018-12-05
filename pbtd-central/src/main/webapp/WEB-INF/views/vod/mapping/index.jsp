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
            		<label class="">cp源：</label>
            		  <select   onchange="cpchoosechannel(0,1);" id="cpcode" name="cpcode" style="width: 90px;"  class="input text easyui-validatebox-disable">
							<option value="">--请选择--</option>
							<c:forEach items="${cpsource}" var="map">
								<option value="${map.ID }">${map.NAME }</option>
							</c:forEach>
						</select>
            	</span>	
            	<span>
            	<label class="">cp频道：</label>
            	  <select  id="cp_chnId" name="cp_chnId" style="width: 120px;" class="chzn-select">
				  </select>
            	</span>
            	<span class="property">
            		<label class="">频道：</label>
            		  <select onchange="choosechannellabel('phone','0');" id="phone_chnId" name="phone_chnId" style="width: 120px;" class="chzn-select">
					  </select>
            	</span>	
            	<span class="property">
            		<label class="">标签名称：</label>
            		<input type="text" id="name" name="name" style="width:200px;"/>
            	</span>	
            	
                <a href="javascript:getVodChannels()" id="btnSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false,disabled:false">查询</a>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table id="vodchannels"></table>
        </div>
        <script type="text/javascript" src="/js/common/scripts/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/js/common/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.min.js"></script>
        <script type="text/javascript" src="/js/common/scripts/jquery-domain.min.js"></script>
       
        
        <script type="text/javascript"> 
            function getVodChannels() {
            	  var f = $('#formQuery');
            	 var queryParams = f.domain('collect');
            	 if (f.form('validate') == false) {
             		return false;
             	}
                var t = $('#vodchannels');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	 //	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del,'-',saveBtn,'-',savestatusstartBtn,'-',savestatusendBtn];
                	var customToolbar = [$.fn.domain.create,'-',$.fn.domain.edit,'-',$.fn.domain.del];
              
                	t.domain('datagrid', {
                        title: '映射管理',
                    	url: '${pageContext.request.contextPath}/integrate/mapping/page',
                    	queryParams: queryParams,
                    	toolbar: customToolbar,
                        columns: [[
	                    	{field: 'id', title: ' ', width: 100, sortable: true, align: 'center', hidden: true}
	                    	,{field: 'bz', title: 'cp源', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'cp_chnId', title: 'cp频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'cp_tagId', title: 'cp标签', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'phone_chnId', title: '频道', width: 100, sortable: true, align: 'center', hidden: false}
	                    	,{field: 'phone_tagId', title: '标签', width: 60,  align: 'center', hidden: false}
	                      	,{field: 'status', title: '状态', width: 100, sortable: true, align: 'center', hidden: false,formatter: getBtn}
	                    ]],
                        onLoadSuccess: function(data, status, XHR) {
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                        },
                        names: [
         	                    ],
        	                    subject: '映射管理',
        	                    onClickRow:function(index,row){
        	                    	$table.datagrid('unselectAll');
        	                    	$table.datagrid('selectRow', index).datagrid('beginEdit', index);
        	                    },
        	                    isHeaderMenu:false
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
                $table = $("#vodchannels");
    		    $phone_chnId=$("#phone_chnId");
    		    $cp_chnId=$("#cp_chnId");
    		    $code="";
    		    cpchoosechannel(1,0);//获取cp频道
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getVodChannels();
                choosechannel("phone");//获取手机频道
            });
        	 function getBtn(value, row, index, text){
			     var ckButton='';
			     var status = row.status;
			     if(status=="1"){
			    	 ckButton = "<span style='color: green;'>上线</span>";
			     }else{
			    	 ckButton = "<span style='color: red;'>下线</span>";
			     }
			     return ckButton;
	   		}
        	 
        	//频道   
         	function choosechannel(type){
         	  var name="";
         		  name="choosechannelphone";
                    	$.ajax({
                        	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                        	type:"GET",
                        	dataType:"JSON",
                        	data:{levels:1},
                        	async:false,
                        	success:function(msg){
                        		var options = "<option  value=''>--请选择--</option>";
     		            		for(var i=0;i<msg.length;i++){
     		            			options+="<option  value="+msg[i].value+">"+msg[i].text+"</option>";
     		            		}
     		               		     $phone_chnId.html(options);
                        	},
                        	error:function(XHR, status, errorThrow){
                        		
                        	}
                        });
                    }
        	
         	  //选择cp频道
        	function cpchoosechannel(type,cptype){
        		var cpcode=$("#cpcode").val();
        		if(type>0){ cpcode=$code; }
        	    var name="";
        	  if(cpcode=='1'){
        		  name="choosechannelyh";
        	  }else if(cpcode=='2'){
        		  name="choosechannelgg";
        	  }else if(cpcode=='3'){
        		  
        	  }else if(cpcode=='4'){
        		  name="choosechannelguttv";
        	  }else if(cpcode=='5'){
        		  name="choosechannelyk";
        	  }
        	  else{  }
                   	$.ajax({
                       	url:"${pageContext.request.contextPath}/pbtd/dictionary/"+name,
                       	type:"GET",
                       	dataType:"JSON",
                       	data:{levels:1},
                       	async:false,
                       	success:function(msg){
                       		var options = "<option  value=''>--请选择--</option>";
    		            		for(var i=0;i<msg.length;i++){
    		            			options+='<option  value='+msg[i].value;
    		            				options	+=' selected="selected" ';
    		            			options+='>'+msg[i].text+'</option>';
    		            		}
    		            		
    		            		$cp_chnId.html(options);
                       	},
                       	error:function(XHR, status, errorThrow){
                       		
                       	}
                       });
                   }
        </script>
    </body>
</html>
