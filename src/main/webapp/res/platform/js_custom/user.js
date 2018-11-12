$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$("#addBtn").click(function() {
		$(".password_input").show();
		$("#addUserWin").dialog('open');
		$("#addUserWin").dialog('setTitle', '新增用户信息');
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					var flag = false;
					$(rows).each(function(index, data) {
						arrayObj[index] = data.userId;
						if (data.supperManager == true) {
							flag = true;
						}
					});
					if (flag == true) {
						$.messager.alert("提示", "管理员用户不允许删除!", "warning");
						return;
					}
					var data = doPost('delete.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
						});
					} else {
						$.messager.alert("提示", "数据删除失败！");
					}
				}
			});
		} else {
			$.messager.alert("提示", "请选择要删除的记录。");
		}
	});

	$("#resetBtn").click(function() {
		$("#user_search_text").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#searchBtn").click(function() {
		var search = $("#user_search_text").val();
		if (search != "") {
			$(dataTable).datagrid("load", {
				'searchParams[search]' : search
			});
		}
	});

	$("#enableBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			var arrayObj = new Array();
			$(rows).each(function(index, data) {
				arrayObj[index] = data.userId;
			});
			var data = doPost('enable.json', arrayObj);
			if (data.state) {
				$.messager.alert("提示", "操作成功！", "info", function() {
					$(dataTable).datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "操作失败！")
			}
		} else {
			$.messager.alert("提示", "请选择要操作的记录。");
		}
	});

	$("#unLockBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			var arrayObj = new Array();
			$(rows).each(function(index, data) {
				arrayObj[index] = data.userId;
			});
			var data = doPost('unLock.json', arrayObj);
			if (data.state) {
				$.messager.alert("提示", "操作成功！", "info", function() {
					$(dataTable).datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "操作失败！")
			}
		} else {
			$.messager.alert("提示", "请选择要操作的记录。");
		}
	});

	$("#resetPWDBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			var arrayObj = new Array();
			$(rows).each(function(index, data) {
				arrayObj[index] = data.userId;
			});
			var data = doPost('resetPassword.json', arrayObj);
			if (data.state) {
				$.messager.alert("提示", "操作成功！", "info");
			} else {
				$.messager.alert("提示", "操作失败！")
			}
		} else {
			$.messager.alert("提示", "请选择要操作的记录。");
		}
	});

	$("#disableBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要禁用选择的 " + rows.length + " 条用户信息?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.userId;
					});
					var data = doPost('disable.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据操作成功！", "info", function() {
							$(dataTable).datagrid("reload");
						});
					} else {
						$.messager.alert("提示", "数据操作失败！")
					}
				}
			});
		} else {
			$.messager.alert("提示", "请选择要操作的记录。");
		}
	});

	$("#editBtn").click(function() {
		if (editIndex >= 0) {
			$.messager.alert("提示", "您正在进行编辑.");
		}
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].userId != null) {
				$.messager.alert("提示", "只能选择一条记录进行编辑。");
				return;
			}
			rows.remove(0);
		}
		if (rows.length > 1) {
			$.messager.alert("提示", "只能选择一条记录进行编辑。");
		} else {
			$(".password_input").hide();
			var data = doPost('getById.json', rows[0].userId);
			$("#addUserWin").dialog('open');
			$("#addUserWin").dialog('setTitle', '编辑用户信息');
			$("#user_form").fromJson(data);
			$('#org_select').combotree('setValue', data.org.orgId);
			$('#org_select').parent().find("input[type='text']").val(data.orgName);
			$('#group_select').combogrid('setValue', data.groupId);
			$('#user_type_select').combogrid('setValue', data.userType);
			$('#user_sex_select').combogrid('setValue', data.sex);
			$('#user_birthday_date').datebox('setValue', data.birthday);
		}
	});

	var pg = $(dataTable).datagrid("getPager");
	if (pg) {
		$(pg).pagination({
			onBeforeRefresh : function() {
				editIndex = -1;
			}
		});
	}

});

var saveAddUserBtnEvent = function() {
	var isValid = $("#user_form").form('validate');
	if (!isValid) {
		return false;
	}
	var postData = $("#user_form").toJson();
	var data = doPost('save.json', postData);
	if (data.state) {
		$.messager.alert("提示", "操作成功！", "info", function() {
			$("#tableList").datagrid("reload");
			$("#user_form").form('clear');
			$("#addUserWin").dialog('close');
		});
	} else {
		$.messager.alert("提示", "操作失败！")
	}
};

var cancelAddUserBtnEvent = function() {
	$("#addUserWin").dialog('close');
};

var addUserWinBeforClose = function() {
	$("#user_form").form('clear');
	return true;
};
