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
	var requesturl = "";
	if (urlStr.indexOf("phone") >= 0){
		requesturl = urlStr+ "getphoneChannelModuleList"
		}else {
			requesturl = urlStr+ "getChannelModuleList"
		}
	//console.log("aaaaaa:"+requesturl+channel);
	$.ajax({
		type: "get",
		url:requesturl,
		async: true,
		timeout: 50000,  
		data: {
			"channel":channel,
			"pageSize":100
		},
		dataType: "json",
		success: function(data) {
			var result = data.data;
			var ulStr = '';
			var atag ='';
			var liStr1='',liStr2='',liStr3='',liStr4='',liStr5='',liStr6='',liStr7='',liStr8='',liStr9='',liStr10='',liStr11='',liStr12='',liStr13='',liStr14='';
			for(var i = 0; i < result.length; i++) {
				var datas = result[i].data;
				var moduleId=result[i].moduleId;
				var moduleName=result[i].moduleName;
				var mId=result[i].mId;
				var id=result[i].moduleName;
				var img=result[i].textrecommendpic;
				var isshow_left = result[i].isShowLeft;
				var isshow_right = result[i].isShowRight;
				ulStr += '<h3 sequence="' + result[i].moduleSequence + '"><p></p><span>' + result[i].moduleName + '</span></h3>';
				for(var k = 0; k < datas.length; k++) {
					var obj = datas[k].itemdata;
					var moban1= moban_1(k,obj,moduleId,mId,tag);
					var moban2= moban_2(k,obj,moduleId,mId,tag); 
					var moban3= moban_3(k,obj,moduleId,mId,tag);
					var moban4= moban_4(k,obj,moduleId,mId,tag); 
					var moban5= moban_5(k,obj,moduleId,mId,tag);
					var moban6= moban_6(k,obj,moduleId,mId,tag);
					var moban7= moban_7(k,obj,moduleId,mId,tag); 
					var moban8= moban_8(k,obj,moduleId,mId,tag);
					var moban9= moban_9(k,obj,moduleId,mId,tag); 
					var moban10= moban_10(k,obj,moduleId,mId,tag);
					var moban11= moban_11(k,obj,moduleId,mId,tag);
					var moban12= moban_12(k,obj,moduleId,mId,tag); 
					var moban13= moban_13(k,obj,moduleId,mId,tag);
					var moban14= moban_14(k,obj,moduleId,mId,tag);
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
					}else if(moduleId === 100){
						liStr6 += moban6;
					}else if(moduleId === 101){
						liStr7 += moban7;
					}else if(moduleId === 102){
						liStr8 += moban8;
					}else if(moduleId === 103){
						liStr9 += moban9;
					}else if(moduleId === 104){
						liStr10 += moban10;
					}else if(moduleId === 105){
						liStr11 += moban11;
					}else if(moduleId === 106){
						liStr12 += moban12;
					}else if(moduleId === 107){
						liStr13 += moban13;
					}else if(moduleId === 108){
						liStr14 += moban14;
					}
				}
				if(moduleId === 1){
					ulStr+='<ul>' + liStr1 + '</ul>';
					liStr1='';
				}else if(moduleId === 2){
					ulStr +='<ul>' + liStr2 + '</ul>';
					liStr2='';
				}else if(moduleId === 3){
					ulStr +='<ul>' + liStr3 + '</ul>';
					liStr3='';
				}else if(moduleId === 4){
					ulStr +='<ul>' + liStr4 + '</ul>';
					liStr4='';
				}else if(moduleId === 5){
					ulStr +='<ul>' + liStr5 + '</ul>';
					liStr5='';
				}else if(moduleId === 100){
					ulStr +='<ul>' + liStr6 + '</ul>';
					liStr6='';
				}else if(moduleId === 101){
					ulStr +='<ul>' + liStr7 + '</ul>';
					liStr7='';
				}else if(moduleId === 102){
					ulStr +='<ul>' + liStr8 + '</ul>';
					liStr8='';
				}else if(moduleId === 103){
					ulStr +='<ul>' + liStr9 + '</ul>';
					liStr9='';
				}else if(moduleId === 104){
					ulStr +='<ul>' + liStr10 + '</ul>';
					liStr10='';
				}else if(moduleId === 105){
					ulStr +='<ul>' + liStr11 + '</ul>';
					liStr11='';
				}else if(moduleId === 106){
					ulStr += '<div class="swiper-container adSider" id="adSider">' +
					'<div class="swiper-wrapper">' + liStr12 + '</div></div>';
					liStr12='';
				}else if(moduleId === 107){
					ulStr +='<ul>' + liStr13 + '</ul>';
					liStr13='';
				}else if(moduleId === 108 && img!= null){
					ulStr += '<h3 sequence="' + result[i].moduleSequence + '"><img src="'+img+'" /><span>' + result[i].moduleName + '</span></h3>'+
					      '<ul>' + liStr14 + '</ul>';
					liStr14='';
				}
				if(isshow_left > 0 && isshow_right > 0) {
					ulStr += '<div class="atag">' +
						'<small><span>更多视频</span> <img src="/js/bossModule/img/more.png" /></small>' +
						'<small><span>换一批</span> <img src="/js/bossModule/img/change.png" /></small>' +
						'</div>';
				} else if(isshow_left > 0 && isshow_right == 0) {
					ulStr += '<div class="atag">' +
						'<small><span>更多视频</span> <img src="/js/bossModule/img/more.png" /></small>' +
						'</div>';
				} else if(isshow_right > 0 && isshow_left == 0) {
					ulStr += '<div class="atag">' +
						'<small><span>换一批</span> <img src="/js/bossModule/img/change.png" /></small>' +
						'</div>';
				}
			}
			$("."+tag).html(ulStr);
			swiperImg('.pic_slide');
			swiperImg(".adSider");
		}
		,error:function(){
			if(tag == "module2"){
				alert("访问线上接口getChannelModuleList ：出错！");
			}else{
				alert("访问接口出错！");
			}
		}
	});
}

function modulephone(urlStr,channel,moduleSequence,tag) {
	var requesturl = "";
	requesturl = urlStr+ "getChannelModuleList"
	//console.log(requesturl+channel);
	$.ajax({
		type: "get",
		url:requesturl,
		async: true,
		timeout: 50000,  
		data: {
			"channel":channel,
			"pageSize":100
		},
		dataType: "json",
		success: function(data) {
			var result = data.data;
			var ulStr = '';
			var atag ='';
			var liStr1='',liStr2='',liStr3='',liStr4='',liStr5='',liStr6='',liStr7='',liStr8='',liStr9='',liStr10='',liStr11='',liStr12='',liStr13='';
			for(var i = 0; i < result.length; i++) {
				var datas = result[i].data;
				var moduleId=result[i].moduleId;
				var moduleName=result[i].moduleName;
				var mId=result[i].mId;
				var id=result[i].moduleName;
				var img=result[i].textrecommendpic;
				var isshow_left = result[i].isShowLeft;
				var isshow_right = result[i].isShowRight;
				ulStr += '<h3 sequence="' + result[i].moduleSequence + '"><p></p><span>' + result[i].moduleName + '</span></h3>';
				for(var k = 0; k < datas.length; k++) {
					var obj = datas[k].itemdata;
					var moban1= moban_1(k,obj,moduleId,mId,tag);
					var moban2= moban_2(k,obj,moduleId,mId,tag); 
					var moban3= moban_3(k,obj,moduleId,mId,tag);
					var moban4= moban_4(k,obj,moduleId,mId,tag); 
					var moban5= moban_5(k,obj,moduleId,mId,tag);
					var moban6= moban_6(k,obj,moduleId,mId,tag);
					var moban7= moban_7(k,obj,moduleId,mId,tag); 
					var moban8= moban_8(k,obj,moduleId,mId,tag);
					var moban9= moban_9(k,obj,moduleId,mId,tag); 
					var moban10= moban_10(k,obj,moduleId,mId,tag);
					var moban11= moban_11(k,obj,moduleId,mId,tag);
					var moban12= moban_12(k,obj,moduleId,mId,tag); 
					var moban13= moban_13(k,obj,moduleId,mId,tag);
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
					}else if(moduleId === 100){
						liStr6 += moban6;
					}else if(moduleId === 101){
						liStr7 += moban7;
					}else if(moduleId === 102){
						liStr8 += moban8;
					}else if(moduleId === 103){
						liStr9 += moban9;
					}else if(moduleId === 104){
						liStr10 += moban10;
					}else if(moduleId === 105){
						liStr11 += moban11;
					}else if(moduleId === 106){
						liStr12 += moban12;
					}else if(moduleId === 107){
						liStr13 += moban13;
					}
				}
				if(moduleId === 1){
					ulStr+='<ul>' + liStr1 + '</ul>';
					liStr1='';
				}else if(moduleId === 2){
					ulStr +='<ul>' + liStr2 + '</ul>';
					liStr2='';
				}else if(moduleId === 3){
					ulStr +='<ul>' + liStr3 + '</ul>';
					liStr3='';
				}else if(moduleId === 4){
					ulStr +='<ul>' + liStr4 + '</ul>';
					liStr4='';
				}else if(moduleId === 5){
					ulStr +='<ul>' + liStr5 + '</ul>';
					liStr5='';
				}else if(moduleId === 100){
					ulStr +='<ul>' + liStr6 + '</ul>';
					liStr6='';
				}else if(moduleId === 101){
					ulStr +='<ul>' + liStr7 + '</ul>';
					liStr7='';
				}else if(moduleId === 102){
					ulStr +='<ul>' + liStr8 + '</ul>';
					liStr8='';
				}else if(moduleId === 103){
					ulStr +='<ul>' + liStr9 + '</ul>';
					liStr9='';
				}else if(moduleId === 104){
					ulStr +='<ul>' + liStr10 + '</ul>';
					liStr10='';
				}else if(moduleId === 105){
					ulStr +='<ul>' + liStr11 + '</ul>';
					liStr11='';
				}else if(moduleId === 106){
					ulStr += '<div class="swiper-container adSider" id="adSider">' +
					'<div class="swiper-wrapper">' + liStr12 + '</div></div>';
					liStr12='';
				}else if(moduleId === 107){
					ulStr +='<ul>' + liStr13 + '</ul>';
					liStr13='';
				}else if(moduleId === 108 && img!= null){
					ulStr += '<h3 sequence="' + result[i].moduleSequence + '"><img src="'+img+'" /><span>' + result[i].moduleName + '</span></h3>';
				}
				if(isshow_left > 0 && isshow_right > 0) {
					ulStr += '<div class="atag">' +
						'<small><span>更多视频</span> <img src="/js/bossModule/img/more.png" /></small>' +
						'<small><span>换一批</span> <img src="/js/bossModule/img/change.png" /></small>' +
						'</div>';
				} else if(isshow_left > 0 && isshow_right == 0) {
					ulStr += '<div class="atag">' +
						'<small><span>更多视频</span> <img src="/js/bossModule/img/more.png" /></small>' +
						'</div>';
				} else if(isshow_right > 0 && isshow_left == 0) {
					ulStr += '<div class="atag">' +
						'<small><span>换一批</span> <img src="/js/bossModule/img/change.png" /></small>' +
						'</div>';
				}
			}
			$("."+tag).html(ulStr);
			swiperImg('.pic_slide');
			swiperImg(".adSider");
		}
		,error:function(){
			if(tag == "module2"){
				alert("访问线上接口getChannelModuleList ：出错！");
			}else{
				alert("访问接口出错！");
			}
		}
	});
}

function swiperImg(tag){
	var bannerImgswiper = new Swiper(tag, {
		loop: true,
		centeredSlides: true,
		autoplay: {
			delay: 3000,
			disableOnInteraction: false,
		},
		pagination: {
			el: tag+' .swiper-pagination',
			clickable: true,
		},
        observer:true,
        observeParents:true,
        onInit: function(swiper){ //Swiper2.x的初始化是onFirstInit
	       	swiperAnimateCache(swiper); //隐藏动画元素 
		    swiperAnimate(swiper); //初始化完成开始动画
	    }, 
	    onSlideChangeEnd: function(swiper){ 
	        swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
	    }
	});
 }
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
function moban_6(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		//liStr += '<li class="moban_6">' +
		liStr += '<li class="moban_6" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'</li>';
	}
	return liStr;
}
function moban_7(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		//liStr += '<li class="lis">' +
		liStr += '<li class="lis" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + obj[j].pictureurl2 + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'<div class="txt">' + (obj[j].viewPoint || ". ") + '</div>' +
		'</li>';
	}
	return liStr;
}
function moban_8(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		//liStr += '<li class="lis typeStyle">' +
		liStr += '<li class="lis typeStyle" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl2) + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'<div class="txt">' + (obj[j].viewPoint || ". ") + '</div>' +
		'</li>';
	}
	return liStr;
}
function moban_9(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		if(k === 0){
			//liStr += '<li class="typeStyle">' +
			liStr += '<li class="typeStyle" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl1) + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			'<div class="txt">' + (k == 0 ? obj[j].viewPoint : "") + '</div>' +
			'</li>';
		}else{
			//liStr += '<li class="moban_6">' +
			liStr += '<li class="moban_6" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl1) + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || "") + '</div>' +
			'</li>';
		}
		
	}
	return liStr;
}
function moban_10(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		//liStr += '<li class="lis typeStyle">' +
		liStr += '<li class="lis typeStyle" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl2) + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'<div class="txt">' + (obj[j].viewPoint || ". ") + '</div>' +
		'</li>';
	}
	return liStr;
}
function moban_11(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		//liStr += '<li class="moban_6">' +
		liStr += '<li class="moban_6" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'</li>';
	}
	return liStr;
}
function moban_12(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		liStr += '<div class="swiper-slide"  moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<img src="' + obj[j].pictureurl4 + '" />' +
		'</div>';
	}
	return liStr;
}
function moban_13(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		if(k === 0){
			//liStr += '<li class="typeStyle">' +
			liStr += '<li class="typeStyle" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl1) + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			'<div class="txt">' + (k == 0 ? obj[j].viewPoint : "") + '</div>' +
			'</li>';
		}else{
			//liStr += '<li class="moban_6">' +
			liStr += '<li class="moban_6" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
			'<div class="module_img">' +
			'<img class="imgTag" src="' + (k == 0 ? obj[j].pictureurl4 : obj[j].pictureurl1) + '">' +
			'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
			'<div class="text">' + obj[j].seriesName + '</div>' +
			//'<div class="txt">' + (obj[j].viewPoint || "") + '</div>' +
			'</li>';
		}
		
	}
	return liStr;
}
function moban_14(k,obj,moduleId,mId,tag){
	var liStr='';
	for(var j = 0; j < obj.length; j++) {
		liStr += '<li class="moban_6" seriesCode="' + obj[j].seriesCode + '" value="'+k+'" moduleId="'+moduleId+'" mId="'+mId+'"';if(tag == "module"){liStr += 'onclick="edit(this);"';} liStr += '>' +
		'<div class="module_img">' +
		'<img class="imgTag" src="' + obj[j].pictureurl1 + '">' +
		'<p>' + (obj[j].volumncount == obj[j].currentnum ? obj[j].volumncount + "集全" : "更新至" + obj[j].currentnum + "集") + '</p >' +
		'<div class="text">' + obj[j].seriesName + '</div>' +
		'</li>';
	}
	return liStr;
}