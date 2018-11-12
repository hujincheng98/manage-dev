$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {
				dictTypeCode : typeCode
			});
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
		} else {
			$.messager.alert("提示", "请先保存之后再新增！");
		}
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm('确认？', '您确定要删除所选记录吗？', function(r) {
				if (r) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.dictCode;
					});
					var deleteNum = doPost('delete_dic.json', arrayObj);
					if (deleteNum == rows.length) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
						});
					} else {
						$.messager.alert("提示", "数据删除过程发现某些资源存在子节点,不能被删除！", "info", function() {
							$(dataTable).datagrid("reload");
						});
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
			$.messager.alert("提示", "只能选择一条记录进行编辑。");
		} else {
			editIndex = $(dataTable).datagrid("getRowIndex", rows[0]);
			initEditor();
			$(dataTable).datagrid("removeEditor", "dictValue");
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
			delete rows[0]._selected;
			var data = doPost('save_dic.json', rows[0]);
			if (data.success) {
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

	var pg = $(dataTable).datagrid("getPager");
	if (pg) {
		$(pg).pagination({
			onBeforeRefresh : function() {
				editIndex = -1;
				$("#resetDataBtn").hide();
			}
		});
	}

	function initEditor() {
		var columns = [ {
			field : 'dictName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'chinese', 'length[3,15]' ],
					missingMessage : '[字典名称] 为必填项'
				}
			}
		}, {
			field : 'dictValue',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						length : [ 1, 20 ],
						remote : [ 'check_dic_value.json', 'dictValue' ]
					},
					missingMessage : '[字典值] 为必填项'
				}
			}
		}, {
			field : 'orderNum',
			editor : {
				type : 'numberbox',
				options : {
					required : true,
					validType : {
						length : [ 1, 4 ]
					},
					missingMessage : '[排序] 为必填项'
				}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}

});
