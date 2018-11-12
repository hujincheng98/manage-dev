package com.skynet.rimp.blackListInfo.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.rimp.blackListInfo.service.IPabaRuleInfoService;
import com.skynet.rimp.blackListInfo.vo.PabaRuleInfo;

/**
 * <p>Title: 黑名单规则</p>
 * <p>Description: </p>
 *
 * @author huyang
 * @version 1.00.00
 * @date 2015-7-11 下午3:18:33 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping(value = "/rimp/patientRegRule/")
public class PatientRegRuleAction {
	
	private static final Logger logger = Logger.getLogger(PabaRuleInfoAction.class);
	
	@Autowired
	private IPabaRuleInfoService pabaRuleInfoService;
	
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerRuleInfo/patient_reg_index";
	}
	
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	@ResponseBody
	public PabaRuleInfo list() {
		return null;
	}
	
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(PabaRuleInfo pabaRuleInfo){
		pabaRuleInfoService.update(pabaRuleInfo);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("success", true);
		model.put("msg", "保存成功");
		return model;
	}
}
