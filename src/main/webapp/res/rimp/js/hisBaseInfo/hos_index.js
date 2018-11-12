var tempHtml = undefined;
/**
 * 新增按钮
 */
var add = function () {
	$('#addDialog').dialog('open');
}

var dataTable = '#dg';

/**
 * 更新按钮
 * @returns
 */
var edit = function (){
	var row = $(dataTable).datagrid("getSelected");
	if(!row){
		$.messager.alert('提示','请选择要修改的数据.');   
	} else {
		$('#editDialog').dialog('open');
	}
}

/**
 * 删除按钮
 * @returns
 */
var _remove = function (){
	var row = $(dataTable).datagrid("getSelected");
	if(row){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		    	var data = [];
				$.ajax({
					url : 'delete.json',
					cache : true,
					type : 'POST',
					data : {hosId : row.hosId},
					async : false,
					success : function(data)
					{
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
	} else {
		$.messager.alert('提示','请选择要删除的数据.');   
	}
}

/**
 * 保存按钮
 * @returns
 */
var save = function () {
	if ($('#hos_add_form').form('validate')) {
		
		if($('#hos_add_form').find("input[name=hosPropOne]").val()==""){
			$.messager.alert("提示", '请上传照片', "error");
			return;
		}
		$('#hos_add_form').form('submit', {
			url: 'save.json',
			success: function(data){
				data = JSON.parse(data);
				if (data.state == 'success') {
					$.messager.alert("提示", data.message, "info", function() {
						$('#addDialog').dialog('close');
						$('#hos_add_form').form('clear');
						//清空图片
						$("#preview").html(tempHtml);
						$('#dg').datagrid("reload");
					});
				} else {
					$.messager.alert("提示", "数据保存失败！")
				}
			}
		});
		
	}
}

/**
 * 修改方法
 * @returns
 */
var update = function (){
	$('#hos_edit_form').form('submit', {
		url: 'update.json',
		success: function(data){
			console.log(data)
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#editDialog').dialog('close');
					//清空图片
					$('#dg').datagrid("reload");
				});
			} else {
				$.messager.alert("提示", "更新失败！")
			}
		}
	});
}

/**
 * 查询按钮
 * @returns
 */
var _search = function () {
	$(dataTable).datagrid('load', $('#searchform').toJson());
}

$(function (){
	$(dataTable).datagrid({
		url:'list.json',
		title: "医院信息",
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
		idField : 'hodId', // 主键
		columns:[[
		    {field:'hodId', title:'医院编号', checkbox:true, width:10},
		    {field:'hosName', title:'医院名称', width:20},
		    {field:'hosLevelName', title:'医院等级', width:10},
		    {field:'hosWebAddr', title:'网站地址', width:20},
		    {field:'hosAddr', title:'医院地址', width:20},
		    {field:'hosTelep', title:'联系电话', width:10},
		    {field:'hosEmail', title:'邮箱', width:20}
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
});