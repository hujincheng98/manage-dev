$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$(dataTable).datagrid({
		url : "list.json",
		idField : "sysId",
		toolbar : "#button-bar",
		showRefresh : false,
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		showHeader : true,
		pagination : true,
		title : "数据权限策略",
		columns : [ [ {
			title : "选择",
			field : "strategyId",
			checkbox : true
		}, {
			title : "策略名称",
			field : "strategyName",
			width : "15%"
		}, {
			title : "表名",
			field : "tableName",
			width : "10%"
		}, {
			title : "字段名",
			field : "columnName",
			width : "10%"
		}, {
			title : "逻辑运算符",
			field : "logicalRelation",
			width : "15%",
			formatter : function(value, row, index) {
				return row.logicalRelationName;
			}
		}, {
			title : "比较运算符",
			field : "comparisonOperator",
			width : "15%",
			formatter : function(value, row, index) {
				return row.comparisonOperatorName;
			}
		}, {
			title : "匹配规则",
			field : "matchingRule",
			width : "30%"
		} ] ]
	});

	var pg = $(dataTable).datagrid("getPager");
	if (pg) {
		$(pg).pagination({
			onBeforeRefresh : function() {
				editIndex = -1;
				$("#resetDataBtn").hide();
			}
		});
	}
	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	$("#searchBtn").click(function() {
		var searchParams_strategyName = $("#searchParams_strategyName").val();
		if (searchParams_strategyName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[strategyName]' : searchParams_strategyName
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchParams_strategyName").textbox('setValue', '');
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
				$.messager.alert("提示", "数据没有发生变化", "info");
				return;
			}
			delete rows[0]._selected;
			delete rows[0].logicalRelationName;
			delete rows[0].comparisonOperatorName;
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
			$.messager.alert("提示", "数据校验失败，请修改。");
		}
	});

	$("#editBtn").click(function() {
		if (editIndex >= 0) {
			$.messager.alert("提示", "您正在进行编辑.");
			return;
		}
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].strategyId != null) {
				$.messager.alert("提示", "只能选择一条记录进行编辑。");
				return;
			}
			rows.remove(0);
		}
		if (rows.length > 1) {
			$.messager.alert("提示", "只能选择一条记录进行编辑。");
		} else {
			editIndex = $(dataTable).datagrid("getRowIndex", rows[0]);
			initEditor();
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
		}
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.strategyId;
					});
					var data = doPost('delete.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
						});
					} else {
						$.messager.alert("提示", "数据删除失败！")
					}
				}
			});
		} else {
			$.messager.alert("提示", "请选择要删除的记录。");
		}
	});

	function initEditor() {
		var columns = [ {
			field : 'strategyName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'length[3,20]' ],
					missingMessage : '[策略名称] 为必填项'
				}
			}
		}, {
			field : 'tableName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						code : [ 3, 20 ],
						remote : [ 'check_table_name.json', 'tableName' ]
					},
					missingMessage : '[表名] 为必填项'
				}
			}
		}, {
			field : 'columnName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						code : [ 3, 20 ]
					},
					missingMessage : '[字段名] 为必填项'
				}
			}
		}, {
			field : 'comparisonOperator',
			editor : {
				type : 'combobox',
				options : {
					editable : false,
					panelHeight : 60,
					url : '../dictionary/show_dic_by_type.json?typeId=SQL_COMPARISON',
					valueField : 'dictValue',
					textField : 'dictName',
					required : true,
					missingMessage : '[比较运算符] 为必填项'
				}
			}
		}, {
			field : 'logicalRelation',
			editor : {
				type : 'combobox',
				options : {
					editable : false,
					panelHeight : 60,
					url : '../dictionary/show_dic_by_type.json?typeId=SQL_LOGICAL',
					valueField : 'dictValue',
					textField : 'dictName',
					required : true,
					missingMessage : '[逻辑运算符] 为必填项'
				}
			}
		}, {
			field : 'matchingRule',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : "pageUrl",
					missingMessage : '[匹配规则] 为必填项'
				}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}
});
