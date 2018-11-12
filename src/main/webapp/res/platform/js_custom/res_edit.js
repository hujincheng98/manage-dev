$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	/**
	 * 定义表格列的拖动事件
	 */

	$(dataTable).datagrid({
		url : tableUrl,
		title : tableTitle,
		idField : "resId",
		toolbar : "#button-bar",
		striped : true,
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		columns : [ [ {
			title : "选择",
			field : "resId",
			checkbox : true
		}, {
			field : "sysId",
			hidden : true
		}, {
			field : "parentId",
			hidden : true
		}, {
			title : "资源名称",
			field : "resName",
			width : "10%"
		}, {
			title : "资源类型",
			field : "resType",
			width : "5%",
			formatter : function(value, row, index) {
				return row.resTypeName;
			}
		}, {
			title : "连接",
			field : "resUrl",
			width : "15%"
		}, {
			title : "基础连接",
			field : "baseResUrl",
			width : "35%"
		}, {
			title : "资源图标",
			field : "resIcon",
			width : "10%"
		}, {
			title : "是否启用",
			field : "isEnabled",
			width : "5%",
			formatter : function(value, row, index) {
				return row.enabledName;
			}
		}, {
			title : "提示信息",
			field : "resTooltip",
			width : "5%"
		}, {
			title : "描述",
			field : "remarks",
			width : "8%"
		}, {
			title : "排序",
			field : "orderNum",
			width : "5%"
		} ] ],
		onLoadSuccess : function() {
			$(this).datagrid('enableDnd');
		},
		onDrop : function(targetRow, sourceRow, point) {
			if (sourceRow != undefined && sourceRow.length == 0) {
				return false;
			}
			var targetNum = targetRow.orderNum;
			targetRow.orderNum = sourceRow.orderNum;
			sourceRow.orderNum = targetNum;
			var objs = new Array();
			delete targetRow._selected;
			delete sourceRow._selected;
			objs[0] = targetRow;
			objs[1] = sourceRow;
			var data = doPost('update.json', objs);
			if (data.state) {
				$.messager.alert("提示", "更改排序成功！", "info", function() {
					$(dataTable).datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "更改排序失败！");
			}
		}
	});

	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			var data = {};
			if (typeof sysId != "undefined") {
				data = {
					sysId : sysId
				}
			}
			if (typeof parentId != "undefined") {
				data = {
					parentId : parentId
				}
			}
			$(dataTable).datagrid("appendRow", data);
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
		} else {
			$.messager.alert("提示", "请先保存之后再新增！")
		}
	});

	$("#deleteBtn")
			.click(
					function() {
						var rows = $(dataTable).datagrid("getChecked");
						if (rows.length > 0) {
							$.messager
									.confirm(
											'确认？',
											'您确定要删除所选记录吗？',
											function(r) {
												if (r) {
													var arrayObj = new Array();
													$(rows)
															.each(
																	function(
																			index,
																			data) {
																		if (data.resId != undefined) {
																			arrayObj[index] = data.resId;
																		}
																	});
													if (arrayObj.length > 0) {
														var data = doPost(
																'delete.json',
																arrayObj);
														if (parseInt(data.deleteNum) == parseInt(rows.length)) {
															$.messager
																	.alert(
																			"提示",
																			"数据删除成功！",
																			"info",
																			function() {
																				reloadAndUncheck();
																			});
														} else {
															$.messager
																	.alert(
																			"提示",
																			"数据删除过程发现某些资源存在子节点,不能被删除！",
																			"info",
																			function() {
																				reloadAndUncheck();
																			});
														}
													} else {
														$.messager
																.alert(
																		"提示",
																		"数据删除成功！",
																		"info",
																		function() {
																			reloadAndUncheck();
																		});
													}
												}
											});
						} else {
							$.messager.alert("提示", "请选择要删除的记录。");
						}
					});

	$("#editBtn").click(function() {
		if (editIndex == -1) {
			var rows = $(dataTable).datagrid("getChecked");
			if (rows.length > 1) {
				$.messager.alert("提示", "只能选择一条记录进行编辑。");
			} else {
				editIndex = $(dataTable).datagrid("getRowIndex", rows[0]);
				initEditor();
				$(dataTable).datagrid("removeEditor", "resUrl");
				$(dataTable).datagrid("beginEdit", editIndex);
				$("#resetDataBtn").show();
			}
		} else {
			$.messager.alert("提示", "请先保存之后再编辑其他行！")
		}
	});

	$("#saveBtn").click(function() {
		var isOk = $(dataTable).datagrid("validateRow", editIndex);
		if (isOk) {
			$(dataTable).datagrid("endEdit", editIndex);
			var rows = $(dataTable).datagrid("getChanges", "updated");
			if (rows == undefined || rows.length == 0) {
				rows = $(dataTable).datagrid("getChanges", "inserted");
			}
			delete rows[0]._selected;
			rows[0].resTypeName = "";
			var data = doPost('save.json', rows[0]);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					parent.reloadTree();
					editIndex = -1;
					$("#resetDataBtn").hide();
				});
			} else {
				$.messager.alert("提示", "数据保存失败！")
			}
		} else {
			$.messager.alert("提示", "数据校验失败，请修改。");
		}
	});

	$("#searchBtn").click(function() {
		var sysName = $("#sysName").val();
		var contentPath = $("#contentPath").val();
		if (sysName != "" || contentPath != "") {
			$(dataTable).datagrid("load", {
				'searchParams[sysName]' : sysName,
				'searchParams[contentPath]' : contentPath
			});
		}
	});

	function reloadAndUncheck() {
		parent.reloadTree();
		$(dataTable).datagrid("reload");
		$(dataTable).datagrid("clearChecked");
	}

	function initEditor() {
		var columns = [
				{
					field : 'resName',
					editor : {
						type : 'validatebox',
						options : {
							required : true,
							validType : {
								chinese : [ 3, 20 ]
							},
							missingMessage : '[资源名称] 为必填项'
						}
					}
				},
				{
					field : 'resType',
					editor : {
						type : 'combobox',
						options : {
							panelHeight : 60,
							url : '../dictionary/show_dic_by_type.json?typeId=RES_TYPE',
							valueField : 'dictValue',
							textField : 'dictName',
							required : true,
							missingMessage : '[资源类型] 为必填项'
						}
					}
				},
				{
					field : 'resUrl',
					editor : {
						type : 'validatebox',
						options : {
							required : true,
							validType : "resUrl",
							missingMessage : '[连接] 为必填项'
						}
					}
				},
				{
					field : 'baseResUrl',
					editor : {
						type : 'textbox',
						options : {
							validType : "baseResUrl"
						}
					}
				},
				{
					field : 'resIcon',
					editor : {
						type : 'combobox',
						options : {
							panelHeight : 200,
							url : '../dictionary/show_dic_by_type.json?typeId=ICONS',
							valueField : 'dictValue',
							textField : 'dictName',
							required : true,
							missingMessage : '[资源图标] 为必填项',
							formatter : function(row) {
								return '<img class="item-img" src="'
										+ row.dictName + '" />';
							}
						}
					}
				},
				{
					field : 'isEnabled',
					editor : {
						type : 'combobox',
						options : {
							panelHeight : 60,
							url : '../dictionary/show_dic_by_type.json?typeId=YES_OR_NO',
							valueField : 'dictValue',
							textField : 'dictName',
							required : true,
							missingMessage : '[是否启用] 为必填项'
						}
					}
				}, {
					field : 'resTooltip',
					editor : {
						type : 'text',
						options : {
							required : true
						}
					}
				}, {
					field : 'remarks',
					editor : {
						type : 'text',
						options : {
							required : true
						}
					}
				}, {
					field : 'orderNum',
					editor : {
						type : 'numberbox',
						options : {
							required : true
						}
					}
				} ];
		$(dataTable).datagrid('addEditor', columns);
	}

});
