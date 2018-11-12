package com.skynet.rimp.hisBaseInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo;

public interface IHosAreaInfoService extends BaseService<HosAreaInfo> {
	
	public int save(HosAreaInfo hosAreaInfo) throws Exception;
	
	public void update(HosAreaInfo hosAreaInfo) throws Exception;

	public HosAreaInfo getByAreaId(String areaId);

	public int delete(String ids) throws Exception;

	public HosAreaInfo getByAreaName(String areaName);
}
