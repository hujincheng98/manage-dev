/** 
 * Project Name:rimp_V1.0 
 * File Name:PayClinicRecipeTransAction.java 
 * Package Name:com.skynet.rimp.registerInfo.action 
 * Date:2017-6-28下午1:50:00 
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
import com.skynet.rimp.registerInfo.service.IPayClinicRecipeTransService;
import com.skynet.rimp.registerInfo.vo.PayClinicRecipeTrans;
import com.skynet.rimp.registerInfo.vo.PayInhosPrepaidTrans;

/**
 * @ClassName:		PayClinicRecipeTransAction.java
 * @Description:	门诊处方缴费
 * @Date:           2017-6-28 下午4:50:00 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping("/rimp/payClinicRecipeTrans")
public class PayClinicRecipeTransAction {
		
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private IPayClinicRecipeTransService payClinicRecipeTransService;
	
	
	/**
	 * 跳转到住院预交金充值交易信息查询页面
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerInfo/pay_clinicrecipe_trans_index";
	}
	
	/**
	 * 返回列表json数据
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<PayClinicRecipeTrans> list(SearchParams params) {
		return this.payClinicRecipeTransService.list(params);
	}
	/**
	 * 导出excel
	 * exportExcel.json
	 * @throws
	 */
	@RequestMapping(value = "exportExcel.json", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			payClinicRecipeTransService.getExportExcel(request, response, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
