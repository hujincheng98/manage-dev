/** 
 * Project Name:rimp_V1.0 
 * File Name:PayInhosPrepaidTransAction.java 
 * Package Name:com.skynet.rimp.registerInfo.action 
 * Date:2017-6-27下午3:26:54 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.registerInfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.service.IPayInhosPrepaidTransService;
import com.skynet.rimp.registerInfo.vo.PayInhosPrepaidTrans;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;

/**
 * @ClassName:		PayInhosPrepaidTransAction.java
 * @Description:	住院预交金充值交易信息 
 * @Date:           2017-6-27 下午3:26:54 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping("/rimp/payInhosPrepaidTrans")
public class PayInhosPrepaidTransAction {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Autowired
	private IPayInhosPrepaidTransService payInhosPrepaidTransService;
	
	
	/**
	 * 跳转到住院预交金充值交易信息查询页面
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerInfo/pay_inhosprepaid_trans_index";
	}
	
	/**
	 * 返回列表json数据
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<PayInhosPrepaidTrans> list(SearchParams params) {
		return this.payInhosPrepaidTransService.list(params);
	}
	/**
	 * 导出excel
	 * exportExcel.json
	 * @throws
	 */
	@RequestMapping(value = "exportExcel.json", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			payInhosPrepaidTransService.getExportExcel(request, response, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
