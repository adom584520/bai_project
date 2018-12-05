$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'版权控制列表',
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/copyright/list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ {
			field : 'ck',
			checkbox:true,
		},{
			field : 'id',
			title : 'id',
			hidden : true
		}, {
			field : 'cpCode',
			title : 'cpCode',
			width : 60,
			align : 'center',
		}, {
			field : 'cpName',
			title : 'cp名称',
			width : 120,
			halign : 'center',
		}, {
			field : 'type',
			title : '类型',
			width : 80,
			align : 'center',
			formatter:typeFormatter,
		}, {
			field : 'status',
			title : '状态',
			width : 80,
			align : 'center',
			formatter:statusFormatter,
		} ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#edit").linkbutton("enable");
	    	}else{
	    		$("#edit").linkbutton("disable");
	    	}
			$("#remove").linkbutton("enable");
		},
		onSelectAll:function(rows){
			$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("unselectAll");
		},
		onUnselectAll:function(index,row){
			$("#edit").linkbutton("disable");
			$("#remove").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#edit").linkbutton("enable");
			}else if(array.length<1){
				$("#edit").linkbutton("disable");
				$("#remove").linkbutton("disable");
			}
		},
	});
	$("#add").linkbutton({
		plain : false,
		iconCls : 'icon-add',
		onClick : function() {
			openAddOrEdit("add");
		}
	});
	$("#edit").linkbutton({
		plain : false,
		iconCls : 'icon-edit',
		disabled:true,
		onClick : function() {
			openAddOrEdit();
		}
	});
	$("#remove").linkbutton({
		plain : false,
		iconCls : 'icon-remove',
		disabled:true,
		onClick : function() {
			remove();
		}
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 300,
		height : 180,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#addOrEditDialogButtons',
		onClose:function(){
			$("#addOrEditFrom").form("clear");
		}
	});

	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#id").val();
			if (id) {
				addOrUpdate("/copyright/update", id);
			} else {
				addOrUpdate("/copyright/insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");
		}
	});
	$("#cpCode").combobox({
		required : true,
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'国广'},
		]
	});
	$("#type").combobox({
		required : true,
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'直播'},
		   {key:'2',value:'点播'},
		]
	});
	$("#status").combobox({
		required : true,
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'有版权'},
		   {key:'2',value:'无版权'},
		]
	});
});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}
function typeFormatter(value, row, index){
	if(value){
		if(value==1){
			return "直播";
		}else if(value==2){
			return "点播";
		}
	}
	return "";
}
function statusFormatter(value, row, index){
	if(value){
		if(value==1){
			return "有版权";
		}else if(value==2){
			return "无版权";
		}
	}
	return "";
}

function openAddOrEdit(index) {
	var addOrEditDialog = $('#addOrEditDialog');
	if (index) {
		$("#cpCode").combobox("select","1");
		$("#type").combobox("select","1");
		$("#status").combobox("select","1");
		addOrEditDialog.dialog("setTitle", "新增版权信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑版权信息");
		$("#id").val(row.id);
		$("#cpCode").combobox("select",row.cpCode);
		$("#type").combobox("select",row.type+'');
		$("#status").combobox("select",row.status+'');
	}
	addOrEditDialog.dialog("open").dialog("restore");
}

function addOrUpdate(url){
	$("#addOrEditFrom").form("submit", {
		url : url,
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					$("#addOrEditDialog").dialog("close");
					var datagrid = $('#datagrid');
					datagrid.datagrid("reload");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
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
				url = "/copyright/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/copyright/delete";
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
						});
					} else {
						$.messager.alert('提示信息', data.message);
					}
				}
			});
		}
	});
}
function validata() {
	if (!$("#addOrEditFrom").form("validate")) {
		$.messager
				.alert(
						'提示',
						"<p style='font-family:verdana;font-size:100%;color:green' align='center'>请按要求填写所有的内容！</p>");
		return false;
	}
	return true;
}
