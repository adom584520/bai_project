$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	var urlSetValue = "";
	datagrid.datagrid({
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/launcher_group_detail/group_detail_list',
		toolbar : '#toolbar',
		checkOnSelect : true,
		pagination : true,
		pagePosition : 'bottom',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : true,
		resizable : false,
		fitColumns : true,
		columns : [ [ {field:'ck',checkbox:true,},
		              {field : 'id',title : 'id',hidden : true},
		              {field : 'groupId',title : '分组ID',hidden : true},
		              {field : 'groupName',title : '分组名称',width : 60,align : 'center'},
		              {field : 'logoUrl',title : 'LOGO URL',width : 60,align : 'center',formatter:showOptimize}, 
		              {field : 'backgroundUrl',title : '背景图  URL',width : 60,align : 'center',formatter:showOptimize}, 
		              {field : 'videoUrl',title : '视频 URL',width : 60,align : 'center',formatter:showOptimize}, 
		              {field : 'countDown',title : '开机倒计时(秒)',width : 60,align : 'center',formatter:countDownUnit}, 
		              {field : 'status',title : '是否上线',width : 80,align : 'center',formatter:upLineOrDownLineFormatter}, 
		              {field : 'slideshows',title : '轮播图',width : 80,align : 'center',formatter:slideshowsFormatter}, 
		          ] ],
		onSelect:function(index,row){
			$("#edit").linkbutton("enable");
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
	});
	$("#add").linkbutton({
		plain : false,
		iconCls : 'icon-add',
		onClick : function() {
			openAddOrEdit("add");
		}
	});
	
	$("#edit").linkbutton({
		plain : false,
		iconCls : 'icon-edit',
		onClick : function() {
			openAddOrEdit();
		}
	});
	$("#remove").linkbutton({
		plain : false,
		iconCls : 'icon-remove',
		onClick : function() {
			remove();
		}
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 500,
		height : 280,
		closed : true,
		modal : true,
		closable : true,
		buttons : 'addOrEditDialogButtons',
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
		plain : true,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#group_detail_id").val();
			if (id) {
				addOrUpdate("/launcher_group_detail/group_detail_update");
			} else {
				addOrUpdate("/launcher_group_detail/group_detail_insert");
			}
		}
	});
	$("#dialogClose").linkbutton({
		iconCls : 'icon-clear',
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
		iconCls : 'icon-clear',
		plain : true,
		onClick : function() {
			$("#fileuploadDialog").dialog("close");
		}
	});
	
	$("#groupId").combobox({
		required : true,
		editable:false,
		panelHeight:"auto",
		valueField:'id',
		textField:'groupName',
        onLoadSuccess:function(){
//        	用来选中默认的下拉框值
        	var id=$("#group_id").val();
        	if(id){
        		$("#groupId").combobox("select",id);
        	}else{
        		var datas=$("#groupId").combobox("getData");
        		var id = datas[0].id;
        		$("#groupId").combobox("select",id);
        	}
        }
	}); 
	
	$("#logoUrl").textbox({
		validType:['length[1,255]'],
	});
	$("#backgroundUrl").textbox({
		validType:['length[1,255]'],
	});
	$("#videoUrl").textbox({
		validType:['length[1,255]'],
	});
	$("#countDown").numberbox({
		min: 0,
	    max: 999,
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
	$(".fileuploadOpen").linkbutton({
		plain : false,
		onClick : function() {
			$("#fileuploadDialog").dialog("open");
			urlSetValue = this.name;
		}
	});
	
	$("#slideshowDialog").dialog({
		title : '轮播图片编辑',
		align : 'center',
		width : 400,
		height : 300,
		closed : true,
		modal : true,
		closable : true,
		buttons : 'slideshowDialogButtons',
		onClose:function(){
			$("#fileupload").filebox("clear");
			document.getElementById('fileSize').innerHTML = "";
			document.getElementById("preview").src="";
			document.getElementById("echo").src="";
			var trs=$("#slideshowTable tr");
			if(trs&&trs.length>0){
				$(trs[0]).find("input").val("");
				for (var i = 1; i < trs.length; i++) {
					trs[i].remove();
				}
			}
		}
	});
	
	$("#slideshowAdd").linkbutton({
		plain : false,
		onClick : function() {
			var clone=$("#slideshowTable tr:first").clone();
			var table=$("#slideshowTable");
			var lastTr=$("#slideshowTable tr:last");
			clone.appendTo(table);
			clone.find("input").val("");
		}
	});
	$("#imageUrlReceive").textbox({
		prompt:"上传成功的图片url",
	});
	$("#slideshowDialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			var inputs=$("#slideshowTable input");
			var array = [];
			for (var i = 0; i < inputs.length; i++) {
				array[i]=$(inputs[i]).val();
			}
			var jsonString = JSON.stringify(array);
			var id=$("#group_detail_slideshow_id").val();
			$.ajax({
				url : "/launcher_group_detail/group_detail_slideshow_update",
				type : "post",
				data : {id:id,slideshows:jsonString},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示信息', data.message, "", function() {
							$("#slideshowDialog").dialog("close");
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
	$("#slideshowDialogClose").linkbutton({
		iconCls : 'icon-clear',
		plain : false,
		onClick : function() {
			$("#slideshowDialog").dialog("close");
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
	if ("add" == index) {
		addOrEditDialog.dialog("setTitle", "新增分组开机信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		if(row.status==1){
			$.messager.alert('警告', "已上线开机信息不能编辑！");
			return;
		}
		addOrEditDialog.dialog("setTitle", "编辑分组开机信息");
		$("#group_detail_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#logoUrl").textbox("setValue",row.logoUrl);
		$("#backgroundUrl").textbox("setValue",row.backgroundUrl);
		$("#videoUrl").textbox("setValue",row.videoUrl);
		$("#countDown").numberbox("setValue",row.countDown);
		$("#contacts_number").textbox("setValue",row.contactsNumber);
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
	var row = datagrid.datagrid("getSelected");
	if(row.status==1){
		$.messager.alert('警告', "已上线开机信息不能删除！");
		return;
	}
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(yes) {
		if (yes) {
			$.ajax({
				url : "/launcher_group_detail/group_detail_delete",
				type : "post",
				data : "id=" + row.id,
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
	var datagrid = $('#datagrid');
	$.ajax({
		url:"/launcher_group_detail/group_detail_upline_or_downline",
		type:"POST",
		data: "id="+id+"&status="+status+"&groupId="+groupId,
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
function slideshowsFormatter(value,row,index){
	if(value&&value.length>0){
		return value.length+"张"+"&nbsp;&nbsp;&nbsp;&nbsp;<a name='slideshowOpen' onclick='slideshowDialogOpen("+index+")' class='easyui-linkbutton'>编辑</a>";
	}
	return "无"+"&nbsp;&nbsp;&nbsp;&nbsp;<a name='slideshowOpen' onclick='slideshowDialogOpen("+index+")' class='easyui-linkbutton'>编辑</a>";;
}
function slideshowDialogOpen(index){
	var datagrid = $('#datagrid');
	datagrid.datagrid("selectRow", index);
	var row = datagrid.datagrid("getSelected");
	if(row.status==1){
		$.messager.alert('提示信息',"已上线信息不能修改！");
		return;
	}
	$("#group_detail_slideshow_id").val(row.id);
	var any = row.slideshows;
	if(any&&any.length>0){
		var table=$("#slideshowTable");
		var first=$("#slideshowTable tr:first");
		first.find("input").val(any[0]);
		for (var i = 1; i < any.length; i++) {
			var clone=first.clone();
			var lastTr=$("#slideshowTable tr:last");
			clone.appendTo(table);
			clone.find("input").val(any[i]);
		}
	}
	$("#slideshowDialog").dialog("open");
	
}
function slideshowsRemove(obj){
	var parent=$(obj).parent("td").parent("tr");
	$.messager.confirm('确认','您确认想要删除该图片吗？',function(ok){    
	    if (ok){
	    	var tr=$("#slideshowTable tr");
	    	if(tr.length>1){
	    		parent.remove();
	    	}
	    }    
	});
}
function slideshowsUp(obj){
	var parent=$(obj).parent("td").parent("tr");
	var prev=parent.prev();
	parent.insertBefore(prev);
}
function slideshowsDown(obj){
	var parent=$(obj).parent("td").parent("tr");
	var next=parent.next();
	parent.insertAfter(next);
}
function showOptimize(value,row,index){
	if(value){
		return value;
	}
	return "无";
}