$(document).ready(function() {
	var win = $('#addWin');
	var form = $('#addForm');
	$("#dictTypeCode").validatebox();

	$('#typeTree').tree({
		url : 'list_dic_type.json',
		lines : true,
		onClick : treeOnClick,
		loadFilter : function(data) {
			return parseTypeTree(data);
		}
	});

	$("#addBtn").click(function() {
		$(form).form('clear');
		$("#dictTypeCode").textbox({
			enable : true,
			required : true,
			validType : {
				code : [ 6, 20 ],
				remote : [ 'check_dic_type_code.json', 'dictTypeCode' ]
			}
		});
		$(win).window("center");
		$(win).window("open");
	});

	$("#editBtn").click(function() {
		var node = $('#typeTree').tree("getSelected");
		if (node == null) {
			$.messager.alert("提示", "请选择要编辑的字典类型。");
		} else {
			$("#dictTypeCode").textbox("setValue", node.id);
			$("#dictTypeCode").textbox("disable");
			$("#dictTypeName").textbox("setValue", node.text);
			$("#remarks").val(node.attributes.remarks);
			$(win).window("center");
			$(win).window("open");
		}
	});

	$(win).dialog({
		title : "新增字典类型",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		width : 400,
		height : 240,
		modal : true,
		closed : true,
		buttons : [ {
			text : '保存',
			iconCls : "icon-save",
			handler : function() {
				var isValid = $(form).form('validate');
				if (isValid) {
					var params = $(form).toJson();
					var requestData = doPost("save_dic_type.json", params);
					if (requestData.success) {
						$(win).window("close");
						$.messager.alert("提示", "操作成功。", "info", function() {
							location.reload();
						});
					} else {
						$.messager.alert("提示", "操作失败。");
					}
				}
			}
		}, {
			text : '取消',
			iconCls : "icon-cancel",
			handler : function() {
				location.reload();
			}
		} ]
	});
});

// id：节点ID，对加载远程数据很重要。
// text：显示节点文本。
// state：节点状态，'open' 或 'closed'，默认：'open'。在设置为'closed'的时候，当前节点的子节点将会从远程服务器加载他们。
// checked：表示该节点是否被选中。
// attributes: 被添加到节点的自定义属性。
// children: 一个节点数组声明了若干节点。
function treeOnClick(node) {
	$("#centerFrame").attr("src", "edit.do?typeCode=" + node.id);
}

function parseTypeTree(data) {
	var childNodes = new Array();
	for ( var i = 0, l = data.length; i < l; i++) {
		childNodes[i] = {};
		childNodes[i].id = data[i].dictTypeCode;
		childNodes[i].iconCls = "icon-tabicons_22_12";
		childNodes[i].text = data[i].dictTypeName.replace(/\.n/g, '.');
		childNodes[i].attributes = {
			"remarks" : data[i].remarks
		};
	}
	return childNodes;
}