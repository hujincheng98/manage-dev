$(function() {
	$('#areaTable')
			.treegrid(
					{
						width : 330,
						idField : 'areaCode',
						treeField : 'areaName',
						onBeforeLoad : function(row, param) {
							if (row) {
								$(this).treegrid('options').url = 'child_area.json?parentAreaCode='
										+ row.areaCode;
							} else {
								$(this).treegrid('options').url = 'child_area.json';
							}
						},
						loadFilter : function(data, parent) {
							$(data).each(function(index, c) {
								if (c.level < 3) {
									c.state = "closed";
								}
							});
							return data;
						},
						columns : [ [ {
							field : 'parentAreaId',
							hidden : true
						}, {
							field : 'areaName',
							title : '区域名称',
							width : 180
						}, {
							field : 'areaCode',
							title : '行政编码',
							width : 60,
							align : 'right'
						}, {
							field : 'phoneAreaCode',
							title : '电话区号',
							width : 80
						} ] ]
					});
});
