package com.skynet.rimp.channelInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.channelInfo.vo.OtherHisChannelsInfo;

public interface IOtherHisChannelsInfoService extends BaseService<OtherHisChannelsInfo> {

	public void save(OtherHisChannelsInfo otherHisChannelsInfo);
	
	public void delete(String chHisId);
	
	public void update(OtherHisChannelsInfo otherHisChannelsInfo);
	
	public int saveNumBypk(OtherHisChannelsInfo otherHisChannelsInfo) throws Exception;
}
