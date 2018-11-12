$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");
	var orgTree = $("#org_tree");
	var selectOrgNode = null;

	var parseOrgTree = function(data) {
		var childNodes = new Array();
		for ( var i = 0, l = data.length; i < l; i++) {
			childNodes[i] = {};
			childNodes[i].id = data[i].orgId;
			childNodes[i].text = data[i].orgName.replace(/\.n/g, '.');
			if (data[i].isParent) {
				childNodes[i].state = 'closed';
			}
		}
		return childNodes;
	};

	var treeOnClick = function(node) {
		selectOrgNode = node;
		$(dataTable).datagrid('load', {
			'searchParams[orgId]' : node.id
		});
	};

	$(orgTree).tree({
		url : 'listOrgRoot.json',
		lines : true,
		id : 'parentId',
		onClick : treeOnClick,
		loadFilter : parseOrgTree
	});

	$("#searchBtn").click(function() {
		var searchParams_deptName = $("#searchParams_deptName").val();
		if (searchParams_deptName != "") {
			$(dataTable).datagrid("load", {
				'searchParams[deptName]' : searchParams_deptName
			});
		}
	});

	$("#resetBtn").click(function() {
		$("#searchform input[type='text']").val("");
		$("#searchform input[type='hidden']").val("");
		$(dataTable).datagrid("load", {});
	});

	$("#addBtn").click(function() {
		if (editIndex == -1) {
			initEditor();
			$(dataTable).datagrid("appendRow", {
				orgId : selectOrgNode ? selectOrgNode.id : '',
				orgName : selectOrgNode ? selectOrgNode.text : ''
			});
			editIndex = $(dataTable).datagrid("getRows").length - 1;
			$(dataTable).datagrid("beginEdit", editIndex);
		} else {
			$.messager.alert("提示", "请先保存之后再新增！");
		}
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.deptId;
					});
					var data = doPost('delete.json', arrayObj);
					if (data.state) {
						$.messager.alert("提示", "数据删除成功！", "info", function() {
							$(dataTable).datagrid("reload");
							$(orgTree).tree('reload');
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

	$("#editBtn").click(function() {
		if (editIndex >= 0) {
			$.messager.alert("提示", "您正在进行编辑.");
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
		}
	});

	$("#saveBtn").click(function() {
		var isOk = $(dataTable).datagrid("validateRow", editIndex);
		if (isOk) {
			$(dataTable).datagrid("endEdit", editIndex);
			var rows = $(dataTable).datagrid("getChanges");
			var postData = {
				deptId : rows[0].deptId,
				deptName : rows[0].deptName,
				remarks : rows[0].remarks,
				phone : rows[0].phone,
				deptManager : rows[0].deptManager,
				orgId : rows[0].orgId
			};
			var data = doPost('save.json', postData);
			if (data.state) {
				$.messager.alert("提示", "数据保存成功！", "info", function() {
					$(dataTable).datagrid("reload");
					$(orgTree).tree('reload');
					editIndex = -1;
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
			}
		});
	}

	function initEditor() {
		var columns = [ {
			field : 'deptName',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : {
						chinese : [ 3, 20 ]
					},
					missingMessage : '[部门名称] 为必填项'
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
					width : 180,
					panelWidth : 200,
					required : true,
					url : base + "/platform/dept/listOrgRoot.json",
					loadFilter : parseOrgTree,
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
			field : 'deptManager',
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		}, {
			field : 'phone',
			editor : {
				type : 'validatebox',
				options : {
					required : true
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
