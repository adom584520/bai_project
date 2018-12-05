$(function() {
	var datagrid = $('#datagrid');
	var macDialog = $("#macDialog");
	datagrid.datagrid({
		striped:true,
		title:"分组管理",
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/launcher_group/list',
		toolbar : [
		           {id:'add',text:'新增',iconCls:'icon-add',height:30,plain:true,handler:function () {
		        	   openAddOrEdit();
		           }},
		           {id:'remove',text:'删除',iconCls: 'icon-remove',plain:true,handler:function () {
		        	   remove();
		           }}
	           ],
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ {field:'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'groupId',title : '分组ID',hidden : true},
		              {field : 'groupName',title : '分组名称',width : 60,align : 'center'}, 
		              {field : 'contacts',title : '联系人',width : 60,align : 'center',formatter : easyFormatter}, 
		              {field : 'contactsNumber',title : '联系电话',width : 60,align : 'center',formatter : easyFormatter}, 
		              {field : 'address',title : '联系地址',width : 60,align : 'center',formatter : easyFormatter}, 
		              {field : 'loginInfoName',title : '创建账号名称',width : 60,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 80,align : 'center',formatter : dateFormatter}, 
		              {field : 'modifyTime',title : '修改时间',width : 80,align : 'center',formatter : dateFormatter},
		              {field : 'operation',title : '操作',width : 80,align : 'center',formatter : operationFormatter},
		          ] ],
		onSelect:function(index,row){
			$("div.datagrid-toolbar [id ='remove']").eq(0).show();
		},
		onSelectAll:function(rows){
			$("div.datagrid-toolbar [id ='remove']").eq(0).show();
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("unselectAll");
			$("[name='lookList']").linkbutton({
				plain : false,
				iconCls : 'icon-look',
			});
			 $("div.datagrid-toolbar [id ='add']").eq(0).show();
			 $("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselectAll:function(index,row){
			$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length<1){
				$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
			}
		},
	});
	$("#groupNameSearch").textbox({
		validType:['length[0,38]'],
		prompt:"请输入分组名称",
	});
	$("#macNameSearch").textbox({
		validType:['length[0,38]'],
		prompt:"请输入MAC名称",
	});
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var groupName=$("#groupNameSearch").textbox("getValue");
			var macName=$("#macNameSearch").textbox("getValue");
			var params = {};
			if(groupName){
				params.groupName=groupName;
			}
			if(macName){
				params.macName=macName;
			}
			datagrid.datagrid("load",params);
			datagrid.datagrid("unselectAll");
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load",{});
			$("#groupNameSearch").textbox("setValue","");
			$("#macNameSearch").textbox("setValue","");
		}
	});
	
	macDialog.dialog({
		width:700,
		height:500,
		modal : true,
		closable : true,
		closed:true,
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
});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}

function openAddOrEdit(index) {
	var macDialog=$("#macDialog");
	if(index||index>-1){
		var datagrid = $('#datagrid');
		datagrid.datagrid("unselectAll");
		datagrid.datagrid("selectRow",index);
		var row = datagrid.datagrid("getSelected");
		macDialog.dialog({title:"分组与MAC查询(编辑)"});
		macDialog.dialog({content:"<iframe src='/mac/page?groupId="+row.id+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
	}else{
		macDialog.dialog({title:"分组与MAC添加"});
		macDialog.dialog({content:"<iframe src='/mac/page?groupId=-1' style='width:100%;height:100%;' frameborder=0></iframe>"});
	}
	macDialog.dialog("open");
}

function remove() {
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			var array = datagrid.datagrid("getSelections");
			var url;
			var data={};
			if(array.length>1){
				var ids = [];
				url = "/launcher_group/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/launcher_group/delete";
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
						'温馨提示',
						"<p style='font-family:verdana;font-size:100%;color:green' align='center'>未按要求填写，不能提交哟！</p>");
		return false;
	}
	return true;
}
function easyFormatter(value, row, index){
	if(value){
		return value;
	}
	return "无";
}
function operationFormatter(value, row, index){
	return "<a name='lookList' onClick='openAddOrEdit("+index+")'>查看</a>";
}
