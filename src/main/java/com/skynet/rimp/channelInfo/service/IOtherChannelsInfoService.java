package com.skynet.rimp.channelInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;

import java.util.List;

public interface IOtherChannelsInfoService extends BaseService<OtherChannelsInfo> {

	public void save(OtherChannelsInfo otherChannelsInfo);
	
	public void delete(String chId);
	
	public void deleteByKeyArr(String[] chIdArr) throws Exception;
	
	public void update(OtherChannelsInfo otherChannelsInfo);
	
	public int saveNumBypk(OtherChannelsInfo otherChannelsInfo) throws Exception;
	
	public List<OtherChannelsInfo> findListByOrgId(String orgId);

	public OtherChannelsInfo findByHosId(String hosId);

}
