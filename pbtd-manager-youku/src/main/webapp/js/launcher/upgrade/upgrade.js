$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	var urlSetValue = "";
	datagrid.datagrid({
		title : 'APK版本列表',
		striped : true,
		rownumbers : true,
		idField : 'id',
		fit : true,
		url : '/upgrade/list',
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
			checkbox : true,
		}, {
			field : 'id',
			title : 'id',
			hidden : true
		}, {
			field : 'gradeName',
			title : '版本名称',
			hidden : true
		}, {
			field : 'download',
			title : '下载链接',
			width : 60,
			align : 'center'
		}, {
			field : 'type',
			title : 'APK类型',
			width : 60,
			align : 'center',
			formatter : showType
		}, {
			field : 'status',
			title : '版本状态',
			width : 60,
			align : 'center',
			formatter : showStatus
		}, {
			field : 'versionInfo',
			title : '版本信息描述',
			width : 60,
			align : 'center'
		}, {
			field : 'version',
			title : '版本号',
			width : 80,
			align : 'center'
		}, {
			field : 'logininfoName',
			title : '创建人',
			width : 80,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 80,
			align : 'center',
			formatter : dateFormatter
		}, {
			field : 'modifyTime',
			title : '修改时间',
			width : 80,
			align : 'center',
			formatter : dateFormatter
		}, ] ],
		onSelect : function(index, row) {
			var array = datagrid.datagrid("getSelections");
			if (array.length == 1) {
				$("#edit").linkbutton("enable");
				$("#remove").linkbutton("enable");
			} else {
				$("#remove").linkbutton("disable");
				$("#edit").linkbutton("disable");
			}
		},
		onLoadSuccess : function(data) {
			datagrid.datagrid("unselectAll");
		},
		onUnselectAll : function(index, row) {
			$("#edit").linkbutton("disable");
			$("#remove").linkbutton("disable");
		},
		onUnselect : function(index, row) {
			var array = datagrid.datagrid("getSelections");
			if (array.length == 1) {
				$("#edit").linkbutton("enable");
				$("#edit").linkbutton("enable");
			} else if (array.length < 1) {
				$("#edit").linkbutton("disable");
				$("#remove").linkbutton("disable");
			}
		},
	});
	$("#typeSearch").combobox({
		editable : false,
		panelHeight : "auto",
		valueField : 'key',
		textField : 'value',
		data : [ {
			key : '1',
			value : 'Android'
		}, {
			key : '2',
			value : 'iOS'
		}, {
			key : '3',
			value : 'TV-直播'
		}, {
			key : '4',
			value : 'TV-Launcher'
		}, {
			key : '5',
			value : 'TV-点播'
		}, {
			key : '6',
			value : '党建'
		}, ]
	});

	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var type = $("#typeSearch").combobox("getValue");
			// 查询所有的类型下的升级信息
			var params = {
				status : -1
			};
			if (type) {
				params.type = type;
			}
			datagrid.datagrid("load", params);
			datagrid.datagrid("unselectAll");
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load", {});
			$("#typeSearch").combobox("unselect");
			$("#typeSearch").combobox("setValue", "");
		}
	});

	$("#add").linkbutton({
		plain : false,
		iconCls : 'icon-add',
		onClick : function() {
			openAddOrEdit("add");
		}
	});

	$("#edit").linkbutton({
		disabled : true,
		plain : false,
		iconCls : 'icon-edit',
		onClick : function() {
			openAddOrEdit();
		}
	});
	$("#remove").linkbutton({
		disabled : true,
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			remove();
		}
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 500,
		height : 250,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#addOrEditDialogButtons',
		onClose : function() {
			addOrEditFrom.form("clear");
		}
	});

	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : true,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#id").val();
			if (id) {
				addOrUpdate("/upgrade/update");
			} else {
				addOrUpdate("/upgrade/insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : true,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");
		}
	});

	$("#type").combobox({
		editable : false,
		required : true,
		panelHeight : "auto",
		valueField : 'key',
		textField : 'value',
		value : '1',
		data : [ {
			key : '1',
			value : 'Android'
		}, {
			key : '2',
			value : 'iOS'
		}, {
			key : '3',
			value : 'TV-直播'
		}, {
			key : '4',
			value : 'TV-Launcher'
		}, {
			key : '5',
			value : 'TV-点播'
		}, {
			key : '6',
			value : '党建'
		}, ]
	});

	$("#gradeName").textbox({
		required : true,
	});
	$("#download").textbox({
		required : true,
	});

	$("#versionInfo").textbox({
		validType : [ 'length[0,255]' ],
	});
	$("#version").textbox({
		required : true,
		validType : [ 'length[0,255]' ],
	});
});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}

function openAddOrEdit(index) {
	var addOrEditDialog = $('#addOrEditDialog');
	if (index) {
		$("#id").val("");
		addOrEditDialog.dialog("setTitle", "新增APK升级信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑APK升级信息");
		$("#id").val(row.id);
		$("#gradeName").textbox("setValue", row.gradeName);
		$("#download").textbox("setValue", row.download);
		$("#version").textbox("setValue", row.version);
		$("#type").combobox("select", row.type + '');
		$("#versionInfo").textbox("setValue", row.versionInfo);
	}
	addOrEditDialog.dialog("open");
}

function addOrUpdate(url) {
	$("#addOrEditFrom").form("submit", {
		url : url,
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					$("#addOrEditDialog").dialog("close");
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
function remove(index) {
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			var array = datagrid.datagrid("getSelections");
			var id = array[0].id;
			var data = {
				id : id
			};
			$.ajax({
				url : "/upgrade/delete",
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
function validata() {
	if (!$("#addOrEditFrom").form("validate")) {
		$.messager
				.alert(
						'温馨提示',
						"<p style='font-family:verdana;font-size:100%;color:green' align='center'>未按要求填写，不能提交哟！</p>");
		return false;
	}
	return true;
}
function showStatus(value, row, index) {
	if (value) {
		if (value == 1) {
			return "最新版本";
		} else if (value == 2) {
			return "旧版本";
		} else {
			return value;
		}
	}
	return "无";
}
function showType(value, row, index) {
	if (value) {
		if (value == 1) {
			return "Android";
		} else if (value == 2) {
			return "iOS";
		} else if (value == 3) {
			return "TV-直播";
		} else if (value == 4) {
			return "TV-Launcher";
		} else if (value == 5) {
			return "TV-点播";
		} else if (value == 6) {
			return "党建";
		} else {
			return value;
		}
	}
	return "无";
}