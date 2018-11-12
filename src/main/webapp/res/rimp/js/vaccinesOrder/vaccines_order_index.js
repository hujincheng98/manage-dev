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

//得到当前日期
formatterDate = function(date) {
var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
+ (date.getMonth() + 1);
return date.getFullYear() + '-' + month + '-' + day;
};

/*window.onload = function () { 
var curDate = new Date();
curDate.setDate(curDate.getDate()+1);
$('#startTime').datebox('setValue', formatterDate(curDate));
$('#apDateEnd').datebox('setValue', formatterDate(curDate));
}*/
/**
 * 
 * @returns
 */
$(function (){

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
		idField : 'userTelephone', // 主键
		columns:[[
		    {field:'userName', title:'接种人姓名', width:10, sortable:true},
		    {field:'userBirthday', title:'接种人出生日期', sortable:true},
		    {field:'userTelephone', title:'联系电话', width:10, sortable:true},
		    {field:'vacDate', title:'接种完成日期', width:10, sortable:true},
		    {field:'apDate', title:'预约接种日期', width:10, sortable:true},
		    {field:'sequenceID', title:'预约编号', width:10, sortable:true},
		    {field:'vacName', title:'接种疫苗内容', width:20, sortable:true},		    
		    {field:'states',
		    	title:'状态',
		    	width:10, 
		    	sortable:true,
				formatter: function(value,row,index){
					//已预约-1，已取消-0,已接种-2,全部-""
	                if (row.states=='1'){
	                    return '已预约';
	                }
	                else if (row.states=='0'){
	                	 return '已取消';
	                }
	                else if (row.states=='2'){
	                	return '已接种';
	                }
	                else if (row.states==''){
	                	return '全部';
	                }
	            }
		    	}		   
		]]  
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
});