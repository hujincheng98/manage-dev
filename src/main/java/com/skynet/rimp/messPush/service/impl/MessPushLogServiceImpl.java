package com.skynet.rimp.messPush.service.impl;

import com.skynet.rimp.messPush.dao.MessPushLogInfoDao;
import com.skynet.rimp.messPush.dto.MessPushBusiLogInfo;
import com.skynet.rimp.messPush.service.MessPushLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messPushLogService")
public class MessPushLogServiceImpl implements MessPushLogService {
	
	@Autowired
	private MessPushLogInfoDao messPushLogInfoDao;
	
	/**
	 * 日志记录
	 */
	@Override
	public void messPushLog(MessPushBusiLogInfo messPushBusiLogInfo) {
		messPushLogInfoDao.insert(messPushBusiLogInfo);
	}

}
