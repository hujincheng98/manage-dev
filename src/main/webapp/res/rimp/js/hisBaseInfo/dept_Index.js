//验证扩展，用于表单验证放号天数
$.extend($.fn.numberspinner.defaults.rules, {
        rateCheck:{
            validator:function(value,param){
	              if(/^[+]?[0-9]\d*$/.test(value)){
	                return parseFloat(value) >= parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
	              }else{
	                return false;
	              }
            },
            message:'请输入大于0的整数'
       }
  });
$(function(){
	
	var editIndex = -1;
	var dataTable = $("#tableList");
	var orgTree = $("#org_tree");
	var selectOrgNode = null;
	
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
	
	var treeOnClick = function(node) {
		selectOrgNode = node;
		$(dataTable).datagrid('load', {
			'searchParams[parentDeptId]' : node.id
		});
		/*$(dataTable).datagrid({  
		    url:'listChildren.json',  
		    queryParams:{  
		    	'parentId' : node.id 
		    }  
		});  */
	};
	
	$(orgTree).tree({
		url : 'listRoot.json',
		lines : true,
		id : 'parentDeptId',
		onClick : treeOnClick,
		loadFilter : parseOrgTree
	});
	/**
	 * 新增
	 */
	$('#addDept').click(function(){
		$('#addDialog').dialog('open');
	});
	/**
	 * 修改
	 */
	$('#editDept').click(function(){
		var rows = $(dataTable).datagrid("getSelections");
		if(rows.length != 1){
			$.messager.alert('提示','请选择一条记录进行操作.');
		} else {
			$('#editDialog').dialog('open');
		}
	});
	/**
	 * 删除
	 */
	$('#delete').click(function(){
		var rows = $(dataTable).datagrid("getSelections");
		if (rows && rows.length > 0){
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].deptId;
				} else {
					ids += rows[i].deptId + ',';
				}
			}
			$.messager.confirm('提示:','你确认要删除选中的科室吗?',function(r){ 
				if (r) {
					$.ajax({
				        url: 'delete.do',
				        method: 'POST',
				        data: {ids : ids},
				        dataType: 'json',
				        success: function(result){
				        	$(dataTable).datagrid("clearSelections");
				        	if(result.state==-99){
								$.messager.alert('错误','科室存在下级科室！','error');
								return;
							}
							if(result.state==-98){
								$.messager.alert('错误','科室存在关联医生！','error');
								return;
							}
							if(result.state>0){
								$.messager.alert('提示','删除成功！','info', function (){
									$(dataTable).datagrid("reload");
								});
							}else{
								$.messager.alert('错误', result.message, 'error');
							}
				        }
				    });
				}
			});
		}else{
			$.messager.alert("提示", "请选择要删除的行。");
		}
	});
	
	/**
	 * 查询
	 */
	$("#search").click(function(){
		$(dataTable).datagrid('load', {
			'searchParams[deptName]' : $("#serdeptName").val(),
			'searchParams[deptState]' : $('#serdeptState').combobox('getValue')
		});
	});
	/**
	 * 重置
	 */
	$("#reset").click(function(){
		$("#searchform").form('clear');
		$("#serdeptState").combobox('clear');// 清空选择表格数据
		$("#search").trigger("click");
	});
	
	/**
	 * 保存方法
	 */
	$("#save").click(function(){
		if(!$('#dept_add_form').form('validate')){
			$.messager.alert('错误','请输入必填项目！','error');
			return;
		}
		var json =  $("#dept_add_form").toJson();
		$.post("save.do",json, function(result) {
			if(result.state==0){
				$('#addDialog').dialog('close');
				$('#dept_add_form').form("clear");
				window.location.reload();
				$.messager.alert('提示','保存成功','info');
			}else{
				if(result.state==-97){
					$.messager.alert('错误', '科室名称重复', 'error');
				}else{
					$.messager.alert('错误', '保存失败', 'error');	
				}
			}
		});
	});
	/**
	 * 更新
	 */
	$("#update").click(function(){
		if(!$('#dept_edit_form').form('validate')){
			$.messager.alert('错误','请输入必填项目！','error');
			return;
		}
		var json =  $("#dept_edit_form").toJson();
		$.post("update.do",json, function(result) {
			if(result.state==0){
				$('#editDialog').dialog('close');
				$('#dept_edit_form').form("clear");
				window.location.reload();
				$.messager.alert('提示','保存成功','info');
			}else{
				$.messager.alert('错误', '保存失败', 'error');
			}
		});
	});
	$("#cancelAdd").click(function(){
		$.messager.confirm('提示:','您确认要取消吗?',function(event){ 
			if(event){ 
				$('#addDialog').dialog('close');
				$('#dept_add_form').form("clear");
			}
		}); 
	});
	
	$("#cancelEdit").click(function(){
		$.messager.confirm('提示:','您确认要取消吗?',function(event){ 
			if(event){ 
				$('#editDialog').dialog('close');
				$('#dept_edit_form').form("clear");
			}
		}); 
	});
	
	$("#cancelDetail").click(function (){
		$('#detailDialog').dialog('close');
		$('#dept_detail_form').form("clear");
	});
	
	$("#enabled").click(function(){
		IsEnabled("dept_state_1");
	});
	
	$("#disEnabled").click(function(){
		IsEnabled("dept_state_0");
	});
	
	//启用停用
	function IsEnabled(state){
		var rows = $(dataTable).datagrid("getSelections");
		if (rows && rows.length > 0){
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].deptId;
				} else {
					ids += rows[i].deptId + ',';
				}
			}
			$.ajax({
		        url: 'updateState.do',
		        method: 'POST',
		        data: {ids : ids,"deptState": state},
		        dataType: 'json',
		        success: function(result){
		        	if(result.state==0){
						$.messager.alert('提示','操作成功','info', function (){
							$(dataTable).datagrid("reload");
						});
					}else{
						$.messager.alert('错误', '操作失败', 'error');
					}
		        }
		    });
		} else {
			$.messager.alert("提示", "请选择要操作的行。");
		}
	}
	
	$(dataTable).datagrid({
		url:'list.json',
		title: "科室信息",
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
		idField : 'deptId', // 主键
		/*onDblClickRow : function (index, row){
			$('#detailDialog').dialog('open');
		},*/
		columns:[[
		    {field:'deptId', title:'科室编号', checkbox:true, width:10},
		    {field:'deptName', title:'科室名称', width:10},
		    {field:'deptTyepName', title:'科别', width:10},
		    {field:'deptTelep', title:'科室电话', width:10},
		    {field:'deptAttr', title:'部门', width:10},
		    {field:'hosAreaName', title:'院区', width:10},
		    {field:'deptPosi', title:'科室位置', width:10},
		    {field:'deptStaName', title:'科室状态', width:10,
		    	formatter : function(value,row,index){
					if (row.deptState == 'dept_state_0')
						return "<span title='红色为停用状态'><font color='red'>"+ row.deptStaName + "</font></span>";
					else 
						return row.deptStaName;
		    	}
		    },
		    {field:'hosName', title:'所属医院', width:10},
		    {field:'remarks', title:'备注', width:10},
		    {field:'releDayNum', title:'放号天数', width:10},
		    {field:'parentDeptId', title:'parent', width:10, hidden:true}
		]],
		onLoadSuccess:function(){
			$(dataTable).datagrid("clearSelections");
		}
	});
	
});

