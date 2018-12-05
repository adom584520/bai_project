$(function() {
	var datagrid = $('#datagrid');
	var seriesCode = $('#seriesCode').val();
	datagrid.datagrid({
//		title:'cp方剧集列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/drama/queryBySeriesCode?seriesCode='+seriesCode,
		toolbar : '#toolbar',
		resizable : false,
		fitColumns : false,
		singleSelect:true,
		columns : [ [ 
		              {field : 'cpSeriesCode',title : 'cp方专辑code',width : 120,align : 'center'},
		              {field : 'cpDrama',title : 'cp方剧集号',width : 120,align : 'center'},
		              {field : 'cpDramaname',title : 'cp方剧集名称',width : 120,align : 'center'},
		              {field : 'drama',title : '己方剧集号',width : 120,align : 'center'},
		              {field : 'dramaname',title : '己方剧集名称',width : 80,align : 'center'}, 
		              {field : 'type',title : '介质类型',width : 80,align : 'center'}, 
		              {field : 'duration',title : '播放时长',width : 80,align : 'center'},
		              {field : 'description',title : '描述信息',width : 80,align : 'center'},
		              {field : 'cpCode',title : 'cpCode',width : 80,align : 'center'},
		              {field : 'joinStatus',title : '绑定状态',width : 80,align : 'center'},
		              {field : 'createTime',title : '创建时间',width : 200,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 200,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#confirm").linkbutton("enable");
	    	}else{
	    		$("#confirm").linkbutton("disable");
	    	}
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
		},
		onUnselectAll:function(index,row){
			$("#confirm").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#confirm").linkbutton("enable");
			}else if(array.length<1){
				$("#confirm").linkbutton("disable");
			}
		},
	});
	
	$("#confirm").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			confirm();
		}
	});
	$("#cancel").linkbutton({
		plain : false,
		iconCls : 'icon-cancel',
		onClick : function() {
			parent.$("#addOrEditDialog").dialog("close");
		}
	});

});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}
function confirm(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	var cpDramaId=$("#cpDramaId").val();
	var data = {};
	data.cpDramaId=cpDramaId;
	data.dramaId=row.id;
	var url = "/cpDrama/bindingDrama"; 
	$.ajax({
		url : url,
		type : "post",
		data : data,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					parent.$('#addOrEditDialog').dialog("close");
					parent.$('#datagrid').datagrid("reload");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}