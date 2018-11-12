$(function() {
	var dataTable = $("#tableList");
	$("#resSetBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 1) {
			var arrayObj = new Array();
			$(rows).each(function(index, data) {
				arrayObj[index] = data.roleId;
			});
			var roleId = arrayObj[0];
			_selectRoleId = roleId;
			$("#res_tree").tree({
				url : 'res_setting.json?roleId=' + roleId,
				checkbox : true,
				cascadeCheck : false,
				onLoadSuccess : onLoadSuccess,
				onCheck : treeOnCheck,
				loadFilter : function(data) {
					return parseSysTree(data);
				}
			});
			$("#resSetWin").dialog('open');
		} else {
			$.messager.alert("提示", "请选择要一条记录进行操作。");
		}
	});
});
var _selectRoleId = null;

var onLoadSuccess = function(node, data) {
	var checkNodes = doPost('init_res_setting.json', _selectRoleId);
	for ( var index in checkNodes) {
		var c_node = $('#res_tree').tree('find', checkNodes[index].resId);
		if ($("#res_tree").tree('isLeaf', c_node.target)) {
			$('#res_tree').tree('check', c_node.target);
		}
	}
};

var saveRoleResBtnEvent = function() {
	var nodes = $('#res_tree').tree('getChecked');
	var indeterminate = $('#res_tree').tree('getChecked', 'indeterminate');
	var json = {};
	json.roleId = _selectRoleId;
	var arrayObj = new Array();
	$(nodes).each(function(index, data) {
		if (data.attributes.type == type_res) {
			arrayObj[arrayObj.length] = {
				resId : data.id
			};
		}
	});
	$(indeterminate).each(function(index, data) {
		if (data.attributes.type == type_res) {
			arrayObj[arrayObj.length] = {
				resId : data.id
			};
		}
	});
	json.resList = arrayObj;
	var data = doPost('save_res_setting.json', json);
	if (data.state) {
		$.messager.alert("提示", "角色资源设置成功！", "info", function() {
			$("#resSetWin").dialog('close');
		});
	} else {
		$.messager.alert("提示", "角色资源设置失败！")
	}
};
var resetRoleResBtnEvent = function() {
	$('#res_tree').tree('reload');
};
var cancelRoleResBtnEvent = function() {
	$("#resSetWin").dialog('close');
};

var type_sys = "SYS";
var type_res = "RES";
var parseSysTree = function(data) {
	var childNodes = new Array();
	for ( var i = 0, l = data.length; i < l; i++) {
		childNodes[i] = {};
		childNodes[i].id = data[i].sysId;
		childNodes[i].iconCls = "icon-sys";
		childNodes[i].text = data[i].sysName.replace(/\.n/g, '.');
		childNodes[i].checked = data[i].checked;
		childNodes[i].attributes = {
			"type" : type_sys
		};
		if (data[i].resources.length > 0) {
			childNodes[i].children = parseResTree(data[i].resources);
		}
	}
	return childNodes;
};
var parseResTree = function(data) {
	var childNodes = new Array();
	for ( var i = 0, l = data.length; i < l; i++) {
		childNodes[i] = {};
		childNodes[i].id = data[i].resId;
		childNodes[i].text = data[i].resName.replace(/\.n/g, '.');
		childNodes[i].checked = data[i].checked;
		childNodes[i].attributes = {
			"type" : type_res
		};
		if (data[i].children.length > 0) {
			childNodes[i].children = parseResTree(data[i].children);
		}
	}
	return childNodes;
}

function treeOnCheck(node, checked) {
	if (checked) {
		checkParent(node);
	} else {
		if (!uncheckParent(node)) {
			$('#res_tree').tree('check', node.target);
		}
	}
}

function checkParent(currentNode) {
	var parentNode = $('#res_tree').tree('getParent', currentNode.target);
	if (parentNode) {
		$('#res_tree').tree('check', parentNode.target);
		checkParent(parentNode);
	}
}

function uncheckParent(currentNode) {
	var childNodes = $('#res_tree').tree('getChildren', currentNode.target);
	var isChecked = true;
	if (childNodes) {
		$(childNodes).each(function(index, node) {
			if (node.checked && isChecked) {
				isChecked = false;
				return false;
			} else {
				isChecked = uncheckParent(node);
			}
		});
	}
	return isChecked;
}