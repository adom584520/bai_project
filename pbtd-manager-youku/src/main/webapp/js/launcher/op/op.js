$(function() {
	var datagrid = $('#datagrid');
	var addOrEditDialog = $('#addOrEditDialog');
	var addOrEditFrom = $('#addOrEditFrom');
	datagrid.datagrid({
		title:'运营位列表',
		striped:true,
		rownumbers:true,
		idField : 'id',
		fit : true,
		url : '/operation_position/list',
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
		              {field : 'navId',title : '导航ID',hidden : true},
		              {field : 'groupId',title : '分组ID',hidden : true},
		              {field : 'operationName',title : '运营位名称',width : 80,align : 'center',formatter:showOptimize}, 
		              {field : 'titleName',title : '图片标题',width : 80,align : 'center',formatter:showOptimize}, 
		              {field : 'titleDetail',title : '内容看点',width : 80,align : 'center',formatter:showShowTypeFormatter}, 
		              {field : 'showTitle',title : '显示标题',width : 60,align : 'center',formatter:showShowTitleFormatter}, 
		              {field : 'playTime',title : '播放时间',width : 60,align : 'center',formatter:showPlayTimeFormatter}, 
		              {field : 'sortpos',title : '位置',width : 40,align : 'center',formatter:showOptimize}, 
		              {field : 'focus',title : '焦点获取',width : 50,align : 'center',formatter:showFocusFormatter},
		              {field : 'showType',title : '显示类型',width : 60,align : 'center',formatter:showShowTypeFormatter},
		              {field : 'status',title : '是否上线',width : 90,align : 'center',formatter:upLineOrDownLineFormatter},
		              {field : 'loginInfoName',title : '创建账号',width : 70,align : 'center'}, 
		              {field : 'createTime',title : '创建时间',width : 100,align : 'center',formatter:dateFormatter}, 
		              {field : 'modifyTime',title : '修改时间',width : 100,align : 'center',formatter:dateFormatter}, 
		          ] ],
		onSelect:function(index,row){
			var array = datagrid.datagrid("getSelections");
	    	if(array.length==1){
	    		$("#edit").linkbutton("enable");
	    		$("#copy").linkbutton("enable");
	    	}else{
	    		$("#edit").linkbutton("disable");
	    		$("#copy").linkbutton("disable");
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
			$("#copy").linkbutton("disable");
		},
		onUnselect:function(index,row){
			var array = datagrid.datagrid("getSelections");
			if(array.length==1){
				$("#edit").linkbutton("enable");
				$("#copy").linkbutton("enable");
			}else if(array.length<1){
				$("#copy").linkbutton("disable");
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
		onSelect: function(rec){
			$("#navIdSearch").combobox("clear");
			$("#tempIdSearch").combobox("clear");
			var url = '/navigation/list_by_group_id?groupId='+rec.id;
			$('#navIdSearch').combobox('reload', url);
		}
	}); 
	$("#navIdSearch").combobox({
		prompt:"请选择导航",
		editable:false,
		valueField:'id',
		textField:'navName',
		onSelect: function(rec){
			$("#tempIdSearch").combobox("clear");
			var url = '/op_template/list_by_navId?navId='+rec.id;
			$('#tempIdSearch').combobox('reload', url);
		}
	}); 
	$("#tempIdSearch").combobox({
		prompt:"请选择模板",
		editable:false,
		valueField:'id',
		textField:'tempName',
	}); 
	
	$("#conditionSearch").linkbutton({
		plain : false,
		iconCls : 'icon-search',
		onClick : function() {
			var navId=$("#navIdSearch").combobox("getValue");
			var tempId=$("#tempIdSearch").combobox("getValue");
			var params = {};
			if(navId){
				params.navId=navId;
			}else{
				$.messager.alert('提示信息', "请选择导航后再查询!");
				return;
			}
			if(tempId){
				params.tempId=tempId;
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
			$("#navIdSearch").combobox("unselect");
			$("#navIdSearch").combobox("setValue","");
			$("#tempIdSearch").combobox("unselect");
			$("#tempIdSearch").combobox("setValue","");
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
	$("#copy").linkbutton({
		disabled:true,
		plain : false,
		iconCls : 'icon-redo',
		onClick : function() {
			copy();
		}
	});

	addOrEditDialog.dialog({
		align : 'center',
		width : 500,
		height : 400,
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
			$("#isCoyp").val("");
		}
	});
	
	$("#dialogSave").linkbutton({
		iconCls : 'icon-save',
		plain : false,
		onClick : function() {
			if (!validata()) {
				return;
			}
			var id = $("#op_id").val();
			if (id) {
				if($("#isCoyp").val()){
					addOrUpdate("/operation_position/copy");
				}else{
					var status = $("#op_status").val();
					if(status==1){
						$.messager.confirm('警告', '您修改的是已上线数据，确认修改该数据吗？', function(yes) {
							if (yes) {
								addOrUpdate("/operation_position/update");
								return;
							}
						});
						return;
					}
					addOrUpdate("/operation_position/update");
				}
			} else {
				addOrUpdate("/operation_position/insert");
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
							urlSetObj.val(data.message);
							$.messager.alert('提示信息', "上传成功！", "", function() {
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
        onSelect: function(rec){
			$("#tempId").combobox("clear");
			var url = '/op_template/list_by_navId?navId='+rec.id;
			$('#tempId').combobox('reload', url);
		}
	}); 
	$("#tempId").combobox({
		prompt:"请选择模板",
		required : true,
		editable:false,
		valueField:'id',
		textField:'tempName',
		onLoadSuccess:function(){
//        	用来选中默认的下拉框值
			var id=$("#temp_id").val();
			if(id){
				$("#tempId").combobox("select",id);
			}else{
				$("#tempId").combobox("unselect");
			}
		},
	}); 
	
	$("#operationName").textbox({
		required:true,
		validType:['length[0,20]'],
	});
	$("#titleName").textbox({
		validType:['length[0,100]'],
	});
	$("#titleDetail").textbox({
		validType:['length[0,100]'],
	});
	$("#showTitle").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'0',
		data:[
		   {key:'0',value:'显示'},
		   {key:'1',value:'不显示'},
		]
	});
	$("#playTime").numberbox({
		required:true,
		min: 0,
		max: 999,
		value:0,
		editable: true,
	});
	$("#sortpos").numberbox({
		required:true,
		min: 0,
		max: 99,
		value:1,
		editable: true,
	});
	$("#showType").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'0',
		data:[
		   {key:'0',value:'图片'},
		   {key:'1',value:'视频'},
		   {key:'2',value:'特殊焦点'},
		   {key:'3',value:'倒影'},
		   {key:'4',value:'多张图片'},
		],
	});
	$("#focus").combobox({
		editable:false,
		required:true,
		panelHeight:"auto",
		valueField:'key',    
		textField:'value',
		value:'0',
		data:[
		      {key:'0',value:'获取'},
		      {key:'1',value:'不获取'},
		      ],
	});
	$("#topMargin").numberbox({
		required:true,
		min: 0,
		max: 9999,
		value:0,
		editable: true,
	});
	$("#leftMargin").numberbox({
		required:true,
		min: 0,
		max: 9999,
		value:0,
		editable: true,
	});
	$("#width").numberbox({
		required:true,
		min: 0,
		max: 9999,
		value:0,
		editable: true,
	});
	$("#height").numberbox({
		required:true,
		min: 0,
		max: 9999,
		value:0,
		editable: true,
	});
	$("#packageName").textbox({
		validType:['length[0,255]'],
	});
	$("#className").textbox({
		validType:['length[0,255]'],
	});
	$("#paramKey").textbox({
		validType:['length[0,255]'],
	});
	$("#paramValue").textbox({
		validType:['length[0,255]'],
	});
	$("#video").textbox({
		validType:['length[0,255]'],
	});
	$("#image").textbox({
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
		$("#op_status").val("");
		$('#groupId').combobox("readonly", false);
		$('#navId').combobox("readonly", false);
		$("#operationName").textbox("setValue","");
		$("#titleName").textbox("setValue","");
		$("#titleDetail").textbox("setValue","");
		$("#showTitle").combobox("select",'0');
		$("#playTime").numberbox("setValue",0);
		$("#sortpos").numberbox("setValue",1);
		$("#showType").combobox("select",'0');
		$("#focus").combobox("select",'0');
		$("#topMargin").numberbox("setValue",0);
		$("#leftMargin").numberbox("setValue",0);
		$("#width").numberbox("setValue",0);
		$("#height").numberbox("setValue",0);
		$("#packageName").textbox("setValue","");
		$("#className").textbox("setValue","");
		$("#paramKey").textbox("setValue","");
		$("#paramValue").textbox("setValue","");
		$("#video").textbox("setValue","");
		$("#image").textbox("setValue","");
		clearImage();
		addOrEditDialog.dialog("setTitle", "新增运营位信息");
	} else {
		var datagrid = $('#datagrid');
		var row = datagrid.datagrid("getSelected");
		addOrEditDialog.dialog("setTitle", "编辑运营位信息");
		$("#op_status").val(row.status);
		$("#op_id").val(row.id);
		$("#group_id").val(row.groupId);
		$("#nav_id").val(row.navId);
		$("#temp_id").val(row.tempId);
		$("#operationName").textbox("setValue",row.operationName);
		$("#titleName").textbox("setValue",row.titleName);
		$("#titleDetail").textbox("setValue",row.titleDetail);
		$("#showTitle").combobox("select",row.showTitle+'');
		$("#playTime").numberbox("setValue",row.playTime);
		$("#sortpos").numberbox("setValue",row.sortpos);
		$("#showType").combobox("select",row.showType+'');
		$("#focus").combobox("select",row.focus+'');
		$("#topMargin").numberbox("setValue",row.topMargin);
		$("#leftMargin").numberbox("setValue",row.leftMargin);
		$("#width").numberbox("setValue",row.width);
		$("#height").numberbox("setValue",row.height);
		$("#packageName").textbox("setValue",row.packageName);
		$("#className").textbox("setValue",row.className);
		var paramKey = row.paramKey;
		if(paramKey){
			paramKey = analysis(paramKey);
		}
		var paramValue = row.paramValue;
		if(paramValue){
			paramValue = analysis(paramValue);
		}
		$("#paramKey").textbox("setValue",paramKey);
		$("#paramValue").textbox("setValue",paramValue);
		$("#video").textbox("setValue",row.video);
		$("#image").textbox("setValue",row.image);
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
				url = "/operation_position/delete_batch";
				data.ids = JSON.stringify(ids);
			}else{
				url = "/operation_position/delete";
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
	var val=data.val();
	if(val){
		document.getElementById("echo").src=val;
	}
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
	var inputs=$("#imageTable input");
	var array = [];
	var jsonString;
	if(inputs.length>0){
		for (var i = 0; i < inputs.length; i++) {
			if($(inputs[i]).val()){
				array[i]=$(inputs[i]).val();
			}
			jsonString = JSON.stringify(array);
		}
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
				});
			} else {
				$.messager.alert('提示信息', data.message);
			}
		}
	});
}
function showPlayTimeFormatter(value,row,index){
	if(value>0){
		return value+"秒";
	}
	return 0+"秒";
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
				url:"/operation_position/upline_or_downline",
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
function showImage(value,row,index){
	if(value.length>0){
		return value.length+"张";
	}
	return "无";
}
function showShowTypeFormatter(value, row, index) {
	if (value == 0) {
		return "图片";
	} else if (value == 1) {
		return "视频";
	} else if (value == 2) {
		return "特殊焦点";
	} else if (value == 3) {
		return "倒影";
	} else if (value == 4) {
		return "多张图片";
	}
}
function showShowTitleFormatter(value, row, index) {
	if (value == 0) {
		return "显示";
	} else if (value == 1) {
		return "不显示";
	}
}
function showFocusFormatter(value, row, index){
	if (value == 0) {
		return "获取";
	} else if (value == 1) {
		return "不获取";
	}
}
function copy(){
	var datagrid = $('#datagrid');
	var row = datagrid.datagrid("getSelected");
	$.ajax({
		url:"/operation_position/query_by_id",
		type:"POST",
		data: {id:row.id},
		dataType:"json",
		success: function(data){
			if(data.success){
				var addOrEditDialog = $('#addOrEditDialog');
				addOrEditDialog.dialog("setTitle", "拷贝运营位信息");
				var obj = data.data;
				$("#isCoyp").val("copy");
				$("#op_id").val(obj.id);
				$("#group_id").val(obj.groupId);
				$("#nav_id").val(obj.navId);
				$("#temp_id").val(obj.tempId);
				$("#operationName").textbox("setValue",obj.operationName);
				$("#titleName").textbox("setValue",obj.titleName);
				$("#titleDetail").textbox("setValue",obj.titleDetail);
				$("#showTitle").combobox("select",obj.showTitle+'');
				$("#playTime").numberbox("setValue",obj.playTime);
				$("#sortpos").numberbox("setValue",obj.sortpos);
				$("#showType").combobox("select",obj.showType+'');
				$("#focus").combobox("select",obj.focus+'');
				$("#topMargin").numberbox("setValue",obj.topMargin);
				$("#leftMargin").numberbox("setValue",obj.leftMargin);
				$("#width").numberbox("setValue",obj.width);
				$("#height").numberbox("setValue",obj.height);
				$("#packageName").textbox("setValue",obj.packageName);
				$("#className").textbox("setValue",obj.className);
				var paramKey = obj.paramKey;
				if(paramKey){
					paramKey = analysis(paramKey);
				}
				var paramValue = obj.paramValue;
				if(paramValue){
					paramValue = analysis(paramValue);
				}
				$("#paramKey").textbox("setValue",paramKey);
				$("#paramValue").textbox("setValue",paramValue);
				$("#video").textbox("setValue",obj.video);
				$("#image").textbox("setValue",obj.image);
				var imageList = obj.imageList;
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
				$("#groupId").combobox("reload","/launcher_group/group_list_all");
				addOrEditDialog.dialog("open");
			}else{
				$.messager.alert('提示信息',data.message);
			}
		},
	});
}
function analysis(str){
	var value = str.substr(str.lastIndexOf('[')+1,str.length-2);
	var reg=new RegExp("\"","g");//g无意义
	value = value.replace(reg,"");
	return value;
}
