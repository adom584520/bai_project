$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	var urlSetValue = "";
	datagrid.datagrid({
		title:'导航列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/navigation/list',
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
		              {field : 'navName',title : '导航名称',width : 60,align : 'center',formatter:showOptimize},
		              {field : 'showType',title : '导航类型',width : 40,align : 'center',formatter:showShowType}, 
		              {field : 'text',title : '导航文字',width : 60,align : 'center',formatter:showOptimize}, 
		              {field : 'normalIcon',title : '默认图标',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'pressIcon',title : '按下图标',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'selectedIcon',title : '选中图标',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'sortpos',title : '导航位置',width : 40,align : 'center'}, 
		              {field : 'status',title : '是否上线',width : 80,align : 'center',formatter:upLineOrDownLineFormatter}, 
		              {field : 'loginInfoName',title : '创建人',width : 60,align : 'center'}, 
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
		width : 450,
		height : 350,
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
		width : 400,
		height : 320,
		closed : true,
		modal : true,
		closable : true,
		buttons : 'fileuploadDialogButtons',
		onClose:function(){
			$("#fileupload").filebox("clear");
			document.getElementById('fileSize').innerHTML = "";
			document.getElementById("preview").src="";
			document.getElementById("echo").src="";
		}
	});
	
	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#nav_id").val();
			if (id) {
				addOrUpdate("/navigation/update");
			} else {
				addOrUpdate("/navigation/insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");
		}
	});
	
	$("#fileuploadDialogSave").linkbutton({
		iconCls : 'icon-upload',
		plain : false,
		onClick : function() {
			var value=$("#fileupload").filebox("getValue");
			if(value){
				$('#uploadImageForm').form('submit',{
					url:"/fileupload/image",
					success:function(data){
						data = $.parseJSON(data);
						if(data.success){
							document.getElementById("echo").src = data.message;
							urlSetObj.textbox("setValue",data.message);
							$.messager.alert('提示信息', "上传成功！", "", function() {
								var textbox=urlSetObj.textbox("getValue");
								$("#fileuploadDialog").dialog("close");
							});
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
		plain : false,
		onClick : function() {
			$("#fileuploadDialog").dialog("close");
		}
	});
	
	$("#showType").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',
		textField:'value',
		prompt:"请选择显示类型",
		data:[
		   {key:'1',value:'图片'},
		   {key:'2',value:'文字'},
		],
		onChange:function(newValue,oldValue){
			if(newValue=='1'){
				$("#iconTr").show();
				$("#textTr").hide();
			}else if(newValue=='2'){
				$("#iconTr").hide();
				$("#textTr").show();
			}
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
	
	$("#navName").textbox({
		required : true,
		validType:['length[0,99]'],
	});
	$("#text").textbox({
		validType:['length[0,99]'],
	});
	$("#normalIcon").textbox({
		validType:['length[0,255]'],
	});
	$("#pressIcon").textbox({
		validType:['length[0,255]'],
	});
	$("#selectedIcon").textbox({
		validType:['length[0,255]'],
	});
	$("#packageName").textbox({
		validType:['length[0,255]'],
	});
	$("#className").textbox({
		validType:['length[0,255]'],
	});
	$("#paramKey").textbox({
		prompt:"多个值请用,(英文)分隔",
		validType:['length[0,255]'],
	});
	$("#paramValue").textbox({
		prompt:"多个值请用,(英文)分隔",
		validType:['length[0,255]'],
	});
	$("#sortpos").numberbox({
		required:true,
		min: 1,
	    max: 99,
	    value:1,
	    editable: true,
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

var urlSetObj;//用来存放上传图片的input对象

function dateFormatter(value, row, index) {
	if (value) {
		return customDateFormatter("yyyy-MM-dd hh:mm", new Date(value));
	}
	return "";
}

function openAddOrEdit(index) {
	var addOrEditDialog = $('#addOrEditDialog');
	if (index) {
		$('#groupId').combobox("readonly", false);
		$("#showType").combobox("select",'1');
		addOrEditDialog.dialog("setTitle", "新增导航信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		if(row.status==1){
			$.messager.alert('警告', "已上线开机信息不能编辑！");
			return;
		}
		addOrEditDialog.dialog("setTitle", "编辑导航信息");
		$("#nav_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#navName").textbox("setValue",row.navName);
		$("#showType").combobox("select",row.showType+'');
		$("#text").textbox("setValue",row.text);
		$("#normalIcon").textbox("setValue",row.normalIcon);
		$("#pressIcon").textbox("setValue",row.pressIcon);
		$("#selectedIcon").textbox("setValue",row.selectedIcon);
		$("#packageName").textbox("setValue",row.packageName);
		$("#className").textbox("setValue",row.className);
		var paramKey = row.paramKey;
		if(paramKey){
			paramKey = paramKey.replace("[","");
			paramKey = paramKey.replace("]","");
		}
		var paramValue = row.paramValue;
		if(paramValue){
			paramValue = paramValue.replace("[","");
			paramValue = paramValue.replace("]","");
		}
		$("#paramKey").textbox("setValue",paramKey);
		$("#paramValue").textbox("setValue",paramValue);
		$("#sortpos").numberbox("setValue",row.sortpos);
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
				url = "/navigation/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/navigation/delete";
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
		return "<font style='color:red'>上线中</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"0"+","+row.groupId+","+row.sortpos+")' class='easyui-linkbutton'>下线</a>";
	}
	return "<font style='color:green'>已下线</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"1"+","+row.groupId+","+row.sortpos+")' class='easyui-linkbutton'>上线</a>";
}
function upLineOrDownLine(id,status,groupId,sortpos){
	$.messager.confirm('确认', '您确认想要修改上下线状态吗？', function(yes) {
		if (yes) {
			$.ajax({
				url:"/navigation/upline_or_downline",
				type:"POST",
				data: {id:id,status:status,groupId:groupId,sortpos:sortpos},
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
function showImage(value,row,index){
	if(value){
		return "<img src="+value+" style='width:25px;height:25px'>";
	}
	return "无";
}
function showShowType(value,row,index){
	if(value==1){
		return "图标";
	}else{
		return "文字";
	}
}
function fileuploadOpen(obj) {
	$("#fileuploadDialog").dialog("open");
	var data=$(obj).siblings("input");
	urlSetObj = data;
	$("#fileuploadDialog").dialog("open");
}