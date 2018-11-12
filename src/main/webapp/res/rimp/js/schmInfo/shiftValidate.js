
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
	}
}


/**
 * 判断总和是否等于100
 * @param datagrid
 * @returns {String}
 */
function validateTotal(datagrid)
{
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