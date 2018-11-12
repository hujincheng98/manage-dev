
var dataTable = '#dg';
/**
 * 查询按钮
 * @returns
 */
var _search = function () {
	$(dataTable).datagrid('load', $('#searchform').toJson());
	$('#report').linkbutton('enable'); 
}

/**
 * 导出按钮
 * @returns
 */
var report = function (url) {
	$(dataTable).datagrid('load', $('#searchform').toJson());
	var data = $(dataTable).datagrid('getData');
	if (data && data.rows && data.rows.length > 0) {
		url += "?page=1&rows=10000&" + $('#searchform').serialize();
		window.location.href = url;
	} else {
		$.messager.alert("提示", "数据列表为空，不能导出!", "error");
	}
}

$(function (){
	$(dataTable).datagrid({
		url:'list.json',
		title: "移动支付交易流水信息",
		loadMsg:'数据加载中,请稍后......',
		border:true,
		fitColumns:true,
		remoteSort:false,
		toolbar: "#button-bar",
		singleSelect : true,
		rownumbers: true,
		showFooter: true,
		pagination:true,
		nowrap : true,//把数据显示在一行里
		pageSize : 10, // 页大小
		pageList : [ 10, 20, 30, 40, 50 ], // 页大小下拉选项此项各value是pageSize的倍数
		striped : true, // 行背景交换
		idField : 'ptoId', // 主键
		columns:[[
		    //{field:'ptoID', title:'主键', width:10, sortable:true},
		    //{field:'chToken', title:'渠道token', width:20, sortable:true},
		    {field:'tradeTime', title:'订单生成时间', width:20, sortable:true},
		    {field:'tradeID', title:'交易订单流水号', width:45, sortable:true},
		    {field:'hisTradeID', title:'HIS交易流水号', width:40, sortable:true},
		    /*{field:'hosID', title:'医院id', width:10, sortable:true},
		    {field:'hosName', title:'医院名称', width:10, sortable:true},
		    {field:'orgID', title:'组织机构id', width:10, sortable:true},
		    {field:'mchID', title:'商户号', width:20, sortable:true},
		    {field:'deviceInfo', title:'设备号', width:20, sortable:true},*/
		    {
		    	field:'businessType',
		    	title:'业务类型', 
		    	width:20, 
		    	sortable:true,
		    	formatter: function(value,row,index){
					//业务类型：也即商品描述，字典项business_type，1-预约取号；2.当日挂号；3-诊间支付；4-门诊预交金充值；5-住院预交金充值
	                if (row.businessType=='1'){
	                    return '预约取号';
	                }
	                else if (row.businessType=='2'){
	                	 return '当日挂号';
	                }
	                else if (row.businessType=='3'){
	                	return '诊间支付';
	                }
	                else if (row.businessType=='4'){
	                	return '门诊预交金充值';
	                }
	                else if (row.businessType=='5'){
	                	return '住院预交金充值';
	                }
	            }		    	
		    },
		    {
		    	field:'payType', 
		    	title:'支付类型', 
		    	width:20, 
		    	sortable:true,
		    	formatter: function(value,row,index){
					//支付方式：1-扫码支付，2-刷卡支付，3-公众号小程序支付，4-app支付，5-h5支付
	                if (row.payType=='1'){
	                    return '扫码支付';
	                }
	                else if (row.payType=='2'){
	                	 return '刷卡支付';
	                }
	                else if (row.payType=='3'){
	                	return '公众号小程序支付';
	                }
	                else if (row.payType=='4'){
	                	return 'app支付';
	                }
	                else if (row.payType=='5'){
	                	return 'h5支付';
	                }
	            }
		    },
		    {
		    	field:'tradeType',
		    	title:'支付方式',
		    	width:15,
		    	sortable:true,
		    	formatter: function(value,row,index){
		    		//充值方式：1-微信支付；2-支付宝支付；3-银联支付
	                if (row.tradeType=='1'){
	                    return '微信支付';
	                }
	                else if (row.tradeType=='2'){
	                	 return '支付宝支付';
	                }
	                else if (row.tradeType=='3'){
	                	return '银联支付';
	                }
	            }
		    },
		    {field:'tradeFee', title:'支付金额', width:15, sortable:true,
		      	formatter: function(value,row,index){
		       		if(row.tradeFee!=null && row.tradeFee!="" && typeof(value)!="undefined"){
			       		return row.tradeFee.toFixed(2);	
		       		}
	            }		
		    },
		    //{field:'authCode', title:'授权码', width:20, sortable:true},
		    {field:'chNa', title:'渠道名称', width:20, sortable:true},
		    /*{field:'shopID', title:'门店编号', width:20, sortable:true},
		    {field:'deviceID', title:'设备编号', width:20, sortable:true},
		    {field:'goodsTag', title:'商品标记', width:20, sortable:true},
		    {field:'operatorID', title:'操作员id', width:20, sortable:true},*/
		    {
		    	field:'tradeState',
		    	title:'交易状态',
		    	width:20,
		    	sortable:true,
		    	formatter: function(value,row,index){ 
		    		//交易状态：0-初始状态，1-交易成功，2-支付成功，-1-支付失败，-2-云平台撤销发起，-3-云平台撤销成功，-4-his撤销发起，-5-his撤销成功,-9-"云平台关闭订单成功,-10-云平台关闭订单失败
	                if (row.tradeState=='0'){
	                    return '初始状态';
	                }
	                else if (row.tradeState=='1'){
	                	 return '交易成功';
	                }
	                else if (row.tradeState=='2'){
	                	return '支付成功';
	                }
	                else if (row.tradeState=='-1'){
	                	return '支付失败';
	                }
	                else if (row.tradeState=='-2'){
	                	return '云平台撤销发起';
	                }
	                else if (row.tradeState=='-3'){
	                	return '云平台撤销成功';
	                }
	                else if (row.tradeState=='-4'){
	                	return 'his撤销发起';
	                }
	                else if (row.tradeState=='-5'){
	                	return 'his撤销成功';
	                }
	                else if (row.tradeState=='-9'){
	                	return '云平台关闭订单成功';
	                }
	                else if (row.tradeState=='-10'){
	                	return '云平台关闭订单失败';
	                }
	            }
		    	}
		    /*{field:'attach', title:'his提供的备注', width:20, sortable:true},
		    {field:'resultCode', title:'支付返回信息', width:20, sortable:true},
		    {field:'errMsg', title:'支付返回错误描述', width:20, sortable:true},
		    {
		    	field:'payResult',
		    	title:' 支付结果',
		    	width:20,
		    	sortable:true,
		    	formatter: function(value,row,index){
		    		//支付结果：威富通返回，0—成功；其它—失败
	                if (row.payResult=='0'){
	                    return '成功';
	                }else{
	                	return '失败';
	                }
	            }
		    	},
		    {field:'payInfo', title:'支付结果信息', width:20, sortable:true},
		    {field:'nonceStr', title:'随机字符串', width:20, sortable:true},
		    {field:'tradeCancleTime', title:'交易撤销时间', width:20, sortable:true},
		    {field:'noticeTime', title:'通知次数', width:20, sortable:true}*/
		]]
	});
	
	$(dataTable).datagrid('hiddenHeaderChecked', false);
	
});