$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	var urlSetValue = "";
	datagrid.datagrid({
		title:'日志列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/sys_logger/list',
		toolbar : '#toolbar',
		checkOnSelect : false,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ 
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'loginInfoName',title : '操作账号',width : 50,align : 'center'},
		              {field : 'operationInfo',title : '操作信息',width : 160,align : 'center'},
		              {field : 'clientIp',title : '登录IP',width : 80,align : 'center',formatter:showOptimize},
		              {field : 'startTime',title : '请求时间',width : 100,align : 'center',formatter:timeFormatter}, 
		              {field : 'returnTime',title : '响应时间',width : 100,align : 'center',formatter:timeFormatter}, 
		              {field : 'consumingTime',title : '请求耗时',width : 40,align : 'center',formatter:showConsumingTime}, 
		              {field : 'operation',title : '操作',width : 50,align : 'center',formatter : operationFormatter},
		          ] ],
		onSelect:function(index,row){
			$("#remove").linkbutton("enable");
		},
		onSelectAll:function(rows){
			$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			$("[name=lookInfo]").linkbutton({
				plain : false,
			});
			datagrid.datagrid("unselectAll");
		},
		onUnselectAll:function(index,row){
			$("#remove").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length<1){
				$("#remove").linkbutton("disable");
			}
		},
	});
	
	$("#loginInfoNameSearch").textbox({    
	    validType:['length[0,20]'],
	});
	$("#operationInfoSearch").textbox({
		validType:['length[0,30]'],
	});
	$("#startTimeSearch").datebox({
		editable:false,
	});
	$("#endTimeSearch").datebox({
		editable:false,
	});
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var loginInfoName=$("#loginInfoNameSearch").textbox("getValue");
			var operationInfo=$("#operationInfoSearch").textbox("getValue");
			var startTime=$("#startTimeSearch").datebox("getValue");
			var endTime=$("#endTimeSearch").datebox("getValue");
			var params = {};
			if(loginInfoName){
				params.loginInfoName=loginInfoName;
			}
			if(operationInfo){
				params.operationInfo=operationInfo;
			}
			if(startTime){
				params.startTime=startTime;
			}
			if(endTime){
				params.endTime=endTime;
			}
			datagrid.datagrid("load",params);
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			$("#loginInfoNameSearch").textbox("setValue","");
			$("#operationInfoSearch").textbox("setValue","");
			$("#startTimeSearch").datebox("clear");
			$("#endTimeSearch").datebox("clear");
			datagrid.datagrid("load",{});
		}
	});
	
	$("#remove").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			remove();
		}
	});
	$("#conditionRemove").linkbutton({
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			conditionRemoveOpen();
		}
	});
	$("#conditionRemoveDialogSave").linkbutton({
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			conditionRemove();
		}
	});
	$("#conditionRemoveDialogClose").linkbutton({
		plain : false,
		iconCls : 'icon-cancel',
		onClick : function() {
			$("#conditionRemoveDialog").dialog("close");
		}
	});
	$("#conditionRemoveDialog").dialog({
		title:'按条件删除日志',
		align : 'center',
		width : 450,
		height : 120,
		buttons:'#addOrEditDialogButtons',
		closed : true,
		modal : true,
		closable : true,
	});
	$("#startTime").datebox({
		prompt:"请输入开始删除的日期",
	});
	$("#endTime").datebox({
		prompt:"请输入结束删除的日期",
	});
	$("#infoDialog").dialog({
		title:'日志详细信息',
		align : 'center',
		width : 500,
		height : 300,
		closed : true,                                                                                                                                                                                                                                                              
		modal : true,
		closable : true,
	});
	
	$('#infoPropertygrid').propertygrid({ 
	    showGroup: false,    
	    scrollbarSize: 0,
	    fit : true,
	    columns:[[
	              {field:'name',title:'参数名称',align : 'center',width:60,sortable:false},
	              {field:'value',title:'参数值',align : 'center',width:200,sortable:false} 
	           ]],
	});  
});

function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm:ss", new Date(value));
	}
	return "";
}
function timeFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm:ss", new Date(parseInt(value)));
	}
	return "";
}
function remove(index) {
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			var array = datagrid.datagrid("getSelections");
			var url;
			var data={};
			if(array.length>1){
				var ids = [];
				url = "/sys_logger/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/sys_logger/delete";
				data.id = array[0].id;
			}
			$.ajax({
				url : url,
				type : "post",
				data : data,
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示信息', data.message, "", function() {
							var datagrid = $('#datagrid');
							datagrid.datagrid("reload");
							datagrid.datagrid("unselectAll");
						});
					} else {
						$.messager.alert('提示信息', data.message);
					}
				}
			});
		}
	});
}
function conditionRemove(){
	var startTime=$("#startTime").datebox("getValue");
	var endTime=$("#endTime").datebox("getValue");
	var params = {};
	if((!startTime)&(!startTime)){
		$.messager.alert('提示信息', "请输入删除条件！");
		return;
	}
	if(startTime){
		params.startTime=startTime;
	}
	if(endTime){
		params.endTime=endTime;
	}
	$.ajax({
		url : "/sys_logger/condition_delete",
		type : "post",
		data : params,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					var datagrid = $('#datagrid');
					datagrid.datagrid("reload");
					datagrid.datagrid("unselectAll");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
function conditionRemoveOpen(){
	$("#conditionRemoveDialog").dialog("open");
}
function showOptimize(value,row,index){
	if(value){
		return value;
	}
	return "无";
}
function showConsumingTime(value,row,index){
	return value/1000+"秒";
}
function operationFormatter(value,row,index){
	return "<a name='lookInfo' onClick='openInfoDialog("+index+")'>详细信息</a>";
}
function openInfoDialog(index){
	var datagrid = $('#datagrid');
	datagrid.datagrid("uncheckAll");
	datagrid.datagrid("selectRow",index);
	var row = datagrid.datagrid("getSelected");
	var createTime=customDateFormatter("yyyy-MM-dd hh:mm:ss", new Date(parseInt(row.createTime)));
	var data = [    
                  {"name":"登录IP","value":row.clientIp,"group":"日志详细数据","editor":"text"},    
                  {"name":"请求接口路径","value":row.url,"group":"日志详细数据","editor":"text"},    
                  {"name":"请求类型","value":row.type,"group":"日志详细数据","editor":"text"},    
                  {"name":"请求方式","value":row.method,"group":"日志详细数据","editor":"text"},  
                  {"name":"请求参数","value":row.paramData,"group":"日志详细数据","editor":"text"},  
                  {"name":"响应参数","value":row.returnData,"group":"日志详细数据","editor":"text"},  
                  {"name":"状态码","value":row.httpStatusCode,"group":"日志详细数据","editor":"text"},  
                  {"name":"日志创建时间","value":createTime,"group":"日志详细数据","editor":"text"},  
               ];
	$('#infoPropertygrid').propertygrid('loadData',data); 
	$("#infoDialog").dialog("open");
}
