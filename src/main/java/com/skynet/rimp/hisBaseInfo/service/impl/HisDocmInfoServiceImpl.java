package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.hisBaseInfo.dao.HisDocmInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHisDocmInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HisDocmInfo;

@Service("hisDocmInfoService")
public class HisDocmInfoServiceImpl extends BaseServiceImpl<HisDocmInfo> implements IHisDocmInfoService {

	@Autowired
	private HisDocmInfoDao hisDocmInfoDao;

	@Override
	public List<HisDocmInfo> findAll() throws Exception {
		return this.hisDocmInfoDao.findAll();
	}

	@Override
	public List<HisDocmInfo> findByCondition(SearchParams params) throws Exception {
		return this.hisDocmInfoDao.findByCondition(params);
	}

}
