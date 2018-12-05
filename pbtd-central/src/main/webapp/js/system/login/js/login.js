$(function(){
	$("#username").blur( function() {
		var username = $("#username")[0].value;
		if(username== null || username==""){
			display("nameIsNull",true);
		}else{
			display("nameIsNull",false);
		}
	});
	$("#password").blur( function() {
		var password = $("#password")[0].value;
		if(password== null || password==""){
			display("passwordIsNull",true);
			return;
		}else{
			display("passwordIsNull",false);
		}
	});
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			login();
		}
	});
})
function login(){
	$.ajax({
        url: "login",
		type: 'post',
		data: $("#loginForm").serialize(),
        success: function (data) {
        	if(data.success){
        		window.location.href="/index";
        	}else{
        		display("nameIsNull",false);
        		display("passwordIsNull",false);
        		$("#erroeInfo").text(data.message);
        		display("userError",true);
        	}
        }
    });
}

function loginValidate(){
	display("nameIsNull",false);
	display("passwordIsNull",false);
	display("userError",false);
	var username = $("#username")[0].value
	var password = $("#password")[0].value;
	if(username== null || username==""){
		display("nameIsNull",true);
		return;
	}
	if(password== null || password==""){
		display("passwordIsNull",true);
		return;
	}
	login();
}

//隐藏或显示，第一个为标签id，第二个为是否显示，true为显示，false为不显示
function display(id,value){
	if(value){
		$("#"+id+"")[0].style.display="block";
		return;
	}
	$("#"+id+"")[0].style.display="none";
	
}