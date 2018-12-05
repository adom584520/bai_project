$(function() {
	var groupId=$("#groupId").val();
	var datagrid = $('#datagrid');
	var macAddOrEditDialog = $('#macAddOrEditDialog');
	var macAOrEditFrom = $('#macAOrEditFrom');
	datagrid.datagrid({
		title:'MAC列表',
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/mac/list?groupId='+groupId,
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ {field:'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'macName',title : 'MAC名称',width : 60,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 80,align : 'center',formatter : dateFormatter}, 
		              {field : 'loginInfoName',title : '创建账号名称',width : 60,align : 'center'},
		          ] ],
		onSelect:function(index,row){
			$("#remove").linkbutton("enable");
		},
		onSelectAll:function(rows){
			$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
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
		onBeforeLoad:function(){
			var groupId=$("#groupId").val();
			if(groupId){
				return true;
			}
			return false;
		}
	});
	$("#macNameSearch").textbox({
		validType:['length[0,38]'],
		prompt:"请输入MAC名称",
	});
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var val=notSaveWarn("groupId");
			if(!val){
				return;
			}
			var groupId=$("#groupId").val();
			var macName=$("#macNameSearch").textbox("getValue");
			var params = {};
			if(macName){
				params.macName=macName;
			}
			datagrid.datagrid("load",params);
			datagrid.datagrid("unselectAll");
		}
	});
	$("#groupDialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata("groupAddOrEditFrom")) {
				return;
			}
			var id = $("#groupId").val();
			if (id) {
				groupAddOrUpdate("/launcher_group/update");
			} else {
				groupAddOrUpdate("/launcher_group/insert");
			}
		}
	});
	$("#groupDialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			parent.$('#macDialog').dialog("close");
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			var val=notSaveWarn("groupId");
			if(!val){
				return;
			}
			datagrid.datagrid("load",{});
			$("#groupNameSearch").textbox("setValue","");
			$("#macNameSearch").textbox("setValue","");
		}
	});
	$("#add").linkbutton({
		plain : false,
		iconCls : 'icon-add',
		onClick : function() {
			var val=notSaveWarn("groupId");
			if(!val){
				return;
			}
			openMacAddOrEdit();
		}
	});
	$("#remove").linkbutton({
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			var val=notSaveWarn("groupId");
			if(!val){
				return;
			}
			remove();
		}
	});

	macAddOrEditDialog.dialog({
		title:'新增MAC',
		align : 'center',
		width : 300,
		height : 140,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#macAddOrEditDialogButtons',
		onClose:function(){
			$("#mac_name").textbox("clear");
		}
	});
	
	$("#macDialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata("groupAddOrEditFrom")) {
				return;
			}
			macAddOrUpdate("/mac/insert");
		}
	});
	$("#macDialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#macAddOrEditDialog").dialog("close");
		}
	});
	
	
	$("#mac_name").textbox({
		required : true,
		validType:['length[1,38]','macValidata'],
	});
	
	$("#group_name").textbox({
		required : true,
		validType:['length[1,38]'],
	});
	
	$("#contacts").textbox({
		validType:['length[0,38]'],
	});
	
	$("#contacts_number").textbox({
		validType:['length[0,38]'],
	});
	
	$("#address").textbox({
		validType:['length[0,255]'],
	});
	
	$("#macBatchImportDialog").dialog({
		title:'批量导入MAC',
		align : 'center',
		width : 300,
		height : 140,
		closed : true,
		modal : true,
		closable : true,
		buttons : 'macBatchImportDialogButtons',
		onClose:function(){
			$("#fileName").filebox("clear");
		}
	});
	$('#fileName').filebox({
		editable:false,
		type:'text',
		buttonText: '选择文件',
		buttonAlign:'right',
		accept:'.xlsx,.xls',
		multiple:false,
	})
	$("#batchImport").linkbutton({
		iconCls : 'icon-redo',
		plain : false,
		onClick : function() {
			var val=notSaveWarn("groupId");
			if(!val){
				return;
			}
			$("#macBatchImportDialog").dialog("open");
		}
	});
	$("#batchExport").linkbutton({
		iconCls : 'icon-undo',
		plain : false,
	});
	$("#macBatchImportUpload").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			macBatchImportFrom();
		}
	});
	$("#macBatchImportClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#macBatchImportDialog").dialog("close");
		}
	});
	if(!groupId){
		$("#batchExport").linkbutton("disable");
	}else{
		$("#batchExport").prop("href","/mac/batch_export?groupId="+groupId);
	}
});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}

function openMacAddOrEdit() {
	$('#macAddOrEditDialog').dialog("open");
}

function groupAddOrUpdate(url) {
	$("#groupAddOrEditFrom").form("submit", {
		url : url,
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				var groupId=$("#groupId").val();
				if(groupId){
					$.messager.alert('提示信息', data.message, "", function() {
						parent.$("#macDialog").dialog("close");
						var datagrid =parent.$('#datagrid');
						datagrid.datagrid("reload");
					});
				}else{
					var data2 = data.data;
					$("#macGroupId").val(data2.id);
					$("#groupId").val(data2.id);
					$("#group_name").textbox("setValue",data2.groupName);
					$("#contacts").textbox("setValue",data2.contacts);
					$("#contacts_number").textbox("setValue",data2.contactsNumber);
					$("#address").textbox("setValue",data2.address);
					$.messager.alert('提示信息', data.message);
				}
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}

function macAddOrUpdate(url) {
	$("#macAOrEditFrom").form("submit", {
		url : url,
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					$("#macAddOrEditDialog").dialog("close");
					var val=$("#groupId").val();
					var datagrid =$('#datagrid');
					datagrid.datagrid({url:"/mac/list?groupId="+val});
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
				url = "/mac/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/mac/delete";
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
function validata(id) {
	if (!$("#"+id).form("validate")) {
		$.messager
				.alert(
						'提示',
						"<p style='font-family:verdana;font-size:100%;color:green' align='center'>未按要求填写，不能提交哟！</p>");
		return false;
	}
	return true;
}
//判断groupid是否有值，没有则表示新增，所有对mac的操作都不能使用
function notSaveWarn(id){
	var val=$("#"+id).val();
	if(val){
		return true;
	}
	$.messager.alert('提示信息', "请先保存分组信息，再进行此操作！");
	return false;
}
function macBatchImportFrom(){
	$("#macBatchImportFrom").form("submit", {
		url : '/mac/batch_import',
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					$("#macBatchImportDialog").dialog("close");
					var val=$("#groupId").val();
					var datagrid =$('#datagrid');
					datagrid.datagrid({url:"/mac/list?groupId="+val});
					datagrid.datagrid("reload");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
function macValidata(mac){
	var reg_name=/[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}/;
	if(!reg_name.test(mac)){
		return
	}
}
$.extend($.fn.validatebox.defaults.rules, {    
	macValidata: {    
        validator: function(value, param){  
        	var reg_name=/[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}/;
        	return reg_name.test(value);
        },    
        message: 'MAC格式不正确！'   
    }    
}); 
