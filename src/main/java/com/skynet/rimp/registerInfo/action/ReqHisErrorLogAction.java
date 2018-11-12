/*
 * @(#) ReqHisErrorLogApi  2017-08-03 15:51:35
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
package com.skynet.rimp.registerInfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.service.IReqHisErrorLogService;
import com.skynet.rimp.registerInfo.vo.ReqHisErrorLog;

/**
 * 
 * <p>Title: 日志表</p>
 * <p>Description: TODO 日志表API层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-08-02 10:51:35
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */   
@Controller
@RequestMapping(value = "/rimp/reqHisErrorLog")
public class ReqHisErrorLogAction{    
    
    @Autowired
    private IReqHisErrorLogService reqHisErrorLogService;
 
    /**
	 * 跳转到错误日志查询页面
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerInfo/req_his_error_Log_index";
	}
	
	/**
	 * 返回列表json数据
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<ReqHisErrorLog> list(SearchParams params) {
		return this.reqHisErrorLogService.list(params);
	}
	
	/**
	 * 导出excel
	 * exportExcel.json
	 * @throws
	 */
	@RequestMapping(value = "exportExcel.json", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			reqHisErrorLogService.getExportExcel(request, response, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 跳转到错误日志查询页面
	* @return
	*/
	@RequestMapping(value = "viewHisErrorLog.do", method = RequestMethod.GET)
	public ModelAndView viewHisErrorLog(HttpServletRequest request, HttpServletResponse response) {
		    ModelAndView view = new ModelAndView();
		    String servletDetails = reqHisErrorLogService.getResolveById(request, response);
		    view.addObject("servletDetails", servletDetails);
		    view.setViewName("registerInfo/req_his_error_log_view");
			return view;
		}
    
}
