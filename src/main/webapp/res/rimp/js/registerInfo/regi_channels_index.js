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
		title: "预约信息",
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
		idField : 'regiId', // 主键
		columns:[[
		    {field:'regiId', title:'预约编号', checkbox:true, width:10, sortable:true},
		    {field:'bespeakid', title:'预约号', width:10, sortable:true},
		    {field:'bespeakdate', title:'就诊日期', width:10, sortable:true},
		    {field:'patientType', title:'患者类型', width:10, sortable:true,
		    	formatter : function(value,row,index){
		    		var str = '';
		    		if (row.patientType == 1) {
		    			str = '成人';
		    		} else if (row.patientType == 2) {
		    			str = '儿童';
		    		} 
					return str;
		    	}},
		    {field:'patientname', title:'患者姓名', width:10, sortable:true},
		    {field:'patientSex', title:'患者性别', width:10, sortable:true},
		    {field:'guarName', title:'监护人姓名', sortable:true},
		    {field:'identitycard', title:'患者身份证号', width:10, sortable:true},
		    {field:'telephone', title:'联系电话', width:10, sortable:true},
		    {field:'hosName', title:'预约医院', width:10, sortable:true},
		    {field:'bespeakofficeid', title:'预约科室', width:10, sortable:true},
		    {field:'bespeakdoctorid', title:'预约医生', width:10, sortable:true},
		    {field:'worktype', title:'预约班次', width:10, sortable:true},
		    {field:'bespeakchannels', title:'渠道名称', width:10, sortable:true},
		    {field:'registateName', title:'预约状态', width:10, sortable:true},
		    {field:'dataType', title:'预约类型', width:10, sortable:true},
		    {field:'starttime', title:'预约开始时段', width:10, sortable:true},
		    {field:'endtime', title:'预约结束时段', width:10, sortable:true},
		    {field:'queueNum', title:'排队号', width:10, sortable:true},
		    {field:'queueDate', title:'排队时间点', width:10, sortable:true}
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
	});  */
	
});