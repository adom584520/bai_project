$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	$("#cpCodeSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'国广'},
		]
	});
	datagrid.datagrid({
		title:'cp源关联数据列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/albuminfoJoin/list?cpCode='+$("#cpCodeSearch").combobox("getValue"),
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : false,
		columns : [ [ {field : 'ck',checkbox:true,},
		              {field : 'seriesCode',title : '节目code',width : 120,align : 'center'},
		              {field : 'seriesName',title : '节目名称',width : 150,align : 'center'},
		              {field : 'cpSeriesCode',title : 'cp节目code',width : 120,align : 'center'}, 
		              {field : 'cpSeriesName',title : 'cp节目名称',width : 150,align : 'center'}, 
		              {field : 'status',title : '是否确认关联',width : 200,align : 'center',formatter:statusFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#edit").linkbutton("enable");
	    		$("#manual").linkbutton("enable");
	    	}else{
	    		$("#edit").linkbutton("disable");
	    		$("#manual").linkbutton("disable");
	    	}
	    	$("#confirmed").linkbutton("enable");
	    	$("#unconfirmed").linkbutton("enable");
	    	$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
			$("[name=confirmedJoin]").linkbutton({
				plain : false,
				iconCls : 'icon-ok',
			});
		},
		onUnselectAll:function(index,row){
			$("#edit").linkbutton("disable");
			$("#confirmed").linkbutton("disable");
			$("#unconfirmed").linkbutton("disable");
			$("#remove").linkbutton("disable");
			$("#manual").linkbutton("disable");
			
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#edit").linkbutton("enable");
				$("#confirmed").linkbutton("enable");
				$("#unconfirmed").linkbutton("enable");
				$("#remove").linkbutton("enable");
				$("#manual").linkbutton("enable");
			}else if(array.length<1){
				$("#edit").linkbutton("disable");
				$("#confirmed").linkbutton("disable");
				$("#unconfirmed").linkbutton("disable");
				$("#remove").linkbutton("disable");
				$("#manual").linkbutton("disable");
			}
		},
	});
	
	$("#seriesCodeSearch").textbox({
		validType:['length[0,32]'],
	});
	$("#seriesNameSearch").textbox({
		validType:['length[0,1024]'],
	});
	$("#statusSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		data:[
		   {key:'-1',value:'全部'},
		   {key:'1',value:'已确认'},
		   {key:'0',value:'未确认'},
		]
	});
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var params = {};
			var seriesName=$("#seriesNameSearch").textbox("getValue");
			if(seriesName){
				params.seriesName=seriesName;
			}
			var seriesCode=$("#seriesCodeSearch").textbox("getValue");
			if(seriesCode){
				params.seriesCode=seriesCode;
			}
			var status=$("#statusSearch").textbox("getValue");
			if(status){
				params.status=status;
			}
			datagrid.datagrid("load",params);
		}
	});
	
	$("#confirmed").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			confirmed();
		}
	});
	$("#unconfirmed").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			unconfirmed();
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
	
	$("#manual").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			var addOrEditDialog=$("#addOrEditDialog");
			var datagrid = $('#datagrid');
			var row = datagrid.datagrid("getSelected");
			var cpCode = $("#cpCodeSearch").combobox("getValue");
			addOrEditDialog.dialog({title:"已方专辑数据列表"});
			addOrEditDialog.dialog({content:"<iframe src='/albuminfo/page?id="+row.id+"&cpCode="+cpCode+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
			addOrEditDialog.dialog("open");
		}
	});

	addOrEditDialog.dialog({
		width:700,
		height:500,
		modal : true,
		closable : true,
		closed:true,
	});
	
	
	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#code").val();
			if (id) {
				addOrUpdate("/albuminfo/updateSeriesCodeAndName");
			} else {
				addOrUpdate("/albuminfo/insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");;
		}
	});
	
	$("#seriesCode").textbox({
		required:true,
		validType:['length[0,32]'],
	});
	$("#seriesName").textbox({
		required:true,
		multiline:true,
		validType:['length[0,1024]'],
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
		$("#seriesName").textbox("setValue","");
		$("#seriesCode").textbox("setValue","");
		addOrEditDialog.dialog("setTitle", "新增cp源专辑信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑cp源专辑信息");
		$("#cpCode").val(row.cpCode);
		$("#code").val(row.code);
		$("#seriesName").textbox("setValue",row.seriesName);
		$("#seriesCode").textbox("setValue",row.seriesCode);
	}
	addOrEditDialog.dialog("open").dialog("restore");
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
function confirmed(value){
	unconfirmed(1);
}
function unconfirmed(value){
	var status;
	if(value){
		status=value;	
	}else{
		status=0;	
	}
	var status
	var datagrid = $('#datagrid');
	var array = datagrid.datagrid("getSelections");
	var ids = [];
	for (var i = 0; i < array.length; i++) {
		ids[i]=array[i].id;
	}
	$.messager.confirm('确认', '您确定将选中数据改为未确认关联的状态？', function(yes) {
		if (yes) {
			var url;
			var data={};
			data.cpCode = $("#cpCodeSearch").combobox("getValue");
			data.status=status;
			if(array.length>1){
				url = "/albuminfoJoin/confirmeds";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/albuminfoJoin/confirmed";
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
function statusFormatter(value, row, index){
	if(value==1){
		return "<font style='color:green'>已确认关联</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='confirmedJoin' onclick='confirmedJoin("+row.id+","+"0"+")' class='easyui-linkbutton'>取消关联</a>";
	}else if(value==0){
		return "<font style='color:red'>未确认关联</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='confirmedJoin' onclick='confirmedJoin("+row.id+","+"1"+")' class='easyui-linkbutton'>确认关联</a>";
	}
}
function confirmedJoin(id,status){
	var message;
	if(status==0){
		message='您确定将数据改为确认关联的状态吗？';
	}else if(status==1){
		message='您确定将数据改为未确认关联的状态吗？';
	}
	$.messager.confirm('确认', message, function(yes) {
		if (yes) {
			var url = "/albuminfoJoin/confirmed";
			var data={};
			data.cpCode = $("#cpCodeSearch").combobox("getValue");
			data.status=status;
			data.id=id;
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
		})
}
function remove() {
	var datagrid = $('#datagrid');
	var array = datagrid.datagrid("getSelections");
	var ids = [];
	for (var i = 0; i < array.length; i++) {
		ids[i]=array[i].id;
	}
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var url;
			var data={};
			if(array.length>1){
				url = "/albuminfoJoin/deleteBatch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/albuminfoJoin/delete";
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