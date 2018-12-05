function GetRequest() {
	var url = decodeURI(decodeURI(location.search)); //获取url中"?"符后的字串
	var theRequest = new Object();
	if(url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
		}
	}
	return theRequest;
} 
function module(urlStr,channel,moduleSequence,tag) {
	$.ajax({
		type: "get",
		url: urlStr + "getChannelModuleList",
		async: true,
		timeout: 5000,  
		data: {
			"channel":channel
		},
		dataType: "json",
		success: function(data) {
			var result = data.data;
			var ulStr = '';
			var liStr1='',liStr2='',liStr3='',liStr4='',liStr5='';
			for(var i = 0; i < result.length; i++) {
				var datas = result[i].data;
				var moduleId=result[i].moduleId;
				var moduleName=result[i].moduleName;
				var mId=result[i].mId;
				var id=result[i].moduleName;
				ulStr += '<h3 sequence="' + result[i].moduleSequence + '"><p></p><span>' + result[i].moduleName + '</span></h3>';
				for(var k = 0; k < datas.length; k++) {
					var obj = datas[k].itemdate;
					var moban1= moban_1(k,obj,moduleId,mId,tag);
					var moban2= moban_2(k,obj,moduleId,mId,tag); 
					var moban3= moban_3(k,obj,moduleId,mId,tag);
					var moban4= moban_4(k,obj,moduleId,mId,tag); 
					var moban5= moban_5(k,obj,moduleId,mId,tag);
					if(moduleId === 1){
						liStr1 += moban1;
					}else if(moduleId === 2){
						liStr2 += moban2;
					}else if(moduleId === 3){
						liStr3 += moban3;
					}else if(moduleId === 4){
						liStr4 += moban4;					
					}else if(moduleId === 5){
						liStr5 += moban5;
					}
				}
				if(moduleId === 1){
					ulStr+='<ul>' + liStr1 + '</ul>';
					liStr1='';
				}else if(moduleId === 2){
					ulStr +='<ul>' + liStr2 + '</ul>';
					liStr3='';
				}else if(moduleId === 3){
					ulStr +='<ul>' + liStr3 + '</ul>';
					liStr3='';
				}else if(moduleId === 4){
					ulStr +='<ul>' + liStr4 + '</ul>';
					liStr4='';
				}else if(moduleId === 5){
					ulStr +='<ul>' + liStr5 + '</ul>';
					liStr5='';
				}
			}
			$("."+tag).html(ulStr);
			/*$("."+tag+">h3").each(function(i, ele) {
				var sequence=$(ele).attr("sequence");
				//console.log(sequence);
				if(moduleSequence === sequence){
					//alert('moduleSequence'+moduleSequence);
					//alert('sequence'+sequence);
    			  scrolltop=$(ele).offset().top;
    			  //alert(scrolltop);
    			  $('html,body').animate({scrollTop:scrolltop},'slow');
		    	}
			});*/
			var swiper = new Swiper('.pic_slide', {
				loop: true,
				centeredSlides: true,
				autoplay: {
					delay: 3000,
					disableOnInteraction: false,
				},
				pagination: {
					el: '.swiper-pagination',
					clickable: true,
				},
			});
		},error:function(){
			if(tag == "module2"){
				alert("访问线上接口getChannelModuleList ：出错！");
			}else{
				alert("访问接口出错！");
			}
		}
	});
}




//function edit(obj){
////window.parent.fatherFunction();
//var k=$(obj).attr("value");
//var moduleId=$(obj).attr("moduleId");
//var URL = '${pageContext.request.contextPath}';
//alert(URL);
//$.ajax({
//type: "get",
//url: "${pageContext.request.contextPath}/vod/tv/vodmouldinfo/getalbum/"+channel+"/"+moduleId+"/"+k,
//async: true,
//dataType: "json",
//success: function(data) {
//alert(1)
//}
//})
//}

function moban_1(k,obj,moduleId,mId,tag){
	var divtags='',liStr='';
	if(k === 0 && obj.length > 0) {
		for(var m = 0; m < obj.length; m++) {
			divtags += '<div class="swiper-slide" style="background:url('+obj[m].pictureurl2+') no-repeat center;background-size:100% 100%;">' +
			'<div class="bann" >' +
			'<div class="name">' + obj[m].seriesName + '</div>' +
			'<div class="point">' + (obj[m].viewPoint || '') + '</div>' +
			'</div>' +
			'</div>';
		}
		liStr += '<li class="slider_moban_1" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '><div class="swiper-container pic_slide">' +
		'<div class="swiper-wrapper">' + divtags + '</div>' +
		'<div class="swiper-pagination"></div>' +
		'</div></li>';
	} else {
		for(var j = 0; j < obj.length; j++) {
			liStr += '<li class="moban_1" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + obj[j].pictureurl2 + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'</div>' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || " ") + '</div>' +
			'</li>';
		}
	}
	return liStr;
}

function moban_2(k,obj,moduleId,mId,tag){
	var liStr='';var divtags='';
	if(k <= 1 && obj.length > 0) {
		for(var m = 0; m < obj.length; m++) {
			divtags += '<div class="swiper-slide" style="background:url('+obj[m].pictureurl2+') no-repeat center;background-size:100% 100%;">' +
			'<div class="bann" >' +
			'<div class="name">' +  obj[m].seriesName + '</div>' +
			'<div class="point">' + (obj[m].viewPoint || '') + '</div>' +
			'</div>' +
			'</div>';
		}
		liStr += '<li class="slider_moban_2" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '><div class="swiper-container pic_slide">' +
		'<div class="swiper-wrapper">' + divtags + '</div>' +
		'<div class="swiper-pagination"></div>' +
		'</div></li>';
	} else {
		for(var j = 0; j < obj.length; j++) {
			liStr += '<li class="moban_2" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'</div>' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || " ") + '</div>' +
			'</li>';
		}	
	}
	return liStr;
}

function moban_3(k,obj,moduleId,mId,tag){
	var liStr='';var divtags='';
	if(k <= 2 && obj.length > 0) {
		for(var m = 0; m < obj.length; m++) {
			divtags += '<div class="swiper-slide" style="background:url('+obj[m].pictureurl2+') no-repeat center;background-size:100% 100%;">' +
			'<div class="bann" >' +
			'<div class="name">' +  obj[m].seriesName + '</div>' +
			'<div class="point">' + (obj[m].viewPoint || '') + '</div>' +
			'</div>' +
			'</div>';
		}
		liStr += '<li class="slider_moban_3" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '><div class="swiper-container pic_slide">' +
		'<div class="swiper-wrapper">' + divtags + '</div>' +
		'<div class="swiper-pagination"></div>' +
		'</div></li>';
	} else {
		for(var j = 0; j < obj.length; j++) {
			liStr += '<li class="moban_3" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'</div>' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || " ") + '</div>' +
			'</li>';
		}	
	}
	return liStr;
}

function moban_4(k,obj,moduleId,mId,tag){
	var liStr='';var divtags='';
	if(k ===0 && obj.length > 0) {
		for(var m = 0; m < obj.length; m++) {
			divtags += '<div class="swiper-slide" style="background:url('+obj[m].pictureurl1+') no-repeat center;background-size:100% 100%;">' +
			'<div class="bann" >' +
			'<div class="name">' +  obj[m].seriesName + '</div>' +
			'<div class="point">' + (obj[m].viewPoint || '') + '</div>' +
			'</div>' +
			'</div>';
		}
		liStr += '<li class="slider_moban_4" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '><div class="swiper-container pic_slide">' +
		'<div class="swiper-wrapper">' + divtags + '</div>' +
		'<div class="swiper-pagination"></div>' +
		'</div></li>';
	} else {
		for(var j = 0; j < obj.length; j++) {
			liStr += '<li class="moban_4" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'</div>' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || " ") + '</div>' +
			'</li>';
		}	
	}
	return liStr;
}

function moban_5(k,obj,moduleId,mId,tag){
	var liStr='';var divtags='';
	var liStr='';var divtags='';
	if(k ===0 && obj.length > 0) {
		for(var m = 0; m < obj.length; m++) {
			divtags += '<div class="swiper-slide" style="background:url('+obj[m].pictureurl2+') no-repeat center;background-size:100% 100%;">' +
			'<div class="bann" >' +
			'<div class="name">' +  obj[m].seriesName + '</div>' +
			'<div class="point">' + (obj[m].viewPoint || '') + '</div>' +
			'</div>' +
			'</div>';
		}
		liStr += '<li class="slider_moban_5" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '><div class="swiper-container pic_slide">' +
		'<div class="swiper-wrapper">' + divtags + '</div>' +
		'<div class="swiper-pagination"></div>' +
		'</div></li>';
	}else {
		for(var j = 0; j < obj.length; j++) {
			liStr += '<li class="moban_5" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'</div>' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || " ") + '</div>' +
			'</li>';
		}
	}
	return liStr;
}