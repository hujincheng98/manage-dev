$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList_roleAuth");

	$(dataTable).datagrid({
		url : "list_roleAuth.json",
		idField : "sysId",
		toolbar : "#button-bar_roleAuth",
		showRefresh : false,
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		showHeader : true,
		pagination : true,
		title : "角色数据权限",
		columns : [ [ {
			title : "选择",
			field : "roleDataAuthId",
			checkbox : true
		}, {
			title : "ROLE_ID",
			field : "roleId",
			width : "15%",
			hidden : true
		}, {
			title : "角色名称",
			field : "roleName",
			width : "15%"
		}, {
			title : "表格名称",
			field : "tableName",
			width : "10%"
		}, {
			title : "权限值",
			field : "authorityScope",
			width : "10%"
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

	$("#resetDataBtn_roleAuth").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn_roleAuth").hide();
	});

	$("#searchBtn_roleAuth").click(function() {
		var searchParams_roleName = $("#searchParams_roleName").val();
		var searchParams_tableName = $("#searchParams_tableName_roleAuth").val();
		if (searchParams_roleName != "" || searchParams_tableName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[roleName]' : searchParams_roleName.length > 0 ? searchParams_roleName : "",
				'searchParams[tableName]' : searchParams_tableName.length > 0 ? searchParams_tableName : "",
			});
		}
	});

	$("#resetBtn_roleAuth").click(function() {
		$("#searchParams_roleName").textbox('setValue', '');
		$("#searchParams_tableName_roleAuth").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#addBtn_roleAuth").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {});
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
			$("#resetDataBtn_roleAuth").show();
		} else {
			$.messager.alert("提示", "请先保存之后再新增！")
		}
	});

	$("#saveBtn_roleAuth").click(function() {
		var isOk = $(dataTable).datagrid("validateRow", editIndex);
		if (isOk) {
			$(dataTable).datagrid("endEdit", editIndex);
			var rows = $(dataTable).datagrid("getChanges", "updated");
			if (rows == undefined || rows.length == 0) {
				rows = $(dataTable).datagrid("getChanges", "inserted");
			}
			if (rows.length == 0) {
				editIndex = -1;
				$("#resetDataBtn_roleAuth").hide();
				$.messager.alert("提示", "数据没有发生变化", "info");
				return;
			}
			delete rows[0]._selected;
			var data = doPost('saveRoleAuth.json', rows[0]);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					editIndex = -1;
					$("#resetDataBtn_roleAuth").hide();
				});
			} else {
				$.messager.alert("提示", "数据保存失败,请检查数据是否已经存在!");
				$(dataTable).datagrid("reload");
			}
		} else {
			$.messager.alert("提示", "数据校验失败，请修改。");
		}
	});

	$("#editBtn_roleAuth").click(function() {
		if (editIndex >= 0) {
			$.messager.alert("提示", "您正在进行编辑.");
			return;
		}
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].roleDataAuthId != null) {
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
			$("#resetDataBtn_roleAuth").show();
		}
	});

	$("#deleteBtn_roleAuth").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.roleDataAuthId;
					});
					var data = doPost('deleteRoleAuth.json', arrayObj);
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
			field : 'roleId',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '[RoleID] 为必填项'
				}
			}
		}, {
			field : 'roleName',
			editor : {
				type : 'combogrid',
				options : {
					required : true,
					panelWidth : 480,
					idField : 'roleName',
					textField : 'roleName',
					pagination : true,// 是否分页
					singleSelect : true,// 只允许选择一行记录
					fitColumns : true,
					mode : 'remote',// 远程连接方式
					method : 'POST',// 请求方式
					dataType : 'json',
					url : "listRole.json",
					required : false,
					columns : [ [ {
						field : 'roleName',
						title : '姓名 ',
						width : 120
					}, {
						field : 'remarks',
						title : '描述',
						width : 240
					} ] ],
					keyHandler : {
						up : function() {
							default_comboxgrid_keyUp(this);
						},
						down : function() {
							default_comboxgrid_keyDown(this);
						},
						enter : function() {
							default_comboxgrid_enterKey(this);
						},
						query : function(searchKey) {
							if (searchKey != null) {
								var grid = $(this).combogrid("grid");
								var params = {
									'searchParams[search]' : searchKey
								};
								var url = "listRole.json";
								$(grid).datagrid('options').queryParams = params;
								$(grid).datagrid('options').url = url;
								$(grid).datagrid('reload');
								$(this).combogrid("setValue", searchKey);
							}
						}
					},
					onClickRow : function(rowIndex, rowData) {
						if (rowData != null) {
							var roleId = $(dataTable).datagrid("getEditor", {
								index : editIndex,
								field : 'roleId'
							});
							$(roleId.target).val(rowData.roleId);
						}
					}
				}
			}
		}, {
			field : 'tableName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '[表名] 为必填项'
				}
			}
		}, {
			field : 'authorityScope',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '[权限值] 为必填项'
				}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}
});
