$(function() {

	/**
	 * 清理表单数据
	 */
	$.fn.clearValue = function() {
		var form = this[0];
		$(form).find("input").each(function(i) {
			$(this).val("");
		});
	};

	/**
	 * 将数据区域的 input数据封装成json
	 */
	$.fn.toJson = function() {
		var form = this[0];
		var arr = {};

		// 处理text类型
		$(form).find("input[type=text][name]").each(function(i) {
			if ($(this).attr("name"))
				arr[$(this).attr("name")] = $(this).val();
		});

		// 处理hidden类型
		$(form).find("input[type=hidden][name]").each(function(i) {
			if ($(this).attr("name"))
				arr[$(this).attr("name")] = $(this).val();
		});

		// 处理password类型
		$(form).find("input[type=password][name]").each(function(i) {
			if ($(this).attr("name"))
				arr[$(this).attr("name")] = $(this).val();
		});

		// 处理checkboxlist类型
		$(form).find("input[type=checkbox][name]").each(function(i) {
			if ($(this).attr("checked")) {
				if (!arr[$(this).attr("name")])
					arr[$(this).attr("name")] = "";
				if (i == 0) {
					arr[$(this).attr("name")] += $(this).val();
				} else {
					arr[$(this).attr("name")] += "|" + $(this).val();
				}
			}
		});

		// 处理radiobuttonlist类型
		$(form).find("input[type=radio][name]:checked").each(function(i) {
			arr[$(this).attr("name")] = $(this).val();
		});

		// 处理textarea类型
		$(form).find("textarea[name]").each(function(i) {
			arr[$(this).attr("name")] = $(this).val().replace(/\n/g,"\\n");
		});

		// 处理select类型
		$(form).find("select[name]").each(
				function(i) {
					// arr[$(this).attr("name")] = $(this).val() ;
					if ($(this).attr("inputtype")
							|| $(this).attr("inputtype") == "combogrid") {
						arr[$(this).attr("name")] = $(this).combogrid(
								'getValue');
					} else {
						// alert($(this).val());
						// alert($(this).find("option:selected").text());
						arr[$(this).attr("name")] = $(this).val();
					}
				});

		// 将数组转化为json字符串
		var jsonstr = "{";
		$.each(arr, function(idx2, val2) {
			jsonstr += "\"" + idx2 + "\" : " + "\"" + val2 + "\",";
		});
		if (jsonstr.length > 1)
			jsonstr = jsonstr.substring(0, jsonstr.length - 1);
		jsonstr += "}";
		var obj = eval("(" + jsonstr + ")");
		return obj;
	};

	var isNull = function(v) {
		if (v == undefined || v == null) {
			return true;
		}
		return false;
	};
	/**
	 * 将json数据自动填充到数据区域的表单 根据表单ID对应填入
	 */
	$.fn.fromJson = function(json) {
		var form = this[0];
		var arr = json;

		// 处理text类型
		$(form).find("input[type=text]").each(
				function(i) {
					if ($(this).attr("numbers") == "true")
						$('#' + $(this).attr("name") + '').numberbox(
								'setValue', arr[$(this).attr("name")]);
					else
						$(this).val(arr[$(this).attr("name")]);
				});

		// 处理hidden类型
		$(form).find("input[type=hidden]").each(function(i) {
			$(this).val(arr[$(this).attr("name")]);
		});

		// 处理password类型
		$(form).find("input[type=password]").each(
				function(i) {
					if ($(this).attr("numbers") == "true")
						$('#' + $(this).attr("name") + '').numberbox(
								'setValue', arr[$(this).attr("name")]);
					else
						$(this).val(arr[$(this).attr("name")]);
				});

		// 处理checkboxlist类型
		$(form).find("input[type=checkbox]").each(function(i) {
			var fieldvalue = arr[$(this).attr("name")];
			if (fieldvalue) {
				var itemvalues = fieldvalue.split('|');
				for (i in itemvalues) {
					if (!itemvalues[i] || itemvalues[i].length == 0)
						continue;
					var itempair = itemvalues[i];
					if (itempair.length != 2)
						continue;
					if (itempair[0] == $(this).val()) {
						$(this).attr("checked", "true");
						break;
					}
				}
			}
		});

		// 处理radiobuttonlist类型
		$(form).find("input[type=radio]").each(function(i) {
			var fieldvalue = arr[$(this).attr("name")];
			if (fieldvalue) {
				var itempair = fieldvalue;
				if (itempair == $(this).val()) {
					$(this).attr("checked", "true");
				}
			}
		});

		// 处理textarea类型
		$(form).find("textarea").each(function(i) {
			$(this).val(arr[$(this).attr("name")]);
		});

		// 处理select类型
		$(form).find("select").each(
				function(i) {

					if ($(this).attr("inputtype")
							|| $(this).attr("inputtype") == "combogrid") {
						var pairvalue = arr[$(this).attr("name")];
						if (!isNull(pairvalue)) {
							var pairarr = pairvalue;
							// alert(pairvalue);
							if (pairarr.length > 1) {
								var value = pairarr[0];
								// alert(value);
								$(this).combogrid('setValue', value);
								$(this).combogrid('setText', pairarr[1]);
							} else {
								$(this).combogrid('setValue', pairvalue);
							}
						}
					} else {
						var pairvalue = arr[$(this).attr("name")];
						if (!isNull(pairvalue)) {
							$(this).val(pairvalue);
						}
					}
				});
	};
});