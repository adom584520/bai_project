$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'cp源列表',
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/cpInfo/list',
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
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		singleSelect:true,
		columns : [ [ 
		              {field : 'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true}, 
		              {field : 'code',title : 'cpCode',width : 60,align : 'center',},
		              {field : 'name',title : 'cp名称',width : 60,align : 'center',},
		              {field : 'status',title : '版权状态',width : 60,align : 'center',formatter:statusFormatter},
		] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("div.datagrid-toolbar [id ='edit']").eq(0).show();
	    		$("div.datagrid-toolbar [id ='remove']").eq(0).show();
	    	}else{
	    		$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
	    		$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
	    	}
		},
		onSelectAll:function(rows){
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
    		$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("unselectAll");
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
    		$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
    		$("div.datagrid-toolbar [id ='add']").eq(0).show();
		},
		onUnselectAll:function(index,row){
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
    		$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
	    		$("div.datagrid-toolbar [id ='edit']").eq(0).show();
	    		$("div.datagrid-toolbar [id ='remove']").eq(0).show();
			}else if(array.length<1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
	    		$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
			}
		},
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 350,
		height : 180,
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
		onClose:function(){
			$("#addOrEditFrom").form("clear");
		}
	});

	$("#code").textbox({
		required : true,
		validType:['length[0,20]'],
	});
	$("#name").textbox({
		required : true,
		validType:['length[0,20]'],
	});
	$("#status").combobox({
		required : true,
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'全部版权'},
		   {key:'2',value:'点播版权'},
		   {key:'3',value:'直播版权'},
		   {key:'4',value:'无版权'},
		]
	});
});
function statusFormatter(value, row, index){
	if(value){
		if(value==1){
			return "全部版权";
		}else if(value==2){
			return "点播版权";
		}else if(value==3){
			return "直播版权";
		}else if(value==4){
			return "无版权";
		}
	}
	return "";
}

function openAddOrEdit(index) {
	var addOrEditDialog = $('#addOrEditDialog');
	if (index) {
		$("#id").val("");
		$("#code").textbox("setValue","");
		$("#name").textbox("setValue","");
		$("#vodTable").textbox("setValue","");
		$("#liveTable").textbox("setValue","");
		$("#status").combobox("select","1");
		addOrEditDialog.dialog("setTitle", "新增CP源信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑CP源信息");
		$("#id").val(row.id);
		$("#code").textbox("setValue",row.code);
		$("#name").textbox("setValue",row.name);
		$("#vodTable").textbox("setValue",row.vodTable);
		$("#liveTable").textbox("setValue",row.liveTable);
		$("#status").combobox("select",row.status+"");
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
				url = "/cpInfo/delete_batch";
				for (var i = 0; i < array.length; i++) {
					ids[i]=array[i].id;
				}
				data.ids = JSON.stringify(ids);
			}else{
				url = "/cpInfo/delete";
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
function save(){
	if (!validata()) {
		return;
	}
	var id = $("#id").val();
	if (id) {
		addOrUpdate("/cpInfo/update", id);
	} else {
		addOrUpdate("/cpInfo/insert");
	}
}