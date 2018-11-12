var dataTable = '#dg';

/**
 * 查询按钮
 * @returns
 */
var _search = function () {
	$(dataTable).datagrid('load', $('#searchform').toJson());
	$('#report').linkbutton('enable');
}

/**
 * 导出按钮
 * @returns
 */
var report = function (url) {
	$(dataTable).datagrid('load', $('#searchform').toJson());
	var data = $(dataTable).datagrid('getData');
	if (data && data.rows && data.rows.length > 0) {
		url += "?page=1&rows=10000&" + $('#searchform').serialize();
		window.location.href = url;
	} else {
		$.messager.alert("提示", "数据列表为空，不能导出!", "error");
	}
}

/**
 * 查看信息
 * @returns
 */
/*var view = function() {
	var rows = $("#dg").datagrid("getSelections");
	if (rows.length != 1) {
		$.messager.alert('提示', '请选择一条记录进行操作.');
	} else {
		$('#viewDialog').dialog('open');
	}
}*/

var view = function() {
	var rows = $("#dg").datagrid("getSelections");
	if (rows.length != 1) {
		$.messager.alert('提示', '请选择一条记录进行操作.');
	} else {
		var id = rows[0].id;
		$('#dd').dialog({
		    title: '解决方案',   
		    width: 600,   
		    height: 400,   
		    closed: false,
		    cache: false,
		    href: 'viewHisErrorLog.do?id='+id,   
		    modal: true,
		    toolbar:[		  
			  /* {
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
				$.messager.confirm('提示:','您确认要取消吗?',function(r){ 
					if(r){ 
						  $('#dd').dialog('close');
					      $('#res_auth_add_form').form('clear');
						 }
				}); 
					}
				}		*/
			]
	   }); 
		
		
	}
}

$(function (){
	
	$(dataTable).datagrid({
		url:'list.json',
		title: "错误日志查询",
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
		idField : 'transId', // 主键
		columns:[[
		    {field:'chnnelsName', title:'渠道名称', width:10, sortable:true, checkbox : true},
		    {field:'oId', title:'组织机构编码', width:10, sortable:true},
		    {field:'reqDate', title:'请求时间', width:20, sortable:true},
		    {field:'reqUrl', title:'请求url地址', width:30, sortable:true},
		    {field:'operationType', title:'交易名称', width:10, sortable:true},
		    {field:'transactionCode', title:'交易码', width:10, sortable:true},
		    {field:'requestData', title:'请求数据', width:45, sortable:true},
		    {field:'respCode', title:'返回编码', width:15, sortable:true},
		    {field:'respData', title:'返回数据', width:45, sortable:true},
		    {field:'hosName', title:'医院名称', width:20, sortable:true},
		    {field:'consuming', title:'请求时长', width:15, sortable:true}
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
/*	$.ajax({
		url : platRoot+'/rimp/payInhosPrepaidTrans/list.json',
		cache : true,
		type : 'POST',
		async : false,
		success : function(data)
		{
			if (data.rows) {
				$('#bespeakchannels').combobox({
					width:147,
					valueField:'token',
		            textField:'chName',
		            editable:false,
		            method:'post',
					data : data.rows
				});
			}
		}
	});  */
	
});