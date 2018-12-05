$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'界面UI信息列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/launcher_ui/list',
		toolbar : [
		           {id:'add',text:'新增',iconCls:'icon-add',height:30,plain:true,handler:function () {
		        	   openAddOrEdit("add");
		           }},
		           {id:'edit',text:'编辑',iconCls: 'icon-edit',plain:true,handler:function () {
		        	   openAddOrEdit();
		           }},
		           {id:'remove',text:'删除',iconCls: 'icon-remove',plain:true,handler:function () {
		        	   remove();
		           }}
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
		              {field : 'groupName',title : '分组名称',width : 60,align : 'center'},
		              {field : 'logo',title : 'LOGO',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'posterBackground',title : '背景海报',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'pickerView',title : '选中框',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'watchBackground',title : '看点背景',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'status',title : '是否上线',width : 80,align : 'center',formatter:upLineOrDownLineFormatter}, 
		              {field : 'loginInfoName',title : '创建人',width : 80,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 80,align : 'center',formatter:dateFormatter}, 
		              {field : 'modifyTime',title : '修改时间',width : 80,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
	    		$("div.datagrid-toolbar [id ='edit']").eq(0).show();
	    	}else{
	    		$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
	    	}
	    	$("div.datagrid-toolbar [id ='remove']").eq(0).show();
		},
		onSelectAll:function(rows){
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
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
			 $("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselectAll:function(index,row){
			$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
			$("div.datagrid-toolbar [id ='remove']").eq(0).hide();
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).show();
			}else if(array.length<1){
				$("div.datagrid-toolbar [id ='edit']").eq(0).hide();
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
	}); 
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var groupId=$("#groupIdSearch").combobox("getValue");
			var params = {};
			if(groupId){
				params.groupId=groupId;
			}
			datagrid.datagrid("load",params);
			datagrid.datagrid("unselectAll");
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load",{});
			$("#groupIdSearch").combobox("unselect");
			$("#groupIdSearch").combobox("setValue","");
		}
	});
	
	addOrEditDialog.dialog({
		align : 'center',
		width : 500,
		height : 250,
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
			addOrEditFrom.form("clear");
		}
	});
	
	$("#fileuploadDialog").dialog({
		title : '上传图片',
		align : 'center',
		width : 500,
		height : 370,
		closed : true,
		modal : true,
		closable : true,
		buttons : [
		           {text:"上传",iconCls:'icon-upload',plain:true,handler:function () {
		        	   $("#fileuploadIframe")[0].contentWindow.upload();
		           }},
		           {text:"返回",iconCls: 'icon-cancel',plain:true,handler:function () {
		        	   $("#fileuploadDialog").dialog("close");
		           }}
		           ],
		onClose:function(){
			$("#"+document.urlSetValue).textbox("setValue",document.imageValueUrl);
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
        }
	}); 
	
	$("#logo").textbox({
		validType:['length[0,255]'],
	});
	$("#posterBackground").textbox({
		validType:['length[0,255]'],
	});
	$("#pickerView").textbox({
		validType:['length[0,255]'],
	});
	$("#watchBackground").textbox({
		validType:['length[0,255]'],
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
		$("#ui_status").val("");
		addOrEditDialog.dialog("setTitle", "新增系统UI信息");
		$('#groupId').combobox("readonly", false);
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑系统UI信息");
		$("#ui_id").val(row.id);
		$("#ui_status").val(row.status);
		$("#group_id").val(row.groupId);
		$("#logo").textbox("setValue",row.logo);
		$("#posterBackground").textbox("setValue",row.posterBackground);
		$("#pickerView").textbox("setValue",row.pickerView);
		$("#watchBackground").textbox("setValue",row.watchBackground);
		$('#groupId').combobox("readonly", true);
	}
	$("#groupId").combobox("reload","/launcher_group/group_list_all");
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
				url = "/launcher_ui/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/launcher_ui/delete";
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
		return "<font style='color:red'>上线中</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"0"+","+row.groupId+")' class='easyui-linkbutton'>下线</a>";
	}
	return "<font style='color:green'>已下线</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"1"+","+row.groupId+")' class='easyui-linkbutton'>上线</a>";
}
function countDownUnit(value,row,index){
	return value+"(秒)";
}
function upLineOrDownLine(id,status,groupId){
	$.messager.confirm('确认', '您确认想要修改上下线状态吗？', function(yes) {
		if (yes) {
			var datagrid = $('#datagrid');
			$.ajax({
				url:"/launcher_ui/upline_or_downline",
				type:"POST",
				data: {id:id,status:status,groupId:groupId},
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
	});
}
function showOptimize(value,row,index){
	if(value){
		return value;
	}
	return "无";
}
function showImage(value,row,index){
	if(value){
		return "<img src="+value+" style='width:25px;height:25px'>";
	}
	return "无";
}
function save(){
	if (!validata()) {
		return;
	}
	var id = $("#ui_id").val();
	if (id) {
		var status = $("#ui_status").val();
		if(status==1){
			$.messager.confirm('警告', '您修改的是已上线数据，确认修改该数据吗？', function(yes) {
				if (yes) {
					addOrUpdate("/launcher_ui/update");
					return;
				}
			});
			return;
		}
		addOrUpdate("/launcher_ui/update");
	} else {
		addOrUpdate("/launcher_ui/insert");
	}
}
function fileuploadOpen(obj) {
	var inputId = $(obj).data("myid");
	document.urlSetValue=inputId;
	document.imageValueUrl=$('#'+inputId).textbox("getValue");
	var fileuploadDialog=$("#fileuploadDialog");
	fileuploadDialog.dialog({title:"图片上传"});
	fileuploadDialog.dialog({content:"<iframe id='fileuploadIframe' src='/html/imageUpload.html' style='width:100%;height:100%;' frameborder=0></iframe>"});
	fileuploadDialog.dialog("open");
}