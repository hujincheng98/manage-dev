package com.skynet.rimp.registerInfo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;

public interface IPayMedicalCardTransService extends BaseService<PayMedicalCardTransInfo> {

	int insert(PayMedicalCardTransInfo record);
	
	int delete(Integer transId);
	
	int update(PayMedicalCardTransInfo record);
	
	//导出excel
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception;
}
