$(function() {
	var datagrid = $('#datagrid');
	datagrid.datagrid({
		title:'已方专辑数据列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/channel/list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : false,
		singleSelect:true,
		columns : [ [ {field : 'ck',checkbox:true,},
		              {field : 'oldChnCode',title : '来源唯一标识',width : 150,align : 'center'},
		              {field : 'chnCode',title : '频道唯一标识',width : 150,align : 'center'},
		              {field : 'chnName',title : '频道名称',width : 200,align : 'center'}, 
		              {field : 'playUrl',title : '播放地址',width : 300,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 200,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 200,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#confirm").linkbutton("enable");
	    	}else{
	    		$("#confirm").linkbutton("disable");
	    	}
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
		},
		onUnselectAll:function(index,row){
			$("#confirm").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#confirm").linkbutton("enable");
			}else if(array.length<1){
				$("#confirm").linkbutton("disable");
			}
		},
	});
	
	$("#chnCodeSearch").textbox({
		validType:['length[0,20]'],
	});
	$("#chnNameSearch").textbox({
		validType:['length[0,20]'],
	});
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var params = {};
			var chnCode=$("#chnCodeSearch").textbox("getValue");
			if(chnCode){
				params.chnCode=chnCode;
			}
			var chnName=$("#chnNameSearch").textbox("getValue");
			if(chnName){
				params.chnName=chnName;
			}
			datagrid.datagrid("load",params);
		}
	});
	$("#confirm").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			confirm();
		}
	});
	$("#cancel").linkbutton({
		plain : false,
		iconCls : 'icon-cancel',
		onClick : function() {
			parent.$("#addOrEditDialog").dialog("close");
		}
	});
})

function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}
function confirm(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	var cpChannelId=$("#cpChannelId").val();
	var data = {};
	data.chnCode=row.chnCode;
	data.chnName=row.chnName;
	data.id=cpChannelId;
	var url = "/cpChannel/bindingChannel"; 
	$.ajax({
		url : url,
		type : "post",
		data : data,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					parent.$('#addOrEditDialog').dialog("close");
					parent.$('#datagrid').datagrid("reload");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
