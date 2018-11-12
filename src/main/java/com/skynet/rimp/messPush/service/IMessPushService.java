package com.skynet.rimp.messPush.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.messPush.vo.MessPushInfo;

public interface IMessPushService extends BaseService<MessPushInfo> {

	int insert(MessPushInfo record);
	
	int delete(String messCode);
	
	int update(MessPushInfo record);
}
