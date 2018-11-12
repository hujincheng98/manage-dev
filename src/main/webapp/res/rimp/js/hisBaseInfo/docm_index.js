$(function(){
	
	//控件变量初始化
	var orgTree = $("#org_tree");
	var selectOrgNode = null;
	var editIndex = -1;
	var dataTable = $("#dg");
	
	//查询
	$("#searchBtn").click(function(){
		var json = $("#searchform").toJson();
		if (json != "") {
			$(dataTable).datagrid("load", json);
		}
	});
	
	//解析树
	var parseOrgTree = function(data) {
		var childNodes = new Array();
		for ( var i = 0, l = data.length; i < l; i++) {
			childNodes[i] = {};
			childNodes[i].id = data[i].deptId;
			childNodes[i].text = data[i].deptName.replace(/\.n/g, '.');
			if (data[i].isParent) {
				childNodes[i].state = 'closed';
			}
		}
		return childNodes;
	};
	
	//点击事件
	var treeOnClick = function(node) {
		selectOrgNode = node;
		$(dataTable).datagrid('load', {
			'searchParams[deptId]' : node.id
		});
	};
	
	//树形科室加载
	$(orgTree).tree({
		url : 'listRoot.json',
		lines : true,
		id : 'parentDeptId',
		onClick : treeOnClick,
		loadFilter : parseOrgTree 
	});
	
	//删除
	$("#deleteBtn").click(function(){
		
		var rows = $('#dg').datagrid('getSelections');
		
		if (rows && rows.length > 0)
		{
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].docmId;
				} else {
					ids += rows[i].docmId + ',';
				}
			}
			
			$.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue){
				if(isTrue)
				{
					var data = {ids : ids};
					$.ajax({
						url : 'delete.json',
						type : 'POST',
						data : data,
						async : false,
						dataType : 'json',
						success : function(data)
						{
							$('#dg').datagrid("clearSelections");//清除前一次选中状态
							if (data.state == 'success') {
								$.messager.alert("提示", "删除成功！", 'info', function (){
									$(dataTable).datagrid("reload");
								});
							} else {
								$.messager.alert('错误', data.message, 'error');
							}
						}
					});
					
				}
			});
			
		}
		else
		{
			$.messager.alert("提示", "请选择要删除的行。");
		}
	});
	
	//重置
	$("#clearBtn").click(function(){
		$('#docmNameSearch').textbox('setValue', '');
		$("#docmState").combobox('clear');// 清空选择表格数据
		$("#searchBtn").click();
	});
	
	
	$(dataTable).datagrid({
		url:'list.json',
		title: "医生信息",
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
		idField : 'docmId', // 主键
		/*onDblClickRow : function (index, row){
			$('#detailDialog').dialog('open');
		},*/
		columns:[[
		    {field:'docmId', title:'医生编号', checkbox:true, width:10},
		    {field:'docmName', title:'医生姓名', width:10},
		    {field:'docmCredTypeName', title:'证件类型', width:10},
		    {field:'docmCredNum', title:'证件号码', width:10},
		    {field:'docmSexName', title:'性别', width:5},
		    {field:'docmEducName', title:'学历', width:5},
		    {field:'docmBirthDate', title:'出生日期', width:10},
		    {field:'docmNationName', title:'民族', width:10},
		    {field:'docmPosition', title:'职务', width:10},
		    {field:'docmTitleName', title:'职称', width:10},
		    {field:'docmWorkNatureName', title:'工作性质', width:5},
		    {field:'docmRoom', title:'所在诊室', width:10},
		    {field:'deptName', title:'所属科室', width:10},
		    {field:'docmStateName', title:'员工状态', width:5,
		    	formatter : function(value,row,index){
					if (row.docmState == 'docm_state_3')
						return "<span title='红色为停用状态'><font color='red'>"+ row.docmStateName + "</font></span>";
					else 
						return row.docmStateName;
		    	}
		    },
		    {field:'diagnosisInte', title:'接诊间隔(秒)'},
		    {field:'docmWageNum', title:'手机号码', hidden:true},
		    {field:'orgId', title:'所属组织机构', hidden:true}
		]],
		onLoadSuccess:function(){
			$(dataTable).datagrid("clearSelections");
		}
	});
	
	
	
	//$(dataTable).datagrid('hiddenHeaderChecked', false);
	
});



//启用、停用、可预约、不可预约
function submitState(stateType){
	
	var rows = $('#dg').datagrid('getSelections');
	if (rows && rows.length > 0)
	{
		var ids = '';
		for (var i = 0; i < rows.length; i++) {
			if (i == rows.length - 1) {
				ids += rows[i].docmId;
			} else {
				ids += rows[i].docmId + ',';
			}
		}
		
		$.messager.confirm("警告", "您确定要执行选择的记录?", function(isTrue){
			if(isTrue)
			{
				var data = {ids:ids,docmState:stateType};
				$.ajax({

					url : 'updateState.json',
					type : 'POST',
					data : data,
					async : false,
					dataType : 'json',
					success : function(data)
					{
						 $('#dg').datagrid("clearSelections");//清除前一次选中状态
						 $('#dg').datagrid('reload');   
						
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
					}
					
				});
				
			}
		});
		
	}
	else
	{
		$.messager.alert("提示", "请选择医生数据。");
	}
	
}

//新增
var add = function () {
	$('#addDialog').dialog('open');
}

//新增保存
var save = function () {
	if(!$('#docm_add_form').form('validate')){
		return;
	}
	
	if ($('#docmPhone').val() == '') {
		$.messager.alert("提示", '请上传照片', "error");
		return;
	}
	
	$('#docm_add_form').form('submit', {
		url: 'save.json',
		success: function(data){
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#addDialog').dialog('close');
					$('#docm_add_form').form('clear');
					$('#dg').datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "数据保存失败！")
			}
		}
	});
	
	/*var json =  $("#docm_add_form").toJson();
	var data = doPost('save.json', json);
	if (data.state) {
		$.messager.alert("提示", data.message, "info", function() {
			$('#addDialog').dialog('close');
			$('#docm_add_form').form('clear');
			$('#dg').datagrid("reload");
		});
	} else {
		$.messager.alert("提示", "数据保存失败！")
	}*/

}


//修改
var edit = function (){
	var rows = $("#dg").datagrid("getSelections");
	if(rows.length > 1 || rows.length == 0){
		$.messager.alert('提示','请选择一条记录进行操作.');
	} else {
		$('#editDialog').dialog('open');
	}
	
}


//修改保存
var update = function () {
	if(!$('#docm_edit_form').form('validate')){
		return ;
	}
	$('#docm_edit_form').form('submit', {
		url: 'update.json',
		success: function(data){
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#editDialog').dialog('close');
					$('#docm_edit_form').form('clear');
					$('#dg').datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "数据修改失败！")
			}
		}
	});
	/*var json =  $("#docm_edit_form").toJson();
	var data = doPost('update.json', json);
	if (data.state) {
		$.messager.alert("提示", data.message, "info", function() {
			$('#editDialog').dialog('close');
			$('#docm_edit_form').form('clear');
			$('#dg').datagrid("reload");
		});
	} else {
		$.messager.alert("提示", "数据保存失败！")
	}*/
}