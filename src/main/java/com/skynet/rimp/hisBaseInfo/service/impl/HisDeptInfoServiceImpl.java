package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.hisBaseInfo.dao.HisDeptInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHisDeptInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HisDeptInfo;

@Service
public class HisDeptInfoServiceImpl extends BaseServiceImpl<HisDeptInfo> implements
		IHisDeptInfoService {
	@Autowired
	private HisDeptInfoDao hisDeptInfoDao;

	@Override
	public List<HisDeptInfo> findByCondition(SearchParams params)
			throws Exception {
		return hisDeptInfoDao.findByCondition(params);
	}
	@Override
	public List<HisDeptInfo> findAll() throws Exception {
		return hisDeptInfoDao.findAll();
	}
}
