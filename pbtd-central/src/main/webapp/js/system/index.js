$(function(){
	var tree=$("#tree");
	var tabs=$("#tabs");
	tree.tree({
		url:"/menu_list",
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
});
