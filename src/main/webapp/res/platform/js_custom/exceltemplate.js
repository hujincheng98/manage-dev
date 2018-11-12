$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

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
		$("#add_frame").attr("src", "toAdd.do");
		$("#addETWin").dialog('open');
	});

	$("#deleteBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0) {
			$.messager.confirm("提示", "确定要删除选择的 " + rows.length + " 条记录?", function(flag) {
				if (flag) {
					var arrayObj = new Array();
					$(rows).each(function(index, data) {
						arrayObj[index] = data.etId;
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
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 2) {
			if (rows[0].etId != null) {
				$.messager.alert("提示", "只能选择一条记录进行编辑。");
				return;
			}
			rows.remove(0);
		}
		if (rows.length > 1) {
			if (rows.length == 2) {
				if (rows[0].etId != null) {
					$.messager.alert("提示", "只能选择一条记录进行编辑。");
					return;
				}
				rows.remove(0);
			}
			$.messager.alert("提示", "只能选择一条记录进行编辑。");
		} else {
			var etId = rows[0].etId;
			$("#update_frame").attr("src", "toUpdate.do?etId=" + etId);
			$("#updateETWin").dialog('open');
		}
	});

	$("#downBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		for (i = 0; i < rows.length; i++) {
			if (rows[i].etId != null && rows[i].etId) {
				window.open("codeToDownExceltemplate.do?code=" + rows[i].etCode);
			}
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

var saveAddETBtnEvent = function() {
	var ifr = document.getElementById("add_frame");
	var win = ifr.window || ifr.contentWindow;
	win.formSubmit(); // 
};

var cancelAddETBtnEvent = function() {
	$("#addETWin").dialog('close');
};

var addETWinBeforClose = function() {
	$("#add_frame").attr("src", "");
	return true;
};

var saveUpdateETBtnEvent = function() {
	var ifr = document.getElementById("update_frame");
	var win = ifr.window || ifr.contentWindow;
	win.formSubmit();
};

var cancelUpdateETBtnEvent = function() {
	$("#updateETWin").dialog('close');
};

var updateETWinBeforClose = function() {
	$("#update_frame").attr("src", "");
	return true;
};
