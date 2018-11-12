// 设置登录窗口
function openPwd() {
	$("#w").window({
		title : "修改密码",
		width : 300,
		modal : true,
		shadow : true,
		closed : true,
		height : 196,
		resizable : false
	});
}
// 关闭登录窗口
function closePwd() {
	$("#w").window("close");
}

// 修改密码
function serverLogin() {
	var $oldpass = $("#txtOldPass");
	var $newpass = $("#txtNewPass");
	var $rePass = $("#txtRePass");

	if ($newpass.val() == "") {
		msgShow("系统提示", "请输入密码！", "warning");
		return false;
	}
	if ($rePass.val() == "") {
		msgShow("系统提示", "请在一次输入密码！", "warning");
		return false;
	}

	if ($newpass.val() != $rePass.val()) {
		msgShow("系统提示", "两次密码不一至！请重新输入", "warning");
		return false;
	}
	var url = platRoot + "/platform/user/edit_password.json";
	var passJson = {};
	passJson.oldPassword = $oldpass.val();
	passJson.newPasswrod = $newpass.val();
	$.ajax({
		cache : true,
		url : url,
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify(passJson),
		async : false,
		success : function(data) {
			$oldpass.val("");
			$newpass.val("");
			$rePass.val("");
			$.messager.alert("系统提示", data.msg, "info", function() {
				if (data.state == "success") {
					window.location = "logout.do";
				}
			});
		}
	});
}

$(function() {

	openPwd();

	$("#editpass").click(function() {
		$("#w").window("open");
	});

	$("#btnEp").click(function() {
		serverLogin();
	})

	$("#btnCancel").click(function() {
		closePwd();
	})

	$("#loginOut").click(function() {
		$.messager.confirm("系统提示", "您确定要退出本次登录吗?", function(r) {
			if (r) {
				location.href = "logout.do";
			}
		});
	})

	$("#refreshSysInfoCacheBtn").click(function() {
		var data = doPost("cache/refresh_sysinfo_cache.json");
		if (data.state) {
			$.messager.alert("提示", "缓存刷新成功。");
		}
	});

	$("#refreshDictionary").click(function() {
		var data = doPost("cache/refresh_dictionary_cache.json");
		if (data.state) {
			$.messager.alert("提示", "缓存刷新成功。");
		}
	});

	$("#refreshStrategy").click(function() {
		var data = doPost("cache/refresh_strategy_cache.json");
		if (data.state) {
			$.messager.alert("提示", "缓存刷新成功。");
		}
	});
});

$(function() {
	$('#pp').portal({
		border : false,
		fit : true
	});
	add();
});
function add() {
	var p = $('#pg').propertygrid({
		showGroup : true,
		showHeader : false
	});

	$('#pp').portal('add', {
		panel : p,
		columnIndex : 0
	});
	$('#pp').portal('resize');
}
function remove() {
	$('#pp').portal('remove', $('#pgrid'));
	$('#pp').portal('resize');
}