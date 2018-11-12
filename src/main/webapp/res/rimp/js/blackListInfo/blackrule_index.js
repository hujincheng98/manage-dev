$(function (){
	var dataTable = '#dg';
	var editIndex = undefined; // 当前编辑行
	/**
	 * 按钮定义
	 */
	$btnAdd = $("#btnAdd").linkbutton({
		onClick:function() {
			add();
		}
	});//新增按钮
	$btnEdit = $("#btnEdit").linkbutton({
		onClick:function() {
			edit();
		}
	});//修改按钮
	$btnSave = $("#btnSave").linkbutton({
		disabled:true,
		onClick:function() {
			save();
		}
	});//保存按钮
	$btnCancel = $("#btnCancel").linkbutton({
		disabled:true,
		onClick:function() {
			$.messager.confirm('提示:','您确认要取消吗?',function(r){
				if(r){
					cancel();
				}
			});
		}
	});//取消按钮
	
	$btnSearch = $("#btnSearch").linkbutton({
		onClick:function() {
			$(dataTable).datagrid('load', $('#searchform').toJson());
		}
	});//查询按钮
	
	$btnReset= $("#btnReset").linkbutton({
		onClick:function() {
			$('#searchform').form('clear');
			$(dataTable).datagrid('load', $('#searchform').toJson());
		}
	});//重置按钮
	function btnNormalMode(){
		$btnAdd.linkbutton("enable");
		$btnEdit.linkbutton("enable");
		$btnSave.linkbutton("disable");
		$btnCancel.linkbutton("disable");
	}
	$(dataTable).datagrid({
		url:'list.json',
		title: "规则信息",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : true,
		rownumbers: true,
		showFooter: true,
		pagination:true,
		nowrap : true,//把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'pabaId', // 主键
		onAfterEdit : onAfterEdit,
		onBeginEdit:function(index,row){
			$btnEdit.linkbutton("disable");
			$btnAdd.linkbutton("disable");
			$btnSave.linkbutton("enable");
			$btnCancel.linkbutton("enable");
		},
		onCancelEdit:function(index,row){
			btnNormalMode();
		},
		columns:[[
		    {field:'pabaId', title:'规则编号', checkbox:true, width:10},
		    {field:'pabaRuleType', title:'规则名称', width:30, 
		    	formatter: function(value,row,index){
		    		return row.pabaRuleName;
			}, editor : {
		    	type : 'combobox',
		    	options: { 
		    		valueField:'dictValue',
	        		textField:'dictName',
	        		editable:false,
	        		required:true,
	                missingMessage:'请选择规则',
	        		url : platRoot + '/platform/dictionary/show_dic_by_type.json?typeId=RULE_TYPE',
	        		onSelect : function (record){
//	        			var pabaRuleName = $(dataTable).datagrid("getEditor", {index : editIndex, field: 'pabaRuleName'});
//						$(pabaRuleName.target).val(record.dictName);
	        		}
		    	}
		    }},
//		    {field:'pabaRuleName', title:'规则取值', width:30, editor : { type : 'text' }, hidden : true },
		    {field:'pabaRuleNum', title:'规则取值', width:30, editor : { type : 'numberbox', options : { required : true }}},
		    {field:'hosId', title:'所属医院', width:30, 
		    	formatter: function(value,row,index){
		    		return row.hosName;
			},editor : {
				type : 'combogrid',
				options : {
					width : 540,
					panelWidth : 540,
					editable:false,
					required : true, 
					pagination : true,// 是否分页
					idField : 'hosId',
					textField : 'hosName',
					url : platRoot + "/rimp/hos/list.json",
					onSelect : function (index,row){
						if (row) {
//							var hosName = $(dataTable).datagrid("getEditor", {index : editIndex, field: 'hosName'});
//							$(hosName.target).val(row.hosName);
						}
					},
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
			}}
			//{field:'hosName', title:'所属医院', width:30, editor : { type : 'text' }, hidden : true }
		]]  
	});
	/**
	 * 结束编辑事件
	 */
	function onAfterEdit(rowIndex, rowData, changes) {
		var inserted = $(dataTable).datagrid('getChanges', 'inserted');
		var updated = $(dataTable).datagrid('getChanges', 'updated');
		if (inserted.length < 1 && updated.length < 1) {
			editIndex = undefined;
			$(dataTable).datagrid('unselectAll');
			return;
		}
		var url = '';
		if (inserted.length > 0) {
			url = 'save.json';
		}
		if (updated.length > 0) {
			url = 'update.json';
		}
		$.ajax({
			url : url,
			data : JSON.stringify(rowData),
			type: 'POST',
			dataType : 'json',
			contentType : 'application/json',
			success : function(r) {
				if (r.state == 'success') {
					btnNormalMode();
					$.messager.alert('提示','操作成功','info',function (){
						$(dataTable).datagrid('acceptChanges');
						editIndex = undefined;
						$(dataTable).datagrid('reload');
					});
				} else {
					$.messager.alert('错误', r.message, 'error');
					$(dataTable).datagrid('beginEdit', rowIndex);
					editIndex = rowIndex;
				}
				$(dataTable).datagrid('unselectAll');
			}
		});

	}
	/**
	 * 新增
	 * @returns
	 */
	function add(){
		if(editIndex != undefined) {
			$.messager.alert('提示', "请先保存或取消正在编辑的行",'warning');
			return;
		} else {
			$(dataTable).datagrid('insertRow',{
				index: 0,	// index start with 0
				row: {}
			});
			$(dataTable).datagrid('beginEdit', 0);
			editIndex = 0;
		}
	}
	/**
	 * 修改
	 * @returns
	 */
	function edit() {
		if(editIndex != undefined) {
			$.messager.alert('提示', '请先保存或取消正在编辑的行', 'warning');
			return;
		} else {
			var row = $(dataTable).datagrid("getSelected");
			if (row) {
				var rowIndex = $(dataTable).datagrid("getRowIndex", row);
				$(dataTable).datagrid('beginEdit', rowIndex);
				/*var pabaRuleType = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'pabaRuleType'});
				var hosId = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'hosId'});
				pabaRuleType.target.combobox({ disabled: true });
				hosId.target.combobox({ disabled: true });*/
				editIndex = rowIndex;
			} else {
				$.messager.alert('提示', '请选择要修改的数据.', 'warning');   
			}
		}
	}
	/**
	 * 取消
	 */
	function cancel() {
		if (editIndex == undefined) {
			$.messager.alert('提示', "没有要取消的行数据",'warning');
			return;
		} else {
			editIndex = undefined;
			$(dataTable).datagrid("rejectChanges");
			$(dataTable).datagrid("unselectAll");
		}
	}
	/**
	 * 保存
	 */
	function save() {
		if (editIndex == undefined) {
			$.messager.alert('提示', "没有要保存的行数据",'warning');
			return;
		} else {
			if ($(dataTable).datagrid('validateRow', editIndex)) {
				$(dataTable).datagrid('acceptChanges');
				editIndex = undefined;
			}
		}
	}
	$(dataTable).datagrid('hiddenHeaderChecked', false);
});