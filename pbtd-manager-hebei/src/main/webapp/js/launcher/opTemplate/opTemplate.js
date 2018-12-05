$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'运营位模板列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/op_template/list',
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
		           {id:'copy',text:'数据拷贝',iconCls: 'icon-redo',plain:true,handler:function () {
		        	   copy();
		           }},
	           ],
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ {field:'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'groupId',title : '分组ID',hidden : true},
		              {field : 'navId',title : '导航ID',hidden : true},
		              {field : 'tempName',title : '运营位模板名称',width : 80,align : 'center',formatter:showOptimize}, 
		              {field : 'status',title : '是否上线',width : 90,align : 'center',formatter:upLineOrDownLineFormatter},
		              {field : 'loginInfoName',title : '创建账号',width : 70,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 100,align : 'center',formatter:dateFormatter}, 
		              {field : 'modifyTime',title : '修改时间',width : 100,align : 'center',formatter:dateFormatter}, 
		          ] ],
        onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).show();
				$("div.datagrid-toolbar [id ='copy']").eq(0).show();
			}else{
				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
				$("div.datagrid-toolbar [id ='copy']").eq(0).hide();
			}
			$("div.datagrid-toolbar [id ='remove']").eq(0).show();
  		},
  		onSelectAll:function(rows){
  			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
  			$("div.datagrid-toolbar [id ='copy']").eq(0).hide();
  			$("div.datagrid-toolbar [id ='remove']").eq(0).show();
  		},
  		onLoadSuccess:function(data){
  			$("[name=upLineOrDownLine]").linkbutton({
  				plain : false,
  			});
  			$("[name=slideshowOpen]").linkbutton({
  				plain : false,
  			});
  			datagrid.datagrid("unselectAll");
  			 $("div.datagrid-toolbar [id ='add']").eq(0).show();
  			 $("div.datagrid-toolbar [id ='edit']").eq(0).hide();
  			 $("div.datagrid-toolbar [id ='copy']").eq(0).hide();
  			 $("div.datagrid-toolbar [id ='remove']").eq(0).hide();
  		},
  		onUnselectAll:function(index,row){
  			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
  			$("div.datagrid-toolbar [id ='copy']").eq(0).hide();
  			$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
  		},
  		onUnselect:function(index,row){
  			var array = datagrid.datagrid("getSelections");
  			if(array.length==1){
  				$("div.datagrid-toolbar [id ='edit']").eq(0).show();
  				$("div.datagrid-toolbar [id ='copy']").eq(0).show();
  			}else if(array.length<1){
  				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
  				$("div.datagrid-toolbar [id ='copy']").eq(0).hide();
  				$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
  			}
  		},
	});
	
	$("#groupIdSearch").combobox({
		prompt:"请选择分组",
		editable:false,
		valueField:'id',
		textField:'groupName',
		url:'/launcher_group/group_list_all',
		onSelect: function(rec){
			$("#navIdSearch").combobox("clear");
			var url = '/navigation/list_by_group_id?groupId='+rec.id;
			$('#navIdSearch').combobox('reload', url);
		}
	}); 
	$("#navIdSearch").combobox({
		prompt:"请选择导航",
		editable:false,
		valueField:'id',
		textField:'navName',
	}); 
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var navId=$("#navIdSearch").combobox("getValue");
			var params = {};
			if(navId){
				params.navId=navId;
				datagrid.datagrid("load",params);
				datagrid.datagrid("unselectAll");
			}else{
				$.messager.alert('提示信息', "请选择导航后再查询!");
			}
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load",{});
			$("#groupIdSearch").combobox("unselect");
			$("#groupIdSearch").combobox("setValue","");
			$("#navIdSearch").combobox("unselect");
			$("#navIdSearch").combobox("setValue","");
		}
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 400,
		height : 150,
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
			$("#isCoyp").val("");
		}
	});
	
	$("#groupId").combobox({
		prompt:"请选择分组",
		required : true,
		editable:false,
		valueField:'id',
		textField:'groupName',
        onLoadSuccess:function(){
//        	用来选中默认的下拉框值
        	var id=$("#group_id").val();
        	if(id){
        		$("#groupId").combobox("select",id);
        	}else{
        		$("#groupId").combobox("unselect");
        	}
        },
        onSelect: function(rec){
        	$("#navId").combobox("clear");
            var url = '/navigation/list_by_group_id?groupId='+rec.id;    
            $('#navId').combobox('reload', url);
        }
	});
	$("#navId").combobox({
		prompt:"请选择导航",
		required : true,
		editable:false,
		valueField:'id',
		textField:'navName',
        onLoadSuccess:function(){
//        	用来选中默认的下拉框值
        	var id=$("#nav_id").val();
        	if(id){
        		$("#navId").combobox("select",id);
        	}else{
        		$("#navId").combobox("unselect");
        	}
        },
	}); 
	
	$("#tempName").textbox({
		required:true,
		validType:['length[0,20]'],
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
		$("#op_temp_status").val("");
		$('#groupId').combobox("readonly", false);
		$('#navId').combobox("readonly", false);
		$("#tempName").textbox("setValue","");
		addOrEditDialog.dialog("setTitle", "新增运营位模板信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑运营位模板信息");
		$("#op_temp_status").val(row.status);
		$("#temp_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#nav_id").val(row.navId);
		$("#tempName").textbox("setValue",row.tempName);
		$('#groupId').combobox("readonly", true);
		$('#navId').combobox("readonly", true);
	}
	$("#groupId").combobox("reload","/launcher_group/group_list_all");
	addOrEditDialog.dialog("open");
}

function remove(index) {
	var datagrid = $('#datagrid');
	var array = datagrid.datagrid("getSelections");
	var ids = [];
	for (var i = 0; i < array.length; i++) {
		ids[i]=array[i].id;
		if(array[i].status==1){
			$.messager.alert('提示信息', "选中项中有已上线信息，不能删除！");
			return;
		}
	}
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var url;
			var data={};
			if(array.length>1){
				url = "/op_template/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/op_template/delete";
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

function upLineOrDownLineFormatter(value,row,index){
	if(value){
		return "<font style='color:red'>上线中</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"0"+","+row.sortpos+","+row.navId+")' class='easyui-linkbutton'>下线</a>";
	}
	return "<font style='color:green'>已下线</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"1"+","+row.sortpos+","+row.navId+")' class='easyui-linkbutton'>上线</a>";
}
function upLineOrDownLine(id,status,sortpos,navId){
	$.messager.confirm('确认', '您确认想要修改上下线状态吗？', function(yes) {
		if (yes) {
			$.ajax({
				url:"/op_template/upline_or_downline",
				type:"POST",
				data: {id:id,sortpos:sortpos,status:status,navId:navId},
				dataType:"json",
				success: function(data){
					if(data.success){
						var datagrid = $('#datagrid');
						datagrid.datagrid('reload');
					}else{
						$.messager.alert('提示信息',data.message);
					}
				},
			});
		}
	});
}
function showOptimize(value,row,index){
	if(value){
		return value;
	}
	return "无";
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
function copy(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	$.ajax({
		url:"/op_template/query_by_id",
		type:"POST",
		data: {id:row.id},
		dataType:"json",
		success: function(data){
			if(data.success){
				var addOrEditDialog = $('#addOrEditDialog');
				addOrEditDialog.dialog("setTitle", "拷贝运营位信息");
				var obj = data.data;
				$("#isCoyp").val("copy");
				addOrEditDialog.dialog("setTitle", "编辑运营位模板信息");
				$("#temp_id").val(obj.id);
				$("#group_id").val(obj.groupId);
				$("#nav_id").val(obj.navId);
				$("#tempName").textbox("setValue",obj.tempName);
				$("#groupId").combobox("reload","/launcher_group/group_list_all");
				addOrEditDialog.dialog("open");
			}else{
				$.messager.alert('提示信息',data.message);
			}
		},
	});
}
function save(){
	if (!validata()) {
		return;
	}
	var id = $("#temp_id").val();
	if (id) {
		if($("#isCoyp").val()){
			addOrUpdate("/op_template/copy");
		}else{
			var status = $("#op_temp_status").val();
			if(status==1){
				$.messager.confirm('警告', '您修改的是已上线数据，确认修改该数据吗？', function(yes) {
					if (yes) {
						addOrUpdate("/op_template/update");
						return;
					}
				});
				return;
			}
			addOrUpdate("/op_template/update");
		}
	} else {
		addOrUpdate("/op_template/insert");
	}
}