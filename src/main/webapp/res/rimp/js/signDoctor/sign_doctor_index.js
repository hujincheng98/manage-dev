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
		window.location.href = "excel.json";
	} else {
		$.messager.alert("提示", "数据列表为空，不能导出!", "error");
	}
}



$(function (){
	function dateFtt(fmt,date)   
	{ //author: meizz   
	  var o = {   
	    "M+" : date.getMonth()+1,                 //月份   
	    "d+" : date.getDate(),                    //日   
	    "h+" : date.getHours(),                   //小时   
	    "m+" : date.getMinutes(),                 //分   
	    "s+" : date.getSeconds(),                 //秒   
	    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
	    "S"  : date.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	} 
	
	
	$(dataTable).datagrid({
		url:'info.json',
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
		idField : 'idCard', // 主键
		columns:[[
		    {field:'userName', title:'签约人姓名', width:10, sortable:true},
		    {field:'idCard', title:'签约人身份证号', sortable:true},
		    {field:'telePhone', title:'签约人联系电话', width:10, sortable:true},
		    {field:'streetName', title:'签约人所在地区', width:10, sortable:true},
		    {field:'detailedAddress', title:'签约人详细地址', width:20, sortable:true},
		    {field:'signedTime',
		    	title:'签约预约日期',
		    	width:20,
		    	sortable:true,
				formatter: function(value,row,index){
					var str = row.signedTime;
					var crtTime = new Date(str);
					return dateFtt("yyyy-MM-dd",crtTime);
	            }
		    },
		    
		    {field:'docName', title:'签约医生姓名', width:20, sortable:true}
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
});