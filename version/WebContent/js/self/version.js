Ext.onReady(function() {
	Ext.QuickTips.init();
//	ds.load();
//	 ds.load({params:{channel:userChannel}});
//	new Ext.Viewport({
//				layout : 'border',
//				items : [{
//							region : 'center',
//							margins : '0 0 0 0',
//							layout : 'fit',
//							items : girdVer
//						}]
//			});
//		 //session过期验证
//		 Ext.Ajax.on('requestcomplete', checkUserSessionStatus, this);
//		 function checkUserSessionStatus(conn, response, options) {
//		 if (response.getResponseHeader("sessionstatus") == 'timeout') {
//		 if (response.getResponseHeader("loginPath")) {
//		 alert("会话过期，请重刷新界面再操作!");
//		 window.top.location.href = response.getResponseHeader("loginPath");
//		 } else {
//		 alert("请求超时请重新登陆 !");
//		 }
//		 }
//		 }
		
			// 设置提示信息位置为边上
	Ext.form.Field.prototype.msgTarget = 'side';
	win = new Ext.Window({
		id : 'login-winddd',
		title : '登陆',
		iconCls : 'tabs',
		width : 300,
		height : 120,
		collapsible : true,
		plain : true,
		// 初始化表单面板
		items : new Ext.form.FormPanel({
			id : 'login-form',
			labelWidth : 50, // 默认标签宽度板
			labelAlign : 'right',
			buttonAlign : 'center',
			// 不设置该值，表单将保持原样，设置后表单与窗体完全融合
			baseCls : 'header',
			layout : 'form',
			defaults : {
				width : 200
			},
			// 默认字段类型
			defaultType : 'textfield',
			items : [{
				name : 'username',
				cls : 'user',
				fieldLabel : '账号',
				allowBlank : false,
				blankText : '帐户不能为空'// 错误提示内容
					// 禁止为空
				}, {
				name : 'password',
				inputType : 'password',
				fieldLabel : '密码',
				cls : 'key',
				allowBlank : false,
				blankText : '密码不能为空'
			}],
			// 初始化按钮
			buttons : [{
				text : '登陆',
				type : 'submit',
				handler : function() {
					var but = this;
					but.setDisabled(true);
					this.setText('正在登陆');
					Ext.getCmp('login-form').getForm().submit({
						waitMsg : '正在登录  ',
						url : 'login.action',
						method : 'POST',
						success : function(form, action) {
							//console.log("成功!!!!!!!!!!");
							win.hide();
							ds.load({params:{channel:userChannel}});
							SetToolButtons(gridVarTb);
							new Ext.Viewport({
												layout : 'border',
												items : [{
															region : 'center',
															margins : '0 0 0 0',
															layout : 'fit',
															items : girdVer
														}]
								});		
							//console.log("open  !!!!!!!!!!");
							but.setDisabled(false);
						},
						failure : function(form, action) {
							Ext.Msg.alert('信息', action.result.info);
							but.setText("登陆");
							but.setDisabled(false);
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					Ext.getCmp('login-form').getForm().reset();
				}// 重置表单
			}]
		})
	});
	// 将窗口显示出来
	win.show();
});
	
var win = null;
var winNewPkgInfo = null;
var winEditPkgInfo = null;
var winCloseVer = null;
var editRecordId = null;
var viewVersionId = null;
var viewChannel = null;
var upFileRecordId = null;
var upFileId = null;
var winViewPkgInfo = null;
var winChannelInfo = null;
var winNewChannel = null;
var winEditChannel = null;
var winNewNotice = null;
var winEditNotice = null;
var viewNotice = null;
var winNoticeInfo=null;
var storecb = null;
var userChannel = "";
var note = Ext.data.Record.create([{
			name : 'id',
			mapping : 'id',
			type : 'int'
		}, {
			name : 'packageDate',
			mapping : 'packageDate'
		}, {
			name : 'versionId'
		}, {
			name : 'svnId'
		}, {
			name : 'engineId'
		}, {
			name : 'releaseDateTime'
		}, {
			name : 'editDateTime'
		}, {
			name : 'channel'
		}, {
			name : 'moduleChange'
		}, {
			name : 'versionNotice'
		}, {
			name : 'state'
		}, {
			name : 'uploadState'
		}, {
			name : 'buildState'
		}, {
			name : 'channelAddr'
		}, {
			name : 'forceUpdate'
		}]);
var ds = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : "Action/versioninfo?gd=1",
						method : "POST"
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : "totalProperty",
						root : "items",
						id : "id"
					}, note)

		});

		
var noteChannel = Ext.data.Record.create([{
			name : 'id',
			mapping : 'id',
			type : 'int'
		}, {
			name : 'channel',
			mapping : 'channel'
		}, {
			name : 'channelName'
		}, {
			name : 'keyWord'
		}, {
			name : 'shopLink'
		}, {
			name : 'pkgCount'
		}]);
var dsChannel = new Ext.data.Store({
		pageSize: 30,//这个是分页的大小 我设置的是30条数据一页     
			proxy : new Ext.data.HttpProxy({
						url : "Action/channelinfo",
						method : "POST"
					}),
    		autoLoad:true,
			reader : new Ext.data.JsonReader({
						totalProperty : "totalProperty",
						root : "items",
						id : "id"
					}, noteChannel)

		});		

var noticeinfo = Ext.data.Record.create([{
	name : 'id',
	mapping : 'id',
	type : 'int'
}, {
	name : 'name',
	mapping : 'name'
}, {
	name : 'title'
}, {
	name : 'imgurl'
}, {
	name : 'bz'
}, {
	name : 'status'
}]);
var dsNotice = new Ext.data.Store({
	pageSize: 30,//这个是分页的大小 我设置的是30条数据一页     
		proxy : new Ext.data.HttpProxy({
					url : "Action/noticeinfo",
					method : "POST"
				}),
		autoLoad:true,
		reader : new Ext.data.JsonReader({
					totalProperty : "totalProperty",
					root : "items",
					id : "id"
				}, noticeinfo)

	});		




var girdChannel = new Ext.grid.GridPanel({
	frame : true,
	stripeRows : true,// 斑马线
	store : dsChannel,
	id : 'girdChannel',
	trackMouseOver : true,
	loadMask : true,
	viewConfig : {
		forceFit : true
	},
	tbar : new Ext.Toolbar(['-', {
				text : '添加渠道',
				cls : 'newChannel',
				handler : function() {
					if (null == winNewChannel) {
						simple.getForm().reset();
						winNewChannel = new Ext.Window({
									title : "添加数据数据：{请慎重操作}",
									layout : 'fit',
									width : 400,
									height : 300,
									closeAction : 'hide',
									plain : true,
									modal : true,
									items : newChannel
								});
					}
					winNewChannel.show();
					var count = girdChannel.getStore().getTotalCount();
					var tmp = newChannel.getForm().findField("id");
					tmp.setValue(count + 1);
				}
			}]),
	columns : [
			new Ext.grid.RowNumberer(),// 行号
			{
				id : 'id',
				header : '序列号',
				dataIndex : 'id',
				width : 30,
				hidden : true,
				sortable : true
			}, {
				id : 'channel',
				header : '渠道',
				dataIndex : 'channel',
				width : 40,
				sortable : true
			}, {
				id : 'channelName',
				header : '渠道名称',
				dataIndex : 'channelName',
				width : 40,
				sortable : true
			}, {
				id : 'keyWord',
				header : '关键字',
				dataIndex : 'keyWord',
				width : 40,
				sortable : true
			}, {
				id : 'shopLink',
				header : '链接',
				dataIndex : 'shopLink',
				width : 40,
				sortable : true
			}, {
				id : 'pkgCount',
				header : '打包数量',
				dataIndex : 'pkgCount',
				width : 40,
				sortable : true
			}, {
				header : "编辑",
				dataIndex : 'editChannel',
				align : "center",
				width : 40,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					createGridButton.defer(1, this, [btnId]);
					function createGridButton() {
						var editBtn = new Ext.Button({
							text : "编辑",
							handler : function() {
								if (null == winEditChannel) {
									winEditChannel = new Ext.Window({
												title : "编辑数据数据：{请慎重操作}",
												layout : 'fit',
												width : 400,
												height : 300,
												closeAction : 'hide',
												plain : true,
												modal : true,
												items : editChannel
											});
								}
								winEditChannel.show();
								var record = girdChannel.getStore()
										.getAt(rowIndex);
								editRecordId = record.get('id');
								var tmp = editChannel.getForm().findField("id");
								tmp.setValue(editRecordId);
								var channel = editChannel.getForm()
										.findField("channel");
								channel.setValue(record.get('channel'));
								var channelName = editChannel.getForm()
										.findField("channelName");
								channelName.setValue(record.get('channelName'));
								var keyWord = editChannel.getForm()
										.findField("keyWord");
								keyWord.setValue(record.get('keyWord'));
								var shopLink = editChannel.getForm()
										.findField("shopLink");
								shopLink.setValue(record.get('shopLink'));
							}
						}).render(document.body, btnId);
						return editBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}, {
				header : "删除",
				dataIndex : 'delChannel',
				align : "center",
				width : 40,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					delGridButton.defer(1, this, [btnId]);
					function delGridButton() {
						var delBtn = new Ext.Button({
							text : "删除",
							handler : function() {

								var record = girdChannel.getStore()
										.getAt(rowIndex);
								var channelValue = record.get('channel');

								Ext.MessageBox.confirm('确认', '确定要删除渠道：'
												+ channelValue + " 信息？",
										function(btn) {
											if (btn == 'yes') {

												Ext.Ajax.request({
															url : "Action/delchannel?",
															method : 'POST',
															params : {
																channel : channelValue
															},
															success : function(
																	response,
																	options) {
																dsChannel.reload();
															}
														});

											}
										});
							}
						}).render(document.body, btnId);
						return delBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}],
	bbar : new Ext.PagingToolbar({// 分页
		pageSize : 30,
		store : dsChannel,
		displayInfo : true,
		displayMsg : '<font size=2>第 {0} 条到 {1} 条，一共 {2} 条记录</font>',
		emptyMsg : "没有记录"
	})
});
	



var  girdNotice= new Ext.grid.GridPanel({
	frame : true,
	stripeRows : true,// 斑马线
	store : dsNotice,
	id : 'girdNotice',
	trackMouseOver : true,
	loadMask : true,
	viewConfig : {
		forceFit : true
	},
	tbar : new Ext.Toolbar(['-', {
				text : '添加通知',
				cls : 'newNotice',
				handler : function() {
					if (null == winNewNotice) {
						simple.getForm().reset();
						winNewNotice = new Ext.Window({
									title : "添加数据数据：{请慎重操作}",
									layout : 'fit',
									width : 400,
									height : 500,
									closeAction : 'hide',
									plain : true,
									modal : true,
									items : newNotice
								});
					}
					winNewNotice.show();
					var count = girdNotice.getStore().getTotalCount();
					var tmp = newNotice.getForm().findField("id");
					tmp.setValue(count + 1);
				}
			}]),
	columns : [
			new Ext.grid.RowNumberer(),// 行号
			{
				id : 'id',
				header : '序列号',
				dataIndex : 'id',
				width : 30,
				hidden : true,
				sortable : true
			}, {
				id : 'name',
				header : '名称',
				dataIndex : 'name',
				width : 40,
				sortable : true
			}, {
				id : 'title',
				header : '标题',
				dataIndex : 'title',
				width : 40,
				sortable : true
			}, {
				id : 'imgurl',
				header : '图片',
				dataIndex : 'imgurl',
				width : 40,
				sortable : true
			}, {
				id : 'bz',
				header : '描述',
				dataIndex : 'bz',
				width : 40,
				sortable : true
			}, {
				id : 'status',
				header : '状态',
				dataIndex : 'status',
				width : 25,
				sortable : true
			}, {
				header : "编辑",
				dataIndex : 'editNotice',
				align : "center",
				width : 25,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					createGridButton.defer(1, this, [btnId]);
					function createGridButton() {
						var editBtn = new Ext.Button({
							text : "编辑",
							handler : function() {
								if (null == winEditNotice) {
									winEditNotice = new Ext.Window({
												title : "编辑数据数据：{请慎重操作}",
												layout : 'fit',
												width : 400,
												height : 500,
												closeAction : 'hide',
												plain : true,
												modal : true,
												items : editNotice
											});
								}
								winEditNotice.show();
								var record = girdNotice.getStore()
										.getAt(rowIndex);
								editRecordId = record.get('id');
								var tmp = editNotice.getForm().findField("id");
								tmp.setValue(editRecordId);
								var id = editNotice.getForm()
										.findField("id");
								id.setValue(record.get('id'));
								var name = editNotice.getForm()
										.findField("name");
								name.setValue(record.get('name'));
								var title = editNotice.getForm()
										.findField("title");
								title.setValue(record.get('title'));
								var imgurl = editNotice.getForm().findField("imgurl");
								imgurl.setValue(record.get('imgurl'));
								var bz = editNotice.getForm().findField("bz");
								bz.setValue(record.get('bz'));
								var status = editNotice.getForm()
										.findField("status");
								status.setValue(record.get('status'));
							}
						}).render(document.body, btnId);
						return editBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}, {
				header : "删除",
				dataIndex : 'delNotice',
				align : "center",
				width : 25,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					delGridButton.defer(1, this, [btnId]);
					function delGridButton() {
						var delBtn = new Ext.Button({
							text : "删除",
							handler : function() {

								var record = girdNotice.getStore()
										.getAt(rowIndex);
								var name = record.get('name');
								var id = record.get('id');
								Ext.MessageBox.confirm('确认', '确定要删除通知：'
												+ name + " 信息？",
										function(btn) {
											if (btn == 'yes') {

												Ext.Ajax.request({
															url : "Action/delnotice?",
															method : 'POST',
															params : {
																id : id,
																name:name
															},
															success : function(
																	response,
																	options) {
																dsNotice.reload();
															}
														});

											}
										});
							}
						}).render(document.body, btnId);
						return delBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}],
	bbar : new Ext.PagingToolbar({// 分页
		pageSize : 30,
		store : dsNotice,
		displayInfo : true,
		displayMsg : '<font size=2>第 {0} 条到 {1} 条，一共 {2} 条记录</font>',
		emptyMsg : "没有记录"
	})
});
		
var buildLogWin = null;

var logText = new Ext.form.TextArea({
			id : "finzip",
			name : "finzip",
			allowBlank : false,
			labelStyle : "text-align:right;",
			disable : false,
			layout : 'fit',
			readOnly : true,
			closeAction : 'hide',
			plain : true,
			xtype : 'textarea',
			value : "开始生成...............\n"
		});
var viewText = new Ext.form.TextArea({
			id : "viewMerge",
			name : "viewMerge",
			allowBlank : false,
			labelStyle : "text-align:right;",
			disable : false,
			layout : 'fit',
			readOnly : true,
			closeAction : 'hide',
			plain : true,
			xtype : 'textarea',
			value : '生成更新包如下：\n'
		});

SetToolButtons = function(tbr) {
	Ext.Ajax.request({
		url : "Action/channelnames?",
		method : 'POST',
		success : function(response, options) {
			var rsp = Ext.util.JSON.decode(response.responseText);
			storecb = new Ext.data.JsonStore({
						// 在此创建了两列数据.第一列列名为value,用于存储值
						// 第二列列名为text,用于存储显示文本
						fields : ['channel', 'channelName'],
						data : rsp.items
					});

			var comboChannel = new Ext.form.ComboBox({
						store : storecb,
						id : 'channel1',
						fieldLabel : '渠道选择',
						name : 'channel1',
						typeAhead : true,
						triggerAction : 'all',
						// lazyRender : true,
						displayField : 'channelName',
						valueField : 'channel',
						editable : false,
						mode : 'local',
						emptyText : '请选择渠道...',
						selectOnFocus : true,
						width : 160,
						listeners : {
							select : function(comboChannel) {
								userChannel = comboChannel.getValue();
								// console.log(userChannel + " ---- " );
								ds.reload({
											params : {
												channel : userChannel
											}
										});
							}
						}
					});

			tbr.addButton({
						text : '渠道管理',
						iconCls : 'icon-desktop',
						scope : this,
						handler : function() {
						if (null == winChannelInfo) {
								winChannelInfo = new Ext.Window({
											title : "渠道管理",
											layout : 'fit',
											width : 600,
											height : 500,
											closeAction : 'hide',
											plain : true,
											modal : true,
											items : girdChannel
										});
								}
							winChannelInfo.show();
						}
					});
			tbr.add("-");
			tbr.add("渠道选择:");
			tbr.add(comboChannel);
			// tbr.addFill();
			tbr.add("-");
			tbr.addButton({
						cls : 'newVer',
						text : '添加版本信息',
						handler : function() {
							if (null == winNewPkgInfo) {
								simple.getForm().reset();
								winNewPkgInfo = new Ext.Window({
											title : "添加数据数据：{请慎重操作}",
											layout : 'fit',
											width : 400,
											height : 500,
											closeAction : 'hide',
											plain : true,
											modal : true,
											items : simple
										});
							}
							winNewPkgInfo.show();
							var record = girdVer.getStore().getAt(0);
							var tmp = simple.getForm().findField("id");
							if (null != record) {
								var bsValue = record.get('id');
								tmp.setValue(bsValue + 1);
							} else
								tmp.setValue(1);
							var channel = simple.getForm().findField("channel");
							channel.setValue(userChannel);
						}
					});
					
			tbr.add("-");
			tbr.addButton({
						text : '封闭版本',
						cls : 'closeVer',
						handler : function() {
							verClosePanel.getForm().reset();
								storeVerId.reload({
											params : {
												channel : userChannel
											}
										});
							if (null == winCloseVer) {
								//verClosePanel.getForm().reset();
								winCloseVer = new Ext.Window({
											title : "关闭版本：{请慎重操作}",
											layout : 'fit',
											width : 400,
											height : 200,
											closeAction : 'hide',
											plain : true,
											modal : true,
											items : verClosePanel
										});
							}
							winCloseVer.show();
						}

					});
			tbr.add("-");
			tbr.addButton({
				text : '紧急发布通知',
				iconCls : 'icon-ok',
				scope : this,
				handler : function() {
					winNoticeInfo = new Ext.Window({
									title : "渠道管理",
									layout : 'fit',
									width : 600,
									height : 500,
									closeAction : 'hide',
									plain : true,
									modal : true,
									items : girdNotice
								});
					winNoticeInfo.show();
				}
			});
		},
		failure : function() {
			Ext.Msg.alert("提示信息", "按钮加载失败,请稍后重试！");
		}
	});
};	
		
		
// var channelData = [[1, 'appstore'],[2, 'appstore1']];
// var storeChannel1 = new Ext.data.JsonStore({
// // 在此创建了两列数据.第一列列名为value,用于存储值
//			// 第二列列名为text,用于存储显示文本
////			fields: ['channel', 'channelName'],
//    		url : "Action/channelnames?",
//    		autoLoad:true,
//    		root : "items"
////			data : channelData
//		});		
		
var gridVarTb = new Ext.Toolbar({
        id: 'tool_bar',
        cls: 'top-toolbar'
    });
//SetToolButtons(gridVarTb);
//new Ext.Toolbar(['-', {
//				text : '渠道管理',
//				handler : function() {
//					if (null == winChannelInfo) {
//						winChannelInfo = new Ext.Window({
//									title : "渠道管理",
//									layout : 'fit',
//									width : 600,
//									height : 500,
//									closeAction : 'hide',
//									plain : true,
//									modal : true,
//									items : girdChannel
//								});
//					}
//					winChannelInfo.show();
//				}
//			},'-', {
//				text : '渠道选择'
//			},comboChannel,'-', {
//				cls : 'newVer',
//				text : '添加版本信息',
//				handler : function() {
//					if (null == winNewPkgInfo) {
//						simple.getForm().reset();
//						winNewPkgInfo = new Ext.Window({
//									title : "添加数据数据：{请慎重操作}",
//									layout : 'fit',
//									width : 400,
//									height : 500,
//									closeAction : 'hide',
//									plain : true,
//									modal : true,
//									items : simple
//								});
//					}
//					winNewPkgInfo.show();
//					var record = girdVer.getStore().getAt(0);
//					var bsValue = record.get('id');
//					var tmp = simple.getForm().findField("id");
//					tmp.setValue(bsValue + 1);
//					var channel = simple.getForm()
//							.findField("channel");
//					channel.setValue(userChannel);		
//				}
//			},'-', {
//				text : '封闭版本',
//				cls : 'closeVer',
//				handler : function() {
//					if (null == winCloseVer) {
//						verClosePanel.getForm().reset();
//						winCloseVer = new Ext.Window({
//									title : "关闭版本：{请慎重操作}",
//									layout : 'fit',
//									width : 400,
//									height : 200,
//									closeAction : 'hide',
//									plain : true,
//									modal : true,
//									items : verClosePanel
//								});
//					}
//					winCloseVer.show();
//				}
//			}]);
	//gridVarTb.addField(comboChannel);	
var girdVer = new Ext.grid.GridPanel({
	frame : true,
	title : '版本管理',
	stripeRows : true,// 斑马线
	store : ds,
	id : 'grid',
	trackMouseOver : true,
	loadMask : true,
	viewConfig : {
		forceFit : true
	},
	tbar : gridVarTb,
	columns : [
			new Ext.grid.RowNumberer(),// 行号
			{
				id : 'id',
				header : '序列号',
				dataIndex : 'id',
				width : 30,
				hidden : true,
				sortable : true
			}, {
				id : 'channelAddr',
				header : '强制更新地址',
				dataIndex : 'channelAddr',
				hidden : true,
				width : 40,
				sortable : true
			}, {
				id : 'packageDate',
				header : '打包日期',
				dataIndex : 'packageDate',
				width : 80,
				sortable : true
			}, {
				id : 'versionId',
				header : '版本',
				dataIndex : 'versionId',
				width : 40,
				sortable : true
			}, {
				id : 'svnId',
				header : 'svn版本',
				dataIndex : 'svnId',
				width : 50,
				sortable : true
			}, {
				id : 'engineId',
				header : '引擎版本',
				dataIndex : 'engineId',
				width : 50,
				sortable : true
			}, {
				id : 'releaseDateTime',
				header : '发布时间',
				dataIndex : 'releaseDateTime',
				width : 90,
				sortable : true
			}, {
				id : 'editDateTime',
				header : '编辑时间',
				dataIndex : 'editDateTime',
				width : 90,
				sortable : true
			}, {
				id : 'channel',
				header : '渠道',
				dataIndex : 'channel',
				width : 60,
				sortable : true,
				autoWidth:true
			}, {
				id : 'forceUpdate',
				header : '强制更新状态',
				dataIndex : 'forceUpdate',
				//hidden : true,
				width : 40,
				sortable : true
			}, {
				id : 'moduleChange',
				header : '功能改变',
				dataIndex : 'moduleChange',
				width : 120,
				autoHeight:true,
				sortable : true
			}, {
				id : 'versionNotice',
				header : '版本变动公告',
				dataIndex : 'versionNotice',
				width : 120,
				autoHeight:true,
				sortable : true
			}, {
				id : 'state_Fb',
				header : '状态',
				dataIndex : 'state',
				align : "center",
				width : 80,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var showValue = stateToShow(value);
					var cbtnId = Ext.id();// 获得Ext创建的唯一id
					createGridButton1.defer(1, this, [cbtnId]);
					function createGridButton1() {
						var combo = new Ext.form.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							lazyRender : true,
							displayField : 'value',
							valueField : 'text',
							store : storeyear,
							editable : false,
							mode : 'local',
							queryMode : 'local',
							width : 80,
							listeners : {
								select : function(combo) {
									var selValue = combo.getValue();
									// console.log(value + " ---- " + selValue);
									if (value == 1 && selValue == 3) {
										// Ext.Msg.alert("提示",
										// " 必须先设置为testing，才能设置为public！");
										Ext.MessageBox.show({
											title : '警告',
											msg : '必须先设置为testing（更新版本包测试状态），\n才能设置为public（更新版本包对外发布状态）!',
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.WARNING
										});
										combo.setValue(value);
									} else {
										if (selValue > value || selValue == 1) {
											Ext.MessageBox
													.confirm(
															'确认',
															'确定修改为：'
																	+ stateToShow(selValue)
																	+ stateDocToShow(selValue)
																	+ "状态吗？",
															function(btn) {
																// console.log("
																// --btn-- "+
																// btn);
																if (btn == 'yes') {
																	value = selValue;
																	var record = girdVer
																			.getStore()
																			.getAt(rowIndex);
																	var sId = record
																			.get('id');
																	var sVerId = record
																			.get('versionId');
																	Ext.Ajax
																			.request(
																					{
																						url : 'Action/releasestate',
																						params : {
																							state : selValue,
																							id : sId,
																							versionId : sVerId,
																							channel : userChannel
																						},
																						success : function(
																								response,
																								option) {
																							//var respText = Ext
																								//	.decode(response.responseText); // 解码JSON格式数据为一个对象
																							Ext.Msg
																									.alert(
																											"提示",
																											stateToShow(selValue)
																													+ stateDocToShow(selValue)
																													+ "设置成功！");
																						}
																					});
																} else {
																	combo
																			.setValue(value);
																}
															});
										} else {
											var record = girdVer.getStore()
													.getAt(rowIndex);
											var sId = record.get('id');
											var sVerId = record
													.get('versionId');
											Ext.Ajax.request({
												url : 'Action/releasestate',
												params : {
													state : selValue,
													id : sId,
													versionId : sVerId,
													channel : userChannel
												},
												success : function(response,
														option) {
													value = selValue;
													// var respText =
													// Ext.decode(response.responseText);
													// // 解码JSON格式数据为一个对象
													Ext.Msg
															.alert(
																	"提示",
																	stateToShow(selValue)
																			+ stateDocToShow(selValue)
																			+ "设置成功！");
												}
											});
										}
									}
								}
							},
							emptyText : showValue
						});
						combo.render(document.body, cbtnId);
						return combo;
					}
					return "<div id=" + cbtnId + "></div>";
				}
			}, {
				header : "上传",
				dataIndex : 'upload',
				align : "center",
				width : 60,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var record = girdVer.getStore().getAt(rowIndex);
					var stateValue = record.get('uploadState');
					if (stateValue == '0') {
						var btnId = Ext.id();// 获得Ext创建的唯一id
						createGridButton.defer(1, this, [btnId]);
						function createGridButton() {
							var uploadBtn = new Ext.Button({
								text : "上传",
								handler : function() {
									upFileId = record.get('id');
									upFileVerId = record.get('versionId');
									var isShow = true;
									for (var i = 0; i < upFileId; i++) {
										var oldRecord = girdVer.getStore()
												.getAt(rowIndex);
										var exitVerId = oldRecord
												.get('versionId');
										var exitId = oldRecord.get('id');
										if (exitId != upFileId
												&& upFileVerId == exitVerId) {
											Ext.MessageBox.show({
														title : exitVerId
																+ '警告'
																+ upFileVerId,
														msg : '存在重复版本，请修改版本后在提交!',
														buttons : Ext.MessageBox.OK,
														icon : Ext.MessageBox.WARNING
													});
													isShow = false;
										}
									}
									if (isShow && null != winUploadPkgInfo) {
										winUploadPkgInfo.show();
									}
								}
							});
							uploadBtn.render(document.body, btnId);
							return uploadBtn;
						}
						return "<div id=" + btnId + "></div>";
					} else {
						return "<label >已上传</label>";
					}
				}

			}, {
				id : 'merge',
				header : "生成",
				dataIndex : 'merge',
				align : "center",
				width : 60,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var record = girdVer.getStore().getAt(rowIndex);
					var bsValue = record.get('buildState');
					if (bsValue == '0') {
						var stateValue = record.get('uploadState');
						var sId = record.get('id');
						var sVerId = record.get('versionId');
						var btnId = Ext.id();// 获得Ext创建的唯一id
						createGridButton.defer(1, this, [btnId]);
						function createGridButton(value) {
							var mergeBtn = new Ext.Button({
								text : "生成",
								handler : function() {

									if (stateValue == 1) {
										logText
												.setValue("开始生成...............\n");
										if (null == buildLogWin) {
											buildLogWin = new Ext.Window({
												title : "生成进度",
												layout : 'fit',
												width : 400,
												height : 500,
												closeAction : 'hide',
												plain : true,
												modal : true,
												autoScroll : true, // 内容溢出时产生滚动条
												collapsible : true, // 右上角上的那个收缩按钮，设为false则不显示
												titleCollapse : true, // true则点击标题栏任何位置都能收缩
												frame : true,// 圆角边框
												items : logText,
												buttons : [{
															text : '关闭',
															handler : function() {
																buildLogWin
																		.hide();
																Ext.TaskMgr
																		.stopAll();// 开始执行这个任务
															}
														}]
											});
										}
										buildLogWin.show();
										Ext.TaskMgr.start(task);// 开始执行这个任务
										// console.log("Ext.TaskMgr.start(task)");
									}

									Ext.Ajax.request({
												url : 'Action/filesmerge',
												params : {
													buildState : bsValue,
													id : sId,
													versionId : sVerId,
													channel : userChannel
												},
												success : function(response, option,action) {
												var falg=	response.responseText;
												 if(falg=='true'){
													 Ext.Msg.alert('提示：', '生成成功！');
													 ds.load({params:{channel:userChannel}});
												 }else{
													 Ext.Msg.alert('提示：', '生成失败！');
													 ds.load({params:{channel:userChannel}});
												 }
													 
													// if (stateValue == 1) {
													// logText
													// .setValue("开始生成...............\n");
													// if (null == buildLogWin)
													// {
													// buildLogWin = new
													// Ext.Window(
													// {
													// title : "生成进度",
													// layout : 'fit',
													// width : 400,
													// height : 500,
													// closeAction : 'hide',
													// plain : true,
													// autoScroll : true, //
													// 内容溢出时产生滚动条
													// collapsible : true, //
													// 右上角上的那个收缩按钮，设为false则不显示
													// titleCollapse : true, //
													// true则点击标题栏任何位置都能收缩
													// frame : true,// 圆角边框
													// items : logText,
													// buttons : [{
													// text : '关闭',
													// handler : function() {
													// buildLogWin
													// .hide();
													// Ext.TaskMgr.stopAll();//
													// 开始执行这个任务
													// }
													// }]
													// });
													// }
													// buildLogWin.show();
													// Ext.TaskMgr.start(task);//
													// 开始执行这个任务
													// console.log("Ext.TaskMgr.start(task)");
													// }
												}
											});
								}
							});
							mergeBtn.render(document.body, btnId);
							return mergeBtn;
						}
						return "<div id=" + btnId + "></div>";
					} else {
						return "<label>已生成</label>";
					}
				}
			}, {
				header : "编辑",
				dataIndex : 'edit',
				align : "center",
				width : 60,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					createGridButton.defer(1, this, [btnId]);
					function createGridButton() {
						var editBtn = new Ext.Button({
							text : "编辑",
							handler : function() {
								if (null == winEditPkgInfo) {
									winEditPkgInfo = new Ext.Window({
												title : "编辑数据数据：{请慎重操作}",
												layout : 'fit',
												width : 400,
												height : 500,
												closeAction : 'hide',
												plain : true,
												modal : true,
												items : simpleEdit
											});
								}
								winEditPkgInfo.show();
								var record = girdVer.getStore().getAt(rowIndex);
								editRecordId = record.get('id');
								var tmp = simpleEdit.getForm().findField("id");
								tmp.setValue(editRecordId);
								var versionIdF = simpleEdit.getForm()
										.findField("versionId");
								versionIdF.setValue(record.get('versionId'));
								var engineIdF = simpleEdit.getForm()
										.findField("engineId");
								engineIdF.setValue(record.get('engineId'));
								var svnIdF = simpleEdit.getForm()
										.findField("svnId");
								svnIdF.setValue(record.get('svnId'));
								var moduleChangeF = simpleEdit.getForm()
										.findField("moduleChange");
								moduleChangeF.setValue(record
										.get('moduleChange'));
								var versionNoticeF = simpleEdit.getForm()
										.findField("versionNotice");
								versionNoticeF.setValue(record
										.get('versionNotice'));
								var channel = simpleEdit.getForm()
										.findField("channel");
								channel.setValue(record
										.get('channel'));
								var channelAddr = simpleEdit.getForm()
										.findField("channelAddr");
								channelAddr.setValue(record
										.get('channelAddr'));
								var forceUpdate = simpleEdit.getForm()
										.findField("forceUpdate");
								forceUpdate.setValue(record
										.get('forceUpdate'));
							}
						}).render(document.body, btnId);
						return editBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}, {
				header : "预览",
				dataIndex : 'viewbt',
				align : "center",
				width : 60,
				sortable : false,
				renderer : function(value, cellmeta, record, rowIndex,
						columnIndex, store) {
					var btnId = Ext.id();// 获得Ext创建的唯一id
					createGridButton.defer(1, this, [btnId]);
					function createGridButton() {
						var editBtn = new Ext.Button({
							text : "预览",
							handler : function() {
								var record = girdVer.getStore().getAt(rowIndex);
								viewVersionId = record.get('versionId');
								viewChannel = record.get('channel');
								// console.log(viewChannel + " ---view--- " +
								// viewVersionId);
								if (null == winViewPkgInfo) {
									winViewPkgInfo = new Ext.Window({
												title : "生成预览",
												layout : 'fit',
												width : 450,
												height : 500,
												closeAction : 'hide',
												plain : true,
												modal : true,
												autoScroll : true, // 内容溢出时产生滚动条
												collapsible : true, // 右上角上的那个收缩按钮，设为false则不显示
												titleCollapse : true, // true则点击标题栏任何位置都能收缩
												frame : true,// 圆角边框
												items : viewText,
												buttons : [{
															text : '关闭',
															handler : function() {
																winViewPkgInfo
																		.hide();
															}
														}]
											});
								}
								Ext.Ajax.request({
									url : 'Action/mergeinfo',
									params : {
										versionId : viewVersionId,
										channel : viewChannel
									},
									disableCaching : true,// 禁止缓存
									timeout : 60000,// 最大等待时间,超出则会触发超时
									success : function(response, option) {
										if (!response
												|| response.responseText == '') {// 返回的内容为空,即服务器停止响应时
											Ext.Msg.alert('错误', '无数据');
											return;
										} else {
											var respText = Ext
													.decode(response.responseText); // 解码JSON格式数据为一个对象
											if (null == respText.fin
													|| respText.fin == '') {
												var s = '生成更新包如下：\n\n'
														+ respText.info + "\n";
												viewText.setValue(s);
											}
										}
									},
									// 请求失败时
									failure : function(data) {
										Ext.MessageBox.show({
											title : '错误',
											msg : '在读取动态数据时时发生网络错误，请确认您已经链接网络后，重启浏览器再试！',
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.ERROR,
											fn : function(btn, text) {
												if (btn == 'ok') {
												}
											}
										});
										Ext.TaskMgr.stopAll();// 结束这个任务}
									}
								});
								winViewPkgInfo.show();
							}
						}).render(document.body, btnId);
						return editBtn;
					}
					return "<div id=" + btnId + "></div>";
				}

			}],
	bbar : new Ext.PagingToolbar({// 分页
		pageSize : 30,
		store : ds,
		displayInfo : true,
		displayMsg : '<font size=2>第 {0} 条到 {1} 条，一共 {2} 条记录</font>',
		emptyMsg : "没有记录"
	})
});

function stateToShow(value) {
	var showValue = value == 1 ? 'development' : value == 2
			? 'testing'
			: 'public';
	return showValue;
};

function stateDocToShow(value) {
	var showValue = value == 1 ? '（版本封闭状态，不对可更新）' : value == 2
			? '（更新版本包测试状态）'
			: '（更新版本包对外发布状态）';
	return showValue;
};
//去掉字符串两头空格 
function trimStr(str) {
	return str.replace(/(^\s*)|(\s*$)/g, '');
};


var task = { // 定义一个任务
	run : function() {
		// console.log("Ext.TaskMgr.start(task) ------- run");
		Ext.Ajax.request({
					url : 'Action/mergeproc',
					disableCaching : true,// 禁止缓存
					timeout : 60000,// 最大等待时间,超出则会触发超时
					success : function(response, option) {
						if (!response || response.responseText == '') {// 返回的内容为空,即服务器停止响应时
							// Ext.TaskMgr.stopAll();// 结束这个任务}
							// Ext.Msg.alert("错误----错误------", respText);
							// Ext.MessageBox.show({
							// title : '错误',
							// msg : '在读取动态数据时返回了空数据，请确认您的设备问题，请重启浏览器再试！',
							// buttons : Ext.Msg.OK,
							// icon : Ext.MessageBox.ERROR
							// });
							return;
						} else {
							var respText = Ext.decode(response.responseText); // 解码JSON格式数据为一个对象
							// console.log(respText);
							if (null == respText.fin || respText.fin == '') {
								// Ext.Msg.alert("---提示---", respText.info);
								insertIntoHtml(respText);
							} else {
								insertIntoHtml(respText);
								Ext.Msg.alert("提示", "生成完成！！！！！！！！！！");
								ds.reload();
								Ext.TaskMgr.stopAll();// 结束这个任务}
							}
						}
					},
					// 请求失败时
					failure : function(data) {
						Ext.MessageBox.show({
									title : '错误',
									msg : '在读取动态数据时时发生网络错误，请确认您已经链接网络后，重启浏览器再试！',
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR,
									fn : function(btn, text) {
										if (btn == 'ok') {
										}
									}
								});
						Ext.TaskMgr.stopAll();// 结束这个任务}
					}
				});
	},
	interval : 1000
	// 2 秒钟更新一次
};

function insertIntoHtml(collection) {
	var s = collection.info + "\n";
	logText.setValue(logText.getValue() + s);
};

var uploadForm = new Ext.form.FormPanel({
			baseCls : 'x-plain',
			labelWidth : 100,
			fileUpload : true,
			defaultType : 'textfield',
			items : [{
						xtype : 'textfield',
						fieldLabel : '上传文件名',
						name : 'userfile',
						id : 'userfile',
						inputType : 'file',
						blankText : 'File can\'t not empty.',
						anchor : '100%'
					}]
		});

var winUploadPkgInfo = new Ext.Window({
	title : '文件上传',
	width : 400,
	height : 100,
	minWidth : 300,
	minHeight : 100,
	layout : 'fit',
	plain : true,
	modal : true,
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	items : uploadForm,
	buttons : [{
		text : '上传',
		handler : function() {

			if (uploadForm.form.isValid()) {
				if (Ext.getCmp('userfile').getValue() == '') {
					Ext.Msg.alert('错误', '请选择你要上传的文件');
					return;
				}
				Ext.MessageBox.show({
							title : '请等待',
							msg : '文件正在上传...',
							progressText : '',
							width : 300,
							progress : true,
							closable : false,
							animEl : 'loding'
						});
				uploadForm.getForm().submit({
					url : 'Action/upload',
					method : 'POST',
					params : {
						uploadfile : uploadForm.findById("userfile").getValue(),
						id : upFileId,
						versionId : upFileVerId,
						channel : userChannel
					},

					success : function(form, action) {
						uploadForm.findById("userfile").setValue("");
						Ext.Msg.alert('提示：', action.result.info);
						winUploadPkgInfo.hide();
						ds.reload();
						// uploadForm.getForm().reset();
					},
					failure : function(form, action) {
						winUploadPkgInfo.hide();
						Ext.Msg.alert('错误', action.result.info);
					}
				});
			}
		}
	}, {
		text : '关闭',
		handler : function() {
			winUploadPkgInfo.hide();
		}
	}]
});

var year = [[1, 'development'], [2, 'testing'], [3, 'public']];
var storeyear = new Ext.data.SimpleStore({
			// 在此创建了两列数据.第一列列名为value,用于存储值
			// 第二列列名为text,用于存储显示文本
			fields : ['text', 'value'],
			data : year
		});
		
var fuData = [[false, 0], [true, 1]];
var storeForce = new Ext.data.SimpleStore({
			// 在此创建了两列数据.第一列列名为value,用于存储值
			// 第二列列名为text,用于存储显示文本
			fields : ['text', 'value'],
			data : fuData
		});
var storeStatus = new Ext.data.SimpleStore({
	// 在此创建了两列数据.第一列列名为value,用于存储值
	// 第二列列名为text,用于存储显示文本
	fields : ['text', 'value'],
	data : fuData
});
//var storeChannel = new Ext.data.JsonStore({
//			// 在此创建了两列数据.第一列列名为value,用于存储值
//			// 第二列列名为text,用于存储显示文本
//			fields: ['channel', 'channelName'],
//    		url : "Action/channelnames?",
//    		autoLoad:true,
//    		root : "items"
//		});
		
var storeVerId = new Ext.data.JsonStore({
			// 在此创建了两列数据.第一列列名为value,用于存储值
			// 第二列列名为text,用于存储显示文本
			fields: ['id', 'versionId'],
    		url : "Action/versionids",
    		//autoLoad:true,
    		root : "items"
		});
		

//// record 模板
//NewRecordTemp = Ext.data.Record.create([{
//			name : 'transModeCode'
//		}, {
//			name : 'transModeFactor'
//		}]);
//// 新record
//var newRecord = new NewRecordTemp({
//			transModeCode : '',
//			transModeFactor : 0
//		});

var simple = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 350,
			defaults : {
				width : 230
			},
			defaultType : 'textfield',

			items : [{
						fieldLabel : '版本号(*)',
						name : 'versionId',
						allowBlank : false
					}, {
						fieldLabel : '序列号',
						readOnly : true,
						name : 'id'
					}, {
						fieldLabel : '引擎版本',
						name : 'engineId'
					}, {
						fieldLabel : 'svn版本',
						name : 'svnId'
					}, {
						fieldLabel : '渠道',
						name : 'channel',
						allowBlank : false,
//						xtype : 'combo',
//						typeAhead : true,
//						triggerAction : 'all',
//						lazyRender : true,
//						displayField : 'channelName',
//						valueField : 'channel',
//						store : storeChannel,
						editable : false,
//						mode : 'local',
//						queryMode : 'local',
//						selectOnFocus : true,
						readOnly : true,
						width : 120
					}, {
						fieldLabel : '渠道客户端下载地址',
						name : 'channelAddr'
					}, {
				fieldLabel : '强制更新状态',
				name : 'forceUpdate',
				xtype : 'combo',
				store : storeForce,
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				displayField : 'text',
				valueField : 'value',
				mode : 'local',
				queryMode : 'local',
				selectOnFocus : true,
				width : 120,
				listeners : {
					select : function() {
						var tmp = simple.getForm().findField("forceUpdate");
						var value = tmp.getValue();
						if (value == 1) {
							var addr = simpleEdit.getForm()
									.findField("channelAddr");
							if (trimStr(addr.getValue()) == '') {
								Ext.Msg.alert('信息',
										"设置强制更新状态为true，渠道客户端下载地址不能为空！！");
								tmp.setValue(0);
							} else {
								Ext.MessageBox
										.confirm(
												'确认',
												'确定要发布强制下载应用安装的更新包吗？此操作将会把此版本以前的所有更新版本屏蔽！！！',
												function(btn) {
													if (btn == 'no') {
														tmp.setValue(0);
													}
												});
							}
						}
					}
				}
			}, new Ext.form.TextArea({
								fieldLabel : '功能变动',
								name : 'moduleChange',
								height : 120,
								maxLength : 500
							}), new Ext.form.TextArea({
								fieldLabel : '版本变动公告',
								name : 'versionNotice',
								height : 150,
								maxLength : 1000
							})],

			buttons : [{
						text : '保存',
						handler : function() {
							simple.getForm().submit({
										waitMsg : '正在保存...  ',
										url : "Action/saveinfo",
										method : 'POST',
										success : function(form, action) {
											// if (action.result.flag == 1) {
											winNewPkgInfo.hide();
											ds.reload();
											// Ext.Msg.alert('信息',
											// action.result.info);
											// } else {
											// Ext.Msg.alert('信息',
											// action.result.info);
											// }
											simple.getForm().reset();
										},
										failure : function(form, action) {
											console.log(action.result);
											Ext.Msg.alert('信息',
													action.result == null ? "录入数据有误！保存失败。" : action.result.info);
										}
									});
						}
					}, {
						text : '取消',
						handler : function() {
							simple.getForm().reset();
							if (null != winNewPkgInfo) {
								winNewPkgInfo.hide();
							}
						}
					}]
});

var simpleEdit = new Ext.FormPanel({
	labelWidth : 75,
	frame : true,
	bodyStyle : 'padding:5px 5px 0',
	width : 350,
	defaults : {
		width : 230
	},
	defaultType : 'textfield',
	items : [{
				fieldLabel : '版本号',
				name : 'versionId',
				allowBlank : false,
				readOnly : true
			}, {
				fieldLabel : '序列号',
				name : 'id',
				readOnly : true
			}, {
				fieldLabel : '引擎版本',
				name : 'engineId'
			}, {
				fieldLabel : 'svn版本',
				name : 'svnId'
			}, {
				fieldLabel : '渠道',
				name : 'channel',
//				xtype : 'combo',
//				typeAhead : true,
//				triggerAction : 'all',
//				lazyRender : true,
//				displayField : 'channelName',
//				valueField : 'channel',
//				store : storeChannel,
				editable : false,
				readOnly : true,
//				mode : 'local',
//				queryMode : 'local',
//				selectOnFocus : true,
				width : 120
			}, {
				fieldLabel : '渠道客户端下载地址',
				name : 'channelAddr'
			}, {
				fieldLabel : '强制更新状态',
				name : 'forceUpdate',
				xtype : 'combo',
				store : storeForce,
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				displayField : 'text',
				valueField : 'value',
				mode : 'local',
				queryMode : 'local',
				selectOnFocus : true,
				width : 120,
				editable : false,
				readOnly : true,
				listeners : {
					select : function() {
						var tmp = simpleEdit.getForm().findField("forceUpdate");
						var value = tmp.getValue();
						if(value == 1) {
							
						var addr = simpleEdit.getForm().findField("channelAddr");
						//console.log(addr.getValue());
						if(trimStr(addr.getValue()) == '') {
							Ext.Msg.alert('信息', "设置强制更新状态为true，渠道客户端下载地址不能为空！！");
							tmp.setValue(0);
						} else {
						Ext.MessageBox.confirm('确认', '确定要发布强制下载应用安装的更新包吗？此操作将会把此版本以前的所有更新版本屏蔽！！！',
							function(btn) {
								if (btn == 'no') {
									tmp.setValue(0);
								}
							});
						}
						}
					}
				}
			}, new Ext.form.TextArea({
						fieldLabel : '功能变动',
						name : 'moduleChange',
						height : 120,
						maxLength : 500
					}), new Ext.form.TextArea({
						fieldLabel : '版本变动公告',
						name : 'versionNotice',
						height : 150,
						maxLength : 1000
					})],

	buttons : [{
				text : '保存',
				handler : function() {
					simpleEdit.getForm().submit({
								waitMsg : '正在保存...  ',
								url : "Action/editinfo",
								method : 'POST',
								success : function(form, action) {
									if (action.result.success == true) {
										Ext.Msg.alert('信息', action.result.info);
										winEditPkgInfo.hide();
										ds.reload();
									} else {
										Ext.Msg.alert('信息', action.result.info);
									}
								},
								failure : function(form, action) {
									Ext.Msg.alert('信息', action.result.info);
								}
							});
				}
			}, {
				text : '取消',
				handler : function() {
					simpleEdit.getForm().reset();
					if (null != winEditPkgInfo) {
						winEditPkgInfo.hide();
					}
				}
			}]
});

var verClosePanel = new Ext.FormPanel({
	labelWidth : 120,
	frame : true,
	bodyStyle : 'padding:5px 5px 0',
	width : 350,
	defaults : {
		width : 230
	},

	items : [{
				fieldLabel : '关闭版本号（选中版本及先前版本都视为作废版本处理，不会出现在以后的更新版本中）',
				name : 'closeVersionId',
				xtype : 'combo',
				
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				store : storeVerId,
				editable : false,
				displayField : 'versionId',
				valueField : 'id',
				mode : 'local',
				queryMode : 'local',
				selectOnFocus : true,
				width : 150
			}],

	buttons : [{
				text : '保存',
				handler : function() {

					var tmp = verClosePanel.getForm()
							.findField("closeVersionId");
					Ext.MessageBox.confirm('确认', '确定关闭版本为：' + tmp.getRawValue()
									+ " 的更新包吗？此操作将会把此版本以前的所有更新版本屏蔽！！！",
							function(btn) {
								if (btn == 'yes') {
									var value = tmp.getValue();
									var verId = tmp.getRawValue();
									Ext.Ajax.request({
												url : 'Action/distory',
												params : {
													channel : userChannel,
													id : value,
													versionId : verId
												},
												success : function(response,
														option) {
													winCloseVer.hide();
													ds.reload();
												},
												failure : function(form, action) {
													Ext.Msg.alert('信息',
															action.result.info);
												}
											});
								}
							});

				}
			}, {
				text : '取消',
				handler : function() {
					verClosePanel.getForm().reset();
					if (null != winCloseVer) {
						winCloseVer.hide();
					}
				}
			}]
});

var newChannel = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 350,
			defaults : {
				width : 230
			},
			defaultType : 'textfield',

			items : [{
						fieldLabel : '序列号',
						readOnly : true,
						name : 'id'
					}, {
						fieldLabel : '渠道',
						allowBlank : false,
						name : 'channel'
					}, {
						fieldLabel : '渠道名称',
						name : 'channelName'
					}, {
						fieldLabel : '关键字',
						name : 'keyWord'
					}, {
						fieldLabel : '链接',
						name : 'shopLink'
					}],

			buttons : [{
						text : '保存',
						handler : function() {

							newChannel.getForm().submit({
										waitMsg : '正在保存...  ',
										url : "Action/createchannel",
										method : 'POST',
										success : function(form, action) {
											winNewChannel.hide();
											dsChannel.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert('信息',
													action.result == null ? "录入数据有误！保存失败。" : action.result.info);
										}
									});
							
						}
					}, {
						text : '取消',
						handler : function() {
							newChannel.getForm().reset();
							if (null != winNewChannel) {
								winNewChannel.hide();
							}
						}
					}]
});

var editChannel = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 350,
			defaults : {
				width : 230
			},
			defaultType : 'textfield',

			items : [{
						fieldLabel : '序列号',
						readOnly : true,
						name : 'id'
					}, {
						fieldLabel : '渠道',
						name : 'channel',
						readOnly : true
					}, {
						fieldLabel : '渠道名称',
						name : 'channelName'
					}, {
						fieldLabel : '关键字',
						name : 'keyWord'
					}, {
						fieldLabel : '链接',
						name : 'shopLink'
					}],

			buttons : [{
						text : '保存',
						handler : function() {

							editChannel.getForm().submit({
										waitMsg : '正在保存...  ',
										url : "Action/editchannel",
										method : 'POST',
										success : function(form, action) {
											winEditChannel.hide();
											dsChannel.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert('信息',
													action.result.info);
										}
									});
							
						}
					}, {
						text : '取消',
						handler : function() {
							editChannel.getForm().reset();
							if (null != winEditChannel) {
								winEditChannel.hide();
							}
						}
					}]
});



//通知

var newNotice = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 350,
			defaults : {
				width : 230
			},
			defaultType : 'textfield',
			items : [{
						fieldLabel : '序列号',
						readOnly : true,
						name : 'id'
					}, {
						fieldLabel : '名称',
						name : 'name'
					}, {
						fieldLabel : '标题',
						name : 'title'
					}, {
						fieldLabel : '图片',
						name : 'imgurl'
					}, {
						fieldLabel : '状态',
						name : 'status',
						xtype : 'combo',
						store : storeStatus,
						typeAhead : true,
						triggerAction : 'all',
						lazyRender : true,
						displayField : 'text',
						valueField : 'value',
						mode : 'local',
						queryMode : 'local',
						width : 120,
					}, new Ext.form.TextArea({
						fieldLabel : '描述',
						name : 'bz',
						height : 120,
						maxLength : 500
					})],

			buttons : [{
						text : '保存',
						handler : function() {
							newNotice.getForm().submit({
										waitMsg : 'Notice正在保存...  ',
										url : "Action/createnotice",
										method : 'POST',
										success : function(form, action) {
											winNewNotice.hide();
											dsNotice.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert('信息',
													action.result == null ? "录入数据有误！保存失败。" : action.result.info);
										}
									});
							
						}
					}, {
						text : '取消',
						handler : function() {
							newNotice.getForm().reset();
							if (null != winNewNotice) {
								winNewNotice.hide();
							}
						}
					}]
});

var editNotice = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 350,
			defaults : {
				width : 230
			},
			defaultType : 'textfield',

			items : [{
				fieldLabel : '序列号',
				readOnly : true,
				name : 'id'
			}, {
				fieldLabel : '名称',
				name : 'name'
			}, {
				fieldLabel : '标题',
				name : 'title'
			}, {
				fieldLabel : '图片',
				name : 'imgurl'
			} ,{
				fieldLabel : '状态',
				name : 'status',
				xtype : 'combo',
				store : storeStatus,
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				displayField : 'text',
				valueField : 'value',
				mode : 'local',
				queryMode : 'local',
				width : 120,
			}, new Ext.form.TextArea({
				fieldLabel : '描述',
				name : 'bz',
				height : 120,
				maxLength : 500
			})],

			buttons : [{
						text : '保存',
						handler : function() {
							editNotice.getForm().submit({
										waitMsg : '正在保存...  ',
										url : "Action/editnotice",
										method : 'POST',
										success : function(form, action) {
											winEditNotice.hide();
											dsNotice.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert('信息',
													action.result.info);
										}
									});
							
						}
					}, {
						text : '取消',
						handler : function() {
							editNotice.getForm().reset();
							if (null != winEditNotice) {
								winEditNotice.hide();
							}
						}
					}]
});

