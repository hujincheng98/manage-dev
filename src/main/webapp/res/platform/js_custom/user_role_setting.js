var selectUserId = null;
$(function() {
	var ts = $("#toSelectRoleTable");
	var sd = $("#selectedRoleTable");
	var dataTable = $("#tableList");
	$("#roleSettingBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].userId != null) {
				$.messager.alert("提示", "只能选择一条记录进行操作.");
				return;
			}
			rows.remove(0);
		}
		if (rows.length != 1) {
			$.messager.alert("提示", "请选择一条记录进行操作.");
			return false;
		}
		selectUserId = rows[0].userId;
		$(ts).datagrid('rejectChanges');
		$(sd).datagrid({
			url : 'list_user_role.json',
			idField : "roleId",
			queryParams : {
				userId : selectUserId
			},
			title : '用户角色',
			checkOnSelect : true,
			selectOnCheck : true,
			singleSelect : false,
			rownumbers : true,
			onLoadSuccess : function(data) {
				var length = data.total;
				var rows = data.rows;
				for ( var i = 0; i < length; i++) {
					var row_index = $(ts).datagrid('getRowIndex', rows[i].roleId);
					$(ts).datagrid('deleteRow', row_index);
				}
			},
			onSelect : function(rowIndex, rowData) {
				$(ts).datagrid('appendRow', rowData);
				$(sd).datagrid('deleteRow', rowIndex);
			},
			columns : [ [ {
				field : 'roleId',
				title : 'roleId',
				hidden : true
			}, {
				field : 'roleName',
				title : '角色名称',
				width : '40%'
			}, {
				field : 'remarks',
				title : '描述',
				width : '40%'
			} ] ]
		});
		$("#userRoleSettingWin").dialog('open');
		return false;
	});

	$(ts).datagrid({
		url : 'list_role.json',
		title : '角色列表',
		idField : "roleId",
		checkOnSelect : true,
		selectOnCheck : true,
		singleSelect : false,
		rownumbers : true,
		onSelect : function(rowIndex, rowData) {
			$(sd).datagrid('appendRow', rowData);
			$(ts).datagrid('deleteRow', rowIndex);
		},
		columns : [ [ {
			field : 'roleId',
			title : 'roleId',
			hidden : true
		}, {
			field : 'roleName',
			title : '角色名称',
			width : '40%'
		}, {
			field : 'remarks',
			title : '描述',
			width : '40%'
		} ] ]
	});
});
var saveUserRoleSettingBtnEvent = function() {
	var userRoles = $("#selectedRoleTable").datagrid('getRows');
	var data = {
		userId : selectUserId,
		roles : userRoles
	};
	var data = doPost('saveUserRoles.json', data);
	if (data.state) {
		$.messager.alert("提示", "操作成功！", "info", function() {
			$("#userRoleSettingWin").dialog('close');
		});
	} else {
		$.messager.alert("提示", "操作失败！");
	}
};
var cancelUserRoleSettingBtnEvent = function() {
	$("#userRoleSettingWin").dialog('close');
};