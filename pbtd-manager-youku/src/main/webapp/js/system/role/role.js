$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'角色列表',
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/role/list',
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
			field : 'name',
			title : '角色名称',
			width : 60,
			align : 'center',
			fixed:false,
		}, {
			field : 'descriptor',
			title : '描述信息',
			width : 120,
			align : 'left',
			halign : 'center',
			formatter : descriptorFormatter
		}, {
			field : 'createDate',
			title : '创建时间',
			width : 80,
			align : 'center',
			formatter : dateFormatter
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
		width : 350,
		height : 350,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#addOrEditDialogButtons',
		onClose:function(){
			$("#addOrEditFrom").form("clear");
		}
	});

	$('#menuTree').tree({
		checkbox : true,
		animate : true,
		onLoadSuccess : function() {
			var roleId = $("#role_id").val();
			if (roleId) {
				$.ajax({
					url : "/menu/by_role_id",
					type : "post",
					data : "roleId=" + roleId,
					dataType : "json",
					success : function(data) {
						if(data.length>0){
							for (var i = 0; i < data.length; i++) {
								var node = $('#menuTree').tree('find', data[i]);
								$('#menuTree').tree('check', node.target);
							}
						}
							
					}
				});
			}
		}
	});
	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#role_id").val();
			if (id) {
				addOrUpdate("/role/update", id);
			} else {
				addOrUpdate("/role/insert");
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
	$("#name").textbox({
		required : true,
		validType:['length[1,10]'],
	});
	$("#descriptor").textbox({
		multiline:true,
		validType:['length[0,255]'],
		prompt: '请输入角色的相关信息',
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
	if ("add" == index) {
		addOrEditDialog.dialog("setTitle", "新增角色");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		if(!row){
			$.messager.alert('提示信息', "请先选中一条数据！");
			return;
		}
		addOrEditDialog.dialog("setTitle", "编辑角色");
		$("#role_id").val(row.id);
		$("#name").textbox("setValue",row.name);
		$("#descriptor").textbox("setValue",row.descriptor);
	}
	//获取所有菜单
	$('#menuTree').tree("options").url = '/menu/self';
	$('#menuTree').tree("reload");
	addOrEditDialog.dialog("open");
}

function addOrUpdate(url, id) {
	$("#addOrEditFrom").form("submit", {
		url : url,
		onSubmit : function(param) {
			var nodes = $('#menuTree').tree('getChecked',['checked','indeterminate']);
			for (var i = 0; i < nodes.length; i++) {
				param['menus[' + i + '].id'] = nodes[i].id;
			}
		},
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					// 保存后清空表单数据
					$('#addOrEditFrom').form("clear");
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
				url = "/role/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/role/delete";
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
function descriptorFormatter(value, row, index){
	if(value){
		return value;
	}
	return "无";
}