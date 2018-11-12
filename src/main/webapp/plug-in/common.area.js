var defaults = {
	province : 'provinceSelect',
	city : 'citySelect',
	county : 'countySelect'
};

$(function() {
	threeSelect(defaults);
});

function threeSelect(config) {
	var $province = $("#" + config.province);
	var $city = $("#" + config.city);
	var $county = $("#" + config.county);
	var v1 = config.v1 ? config.v1 : null;
	var v2 = config.v2 ? config.v2 : null;
	var v3 = config.v3 ? config.v3 : null;
	$("<option value=\"00\">请选择省份</option>").appendTo($province);
	appendOptionTo($province, "0", v1);

	$province.change(function() {
		$city.html("");
		$county.html("");
		if (this.selectedIndex == -1)
			return;
		var province_curr_val = this.options[this.selectedIndex].value;
		$("<option value=\"00\">请选择市区</option>").appendTo($city);
		appendOptionTo($city, province_curr_val, v2);
		if ($city[0].options.length == 0) {
			appendOptionTo($city, "...", "", v2);
		}
		$city.change();
	}).change();

	$city.change(function() {
		$county.html("");
		// var province_curr_val =
		// $province[0].options[$province[0].selectedIndex].value;
		if (this.selectedIndex == -1)
			return;
		var city_curr_val = this.options[this.selectedIndex].value;
		$("<option value=\"00\">请选择区县</option>").appendTo($county.show());
		appendOptionTo($county.show(), city_curr_val, v3);
	}).change();

	function appendOptionTo($o, parentId, d) {
		var url = "/platform/base/child_area.json?parentAreaCode=" + parentId;
		$.ajax({
			cache : true,
			url : url,
			type : 'POST',
			contentType : 'application/json',
			success : function(data) {
				$.each(data, function(index, area) {
					var $opt = $("<option>").text(area.areaName).val(
							area.areaCode);
					if (area.areaCode == d) {
						$opt.attr("selected", "selected")
					}
					$opt.appendTo($o);
				});
			},
			error : function(request, status, errorThrown) {
				returnData = {
					state : false
				};
			}
		});
	}

}