package com.skynet.rimp.blackListInfo.service;

import java.util.Map;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.blackListInfo.vo.PabaRuleInfo;

public interface IPabaRuleInfoService extends BaseService<PabaRuleInfo> {
	
	void insert(PabaRuleInfo pabaRuleInfo);

	void delete(String pabaId);

	void update(PabaRuleInfo pabaRuleInfo);
	
	public Map<String, Map> initAllRul();

}
