$(function() {
	var dataTable = $("#addTable");
	
	$("#addForm").validate();
	
	var shiftid = getURLParameter("shiftid");
	
	$("#addBtn").click(
		function()
		{
			var shiftStartDate = $("#shiftStartDate").val();
			var shiftEndDate = $("#shiftEndDate").val();
			if (shiftStartDate == "" || shiftStartDate == undefined)
			{
				$.messager.alert("提示", "请先选择起始时间。", "info", function(){
					$("#shiftStartDate").focus();
				});
				return false;
			}
			if (shiftEndDate == "" || shiftEndDate == undefined)
			{
				$.messager.alert("提示", "请先选择结束时间。", "info", function(){
					$("#shiftEndDate").focus();
				});
				return false;
			}
			
			var rows =  $(dataTable).datagrid("getRows");
			
			
			var startDate = $('#shiftStartDate').val();
			var endDate = $('#shiftEndDate').val();
			
			if (rows.length > 0) {
				var tislEndDateValue = "";
				var tislEndDate = $(dataTable).datagrid("getEditor", {index : rows.length - 1, field: 'tislEndDate'});
				if (tislEndDate) {
					tislEndDateValue = $(tislEndDate.target).val();
				} else {
					tislEndDateValue = rows[rows.length - 1].tislEndDate;
				}
				if(endDate == tislEndDateValue){
					return;
				}
				$(dataTable).datagrid("appendRow", { 'tislStartDate' : tislEndDateValue, 'tislEndDate' : endDate });
			} else {
				$(dataTable).datagrid("appendRow", { 'tislStartDate' : startDate, 'tislEndDate' : endDate });
			}
			
			var editIndex = $(dataTable).datagrid("getRows").length-1;
			$(dataTable).datagrid("beginEdit", editIndex);
		}
	);
	
	$("#deleteBtn").click(
		function()
		{
			var row = $(dataTable).datagrid("getChecked");
			if (row.length > 0)
			{
				$.messager.confirm("警告", "您确定要删除所选择的记录?", function(isTrue){
					if (isTrue)
					{
						$(row).each(function(index, data){
							var datas = {"tislId" : data.tislId, 'shiftId' : data.shiftId};
							if(!datas.tislId){
								var t_index = $(dataTable).datagrid("getRowIndex",data);
								$(dataTable).datagrid("deleteRow",t_index);
								$(dataTable).datagrid("unselectAll");
								return;
							}
							$.ajax({
								cache : false,
								url : 'delete_tisl.json',
								type : 'POST',
								data : datas,
								async : false,
								success : function(r)
								{
									if (r.state == 'success')
									{
										$.messager.alert("提示", "删除成功", 'info', function (){
											var index = $(dataTable).datagrid('getRowIndex', data);
											$(dataTable).datagrid("deleteRow", index);
										});
									} else {
										$.messager.alert("提示", r.message, 'error');
									}
								}
							});
						});
					}
				});
			}
			else
			{
				$.messager.alert("提示", "请选择要删除的行。");
			}
		}
	);
	
	
	$("#saveBtn").click(
		function()
		{
			if ($('#addForm').form('validate'))
			{
				var json = $(addForm).toJson();
				$(dataTable).datagrid("acceptChanges");
				var edatagrid = $(dataTable).edatagrid("getRows");
				var msg = validateTotal(edatagrid);
				msg += validateGreaterThan(edatagrid);
				msg += validateTimes(edatagrid);
				if (msg != "")
				{
					$.messager.alert("提示", msg);
					return false;
				}
				$(edatagrid).each(function(index, data){
					json["tisls[" + index + "].shiftId"] = data.shiftId == null ? "" : data.shiftId;
					json["tisls[" + index + "].orgId"] = data.orgId == null ? "" : data.orgId;
					json["tisls[" + index + "].remarks"] = data.remarks == null ? "" : data.remarks;
					json["tisls[" + index + "].tislId"] = data.tislId == null ? "" : data.tislId;
					json["tisls[" + index + "].tislStartDate"] = data.tislStartDate;
					json["tisls[" + index + "].tislEndDate"] = data.tislEndDate;
					json["tisls[" + index + "].tislOnline"] = data.tislOnline;
					json["tisls[" + index + "].tislOffline"] = data.tislOffline;
				});
				var data = json;
				$.ajax({
					cache : true,
					url : 'save.json',
					type : 'POST',
					data : data,
					async : false,
					success : function(r)
					{
						if (r.success)
						{
							$.messager.alert("提示", "数据保存成功！", "info", function (){
								$(dataTable).datagrid({url : 'selectTisl.json', 
									queryParams:{  
								        id:r.shiftId
								    }
								});
							});
						} else {
							$.messager.alert("提示", r.message, "error");
						}
					}
				});
			}
		}
	);
	
	$("#updateBtn").click(
			function()
			{
				if ($("#addForm").valid())
				{
					var json = $(addForm).toJson();
					$(dataTable).datagrid("acceptChanges");
					var edatagrid = $(dataTable).edatagrid("getRows");
					var msg = validateTotal(edatagrid);
					msg += validateGreaterThan(edatagrid);
					msg += validateTimes(edatagrid);
					if (msg != "")
					{
						$.messager.alert("提示", msg);
						return false;
					}
					$(edatagrid).each(function(index, data){
						json["tisls[" + index + "].shiftId"] = data.shiftId == null ? "" : data.shiftId;
						json["tisls[" + index + "].orgId"] = data.orgId == null ? "" : data.orgId;
						json["tisls[" + index + "].remarks"] = data.remarks == null ? "" : data.remarks;
						json["tisls[" + index + "].tislId"] = data.tislId == null ? "" : data.tislId;
						json["tisls[" + index + "].tislStartDate"] = data.tislStartDate;
						json["tisls[" + index + "].tislEndDate"] = data.tislEndDate;
						json["tisls[" + index + "].tislOnline"] = data.tislOnline;
						json["tisls[" + index + "].tislOffline"] = data.tislOffline;
					});
					var data = json;
					$.ajax({
						cache : true,
						url : 'update.json',
						type : 'POST',
						data : data,
						async : false,
						success : function(r)
						{
							if (r.success)
							{
								$.messager.alert("提示", "数据更新成功！", "info", function (){
									$(dataTable).datagrid({url : 'selectTisl.json', 
										queryParams:{  
									        id:r.shiftId
									    }
									});
								});
							} else {
								$.messager.alert("提示", r.message, "error");
							}
						}
					});
				}
			}
	);
	
	
	$(dataTable).edatagrid({
		url:'',
		title: "时段明细列表",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : true,
		rownumbers: false,
		showFooter: true,
		columns:[[
		          {field:'shiftId', hidden:true},
		          {field:'orgId', hidden:true},
		          {field:'remarks', hidden:true},
		          {field:'createDate', hidden:true},
		          {field:'createUser', hidden:true},
		          {field:'updateDate', hidden:true},
		          {field:'updateUser', hidden:true},
		    {field:'tislId',title:'选择', checkbox:true},
		    {field:'tislStartDate',title:'时段起始时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')}'}}, width:"25%"},
		    {field:'tislEndDate',title:'时段终止时间', editor:{type:'datebox', options : {required:true, dateFmt:'H:mm', minDate:'#F{$dp.$D(\'shiftStartDate\')}', maxDate:'#F{$dp.$D(\'shiftEndDate\')}'}}, width:"25%"},
			{field:'tislOnline',title:'线上可预约限数百分比(%)', editor:{type:'numberbox', options : {value:0, min:1, max:100, precision:0}}, width:"25%"},
			{field:'tislOffline',title:'线下可预约限数百分比(%)', editor:{type:'numberbox', options : {value:0, min:1, max:100, precision:0}}, width:"24.8%"}
		]]
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
	if (shiftid != null || shiftid != undefined)
	{
		var data = {"id" : shiftid};
		$.ajax({
			cache : false,
			url : 'select.json',
			type : 'POST',
			data : data,
			async : false,
			success : function(data)
			{
				$("#addForm").fromJson(data);
				$(dataTable).datagrid("loadData", data.tisls);
			}
		});
	}
});


/**
 * 判断起始时间是否输入
 * @param element
 * @param id
 */
function checkStartTime(element, id){
	var val = $(id).val();
	if (val == "" || val == undefined)
	{
		
		$(element).val("");
		$.messager.alert("提示", "请先选择起始时间。", "info", function(){
			$(id).focus();
		});
	}else{
		if($(element).val() == $(id).val()){
			$(element).val("");
			$.messager.alert("提示", "起始时间和结束时间不能相等。", "info", function(){
				$(element).focus();
			});
		}
	}
}


/**
 * 判断总和是否等于100
 * @param datagrid
 * @returns {String}
 */
function validateTotal(datagrid)
{
	if (datagrid.length == 0)
	{
		return "";
	}
	var totalOnline = 0;
	var totalOffline = 0;
	$(datagrid).each(function(index, data){
		totalOnline += parseInt(data.tislOnline);
		totalOffline += parseInt(data.tislOffline);
	});
	var msg = "";
	if (totalOnline != 100)
	{
		msg += "[线上可预约限数百分比]总和不为100%。<br />";
	}
	if (totalOffline != 100)
	{
		msg += "[线下可预约限数百分比]总和不为100%。<br />";
	}
	return msg;
}

/**
 * 判断结束时间是否大于 开始时间
 * @param endTime 结束时间
 * @param startTime 开始时间
 */
function validateGreaterThan(datagrid)
{
	var msg = "";
	$(datagrid).each(function(index, data){
		var startTime = data.tislStartDate;
		var endTime = data.tislEndDate;
		
		var arr = endTime.split(":");
		var endDate = new Date();
		endDate.setHours(arr[0], arr[1], 0, 0);
		
		arr = startTime.split(":");
		var startDate = new Date();
		startDate.setHours(arr[0], arr[1], 0, 0);
		
		if (startDate > endDate)
		{
			msg += "第"+(index+1)+"条记录：起始时间【"+startTime+"】大于终止时间【"+endTime+"】<br />";
		}
		
		var shiftStartTime = $("#shiftStartDate").val();
		var shiftEndTime = $("#shiftEndDate").val();
		
		var shiftArr = shiftStartTime.split(":");
		var shiftStartDate = new Date();
		shiftStartDate.setHours(shiftArr[0], shiftArr[1], 0, 0);
		
		shiftArr = shiftEndTime.split(":");
		var shiftEndDate = new Date();
		shiftEndDate.setHours(shiftArr[0], shiftArr[1], 0, 0);
		
		if (startDate < shiftStartDate) {
			msg += "第"+(index+1)+"条记录：时段明细起始时间【"+startTime+"】小于班次起始时间【"+shiftStartTime+"】<br />";
		}
		if (endDate > shiftEndDate) {
			msg += "第"+(index+1)+"条记录：时段明细结束时间【"+endTime+"】大于班次结束时间【"+shiftEndTime+"】<br />";
		}
		
	});
	return msg;
}
/**
 * 判断日期是否有交叉
 * @param datagrid
 * @returns {String}
 */
function validateTimes(datagrid)
{
	var msg = "";
	$(datagrid).each(function(index, data){
		$(datagrid).each(function(nindex, ndata){
			var err = true;
			if (index != nindex)
			{
				if (data.tislStartDate < ndata.tislStartDate && data.tislEndDate > ndata.tislStartDate)
				{
					msg += "第"+(index+1)+"条数据与第"+(nindex+1)+"条数据时间区间有交叉。";
					err = false;
				}
				if (data.tislStartDate < ndata.tislEndDate && data.tislEndDate > ndata.tislEndDate && err)
				{
					msg += "第"+(index+1)+"条数据与第"+(nindex+1)+"条数据时间区间有交叉。";
				}
			}
			
		});
	});
	return msg;
}