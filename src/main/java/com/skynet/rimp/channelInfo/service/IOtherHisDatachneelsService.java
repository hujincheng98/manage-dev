package com.skynet.rimp.channelInfo.service;

import java.util.Map;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.channelInfo.vo.OtherHisDatachneelsInfo;

public interface IOtherHisDatachneelsService extends BaseService<OtherHisDatachneelsInfo> {

	int save(OtherHisDatachneelsInfo record);
	
	int update(OtherHisDatachneelsInfo record);
	
	int delete(Integer id);
	
	Map<String, Object> syncDept(Integer id); // 同步科室
	
	Map<String, Object> syncDocm(Integer id); // 同步医生
}
