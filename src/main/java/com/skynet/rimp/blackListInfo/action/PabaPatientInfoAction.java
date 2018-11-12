package com.skynet.rimp.blackListInfo.action;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.blackListInfo.service.IPabaPatientInfoService;
import com.skynet.rimp.blackListInfo.vo.PabaPatientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
/**
 * <p>Title: 黑名单</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-11 下午3:18:33
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping(value = "/rimp/paba/")
public class PabaPatientInfoAction {
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private IPabaPatientInfoService pabaInfoService;

	/**
	 * 黑名单首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "blackListInfo/pada_Index";
	}

	/**
	 * 列表数据
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<PabaPatientInfo> list(SearchParams params) {
		Pagination<PabaPatientInfo> pageList = null;
		try {
			pageList = this.pabaInfoService.findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}
	/*
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(PabaPatientInfo pabaPatientInfo){
		Map<String, Object> model = new HashMap<String, Object>();
		int result = this.pabaInfoService.updateByPrimaryKeySelective(pabaPatientInfo);
		if(result>0){
			model.put("state", "0");
			model.put("info", "保存成功");
		}else{
			model.put("state", "-1");
			model.put("info", "保存失败");
		}
		return model;
	}*/

	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(String[] pabaPatientIds,String pabaPatientState)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			for (String id : pabaPatientIds) {
				PabaPatientInfo pabaPatientInfo =new PabaPatientInfo();
				pabaPatientInfo.setPabaPatientId(id);
				pabaPatientInfo.setPabaPatientState(pabaPatientState);
				pabaInfoService.updateByPrimaryKeySelective(pabaPatientInfo);
			}
			model.put("state", "0");
			model.put("info", "保存成功");
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			model.put("state", "-1");
			model.put("info", "保存失败");
		}
		return model;

	}
}
