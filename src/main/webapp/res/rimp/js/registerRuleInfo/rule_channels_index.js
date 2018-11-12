$(function() {
	var editRow = undefined; // 定义全局变量：当前编辑的行
	//修改按钮
	$btnEdit = $("#editBtn").linkbutton({
		onClick:function() {
			// 修改时要获取选择到的行
			var rows = datagrid.datagrid("getSelections");
			// 如果只选择了一行则可以进行修改，否则不操作
			if (rows.length == 1) {
				// 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
				if (editRow != undefined) {
					datagrid.datagrid("endEdit",
							editRow);
				}
				// 当无编辑行时
				if (editRow == undefined) {
					// 获取到当前选择行的下标
					var index = datagrid.datagrid("getRowIndex", rows[0]);
					// 开启编辑
					datagrid.datagrid("beginEdit",index);
					// 把当前开启编辑的行赋值给全局变量editRow
					editRow = index;
					// 当开启了当前选择行的编辑状态之后，
					// 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
//					datagrid.datagrid("unselectAll");
				}
			}
		}
	});
	
	//保存按钮
	$btnSave = $("#saveBtn").linkbutton({
		disabled:true,
		onClick:function() {
			datagrid.datagrid("endEdit", editRow);
		}
	});//保存按钮
	//取消按钮
	$btnCancel = $("#cancelBtn").linkbutton({
		disabled:true,
		onClick:function() {
			// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
			editRow = undefined;
			datagrid.datagrid("rejectChanges");
			datagrid.datagrid("unselectAll");
		}
	});//取消按钮
	
	$("#queryBtn").click(function() {
		var json = $("#searchform").toJson();
		datagrid.datagrid("load", json);
		editRow = undefined;
		return false;
	});
	/**
	 * 重置
	 */
	$("#resetBtn").click(function() {
		$('#searchform').form('clear');
		//$("#searchform")[0].reset();
		editRow = undefined;
		return false;
	});
	
	var datagrid = $("#rulechannelsinfo").datagrid(
					{
						title : "渠道预约限制列表",
						rownumbers : true,
						fit : true,
						nowrap : false,
						url : 'list.json', // 请求的数据源
						fit : true, // datagrid自适应宽度
						fitColumn : false, // 列自适应宽度
						striped : true, // 行背景交换
						idField : 'chId', // 主键
						singleSelect : true,
						remoteSort : false,//前端排序这个是必须的
						columns : [ [// 显示的列
						{
							field : 'chId',
							title : '',
							// sortable : true,
							checkbox : true
						}, {
							field : 'chName',
							title : '渠道名称',
							sortable : true,
							width : '20%'
						}, 
						{
							field : 'chRegiNum',
							title : '渠道当天预约限制次数',
							width : '77%',
							sortable : true,
							editor : {
								type : 'numberbox',
								options : {
									required : true,
									value:"0", 
									min:0, 
									max:999, 
									precision:0 
								}
							}
						}
						] ],
						toolbar : "#button-bar",
						onCancelEdit : function(){
							$btnCancel.linkbutton('disable');
							$btnSave.linkbutton('disable');
							$btnEdit.linkbutton('enable');
						},
						onBeginEdit : function(){
							$btnCancel.linkbutton('enable');
							$btnSave.linkbutton('enable');
							$btnEdit.linkbutton('disable');
						},
						onAfterEdit : function(rowIndex, rowData, changes) {
							// endEdit该方法触发此事件
							delete rowData.createDate;
							delete rowData.createUser;
							delete rowData.updateDate;
							delete rowData.updateUser;
							delete rowData.hosInfo;
//							console.log(rowData)
							datagrid.datagrid("loading");
							$.ajax({
								url : "save.json",
								dataType : "json",
								async:false,
								type : "post",
								data : rowData,
								error : function() {
									$.message.alert("出错了");
								},
								success : function(data) {
									datagrid.datagrid("loaded");
									if (data.success) {
										$.messager.alert('渠道预约限制管理', data.msg,
												'info');
										editRow = undefined;
										$btnCancel.linkbutton('disable');
										$btnSave.linkbutton('disable');
										$btnEdit.linkbutton('enable');
									} else {
										datagrid.datagrid("beginEdit", rowIndex);
										// 把当前开启编辑的行赋值给全局变量editRow
										editRow = rowIndex;
										$.messager.alert('渠道预约限制管理', data.msg,
												'warning');
									}
								}
							})
							editRow = undefined;
						}
					});
	datagrid.datagrid('hiddenHeaderChecked', false); 
});