$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	/**
	 * 定义表格列的拖动事件
	 */

	$(dataTable).datagrid({
		url : "list.json",
		idField : "sysId",
		toolbar : "#button-bar",
		showRefresh : false,
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		showHeader : true,
		title : "系统列表",
		columns : [ [ {
			title : "选择",
			field : "sysId",
			checkbox : true
		}, {
			title : "系统名称",
			field : "sysName",
			width : "10%"
		}, {
			title : "应用路径",
			field : "contentPath",
			width : "10%"
		}, /*
			 * { title : "模板路径", field : "templateFloder", width : "10%" }, {
			 * title : "登录路径", field : "loginUrl", width : "15%" }, { title :
			 * "注销路径", field : "logoutUrl", width : "15%" }, { title : "管理首页",
			 * field : "welcomeUrl", width : "10%" },
			 */{
			title : "描述",
			field : "remarks",
			width : "15%"
		}, {
			title : "排序",
			field : "orderNum",
			width : "10%"
		} ] ],
		dragSelection : true,
		onLoadSuccess : function() {
			$(this).datagrid('enableDnd');
			$("#resetDataBtn").hide();
		},
		onDrop : function(targetRow, sourceRow, point) {
			if (sourceRow != undefined && sourceRow.length == 0) {
				return false;
			}
			var targetNum = targetRow.orderNum;
			targetRow.orderNum = sourceRow.orderNum;
			targetRow.resources = null;
			sourceRow.orderNum = targetNum;
			sourceRow.resources = null;
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

	$("#resetBtn").click(function() {
		$("#sysName").textbox('setValue', '');
		$("#contentPath").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {});
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
		} else {
			$.messager.alert("提示", "请先保存之后再新增！")
		}
	});

	$("#deleteBtn").click(
			function() {
				var rows = $(dataTable).datagrid("getChecked");
				if (rows.length > 0) {
					$.messager.confirm('确认？', '您确定要删除所选记录吗？', function(r) {
						if (r) {
							var arrayObj = new Array();
							var isDeleteInDB = true;
							$(rows).each(
									function(index, data) {
										if (data.sysId != undefined) {
											arrayObj[index] = data.sysId;
										} else {
											if (rows.length == 1) {
												var rowIndex = $(dataTable)
														.datagrid(
																"getRowIndex",
																data);
												$(dataTable).datagrid(
														"deleteRow", rowIndex);
												$.messager.alert("提示",
														"数据删除成功！", "info");
												editIndex = -1;
												isDeleteInDB = false;
											}
										}
									});
							if (isDeleteInDB) {
								var data = doPost('delete.json', arrayObj);
								if (data.state) {
									$.messager
											.alert("提示", "数据删除成功！", "info",
													function() {
														$(dataTable).datagrid(
																"reload");
													});
								} else {
									$.messager.alert("提示", "数据删除失败！")
								}
							}
						}
					});
				} else {
					$.messager.alert("提示", "请选择要删除的记录。");
				}
			});

	$("#editBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 1) {
			$.message.alert("提示", "只能选择一条记录进行编辑。");
		} else {
			editIndex = $(dataTable).datagrid("getRowIndex", rows[0]);
			initEditor();
			$(dataTable).datagrid("removeEditor", "contentPath");
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
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
			if (rows.length == 0) {
				editIndex = -1;
				$("#resetDataBtn").hide();
				return;
			}
			delete rows[0]._selected;
			var data = doPost('save.json', rows[0]);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					editIndex = -1;
					$("#resetDataBtn").hide();
				});
			} else {
				$.messager.alert("提示", "数据保存失败！")
			}
		} else {
			$.messager.alert("提示", "请检查红色警告区域的数据填写格式是否正确。");
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

	function initEditor() {
		var columns = [ {
			field : 'sysName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						chinese : [ 3, 20 ]
					},
					missingMessage : '[系统名称] 为必填项'
				}
			}
		}, {
			field : 'contentPath',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						english : [ 3, 15 ],
						remote : [ 'check_content_path.json', 'contentPath' ]
					},
					missingMessage : '[应用路径] 为必填项'
				}
			}
		},/*
			 * { field : 'templateFloder', editor : { type : 'validatebox',
			 * options : { required : true, validType : "english",
			 * missingMessage : '[模板路径] 为必填项' } } }, { field : 'loginUrl',
			 * editor : { type : 'validatebox', options : { required : true,
			 * validType : "pageUrl", missingMessage : '[登录路径] 为必填项' } } }, {
			 * field : 'logoutUrl', editor : { type : 'validatebox', options : {
			 * required : true, validType : "pageUrl", missingMessage : '[注销路径]
			 * 为必填项' } } }, { field : 'welcomeUrl', editor : { type :
			 * 'validatebox', options : { required : true, validType :
			 * "pageUrl", missingMessage : '[首页路径] 为必填项' } } },
			 */
		{
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
