$(function() {
	var dataTable = $("#tableList");
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
	$('#searchParams[shiftEndDate]').change(function (){
		var startTime = $('#searchParams[shiftStartDate]').val();
		alert(startTime >= $(this).val());
	});
	
	//重置
	$("#clearBtn").click(function(){
		$('#searchform').form('clear');
		var json = $("#searchform").toJson();
		$(dataTable).datagrid("reload", json);
	});
	
	/**
	 * 删除
	 */
	$("#deleteBtn").click(function(){
		var rows = $(dataTable).datagrid("getChecked");
		if (rows.length > 0)
		{
			$.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue){
				if(isTrue)
				{
					var ids = "";
					$(rows).each(function(index, data){
						ids += data.shiftId + ",";
					});
					var data = {"ids" : ids};
					$.ajax({
						url : 'delete.json',
						type : 'POST',
						data : data,
						async : false,
						success : function(data)
						{
							if (data.state)
							{
								$.messager.alert("提示", data.msg);
							}
							else
							{
								$.messager.alert("提示", "删除成功！");
							}
							$(dataTable).datagrid("clearChecked");
							var obj = $(dataTable).datagrid("options").queryParams;
							$(dataTable).datagrid("reload", obj);
						}
					});
				}
			});
		}
		else
		{
			$.messager.alert("提示", "请选择要删除的行。");
		}
	});
	
	$("#searchBtn").click(function(){
		
		var startTime = $('#shiftStartDate').val();
		var endTime = $('#shiftEndDate').val();
		var arr1 = startTime.split(':');
		var arr2 = endTime.split(':');
		if (parseInt(arr1[0]) > parseInt(arr2[0]) || (parseInt(arr1[0]) == parseInt(arr2[0]) && parseInt(arr1[1]) > parseInt(arr2[1]))) {
			$.messager.alert("提示", '起始时间不能大于结束时间', 'warning', function (){
				$('#shiftEndDate').val('');
			});
		} else {
			var json = $("#searchform").toJson();
			$(dataTable).datagrid("reload", json);
		}
	});
	
	
	
	/**
	 * 编辑
	 */
	$("#editBtn").click(function(){
		var row = $(dataTable).datagrid("getChecked");
		if (row != null)
		{
			if (row.length == 1)
			{
				$(this).attr("url", platRoot+"/rimp/shift/edit.do?shiftid=" + row[0].shiftId);
				openTab(this);
			}
			else
			{
				$.messager.alert("提示", "最多只能选择一条数据进行编辑。");
			}
		}
		else
		{
			$.messager.alert("提示", "请选择要编辑的行。");
		}
	});
});