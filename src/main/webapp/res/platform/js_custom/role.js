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
			$(dataTable).datagrid("appendRow", {});
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn").show();
		} else {
			$.messager.alert("提示", "请先保存之后再新增！")
		}
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.roleId;
					});
					var data = doPost('delete.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
						});
					} else {
						$.messager.alert("提示", "数据删除失败,请查看是否已分配了资源.")
					}
				}
			});
		} else {
			$.messager.alert("提示", "请选择要删除的记录。");
		}
	});

	$("#editBtn").click(function() {
		if (editIndex >= 0) {
			$.messager.alert("提示", "您正在进行编辑.");
		}
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].roleId != null) {
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

	$("#saveBtn").click(function() {
		var isOk = $(dataTable).datagrid("validateRow", editIndex);
		if (isOk) {
			$(dataTable).datagrid("endEdit", editIndex);
			var rows = $(dataTable).datagrid("getChanges");
			if (rows.length == 0) {
				$.messager.alert("提示", "数据没有发生变化", "info");
				editIndex = -1;
				$("#resetDataBtn").hide();
				return false;
			}
			var postData = {
				roleId : rows[0].roleId,
				roleName : rows[0].roleName,
				remarks : rows[0].remarks
			};
			var data = doPost('save.json', postData);
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
			field : 'roleName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'chinese', 'length[3,15]' ],
					missingMessage : '[角色名称] 为必填项'
				}
			}
		}, {
			field : 'remarks',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'length[3,20]' ],
					missingMessage : '[备注] 为必填项'
				}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}

	$("#searchBtn").click(function() {
		var searchParams_roleName = $("#searchParams_roleName").val();
		if (searchParams_roleName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[roleName]' : searchParams_roleName
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchParams_roleName").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

});
