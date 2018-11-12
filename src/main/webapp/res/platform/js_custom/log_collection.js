$(function() {
	$("#loginBtn").click(function() {
		if ($("#myform").form('validate')) {
			var startDate = $("#startDate").datebox("getValue");
			var endDate = $("#endDate").datebox("getValue");
			var params = {};
			params.type = "login";
			params.startDate = startDate;
			params.endDate = endDate;
			$.ajax({
				cache : true,
				url : "log_collection.json",
				type : 'POST',
				data : $.param(params),
				beforeSend : ajaxLoading,
				success : function(data) {
					ajaxLoadEnd();
					returnData = data;
				},
				error : function(request, status, errorThrown) {
					returnData = {
						state : false
					};
				}
			});
		}
	});

	$("#optBtn").click(function() {
		if ($("#optform").form('validate')) {
			var startDate = $("#optStartDate").datebox("getValue");
			var endDate = $("#optEndDate").datebox("getValue");
			var params = {};
			params.startDate = startDate;
			params.endDate = endDate;
			$.ajax({
				cache : true,
				url : "log_collection.json",
				type : 'POST',
				data : $.param(params),
				beforeSend : ajaxLoading,
				success : function(data) {
					ajaxLoadEnd();
					returnData = data;
				},
				error : function(request, status, errorThrown) {
					returnData = {
						state : false
					};
				}
			});
		}
	});
});

function ajaxLoadEnd() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
	$.messager.alert("提示", "日志采集成功。");
}

function ajaxLoading() {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : $(window).height()
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在采集，请稍候。。。").appendTo(
			"body").css({
		display : "block",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2
	});
}
