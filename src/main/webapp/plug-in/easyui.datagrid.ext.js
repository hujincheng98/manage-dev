/**
 * 针对easyui的datagrid扩展 可以动态的为列增加编辑属性
 * ################################################################ 使用方式：
 * 为password字段添加一个editor
 * 
 * $("#gridId").datagrid('addEditor', { field : 'password', editor : { type :
 * 'validatebox', options : { required : true } } });
 * 
 * 删除password的editor
 * 
 * $("#gridid").datagrid('removeEditor', 'password'); 注：两个方法，第二个参数都可以传递数组。
 * ################################################################
 */

$.extend($.fn.datagrid.methods, {
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	},
	hiddenHeaderChecked : function (){
		$(this.getPanel).find("div[class=datagrid-header-check] input").hide();
	}
});