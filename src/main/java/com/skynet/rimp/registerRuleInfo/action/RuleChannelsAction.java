package com.skynet.rimp.registerRuleInfo.action;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;

/**
 * 
 * <p>Title: 第三方渠道管理规则管理</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */
@Controller
@RequestMapping("/rimp/ruleInfo")
public class RuleChannelsAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleChannelsAction.class);

	@Autowired
	private IOtherChannelsInfoService otherChannelsInfoService;
	
	/**
	 * 跳转到渠道管理规则设置首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "registerRuleInfo/rule_channels_index";
	}
	
	/**
	 * 返回列表json数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<OtherChannelsInfo> list(SearchParams params) {
		Pagination<OtherChannelsInfo> pageList = null;
		try {
			pageList = this.otherChannelsInfoService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(OtherChannelsInfo otherChannelsInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result=otherChannelsInfoService.saveNumBypk(otherChannelsInfo);
			
			if(result>0){
				map.put("success", true);
				map.put("msg", "修改完成");
			}else{
				map.put("success", false);
				map.put("msg", "系统出错");
			}
			
			
		} catch (Exception e1) {
			map.put("success", false);
			map.put("msg", "系统出错");
			e1.printStackTrace();
		}
		return map;
	}
	
	
	
}
