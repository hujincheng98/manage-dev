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

$(function (){
	
	$(dataTable).datagrid({
		url:'list.json',
		title: "门诊就诊卡充值信息",
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
		    {field:'transId', title:'充值记录编号', checkbox:true, width:10, sortable:true},
		    {field:'chTransNum', title:'支付流水号', width:10, sortable:true},
		    {field:'hisTransNum', title:'HIS充值流水号', width:10, sortable:true},
		    {field:'transTime', title:'充值时间', width:10, sortable:true},
		    {field:'transMedicardName', title:'患者姓名', width:10, sortable:true},
		    {field:'transMedicardIdencard', title:'身份证号', sortable:true},
		    {field:'transMedicardPhone', title:'联系电话', width:10, sortable:true},
		    {field:'transMedicardId', title:'就诊卡卡号', width:10, sortable:true},
		    {field:'transAmount', title:'充值金额', width:10, sortable:true,
		    	formatter: function(value,row,index){
		       		if(row.transAmount!=null && row.transAmount!="" && typeof(value)!="undefined"){
			       		return row.transAmount.toFixed(2);	
		       		}
	            }	
		    },
		    {field:'transTypeName', title:'充值方式', width:10, sortable:true},
		    {field:'transStateName', title:'充值状态', width:10, sortable:true},
		    {field:'chName', title:'渠道名称', width:10, sortable:true},
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
/*	$.ajax({
		url : platRoot+'/rimp/otherChannels/list.json',
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
	}); */ 
	
});