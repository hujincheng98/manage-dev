/**
 * 基础排班(排班模板)
 */
$(function() {

	var dataTable = $("#dg-schmbasemaininfo");
	var editRowIndex = -1;
	var tdocmId='';
    var tdocmName='';

	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		$(dataTable).datagrid('load', $('#searchform').toJson());
	});

	/**
	 * 清空按钮
	 */
	$("#clearBtn").click(function(){
		$('#_common_docm_comboxgrid').combogrid('clear');// 清空选择表格数据
    	$("#_common_dept_comboxgrid").combogrid('clear');	// 清空选择tree数据
    	if(editRowIndex == -1){
    		$("#searchBtn").click();
    	}
	});
	/**
	 * 新增按钮
	 */
	$btnAdd = $("#dg-schmbasemaininfo-addBtn").linkbutton({
		onClick:function(){
				if(editRowIndex >= 0){
					$.messager.alert("提示", "您正在编辑...");
					return;
				}
		    	//默认排班医生
		    	var docmId = "";
				var docmName = "";
				var schmDeptId = "";
				var schmDeptName="";
				var docmTitle = "";
				var deptId = "";
				var deptName = "";
		    	var domDataGrid = $('#_common_docm_comboxgrid').combogrid('grid');// 获取数据表格对象
					var domRowData = domDataGrid.datagrid('getSelected');	// 获取选择的行
					// console.log(domRowData)
		    	if(domRowData){
		    		docmId = domRowData.docmId;
					docmName = domRowData.docmName;
					docmTitle = domRowData.docmTitle;
					schmDeptId = domRowData.deptId;
					schmDeptName = domRowData.deptName;
					deptId = domRowData.deptId;
					deptName = domRowData.deptName;
		    	}
		    	//默认排班科室
				var g = $("#_common_docm_comboxgrid").combogrid('grid');	// 获取表格对象
		    	var n = g.datagrid('getSelected');
		    	if(n){
		    		schmDeptId = n.deptId;
		    		schmDeptName = n.deptName;
		    	}
				$(dataTable).datagrid("insertRow",
					{
						index:0,
						row:{
							docmId:docmId,
							docmTitle:docmTitle,
							schmDeptId:schmDeptId,
							deptId:deptId,
							deptName:deptName,
							schmRegiSum:"0",
							schmDownRegiSum:"0",
							schmOnRegiSum:"0",
							schmDownSum:"0",
							schmOnSum:"0"
						}
					}
				);
				$(dataTable).datagrid("beginEdit", 0);

				var rowIndex = 0;

				// 得到单元格对象,index指哪一行,field跟定义列的那个一样
			    var dcomTitleEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'docmTitleName'});
			    var deptNameEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'deptName'});
			    var schmRegiSumEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'schmRegiSum'});
			    dcomTitleEditor.target.prop('readonly',true); // 设值只读
			    deptNameEditor.target.prop('readonly',true); // 设值只读
			    schmRegiSumEditor.target.prop('readonly',true); // 设值只读


			    //按钮置灰

			}
	});

	/**
	 * 修改按钮
	 */
	$btnEdit = $("#dg-schmbasemaininfo-editBtn").linkbutton({
		onClick:function (){
			var rows = $(dataTable).datagrid("getSelections");
			if(rows.length != 1){
				$.messager.alert('提示','请选择一条记录进行操作.');
			} else {
				var rowIndex = $(dataTable).datagrid("getRowIndex", rows[0]);
				$(dataTable).datagrid("beginEdit", rowIndex);
				// 得到单元格对象,index指哪一行,
                var dcomIdEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'docmId'});
			    var dcomTitleEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'docmTitleName'});
			    var deptNameEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'deptName'});
			    var schmRegiSumEditor = $(dataTable).datagrid('getEditor', {index:rowIndex,field:'schmRegiSum'});
			    dcomTitleEditor.target.prop('readonly',true); // 设值只读
			    deptNameEditor.target.prop('readonly',true); // 设值只读
			    schmRegiSumEditor.target.prop('readonly',true); // 设值只读
				console.log(dcomIdEditor);
                dcomIdEditor.actions.setValue(dcomIdEditor.target, rows[0].docmName);
                console.log(rows[0]);
                tdocmId=rows[0].docmId;
                tdocmName=rows[0].docmName;
			    editRowIndex = rowIndex;
			}
		}
	});

	/**
	 * 保存按钮
	 */
	$btnSave = $("#schmbasemaininfo-saveBtn").linkbutton({
		disabled:true,
		onClick:function(){
			if (endEditing()){
				$(dataTable).datagrid('acceptChanges');
				getChanges();
			}
		}
	});

	/**
	 * 结束编辑方法
	 */
	function endEditing(){
		if (editRowIndex < 0){return true}
		if ($(dataTable).datagrid('validateRow', editRowIndex)){
			$(dataTable).datagrid('endEdit', editRowIndex);
			editRowIndex = -1;
			return true;
		} else {
			$.messager.alert('提示','请完善信息!','warning');
			return false;
		}
	}

	/**
	 * 获取更新行
	 */
	function getChanges(){
		var rows = $(dataTable).datagrid('getChanges');
	}

	/**
	 * 结束编辑事件
	 */
	function onAfterEdit(rowIndex, rowData, changes) {
		// rowData.domId=tdocmId;
		console.log(rowData);
		var inserted = $(dataTable).datagrid('getChanges', 'inserted');
		var updated = $(dataTable).datagrid('getChanges', 'updated');
		if (inserted.length < 1 && updated.length < 1) {
			editRowIndex = -1;
			$(dataTable).datagrid('unselectAll');
			return;
		}
		var url = '';
		if (inserted.length > 0) {
			url = 'save.json';
		}
		if (updated.length > 0) {
			url = 'update.json';
            rowData.docmId=tdocmId;
            rowData.docmName=tdocmName;
		}


		$.ajax({
			url : url,
			data : JSON.stringify(rowData),
			type: 'POST',
			dataType : 'json',
			contentType : 'application/json',
			success : function(r) {
				if (r.state == 'success') {
					//按钮状态恢复，huyang新增
					btnNormalMode();
					$.messager.alert('提示','操作成功','info',function (){
						$(dataTable).datagrid('acceptChanges');
						editRowIndex = -1;
						$(dataTable).datagrid('reload');
					});
				} else {
					$.messager.alert('错误', r.message, 'error');
					$(dataTable).datagrid('beginEdit', rowIndex);
					editRowIndex = rowIndex;
				}
				$(dataTable).datagrid('unselectAll');
			}
		});

	}

	/**
	 * 删除按钮
	 */
	$btnDel = $("#dg-schmbasemaininfo-delBtn").linkbutton({
		onClick:function(){
			var rows = $(dataTable).datagrid("getSelections");
			if(rows.length ==0){
				$.messager.alert('提示','请选择要删除的数据.');
				return;
			}
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].schmId;
				} else {
					ids += rows[i].schmId + ',';
				}
			}
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){
			    if (r){
					$.ajax({
						url : 'delete.json',
						cache : true,
						type : 'POST',
						data: {'schmIds' : ids},
				        dataType: 'json',
						async : false,
						success : function(data)
						{
							$(dataTable).datagrid("clearSelections");
							if (data.state == 'success') {
								$.messager.alert("提示", "删除成功！", 'info', function (){
									$(dataTable).datagrid("reload");
								});
							} else {
								$.messager.alert('错误', '删除失败', 'error');
							}
						}
					});
			    }
			});
		}
	});

	/**
	 * 取消按钮
	 */
	$btnCancel = $("#schmbasemaininfo-cancelBtn").linkbutton({
		disabled:true,
		onClick:function () {
			$.messager.confirm('提示:','您确认要取消吗?',function(r){
				if(r){
					$(dataTable).datagrid('unselectAll');
					$(dataTable).datagrid('rejectChanges');
					if(editRowIndex==0){//解决新增排版时，提示错误后取消无法第一行不消失
						rowData = $(dataTable).datagrid('getData').rows[0];
						if(rowData && !rowData.schmId){
							$(dataTable).datagrid('deleteRow',0);
						}
						$(dataTable).datagrid('acceptChanges');
					}
					editRowIndex = -1;
				}
			});
		}
	});
	//启用按钮
	$btnEnabled = $("#enabledBtn").linkbutton({
		onClick:function () {
			var row = $("#dg-schmbasemaininfo").datagrid("getSelected");
			if (row.schmState == 'state_1') {
				$.messager.alert('提示','该模板已启用','warning');
				return;
			}
			$.ajax({
				url : 'update.json',
				cache : true,
				type : 'POST',
				data : JSON.stringify({schmId : row.schmId, schmState : 'state_1'}),
				contentType : 'application/json',
				async : false,
				success : function(data)
				{
					if (data.state == 'success') {
						$("#dg-schmbasemaininfo").datagrid("reload");
					} else {
						$.messager.alert('错误', '启用失败', 'error');
					}
				}
			});
		}
	});
	//停用按钮
	$btnDisable = $("#disableBtn").linkbutton({
		onClick:function () {
			var row = $("#dg-schmbasemaininfo").datagrid("getSelected");
			if (row.schmState == 'state_0') {
				$.messager.alert('提示','该模板已停用','warning');
				return;
			}
			$.ajax({
				url : 'update.json',
				cache : true,
				type : 'POST',
				data : JSON.stringify({schmId : row.schmId, schmState : 'state_0'}),
				contentType : 'application/json',
				async : false,
				success : function(data)
				{
					if (data.state == 'success') {
						$("#dg-schmbasemaininfo").datagrid("reload");
					} else {
						$.messager.alert('错误', '停用失败', 'error');
					}
				}
			});
		}
	});
	function btnEditMode(){
		$btnEdit.linkbutton('disable');
		$btnAdd.linkbutton('disable');
		$btnDel.linkbutton('disable');
		$btnEnabled.linkbutton('disable');
		$btnDisable.linkbutton('disable');

		$btnSave.linkbutton('enable');
		$btnCancel.linkbutton('enable');
	}
	function btnNormalMode(){
		$btnAdd.linkbutton('enable');
		$btnEdit.linkbutton('enable');
		$btnDel.linkbutton('enable');
		$btnEnabled.linkbutton('enable');
		$btnDisable.linkbutton('enable');

		$btnSave.linkbutton('disable');
		$btnCancel.linkbutton('disable');
	}
    //转换函数
    function formatterWeek(w) {
        var x;
        switch (w) {
            case "星期日":x=7;
                break;
            case "星期一":x=1;
                break;
            case "星期二":x=2;
                break;
            case "星期三":x=3;
                break;
            case "星期四":x=4;
                break;
            case "星期五":x=5;
                break;
            case "星期六":x=6;
                break;
        };
        return x
    }
	$(dataTable).datagrid({
		url:'list.json',
		title: "排班模板",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : false,
		rownumbers: true,
		showFooter: true,
		pagination:true,
		nowrap : true,//把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'schmId', // 主键
		onAfterEdit: onAfterEdit,
		onBeginEdit:function(index,row){
			/*if(row && row.schmId){//修改

			}else{//新增

			}*/
			btnEditMode();
		},
		onCancelEdit:function(index,row){
			btnNormalMode();
//			console.log("onCancelEdit")
		},
		columns:[[
		    {field:'schmId',title:'', checkbox:true, width:10},
		    {field:'schmWeek',title:'星期', sortable:true,sorter:function(a,b){
                    return formatterWeek(a)>formatterWeek(b)?1:-1
                }, editor:{
			    	type:'combobox', options : {
			    		valueField: 'label',
			    		textField: 'value',
			    		data: [{
			    			label: '星期一',
			    			value: '星期一'
			    		},{
			    			label: '星期二',
			    			value: '星期二'
			    		},{
			    			label: '星期三',
			    			value: '星期三'
			    		},{
			    			label: '星期四',
			    			value: '星期四'
			    		},{
			    			label: '星期五',
			    			value: '星期五'
			    		},{
			    			label: '星期六',
			    			value: '星期六'
			    		},{
			    			label: '星期日',
			    			value: '星期日'
			    		}],
			    		editable:false,
			    		required: true
			    	}
		    	},
		    width:10},
		    {field:'shiftId',title:'班次', sortable:true, editor:{
		    	type:'combogrid',
                options:{
                    panelWidth:370,
                    idField:'shiftId',
                    textField:'shiftName',
            		pagination : true,// 是否分页
            		singleSelect : true,// 只允许选择一行记录
            		fitColumns:true,
                    mode : 'remote',// 远程连接方式
                    method : 'POST',// 请求方式
                    dataType : 'json',
                    url:platRoot+"/rimp/shift/list.json",
                    editable:false,
                    required:true,
                    columns:[[
                        {field:'shiftId',title:'序号',hidden:true},
                        {field:'shiftName',title:'班次名称 ',width:120},
                        {field:'shiftStartDate',title:'开始时间',width:120},
                        {field:'shiftEndDate',title:'结束时间',width:120}
                    ]],
                    keyHandler : {
                    	up : function() {
                    		c_comboxgrid_keyUp(this);
        				},
        				down : function() {
        					c_comboxgrid_keyDown(this);
        				},
            			enter : function() {
            				c_comboxgrid_enterKey(this);
            			},
            			query : function(searchKey)
            			{
            				if (searchKey != null)
            				{
            					var grid = $(this).combogrid("grid");
        						var searchParam = {
            							'searchParams[shiftName]' : searchKey
            					};
        						var url = platRoot+"/rimp/shift/list.json";
            					$(grid).datagrid('options').queryParams = searchParam;
            					$(grid).datagrid('options').url = url;
                				$(grid).datagrid('reload');
                				$(this).combogrid("setValue", searchKey);
            				}
            			}
            		}
                }
		    },
		    formatter: function(value,row,index){
				return row.shiftName;
			},
		    width:10},
			{field:'docmId',title:'医生', sortable:true, width:10, editor:{
				type:'combogrid',
                options:{
                    panelWidth:480,
                    idField:'docmId',
                    textField:'docmName',
            		pagination : true,// 是否分页
            		singleSelect : true,// 只允许选择一行记录
            		fitColumns:true,
                    mode : 'remote',// 远程连接方式
                    method : 'POST',// 请求方式
                    dataType : 'json',
                    url:platRoot+"/rimp/hosdocm/list.json",
                    required:true,
                    columns:[[
                        {field:'docmId',title:'医生编号',hidden:true},
                        {field:'docmName',title:'姓名 ',width:120},
                        {field:'docmTitleName',title:'职称',width:120},
                        {field:'deptId',title:'所属科室',hidden:true},
                        {field:'deptName',title:'所属科室',width:120}
                    ]],
                    onSelect : function (index,row){
											// console.log(row);
											// console.log(index)
        				if (row) {
									var ed_docmId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmId'});
									console.log(ed_docmId)
							$(ed_docmId.target).val(row.docmId);
                            tdocmId=row.docmId;
                            tdocmName=row.docmName;

							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitle'});
							$(ed_docmTitle.target).val(row.docmTitle);

							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitleName'});
							$(ed_docmTitle.target).val(row.docmTitleName);

							var ed_deptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptId'});
							$(ed_deptId.target).val(row.deptId);

							var ed_deptName = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptName'});
							$(ed_deptName.target).val(row.deptName);

							var ed_schmDeptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDeptId'});
							ed_schmDeptId.target.combogrid('setValue', row.deptId);
        				}
                    },
                    onClickRow : function (rowIndex, rowData) {
                    	if (rowData != null)
						{
							var ed_docmId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmId'});
							$(ed_docmId.target).val(rowData.docmId);
                            tdocmId=rowData.docmId;
                            tdocmName=rowData.docmName;

							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitle'});
							$(ed_docmTitle.target).val(rowData.docmTitle);

							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitleName'});
							$(ed_docmTitle.target).val(rowData.docmTitleName);

							var ed_deptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptId'});
							$(ed_deptId.target).val(rowData.deptId);

							var ed_deptName = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptName'});
							$(ed_deptName.target).val(rowData.deptName);

							var ed_schmDeptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDeptId'});
							ed_schmDeptId.target.combogrid('setValue', rowData.deptId);
						}
                    },
                    keyHandler : {
                    	up : function() {
                    		c_comboxgrid_keyUp(this);
        				},
        				down : function() {
        					c_comboxgrid_keyDown(this);
        				},
            			enter : function() {
            				var grid = $(this).combogrid('grid');
            				var rowData = grid.datagrid('getSelected');
            				if (rowData) {
            					var ed_docmId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmId'});
    							$(ed_docmId.target).val(rowData.docmId);
                                tdocmId=rowData.docmId;
                                tdocmName=rowData.docmName;
    							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitle'});
    							$(ed_docmTitle.target).val(rowData.docmTitle);

    							var ed_docmTitle = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'docmTitleName'});
    							$(ed_docmTitle.target).val(rowData.docmTitleName);

    							var ed_deptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptId'});
    							$(ed_deptId.target).val(rowData.deptId);

    							var ed_deptName = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'deptName'});
    							$(ed_deptName.target).val(rowData.deptName);

    							var ed_schmDeptId = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDeptId'});
    							ed_schmDeptId.target.combogrid('setValue', rowData.deptId);
            				}
            				c_comboxgrid_enterKey(this);
            			},
            			query : function(searchKey)
            			{
            				if (searchKey != null)
            				{
            					var grid = $(this).combogrid("grid");
        						var searchParam = {
            							'searchParams[docmName]' : searchKey
            					};
        						var url = platRoot+"/rimp/hosdocm/list.json";
            					$(grid).datagrid('options').queryParams = searchParam;
            					$(grid).datagrid('options').url = url;
                				$(grid).datagrid('reload');
                				$(this).combogrid("setValue", searchKey);
            				}
            			}
            		}
                }
			},
			formatter: function(value,row,index){
				return row.docmName;
			},
			width:10},
			{field:'docmTitle',title:'职称',editor:{type:'text',options:{required:true}} ,hidden:true},
			{field:'docmTitleName',title:'职称', sortable:true, editor:{type:'text',options:{required:true}}, width:10},
			{field:'schmDeptId',title:'排班科室', sortable:true,
				editor:{
					type:'combogrid',
					options: {
						width: 180,
						panelWidth: 480,
						idField: 'deptId',
						textField: 'deptName',
						pagination: true,// 是否分页
						singleSelect: true,// 只允许选择一行记录
						fitColumns: true,
						pageSize: 200,
						pageList:[200,400,500,600],
						pageNumber: 1,
						mode: 'remote',// 远程连接方式
						method: 'POST',// 请求方式
						dataType: 'json',
						url: platRoot + "/rimp/dept/list.json",
						delay: 200, // 输入到请求的时间间隔 （毫秒）
						required: false,
						columns: [[
							{ field: 'deptId', title: '科室编号', hidden: true },
							{ field: 'deptName', title: '科室名称', width: 120 },
							{ field: 'deptTyepName', title: '科别', width: 120 },
							{ field: 'deptAttr', title: '部门', width: 120 },
							{ field: 'hosAreaName', title: '院区', width: 120 }
						]],
						keyHandler: {
							up: function () {
								c_comboxgrid_keyUp(this);
							},
							down: function () {
								c_comboxgrid_keyDown(this);
							},
							enter: function () {
								c_comboxgrid_enterKey(this);
							},
							query: function (searchKey) {
								if (searchKey != null) {
									var grid = $(this).combogrid("grid");
									var searchParam = {
										'searchParams[deptName]': searchKey
									};
									var url = platRoot + "/rimp/dept/list.json";
									$(grid).datagrid('options').queryParams = searchParam;
									$(grid).datagrid('options').url = url;
									$(grid).datagrid('reload');
									$(this).combogrid("setValue", searchKey);
								}
							}
						}
					}
				},
				formatter: function(value,row,index){
					return row.schmDeptName;
				},
				width:10},
			{field:'deptId',title:'所属科室',editor:{type:'text',options:{required:true}} ,hidden:true},
			{field:'deptName',title:'所属科室', sortable:true, editor:{type:'text',options:{required:true}} ,width:10},
			{field:'schmState',title:'排班模板状态', editor:{type:'combobox',options:{
				valueField:'dictValue',
        		textField:'dictName',
        		required:true,
        		editable:false,
                url:platRoot+'/platform/dictionary/show_dic_by_type.json?typeId=STATE'},
            },
            formatter: function(value,row,index){
            	if (row.schmState == 'state_0')
					return "<span title='红色为停用状态'><font color='red'>"+ row.schmStateName + "</font></span>";
				else
					return row.schmStateName;
			}, width:10},
			{field:'schmRegiSum',title:'总限数', sortable:true, editor:{type:'text',options:{required:true,readonly:true}}, width:10},
			{field:'schmOnSum',title:'线上预约限数', sortable:true,
				editor:{type:'numberbox', options : {
						value:"0",
						min:0,
						precision:0,
						required:true,
						onChange:function(newValue,oldValue){
							var ed_schmDownSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownSum'});
							var n1 = $(ed_schmDownSum.target).val();
							n1= n1==""?0:parseInt(n1);
							var ed_schmOnRegiSum= $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnRegiSum'});
							var n2 = $(ed_schmOnRegiSum.target).val();
							n2= n2==""?0:parseInt(n2);
							var ed_schmDownRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownRegiSum'});
							var n3 = $(ed_schmDownRegiSum.target).val();
							n3= n3==""?0:parseInt(n3);
							var ed_schmRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmRegiSum'});
							$(ed_schmRegiSum.target).val(eval(n1+n2+n3+parseInt(newValue)));
						}
					}
				}, width:10},
			{field:'schmDownSum',title:'线下预约限数', sortable:true,
					editor:{type:'numberbox',
						options : {value:"0", min:0, precision:0,required:true,
							onChange:function(newValue,oldValue){
								var ed_schmOnSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnSum'});
								var n1 = $(ed_schmOnSum.target).val();
								n1= n1==""?0:parseInt(n1);
								var ed_schmOnRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnRegiSum'});
								var n2 = $(ed_schmOnRegiSum.target).val();
								n2= n2==""?0:parseInt(n2);
								var ed_schmDownRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownRegiSum'});
								var n3 = $(ed_schmDownRegiSum.target).val();
								n3= n3==""?0:parseInt(n3);
								var ed_schmRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmRegiSum'});
								 $(ed_schmRegiSum.target).val(eval(n1+n2+n3+parseInt(newValue)));
							}}
				}, width:10},
			{field:'schmOnRegiSum',title:'线上挂号限数', sortable:true, editor:{type:'numberbox', options : {value:"0", min:0, precision:0,required:true,
				onChange:function(newValue,oldValue){
					var ed_schmOnSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnSum'});
					var n1 = $(ed_schmOnSum.target).val();
					n1= n1==""?0:parseInt(n1);
					var ed_schmDownSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownSum'});
					var n2 = $(ed_schmDownSum.target).val();
					n2= n2==""?0:parseInt(n2);
					var ed_schmDownRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownRegiSum'});
					var n3 = $(ed_schmDownRegiSum.target).val();
					n3= n3==""?0:parseInt(n3);
					var ed_schmRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmRegiSum'});
					 $(ed_schmRegiSum.target).val(eval(n1+n2+n3+parseInt(newValue)));
				}}}, width:10},
			{field:'schmDownRegiSum',title:'线下挂号限数', sortable:true, editor:{type:'numberbox', options : {value:"0", min:0, precision:0,required:true,
				onChange:function(newValue,oldValue){
					var ed_schmOnSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnSum'});
					var n1 = $(ed_schmOnSum.target).val();
					n1= n1==""?0:parseInt(n1);
					var ed_schmDownSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmDownSum'});
					var n2 = $(ed_schmDownSum.target).val();
					n2= n2==""?0:parseInt(n2);
					var ed_schmOnRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmOnRegiSum'});
					var n3 = $(ed_schmOnRegiSum.target).val();
					n3= n3==""?0:parseInt(n3);
					var ed_schmRegiSum = $(dataTable).datagrid("getEditor", {index : editRowIndex, field: 'schmRegiSum'});
					 $(ed_schmRegiSum.target).val(eval(n1+n2+n3+parseInt(newValue)));
				}}}, width:10},
            {field:'ext1',title:'挂号类别', sortable:true, editor:{
                    type:'combogrid',
                    options:{
                        panelWidth:370,
                        idField:'id',
                        textField:'regcategory',
                        pagination : true,// 是否分页
                        singleSelect : true,// 只允许选择一行记录
                        fitColumns:true,
                        mode : 'remote',// 远程连接方式
                        method : 'POST',// 请求方式
                        dataType : 'json',
                        url: platRoot + "/rimp/regcategory/list.json",
                        delay: 200, // 输入到请求的时间间隔 （毫秒）
                        required: false,
                        columns: [[
                            { field: 'id', title: '编号', hidden: true },
                            { field: 'regcategory', title: '挂号类别', width: 120 },
                            { field: 'regfee', title: '挂号费', width: 120 },
                            { field: 'servcoding', title: 'HIS编码', hidden: true }
                        ]],
                        keyHandler: {
                            up: function () {
                                c_comboxgrid_keyUp(this);
                            },
                            down: function () {
                                c_comboxgrid_keyDown(this);
                            },
                            enter: function () {
                                c_comboxgrid_enterKey(this);
                            },
                            query: function (searchKey) {
                                if (searchKey != null) {
                                    var grid = $(this).combogrid("grid");
                                    var searchParam = {
                                        'searchParams[regcategory]': searchKey
                                    };
                                    var url = platRoot + "/rimp/regcategory/list.json";
                                    $(grid).datagrid('options').queryParams = searchParam;
                                    $(grid).datagrid('options').url = url;
                                    $(grid).datagrid('reload');
                                    $(this).combogrid("setValue", searchKey);
                                }
                            }
                        }
                    }
                },
                formatter: function(value,row,index){
                    return row.regcategory;
                },
            }
		]],
		onBeforeEdit:function(rowIndex, rowData){
			if(editRowIndex >= 0){
				return false;
			}
			editRowIndex = rowIndex;
			$("#schmbasemaininfo-cancelBtn").attr("eidtFlag","true");
		},
		onLoadSuccess:function(){
			$(dataTable).datagrid("clearSelections");
		}
	});
	var pg = $(dataTable).datagrid("getPager");
	if(pg)
	{
	   $(pg).pagination({
	       onBeforeRefresh:function(){
	    	   editRowIndex = -1;
			   $("#schmbasemaininfo-cancelBtn").attr("eidtFlag","false");
	       }
	   });
	}

});
/**
 * 导出按钮
 * @returns
 */
var report = function (url) {
    var dataTable = $("#dg-schmbasemaininfo");
    $(dataTable).datagrid('load', $('#searchform').toJson());
    var data = $(dataTable).datagrid('getData');
    if (data && data.rows && data.rows.length > 0) {
        url += "?page=1&rows=10000&" + $('#searchform').serialize();
        console.log(url);
        window.location.href = url;
    } else {
        $.messager.alert("提示", "数据列表为空，不能导出!", "error");
    }
}

/**
 * 查询按钮
 * @returns
 */
var _search = function () {
    var dataTable = $("#dg-schmbasemaininfo");
    $(dataTable).datagrid('load', $('#searchform').toJson());
    $('#report').linkbutton('enable');
}
