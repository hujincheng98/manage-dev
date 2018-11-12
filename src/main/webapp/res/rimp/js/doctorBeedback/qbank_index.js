$(function() {

	var editIndex = -1;
	var dataTable = $("#dg");

	// 查询
	$("#searchBtn").click(function() {
		var json = $("#searchform").toJson();
		if (json != "") {
			$(dataTable).datagrid("load", json);
		}
	});
    //删除
	$("#deleteBtn").click(
			function() {
				var rows = $('#dg').datagrid('getSelections');
				if (rows && rows.length > 0) {
					var ids = '';
					for ( var i = 0; i < rows.length; i++) {
						if (i == rows.length - 1) {
							ids += rows[i].hfid;
						} else {
							ids += rows[i].hfid + ',';
						}
					}
					$.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue) {
						if (isTrue) {
							var data = {
								ids : ids
							};
							$.ajax({
								url : 'delete.json',
								type : 'POST',
								data : data,
								async : false,
								dataType : 'json',
								success : function(data) {
									$('#dg').datagrid("clearSelections");// 清除前一次选中状态
									if (data.state == 'success') {
										$.messager
												.alert("提示", "删除成功！", 'info',
														function() {
															$('#dg').datagrid("reload");
														});
									} else {
										$.messager.alert('错误', data.message,
												'error');
										$('#dg').datagrid("reload");
									}
								}
							});

						}
					});

				} else {
					$.messager.alert("提示", "请选择要删除的行。");
				}
			});

	// 重置
	$("#clearBtn").click(function() {
		$('#questionSearch').textbox('setValue', '');
		$("#hfstate").combobox('clear');// 清空选择表格数据
		$("#searchBtn").click();
	});

	$(dataTable).datagrid({
		url : 'list.json',
		title : "题库维护",
		loadMsg : '数据加载中,请稍后......',
		border : true,
		fitColumns : true,
		remoteSort : false,
		toolbar : "#button-bar",
		singleSelect : false,
		rownumbers : true,
		showFooter : true,
		pagination : true,
		fit : false,// 自适应大小
		nowrap : true,// 把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'hfid', // 主键
		columns : [ [ {
			field : 'hfid',
			title : '公告id',
			checkbox : true,
			width : 10
		}, {
			field : 'question',
			title : '题目内容',
			width : 25,
			sortable : true
		}, {
			field : 'option1',
			title : '选项一',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){  //满意：1,基本满意：2, 一般：3, 不满意：4
                if (row.option1=='1'){
                    return '满意';
                }
                else if (row.option1=='3'){
                	 return '一般';
                }
                else if (row.option1=='4'){
               	 return '不满意';
               }
                else if (row.option1=='2'){
               	 return '基本满意';
               }
                else {
                    return row.option1;
                }
            }
			
		}, {
			field : 'option2',
			title : '选项二',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				 if (row.option2=='1'){
	                    return '满意';
	                }
	                else if (row.option2=='3'){
	                	 return '一般';
	                }
	                else if (row.option2=='4'){
	               	 return '不满意';
	               }
	                else if (row.option2=='2'){
	               	 return '基本满意';
	               }
	                else {
	                    return row.option2;
	                }
            }
		}, {
			field : 'option3',
			title : '选项三',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				 if (row.option3=='1'){
	                    return '满意';
	                }
	                else if (row.option3=='3'){
	                	 return '一般';
	                }
	                else if (row.option3=='4'){
	               	 return '不满意';
	               }
	                else if (row.option3=='2'){
	               	 return '基本满意';
	               }
	                else {
	                    return row.option3;
	                }
            }
		}, {
			field : 'option4',
			title : '选项四',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				 if (row.option4=='1'){
	                    return '满意';
	                }
	                else if (row.option4=='3'){
	                	 return '一般';
	                }
	                else if (row.option4=='4'){
	               	 return '不满意';
	               }
	                else if (row.option4=='2'){
	               	 return '基本满意';
	               }
	                else {
	                    return row.option4;
	                }
            }
		},  {
			field : 'option5',
			title : '选项五',
			width : 5,
			sortable : true,
			formatter: function(value,row,index){
				 if (row.option5=='1'){
	                    return '满意';
	                }
	                else if (row.option5=='3'){
	                	 return '一般';
	                }
	                else if (row.option5=='4'){
	               	 return '不满意';
	               }
	                else if (row.option5=='2'){
	               	 return '基本满意';
	               }
	                else {
	                    return row.option5;
	                }
            }
		}, {
			field : 'hfstate',
			title : '状态',
			width : 3,
			sortable : true,
			formatter: function(value,row,index){
                if (row.hfstate=='1'){
                    return '启用';
                }
                else if (row.hfstate=='0'){
                	 return '停用';
                }
       
                else {
                    return null;
                }
            }
		} ] ],
		onLoadSuccess : function() {
			$(dataTable).datagrid("clearSelections");
		}
	});

});

// 新增
var add = function() {
	$('#addDialog').dialog('open');
}

// 新增保存
var save = function() {
	if (!$('#qbank_add_form').form('validate')) {
		return;
	}
	
	//验证输入长度限制
	var queLen = $("#question").val().length;
	var num = $("#option1").val();
	var option1Len = $("#option1").val().length;
	var option2Len = $("#option2").val().length;
	var option3Len = $("#option3").val().length;
	var option4Len = $("#option4").val().length;
	var option5Len = $("#option5").val();
	if(option5Len !=null && typeof(option5Len) != "undefined"){
		option5Len = $("#option5").val().length;
	}else{
		option5Len = 1;
	}
	if(queLen > 30 || option1Len > 30 || option2Len > 30 || option3Len > 30 || option4Len > 30 || option5Len > 30){
		return $.messager.alert("提示", "输入内容长度过长！");
	}

	$('#qbank_add_form').form('submit', {
		url : 'save.json',
		success : function(data) {
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#addDialog').dialog('close');
					$('#qbank_add_form').form('clear');
					$('#dg').datagrid("reload");
				});
			} else if (data.state == 'validatedDate') {
				$.messager.alert("提示", data.message);
			} else if (data.state == 'validatedNoticeName') {
				$.messager.alert("提示", data.message);
			} else {
				$.messager.alert("提示", "数据保存失败！");
			}
		}
	});

}

// 修改
var edit = function() {  
	var rows = $("#dg").datagrid("getSelections");
	console.log(rows);
	if (rows.length != 1) {
		$.messager.alert('提示', '请选择一条记录进行操作.');
	}
	if(rows.length == 1){
		var orgid = rows[0].orgid.length;
		if(orgid == 6){
			$('#editDialog').dialog('open');
		}else{
			$('#editDialogForAppId').dialog('open');
		}
	}
}

// 修改保存
var update = function() {
	var rows = $("#dg").datagrid("getSelections");
	var orgid = rows[0].orgid.length;
	if(orgid > 6){
		if (!$('#qbank_edit_form_admin').form('validate')) {
			return;
		}
		$('#qbank_edit_form_admin').form('submit', {
			url : 'update.json',
			success : function(data) {
				data = JSON.parse(data);
				if (data.state == 'success') {
					$.messager.alert("提示", data.message, "info", function() {
						$('#editDialogForAppId').dialog('close');
						$('#qbank_edit_form_admin').form('clear');
						$('#dg').datagrid("reload");
					});
				} else if (data.state == 'validatedDate') {
					$.messager.alert("提示", data.message);
				} else if (data.state == 'validatedNoticeName') {
					$.messager.alert("提示", data.message);
				} else {
					$.messager.alert("提示", "数据修改失败！")
				}
			}
		});
	}
	if(orgid == 6){
		if (!$('#qbank_edit_form').form('validate')) {
			return;
		}
		$('#qbank_edit_form').form('submit', {
			url : 'update.json',
			success : function(data) {
				data = JSON.parse(data);
				if (data.state == 'success') {
					$.messager.alert("提示", data.message, "info", function() {
						$('#editDialog').dialog('close');
						$('#qbank_edit_form').form('clear');
						$('#dg').datagrid("reload");
					});
				} else if (data.state == 'validatedDate') {
					$.messager.alert("提示", data.message);
				} else if (data.state == 'validatedNoticeName') {
					$.messager.alert("提示", data.message);
				} else {
					$.messager.alert("提示", "数据修改失败！")
				}
			}
		});
	}
	/*//验证输入长度限制
	var queLen = $("#question").val().length;
	var num = $("#option1").val();
	var option1Len = $("#option1").val().length;
	var option2Len = $("#option2").val().length;
	var option3Len = $("#option3").val().length;
	var option4Len = $("#option4").val().length;
	var option5Len = $("#option5").val();
	if(option5Len !=null && typeof(option5Len) != "undefined"){
		option5Len = $("#option5").val().length;
	}else{
		option5Len = 1;
	}
	if(queLen > 30 || option1Len > 30 || option2Len > 30 || option3Len > 30 || option4Len > 30 || option5Len > 30){
		return $.messager.alert("提示", "输入内容长度过长！");
	}*/

}