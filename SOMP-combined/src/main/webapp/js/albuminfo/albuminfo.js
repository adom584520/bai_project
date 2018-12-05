$(function() {
	var datagrid = $('#datagrid');
	datagrid.datagrid({
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/albuminfo/list',
		toolbar : [
		           {id:'confirm',text:'确认',iconCls:'icon-ok',height:30,plain:true,handler:function () {
		        	   confirm();
		           }},
		           {id:'cancel',text:'取消',iconCls: 'icon-cancel',plain:true,handler:function () {
		        	   parent.$("#addOrEditDialog").dialog("close");
		           }},
	           ],
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : false,
		singleSelect:true,
		columns : [ [ 
		              {field : 'seriesCode',title : '专辑code',width : 120,align : 'center'},
		              {field : 'seriesName',title : '专辑名称',width : 120,align : 'center'},
		              {field : 'volumnCount',title : '总剧集数',width : 80,align : 'center'}, 
		              {field : 'currentNum',title : '当前剧集数',width : 80,align : 'center'},
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
		              {field : 'createTime',title : '创建时间',width : 120,align : 'center',formatter:dateFormatter}, 
		              {field : 'updateTime',title : '修改时间',width : 120,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("div.datagrid-toolbar [id ='confirm']").eq(0).show();
	    	}else{
	    		$("div.datagrid-toolbar [id ='confirm']").eq(0).hide();
	    	}
		},
		onLoadSuccess:function(data){
			datagrid.datagrid("clearSelections");
			$("div.datagrid-toolbar [id ='confirm']").eq(0).hide();
		},
		onUnselectAll:function(index,row){
			$("div.datagrid-toolbar [id ='confirm']").eq(0).hide();
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("div.datagrid-toolbar [id ='confirm']").eq(0).show();
			}else if(array.length<1){
				$("div.datagrid-toolbar [id ='confirm']").eq(0).hide();
			}
		},
	});
	
	$("#seriesNameSearch").textbox({
		validType:['length[0,1024]'],
	});
	$("#seriesCodeSearch").textbox({
		validType:['length[0,32]'],
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
			datagrid.datagrid("load",params);
		}
	});
})

function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}
function confirm(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	var cpAlbuminfoId=$("#cpAlbuminfoId").val();
	var cpCode=$("#cpCode").val();
	var data = {};
	data.id=cpAlbuminfoId;
	data.cpCode=cpCode;
	data.seriesCode=row.seriesCode;
	data.seriesName=row.seriesName;
	var url = "/cpAlbuminfo/bindingAlbum"; 
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
