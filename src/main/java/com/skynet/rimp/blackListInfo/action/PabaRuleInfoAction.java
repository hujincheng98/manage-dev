package com.skynet.rimp.blackListInfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
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
@RequestMapping(value = "/rimp/blackRule/")
public class PabaRuleInfoAction {
	
	private static final Logger logger = Logger.getLogger(PabaRuleInfoAction.class);
	
	@Autowired
	private IPabaRuleInfoService pabaRuleInfoService;
	
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "blackListInfo/blackrule_index";
	}
	
	/**
	 * 返回列表数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<PabaRuleInfo> list(SearchParams params) {
		Pagination<PabaRuleInfo> pageList = null;
		try {
			pageList = this.pabaRuleInfoService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	/**
	 * 保存规则
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@RequestBody PabaRuleInfo pabaRuleInfo)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	SearchParams params = new SearchParams();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("pabaRuleType", pabaRuleInfo.getPabaRuleType());
			searchParams.put("hosId", pabaRuleInfo.getHosId());
			params.setSearchParams(searchParams);
        	List<PabaRuleInfo> list = this.pabaRuleInfoService.findByCondition(params);
        	if (list.isEmpty()) {
        		this.pabaRuleInfoService.insert(pabaRuleInfo);
        		map.put("state", "success");
        		map.put("message", "保存成功");
        	} else {
        		map.put("state", "fail");
        		map.put("message", "保存失败，同医院规则名称不能重复");
        	}
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            map.put("state", "fail");
            map.put("message", "保存失败");
        }
        return map;
    }
	
	/**
	 * 更新规则
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@RequestBody PabaRuleInfo pabaRuleInfo)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			this.pabaRuleInfoService.update(pabaRuleInfo);
			map.put("state", "success");
			map.put("message", "更新成功");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "更新失败");
		}
		return map;
	}
	
	/**
	 * 删除医院信息
	 * @param hosId
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String pabaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.pabaRuleInfoService.delete(pabaId);
			map.put("state", "success");
			map.put("message", "删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
            map.put("message", "删除失败");
		}
		return map;
	}
	
}
