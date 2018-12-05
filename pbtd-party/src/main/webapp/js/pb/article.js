$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'文章列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/article/list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		resizable : false,
		fitColumns : true,
		columns : [ [ {field : 'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'groupId',title : '分组ID',hidden : true},
		              {field : 'groupName',title : '分组名称',width : 80,align : 'center'},
		              {field : 'title',title : '标题',width : 80,align : 'center'}, 
		              {field : 'content',title : '内容文本',width : 80,align : 'center'}, 
		              {field : 'type',title : '类型',width : 50,align : 'center',formatter:showTypeFormatter}, 
		              {field : 'images',title : '内容图片',width : 40,align : 'center',formatter:showImage}, 
		              {field : 'logininfoName',title : '创建账号',width : 60,align : 'center'}, 
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
			datagrid.datagrid("clearSelections");
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
		url:'/group/group_list_all',
	});
	$("#typeSearch").combobox({
		editable:false,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		data:[
		   {key:'1',value:'党务'},
		   {key:'2',value:'村务'},
		   {key:'3',value:'财务'},
		]
	});
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var params = {};
			var groupId=$("#groupIdSearch").combobox("getValue");
			var type=$("#typeSearch").combobox("getValue");
			if(groupId){
				params.groupId=groupId;
			}
			if(type){
				params.type=type;
			}
			datagrid.datagrid("load",params);
			
		}
	});
	$("#reload").linkbutton({
		plain : false,
		iconCls : 'icon-reload',
		onClick : function() {
			datagrid.datagrid("load",{});
			$("#groupIdSearch").combobox("unselect");
			$("#groupIdSearch").combobox("setValue","");
			$("#typeSearch").combobox("unselect");
			$("#typeSearch").combobox("setValue","");
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
			var id = $("#article_id").val();
			if (id) {
				addOrUpdate("/article/update");
			} else {
				addOrUpdate("/article/insert");
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
	
	$("#title").textbox({
		required:true,
		validType:['length[0,20]'],
	});
	$("#content").textbox({
		validType:['length[0,900]'],
	});
	$("#type").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'1',
		data:[
		   {key:'1',value:'党务'},
		   {key:'2',value:'财务'},
		   {key:'2',value:'村务'},
		]
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
		$("#type").combobox("select",'1');
		$("#imageEditDiv").show();
		addOrEditDialog.dialog("setTitle", "新增三务信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑三务信息");
		$("#article_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#title").textbox("setValue",row.title);
		$("#content").textbox("setValue",row.content);
		$("#type").combobox("select",row.type+'');
		var images = row.images;
		if(images&&images.length>0){
			var table=$("#imageTable");
			var first=$("#imageTable tr:first");
			first.find("input").val(images[0]);
			for (var i = 1; i < images.length; i++) {
				var clone=first.clone();
				var lastTr=$("#imageTable tr:last");
				clone.appendTo(table);
				clone.find("input").val(images[i]);
			}
		}
	}
	$("#groupId").combobox("reload","/group/group_list_all");
	addOrEditDialog.dialog("open").dialog("restore");
}

function remove(index) {
	var datagrid = $('#datagrid');
	var array = datagrid.datagrid("getSelections");
	var ids = [];
	for (var i = 0; i < array.length; i++) {
		ids[i]=array[i].id;
	}
	$.messager.confirm('确认', '您确认想要删除选中的记录吗？', function(yes) {
		if (yes) {
			var url;
			var data={};
			if(array.length>1){
				url = "/article/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/article/delete";
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
	var jsonString;
	var inputs=$("#imageTable input");
	var array = [];
	for (var i = 0; i < inputs.length; i++) {
		if($(inputs[i]).val()){
			array[i]=$(inputs[i]).val();
		}
	}
	jsonString = JSON.stringify(array);
	$("#addOrEditFrom").form("submit", {
		url : url,
		queryParams:{image:jsonString},
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
		return '党务';
	}else if(value=2){
		return '村务';
	}else if(value=3){
		return '财务';
	}
}
function showImage(value,row,index){
	if(value.length>0){
		return value.length+"张";
	}
	return "无";
}