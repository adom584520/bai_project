$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	datagrid.datagrid({
		title:'cp方频道列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/cpChannel/list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : false,
		columns : [ [ {field : 'ck',checkbox:true,},
		              {field : 'id',title : '频道ID',hidden : true},
		              {field : 'cpChnCode',title : 'cp频道code',width : 150,align : 'center'},
		              {field : 'cpChnName',title : 'cp频道名称',width : 120,align : 'center'}, 
		              {field : 'chnCode',title : '绑定频道code',width : 120,align : 'center'},
		              {field : 'chnName',title : '绑定频道名称',width : 120,align : 'center'},
		              {field : 'cpCode',title : 'cpCode',width : 120,align : 'center'}, 
		              {field : 'status',title : '上线状态',width : 120,align : 'center',formatter:statusFormatter}, 
		              {field : 'joinStatus',title : '绑定状态',width : 120,align : 'center',formatter:joinStatusFormatter}, 
		              {field : 'createTime',title : '创建时间',width : 120,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 120,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#bindingChannel").linkbutton("enable");
	    	}else{
	    		$("#bindingChannel").linkbutton("disable");
	    	}
	    	$("#unpinless").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
		},
		onUnselectAll:function(index,row){
			$("#bindingChannel").linkbutton("disable");
			$("#unpinless").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#bindingChannel").linkbutton("enable");
			}else if(array.length<1){
				$("#bindingChannel").linkbutton("disable");
				$("#unpinless").linkbutton("enable");
			}
		},
	});
	
	$("#cpCodeSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'code',    
		textField:'name',
		url:'/cpInfo/list_all',
	});
	
	$("#statusSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'-1',
		data:[
		   {key:'-1',value:'全部'},
		   {key:'1',value:'已上线'},
		   {key:'0',value:'已下线'},
		]
	});
	$("#joinStatusSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'-1',
		data:[
		      {key:'-1',value:'全部'},
		      {key:'1',value:'已绑定'},
		      {key:'0',value:'未绑定'},
		      ]
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
			var cpChnCode=$("#chnCodeSearch").textbox("getValue");
			if(cpChnCode){
				params.cpChnCode=cpChnCode;
			}
			var cpChnCode=$("#chnNameSearch").textbox("getValue");
			if(cpChnCode){
				params.cpChnCode=cpChnCode;
			}
			var cpCode=$("#cpCodeSearch").combobox("getValue");
			if(cpCode){
				params.cpCode=cpCode;
			}
			var status=$("#statusSearch").combobox("getValue");
			if(status){
				params.status=status;
			}
			var joinStatus=$("#joinStatusSearch").combobox("getValue");
			if(joinStatus){
				params.joinStatus=joinStatus;
			}
			datagrid.datagrid("load",params);
		}
	});
	
	$("#unpinless").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			unpinless();
		}
	});
	
	$("#bindingChannel").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			var datagrid = $('#datagrid');
			var row = datagrid.datagrid("getSelected");
			var cpCode = $("#cpCodeSearch").combobox("getValue");
			var addOrEditDialog=$("#addOrEditDialog");
			addOrEditDialog.dialog({title:"已方频道数据列表"});
			addOrEditDialog.dialog({content:"<iframe src='/channel/page?id="+row.id+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
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

});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}

function unpinless() {
	$.messager.confirm('确认', '您确认想要将选中的记录清除绑定吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			var array = datagrid.datagrid("getSelections");
			var ids = [];
			for (var i = 0; i < array.length; i++) {
				if(array[i].joinStatus==1){
					ids[i]=array[i].id;
				}
			}
			$.ajax({
				url : "/cpChannel/unpinless",
				type : "post",
				data : {ids:JSON.stringify(ids)},
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
		return "<font style='color:green'>已上线</font>";
	}else if(value==0){
		return "<font style='color:red'>已下线</font>";
	}
}
function joinStatusFormatter(value, row, index){
	if(value==0){
		return "未绑定";
	}else if(value==1){
		return "已确认绑定";
	}else if(value==2){
		return "未确认绑定";
	}else{
		return "";
	}
}