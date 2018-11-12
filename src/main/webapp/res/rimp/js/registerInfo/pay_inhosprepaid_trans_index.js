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
		title: "住院预交金充值交易信息",
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
		    {field:'serialNumber', title:'支付流水号', width:10, sortable:true},
		    {field:'hisserialNumber', title:'his充值流水号', width:10, sortable:true},
		    {field:'inHosId', title:'住院号', width:10, sortable:true},
		    {field:'payDate', title:'充值时间', width:10, sortable:true},
		    {field:'patientName', title:'患者姓名', width:10, sortable:true},
		    /*{field:'identityCard', title:'身份证号', sortable:true,formatter:formatCard},
		    {field:'telephone', title:'联系电话', width:10, sortable:true,formatter:formatPhone},*/
		    {field:'identityCard', title:'身份证号', sortable:true},
		    {field:'telephone', title:'联系电话', width:10, sortable:true},
		    {field:'money', title:'充值金额', width:10, sortable:true,
		    	formatter: function(value,row,index){
		       		if(row.money!=null && row.money!="" && typeof(value)!="undefined"){
			       		return row.money.toFixed(2);	
		       		}
	            }	
		    },
		    {field:'payTypeName', title:'充值方式', width:10, sortable:true},
		    {field:'transStateName', title:'充值状态', width:10, sortable:true},
		    {field:'chName', title:'渠道名称', width:10, sortable:true},
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
	/*function formatCard(val,row){
		if (val!='' && val!=undefined) {		
			val=val.substr(0,6)+"********"+val.substr(14);
			}
		return val;
		
	}
	function formatPhone(val,row){
		if (val!='' && val!=undefined) {		
			val=val.substr(0,3)+"****"+val.substr(7);
			}
		return val;		
	}*/
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