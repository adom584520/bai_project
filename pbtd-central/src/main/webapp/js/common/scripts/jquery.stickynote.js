(function($) {
	var index= 0;
	$.fn.stickynote = function(options) {
		var opts = $.extend({}, $.fn.stickynote.defaults, options);
		return this.each(function() {
			$this = $(this);
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			switch(o.event){
				case 'dblclick':
					$this.dblclick(function(e){$.fn.stickynote.createNote(o);})
					break;
				case 'click':
					$this.click(function(e){
						if(index>0){
							jQuery.messager.alert('提示:','只能打开一个便签窗口!');
							return;
						}
						$.fn.stickynote.createNote(o);
					})
					break;
			}		
		});
	};
	$.fn.stickynote.defaults = {
		size 	: 'small',
		event	: 'click',
		color	: '#000000'
	};
	$.fn.stickynote.createNote = function(o) {
		var _note_content = $(document.createElement('textarea'));
		var _div_note 	= 	$(document.createElement('div'))
							.addClass('jStickyNote')
							.css('cursor','move');
		if(!o.text){
			_div_note.append(_note_content);
			var _div_create = $(document.createElement('div'))
						.addClass('jSticky-create')
						.css("font-size","15px")
						.attr('title','Create Sticky Note').html("保存");
		
			_div_create.click(function(e){
				var content = $(this).parent().find('textarea').val();
				 if(content!=null&&content!=""){
				 $.ajax({ 
						type: "post", 
						url: '../grbg/grbq/create',
						data: {"content":content} ,
						success: function(data, status, XHR) {
								if(data==0){
									top.$.messager.show({ title: "信息", msg: '便签添加成功!', timeout: 4000, showType: "slide" });
								}else{
			                    	top.$.messager.show({ title: "信息", msg: '便签添加失败!', timeout: 4000, showType: "slide" });
								}
								index -= 250;
	                   }
					}); 
				 }else{
					 jQuery.messager.alert('提示:','便签为空值不能保存!');
					 return;
				 }
				var _p_note_text = 	$(document.createElement('p'))
									.css('color',o.color)
									.html	(
											$(this)
											.parent()
											.find('textarea')
											.val()
											);
				index -= 250;
				$(this).parent().remove();
				$(this)
				.parent()
				.find('textarea')
				.before(_p_note_text)
				.remove(); 
				$(this).remove();				
				
			})
		}	
		else
			_div_note.append('<p style="color:'+o.color+'">'+o.text+'</p>');					
		
		var _div_delete = 	$(document.createElement('div'))
							.addClass('jSticky-delete');
		
		_div_delete.click(function(e){
			index -= 250;
			$(this).parent().remove();
		})
		var _div_wrap 	= 	$(document.createElement('div'))
							.css({'position':'absolute','bottom':'0','right':index})
							.append(_div_note)
							.append(_div_delete)
							.append(_div_create);	
		index+=250;
		switch(o.size){
			case 'large':
				_div_wrap.addClass('jSticky-large');
				break;
			case 'small':
				_div_wrap.addClass('jSticky-medium');
				break;
		}		
		if(o.containment){
			_div_wrap.draggable({ containment: '#'+o.containment , scroll: false ,start: function(event, ui) {
				if(o.ontop)
					$(this).parent().append($(this));
			}});	
		}	
		else{
			_div_wrap.draggable({ scroll: false ,start: function(event, ui) {
				if(o.ontop)
					$(this).parent().append($(this));
			}});	
		}
		$('#content').append(_div_wrap);
	};
})(jQuery);