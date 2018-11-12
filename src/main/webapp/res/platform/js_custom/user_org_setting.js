var selectUserId = null;
$(function() {
	var ts = $("#toSelectOrgTree");
	var sd = $("#selectedOrgTable");
	var dataTable = $("#tableList");
	$("#orgSettingBtn").click(function() {
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
		$(sd).datagrid({
			url : 'list_user_org.json',
			idField : "orgId",
			queryParams : {
				userId : selectUserId
			},
			title : '用户机构权限',
			checkOnSelect : true,
			selectOnCheck : true,
			singleSelect : false,
			rownumbers : true,
			onLoadSuccess : function(data) {
			},
			onSelect : function(rowIndex, rowData) {
				$(sd).datagrid('deleteRow', rowIndex);
			},
			columns : [ [ {
				field : 'orgId',
				title : 'orgId',
				hidden : true
			}, {
				field : 'orgName',
				title : '机构名称',
				width : '40%'
			}, {
				field : 'remarks',
				title : '描述',
				width : '40%'
			} ] ]
		});
		$("#userOrgSettingWin").dialog('open');
		return false;
	});

	$(ts).tree({
		url : 'list_orgTree.json',
		title : '机构信息',
		onClick : function(node) {
			var orgId = node.id;
			var row_index = $(sd).datagrid('getRowIndex', orgId);
			if (row_index == -1) {
				$(sd).datagrid("appendRow", {
					orgId : orgId,
					orgName : node.text,
					remarks : node.attributes.remarks
				});
			}
		}
	});
});
var saveUserOrgSettingBtnEvent = function() {
	var userOrgs = $("#selectedOrgTable").datagrid('getRows');
	var data = {
		userId : selectUserId,
		orgs : userOrgs
	};
	var data = doPost('saveUserOrgs.json', data);
	if (data.state) {
		$.messager.alert("提示", "操作成功！", "info", function() {
			$("#userOrgSettingWin").dialog('close');
		});
	} else {
		$.messager.alert("提示", "操作失败！");
	}
};
var cancelUserOrgSettingBtnEvent = function() {
	$("#userOrgSettingWin").dialog('close');
};