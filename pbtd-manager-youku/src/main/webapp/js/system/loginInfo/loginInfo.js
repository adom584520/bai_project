$(function(){
	var datagrid = $('#datagrid');
	var allRole=$("#allRole");
	var selfRole=$("#selfRole");
	var addOrEditFrom=$("#addOrEditFrom");
	datagrid.datagrid({
		title:'账号列表',
		rownumbers:true,
		idField:'id',
		fit:true,
	    url:'/loginInfo/list',
	    toolbar:'#toolbar',
	    checkOnSelect:true,
	    pagination:true,
	    pagePosition:'bottom',
	    pageSize:10,
	    pageList:[10,20,30,40,50],
	    resizable:false,
	    fitColumns:true,
	    columns:[[
	        {field:'ck',checkbox:true,},
	        {field:'id',title:'id',hidden:true},    
	        {field:'username',title:'账号名',width:80,align:'center',halign:'center'},    
	        {field:'realName',title:'员工名字',width:60,align:'center',halign:'center'},  
	        {field:'contactInformation',title:'联系方式',width:80,align:'center',halign:'center',formatter:contactInformationFormatter},  
	        {field:'level',title:'账号级别',width:60,align:'center',halign:'center',formatter:levelFormatter}, 
	        {field:'status',title:'账号状态',width:50,align:'center',halign:'center',formatter:statusFormatter},    
	        {field:'createLoginInfo',title:'创建账号名称',width:80,align:'center',halign:'center'},  
	        {field:'createDate',title:'创建时间',width:80,align:'center',halign:'center',formatter:dateFormatter},  
	    ]],
	    onSelect:function(index,row){
	    	var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#edit").linkbutton("enable");
	    		$("#privileges").linkbutton("enable");
	    		$("#resetPassword").linkbutton("enable");
	    	}else{
	    		$("#edit").linkbutton("disable");
	    		$("#privileges").linkbutton("disable");
	    		$("#resetPassword").linkbutton("disable");
	    	}
			$("#remove").linkbutton("enable");
		},
		onSelectAll:function(rows){
			$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			$("[name=loginInfo_disable]").linkbutton({
				plain : false,
				iconCls : 'icon-switch',
			});
			datagrid.datagrid("unselectAll");
		},
		onUnselectAll:function(index,row){
			$("#remove").linkbutton("disable");
			$("#edit").linkbutton("disable");
			$("#privileges").linkbutton("disable");
			$("#resetPassword").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length<1){
				$("#remove").linkbutton("disable");
				$("#edit").linkbutton("disable");
				$("#privileges").linkbutton("disable");
				$("#resetPassword").linkbutton("disable");
			}else if(array.length==1){
				$("#edit").linkbutton("enable");
				$("#privileges").linkbutton("enable");
				$("#resetPassword").linkbutton("enable");
			}
		},
	});
	$('#addOrEditDialog').dialog({
	    align:'center',
	    width: 300,
		height: 180,    
		closed: true,    
	    modal: true,
	    closable:true,
	    buttons:'#addOrEditDialogButtons',
	    onClose:function(){
			$("#addOrEditFrom").form("clear");
		}
	});
	$('#assignDialog').dialog({
		title:'分配角色',
	    align:'center',
	    width: 500,
		height: 300,
		closed: true,    
	    modal: true,
	    closable:true,
	    buttons:'#assignDialogButtons',
		onClose:function(){
			document.getElementById("allRole").innerHTML="";
			document.getElementById("selfRole").innerHTML="";
		}
	});
	
	$("#privileges").linkbutton({
		plain : false,
		iconCls : 'icon-assign-role',
		disabled:true,
		onClick:function(){
			assignRole();
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
	$("#resetPassword").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		disabled:true,
		onClick : function() {
			resetPassword();
		}
	});
	$("#addOrEditDialogSave").linkbutton({    
	    iconCls: 'icon-save',
	    plain:false,
	    onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#loginInfo_id").val();
			if (id) {
				addOrUpdate("/loginInfo/update");
			} else {
				addOrUpdate("/loginInfo/insert");
			}
		}
	}); 
	$("#addOrEditDialogClose").linkbutton({    
		iconCls: 'icon-cancel',
		plain:false,
		onClick:function(){
			$("#addOrEditDialog").dialog("close");
		}
	});
	
	$("#assignDialogSave").linkbutton({    
	    iconCls: 'icon-save',
	    plain:false,
	    onClick:function(){
	    	$("#assignDialogFrom").form("submit",{
	    		url:"/loginInfo/insert_role",  
	    		onSubmit: function(param){
	    			var options=$("#selfRole option");
	    			for (var i = 0; i < options.length; i++) {
	    				param['list[' + i + ']'] = $(options[i]).val();
	    			}
	    		},    
	    		success:function(data){
	    			data =$.parseJSON(data);
	    			if(data.success){
	    				$.messager.alert('提示信息',data.message,"",function(){
	    					//保存后清空表单数据
	    					$("#assignDialog").dialog("close");
	    				});
	    			}else{
	    				$.messager.alert('提示信息',data.message);  
	    			}
	    		}  
	    	});
	    }
	}); 
	$("#assignDialogClose").linkbutton({    
		iconCls: 'icon-cancel',
		plain:false,
		onClick:function(){
			$("#assignDialog").dialog("close");
		}
	});
	
	$("#username").textbox({    
	    required: true,
	    validType:['length[1,8]'],
	}); 
	$("#realName").textbox({   
		required: true, 
		validType:['length[2,6]'],
	});
	$("#contactInformation").textbox({
		validType:['length[0,38]'],
	});
});
function dateFormatter(value,row,index){
    if (value){
        return customDateFormatter("yyyy-MM-dd hh:mm",new Date(value));
    }
    return "";
}
function statusFormatter(value,row,index){
	if(value==1){
		return "<font style='color:green'>正常</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='loginInfo_disable' onclick='javascript:loginInfoDisable("+row.id+","+"2"+")' class='easyui-linkbutton'>禁用</a>";
	}
	return "<font style='color:red'>禁用</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='loginInfo_disable' onclick='javascript:loginInfoDisable("+row.id+","+"1"+")' class='easyui-linkbutton'>激活</a>";
}
function levelFormatter(value,row,index){
	if(value==1){
		return "超级管理员";
	}
	return "普通管理员";
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
		$("#loginInfo_id").val(row.id);
		$("#username").textbox("setValue",row.username);
		$("#realName").textbox("setValue",row.realName);
		$("#contactInformation").textbox("setValue",row.contactInformation);
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
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
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
				url = "/loginInfo/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/loginInfo/delete";
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
function loginInfoDisable(id,status){
	var datagrid = $('#datagrid');
	$.ajax({
		url:"/loginInfo/update_status",
		type:"POST",
		data:{id:id,status:status},
		dataType:"json",
		success: function(data){
			if(data.success){
				datagrid.datagrid('reload');
			}else{
				$.messager.alert('提示信息',data.message);
			}
		},
	});
}
function assignRole(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	if(row){
		$("#assignLoginInfoId").val(row.id);
		getAllRole();
		getSelfRole();
		$("#assignDialog").dialog("open");
	}else{
		$.messager.alert('提示信息', "请先选中一条数据！");
	}
}
function getAllRole(){
	var allRole=$("#allRole");
	$.ajax({
		url: "/role/lack",
		type:"POST",
		data:'loginInfoId='+$("#assignLoginInfoId").val(),
		dataType:"json",
		success: function(data){
			for (var i = 0; i < data.length; i++) {
				var role = data[i];
				$("#allRole").append("<option value="+role.id+">"+role.name+"</option>");
			}
		},
	});
}
function getSelfRole(){
	var selfRole=$("#selfRole");
	$.ajax({
		url: "/role/query_role_byL_logininfo_id",
		type:"POST",
		data:{loginInfoId:$("#assignLoginInfoId").val()},
		dataType:"json",
		success: function(data){
			for (var i = 0; i < data.length; i++) {
				var role = data[i];
				$("#selfRole").append("<option value="+role.id+">"+role.name+"</option>");
			}
		},
	});
}
function exchangeRole(source,target,isAll){
	var options;
	if(isAll){
		options=$("#"+source+" option");
	}else{
		options=$("#"+source+" option:selected");
	}
	for (var i = 0; i < options.length; i++) {
		$(options[i]).appendTo($("#"+target));
	}
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
function contactInformationFormatter(value,row,index){
	if(value){
		return value;
	}
	return "无";
}
function resetPassword(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	$.messager.confirm('确认', '您确认想要重置该用户的密码？', function(yes) {
		if (yes) {
			$.ajax({
				url: "/loginInfo/reset_password",
				type:"POST",
				data:{loginInfoId:row.id},
				dataType:"json",
				success: function(data){
					$.messager.alert('提示信息',data.message);
				},
			});
		}
	});
}
