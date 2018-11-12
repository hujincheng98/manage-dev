package com.skynet.rimp.messPush.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.messPush.dao.MessPushInfoDao;
import com.skynet.rimp.messPush.service.IMessPushService;
import com.skynet.rimp.messPush.vo.MessPushInfo;

@Service("messPushService")
public class MessPushServiceImpl extends BaseServiceImpl<MessPushInfo> implements IMessPushService {

	@Autowired
	private MessPushInfoDao messPushInfoDao;
	
	@Override
	public List<MessPushInfo> findByCondition(SearchParams params) throws Exception {
		return this.messPushInfoDao.findByCondition(params);
	}

	@Override
	public List<MessPushInfo> findAll() throws Exception {
		return this.messPushInfoDao.findAll();
	}

	@Override
	public int insert(MessPushInfo record) {
		record.setCreateDate(new Date());
		record.setCreateUser(UserUtil.getUserId());
		record.setMessState("1");
		return this.messPushInfoDao.insert(record);
	}

	@Override
	public int delete(String messCode) {
		return this.messPushInfoDao.delete(messCode);
	}

	@Override
	public int update(MessPushInfo record) {
		record.setUpdateDate(new Date());
		record.setUpdateUser(UserUtil.getUserId());
		return this.messPushInfoDao.update(record);
	}
	
}
