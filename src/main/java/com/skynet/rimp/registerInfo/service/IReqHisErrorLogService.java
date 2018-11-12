/*
 * @(#) IReqHisErrorLogService  2017-08-03 15:51:35
 *
 * Copyright 2003 by TiuWeb Corporation.
 * 51 zhangba six Road, xian, PRC 710065 // Replace with xian’s address
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * TiuWeb Corporation.  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with TiuWeb.
 */
package com.skynet.rimp.registerInfo.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.registerInfo.vo.ReqHisErrorLog;
  
/**
 * 
 * <p>Title: 日志表</p>
 * <p>Description: TODO 日志表Service层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-02 10:10:35
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
public interface IReqHisErrorLogService extends BaseService<ReqHisErrorLog> 
{
	
	//返回列表json数据
	public Pagination<ReqHisErrorLog> list(SearchParams params);
			
	//导出excel
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception;
	
	//根据错误日志id获取相关的解决方案
	public String getResolveById(HttpServletRequest request, HttpServletResponse response);
	
}
