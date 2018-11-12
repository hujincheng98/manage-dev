/*
 * @(#) ReqHisErrorLogServiceImpl  2017-08-03 15:51:35
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
package com.skynet.rimp.registerInfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.registerInfo.dao.ReqHisErrorCodeDao;
import com.skynet.rimp.registerInfo.dao.ReqHisErrorLogDao;
import com.skynet.rimp.registerInfo.service.IReqHisErrorLogService;
import com.skynet.rimp.registerInfo.vo.ReqHisErrorCode;
import com.skynet.rimp.registerInfo.vo.ReqHisErrorLog;

/**
 * 
 * <p>
 * Title: 日志表
 * </p>
 * <p>
 * Description: TODO 日志表Impl层
 * 
 * @author llt
 * @version 1.00.00 创建时间：2017-08-02 10:51:35
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */
@Service
public class ReqHisErrorLogServiceImpl extends BaseServiceImpl<ReqHisErrorLog>
		implements IReqHisErrorLogService {

	@Autowired
	private ReqHisErrorLogDao reqHisErrorLogDao;

	@Autowired
	private IHosInfoService hosInfoService;
	
	@Autowired
	private ReqHisErrorCodeDao reqHisErrorCodeDao;

	@Override
	public List<ReqHisErrorLog> findByCondition(SearchParams params)
			throws Exception {
		return this.reqHisErrorLogDao.findByCondition(params);
	}

	@Override
	public List<ReqHisErrorLog> findAll() throws Exception {
		return this.reqHisErrorLogDao.findAll();
	}

	// 填充对应的组织机构编码
	public void fillingOrgIdByUser(SearchParams params) {
		// 获取机构编码
		String code = UserUtil.getAuthCode();
		String hosId = null;
		// 根据orgid获取医院id
		HosInfo hosInfo = hosInfoService.findByHosOrgId(code);
		if (hosInfo != null) {
			hosId = hosInfo.getHosId();
		}
		// 0表示admin
		if (!code.trim().equals("0")) {
			if (params.getSearchParams() == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("hosId", hosId);
				params.setSearchParams(map);
			} else {
				params.getSearchParams().put("hosId", hosId);
			}
		}
	}

	// 处理查询时间段
	public void formatReqTime(SearchParams params) {
		if (params.getSearchParams() != null) {
			if (params.getSearchParams().get("reqDateStart") != null
					&& params.getSearchParams().get("reqDateStart") != "") {
				params.getSearchParams().put(
						"reqDateStart",
						params.getSearchParams().get("reqDateStart")
								+ " 00:00:00 ");
			}
			if (params.getSearchParams().get("reqDateEnd") != null
					&& params.getSearchParams().get("reqDateEnd") != "") {
				params.getSearchParams().put(
						"reqDateEnd",
						params.getSearchParams().get("reqDateEnd")
								+ " 23:59:59 ");
			}
		}
	}

	@Override
	public Pagination<ReqHisErrorLog> list(SearchParams params) {
		fillingOrgIdByUser(params);
		formatReqTime(params);
		Pagination<ReqHisErrorLog> pageList = null;
		try {
			pageList = findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	@Override
	public void getExportExcel(HttpServletRequest request,
			HttpServletResponse response, SearchParams params) throws Exception {
		// TODO Auto-generated method stub

	}

	//根据错误日志id获取相关的解决方案
	@Override
	public String getResolveById(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		ReqHisErrorLog reqHisErrorLog = reqHisErrorLogDao.findByPrimaryKey(id);
		String transactionCode = reqHisErrorLog.getTransactionCode();
		String errorMsg = reqHisErrorLog.getRespData();
		List<ReqHisErrorCode> lists = reqHisErrorCodeDao.getReqHisErrorCodesByErrorMsg(errorMsg,transactionCode);
		if(lists.size() >0){
			String temp = "";
			for (ReqHisErrorCode r : lists) {
				//temp += r.getResolve()+",";
				temp += r.getResolve()+"\n";
			}
			//String str = temp.substring(0, temp.length()-1);
			return temp;
		}else{
			return "暂无解决方案";
		}
	}

}
