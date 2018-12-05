$('#mouidSynBtn').click(function(){
	var curid=$('#mouidSynBtn').attr("data-id");
	console.log(curid);
		if(curid!=null && curid!=""){
			$.ajax({
				url:'/vod/tv/vodmouldinfo/synMoudleInfo',
				type:'post',
				data:{
					curid:curid
				},
				success:function(data){
					if(data!=null){
						if(data.code==1){
							$.message({
								message:data.message,
								type:'success'
							});	
						}else if(data.code==0){
							$.message({
								message:data.message,
								type:'warning'
							});
						}
					}else{
						$.message({
							message:'同步失败',
							type:'error'
						});
					}
					
				}
				
			});
		}
		/*console.log("同步按钮:"+curid);*/
});
