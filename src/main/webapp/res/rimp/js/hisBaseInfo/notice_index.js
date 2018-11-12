$(function(){
	
	var editIndex = -1;
	var dataTable = $("#dg");
	
	//查询
	$("#searchBtn").click(function(){
		var json = $("#searchform").toJson();
		if (json != "") {
			$(dataTable).datagrid("load", json);
		}
	});
	
	//判断公告唯一性
	
	/*$(document).click(function(){
		var hosId = $('#affiliatedhos').combobox('getValue').trim();
	    //var text = $('#affiliatedhos').combobox('getText');
		var noticetype = $("#noticetype").combobox('getValue').trim();
		//医院id和通告类型均有值
		if(hosId !== null && hosId !== undefined && hosId !== '' && noticetype !==null && noticetype !==undefined && noticetype!== ''){
			//通告类型只能是预约说明和停诊通知
			if(noticetype == "noticetype_01" || noticetype == "noticetype_02"){
				$.ajax({
					url: 'checkNoticeUnique.do',
				    dataType : "json",
				    type : "post",
				    data : {"hosId":hosId,"noticetype":noticetype},
				    success : function(data){
				    	console.log(data);
				    	if (data.state == 'fail') {
				    		alert("fail");
				    		$.messager.alert("错误", "data.message！", 'error', function (){
								$(dataTable).datagrid("reload");
							});
				    	}
						
				    }
					
				});
				
			}
		}
	    
	});*/
	//删除
	$("#deleteBtn").click(function(){
		
		var rows = $('#dg').datagrid('getSelections');
		
		if (rows && rows.length > 0)
		{
			var ids = '';
			for (var i = 0; i < rows.length; i++) {
				if (i == rows.length - 1) {
					ids += rows[i].id;
				} else {
					ids += rows[i].id + ',';
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
									$('#dg').datagrid("reload");
									alert("aaa");
								});
							} else {
								$.messager.alert('错误', data.message, 'error');
								$('#dg').datagrid("reload");
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
		/*$('#noticeNameSearch').textbox('setValue', '');
		$("#noticeType").combobox('clear');// 清空选择表格数据
		$("#searchBtn").click();
		$('#releaseStartDate').combo("setValue","");
    	$('#releaseStartDate').combo("setText","");
    	$('#releaseEndDate').combo("setValue","");
    	$('#releaseEndDate').combo("setText","");
    	$('#offlineStartDate').combo("setValue","");
    	$('#offlineStartDate').combo("setText","");
    	$('#offlineEndDate').combo("setValue","");
    	$('#offlineEndDate').combo("setText","");*/
		$('#searchform').form('clear');
    	$("#searchBtn").click();
	});
	
	
	$(dataTable).datagrid({
		url:'list.json',
		title: "通告通知信息",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : false,
		rownumbers: true,
		showFooter: true,
		pagination:true,
		fit: false,//自适应大小
		nowrap : true,//把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'id', // 主键
		/*onDblClickRow : function (index, row){
			$('#detailDialog').dialog('open');
		},*/
		columns:[[
		    {field:'id', title:'公告id', checkbox:true, width:10},
		    {field:'noticename', title:'通告名称', width:10, sortable:true},
		    {field:'noticetypeName', title:'通告类型', width:10, sortable:true},
		    {field:'affiliatedhos', title:'所属医院', width:10, sortable:true},
		    {field:'releasedate', title:'发布日期', width:5, sortable:true},
		    {field:'offlinedate', title:'下线日期', width:5, sortable:true}
		   
		]],
		onLoadSuccess:function(){
			$(dataTable).datagrid("clearSelections");
		}
	});
		
	//$(dataTable).datagrid('hiddenHeaderChecked', false);
	
});


//新增
var add = function () {
	$('#addDialog').dialog('open');
}

//新增保存
var save = function () {
	var flag = $('#checkNoticeUnique').val().trim();
	//alert(flag);
	if(!$('#notice_add_form').form('validate')){
		return;
	}
	
	if ($('#upload').val() == '') {
		$.messager.alert("提示", '请上传照片', "error");
		return;
	}
	if (flag) {
		$.messager.alert("提示", '同一个医院下预约说明或者停诊通知只能有一条', "error");
		return;
	}		
	$('#notice_add_form').form('submit', {
		url: 'save.json',
		success: function(data){
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#addDialog').dialog('close');
					$('#notice_add_form').form('clear');
					$('#dg').datagrid("reload");
				});
			} 
			else if(data.state == 'validatedDate') { 
				$.messager.alert("提示", data.message);
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
			else if(data.state == 'validatedNoticeName'){
				//$('#notice_add_form').form('clear');
				$.messager.alert("提示", data.message);
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
			else {
				$('#notice_add_form').form('clear');
				$.messager.alert("提示", "数据保存失败！");
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
		}
	});
	
	

}


//修改
var edit = function (){
	var rows = $("#dg").datagrid("getSelections");
	if(rows.length != 1){
		$.messager.alert('提示','请选择一条记录进行操作.');
	} else {
		$('#editDialog').dialog('open');
	}	
}


//修改保存
var update = function () {
	var flag = $('#checkNoticeUnique').val().trim();
	//alert(flag);
	/*if(!$('#notice_add_form').form('validate')){
		return;
	}*/
	
	/*if ($('#upload').val() == '') {
		$.messager.alert("提示", '请上传照片', "error");
		return;
	}*/
	if (flag) {
		$.messager.alert("提示", '同一个医院下预约说明或者停诊通知只能有一条', "error");
		return;
	}
	if(!$('#notice_edit_form').form('validate')){
		return ;
	}
	$('#notice_edit_form').form('submit', {
		url: 'update.json',
		success: function(data){
			data = JSON.parse(data);
			if (data.state == 'success') {
				$.messager.alert("提示", data.message, "info", function() {
					$('#editDialog').dialog('close');
					$('#notice_edit_form').form('clear');
					$('#dg').datagrid("reload");
				});
			}else if(data.state == 'validatedDate') { 
				$.messager.alert("提示", data.message);
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
			else if(data.state == 'validatedNoticeName'){
				$.messager.alert("提示", data.message);
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
			else {
				$.messager.alert("提示", "数据修改失败！");
				/*$('#addDialog').dialog('close');
				$('#notice_add_form').form('clear');
				$('#dg').datagrid("reload");*/
			}
		}
	});
	
}