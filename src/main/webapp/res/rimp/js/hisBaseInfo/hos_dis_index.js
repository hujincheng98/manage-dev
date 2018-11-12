$.extend($.fn.datagrid.defaults.editors,{
					combogrid : {
						init : function(container, options) {
							var input = $(
									'<input type="text" class="datagrid-editable-input"/>')
									.appendTo(container);
							input.combogrid(options);
							return input;
						},
						destroy : function(target) {
							$(target).combogrid('destroy');
						},
						getValue : function(target) {
							return $(target).combogrid('getValue');
						},
						setValue : function(target, value) {
							$(target).combogrid('setValue', value);
						},
						resize : function(target, width) {
							$(target).combogrid('resize', width);
						}
					}
				});
$(function() {
	var datagrid; // 定义全局变量datagrid
	var editRow = undefined; // 定义全局变量：当前编辑的行
	datagrid = $("#hosdisinfo")
			.datagrid({
						title : "病区管理",
						fit : true,//缩放窗口的时候适应
						rownumbers : true,
						singleSelect : true,
						nowrap : true,//把数据显示在一行里
						url : 'list.json', // 请求的数据源
						pagination : true, // 显示分页
						pageSize : 10, // 页大小
						pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
						striped : true, // 行背景交换
						idField : 'disId', // 主键
						remoteSort : false,//前端排序这个是必须的
						columns : [ [// 显示的列
						{
							field : 'disId',
							title : '',
							checkbox : true
						},{
							field : 'createDate',
							sortable : true,
							hidden : true//用来默认排序的
						}, {
							field : 'disName',
							title : '病区名称',
							width : '16%',
							sortable : true,
							editor : {
								type : 'validatebox',
								/*options : {
									required : true,
									validType : {
										remote : ['checkDisName.json','param']
									},
									invalidMessage : '病区名称已存在，请更换其他名称'
								}*/
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								}
							}
						}, {
							field : 'hosId',
							title : '所属医院',
							width : '17%',
							sortable : true,
							formatter: function(value,row,index){
									return row.hosName;
							},
							editor : {
								type : 'combogrid',
								options : {
									width : 170,
									panelWidth : 482,
									editable:false,
									pagination : true,// 是否分页
									idField : 'hosId',
									textField : 'hosName',
									url : platRoot + "/rimp/hos/list.json",
									columns : [ [ {
										field : 'hosId',
										title : '医院编号',
										hidden : true
									}, {
										field : 'hosName',
										title : '医院名称',
										width : 120
									}, {
										field : 'hosLevelName',
										title : '医院等级',
										width : 120
									}, {
										field : 'hosAddr',
										title : '医院地址',
										width : 120
									}, {
										field : 'hosTelep',
										title : '联系电话',
										width : 120
									} ] ]
								}
							}
						}, {
							field : 'disPhone',
							title : '病区电话',
							width : '12%',
							sortable : true,
							editor : {
								type : 'textbox',
								options : {
									required : false,
									validType : 'mobileAndTel'
								}
							}
						},{
							field : 'disSite',
							title : '病区位置',
							width : '26%',
							sortable : true,
							editor : {
								type : 'validatebox',
								options : {
									required : true
								}
							}
						}, {
							field : 'remarks',
							title : '备注',
							width : '26%',
							sortable : true,
							editor : {
								type : 'validatebox',
								options : {
									required : false
								}
							}
						} ] ],
						toolbar : "#button-bar",
						onAfterEdit : function(rowIndex, rowData, changes) {
//							console.log(datagrid.datagrid("validateRow",rowIndex));
							// endEdit该方法触发此事件
							delete rowData.createDate;
							delete rowData.createUser;
							delete rowData.updateDate;
							delete rowData.updateUser;
							delete rowData.hosInfo;
							datagrid.datagrid("loading");
							if(rowData.disId){//更新
								$.ajax({
									url : "update.json",
									dataType : "json",
									type : "post",
									data : rowData,
									error : function() {
										datagrid.datagrid("loaded");
										$.messager.alert("出错了");
									},
									success : function(data) {
										datagrid.datagrid("loaded");
										if (data.state=="success") {
											btnEnState();
											$.messager.alert('病区管理', data.message,
													'info');
											datagrid.datagrid('reload');
										} else {
											datagrid.datagrid("beginEdit", rowIndex);
											editRow=rowIndex;
											// 把当前开启编辑的行赋值给全局变量editRow
											$.messager.alert('病区管理', data.message,'warning');
										}
									}
								})
							}else{//新增
								$.ajax({
									url : "save.json",
									dataType : "json",
									type : "post",
									data : rowData,
									error : function() {
										$.message.alert("出错了");
									},
									success : function(data) {
										datagrid.datagrid("loaded");
										if (data.state=="success") {
											btnEnState();
											$.messager.alert('病区管理', data.message,'info');
											datagrid.datagrid('reload');
										} else {
											editRow = 0;
											datagrid.datagrid("beginEdit", editRow);
											// 把当前开启编辑的行赋值给全局变量editRow
											$.messager.alert('病区管理', data.message,
													'warning');
										}
									}
								})
							}
							editRow = undefined;
						}/*,
						onDblClickRow : function(rowIndex, rowData) {
							// 双击开启编辑行
							if (editRow != undefined) {
								$.messager.alert('病区管理', '请先保存或取消正在编辑的行','warning');
								return;
							}
							if (editRow == undefined) {
								datagrid.datagrid("unselectAll");
								datagrid.datagrid("beginEdit", rowIndex);
								editRow = rowIndex;
							}
						}*/
					});
	
	datagrid.datagrid('hiddenHeaderChecked', false);
	
	//新增按钮
	$btnAdd = $("#addDis").linkbutton({
		onClick:function(){
			// 添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
			if (editRow != undefined) {
				$.messager.alert('病区管理', "请先保存或取消正在编辑的行",'warning');
				return;
//				datagrid.datagrid("endEdit",editRow);
			}
			// 添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
			if (editRow == undefined) {
				btnAddState();
				datagrid.datagrid("insertRow", {
					index : 0, // index start with
								// 0
					row : {

					}
				});
				// 将新插入的那一行开户编辑状态
				datagrid.datagrid("beginEdit", 0);
				// 给当前编辑的行赋值
				editRow = 0;
			}
		}
	});//新增按钮
	//修改按钮
	$btnEdit = $("#editDis").linkbutton({
		onClick:function(){
			// 修改时要获取选择到的行
			var rows = datagrid
					.datagrid("getSelections");
			// 如果只选择了一行则可以进行修改，否则不操作
			if (rows.length == 1) {
				// 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
				if (editRow != undefined) {
					$.messager.alert('病区管理', '请先保存或取消正在编辑的行','warning');
					return;
//					datagrid.datagrid("endEdit",editRow);
				}
				// 当无编辑行时
				if (editRow == undefined) {
					btnEditState();
					// 获取到当前选择行的下标
					var index = datagrid.datagrid(
							"getRowIndex", rows[0]);
					// 开启编辑
					datagrid.datagrid("beginEdit",
							index);
					// 把当前开启编辑的行赋值给全局变量editRow
					editRow = index;
					// 当开启了当前选择行的编辑状态之后，
					// 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
					datagrid
							.datagrid("unselectAll");
				}
			}
		}
	});//修改按钮
	//删除按钮
	$btnDel = $("#delete").linkbutton({
		onClick:function(){
			// 删除时先获取选择行
			var rows = datagrid
					.datagrid("getSelections");
			// 选择要删除的行
			if (rows.length > 0) {
				$.messager
						.confirm(
								"提示",
								rows.length>1?"是否删除这些数据":"是否删除该条数据",
								function(r) {
									if (r) {
										var ids = [];
										for (var i = 0; i < rows.length; i++) {
											ids
													.push(rows[i].disId);
										}
										// 将选择到的行存入数组并用,分隔转换成字符串，
										// 本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
										datagrid
												.datagrid("loading");
										$
												.ajax({
													url : "delete.json",
													dataType : "json",
													type : "post",
													data : {
														ids : ids
													},
													error : function() {
														$.message
																.alert("出错了");
													},
													success : function(
															data) {
														datagrid
																.datagrid("loaded");
														if (data.success) {
															$.messager
																	.alert(
																			'病区管理',
																			data.msg,
																			'info');
															datagrid
																	.datagrid('reload');
														} else {
															$.messager
																	.alert(
																			'病区管理',
																			data.msg,
																			'warning');
														}
														datagrid.datagrid("loaded");
														datagrid.datagrid("unselectAll");//防止删除后不能编辑
													}
												})
									}
								});
			} else {
				$.messager.alert("提示", "请选择要删除的行",
						"error");
			}
		}
	});//删除按钮
	//保存按钮
	$btnSave = $("#save").linkbutton({
//		disabled:true,
		onClick:function() {
			datagrid.datagrid("endEdit", editRow);
		}
	});//保存按钮
	//取消
	$btnCancel = $("#cancel").linkbutton({
//		disabled:true,
		onClick:function() {
			if (editRow == undefined) {
				return;
			}
			// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
			$.messager.confirm('提示:','您确认要取消吗?',function(r){ 
				if(r){ 
					// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
					btnEnState();
					editRow = undefined;
					datagrid.datagrid("rejectChanges");
					datagrid.datagrid("unselectAll");
				}
			}); 
		}
	});//取消按钮
	$("#hosdis-queryBtn").click(function() {
		var json = $("#searchForm").toJson();
		datagrid.datagrid("load", json);
		editRow = undefined;
		return false;
	});
	/**
	 * 重置
	 */
	$("#hosdis-resetBtn").click(function() {
		$("#searchForm").form('clear');
		 datagrid.datagrid("load",{});
		editRow = undefined;
		return false;
	});
	function btnAddState(){
		$btnAdd.linkbutton('disable');
		$btnEdit.linkbutton('disable');
		$btnDel.linkbutton('disable');
		$btnCancel.linkbutton('enable');
		$btnSave.linkbutton('enable');
	}
	function btnEditState(){
		$btnAdd.linkbutton('disable');
		$btnEdit.linkbutton('disable');
		$btnDel.linkbutton('disable');
		$btnCancel.linkbutton('enable');
		$btnSave.linkbutton('enable');
	}
	function btnEnState(){
		$btnEdit.linkbutton('enable');
		$btnDel.linkbutton('enable');
		$btnAdd.linkbutton('enable');
		$btnCancel.linkbutton('disable');
		$btnSave.linkbutton('disable');
	}
});