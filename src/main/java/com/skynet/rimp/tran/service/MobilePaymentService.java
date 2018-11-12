/** 
 * Project Name:rimp_V1.0 
 * File Name:MobilePaymentService.java 
 * Package Name:com.skynet.rimp.tran.service 
 * Date:2017-6-6上午9:39:15 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;

/**
 * @ClassName:		MobilePaymentService.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-6-7 上午9:39:15 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
public interface MobilePaymentService {
	
	
	//导出excel
	public void getExportExcel(HttpServletRequest request,HttpServletResponse response,SearchParams params) throws Exception;

	//分页列表展示
	public Object getListByPage(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
	//返回渠道信息
	public List<OtherChannelsInfo> findListByOrgId();
	
}
