$(function() {
	var datagrid = $('#datagrid');
	var cpCode = $('#cpCode').val();
	var cpSeriesCode = $('#cpSeriesCode').val();
	datagrid.datagrid({
//		title:'cp方剧集列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/cpDrama/queryByAlbumId?cpCode='+cpCode+'&cpSeriesCode='+cpSeriesCode,
		toolbar : '#toolbar',
		resizable : false,
		fitColumns : false,
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
		              {field : 'joinStatus',title : '绑定状态',width : 80,align : 'center',formatter:joinStatusFormatter},
		              {field : 'createTime',title : '创建时间',width : 200,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 200,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#bindingDrama").linkbutton("enable");
	    	}else{
	    		$("#bindingDrama").linkbutton("disable");
	    	}
	    	$("#confirm").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
		},
		onUnselectAll:function(index,row){
			$("#bindingDrama").linkbutton("disable");
			$("#confirm").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#bindingDrama").linkbutton("enable");
				$("#confirm").linkbutton("enable");
			}else if(array.length<1){
				$("#bindingDrama").linkbutton("disable");
				$("#confirm").linkbutton("disable");
			}else{
				$("#confirm").linkbutton("enable");
			}
		},
	});
	
	$("#addOrEditDialog").dialog({
		width:700,
		height:400,
		modal : true,
		closable : true,
		closed:true,
	});
	
	$("#bindingDrama").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			var datagrid = $('#datagrid');
			var row = datagrid.datagrid("getSelected");
			var seriesCode = $('#seriesCode').val();
			var addOrEditDialog = $("#addOrEditDialog");
			addOrEditDialog.dialog({title:"已方剧集数据列表"});
			addOrEditDialog.dialog({content:"<iframe src='/drama/page?cpDramaId="+row.id+"&seriesCode="+seriesCode+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
			addOrEditDialog.dialog("open");
		}
	});
	$("#confirm").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			confirm();
		}
	});

});
function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
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
function confirm(){
	$.messager.confirm('确认', '您确认选中的数据绑定关系正确吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			var array = datagrid.datagrid("getSelections");
			var ids = [];
			for (var i = 0; i < array.length; i++) {
				var data = array[i];
				if(data.joinStatus==2){
					ids[i]=array[i].id;
				}
			}
			$.ajax({
				url : "/cpDrama/confirm",
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