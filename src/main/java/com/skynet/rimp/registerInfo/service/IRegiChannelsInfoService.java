package com.skynet.rimp.registerInfo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;

public interface IRegiChannelsInfoService extends BaseService<RegiChannelsInfo> {

	int insert(RegiChannelsInfo regiChannelsInfo);

	int delete(String regiId);

	int update(RegiChannelsInfo regiChannelsInfo);
	
	//导出excel
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception;
	
}
