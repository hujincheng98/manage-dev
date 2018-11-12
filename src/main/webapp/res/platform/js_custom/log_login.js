$(function() {
	var editIndex = -1;
	var dataTable = $("#tableList");

	$(dataTable).datagrid({
		url : "login_list.json",
		title : "登录日志",
		idField : "logId",
		striped : true,
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		pagination : true,
		columns : [ [ {
			field : "logId",
			hidden : true
		}, {
			title : "登录账号",
			field : "loginName",
			width : "15%"
		}, {
			title : "登录IP",
			field : "loginIp",
			width : "15%"
		}, {
			title : "登录MAC地址",
			field : "loginMac",
			width : "15%"
		}, {
			title : "登录时间",
			field : "loginDate",
			width : "15%"
		}, {
			title : "日志描述",
			field : "remark",
			width : "40%"
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
