$.extend($.fn.datagrid.defaults.editors,{
					combogrid : {
						init : function(container, options) {
							var input = $(
									'<input type="text" class="datagrid-editable-input"/>')
									.appendTo(container);
							input.combogrid(options);
							return input;
						},
						destroy : function(target) {
							$(target).combogrid('destroy');
						},
						getValue : function(target) {
							return $(target).combogrid('getValue');
						},
						setValue : function(target, value) {
							$(target).combogrid('setValue', value);
						},
						resize : function(target, width) {
							$(target).combogrid('resize', width);
						}
					}
				});
$(function() {
	var datagrid; // 定义全局变量datagrid
	var editRow = undefined; // 定义全局变量：当前编辑的行
	datagrid = $("#hosareainfo")
			.datagrid(
					{
						title:"院区列表",
						fit : true,//缩放窗口的时候适应
						rownumbers : true,//显示序号
						nowrap : true,//把数据显示在一行里
						singleSelect : true,
						url : 'list.json', // 请求的数据源
						pagination : true, // 显示分页
						pageSize : 10, // 页大小
						pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
						striped : true, // 行背景交换
						idField : 'areaId', // 主键
						remoteSort : false,//前端排序这个是必须的
						sortName : "createDate",
						sortOrder : "desc",
						columns : [ [// 显示的列
						{
							field : 'areaId',
							title : '',
							checkbox : true
						},{
							field : 'createDate',
							sortable : true,
							hidden : true
						},  {
							field : 'areaName',
							title : '院区名称',
							width : '18%',
							sortable : true,
							/*editor : {
								type : 'validatebox',
								options : {
									required : true,
									validType : {
										remote : ['checkAreaName.json','param']
									},
									invalidMessage : '院区名称已存在，请更换其他名称'
								}
							}*/
							editor : {
								type : 'validatebox',
								options : {
									required : true
								}
							}
						}, {
							field : 'hosId',
							title : '所属医院',
							width : '19%',
							sortable : true,
							formatter: function(value,row,index){
									return row.hosName;
							},
							editor : {
								type : 'combogrid',
								options : {
									width : 170,
									editable:false,
									panelWidth : 482,
									pagination : true,// 是否分页
									idField : 'hosId',
									textField : 'hosName',
									url : platRoot + "/rimp/hos/list.json",
									columns : [ [ {
										field : 'hosId',
										title : '医院编号',
										hidden : true
									}, {
										field : 'hosName',
										title : '医院名称',
										width : 120
									}, {
										field : 'hosLevelName',
										title : '医院等级',
										width : 120
									}, {
										field : 'hosAddr',
										title : '医院地址',
										width : 120
									}, {
										field : 'hosTelep',
										title : '联系电话',
										width : 120
									} ] ]
								}
							}
						}, {
							field : 'areaAddr',
							title : '院区位置',
							width : '20%',
							sortable : true,
							editor : {
								type : 'validatebox',
								options : {
									required : true
								}
							}
						}, {
							field : 'hosAreaHisCode',
							title : '院区业务编码',
							width : '20%',
							sortable : true,
							editor : {
								type : 'validatebox',
								options : {
									required : true
								}
							}
						},{
							field : 'remarks',
							title : '备注',
							width : '20%',
							sortable : true,
							editor : {
								type : 'validatebox',
								options : {
									required : false
								}
							}
						} ] ],
						queryParams : {
							action : 'query'
						}, // 查询参数
						toolbar : "#button-bar",
						onAfterEdit : function(rowIndex, rowData, changes) {
							// endEdit该方法触发此事件
							delete rowData.createDate;
							delete rowData.createUser;
							delete rowData.updateDate;
							delete rowData.updateUser;
							delete rowData.hosInfo;
							//判断是更新还是新增
							var targetUrl = "save.json";
							if(rowData.areaId){//更新
								var targetUrl = "update.json"
							}
							datagrid.datagrid("loading");
							$.ajax({
								url : targetUrl,
								dataType : "json",
								type : "post",
								data : rowData,
								error : function() {
									datagrid.datagrid("loaded");
									$.message.alert("出错了");
								},
								success : function(data) {
									datagrid.datagrid("loaded");
									if (data.success) {//修改成功
										$.messager.alert('提示', data.msg, 'info');
										datagrid.datagrid('reload');
										editRow = undefined;
										btnEnState();
									} else {
										datagrid.datagrid("beginEdit", rowIndex);
										// 把当前开启编辑的行赋值给全局变量editRow
										editRow = rowIndex;
										$.messager.alert('提示', data.msg, 'error');
									}
								}
							});
						}/*,
						onDblClickRow : function(rowIndex, rowData) {
							// 双击开启编辑行
							if (editRow != undefined) {
								$.messager.alert('提示', '请先保存或取消正在编辑的行','warning');
								return;
							}
							if (editRow == undefined) {
								datagrid.datagrid("unselectAll");
								editRow = rowIndex;
								datagrid.datagrid("beginEdit", rowIndex);
							}
						}*/
					});
	
	datagrid.datagrid('hiddenHeaderChecked', false);
	
	//新增按钮
	$btnAdd = $("#addArea").linkbutton({
		onClick:function(){
			// 添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
			if (editRow != undefined) {
				$.messager.alert('提示', "请先保存或取消正在编辑的行",'warning');
				return;
//				datagrid.datagrid("endEdit",editRow);
			}
			// 添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
			if (editRow == undefined) {
				btnAddState();
				datagrid.datagrid("insertRow", {
					index : 0, // index start with
								// 0
					row : {

					}
				});
				// 将新插入的那一行开户编辑状态
				datagrid.datagrid("beginEdit", 0);
				// 给当前编辑的行赋值
				editRow = 0;
			}
		}
	});
	//修改按钮
	$btnEdit = $("#editArea").linkbutton({
		onClick:function(){
			// 修改时要获取选择到的行
			var rows = datagrid.datagrid("getSelections");
			// 如果只选择了一行则可以进行修改，否则不操作
			if (rows.length == 1) {
				// 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
				if (editRow != undefined) {
					$.messager.alert('提示', '请先保存或取消正在编辑的行','warning');
					return;
//					datagrid.datagrid("endEdit",editRow);
				}
				// 当无编辑行时
				if (editRow == undefined) {
					btnEditState();
					// 获取到当前选择行的下标
					var index = datagrid.datagrid("getRowIndex", rows[0]);
					// 开启编辑
					datagrid.datagrid("beginEdit",index);
					// 把当前开启编辑的行赋值给全局变量editRow
					editRow = index;
					// 当开启了当前选择行的编辑状态之后，
					// 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
					datagrid.datagrid("unselectAll");
				}
			}
		}
	});
	//删除按钮
	$btnDel = $("#delete").linkbutton({
		onClick : function(){
			// 删除时先获取选择行
			var rows = datagrid.datagrid("getSelections");
			// 选择要删除的行
			if (rows.length > 0) {
				$.messager.confirm("提示",rows.length>1?"是否删除这些数据":"是否删除该条数据",function(r) {
									if (r) {
										var ids = [];
										for (var i = 0; i < rows.length; i++) {
											ids.push(rows[i].areaId);
										}
										// 将选择到的行存入数组并用,分隔转换成字符串，
										// 本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
										datagrid.datagrid("loading");
										$.ajax({
													url : "delete.json",
													dataType : "json",
													type : "post",
													data : {
														ids : ids
													},
													error : function() {
														$.message.alert("出错了");
													},
													success : function(data) {
														datagrid.datagrid("loaded");
														if (data.success) {
															$.messager.alert('院区管理',data.msg,'info');
															datagrid.datagrid('reload');
														} else {
															$.messager.alert('院区管理',data.msg,'warning');
														}
														//刷新
														datagrid.datagrid("reload");
														datagrid.datagrid("unselectAll");
													}
												})
									}
								});
			} else {
				$.messager.alert("提示", "请选择要删除的行","error");
			}
		}
	});//删除按钮
	//保存按钮
	$btnSave = $("#save").linkbutton({
		disabled:true,
		onClick:function() {
			datagrid.datagrid("endEdit", editRow);
		}
	});//保存按钮
	//取消
	$btnCancel = $("#cancel").linkbutton({
		disabled:true,
		onClick:function() {
			if (editRow == undefined) {
				return;
			}
			// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
			$.messager.confirm('提示:','您确认要取消吗?',function(r){
				if(r){ 
					editRow = undefined;
					btnEnState();
					datagrid.datagrid("rejectChanges");
					datagrid.datagrid("unselectAll");
				}
			}); 
		}
	});//取消按钮
	//上传按钮
	$("#upload").on('click', function (){
		var row = datagrid.datagrid("getSelected");
		if(row){
			$('#uploadDialog').dialog('open');
		} else {
			$.messager.alert('提示','请选择要操作的行.', 'warning');   
		}
	});
	$("#hosarea-queryBtn").click(function() {
		var json = $("#searchForm").toJson();
		datagrid.datagrid("load", json);
		editRow = undefined;
		return false;
	});
	/**
	 * 重置
	 */
	$("#hosarea-resetBtn").click(function() {
		$("#searchForm").form('clear');
		datagrid.datagrid("load",{});
		editRow = undefined;
		return false;
	});
	function btnAddState(){
		$btnAdd.linkbutton('disable');
		$btnEdit.linkbutton('disable');
		$btnDel.linkbutton('disable');
		$btnCancel.linkbutton('enable');
		$btnSave.linkbutton('enable');
	}
	function btnEditState(){
		$btnEdit.linkbutton('disable');
		$btnAdd.linkbutton('disable');
		$btnDel.linkbutton('disable');
		$btnCancel.linkbutton('enable');
		$btnSave.linkbutton('enable');
	}
	function btnEnState(){
		$btnEdit.linkbutton('enable');
		$btnDel.linkbutton('enable');
		$btnAdd.linkbutton('enable');
		$btnCancel.linkbutton('disable');
		$btnSave.linkbutton('disable');
	}
});

/**
 * 上传方法
 */
var upload = function (){
	if($('#file').val()==""){
		$.messager.alert("提示", '请上传照片', "error");
		return;
	}
	$('#hosarea_upload_form').form('submit', {
		url: 'upload.json',
		success: function(data){
			data = JSON.parse(data);
			if (data.success) {
				$.messager.alert("提示", data.msg, "info", function() {
					$('#uploadDialog').dialog('close');
					datagrid.datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "数据保存失败！", 'error')
			}
		}
	});
}