$(function() {
	var datagrid = $('#datagrid');
	datagrid.datagrid({
		title:'绑定关系列表',
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/bindingInfo/list',
		toolbar : '#toolbar',
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
		              {field : 'phonenumber',title : '电话号码',width : 60,align : 'center'},
		              {field : 'userId',title : '用户ID',width : 60,align : 'center'},
		              {field : 'cpCode',title : 'cpCode',width : 60,align : 'center'},
		              {field : 'mac',title : 'MAC',width : 60,align : 'center'},
		              {field : 'status',title : '版权状态',width : 60,align : 'center',formatter:statusFormatter},
		              {field : 'createTime',title : '创建时间',width : 60,align : 'center',formatter:dateFormatter},
		              {field : 'updateTime',title : '修改时间',width : 60,align : 'center',formatter:dateFormatter},
		] ],
		onLoadSuccess:function(data){
			datagrid.datagrid("unselectAll");
		},
	});
	$("#cpCodeSearch").combobox({
		prompt:"请选择cp方",
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
		data:[
		   {key:'1',value:'绑定中'},
		   {key:'2',value:'解除绑定'},
		]
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load",{});
			$("#cpCodeSearch").combobox("unselect");
			$("#cpCodeSearch").combobox("setValue","");
			$("#statusSearch").combobox("unselect");
			$("#statusSearch").combobox("setValue","");
		}
	});
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var params = {};
			var cpCode=$("#cpCodeSearch").combobox("getValue");
			if(cpCode){
				params.cpCode=cpCode;
			}
			var status=$("#statusSearch").combobox("getValue");
			if(status){
				params.status=status;
			}
			datagrid.datagrid("load",params);
		}
	});

});
function statusFormatter(value, row, index){
	if(value){
		if(value==1){
			return "绑定";
		}else if(value==2){
			return "解除绑定";
		}
	}
	return "";
}
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}