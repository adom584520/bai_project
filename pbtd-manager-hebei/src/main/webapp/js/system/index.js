$(function(){
	var tree=$("#tree");
	var tabs=$("#tabs");
	tree.tree({
		url:"/menu/self",
		onDblClick:function(node){
			tree.tree("toggle",node.target);
		},
		onClick:function(node){
			if(node.url){
				if(tabs.tabs("exists",node.text)){
					//回到这个选项卡中
					tabs.tabs("select",node.text);
					return;
				}
				//否则创建
				tabs.tabs("add",{
					closable:true,
					title: node.text,
					fit:true,
					content:"<iframe src='"+node.url+"' style='width:100%;height:100%;' frameborder=0 ></iframe>"
				});
			}
		}
	});
	$("#logininfo_edit").menubutton({
		plain:false,
		menu: '#logininfo_edit_1',
		menuAlign:'left',
	});
	$("#selfInfoDialog").dialog({
		title:'账号信息修改',
		width:250,
		height:180,
		modal : true,
		closable : true,
		closed:true,
		buttons:'#selfInfoDialogButtons',
	});
	$("#editPasswordDialog").dialog({
		title:'修改密码',
		width:250,
		height:180,
		modal : true,
		closable : true,
		closed:true,
		buttons:'#editPasswordDialogButtons',
	});
});
function openSelfInfoDialog(){
	$("#username").textbox({
		required:true,
		validType:['length[3,10]'],
	});
	$("#realName").textbox({
		required:true,
		validType:['length[2,4]'],
	});
	$("#contactInformation").textbox({
		validType:['length[11,20]'],
	});
	$("#selfInfoDialogSave").linkbutton({
		iconCls:'icon-save',
		plain:false,
		onClick:function(){
			selfInfoSave();
		}
	});
	$("#selfInfoDialogCancel").linkbutton({
		iconCls:'icon-cancel',
		plain:false,
		onClick:function(){
			$("#selfInfoDialog").dialog("close");
		}
	});
	$("#username").textbox("setValue",$("#usernameCache").val());
	$("#realName").textbox("setValue",$("#realNameCache").val());
	$("#contactInformation").textbox("setValue",$("#contactInformationCache").val());
	$("#selfInfoDialog").dialog("open");
}
function selfInfoSave(){
	$('#selfInfoForm').form('submit', {
		url:'/loginInfo/self_info_update',
	    success: function(data){
	    	var data = $.parseJSON(data);
	    	console.log(data);
	    	if(data.success){
	    		$.messager.alert('提示信息', "修改成功！请重新登录！", "", function() {
	    			window.location.href="/"; 
				});
	    	}else{
	    		$.messager.alert('提示信息', data.message);
	    	}
	    }    
	});
}
function openEditPasswordDialog(){
	$("#oldPassword").passwordbox({
		required:true,
		validType:['length[6,20]'],
	});
	$("#newPassword").passwordbox({
		required:true,
		validType:['length[6,20]'],
	});
	$("#confirmPassword").passwordbox({
		required:true,
		validType:['length[6,20]'],
	});
	$("#editPasswordDialogSave").linkbutton({
		iconCls:'icon-save',
		plain:false,
		onClick:function(){
			editPasswordSave();
		}
	});
	$("#editPasswordDialogCancel").linkbutton({
		iconCls:'icon-cancel',
		plain:false,
		onClick:function(){
			$("#editPasswordDialog").dialog("close");
		}
	});
	$("#editPasswordDialog").dialog("open");
}
function editPasswordSave(){
	var newPassword=$("#newPassword").textbox("getValue");
	var confirmPassword=$("#confirmPassword").textbox("getValue");
	$('#editPasswordForm').form('submit', {
		url:'/loginInfo/update_password',
	    success: function(data){
	    	var data = $.parseJSON(data);
	    	console.log(data);
	    	if(data.success){
	    		$.messager.alert('提示信息', "修改成功！请重新登录！", "", function() {
	    			window.location.href="/"; 
				});
	    	}else{
	    		$.messager.alert('提示信息', data.message);
	    	}
	    }    
	});
	
}
