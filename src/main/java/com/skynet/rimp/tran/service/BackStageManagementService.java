/** 
 * Project Name:rimp_V1.0 
 * File Name:backStageManagementService.java 
 * Package Name:com.skynet.rimp.tran.service 
 * Date:2017-5-2上午9:21:33 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skynet.common.SearchParams;


/**
 * @ClassName:		backStageManagementService.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-5-2 上午9:21:33 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
public interface BackStageManagementService {
	
	public void getExportExcel(HttpServletRequest request,HttpServletResponse response,SearchParams params) throws Exception;

}
