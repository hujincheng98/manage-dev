/**
 * 方向键 上
 * 
 * @param element
 * @returns
 */
var default_comboxgrid_keyUp = function(element) {
	var grid = $(element).combogrid("grid");
	var rowData = grid.datagrid("getSelected");
	var rowIndex = grid.datagrid("getRows").length - 1;
	if (rowData) {
		rowIndex = grid.datagrid("getRowIndex", rowData) - 1;
		rowIndex = rowIndex < 0 ? 0 : rowIndex;
	}
	grid.datagrid("selectRow", rowIndex);
};
/**
 * 方向键 下
 * 
 * @param element
 * @returns
 */
var default_comboxgrid_keyDown = function(element) {
	var grid = $(element).combogrid("grid");
	var rowData = grid.datagrid("getSelected");
	var rowIndex = 0;
	var footIndex = grid.datagrid("getRows").length - 1;
	if (rowData) {
		rowIndex = grid.datagrid("getRowIndex", rowData) + 1;
		rowIndex = rowIndex > footIndex ? footIndex : rowIndex;
	}
	grid.datagrid("selectRow", rowIndex);
};
/**
 * 回车键
 * 
 * @param element
 * @returns
 */
var default_comboxgrid_enterKey = function(element) {
	$(element).combogrid("hidePanel");
};