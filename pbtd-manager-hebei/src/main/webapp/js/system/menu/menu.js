$(function() {
	var treegrid = $('#treegrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	treegrid.treegrid({
		title:'菜单列表',
		rownumbers:true,
		idField : 'id',
		treeField:'text',
		fit : true,
		url : '/menu/list',
		toolbar : [
		           {id:'add',text:'新增',iconCls:'icon-add',height:30,plain:true,handler:function () {
		        	   openAddOrEdit("add");
		           }},
		           {id:'edit',text:'编辑',iconCls: 'icon-edit',plain:true,handler:function () {
		        	   openAddOrEdit();
		           }},
		           {id:'remove',text:'删除',iconCls: 'icon-remove',plain:true,handler:function () {
		        	   remove();
		           }},
	           ],
		singleSelect : true,
		resizable : false,
		fitColumns : true,
		columns:[[
	        {field:'id',title:'id',hidden:true},    
	        {field:'parent',title:'父节点',hidden:true},    
	        {field:'text',title:'菜单名称',width:150,align:'left',halign:'center'},    
	        {field:'url',title:'菜单页面地址',width:100,align:'center',halign:'center'},  
	        {field:'state',title:'是否自动展开',width:50,align:'center',halign:'center',formatter:isOpenAndFold},    
	        {field:'permission',title:'权限表达式',width:100,align:'center',halign:'center'},
	        {field:'sort',title:'菜单排序',width:30,align:'center',halign:'center'},
	    ]],
	    onSelect:function(index,row){
	    	var array = treegrid.treegrid("getSelections");
			if(array.length==1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).show();
			}else{
				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
			}
			$("div.datagrid-toolbar [id ='remove']").eq(0).show();
		},
		onLoadSuccess:function(data){
			treegrid.treegrid("unselectAll");
			$("div.datagrid-toolbar [id ='add']").eq(0).show();
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
			$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselectAll:function(index,row){
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
			$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselect:function(index,row){
			var array = treegrid.treegrid("getSelections");
			if(array.length==1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).show();
			}else if(array.length<1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
				$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
			}
		},
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 450,
		height : 330,
		closed : true,
		modal : true,
		closable : true,
		buttons : [
		           {text:'保存',iconCls:'icon-save',plain:true,handler:function () {
		        	   save();
		           }},
		           {text:'取消',iconCls: 'icon-cancel',plain:true,handler:function () {
		        	   $("#addOrEditDialog").dialog("close");
		           }}
	           ],
		onClose: function(){
			$("#addOrEditFrom").form("clear");
			$("#parentId").combotree("clear");
		}
	});
	$('#parentId').combotree({
		height:25,
		width:200,
		valueField: "id",
		textField: "text",
		onLoadSuccess:function(){
        	var parentId=$("#parentId_hidden").val();
        	if(parentId){
        		$("#parentId").combotree("setValue",parentId);
        	}
       },
	}); 
	$("#state").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		data:[
		   {key:'open',value:'展开'},
		   {key:'closed',value:'折叠'}
		]
	});
	$("#checked").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		data:[
		   {key:'true',value:"选中"},
		   {key:'false',value:"不选中"}
		]
	}); 
	$('#text').textbox({
	    required: true,
	    validType:['length[1,10]'],
	}); 
	$('#permission').textbox({
		required: true,
		validType:['length[1,100]'],
	}); 
	$('#url').textbox({
		validType:['length[0,100]'],
	}); 
	$('#iconCls').combobox({
		required: true,
		editable:false,
		panelHeight:"auto",
		valueField:'key',
		textField:'value',
		data:[
		   {key:'icon-prent-menu',value:"菜单图标-图样:&nbsp&nbsp<img src='/js/common/jquery-easyui-1.5.2//themes/icons/prent_menu.png'>"},
		   {key:'icon-submenu',value:"子菜单图标-图样:&nbsp&nbsp<img src='/js/common/jquery-easyui-1.5.2//themes/icons/submenu.png'>"}
		]
	}); 
	$("#sort").numberbox({
		required: true, 
		min:1,
		max:999,
	});
	
});

function openAddOrEdit(value) {
	var addOrEditDialog = $('#addOrEditDialog');
	var state;
	var checked;
	if ("add" == value) {
		addOrEditDialog.dialog("setTitle", "新增菜单");
		state = 'open';
		checked = 'false';
	} else {
		var treegrid = $('#treegrid');
		var row = treegrid.treegrid("getSelected");
		if(!row){
			$.messager.alert('提示信息', "请先选中一条数据！");
			return;
		}
		addOrEditDialog.dialog("setTitle", "编辑菜单");
		$("#menu_id").val(row.id);
		$("#text").textbox("setValue",row.text);
		if(row.url){
			$("#url").textbox("setValue",row.url);
		}
		$("#permission").textbox("setValue",row.permission);
		$("#iconCls").textbox("setValue",row.iconCls);
		$("#sort").numberbox("setValue",row.sort);
		state = row.state;
		checked = row.checked;
		$("#parentId_hidden").val(row.parentId);
	}
	$("#state").combobox("select",state+"");
	$("#checked").combobox("select",checked+"");
	$("#parentId").combotree("reload","/menu/self");
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
					var treegrid = $('#treegrid');
					treegrid.treegrid("reload");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
function remove(index) {
	var treegrid = $('#treegrid');
	var row = treegrid.treegrid("getSelected");
	if(!row){
		$.messager.alert('提示信息', "请先选中一条数据！");
		return;
	}
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(yes) {
		if (yes) {
			$.ajax({
				url : "/menu/delete",
				type : "post",
				data : "id=" + row.id,
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示信息', data.message, "", function() {
							treegrid.treegrid("reload");
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
		$.messager.alert('提示',"<p style='font-family:verdana;font-size:100%;color:green' align='center'>请按要求填写所有的内容！</p>");
		return false;
	}
	return true;
}
function isOpenAndFold(value,row,index){
	if(value=='open'){
		return "菜单展开";
	}
	return "菜单折叠";
}
function ispitch(value,row,index){
	if(value){
		return "选中";
	}
	return "不选中";
}
function save(){
	if (!validata()) {
		return;
	}
	var id = $("#menu_id").val();
	if (id) {
		addOrUpdate("/menu/update");
	} else {
		addOrUpdate("/menu/insert");
	}
}
