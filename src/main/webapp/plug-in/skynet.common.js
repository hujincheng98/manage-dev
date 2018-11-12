/**
 * ajax JSON数据 POST 异步提交
 * 
 * @param url
 * @param objectData
 * @returns
 */
function doPost(url, objectData) {
	var returnData;
	$.ajax({
		cache : true,
		url : url,
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify(objectData),
		async : false,
		success : function(data) {
			returnData = data;
		},
		error : function(request, status, errorThrown) {
			returnData = {
				state : false
			};
		}
	});
	return returnData;
}