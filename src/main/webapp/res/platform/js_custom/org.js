$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");
	var orgTree = $("#org_tree");
	var selectOrgNode = null;

	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	var treeOnClick = function(node) {
		selectOrgNode = node;
		$(dataTable).datagrid('load', {
			'searchParams[parentOrgId]' : node.id
		});
	};

	$(orgTree).tree({
		url : 'listRoot.json',
		lines : true,
		id : 'parentId',
		onClick : treeOnClick
	});

	$("#searchBtn").click(function() {
		var searchParams_orgName = $("#searchParams_orgName").val();
		if (searchParams_orgName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[orgName]' : searchParams_orgName
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchParams_orgName").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {
				parentOrgId : ((selectOrgNode && selectOrgNode.id) ? (selectOrgNode.id) : 0)
			});
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
						arrayObj[index] = data.orgId;
					});
					var data = doPost('delete.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
							$(orgTree).tree('reload');
						});
					} else {
						$.messager.alert("提示", "数据删除失败,该机构下可能存在用户信息!")
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
			if (rows[0].orgId != null) {
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
				orgId : rows[0].orgId,
				orgName : rows[0].orgName,
				remarks : rows[0].remarks,
				parentOrgId : rows[0].parentOrgId,
				authCode : rows[0].authCode,
				orgType : rows[0].orgTypeName
			};
			var data = doPost('save.json', postData);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					$(orgTree).tree('reload');
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
			field : 'orgName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'chinese', 'length[3,20]' ],
					missingMessage : '[机构名称] 为必填项'
				}
			}
		}, {
			field : 'orgTypeName',
			editor : {
				type : 'combobox',
				options : {
					required : true,
					editable : false,
					valueField : 'dictValue',
					textField : 'dictName',
					url : base + '/platform/dictionary/show_dic_by_type.json?typeId=ORG_YTPE',
					missingMessage : '[机构类型] 为必填项'
				}
			}
		}, {
			field : 'authCode',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'integer', 'length[3,50]' ],
					missingMessage : '[权限编码] 为必填项'
				}
			}
		}, {
			field : 'remarks',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '[描述] 为必填项'
				}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}
});
