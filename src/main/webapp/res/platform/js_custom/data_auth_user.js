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
		title : "用户数据权限",
		columns : [ [ {
			title : "选择",
			field : "userDataAuthId",
			checkbox : true
		}, {
			title : "用户ID",
			field : "userId",
			width : "15%",
			hidden : true
		}, {
			title : "用户姓名",
			field : "userName",
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

	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	$("#searchBtn").click(function() {
		var searchParams_userName = $("#searchParams_userName").val();
		var searchParams_tableName = $("#searchParams_tableName").val();
		if (searchParams_userName != "" || searchParams_tableName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[userName]' : searchParams_userName.length > 0 ? searchParams_userName : "",
				'searchParams[tableName]' : searchParams_tableName.length > 0 ? searchParams_tableName : "",
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchParams_userName").textbox('setValue', '');
		$("#searchParams_tableName").textbox('setValue', '');
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
			var data = doPost('save.json', rows[0]);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					editIndex = -1;
					$("#resetDataBtn").hide();
				});
			} else {
				$.messager.alert("提示", "数据保存失败,请检查数据是否已经存在!");
				$(dataTable).datagrid("reload");
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
			if (rows[0].userDataAuthId != null) {
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
						arrayObj[index] = data.userDataAuthId;
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
			field : 'userId',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '[用户ID] 为必填项'
				}
			}
		}, {
			field : 'userName',
			editor : {
				type : 'combogrid',
				options : {
					required : true,
					panelWidth : 480,
					idField : 'realName',
					textField : 'realName',
					pagination : true,// 是否分页
					singleSelect : true,// 只允许选择一行记录
					fitColumns : true,
					mode : 'remote',// 远程连接方式
					method : 'POST',// 请求方式
					dataType : 'json',
					url : "listUser.json",
					required : false,
					columns : [ [ {
						field : 'realName',
						title : '姓名 ',
						width : 120
					}, {
						field : 'orgName',
						title : '所属机构',
						width : 120
					}, {
						field : 'idNumber',
						title : '身份证号码',
						width : 120
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
								var url = "listUser.json";
								$(grid).datagrid('options').queryParams = params;
								$(grid).datagrid('options').url = url;
								$(grid).datagrid('reload');
								$(this).combogrid("setValue", searchKey);
							}
						}
					},
					onClickRow : function(rowIndex, rowData) {
						if (rowData != null) {
							var userId = $(dataTable).datagrid("getEditor", {
								index : editIndex,
								field : 'userId'
							});
							$(userId.target).val(rowData.userId);
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
