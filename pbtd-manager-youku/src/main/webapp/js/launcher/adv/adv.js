$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'广告列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/advertisement/list',
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
		              {field : 'advName',title : '广告名称',width : 60,align : 'center',formatter:showOptimize}, 
		              {field : 'type',title : '广告类型',width : 80,align : 'center',formatter:showTypeFormatter}, 
		              {field : 'showType',title : '广告显示类型',width : 60,align : 'center',formatter:showShowTypeFormatter}, 
		              {field : 'imageList',title : '广告图片',width : 60,align : 'center',formatter:showImage}, 
		              {field : 'video',title : '广告视频',width : 60,align : 'center',formatter:showVideoFormatter}, 
		              {field : 'advTime',title : '广告时间',width : 60,align : 'center',formatter:showAdvTimeFormatter}, 
		              {field : 'status',title : '是否上线',width : 80,align : 'center',formatter:upLineOrDownLineFormatter},
		              {field : 'loginInfoName',title : '创建账号',width : 80,align : 'center'}, 
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
			datagrid.datagrid("unselectAll");
			$("[name=upLineOrDownLine]").linkbutton({
				plain : false,
			});
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
		height : 300,
		closed : true,
		modal : true,
		closable : true,
		buttons : '#addOrEditDialogButtons',
		onClose:function(){
			$("#addOrEditFrom").form("clear");
			clearImage();
		}
	});
	
	$("#fileuploadDialog").dialog({
		title : '上传图片',
		align : 'center',
		width : 450,
		height : 340,
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
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#adv_id").val();
			if (id) {
				var status = $("#adv_status").val();
				if(status==1){
					$.messager.confirm('警告', '您修改的是已上线数据，确认修改该数据吗？', function(yes) {
						if (yes) {
							addOrUpdate("/advertisement/update");
							return;
						}
					});
					return;
				}
				addOrUpdate("/advertisement/update");
			} else {
				addOrUpdate("/advertisement/insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-cancel',
		plain : false,
		onClick : function() {
			$("#addOrEditDialog").dialog("close");;
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
							$.messager.alert('提示信息', "上传成功！", "", function() {
								urlSetObj.val(data.message);
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
	
	$("#advName").textbox({
		required:true,
		validType:['length[0,20]'],
	});
	$("#video").textbox({
		validType:['length[0,100]'],
	});
	$("#type").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'开机广告'},
		   {key:'2',value:'launcher启动广告'},
		]
	});
	$("#showType").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'图片'},
		   {key:'2',value:'视频'},
		],
		onChange:function(newValue,oldValue){
			if(newValue=='1'){
				$("#imageEditDiv").show();
				$("#videoTr").hide();
			}else{
				$("#imageEditDiv").hide();
				$("#videoTr").show();
			}
		}
	});
	$("#advTime").numberbox({
		required:true,
		min: 0,
	    max: 99999,
	    value:0,
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
		$("#adv_status").val("");
		$('#groupId').combobox("readonly", false);
		$('#type').combobox("readonly", false);
		$("#type").combobox("select",'1');
		$("#showType").combobox("select",'1');
		$("#advTime").numberbox("setValue",0+'');
		$("#imageEditDiv").show();
		$("#videoTr").hide();
		addOrEditDialog.dialog("setTitle", "新增广告信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑广告信息");
		$("#adv_status").val(row.status);
		$("#adv_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#advName").textbox("setValue",row.advName);
		$("#advTime").textbox("setValue",row.advTime);
		$("#video").textbox("setValue",row.video);
		$("#type").combobox("select",row.type+'');
		$("#showType").combobox("select",row.showType+'');
		$('#groupId').combobox("readonly", true);
		$('#type').combobox("readonly", true);
		var showType = row.showType;
		if(showType==1){
			var imageList = row.imageList;
			if(imageList&&imageList.length>0){
				var table=$("#imageTable");
				var first=$("#imageTable tr:first");
				first.find("input").val(imageList[0]);
				for (var i = 1; i < imageList.length; i++) {
					var clone=first.clone();
					var lastTr=$("#imageTable tr:last");
					clone.appendTo(table);
					clone.find("input").val(imageList[i]);
				}
			}
			$("#imageEditDiv").show();
			$("#videoTr").hide();
		}else{
			$("#imageEditDiv").hide();
			$("#videoTr").show();
		}
	}
	$("#groupId").combobox("reload","/launcher_group/group_list_all");
	addOrEditDialog.dialog("open").dialog("restore");
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
				url = "/advertisement/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/advertisement/delete";
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

var urlSetObj;//用来存放上传图片的input对象

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

function fileuploadOpen(obj) {
	$("#fileuploadDialog").dialog("open");
	var data=$(obj).siblings("input");
	urlSetObj = data;
	$("#echo").prop("src",data.val());
	$("#fileuploadDialog").dialog("open");
}
function imageRemove(obj){
	var parent=$(obj).parent("td").parent("tr");
	$.messager.confirm('确认','您确认想要删除该图片吗？',function(ok){    
	    if (ok){
	    	var tr=$("#imageTable tr");
	    	if(tr.length>1){
	    		parent.remove();
	    	}else{
	    		$(obj).val("");
	    	}
	    }    
	});
}
function imageUp(obj){
	var parent=$(obj).parent("td").parent("tr");
	var prev=parent.prev();
	parent.insertBefore(prev);
}
function imageDown(obj){
	var parent=$(obj).parent("td").parent("tr");
	var next=parent.next();
	parent.insertAfter(next);
}
function imageAdd(obj){
	var clone=$("#imageTable tr:first").clone();
	var table=$("#imageTable");
	var lastTr=$("#imageTable tr:last");
	clone.appendTo(table);
	clone.find("input").val("");
}
function clearImage(){
	var children=$("#imageTable").find("tr");
	for (var i = 0; i < children.length; i++) {
		if(i>0){
			children[i].remove();
		}else{
			$(children[i]).find("input").val("");
		}
	}
}
function addOrUpdate(url){
	var val = $("#showType").combobox("getValue");
	var jsonString;
	if(val=='1'){
		var inputs=$("#imageTable input");
		var array = [];
		for (var i = 0; i < inputs.length; i++) {
			if($(inputs[i]).val()){
				array[i]=$(inputs[i]).val();
			}
		}
		jsonString = JSON.stringify(array);
	}
	$("#addOrEditFrom").form("submit", {
		url : url,
		queryParams:{images:jsonString},
		success : function(data) {
			data = $.parseJSON(data);
			if (data.success) {
				$.messager.alert('提示信息', data.message, "", function() {
					$("#addOrEditDialog").dialog("close");
					var datagrid = $('#datagrid');
					datagrid.datagrid("reload");
					$("#addOrEditFrom").form("clear");
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
function showTypeFormatter(value,row,index){
	if(value==1){
		return '开机广告';
	}else if(value=2){
		return 'launcher启动广告';
	}
}
function showVideoFormatter(value,row,index){
	if(value){
		return value;
	}
	return "无";
}
function showAdvTimeFormatter(value,row,index){
	if(value>0){
		return value+"秒";
	}
	return 0+"秒";
}
function upLineOrDownLineFormatter(value,row,index){
	if(value){
		return "<font style='color:red'>上线中</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"0"+")' class='easyui-linkbutton'>下线</a>";
	}
	return "<font style='color:green'>已下线</font>&nbsp;&nbsp;&nbsp;&nbsp;<a name='upLineOrDownLine' onclick='upLineOrDownLine("+row.id+","+"1"+")' class='easyui-linkbutton'>上线</a>";
}
function upLineOrDownLine(id,status){
	$.messager.confirm('确认', '您确认想要修改上下线状态吗？', function(yes) {
		if (yes) {
			$.ajax({
				url:"/advertisement/upline_or_downline",
				type:"POST",
				data: {id:id,status:status},
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
	if(value.length>0){
		return value.length+"张";
	}
	return "无";
}
function showShowTypeFormatter(value,row,index){
	if(value==1){
		return "图片";
	}
	return "视频";
}