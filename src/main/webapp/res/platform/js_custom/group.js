$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$("#resetDataBtn").click(function() {
		$(dataTable).datagrid("rejectChanges");
		editIndex = -1;
		$("#resetDataBtn").hide();
	});

	$("#searchBtn").click(function() {
		var searchParams_groupName = $("#searchParams_groupName").val();
		if (searchParams_groupName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[groupName]' : searchParams_groupName
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchParams_groupName").textbox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {
				status : 1,
				orgId : '',
				orgName : ''
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
						arrayObj[index] = data.groupId;
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
			return;
		}
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].deptId != null) {
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
			var postData = {
				groupId : rows[0].groupId,
				groupName : rows[0].groupName,
				orgId : rows[0].orgId,
				status : rows[0].status,
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
				$(dataTable).datagrid("beginEdit", editIndex);
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
			field : 'groupName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'chinese', 'length[3,20]' ],
					missingMessage : '[用户组名称] 为必填项'
				}
			}
		}, {
			field : 'orgId',
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		}, {
			field : 'orgName',
			editor : {
				type : 'combotree',
				options : {
					panelWidth : 200,
					required : true,
					url : 'list_orgTree.json',
					onChange : function(newValue, oldValue) {
						var t = $(this).combotree('tree'); // 获取树对象
						var n = t.tree('getSelected'); // 获取选择的节点
						if (!n) {
							return false;
						}
						var orgId = $(dataTable).datagrid("getEditor", {
							index : editIndex,
							field : 'orgId'
						});
						$(orgId.target).val(n.id);
						var orgName = $(dataTable).datagrid("getEditor", {
							index : editIndex,
							field : 'orgName'
						});
						$(orgName.target).combotree("setValue", n.text);
						return false;
					}
				}
			}
		}, {
			field : 'status',
			editor : {
				type : 'validatebox'
			}
		}, {
			field : 'statusName',
			editor : {
				type : 'combobox',
				options : {
					required : true,
					editable : false,
					valueField : 'v',
					textField : 't',
					data : [ {
						t : '是',
						v : 'true'
					}, {
						t : '否',
						v : 'false'
					} ],
					onChange : function(newValue, oldValue) {
						var status = $(dataTable).datagrid("getEditor", {
							index : editIndex,
							field : 'status'
						});
						$(status.target).val(newValue);
					},
					missingMessage : '[是否启用] 为必填项'
				}
			}
		}, {
			field : 'remarks',
			editor : {
				type : 'validatebox',
				options : {}
			}
		} ];
		$(dataTable).datagrid('addEditor', columns);
	}

});
