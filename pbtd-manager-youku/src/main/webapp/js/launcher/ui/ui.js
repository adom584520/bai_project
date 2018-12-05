$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	var urlSetValue = "";
	datagrid.datagrid({
		title:'系统UI信息列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/launcher_ui/list',
		toolbar : '#toolbar',
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
	    		$("#edit").linkbutton("enable");
	    	}else{
	    		$("#edit").linkbutton("disable");
	    	}
			$("#remove").linkbutton("enable");
		},
		onSelectAll:function(rows){
			$("#remove").linkbutton("enable");
		},
		onLoadSuccess:function(data){
			$("[name=upLineOrDownLine]").linkbutton({
				plain : false,
			});
			$("[name=slideshowOpen]").linkbutton({
				plain : false,
			});
			datagrid.datagrid("unselectAll");
		},
		onUnselectAll:function(index,row){
			$("#edit").linkbutton("disable");
			$("#remove").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#edit").linkbutton("enable");
			}else if(array.length<1){
				$("#edit").linkbutton("disable");
				$("#remove").linkbutton("disable");
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
	
	$("#add").linkbutton({
		plain : false,
		iconCls : 'icon-add',
		onClick : function() {
			openAddOrEdit("add");
		}
	});
	
	$("#edit").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-edit',
		onClick : function() {
			openAddOrEdit();
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

	addOrEditDialog.dialog({
		align : 'center',
		width : 500,
		height : 250,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#addOrEditDialogButtons',
		onClose:function(){
			addOrEditFrom.form("clear");
		}
	});
	
	$("#fileuploadDialog").dialog({
		title : '上传图片',
		align : 'center',
		width : 450,
		height : 350,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#fileuploadDialogButtons',
		onClose:function(){
			$("#fileupload").filebox("clear");
			document.getElementById('fileSize').innerHTML = "";
			document.getElementById("preview").src="";
			document.getElementById("echo").src="";
		}
	});
	
	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : true,
		onClick : function() {
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
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : true,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");
		}
	});
	
	$("#fileuploadDialogSave").linkbutton({
		iconCls : 'icon-upload',
		plain : true,
		onClick : function() {
			var value=$("#fileupload").filebox("getValue");
			if(value){
				$('#uploadImageForm').form('submit',{
					url:"/fileupload/image",
					success:function(data){
						data = $.parseJSON(data);
						if(data.success){
							$.messager.alert('提示','上传成功！');
							document.getElementById("echo").src = data.message;
							$("#"+urlSetValue).textbox("setValue",data.message);
						}else{
							$.messager.alert('警告',data.message);
						}
					} 
				});
			}else{
				$.messager.alert('警告','请选择需要上传的图片！');  
			}
		}
	});
	
	$("#fileuploadDialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : true,
		onClick : function() {
			$("#fileuploadDialog").dialog("close");
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
	$('#fileupload').filebox({    
	    buttonText: '上传图片', 
	    buttonAlign: 'left',
	    accept:'.png,.jpg,.PNG,.JPG',
	    onChange:function(){
	    	var fileboxValue=$(this).filebox("getValue");
	    	if(fileboxValue){
	    		var file = $('input[name="file"][type="file"]').prop('files')[0];
		    	//计算文件大小 
		    	var fileSize = 0;
		    	//如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB  
		    	if (file.size > 1024 * 1024) {  
		    		fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;  
		    		if (fileSize > 10) {  
		    			$.messager.alert('警告','文件超过10MB，禁止上传！'); 
		    			return;  
		    		}  
		    		fileSize = fileSize.toString() + 'MB';  
		    	}
		    	else {
		    		fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';  
		    	}
		    	document.getElementById('fileSize').innerHTML = "<span style='color:Blue'>文件大小:"+fileSize;
	    	    var reader = new FileReader();
	    	    reader.readAsDataURL(file)
	            reader.onload = function (e) {
	               var img = document.getElementById("preview");
	               img.src = reader.result;
	            }
	    	}
	    }
	});
	$(".fileuploadOpen").linkbutton({
		plain : false,
		onClick : function() {
			$("#fileuploadDialog").dialog("open");
			var data=$(this).data("myid");
			var url=$("#"+data).textbox("getValue");
			$("#echo").prop("src",url);
			urlSetValue = $(this).data("myid");
		}
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