$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'cp方专辑数据列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/cpAlbuminfo/list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : false,
		columns : [ [ {field : 'ck',checkbox:true,},
		              {field : 'cpSeriesCode',title : 'cp方专辑code',width : 120,align : 'center'},
		              {field : 'cpSeriesName',title : 'cp方专辑名称',width : 120,align : 'center'},
		              {field : 'seriesCode',title : '专辑code',width : 120,align : 'center'},
		              {field : 'seriesName',title : '专辑名称',width : 120,align : 'center'},
		              {field : 'volumnCount',title : '总剧集数',width : 60,align : 'center'}, 
		              {field : 'cpCode',title : 'cpCode',width : 80,align : 'center'}, 
		              {field : 'currentNum',title : '当前剧集数',width : 70,align : 'center'},
		              {field : 'originalName',title : '原名',width : 80,align : 'center'},
		              {field : 'actorName',title : '导演名称',width : 80,align : 'center'},
		              {field : 'writerName',title : '主演名称',width : 80,align : 'center'},
		              {field : 'orgairDate',title : '首映时间',width : 80,align : 'center'},
		              {field : 'releaseYear',title : '上映时间',width : 80,align : 'center'},
		              {field : 'description',title : '描述信息',width : 80,align : 'center'},
		              {field : 'tag',title : '标签',width : 80,align : 'center'},
		              {field : 'viewPoint',title : '内容看点',width : 80,align : 'center'},
		              {field : 'originalCountry',title : '国家(地区)',width : 80,align : 'center'},
		              {field : 'duration',title : '总时长',width : 80,align : 'center'},
		              {field : 'status',title : '是否上线',width : 80,align : 'center',formatter:statusFormatter}, 
		              {field : 'joinStatus',title : '关联状态',width : 80,align : 'center',formatter:joinStatusFormatter}, 
		              {field : 'createTime',title : '创建时间',width : 150,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 150,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#bindingAlbum").linkbutton("enable");
	    		$("#bindingDrama").linkbutton("enable");
	    	}else{
	    		$("#bindingAlbum").linkbutton("disable");
	    		$("#bindingDrama").linkbutton("disable");
	    	}
	    	$("#unpinless").linkbutton("enable");
	    	$("#confirm").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
		},
		onUnselectAll:function(index,row){
			$("#bindingAlbum").linkbutton("disable");
			$("#bindingDrama").linkbutton("disable");
			$("#unpinless").linkbutton("disable");
			$("#confirm").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#bindingAlbum").linkbutton("enable");
				$("#bindingDrama").linkbutton("enable");
				$("#unpinless").linkbutton("enable");
				$("#confirm").linkbutton("enable");
			}else if(array.length<1){
				$("#bindingAlbum").linkbutton("disable");
				$("#bindingDrama").linkbutton("disable");
				$("#unpinless").linkbutton("disable");
				$("#confirm").linkbutton("disable");
			}else{
				$("#unpinless").linkbutton("enable");
				$("#confirm").linkbutton("enable");
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
		      {key:'1',value:'已确认绑定'},
		      {key:'2',value:'未确认绑定'},
		      {key:'0',value:'未绑定'},
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
			var joinStatus=$("#joinStatusSearch").textbox("getValue");
			if(joinStatus){
				params.joinStatus=joinStatus;
			}
			datagrid.datagrid("load",params);
		}
	});
	
	$("#bindingAlbum").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			var addOrEditDialog=$("#addOrEditDialog");
			var datagrid = $('#datagrid');
			var row = datagrid.datagrid("getSelected");
			var cpCode = row.cpCode;
			addOrEditDialog.dialog({title:"己方剧集数据列表"});
			addOrEditDialog.dialog({content:"<iframe src='/albuminfo/page?cpAlbuminfoId="+row.id+"&cpCode="+cpCode+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
			addOrEditDialog.dialog("open");
		}
	});
	$("#bindingDrama").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			var datagrid = $('#datagrid');
			var row = datagrid.datagrid("getSelected");
			if(!row.seriesCode){
				$.messager.alert('提示信息', "请先绑定专辑，才能进行绑定剧集操作！");
				return;
			}
			addOrEditDialog.dialog({title:"cp方剧集数据列表"});
			addOrEditDialog.dialog({content:"<iframe src='/cpDrama/page?cpSeriesCode="+row.cpSeriesCode+"&cpCode="+row.cpCode+"&seriesCode="+row.seriesCode+"' style='width:100%;height:100%;' frameborder=0></iframe>"});
			addOrEditDialog.dialog("open");
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
	$("#confirm").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-ok',
		onClick : function() {
			confirm();
		}
	});

	addOrEditDialog.dialog({
		width:800,
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
				ids[i]=array[i].id;
			}
			$.ajax({
				url : "/cpAlbuminfo/unpinless",
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
				url : "/cpAlbuminfo/confirm",
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