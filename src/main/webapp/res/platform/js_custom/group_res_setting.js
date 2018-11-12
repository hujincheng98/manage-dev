$(function() {
	var dataTable = $("#tableList");
	$("#resSetBtn").click(function() {
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length == 1) {
			var arrayObj = new Array();
			$(rows).each(function(index, data) {
				arrayObj[index] = data.groupId;
			});
			var groupId = arrayObj[0];
			_selectGroupId = groupId;
			$("#res_tree").tree({
				url : 'res_setting.json',
				checkbox : true,
				onLoadSuccess : onLoadSuccess,
				loadFilter : function(data) {
					return parseSysTree(data);
				}
			});
			$("#groupResSetWin").dialog('open');
		} else {
			$.messager.alert("提示", "请选择要一条记录进行操作。");
		}
	});
});
var _selectGroupId = null;

var onLoadSuccess = function(node, data) {
	var checkNodes = doPost('init_res_setting.json', _selectGroupId);
	for ( var index in checkNodes) {
		var c_node = $('#res_tree').tree('find', checkNodes[index].resId);
		if (c_node && $("#res_tree").tree('isLeaf', c_node.target)) {
			$('#res_tree').tree('check', c_node.target);
		}
	}
};

var saveGroupResBtnEvent = function() {
	var nodes = $('#res_tree').tree('getChecked');
	var indeterminate = $('#res_tree').tree('getChecked', 'indeterminate');
	var json = {};
	json.groupId = _selectGroupId;
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
		$.messager.alert("提示", "用户组资源设置成功！", "info", function() {
			$("#groupResSetWin").dialog('close');
		});
	} else {
		$.messager.alert("提示", "用户组资源设置失败！")
	}
};
var resetGroupResBtnEvent = function() {
	$('#res_tree').tree('reload');
};
var cancelGroupResBtnEvent = function() {
	$("#groupResSetWin").dialog('close');
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