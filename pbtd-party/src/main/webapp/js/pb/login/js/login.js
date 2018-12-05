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
	var key  = CryptoJS.enc.Latin1.parse('abcdefghyjklmnob');
    var iv   = CryptoJS.enc.Latin1.parse('abcdefghyjklmnob');
    var data=$("#password").val();
	var encrypt= CryptoJS.AES.encrypt(data, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
	$("#password").val(encrypt);
//	解密
//	var decrypted = CryptoJS.AES.decrypt(encrypt,key,{iv:iv,padding:CryptoJS.pad.ZeroPadding});
//	console.log(decrypted.toString(CryptoJS.enc.Utf8));
	$.ajax({
        url: "login",
		type: 'post',
		data: $("#loginForm").serialize(),
        success: function (data) {
        	if(data.success){
        		window.location.href="index";
        	}else{
        		display("nameIsNull",false);
        		display("passwordIsNull",false);
        		$("#erroeInfo").text(data.message);
        		display("userError",true);
        		$("#password").val("");
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
function encrypt(data,key,iv) {
    return CryptoJS.AES.encrypt(data, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
}function decrypt(data,key,iv) {
    return CryptoJS.AES.decrypt(data, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
}