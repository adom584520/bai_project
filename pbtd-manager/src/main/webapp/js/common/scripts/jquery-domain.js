/**
 * validatebox, timespinner: name and visible
 * combobox, combotree, datebox & datetimebox: comboname and hidden
 * numberspinner: numberboxname and visible
 * numberbox: numberboxname and hidden
 */
(function($) {
	
    /**
     * 收集参数
     */
    function collect(target) {
    	var collection = {};
        var t = $(target);
        t.find(":input[name]").not(":button,:reset,:image,:submit").each(function(ndx, ele) {
        	var box = $(this);
            var key = $.trim(box.attr("name"));
            var val =$.trim(box.val());
          
            if (key === "") {
            	return true;
            }
            if (typeof(collection[key]) == "undefined") {
            	collection[key] = val ;
            } else {
            	if($.isArray(collection[key])) {
            		collection[key].push(val);
            	} else {
            		collection[key] = [collection[key],  val];
            	}
            }
        });
        return collection; 
    }
    /**
     * 关闭窗口
     */
    function closeDialog(target, options) {
    	target.$("#" + window.location.pathname.replace(new RegExp("\/", "g"), "_")).dialog("close");
    }
	/*
	 * 打开窗口
	 */
    function openDialog(target, options) {
        var opts = $.extend({}, $.fn.domain.defaults.openDialog, options);
        var did = opts.src.substring(0, opts.src.indexOf("?") > -1 ? opts.src.indexOf("?") : undefined).replace(new RegExp("\/", "g"), "_");
        var dlg = target.$("<div style='overflow:auto'><iframe scrolling='auto' frameborder='0' style='width:100%;height:100%;' src='about:blank'></iframe></div>").appendTo(target.document.body);
        dlg.dialog($.extend({}, opts, {
        	id: did,
            onOpen: function() {
                dlg.dialog("body").find("iframe").attr("src", opts.src);
                opts.onOpen.call(target);
            },
            onClose: function() {
                dlg.dialog("destroy");
                opts.onClose.call(target);
            }
        })).dialog("open");
    }
    /**
     * 设置宽度
     */
    function resize(target) {
    	var t = $(target);
    	if(t.attr("autoTypeset") != "true") {
    		return;
    	}
    	//若已设置宽度
    	if($.data(target, "resized") == true) {
    		return;
    	}
    	var columnSize = t.attr("columnSize") ? parseInt(t.attr("columnSize"), 10) : 2;
    	var wwhl = Math.floor((t.width() - 24) / columnSize);
    	t.find(":input[name]:visible").each(function(ndx, dom) {
    		var box = $(dom);
    		var pan = box.parent();
    		var min = (pan.outerWidth(true) - pan.width()) + (box.outerWidth(true) - box.width()) + (pan.width() - box.outerWidth(true));    		
    		var wth = wwhl - min;
    		if(box.is(":radio") || box.is(":checkbox")) {
    			pan.width(wth + (box.outerWidth(true) - box.width()) + (pan.width() - box.outerWidth(true)));
    			pan.height(pan.height() + 2);
    		}
    		else {
    			box.width(wth);
    		}
    	});
    	//标志已经设置宽度
    	$.data(target, "resized", true);
    }
    /**
     * 生成控件
     */
    function build(target) {
    	//若已生成控件
    	if($.data(target, "builded") == true) {
    		return;
    	}
    	var t = $(target);
    	var fn;
    	if(t.attr("queryFunction")) {
    		fn = eval(t.attr("queryFunction"));
    	}
    	t.find(":input[name]:visible").each(function(ndx, dom) {
    		var box = $(dom);
    		//将要转换为combobox/combotree的dom对象
			if (box.hasClass("easyui-combobox-disable") || box.hasClass("easyui-combotree-disable")) {
				var type = box.hasClass("easyui-combobox-disable") ? "combobox" : "combotree";
				box[type]({
                    url: null,
                    onChange: function(nv, ov) {
                    	cascadeHandler(dom);
                    	eventHandler(dom, fn);
                    },
                    onLoadSuccess: function() {
                    	if(box[type]("options").url) {
                    		$.data(dom, "loaded", true);
                    	}
                    },
                    onShowPanel: function() {
                    	if(typeof($.data(dom, "loaded")) == "undefined" || $.data(dom, "loaded") == false) {
                    		var tri = box.attr("trigger");//触发器
                    		var ign = box.attr("ignore") == "true";
                    		var url = $.parser.parseOptions(dom, ["url"]).url;
                    		if(tri && url) {
                    			/*var val = t.find(":input[name='" + tri + "']").val();//触发器值
                    			if(($.trim(val) == "" || parseInt(val, 10) < 0) && ign == false) {
                            		url = null;
                            	}
                    			else {
                    				url = url + (url.indexOf("?") == -1 ? "?" : "&") + tri + "=" + val;
                    			}
                    			*/
                    			var vals = [];
                    			t.find(":input[name='" + tri + "']").each(function(i, d) {
                    				var val = $.trim($(this).val());
                    				if (val == "" || parseInt(val, 10) < 0) {
                    					return true;
                    				}
                    				vals.push(val);
                    			});
                    			if (vals.length == 0 && ign == false) {
                    				url = null;
                    			}
                    			else {
                    				var params = {};
                    				params[tri] = vals;
                    				url = url + (url.indexOf("?") == -1 ? "?" : "&") + $.param(params);
                    			}
                    		}
                    		if(url) {
                    			box[type]("reload", url);
                    		}
                    	}
                    },
                    onSelect: function(node) {
                    	var opts = box[type]("options");
                    	if (opts.multiple) {
                    		if (type == "combobox") {
                    			if (node[opts.valueField] == "") {
                    				var data = box.combobox("getData");
                    				for (var i = 0; i < data.length; i++) {
                    					if (data[i] == node) {
                    						continue;
                    					}
                    					box.combobox("unselect", data[i][opts.valueField]);
                    				}
                    				//box.combo("hidePanel");
                    			} else {
                    				box.combobox("unselect", "");
                    			}
                    		}
                    		else if (type == "combotree") {
                    			if (node.id == "") {
                    				var tree = box.combotree("tree");
                    				var checkeds = tree.tree("getChecked");
                    				for (var i = 0; i < checkeds.length; i++) {
                    					tree.tree("uncheck", checkeds[i].target);
                    				}
                    			}
                    		}
                    	}
                    }
                });
			}
			//将要转换为datebox/datetimebox的dom对象
			else if(box.hasClass("easyui-datebox-disable") || box.hasClass("easyui-datetimebox-disable")) {
				box.width(box.width() + 2);
				var c = box.hasClass("easyui-datebox-disable") ? "datebox" : "datetimebox";
				box[c]({
                    onSelect: function(date) {
                    	eventHandler(dom, fn);
                    }
                });
			}
			//将要转换为numberspinner/timespinner的dom对象
			else if(box.hasClass("easyui-numberspinner-disable") || box.hasClass("easyui-timespinner-disable")) {
				box.css({"borderWidth": 0}).width(box.width() + 2);
				var c = box.hasClass("easyui-numberspinner-disable") ? "numberspinner" : "timespinner";
				box[c]({
					onSpinUp: function() {
						eventHandler(dom, fn);
					},
					onSpinDown: function() {
						eventHandler(dom, fn);
					}
				});
				box.unbind("keyup").bind("keyup", {}, function(event) {
                    if (event.keyCode == 13) {
                    	box.blur();
                    	eventHandler(dom, fn);
                    }
                });
			}
			//将要转换为numberbox/validatebox的dom对象
			else if(box.hasClass("easyui-numberbox-disable") || box.hasClass("easyui-validatebox-disable")) {
				var c = box.hasClass("easyui-numberbox-disable") ? "numberbox" : "validatebox";
				box[c]();
				box.unbind("keyup").bind("keyup", {}, function(event) {
                    if (event.keyCode == 13) {
                    	box.blur();
                    	eventHandler(dom, fn);
                    }
                });
			}
			//不作转换的select对象，一般不会有这种情况
			else if(box.is("select")) {
				box.unbind("change").bind("change", {}, function(event) {
					cascadeHandler(dom);
                });
			}
			//不作转换的input[type=text]对象，一般不会有这种情况
			else if(box.is(":text")) {
				box.unbind("keyup").bind("keyup", {}, function(event) {
                    if (event.keyCode == 13) {
                    	box.blur();
                    	eventHandler(dom, fn);
                    }
                });
			}
    	});
    	//标志已经生成控件
    	$.data(target, "builded", true); 
    	//事件处理函数
		function eventHandler(target, fn) {
			if(!fn) {
				return;
			}
			var t = $(target);
			if(t.attr("autoQuery") == "false") {
				return;
			}
			window.setTimeout(function() { fn(); }, 0);
		}
		//级联处理函数
        function cascadeHandler(target) {
        	var origin = $(target);
        	var name = origin.attr("comboname") || origin.attr("numberboxname") || origin.attr("name");
        	if(!name) {
        		return;
        	}
        	var box = t.find(":input[trigger='" + name + "']");
        	if(box.length == 0) {
        		return;
        	}
        	if(box.hasClass("easyui-combobox-disable") || box.hasClass("easyui-combotree-disable")) {
        		var type = box.hasClass("easyui-combobox-disable") ? "combobox" : "combotree";
        		box[type]("clear");
            	box[type]("loadData", []);
        	}
        	box.each(function(ndx, ele) {
        		$.data(ele, "loaded", false);
        	});
        }
    }
    /**
     * 填充数据
     */
    function fill(target, data) {
    	var t = $(target);
    	for (var key in data) {
    		if(data[key] == null) {
    			data[key] = "";
    			continue;
    		}
    		var box = t.find(":input[name='" + key + "']");
			if(box.is(":hidden") && typeof(box.attr("id")) == "undefined") {
				box = t.find(":input[numberboxname='" + key + "'], :input[comboname='" + key + "']");
			}
			var dataType = box.hasClass("date") ? "date" : (box.hasClass("number") ? "number" : "text");
			if(dataType == "number") {
				data[key] = $.modifiers.formatNumber(data[key]);
			}
			else if(dataType == "date") {
				
			}
    	}
		t.form("load", data);
		t.find(":input.validatebox-invalid").removeClass("validatebox-invalid");
    	t.find(":input[focused='true']:visible:enabled:first").focus(); 
    }
    /**
     * 装载表单
     */
    function load(target, options) {
    	//设置宽度
    	resize(target);
    	//生成表单
    	build(target);
    	var t = $(target);
    	if(options && options.url && new RegExp("/(-?\d+)").test(options.url)) {
    		var opts =  $.extend({}, $.fn.domain.defaults.load, {}, options);
    		t.before("<div class='panel-loading'>loading...</div>");
            $.ajax({
                async: opts.async,
                cache: opts.cache,
                type: opts.type,
                url: opts.url,
                dataType: opts.dataType,
    			beforeSend: function(XHR) {
               	 	return opts.onBeforeLoad.call(target, XHR); 
                },
                success: function(data, status, XHR) {
                    //显示表单
                    t.prev().remove();
                    t.css({ visibility: "visible" });   
                    //回填数据
                    fill(target, data);
                	//命名Combo
                	if(opts.names && opts.names.length > 0) {
                    	for(var i = 0; i < opts.names.length; i++) {
                    		var item = opts.names[i];
                    		var fKeyField = item.fKeyField;
                    		if(!fKeyField) {
                    			continue;
                    		}
                    		var b = t.find(':input[comboname="' + fKeyField + '"]');
                			if(b.length == 0) {
                				continue;
                			}
                			b.domain('nameCombo', item);
                    	}
                    }
                	//函数回调
                	window.setTimeout(function() { opts.onLoadSuccess.call(target, data, status, XHR); }, 0);
                },
                error: function(XHR, status, errorThrow) {
                    t.prev().remove();
                    t.css({ visibility: "visible" });
                    top.$.messager.alert("信息", XHR.responseText, "500");
                    opts.onLoadError.call(target, XHR, status, errorThrow);
                },
                complete: function(XHR, status) {
                    opts.onLoadComplete.call(target);
                }
            });
    	}
    	else {
    		fill(target, options);
    	}
    }
    /**
     * 判断记录是否存在
     */
    function exist(target, options) {
        var opts = $.extend({}, $.fn.domain.defaults.exist, {}, options);
        if (!opts.url) {
            return false;
        }
    	var exist = false;
    	var t = $(target);
    	var boxes = t.find(":input[unique='true']").not(":button,:reset,:image,:submit");
        if (boxes.length == 0) {
            return false;
        }
        var queryParams = opts.queryParams || {};
        boxes.each(function(ndx, ele) {
            var box = $(this);
            var key = box.attr("comboname") || box.attr("numberboxname");
            if (key) {
                box = t.find(":input[name='" + key + "']").not(":button,:reset,:image,:submit");
            }
            var vals = [];
            box.each(function(i, d) {
            	var b = $(this);
            	var v = $.trim(b.val());
            	vals.push( v );
            })
            queryParams[box.attr("name")] = (vals.length == 1 ? vals[0] : vals);
        });
        exist = $.ajax({
			url : opts.url,
			dataType : opts.dataType,
			data : queryParams,
			async : opts.async,
			cache : opts.cache,
			type : opts.type,
			beforeSend: function(XHR) {
           	 	return opts.onBeforeLoad.call(target, XHR);
            },
			success: function(data, status, XHR) {
				if(data > 0) {
					var val = [];
					for(var key in queryParams) {
						val.push(decodeURIComponent(queryParams[key]));
					}
					var m = $.format("很抱歉，<font style='color:red'>{0}</font> {1}已存在。", val.join(","), opts.title);
		        	top.$.messager.show({ title: "错误", msg: m, timeout: 4000, showType: "slide" });
					t.find(":input[unique='true']:first").focus();
					t.find(":input[unique='true']:first").select();
		        }
            }, 
			error: function(XHR, status, errorThrow) {
                top.$.messager.alert("信息", XHR.responseText, "500");
                opts.onLoadError.call(target, XHR, statusText, errorThrow);
            },
            complete: function(XHR, status) {
                opts.onLoadComplete.call(target);
            }
		}).responseText;
        
    	return exist > 0;
    }
    function formatData(target, data, name) {
    	if(data == null) {
			return null;
		}
    	if($.isArray(data)) {//数组
			var builder = [];
			for (var i = 0; i < data.length; i++) {
                builder.push(formatData(target, data[i], name));
            }
			return builder;
		}
    	else if($.isPlainObject(data)) {//对象
			var builder = {};
            for (var key in data) {
            	builder[key] = formatData(target, data[key], key);
            }
            return builder;
		}
		else if(typeof(data) == "string" || typeof(data) == "number") {//字符串或数字
			var box = $(target).find(":input[comboname='" + name + "']").not(".datebox-f, .datetimebox-f");
 			if(box.length > 0) {
 				var text = box.combo("getText");
 				data = data + "/Text(" + text + ")" + "/";
 			}
    		return data;
		}
		else {//其它
			return data;
		}
    }
    /**
     * 创建
     */
    function create(target, options) {
    	var state = $.data(target, "domain.create");
        if (state) {
            $.extend(state.options, options);
        }
        else {
            state = $.data(target, "domain.create", { options: $.extend({}, $.fn.domain.defaults.create, {}, options) });
        }
        var opts = state.options;
        if (!opts.url) {
            return;
        }
        $.ajax({
            async: opts.async,
            cache: opts.cache,
            type: opts.type,
            url: opts.url,
            data: opts.data,
            dataType: opts.dataType,
            beforeSend: function(XHR) {
            	return opts.onBeforeLoad.call(target, XHR);
            },
            success: function(data, statusText, XHR) {
            	data = $.extend({}, $.decodeURIComponent(opts.data), { id: data });
            	data = formatData(target, data);
            	var m = $.format("恭喜您，添加 <font style='color:red'>{0}</font> {1}成功。", (data.code || data.name || ""), opts.title);
                top.$.messager.show({ title: "信息", msg: m, timeout: 4000, showType: "slide" });
                if (window.parent != window.self) {
                    var win = window.parent;
                    if (win.$("#tabs").length == 1) {
                        win = win.$("#tabs").tabs("getSelected").panel("body").find("iframe")[0].contentWindow;
                    }
                    if (win) {
                        win.$.data(win.document.body, "domain.create.refresh", true);
                    }
                }
                //
                window.setTimeout(function() { opts.onLoadSuccess.call(target, data, status, XHR); }, 800);
            },
            error: function(XHR, statusText, errorThrow) {
                top.$.messager.alert("信息", XHR.responseText, "500");
                opts.onLoadError.call(target, XHR, statusText, errorThrow);
            },
            complete: function(XHR, status) {
            	opts.onLoadComplete.call(target, XHR, status);
            }
        });
    }
    /**
     * 编辑
     */
    function edit(target, options) {
    	var state = $.data(target, "domain.create");
        if (state) {
            $.extend(state.options, options);
        }
        else {
            state = $.data(target, "domain.create", { options: $.extend({}, $.fn.domain.defaults.create, {}, options) });
        }
        var opts = state.options;
        if (!opts.url) {
            return;
        }
        $.ajax({
            async: opts.async,
            cache: opts.cache,
            type: opts.type,
            url: opts.url,
            data: opts.data,
            dataType: opts.dataType,
            beforeSend: function(XHR) {
           	 	return opts.onBeforeLoad.call(target, XHR);
            },
            success: function(data, statusText, XHR) {
            	data = $.decodeURIComponent(opts.data);
            	data = formatData(target, data);
            	var m = $.format("恭喜您，编辑 <font style='color:red'>{0}</font> {1}成功。", (data.code || data.name || ""), opts.title);
                top.$.messager.show({ title: "信息", msg: m, timeout: 4000, showType: "slide" });
                window.setTimeout(function() { opts.onLoadSuccess.call(target, data, statusText, XHR); }, 800);
            },
            error: function(XHR, statusText, errorThrow) {
                top.$.messager.alert("信息", XHR.responseText, "500");
                opts.onLoadError.call(target, XHR, statusText, errorThrow);
            },
            complete: function(XHR, status) {
            	opts.onLoadComplete.call(target);
            }
        });
    }
    /**
     * 删除一条，treegrid和tree使用
     */
    function remove(target, options) {
    	var opts = $.extend({}, $.fn.domain.defaults.remove, {}, options);
    	if(!opts.url) {
            return;
        }
    	var t = $(target);
    	var c = $.data(target, "tree") ? "tree" : ($.data(target, "treegrid") ? "treegrid" : undefined);
    	if(!c) {
    		return;
    	}
    	var title = opts.title || opts.subject;
    	var node = t[c]("getSelected");
    	if (!node) {
            top.$.messager.alert("信息", "很抱歉，尚未选择 " + title + " 。", "info", null, 2000);
            return;
        }
    	var idField = "id", textField = "text";
    	if(c == "treegrid") {
    		idField = t.treegrid("options").idField;
    		textField = t.treegrid("options").treeField;
    	}
    	var id = node[idField], text = node[textField];
    	var m = $.format("即将删除 <font style='color:red'>{0}</font> {1}及其下{2}，且不能恢复，确定吗？", text, title, title);
    	top.$.messager.confirm("确认", m, function(result) {
            if (result) {
                $.ajax({
                    async: opts.async,
                    cache: opts.cache,
                    type: opts.type,
                    url: opts.url + "/" + id,
                    data: {id: id},
                    dataType: opts.dataType,
                    beforeSend: function(XHR) {
                    	return opts.onBeforeLoad.call(target, XHR);
                    },
                    success: function(data, status, XHR) {
                    	m = $.format("恭喜您，删除 <font style='color:red'>{0}</font> {1}及其下{2}成功。", text, title, title);
                        top.$.messager.show({ title: "信息", msg: m, timeout: 4000, showType: "slide" });
                        window.setTimeout(function() {
                        	if(c == "tree") {
                            	t.tree("remove", t.tree('find', id).target);
                            }
                            else if(c == "treegrid") {
                            	t.treegrid("remove", id);
                            }
                        	opts.onLoadSuccess.call(target, data, status, XHR); 
                        }, 800);
                    },
                    error: function(XHR, status, errorThrow) {
                        opts.onLoadError.call(target, XHR, status, errorThrow);
                        top.$.messager.alert("信息", XHR.responseText, "500");
                    }
                });
            }
        });
    }
    /**
     * 删除多条，datagrid使用
     */
    function deletes(target, options) {
    	var opts = $.extend({}, $.fn.domain.defaults.deletes, {}, options);
    	if(!opts.url) {
            return;
        }
    	var t = $(target);
    	var title = opts.title || opts.subject;
    	var nodes = t.datagrid("getSelections");
    	if (!nodes || nodes.length == 0) {
            top.$.messager.alert("信息", "很抱歉，尚未选择 " + title + " 。", "info", null, 2000);
            return;
        }
    	var idField = t.datagrid("options").idField;
    	var ids = [];
        for (var i = 0; i < nodes.length; i++) {
            ids.push(nodes[i][idField]);
        }
        var m = $.format("即将删除选中的 <font style='color:red'>{0}</font> 条{1}，且不能恢复，确定吗？", ids.length, title);
        top.$.messager.confirm("确认", m, function(result) {
            if (result) {
                $.ajax({
                    async: opts.async,
                    cache: opts.cache,
                    type: opts.type,
                    url: opts.url,
                    contentType: opts.contentType,
                    data: $.serialize(ids),
                    dataType: opts.dataType,
                    success: function(data, status, XHR) {
                    	m = $.format("恭喜您，删除 <font style='color:red'>{0}</font> 条{1}成功。", ids.length, title);
                        top.$.messager.show({ title: "信息", msg: m, timeout: 4000, showType: "slide" });
                        window.setTimeout(function() {
                        	/*var queryParams = $.data(target, "domain.datagrid").options.queryParams || {};
                			queryParams.total = count(target);
                            datagrid(target, { queryParams: queryParams });
                            */
                        	t.datagrid("reload");
                        	opts.onLoadSuccess.call(target, data, status, XHR);
                        }, 800);
                    },
                    error: function(XHR, status, errorThrow) {
                        opts.onLoadError.call(target, XHR, status, errorThrow);
                        top.$.messager.alert("信息", XHR.responseText, "500");
                    }
                });
            }
        });
    }
    /**
     * 统计符合查询条件的记录总数
     */
    function count(target, options) {
    	var state = $.data(target, "domain.count");
        if (state) {
            $.extend(state.options, options);
        }
        else {
            state = $.data(target, "domain.count", { options: $.extend({}, $.fn.domain.defaults.count, {}, options) });
        }
        var opts = state.options;
        if (!opts.url) {
            return;
        }
    	var total = $.ajax({
			url : opts.url,
			dataType : opts.dataType,
			data : opts.queryParams,
			async : opts.async,
			cache : opts.cache,
			type : opts.type,
			success: function(data, status, XHR) {
                window.setTimeout(function() { opts.onLoadSuccess.call(target, data, status, XHR); }, 0);
            }, 
			error: function(XHR, status, errorThrow) {
				window.setTimeout(function() { opts.onLoadError.call(target, XHR, status, errorThrow); }, 0);
                top.$.messager.alert("信息", XHR.responseText, "500");
            }
		}).responseText;
    	return total;
    }
    /**
     * 列举符合查询条件的记录
     */
    function datagrid(target, options) {
    	var state = $.data(target, "domain.datagrid");
        if (state) {
            $.extend(state.options, options);
        }
        else {
            state = $.data(target, "domain.datagrid", { options: $.extend({}, $.fn.domain.defaults.datagrid, $.fn.domain.parseOptions.datagrid(target), options) });
        }
        var opts = state.options;
        if(!opts.url) {
        	return;
        }
        var title = opts.title || opts.subject;
        $(target).datagrid($.extend({}, opts, {
        	onBeforeLoad: function(XHR) {
                return opts.onBeforeLoad.call(target, XHR);
            },
        	onLoadSuccess: function(data, status, XHR) {
        		var t = $(target);
        		if (data && data.rows && data.rows.length > 0) {
                    t.datagrid("clearSelections");
                    if(opts.names && opts.names.length > 0) {
                    	for(var i = 0; i < opts.names.length; i++) {
                			t.domain('nameGrid', $.extend({}, opts.names[i], {rows: data.rows}));
                    	}
                    }
                }
                else {
                    top.$.messager.show({ title: "信息", msg: "很抱歉，没有 " + title + " 。", timeout: 4000, showType: "slide" });
                }
                var panel = t.datagrid("getPanel");
                panel.find(">div.datagrid-view").unbind("click").bind("click", function(e) {
                    t.datagrid("clearSelections");
                }).find(">div>div.datagrid-header").unbind("click").bind("click", function(e) {
                	e.stopPropagation();
                });
                opts.onLoadSuccess.call(target, data, status, XHR);
        	},
        	onLoadError: function(XHR, status, errorThrow) {
                top.$.messager.alert("信息", XHR.responseText, "500");
                opts.onLoadError.call(target, XHR, status, errorThrow);
            },
            onSortColumn: function(sort, order) {
                $(this).datagrid("options").sortName = sort.replace(new RegExp("[A-Z]", "g"), function($1, i) { return (i == 0 ? $1 : "_" + $1); });
                opts.onSortColumn.call(target, sort, order);
            },
            onSelect: function(rowIndex, rowData) {
            	changeButtonState(target);
                opts.onSelect.call(target, rowIndex, rowData);
            },
            onUnselect: function(rowIndex, rowData) {
            	changeButtonState(target);
                opts.onUnselect.call(target, rowIndex, rowData);
            },
            onSelectAll: function(rows) {
            	changeButtonState(target);
                opts.onSelectAll.call(target, rows);
            },
            onUnselectAll: function(rows) {
            	changeButtonState(target);
                opts.onUnselectAll.call(target, rows);
            },
            onRowContextMenu: function(e, rowIndex, rowData) {
            	e.preventDefault();
            	var t = $(target);
            	var opts = t.datagrid("options");
            	var i = 0;
            	var rows = t.datagrid("getSelections");
            	for(i = 0; i < rows.length; i++) {
            		if(rows[i][opts.idField] == rowData[opts.idField]) {
            			break;
            		}
            	}
            	if(i >= rows.length) {
            		t.datagrid("unselectAll");
            	}
            	t.datagrid("selectRow", rowIndex);
            	
            	var rowContextMenu = createRowContextMenu(target);
            	rowContextMenu.menu("show", {
            		left:e.pageX,
            		top:e.pageY
            	});
            },
            onHeaderContextMenu: function(e, field){
            	e.preventDefault();
            	var headerContextMenu = createHeaderContextMenu(target);
            	headerContextMenu.menu("show", {
            		left:e.pageX,
            		top:e.pageY
            	});
            }
        }));
    }
    /**
     * 
     */
    function createHeaderContextMenu(target) {
    	var t = $(target);
    	var opts = t.datagrid("options");
		var headerContextMenu = opts.headerContextMenu;
		if(!headerContextMenu) {
			var buffer = [];
			buffer.push("<div style=\"width:120px;\">");
			var fields = t.datagrid("getColumnFields");  
			for(var i = 0; i < fields.length; i++) {
				var field = fields[i];
				var col = t.datagrid("getColumnOption", field);
				var iconCls = (col.hidden == true ? "icon-empty": "icon-tick");
				var disabled = (field.toLowerCase() == "code" || field.toLowerCase() == "name") ? true : false;
				buffer.push($.format("<div data-options=\"name:'{0}',iconCls:'{1}',disabled:{2}\"><span>{3}</span></div>", field, iconCls, disabled, col.title));
			}
			buffer.push("</div>");
			headerContextMenu = $(buffer.join("")).appendTo("body"); 
			headerContextMenu.menu({
    			onShow: function() {
    				
            	},
            	onHide: function() {
            		
            	},
    			onClick: function(item) {
    				if(item.iconCls == "icon-tick") {
    					t.datagrid("hideColumn", item.name);
    					headerContextMenu.menu('setIcon', { target: item.target, iconCls: 'icon-empty' });
    				}
    				else {
    					t.datagrid('showColumn', item.name);
    					headerContextMenu.menu('setIcon', { target: item.target, iconCls: 'icon-tick' });
    				}
    			}
    		});
    		opts.headerContextMenu = headerContextMenu;
		}
		return headerContextMenu;
    }
    /**
     * 
     */
    function createRowContextMenu(target) {
    	var t = $(target);
    	var opts = t.datagrid("options");
		var rowContextMenu = opts.rowContextMenu;
		if(!rowContextMenu) {
			var buffer = [];
			buffer.push("<div style=\"width:120px;\">");
    		var btns = opts.toolbar;
    		for(var i = 0; i < btns.length; i++) {
    			var btn = btns[i];	
    			if(btn == "-") {
    				buffer.push("<div class=\"menu-sep\"></div>");
    			}
    			else {
    				var name = btn.id.replace("btn", "");
    				name = name.substring(0, 1).toLowerCase() + name.substring(1);
    				buffer.push($.format("<div data-options=\"name:'{0}',iconCls:'{1}',disabled:{2}\"><span>{3}</span></div>", name, btn.iconCls, btn.disabled, btn.text));
    			}
    		}
    		buffer.push("</div>");
    		rowContextMenu = $(buffer.join("")).appendTo("body"); 
    		rowContextMenu.menu({
    			onShow: function() {
    				var btns = opts.toolbar;
            		for(var i = 0; i < btns.length; i++) {
            			var btn = btns[i];	
            			if(btn == "-") {
            				continue;
            			}
            			var item = rowContextMenu.menu("findItem", btn.text);
            			if(btn.disabled == true) {
            				rowContextMenu.menu("disableItem", item.target);
            			}
            			else {
            				rowContextMenu.menu("enableItem", item.target);
            			}
            		}
            	},
            	onHide: function() {
            		
            	},
    			onClick: function(item) {
    				var id = "btn" + item.name.substring(0, 1).toUpperCase() + item.name.substring(1);
    				$("#" + id).trigger("click");
    			}
    		});
    		opts.rowContextMenu = rowContextMenu;
		}
		return rowContextMenu;
	}
    /**
     * 
     */
    function changeButtonState(target) {
    	var t = $(target);
    	var length = t.datagrid("getSelections").length;
    	var btns = t.datagrid("options").toolbar;
    	for(var i = 0; i < btns.length; i++) {
    		var btn = btns[i];
    		if(btn == "-") {
    			continue;
    		}
    		var scope = btn.scope;
    		var b = $("#" + btn.id);
    		if(typeof(scope) == "undefined" || scope == "all") {
    			b.linkbutton("enable");
    			btn.disabled = false;
    		}
    		else if(typeof(scope) == "string") {
    			b.linkbutton("disable");
    			btn.disabled = true;
    			if(length == 0) {
    				if(scope.indexOf("none") != -1) {
    					b.linkbutton("enable");
    	    			btn.disabled = false;
    				}
    			}
    			else if(length == 1) {
    				if(scope.indexOf("one") != -1) {
    					b.linkbutton("enable");
    	    			btn.disabled = false;
    				}
    			}
    			else if(length >= 2) {
    				if(scope.indexOf("more") != -1) {
    					b.linkbutton("enable");
    	    			btn.disabled = false;
    				}
    			}
    		}
    	}
    	/*
    	var t = $(target);
        var panel = t.datagrid("getPanel");
        var length = t.datagrid("getSelections").length;
        panel.find(">div.datagrid-toolbar>a").each(function() {
            var btn = $(this);
            var bid = btn.attr("id");
            if (bid != "btnCreate" && bid != "btnDelete") {
                btn.linkbutton(length == 0 || length > 1 ? "disable" : "enable");
            }
            else if (bid == "btnDelete") {
                btn.linkbutton(length == 0 ? "disable" : "enable");
            }
        });
        */
    }
    /**
     * 
     */
    function treegrid(target, options) {
    	var state = $.data(target, "domain.treegrid");
        if (state) {
            $.extend(state.options, options);
        }
        else {
            state = $.data(target, "domain.treegrid", { options: $.extend({}, $.fn.domain.defaults.treegrid, $.fn.domain.parseOptions.treegrid(target), options) });
        }
        var opts = state.options;
        if(!opts.url) {
        	return;
        }
        var title = opts.title || opts.subject;
        $(target).treegrid($.extend({}, opts, {
        	onBeforeLoad: function(XHR) {
                return opts.onBeforeLoad.call(target, XHR);
            },
        	onLoadSuccess: function(node, data, status, XHR) {
        		var t = $(target);
        		if(data && data.length > 0) {
        			if(opts.names && opts.names.length > 0) {
                    	for(var i = 0; i < opts.names.length; i++) {
                			t.domain('nameGrid', $.extend({}, opts.names[i], {rows: data}));
                    	}
                    }
        		}
        		else {
        			top.$.messager.show({ title: "信息", msg: "很抱歉，没有 " + title + " 。", timeout: 4000, showType: "slide" });
                }
                var panel = t.datagrid("getPanel");
                panel.find(">div.datagrid-view").unbind("click").bind("click", function(e) {
                    t.datagrid("clearSelections");
                }).find(">div>div.datagrid-header").unbind("click").bind("click", function(e) {
                	e.stopPropagation();
                });
                opts.onLoadSuccess.call(target, data, status, XHR);
        	},
        	onLoadError: function(XHR, status, errorThrow) {
                top.$.messager.alert("信息", XHR.responseText, "500");
                opts.onLoadError.call(target, XHR, status, errorThrow);
            },
            onSelect: function(rowIndex, rowData) {
            	changeButtonState(target);
                opts.onSelect.call(target, rowIndex, rowData);
            },
            onUnselect: function(rowIndex, rowData) {
            	changeButtonState(target);
                opts.onUnselect.call(target, rowIndex, rowData);
            },
            onSelectAll: function(rows) {
            	changeButtonState(target);
                opts.onSelectAll.call(target, rows);
            },
            onUnselectAll: function(rows) {
            	changeButtonState(target);
                opts.onUnselectAll.call(target, rows);
            },
            onContextMenu: function(e, row) {
            	e.preventDefault();
            	var t = $(target);
            	var opts = t.treegrid("options");
            	var i = 0;
            	var rows = t.treegrid("getSelections");
            	for(i = 0; i < rows.length; i++) {
            		if(rows[i][opts.idField] == row[opts.idField]) {
            			break;
            		}
            	}
            	if(i >= rows.length) {
            		t.treegrid("unselectAll");
            	}
            	t.treegrid("select", row[opts.idField]);
            	var rowContextMenu = createRowContextMenu(target);
            	rowContextMenu.menu("show", {
            		left:e.pageX,
            		top:e.pageY
            	});
            },
            onHeaderContextMenu: function(e, field){
            	e.preventDefault();
            	var headerContextMenu = createHeaderContextMenu(target);
            	headerContextMenu.menu("show", {
            		left:e.pageX,
            		top:e.pageY
            	});
            }
        }));
    }
    /**
     * combobox,combotree使用
     */
    function nameCombo(target, options) {
    	var opts = $.extend({}, $.fn.domain.defaults.nameCombo, {}, options);
    	if(!opts.url) {
            return;
        }
    	var idField = getUrlParameterValue("idField", opts.url);
    	var nameField = getUrlParameterValue("nameField", opts.url);
    	if(!idField || !nameField) {
    		return;
    	}
    	var t = $(target);
    	var values = t.combo("getValues");
    	if(!values || values.length == 0) {
    		return;
    	}
    	var queryParams = opts.queryParams || {};
    	queryParams[idField] = values;
    	$.ajax({
            async: opts.async,
            cache: opts.cache,
            type: opts.type,
            url: opts.url,
            data: queryParams,
            dataType: opts.dataType,
            success: function(data, status, XHR) {
            	if(data && data.length > 0) {
            		var texts = [];
            		var separator = t.combo("options").separator;
            		for(var i = 0; i < data.length; i++) {
            			texts.push(data[i][nameField]);
            		}
            		t.combo("setText", texts.join(separator));
            	}
                window.setTimeout(function() { opts.onLoadSuccess.call(target, data, status, XHR); }, 800);
            },
            error: function(XHR, status, errorThrow) {
                opts.onLoadError.call(target, XHR, status, errorThrow);
                top.$.messager.alert("信息", XHR.responseText, "500");
            }
        });
    }
    function getUrlParameterValue(key, url) {
    	var reg = new RegExp("\\?(?:.+&)?" + key + "=(.*?)(?:&.*)?$");
        var mch = url.match(reg);
        return mch ? mch[1] : "";
    }
    /**
     * datagrid, treegrid使用。
     */
    function nameGrid(target, options) {
    	var opts = $.extend({}, $.fn.domain.defaults.nameGrid, {}, options);
    	if(!opts.url || !opts.fKeyField || !opts.rows) {
            return;
        }
    	var idField = getUrlParameterValue("idField", opts.url);
    	var nameField = getUrlParameterValue("nameField", opts.url);
    	if(!idField || !nameField) {
    		return;
    	}
    	var t = $(target);
    	var rows = opts.rows;
    	var values = [];
    	for (var i = 0; i < rows.length; i++) {
    		var fKeyValue = rows[i][opts.fKeyField];
            if ($.inArray(fKeyValue, values) == -1) {
            	values.push(fKeyValue);
            }
    	}
    	if(values.length == 0) {
    		return;
    	}
    	var queryParams = opts.queryParams || {};
    	queryParams[idField] = values;
    	$.ajax({
            async: opts.async,
            cache: opts.cache,
            type: opts.type,
            url: opts.url,
            data: queryParams,
            dataType: opts.dataType,
            success: function(data, status, XHR) {
            	data = data || [];
            	var divs = t.datagrid("getPanel").find("div.datagrid-body").find("td[field='" + opts.fKeyField + "']>div");
            	for(var j = 0; j < divs.length; j++) {
            		var div = $(divs[j]);
            		var val = div.text();
            		var mch = false;
            		for (var i = 0; i < data.length; i++) {
            			var item = data[i]; 
            			if (item[idField] == val) {
                        	div.text(item[nameField]);
                        	mch = true;
                        	break;
                        }
            		}
            		if(!mch) {
            			div.html("");
            		}
            	}
                window.setTimeout(function() { opts.onLoadSuccess.call(target, data, status, XHR); }, 800);
            },
            error: function(XHR, status, errorThrow) {
                opts.onLoadError.call(target, XHR, status, errorThrow);
                top.$.messager.alert("信息", XHR.responseText, "500");
            }
        });
    }
    //
    $.fn.domain = function(options, param) {
        if (typeof options == "string") {
            var fn = $.fn.domain.methods[options];
            if (fn) {
                return fn(this, param);
            }
        }
    };
    //
    $.fn.domain.methods = {
    	collect: function(jq) {
    		return collect(jq[0]);
    	},
		openDialog: function(jq, param) {
			return jq.each(function() {
				openDialog(this, param);
			});
        },
        closeDialog: function(jq, param) {
			return jq.each(function() {
				closeDialog(this, param);
			});
        },
        load: function(jq, param) {
        	return jq.each(function() {
				load(this, param);
			});
        },
        exist: function(jq, param) {
            return exist(jq[0], param);
        },
        create: function(jq, param) {
        	return jq.each(function() {
        		create(this, param);
			});
        },
        edit: function(jq, param) {
        	return jq.each(function() {
        		edit(this, param);
			});
        },
        remove: function(jq, param) {
        	return jq.each(function() {
        		remove(this, param);
			});
        },
        deletes: function(jq, param) {
        	return jq.each(function() {
        		deletes(this, param);
			});
        },
        count: function(jq, param) {
            return count(jq[0], param);
        },
        datagrid: function(jq, param) {
        	return jq.each(function() {
        		datagrid(this, param);
			});
        },
        treegrid: function(jq, param) {
        	return jq.each(function() {
        		treegrid(this, param);
			});
        },
        nameCombo: function(jq, param) {
        	return jq.each(function() {
        		nameCombo(this, param);
			});
        },
        nameGrid: function(jq, param) {
        	return jq.each(function() {
        		nameGrid(this, param);
			});
        }
    };
    $.fn.domain.parseOptions = {
        datagrid: function(target) {
            return $.extend({}, 
            		$.fn.datagrid.parseOptions(target), 
            		$.parser.parseOptions(target, ["urlCreate", "urlEdit", "urlDelete", "urlAttatch", {openWidth: "number", openHeight: "number"}])
            );
        },
        treegrid: function(target) {
            return $.extend({}, 
            		$.fn.treegrid.parseOptions(target), 
            		$.parser.parseOptions(target, ["urlCreate", "urlEdit", "urlDelete", "urlAttatch", {openWidth: "number", openHeight: "number"}])
            );
        }
    };
    //
    $.fn.domain.defaultsOfCommon = {
		async: true,
        cache: false,
        type: "GET",
        url: null,
        contentType: "application/json; charset=utf-8",
        data: null,
        dataType: "json",
        onBeforeLoad: function(XHR) { return true; },
        onLoadSuccess: function(data, status, XHR) {},
        onLoadError: function(XHR, status, errorThrow) {},
        onLoadComplete: function(XHR, status) {}	
    };
    //
    $.fn.domain.defaults = {
		openDialog: $.extend({}, $.fn.dialog.defaults, {
            modal: true,
            collapsible: true,
            minimizable: false,
            maximizable: true,
            closed: true,
            src: null
        }),
        load: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	names: null
        }),
        exist: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	async: false,
        	title: "记录"
        }),
        create: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	type: "POST",
        	title: "记录"
        }),
        edit: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	type: "POST",
        	title: "记录"
        }),
        remove: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	type: "POST",
        	title: "记录"
        }),
        deletes: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	type: "POST",
        	title: "记录"
        }),
        count: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	async: false,
        	queryParams: null
        }),
        datagrid: $.extend({}, $.fn.datagrid.defaults, {
            iconCls: "icon-grid",
            nowrap: true,
            striped: true,
            fit: true,
            border: false,
            pagination: true,
            pagePosition: "both",
            rownumbers: true,
            autoRowHeight: false,
            collapsible: false,
            remoteSort: true,
			singleSelect: false,
            cache:false,
            method: "get",
            //sortName: "id",
            //sortOrder: "asc",
            pageNumber: 1,
            pageSize: 10,
            idField: "id",
            frozenColumns: [[{
            	field: "ck", checkbox: true
            }]],
            toolbar: [{
            	id: "btnCreate",
                text: "新建",
                iconCls: "icon-add",
                disabled: false,
                handler: function() {
                	var t = $(this).parent().next().find(">table:hidden");
                    var opts = t.datagrid("options");
                    var title = opts.title || opts.subject;
                    var url = opts.urlCreate || (opts.url.match(new RegExp("page", "i")) ? opts.url.replace(new RegExp("page", "i"), "create") : undefined);
                    if (!url) {
                    	return;
                    }
                    $(parent).domain("openDialog", { 
                    	iconCls: "icon-add", 
                    	title: "新建 " + title, 
                    	src: url, 
                    	width: opts.openWidth, 
                    	height: opts.openHeight, 
                    	onClose: function() {
                    		if ($.data(document.body, "domain.create.refresh")) {
                    			$.removeData(document.body, "domain.create.refresh");
                    			//var queryParams = $.data(t[0], "domain.datagrid").options.queryParams || {};
                    			//queryParams.total = count(t[0]);
                                //datagrid(t[0], { queryParams: queryParams });
                    			//t.datagrid("reload", queryParams);
                    			t.datagrid("reload");
                            }
                        }
                    });
                },
                scope: "all"
            },{
                id: "btnEdit",
                text: "编辑",
                iconCls: "icon-edit",
                disabled: true,
                handler: function() {
                    var t = $(this).parent().next().find(">table:hidden");
                    var opts = t.datagrid("options");
                    var title = opts.title || opts.subject;
                    var node = t.datagrid("getSelected");
                    if (!node) {
                        top.$.messager.alert("信息", "很抱歉，尚未选择 " + title + " 。", "info", null, 2000);
                        return;
                    }
                    var id = node[opts.idField];
                    var url = (opts.urlEdit ? $.format(opts.urlEdit, id) : undefined) || (opts.url.match(new RegExp("page", "i")) ? opts.url.replace(new RegExp("page", "i"), "edit") + "/" + id : undefined);
                    if (!url) {
                        return;
                    }               
                    $(parent).domain("openDialog", { 
                    	iconCls: "icon-edit", 
                    	title: "编辑 " + title, 
                    	src: url, 
                    	width: opts.openWidth, 
                    	height: opts.openHeight, 
                    	onClose: function() {}
                    });
                },
                scope: "one"
            },
            "-",
            {
                id: "btnDelete",
                text: "删除",
                iconCls: "icon-remove",
                disabled: true,
                handler: function() {
                	var t = $(this).parent().next().find(">table:hidden");
                	var opts = t.datagrid("options");
                	var title = opts.title || opts.subject;
                	var url = opts.urlDelete || (opts.url.match(new RegExp("page", "i")) ? opts.url.replace(new RegExp("page", "i"), "deletes") : undefined);
                	t.domain("deletes", { 
                    	url: url,
                    	title: title
                    });
                },
                scope: "one,more"
            }],
            names: null,
            subject: "记录",
            urlCreate: null,
            urlEdit: null,
            urlDelete: null,
            urlAttatch: null,
            openWidth: 600,
            openHeight: 360,
            auto: true
        }),
        treegrid: $.extend({}, $.fn.treegrid.defaults, {
        	iconCls: "icon-grid",
            nowrap: true,
            striped: true,
            fit: true,
            border: false,
            rownumbers: true,
            autoRowHeight: false,
            collapsible:false,
			singleSelect: true,
			animate: true,
            cache:false,
            method: "get",
            //sortName: "id",
            //sortOrder: "asc",
            idField: "id",
            /*treeField: "name",
            frozenColumns: [[{
            	field: "name", title: "名称", width: 150
            }]],*/
            toolbar: [{
            	id: "btnCreate",
                text: "新建",
                iconCls: "icon-add",
                disabled: false,
                handler: function() {
                	var t = $(this).parent().next().find(">table:hidden");
                    var opts = t.treegrid("options");
                    var title = opts.title || opts.subject;
                    var url = opts.urlCreate || (opts.url.match(new RegExp("treegrid", "i")) ? opts.url.replace(new RegExp("treegrid", "i"), "create") : undefined);
                    if (!url) {
                    	return;
                    }
                    url += (url.indexOf("?") == -1 ? "?" : "&") + "parent={0}&ancestor={1}&depth={2}";
                    var node = t.treegrid('getSelected');
                    url = $.format(url, (node ? node[opts.idField] : 0), (node ? (node.ancestor ? node.ancestor + "," : "") + "_" + node[opts.idField] + "_" : ""), (node ? parseInt(node.depth, 10) + 1 : 1));
                    $(parent).domain("openDialog", { 
                    	iconCls: "icon-add", 
                    	title: "新建 " + title, 
                    	src: url, 
                    	width: opts.openWidth, 
                    	height: opts.openHeight,
                    	onClose: function() {
                    		if ($.data(document.body, "domain.create.refresh")) {
                    			$.removeData(document.body, "domain.create.refresh");
                    			t.treegrid("reload", (node ? node[opts.idField] : undefined));
                            }
                        }
                    });
                },
                scope: "all"
            },{
                id: "btnEdit",
                text: "编辑",
                iconCls: "icon-edit",
                disabled: true,
                handler: function() {
                    var t = $(this).parent().next().find(">table:hidden");
                    var opts = t.treegrid("options");
                    var title = opts.title || opts.subject;
                    var node = t.treegrid("getSelected");
                    if (!node) {
                        top.$.messager.alert("信息", "很抱歉，尚未选择 " + title + " 。", "info", null, 2000);
                        return;
                    }
                    var id = node[opts.idField];
                    var url = (opts.urlEdit ? $.format(opts.urlEdit, id) : undefined) || (opts.url.match(new RegExp("treegrid", "i")) ? opts.url.replace(new RegExp("treegrid", "i"), "edit") + "/" + id : undefined);
                    if (!url) {
                        return;
                    }
                    $(parent).domain("openDialog", { 
                    	iconCls: "icon-edit", 
                    	title: "编辑 " + title, 
                    	src: url, 
                    	width: opts.openWidth, 
                    	height: opts.openHeight, 
                    	onClose: function() {
                    		
                    	}
                    });
                },
                scope: "one"
            },
            "-",
            {
                id: "btnDelete",
                text: "删除",
                iconCls: "icon-remove",
                disabled: true,
                handler: function() {
                    var t = $(this).parent().next().find(">table:hidden");
                    var opts = t.treegrid("options");
                    var title = opts.title || opts.subject;
                    var url = opts.urlDelete || (opts.url.match(new RegExp("treegrid", "i")) ? opts.url.replace(new RegExp("treegrid", "i"), "delete") : undefined);
                    t.domain("remove", { 
                    	url: url,
                    	title: title
                    });
                },
                scope: "one,more"
            },
            "-",
            {
                id: "btnReload",
                text: "刷新",
                iconCls: "icon-reload",
                disabled: false,
                handler: function() {
                	var t = $(this).parent().next().find(">table:hidden");
                	var row = t.treegrid('getSelected');
                	t.treegrid('reload', (row ? row.id : undefined));
                },
                scope: "none,one"
            }],
            names: null,
            subject: "记录",
            urlCreate: null,
            urlEdit: null,
            urlDelete: null,
            urlAttatch: null,
            openWidth: 600,
            openHeight: 360,
            auto: true
        }),
        nameCombo: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	queryParams: null,
        	fKeyField: null
        }),
        nameGrid: $.extend({}, $.fn.domain.defaultsOfCommon, {
        	queryParams: null,
        	fKeyField: null
        })
    };
    
})(jQuery);

