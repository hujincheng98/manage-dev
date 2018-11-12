$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$(dataTable)
			.datagrid(
					{
						url : "opt_list.json",
						title : "操作日志",
						idField : "logId",
						striped : true,
						singleSelect : true,
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,
						columns : [ [
								{
									field : "logId",
									hidden : true
								},
								{
									title : "操作账号",
									field : "optUser",
									width : "5%"
								},
								{
									title : "IP",
									field : "ip",
									width : "5%"
								},
								{
									title : "MAC地址",
									field : "mac",
									width : "8%"
								},
								{
									title : "请求URL",
									field : "url",
									width : "15%"
								},
								{
									title : "请求参数",
									field : "parameters",
									width : "10%"
								},
								{
									title : "业务方法",
									field : "serviceClassName",
									width : "20%"
								},
								{
									title : "业务参数",
									field : "methodParameters",
									width : "15%"
								},
								{
									title : "操作时间",
									field : "optDate",
									width : "8%"
								},
								{
									title : "异常信息",
									field : "exception",
									width : "10%",
									formatter : function(value, row, index) {
										if (value) {
											return '<a id="exception" href="#" title="'
													+ row.exception
													+ '" class="easyui-tooltip">异常信息</a>';
										} else {
											return "";
										}
									}
								} ] ]
					});

	$("#resetBtn").click(function() {
		$("#loginName").textbox('setValue', '');
		$("#startDate").datebox('setValue', '');
		$("#endDate").datebox('setValue', '');
		$(dataTable).datagrid("load", {});
	});

	$("#searchBtn").click(function() {
		var loginName = $("#loginName").val();
		var startDate = $("#startDate").datebox("getValue");
		var endDate = $("#endDate").datebox("getValue");
		$(dataTable).datagrid("load", {
			'searchParams[loginName]' : loginName,
			'searchParams[startDate]' : startDate,
			'searchParams[endDate]' : endDate
		});
	});

});
