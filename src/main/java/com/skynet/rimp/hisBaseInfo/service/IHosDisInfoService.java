package com.skynet.rimp.hisBaseInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo;

public interface IHosDisInfoService extends BaseService<HosDisAreaInfo> {

	public int save(HosDisAreaInfo hosDisAreaInfo);
	
	public int delete(String disId);
	
	public int update(HosDisAreaInfo hosDisAreaInfo);
	public HosDisAreaInfo getByDisId(String disId);
	//根据病区名称查找病区
	public HosDisAreaInfo getByDisName(String disName);
}
