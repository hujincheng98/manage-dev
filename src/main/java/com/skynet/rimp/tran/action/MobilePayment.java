/** 
 * Project Name:rimp_V1.0 
 * File Name:signDoctor.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-5-18下午5:08:26 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.tran.service.MobilePaymentService;



/**
 * @ClassName:		MobilePayment.java
 * @Description:	his移动支付交易流水查询
 * @Date:           2017-06-06 下午5:08:26 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping(value = "/rimp/mobilePayment/")
public class MobilePayment {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName()); 
	
	@Autowired
	private MobilePaymentService mobilePaymentService; 
	
	
	/** 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("mobilePayment/mobile_payment_index");
		return view;
	}
	
	/**
	 * 转发分页查询
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Object list(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		return mobilePaymentService.getListByPage(params, req, resp);
	}
	
	
	/**
	 * 导出excel
	 * exportExcel.json
	 * @throws
	 */
	@RequestMapping(value = "exportExcel.json", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			mobilePaymentService.getExportExcel(request, response, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取渠道名称下拉框数据
	 */
	@RequestMapping(value = "listBychName.json", method = RequestMethod.POST)
	@ResponseBody
	public Object listBychName() {
		return mobilePaymentService.findListByOrgId();
	}
}
