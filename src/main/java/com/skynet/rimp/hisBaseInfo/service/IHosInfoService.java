package com.skynet.rimp.hisBaseInfo.service;

import java.util.List;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;

public interface IHosInfoService extends BaseService<HosInfo> {

	void save(HosInfo hosInfo);
	
	void delete(String hosId);
	
	void update(HosInfo hosInfo);
	
	List<HosInfo> findByHosName(String hosName);
	
	HosInfo findByHosOrgId(String OrgId);
	
	HosInfo findByhosId(String hosId);
	
	// 根据orgId获取hosId
	String getHosIdByOrgId();
	
}
